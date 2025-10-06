package com.santander.batch.negativefilesrequest.service;

import com.santander.batch.negativefilesrequest.model.BureauInfo;
import com.santander.batch.negativefilesrequest.model.CxmInfo;

public interface CxmService {

    /**
     * Send data to Cxm.
     *
     * @param bureauInfo the bureau input
     * @return the card data
     */
    CxmInfo sendCxmData(BureauInfo bureauInfo);
}
