package com.santander.batch.negativefilesrequest.listener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.JobExecution;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
public class JobCompletionNotificationListenerTest {

    @InjectMocks
    private JobCompletionNotificationListener jobCompletionNotificationListener;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void beforeJobTest() {
        //Create a JobExecution (You have 6 overloaded methods choose the one you like)
        JobExecution jobExecution = new JobExecution(Long.valueOf(1));
        jobCompletionNotificationListener.beforeJob(jobExecution);
        Assertions.assertNotNull(jobExecution);
    }

    @Test
    void afterJobTest() {
        //Create a JobExecution (You have 6 overloaded methods choose the one you like)
        JobExecution jobExecution = new JobExecution(Long.valueOf(1));
        jobCompletionNotificationListener.afterJob(jobExecution);
        Assertions.assertNotNull(jobExecution);
    }
}
