package com.santander.batch.negativefilesrequest.repository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(MockitoExtension.class)
class DetachRepositoryTest {

    @InjectMocks
    private DetachRepository detachRepository;
    @Mock
    private EntityManager entityManager;

    @Test
    void detach() {
        detachRepository.detach(new Object());
        assertNotNull(entityManager);
    }
}
