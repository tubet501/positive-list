package com.santander.batch.negativefilesrequest.model;

import com.santander.batch.negativefilesrequest.GetterSetterVerifier;
import com.santander.batch.negativefilesrequest.model.dto.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class VerificatorBeansTest {

    @Test
    public void verificaBeans() {
        GetterSetterVerifier<BureauData> bureauDataGetterSetterVerifier =
                new GetterSetterVerifier<>(BureauData.class);
        GetterSetterVerifier<BureausData> bureausDataGetterSetterVerifier =
                new GetterSetterVerifier<>(BureausData.class);
        GetterSetterVerifier<CxmData> cxmDataGetterSetterVerifier =
                new GetterSetterVerifier<>(CxmData.class);
        GetterSetterVerifier<CxmDataCase> cxmDataCaseGetterSetterVerifier =
                new GetterSetterVerifier<>(CxmDataCase.class);
        GetterSetterVerifier<CxmReq> cxmReqGetterSetterVerifier =
                new GetterSetterVerifier<>(CxmReq.class);
        GetterSetterVerifier<CxmRes> cxmResGetterSetterVerifier =
                new GetterSetterVerifier<>(CxmRes.class);
        GetterSetterVerifier<ErrorData> errorDataGetterSetterVerifier =
                new GetterSetterVerifier<>(ErrorData.class);
        GetterSetterVerifier<IdAttributes> idAttributesGetterSetterVerifier =
                new GetterSetterVerifier<>(IdAttributes.class);
        GetterSetterVerifier<MatrixData> matrixDataGetterSetterVerifier =
                new GetterSetterVerifier<>(MatrixData.class);
        GetterSetterVerifier<ParticipantsData> participantsDataGetterSetterVerifier =
                new GetterSetterVerifier<>(ParticipantsData.class);
        GetterSetterVerifier<ParticipantsInformationData> participantsInformationDataGetterSetterVerifier =
                new GetterSetterVerifier<>(ParticipantsInformationData.class);
        GetterSetterVerifier<RiskDataInformationReq> riskDataInformationReqGetterSetterVerifier =
                new GetterSetterVerifier<>(RiskDataInformationReq.class);
        GetterSetterVerifier<RiskDataInformationRes> riskDataInformationResGetterSetterVerifier =
                new GetterSetterVerifier<>(RiskDataInformationRes.class);
        GetterSetterVerifier<T1Data> t1DataGetterSetterVerifier =
                new GetterSetterVerifier<>(T1Data.class);
        GetterSetterVerifier<T1ResponseData> t1ResponseDataGetterSetterVerifier =
                new GetterSetterVerifier<>(T1ResponseData.class);
        GetterSetterVerifier<TokenCredentialsInput> tokenCredentialsInputGetterSetterVerifier =
                new GetterSetterVerifier<>(TokenCredentialsInput.class);
        GetterSetterVerifier<TokenCredentialsOutput> tokenCredentialsOutputGetterSetterVerifier =
                new GetterSetterVerifier<>(TokenCredentialsOutput.class);
        GetterSetterVerifier<TotalSummaryData> totalSummaryDataGetterSetterVerifier =
                new GetterSetterVerifier<>(TotalSummaryData.class);

        GetterSetterVerifier<BureauInfo> bureauInfoGetterSetterVerifier =
                new GetterSetterVerifier<>(BureauInfo.class);
        GetterSetterVerifier<BureauResponseData> bureauResponseDataGetterSetterVerifier =
                new GetterSetterVerifier<>(BureauResponseData.class);
        GetterSetterVerifier<CxmInfo> cxmInfoGetterSetterVerifier =
                new GetterSetterVerifier<>(CxmInfo.class);
        GetterSetterVerifier<DecisionRisk> decisionRiskGetterSetterVerifier =
                new GetterSetterVerifier<>(DecisionRisk.class);

        bureauDataGetterSetterVerifier.verify();
        bureausDataGetterSetterVerifier.verify();
        cxmDataGetterSetterVerifier.verify();
        cxmDataCaseGetterSetterVerifier.verify();
        cxmReqGetterSetterVerifier.verify();
        cxmResGetterSetterVerifier.verify();
        errorDataGetterSetterVerifier.verify();
        idAttributesGetterSetterVerifier.verify();
        matrixDataGetterSetterVerifier.verify();
        participantsDataGetterSetterVerifier.verify();
        participantsInformationDataGetterSetterVerifier.verify();
        riskDataInformationReqGetterSetterVerifier.verify();
        riskDataInformationResGetterSetterVerifier.verify();
        t1DataGetterSetterVerifier.verify();
        t1ResponseDataGetterSetterVerifier.verify();
        tokenCredentialsInputGetterSetterVerifier.verify();
        tokenCredentialsOutputGetterSetterVerifier.verify();
        totalSummaryDataGetterSetterVerifier.verify();

        bureauInfoGetterSetterVerifier.verify();
        bureauResponseDataGetterSetterVerifier.verify();
        cxmInfoGetterSetterVerifier.verify();
        decisionRiskGetterSetterVerifier.verify();

    }
}
