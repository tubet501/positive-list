package com.santander.batch.negativefilesrequest.service;

import com.santander.darwin.core.exceptions.InternalServerErrorDarwinException;
import com.santander.darwin.core.exceptions.NotFoundDarwinException;
import com.santander.batch.negativefilesrequest.model.BureauInfo;
import com.santander.batch.negativefilesrequest.model.DecisionRisk;
import com.santander.batch.negativefilesrequest.model.dto.*;
import com.santander.batch.negativefilesrequest.persistence.MPDT606CustomEntity;
import com.santander.batch.negativefilesrequest.repository.MPDT606Repository;
import com.santander.batch.negativefilesrequest.service.impl.DecisionRiskServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DecisionRiskServiceImplTest {

    @InjectMocks
    private DecisionRiskServiceImpl decisionRiskService;

    @Mock
    private ExternalAPIService externalAPIService;

    @Mock
    private SecurityTokenService securityTokenService;

    @Mock
    private MPDT606Repository mpdt606Repository;

    private MPDT606CustomEntity mpdt606CustomEntity;

    private RiskDataInformationRes riskDataInformationRes;

    private DecisionRisk decisionRisk;

    @BeforeEach
    public void setup() {

        BureauInfo bureauInfo = new BureauInfo();
        bureauInfo.setBureauList(null);
        bureauInfo.setBureauIncludedIndicator("N");

        decisionRisk = new DecisionRisk();
        decisionRisk.setIdEmpr("0049");
        decisionRisk.setIdCent("0075");
        decisionRisk.setCodCaso("0077949017");
        decisionRisk.setVersion("00");
        decisionRisk.setIdSolcas("001");
        decisionRisk.setCdPersona("128032428");
        decisionRisk.setDataDatePart("2022-11-22");

        bureauInfo.setDecisionRisk(decisionRisk);

        TotalSummaryData totalSummary = new TotalSummaryData();
        totalSummary.setInclusionInd("S");
        totalSummary.setOriginInd("AE");
        totalSummary.setLastUnpaidDate(LocalDate.now());
        totalSummary.setTotalUnpaidPaymentAmount(BigDecimal.TEN);
        totalSummary.setNumberOfUnpaidOperations(1);

        BureauData bureauData = new BureauData();
        bureauData.setTotalSummary(totalSummary);

        BureausData bureausData = new BureausData();
        bureausData.setBureauData(bureauData);
        bureausData.setBureauCode("MO");

        T1ResponseData t1ResponseData = new T1ResponseData();
        List<BureausData> bureausDataList = new ArrayList<BureausData>(1);
        bureausDataList.add(bureausData);
        //t1ResponseData.setBureausData(List.of(bureausData));
        t1ResponseData.setBureausData(bureausDataList);

        ParticipantsInformationData participantsInformation = new ParticipantsInformationData();
        participantsInformation.setT1(t1ResponseData);

        riskDataInformationRes = new RiskDataInformationRes();
        riskDataInformationRes.setParticipantsInformation(participantsInformation);

        mpdt606CustomEntity = new MPDT606CustomEntity();
        mpdt606CustomEntity.setDsvalor("");
    }

    @Test
    void getBureausInformation() {
        when(externalAPIService.doPost(any(), any(), any(), eq(RiskDataInformationRes.class), any(), any()))
                .thenReturn(ResponseEntity.ok(riskDataInformationRes));
        when(securityTokenService.getSecurityToken()).thenReturn("");
        when(mpdt606Repository.findDescription(any(), any(), any())).thenReturn(mpdt606CustomEntity);
        assertNotNull(decisionRiskService.getBureausInformation(decisionRisk));
    }

    @Test
    void getBureausInformation400ExceptionCallHubBureau() {
        assertThrows(NotFoundDarwinException.class, () -> {
            when(externalAPIService.doPost(any(), any(), any(), eq(RiskDataInformationRes.class), any(), any()))
                    .thenThrow(HttpClientErrorException.class);
            decisionRiskService.getBureausInformation(decisionRisk);
        });
    }

    @Test
    void getBureausInformation500ExceptionCallHubBureau() {
        assertThrows(InternalServerErrorDarwinException.class, () -> {
            when(externalAPIService.doPost(any(), any(), any(), eq(RiskDataInformationRes.class), any(), any()))
                    .thenThrow(HttpServerErrorException.class);
            decisionRiskService.getBureausInformation(decisionRisk);
        });
    }

    @Test
    void evaluateRiskDecisionResponseTestOK() {
        when(externalAPIService.doPost(any(), any(), any(), eq(RiskDataInformationRes.class), any(), any()))
                .thenReturn(ResponseEntity.ok(riskDataInformationRes));
        when(mpdt606Repository.findDescription(any(), any(), any())).thenReturn(mpdt606CustomEntity);
        assertNotNull(decisionRiskService.getBureausInformation(decisionRisk));
    }

    @Test
    void evaluateRiskDecisionResponseTestBureausEmpty() {
        riskDataInformationRes.getParticipantsInformation().getT1().setBureausData(null);
        when(externalAPIService.doPost(any(), any(), any(), eq(RiskDataInformationRes.class), any(), any()))
                .thenReturn(ResponseEntity.ok(riskDataInformationRes));
        assertNotNull(decisionRiskService.getBureausInformation(decisionRisk));
    }

    @Test
    void getBureausInformationTestRiskDataEmpty() {
        when(externalAPIService.doPost(any(), any(), any(), eq(RiskDataInformationRes.class), any(), any()))
                .thenReturn(ResponseEntity.ok(null));
        assertNotNull(decisionRiskService.getBureausInformation(decisionRisk));
    }

}
