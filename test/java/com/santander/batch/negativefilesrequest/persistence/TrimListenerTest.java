package com.santander.batch.negativefilesrequest.persistence;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TrimListenerTest {

    @Test
    void trimTest() throws Exception {

        MPDT606CustomEntity mpdt606CustomEntity = new MPDT606CustomEntity();
        MPDT606CustomEntityPK mpdt606CustomEntityPK = new MPDT606CustomEntityPK();
        mpdt606CustomEntityPK.setCdgenti("0049");
        mpdt606CustomEntityPK.setValores("02");
        mpdt606CustomEntityPK.setCampoopc("INDEBCRE");
        mpdt606CustomEntity.setMpdt606CustomEntityPK(mpdt606CustomEntityPK);
        mpdt606CustomEntity.setDsvalor("CREDITO");

        TrimListener trimListener = new TrimListener();
        //trimListener.repairAfterLoad(mpdt606CustomEntity);
        assertEquals(mpdt606CustomEntityPK.getCdgenti(), "0049");
        assertEquals(mpdt606CustomEntityPK.getValores(), "02");
        assertEquals(mpdt606CustomEntityPK.getCampoopc(), "INDEBCRE");
        assertEquals(mpdt606CustomEntity.getDsvalor(), "CREDITO");
    }
}
