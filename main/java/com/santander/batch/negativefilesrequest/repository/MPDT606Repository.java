package com.santander.batch.negativefilesrequest.repository;

import com.santander.batch.negativefilesrequest.persistence.MPDT606CustomEntity;
import com.santander.batch.negativefilesrequest.persistence.MPDT606CustomEntityPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

/**
 * The interface
 * Mpdt 606 repository.
 */
@Repository
public interface MPDT606Repository extends JpaRepository<MPDT606CustomEntity, MPDT606CustomEntityPK> {

    /**
     * Find by cdgenti valores mpdt 606 custom entity
     * to return a list of MPDT606CustomEntity.
     *
     * @param cdgenti the cdgenti
     * @param valores the valores
     * @return the mpdt 606 custom entity
     */
    @Query("FROM MPDT606CustomEntity b WHERE"
            + " b.mpdt606CustomEntityPK.cdgenti IN (:cdgentiSet) AND"
            + " b.mpdt606CustomEntityPK.valores IN (:valoresSet) AND"
            + " b.mpdt606CustomEntityPK.campoopc = 'INDEBCRE'")
    List<MPDT606CustomEntity> findAllByCdgentiValores(@Param("cdgentiSet") Set<String> cdgenti,
                                                      @Param("valoresSet") Set<String> valores);

    /**
     * Method to look up the description
     * of the bureau code and
     * the origin indicator.
     *
     * @param cdgenti  the entity code
     * @param valores  the values
     * @param campoopc the option field
     * @return the mpdt 606 custom entity
     */
    @Query("FROM MPDT606CustomEntity b WHERE"
            + " b.mpdt606CustomEntityPK.cdgenti = :cdgenti AND"
            + " b.mpdt606CustomEntityPK.valores = :valores AND"
            + " b.mpdt606CustomEntityPK.campoopc = :campoopc")
    MPDT606CustomEntity findDescription(@Param("cdgenti") String cdgenti,
                                        @Param("valores") String valores,
                                        @Param("campoopc") String campoopc);
}


