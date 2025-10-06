package com.santander.batch.negativefilesrequest.service.impl;

import com.santander.batch.negativefilesrequest.model.BureauInfo;
import com.santander.batch.negativefilesrequest.model.CxmInfo;
import com.santander.batch.negativefilesrequest.model.dto.CxmData;
import com.santander.batch.negativefilesrequest.model.dto.CxmDataCase;
import com.santander.batch.negativefilesrequest.model.dto.CxmReq;
import com.santander.batch.negativefilesrequest.model.dto.CxmRes;
import com.santander.batch.negativefilesrequest.service.CxmService;
import com.santander.batch.negativefilesrequest.service.ExternalAPIService;
import com.santander.batch.negativefilesrequest.service.SecurityTokenService;
import com.santander.batch.negativefilesrequest.utils.Constants;
import com.santander.darwin.core.exceptions.InternalServerErrorDarwinException;
import com.santander.darwin.core.exceptions.NotFoundDarwinException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * The Cxm service.
 */
@Service
@Slf4j
public class CxmServiceImpl implements CxmService {

    /**
     * The hub gateway
     */
    @Value("${app.hub.gateway-cxm}")
    public String hubGatewayCxm;

    /**
     * The evaluate endpoint
     */
    @Value("${app.hub.cxm-send-data}")
    public String cxmSendDataEndpoint;

    /**
     * The consumer id
     */
    @Value("${app.hub.application-id}")
    public String cxmApplicationId;

    /**
     * The External api service.
     */
    private final ExternalAPIService externalAPIService;

    /**
     * The Security Token Service.
     */
    private SecurityTokenService securityTokenService;

    /**
     * Instantiates a new Cxm service.
     *
     * @param externalAPIService the external api service
     * @param securityTokenService the security token service
     */
    public CxmServiceImpl(ExternalAPIService externalAPIService,
                          SecurityTokenService securityTokenService) {
        this.externalAPIService = externalAPIService;
        this.securityTokenService = securityTokenService;
    }

    @Override
    public CxmInfo sendCxmData(BureauInfo bureauInfo) {

        CxmInfo cxmInfo;

        List<CxmDataCase> cxmDataCaseList = createCxmDataCasesList(bureauInfo);

        //Si la respuesta de Bureau fue mala, devuelve una respuesta de Cxm a null
        if (bureauInfo.getBureauIncludedIndicator().equals(Constants.STR_N) || cxmDataCaseList.isEmpty()) {
            return new CxmInfo(Constants.STR_N, bureauInfo.getDecisionRisk().getCdPersona(),
                    bureauInfo.getDecisionRisk().getDataDatePart(),
                    null);
        }

        CxmData cxmData = new CxmData(cxmDataCaseList);
        CxmReq cxmReq = new CxmReq(cxmApplicationId, Constants.EMAIL_CHANNEL, Constants.SPANISH,
                Constants.PHYSIC+bureauInfo.getDecisionRisk().getCdPersona(),
                cxmData);
        try {
            //Call cxm API
            String token = securityTokenService.getSecurityToken();
            CxmRes cxmRes = externalAPIService.
                    doPost(hubGatewayCxm, cxmSendDataEndpoint, cxmReq, CxmRes.class,
                            null, getHeaders(token)).getBody();

            if (cxmRes == null) {
                log.info("Respuesta de CXM a null");
                //Escribir en fichero KO
                //Hacer funcion para que transforme la salida a CxmInfo
                cxmInfo = new CxmInfo(Constants.STR_E, bureauInfo.getDecisionRisk().getCdPersona(),
                        bureauInfo.getDecisionRisk().getDataDatePart(),
                        null);
            } else {
                log.info("Respuesta con cuerpo");
                //log.info(bureauInfo.getBureauList().get(0).getBureauDescription());
                cxmInfo = new CxmInfo(Constants.STR_Y, bureauInfo.getDecisionRisk().getCdPersona(),
                        bureauInfo.getDecisionRisk().getDataDatePart(),
                        cxmRes);
            }
            return cxmInfo;

        } catch (HttpClientErrorException hce) {
            //TODO
            throw new NotFoundDarwinException(Constants.FUNCTIONAL_ERROR + hce.getMessage());
        } catch (HttpServerErrorException hse){
            //Borrar cuando tenga application_id
            /*cxmInfo = new CxmInfo(Constants.STR_Y, bureauInfo.getDecisionRisk().getCdPersona(),
                    bureauInfo.getDecisionRisk().getTsInicio(),
                    null);
            return cxmInfo;*/
            throw new InternalServerErrorDarwinException(Constants.TECHNICAL_ERROR, hse.getMessage());
        } catch (Exception e) {
            //TODO
            throw new InternalServerErrorDarwinException(Constants.TECHNICAL_ERROR, e.getMessage());
        }

    }

    /**
     * Create CxmDataList from BureauInfo.
     *
     * @param bureauInfo the bureau information
     * @return list of CxmDataCase
     */
    private List<CxmDataCase> createCxmDataCasesList(BureauInfo bureauInfo) {

        List<CxmDataCase> casesList = new ArrayList<>();

        if (bureauInfo.getBureauList() != null &&
                bureauInfo.getBureauIncludedIndicator().equals(Constants.STR_Y)) {
            casesList =  bureauInfo.getBureauList().stream()
                    .filter(bureau ->
                            Constants.STR_Y.equals(bureau.getInclusionInd())
                    ).map(bureauFilter ->
                            new CxmDataCase(
                                    bureauFilter.getBureauDescription(),
                                    bureauFilter.getOriginDescription(),
                                    bureauFilter.getLastUnpaidDate(),
                                    bureauFilter.getTotalUnpaidPaymentAmount(),
                                    bureauFilter.getNumberOfUnpaidOperations()
                            )
                    ).collect(Collectors.toList());
        }
        // Return the result
        return casesList;
    }

    /**
     * Gets headers.
     * Method to get the headers necessary to call get token service
     *
     * @param token   the token
     * @return the headers
     */
    private Map<String, String> getHeaders(String token) {
        Map<String, String> darwinHeaders = new HashMap<>();
        darwinHeaders.put("Authorization", "Bearer " + token);
        // Puede que falte Content-Type
        return darwinHeaders;
    }
}
