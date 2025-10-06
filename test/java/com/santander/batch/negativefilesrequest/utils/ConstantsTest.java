package com.santander.batch.negativefilesrequest.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ConstantsTest {

    @Test
    public void constantsTest() {
        assertNotNull(Constants.STR_N);
        assertNotNull(Constants.PHYSIC);
    }
}
