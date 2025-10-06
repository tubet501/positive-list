package com.santander.batch.negativefilesrequest.persistence;

import com.santander.batch.negativefilesrequest.GetterSetterVerifier;
import org.junit.jupiter.api.Test;

public class PersistenceTest {

    @Test
    void Test() {

        GetterSetterVerifier<MPDT606CustomEntity> mpdt606CustomEntity =
                new GetterSetterVerifier<>(MPDT606CustomEntity.class);
        mpdt606CustomEntity.verify();

        MPDT606CustomEntity mpdt606CustomEntity1 = new MPDT606CustomEntity(null, "");

        mpdt606CustomEntity1.hashCode();
        mpdt606CustomEntity1.equals("");

        GetterSetterVerifier<MPDT606CustomEntityPK> mpdt606CustomEntityPK =
                new GetterSetterVerifier<>(MPDT606CustomEntityPK.class);
        mpdt606CustomEntityPK.verify();

    }
}
