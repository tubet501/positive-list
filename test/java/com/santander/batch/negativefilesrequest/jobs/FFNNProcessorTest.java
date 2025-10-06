package com.santander.batch.negativefilesrequest.jobs;

import com.santander.batch.negativefilesrequest.model.BureauInfo;
import com.santander.batch.negativefilesrequest.model.BureauResponseData;
import com.santander.batch.negativefilesrequest.model.CxmInfo;
import com.santander.batch.negativefilesrequest.model.DecisionRisk;
import com.santander.batch.negativefilesrequest.model.dto.CxmRes;
import com.santander.batch.negativefilesrequest.service.CxmService;
import com.santander.batch.negativefilesrequest.service.DecisionRiskService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class FFNNProcessorTest {

    @InjectMocks
    private FFNNProcessor ffnnProcessor;

    @Mock
    private DecisionRiskService decisionRiskService;

    @Mock
    private CxmService cxmService;

    private DecisionRisk decisionRisk;
    private BureauInfo bureauInfo;
    private CxmInfo cxmInfo;
    private BureauResponseData bureauResponseData;

    /**
     * Response of Bureau equal to null
     */
    /*@Test
    public void processNullBureauResponseData() {

        decisionRisk = new DecisionRisk("0049", "0075", "0077949017",
                "00", "001", "46631372", "2022-10-18");
        //bureauResponseData = new BureauResponseData()
        bureauInfo = new BureauInfo("N", null,decisionRisk);
        when(decisionRiskService.getBureausInformation(decisionRisk)).thenReturn(bureauInfo);
        Assertions.assertEquals(bureauInfo, decisionRiskService.getBureausInformation(decisionRisk));
        //cxmInfo = new CxmInfo("N", "128032428", "2022-10-18", null);
    }*/

    /**
     * Client without MO,IJ
     */
    /*@Test
    public void processEmptyBureauResponseData(){

        //TODO buscar un cliente sin MO,IJ
        decisionRisk = new DecisionRisk("0049", "0075", "0077949017",
                "00", "001", "128032428", "2022-10-18");
        //bureauResponseData = new BureauResponseData()
        bureauInfo = new BureauInfo("N", null, decisionRisk);
        when(decisionRiskService.getBureausInformation(decisionRisk)).thenReturn(bureauInfo);
        Assertions.assertEquals(bureauInfo, decisionRiskService.getBureausInformation(decisionRisk));
    }*/

    /**
     * Client with MO
     */
    /*@Test
    public void processFFNNBureauResponseData(){

        //TODO buscar un cliente con MO,IJ
        decisionRisk = new DecisionRisk("0049", "0075", "0077949017",
                "00", "001", "46631372", "2022-10-18");

        bureauResponseData = new BureauResponseData("MO", "Mosoridad", "Y", 2,
                new BigDecimal(1250.55), LocalDate.now(), "AE", "Experian");
        List<BureauResponseData> bureauResponseDataList = new ArrayList<>();
        bureauResponseDataList.add(bureauResponseData);

        bureauInfo = new BureauInfo("Y", bureauResponseDataList, decisionRisk);
        when(decisionRiskService.getBureausInformation(decisionRisk)).thenReturn(bureauInfo);
        Assertions.assertEquals(bureauInfo, decisionRiskService.getBureausInformation(decisionRisk));
    }*/

    /**
     *
     */
    @Test
    public void process() {
        decisionRisk = new DecisionRisk("0049", "0075", "0077949017",
                "00", "001", "46631372", "2022-10-18");
        bureauInfo = new BureauInfo("N", null, decisionRisk);
        cxmInfo = new CxmInfo("N", "46631372", "2022-10-18",
                new CxmRes("uuid","EMAIl","Prueba"));
        when(decisionRiskService.getBureausInformation(decisionRisk)).thenReturn(bureauInfo);
        when(cxmService.sendCxmData(bureauInfo)).thenReturn(cxmInfo);
        Assertions.assertEquals(cxmInfo, ffnnProcessor.process(decisionRisk));
    }
}
