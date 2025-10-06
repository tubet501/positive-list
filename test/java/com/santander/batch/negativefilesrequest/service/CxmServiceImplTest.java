package com.santander.batch.negativefilesrequest.service;

import com.santander.darwin.core.exceptions.InternalServerErrorDarwinException;
import com.santander.darwin.core.exceptions.NotFoundDarwinException;
import com.santander.batch.negativefilesrequest.model.BureauInfo;
import com.santander.batch.negativefilesrequest.model.BureauResponseData;
import com.santander.batch.negativefilesrequest.model.DecisionRisk;
import com.santander.batch.negativefilesrequest.model.dto.CxmRes;
import com.santander.batch.negativefilesrequest.model.dto.RiskDataInformationRes;
import com.santander.batch.negativefilesrequest.service.impl.CxmServiceImpl;
import com.santander.batch.negativefilesrequest.service.impl.DecisionRiskServiceImpl;
import org.junit.jupiter.api.BeforeAll;
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
public class CxmServiceImplTest {

    @InjectMocks
    private CxmServiceImpl cxmService;

    @Mock
    private ExternalAPIService externalAPIService;

    @Mock
    private SecurityTokenService securityTokenService;

    private BureauInfo bureauInfo;
    private CxmRes cxmRes;

    @BeforeEach
    public void setup() {

        bureauInfo = new BureauInfo();
        //bureauInfo.setBureauList(null);
        bureauInfo.setBureauIncludedIndicator("Y");

        DecisionRisk decisionRisk = new DecisionRisk();
        decisionRisk.setIdEmpr("0049");
        decisionRisk.setIdCent("0075");
        decisionRisk.setCodCaso("0077949017");
        decisionRisk.setVersion("00");
        decisionRisk.setIdSolcas("001");
        decisionRisk.setCdPersona("128032428");
        decisionRisk.setDataDatePart("2022-11-22");
        bureauInfo.setDecisionRisk(decisionRisk);

        BureauResponseData bureauResponseData = new BureauResponseData();
        bureauResponseData.setBureauCode("MO");
        bureauResponseData.setBureauDescription("Morosidad");
        bureauResponseData.setInclusionInd("Y");
        bureauResponseData.setNumberOfUnpaidOperations(2);
        bureauResponseData.setTotalUnpaidPaymentAmount(new BigDecimal(1000.0));
        bureauResponseData.setLastUnpaidDate(LocalDate.of(2022, 11, 22));
        bureauResponseData.setOriginInd("AE");
        bureauResponseData.setOriginDescription("Experian");
        List<BureauResponseData> bureauResponseDataList = new ArrayList<>(1);
        bureauResponseDataList.add(bureauResponseData);
        bureauInfo.setBureauList(bureauResponseDataList);

        cxmRes = new CxmRes();
        cxmRes.setUuid("UUID");
        cxmRes.setChannel("EMAIL");
        cxmRes.setMessage("Prueba");

    }

    @Test
    void sendCxmData() {
        when(externalAPIService.doPost(any(), any(), any(), eq(CxmRes.class), any(), any()))
                .thenReturn(ResponseEntity.ok(cxmRes));
        when(securityTokenService.getSecurityToken()).thenReturn("");
        assertNotNull(cxmService.sendCxmData(bureauInfo));
    }

    @Test
    void sendCxmData400ExceptionCallHubBureau() {
        assertThrows(NotFoundDarwinException.class, () -> {
            when(externalAPIService.doPost(any(), any(), any(), eq(CxmRes.class), any(), any()))
                    .thenThrow(HttpClientErrorException.class);
            cxmService.sendCxmData(bureauInfo);
        });
    }

    @Test
    void sendCxmData500ExceptionCallHubBureau() {
        assertThrows(InternalServerErrorDarwinException.class, () -> {
            when(externalAPIService.doPost(any(), any(), any(), eq(CxmRes.class), any(), any()))
                    .thenThrow(HttpServerErrorException.class);
            cxmService.sendCxmData(bureauInfo);
        });
    }

    @Test
    void sendCxmDataTestOK() {
        when(externalAPIService.doPost(any(), any(), any(), eq(CxmRes.class), any(), any()))
                .thenReturn(ResponseEntity.ok(cxmRes));
        cxmService.sendCxmData(bureauInfo);
        assertNotNull(cxmService.sendCxmData(bureauInfo));
    }

    @Test
    void createCxmDataCasesListTestBureausEmpty() {
        //bureauInfo.setBureauIncludedIndicator("Y");
        when(externalAPIService.doPost(any(), any(), any(), eq(CxmRes.class), any(), any()))
                .thenReturn(ResponseEntity.ok(null));
        cxmService.sendCxmData(bureauInfo);
        assertNotNull(cxmService.sendCxmData(bureauInfo));
    }

    @Test
    void sendCxmDataTestBureauInfoEmpty() {
        bureauInfo.setBureauIncludedIndicator("N");
        /*when(externalAPIService.doPost(any(), any(), any(), eq(CxmRes.class), any(), any()))
                .thenReturn(ResponseEntity.ok(null));*/
        cxmService.sendCxmData(bureauInfo);
        assertNotNull(cxmService.sendCxmData(bureauInfo));
    }
}
