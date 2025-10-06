package com.santander.batch.negativefilesrequest.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * This class
 * will detach the entity
 * that you select
 * @param <T> the param that you want
 */
@Repository
public class DetachRepository<T> {
    /**
     * The entity
     */
    @PersistenceContext
    @Autowired
    private EntityManager entityManager;

    /**
     * detach method
     *
     * @param entity entity
     * @param <E> Param E
     */
    public <E extends T> void detach(E entity) {
        entityManager.detach(entity);
    }

}

