package com.santander.batch.negativefilesrequest.service.impl;

import com.santander.darwin.core.exceptions.InternalServerErrorDarwinException;
import com.santander.darwin.core.exceptions.NotFoundDarwinException;
import com.santander.batch.negativefilesrequest.model.BureauInfo;
import com.santander.batch.negativefilesrequest.model.BureauResponseData;
import com.santander.batch.negativefilesrequest.model.DecisionRisk;
import com.santander.batch.negativefilesrequest.model.dto.RiskDataInformationReq;
import com.santander.batch.negativefilesrequest.model.dto.ParticipantsData;
import com.santander.batch.negativefilesrequest.model.dto.T1Data;
import com.santander.batch.negativefilesrequest.model.dto.RiskDataInformationRes;
import com.santander.batch.negativefilesrequest.model.dto.BureausData;
import com.santander.batch.negativefilesrequest.repository.MPDT606Repository;
import com.santander.batch.negativefilesrequest.service.DecisionRiskService;
import com.santander.batch.negativefilesrequest.service.ExternalAPIService;
import com.santander.batch.negativefilesrequest.service.SecurityTokenService;
import com.santander.batch.negativefilesrequest.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Bureau service.
 */
@Service
@Slf4j
public class DecisionRiskServiceImpl implements DecisionRiskService {

    /**
     * The constant LOG_APPROVE_REJECT_PROPOSAL.
     */
    private static final String LOG_HUB_BUREAU_API = "[API][callHubBureauApi]";
    /**
     * The hub gateway
     */
    @Value("${app.hub.gateway-risk}")
    public String hubGatewayRisk;
    /**
     * The evaluate endpoint
     */
    @Value("${app.hub.risk-data-information}")
    public String riskDataInfoEndpoint;

    /**
     * The Channel.
     */
    @Value("${app.bureau.channel}")
    public String channel;

    /**
     * The Client id.
     */
    @Value("${app.bureau.clientId}")
    public String clientId;

    /**
     * The External api service.
     */
    private final ExternalAPIService externalAPIService;

    /**
     * MPDT606 repository
     */
    private final MPDT606Repository mpdt606Repository;

    /**
     * The Security Token Service.
     */
    private final SecurityTokenService securityTokenService;

    /**
     * Instantiates a new Bureau service.
     *
     * @param externalAPIService the external api service
     * @param mpdt606Repository the external mpdt606 repository
     * @param securityTokenService the security token service
     */
    public DecisionRiskServiceImpl(ExternalAPIService externalAPIService,
                                   MPDT606Repository mpdt606Repository,
                                   SecurityTokenService securityTokenService) {
        this.externalAPIService = externalAPIService;
        this.mpdt606Repository = mpdt606Repository;
        this.securityTokenService = securityTokenService;
    }

    /**
     * Get bureau information bureau info response.
     *
     * @param decisionRisk the proposal auxiliary info
     * @return the bureau info response
     */
    @Override
    public BureauInfo getBureausInformation(DecisionRisk decisionRisk) {

        BureauInfo bureauInfo;
        //Generate the bureau codes
        List<String> bureauCodes = new ArrayList<>(Constants.TWO);
        //Add morosidad & incidencias judiciales
        bureauCodes.add("MO");
        bureauCodes.add("IJ");
        //Generate the request
        RiskDataInformationReq riskDataRequest = new RiskDataInformationReq(
                null,
                new ParticipantsData(new T1Data(Constants.PHYSIC,
                        Integer.parseInt(decisionRisk.getCdPersona()))),
                bureauCodes);
        try {
            //Call bureau API
            String token = securityTokenService.getSecurityToken();
            RiskDataInformationRes riskDataResponse = externalAPIService.
                    doPost(hubGatewayRisk, riskDataInfoEndpoint, riskDataRequest, RiskDataInformationRes.class,
                            null, getHeaders(token, channel, clientId)).getBody();
            if (riskDataResponse == null) {
                bureauInfo = new BureauInfo(Constants.STR_N, null, decisionRisk);
            } else {
                bureauInfo = evaluateRiskDecisionResponse(riskDataResponse, decisionRisk);
            }
            return bureauInfo;
        } catch (HttpClientErrorException hce) {
            //Log and throw exception
            log.error(LOG_HUB_BUREAU_API, hce);
            throw new NotFoundDarwinException(Constants.FUNCTIONAL_ERROR + hce.getMessage());
        } catch (Exception e) {
            //Log and throw exception
            log.error(LOG_HUB_BUREAU_API, e);
            throw new InternalServerErrorDarwinException(Constants.TECHNICAL_ERROR, e.getMessage());
        }
    }

