package com.santander.batch.negativefilesrequest.jobs;

import com.santander.batch.negativefilesrequest.model.BureauInfo;
import com.santander.batch.negativefilesrequest.model.CxmInfo;
import com.santander.batch.negativefilesrequest.model.DecisionRisk;
import com.santander.batch.negativefilesrequest.service.CxmService;
import com.santander.batch.negativefilesrequest.service.DecisionRiskService;
import org.springframework.batch.item.ItemProcessor;

/**
 * The type FFNN processor.
 */
public class FFNNProcessor implements ItemProcessor<DecisionRisk, CxmInfo> {
    /**
     * The Decision Risk service
     */
    private DecisionRiskService decisionRiskService;

    /**
     * The Cxm service
     */
    private CxmService cxmService;

    /**
     * Instantiates a new FFNN processor.
     *
     * @param decisionRiskService the Bureau service
     * @param cxmService the Cxm service
     */
    public FFNNProcessor(DecisionRiskService decisionRiskService, CxmService cxmService) {
        this.decisionRiskService = decisionRiskService;
        this.cxmService = cxmService;
    }

    /**
     * Method to process a input file
     * This method get a input file an call to bureau service
     * @param inputFile     the input card
     * @return              the ffnn with ok or ko info
     */
    @Override
    public CxmInfo process(final DecisionRisk inputFile) {
        //Call pan swap service method
        BureauInfo bureauInfo =
                decisionRiskService.getBureausInformation(inputFile);
        CxmInfo cxmInfo =
                cxmService.sendCxmData(bureauInfo);

        return cxmInfo;
    }
}

