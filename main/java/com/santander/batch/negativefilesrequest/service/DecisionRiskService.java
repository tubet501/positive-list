package com.santander.batch.negativefilesrequest.service;

import com.santander.batch.negativefilesrequest.model.BureauInfo;
import com.santander.batch.negativefilesrequest.model.DecisionRisk;
import org.springframework.stereotype.Service;
@Service
public interface DecisionRiskService {

    /**
     * Gets bureau data.
     *
     * @param decisionRisk the input card
     * @return the card data
     */
    BureauInfo getBureausInformation(DecisionRisk decisionRisk);

}