    /**
     * Evaluate risk decision response bureau info response.
     *
     * @param riskDataInformationRes the risk data information response
     * @param decisionRisk the DecisionRisk
     * @return the bureau info response
     */
    private BureauInfo evaluateRiskDecisionResponse(RiskDataInformationRes riskDataInformationRes,
                                                    DecisionRisk decisionRisk) {
        BureauInfo bureauInfo;
        //Generate the result include list
        List<BureauResponseData> includedList = new ArrayList<>();
        //Get the response bureau list
        List<BureausData> bureausList =
                riskDataInformationRes.getParticipantsInformation().getT1().getBureausData();
        // Transformation of the information obtained from bureau responses
        // If have not error and inclusion Ind is S, add to inclusion list
        if (bureausList != null) {
            includedList = bureausList.stream()
                    .filter(bureaus ->
                            bureaus.getError() == null && bureaus.getBureauData() != null
                                    && Constants.STR_S.equals(bureaus.getBureauData()
                                        .getTotalSummary().getInclusionInd())
                    ).map(bureausFilter ->
                            new BureauResponseData(
                                    bureausFilter.getBureauCode(),
                                    bureauDescription(decisionRisk.getIdEmpr(), bureausFilter.getBureauCode()),
                                    Constants.STR_Y,
                                    bureausFilter.getBureauData().getTotalSummary().getNumberOfUnpaidOperations(),
                                    bureausFilter.getBureauData().getTotalSummary().getTotalUnpaidPaymentAmount(),
                                    bureausFilter.getBureauData().getTotalSummary().getLastUnpaidDate(),
                                    bureausFilter.getBureauData().getTotalSummary().getOriginInd(),
                                    bureauOriginDescription(decisionRisk.getIdEmpr(),
                                            bureausFilter.getBureauData().getTotalSummary().getOriginInd()))
                    ).collect(Collectors.toList());
        }
        // Return the result
        if (includedList.isEmpty()) {
            bureauInfo = new BureauInfo(Constants.STR_N, null, decisionRisk);
        } else {
            bureauInfo = new BureauInfo(Constants.STR_Y, includedList, decisionRisk);
        }
        return bureauInfo;
    }

    /**
     * Method to obtain the
     * bureau code description.
     *
     * @param companyCode the entity code
     * @param valor       the bureau code
     * @return description of value
     */
    private String bureauDescription(String companyCode, String valor) {
        return mpdt606Repository.findDescription(companyCode, valor, "BUREAUCODE").getDsvalor().trim();
    }

    /**
     * Method to obtain the
     * description of the origin
     * of the indicator.
     *
     * @param companyCode the entity code
     * @param valor       the origin indicator
     * @return description of value
     */
    private String bureauOriginDescription(String companyCode, String valor) {
        return mpdt606Repository.findDescription(companyCode, valor, "ORIGININD").getDsvalor().trim();
    }

    /**
     * Gets headers.
     * Method to get the headers necessary to call get token service
     *
     * @param token   the token
     * @param channel the channel
     * @param appKey  the app key
     * @return the headers
     */
    private Map<String, String> getHeaders(String token, String channel, String appKey) {
        Map<String, String> darwinHeaders = new HashMap<>();
        darwinHeaders.put("Authorization", "Bearer " + token);
        darwinHeaders.put("X-Santander-Channel", channel);
        darwinHeaders.put("X-Clientid", appKey);
        return darwinHeaders;
    }
}

