package com.santander.batch.negativefilesrequest.model;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class DecisionRiskTest {

    @InjectMocks
    private DecisionRisk decisionRisk;

    @Test
    void getNumCase() {

        decisionRisk = new DecisionRisk("0049","4856","0077949017",
                "00","002","128032428","2022-10-18");

        decisionRisk.getNumCase();
        assertNotNull(decisionRisk);
    }
}
