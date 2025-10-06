package com.santander.batch.negativefilesrequest.config;

import com.santander.batch.negativefilesrequest.jobs.FFNNProcessor;
import com.santander.batch.negativefilesrequest.jobs.FFNNReader;
import com.santander.batch.negativefilesrequest.jobs.FFNNWriter;
import com.santander.batch.negativefilesrequest.listener.JobCompletionNotificationListener;
import com.santander.batch.negativefilesrequest.model.CxmInfo;
import com.santander.batch.negativefilesrequest.model.DecisionRisk;
import com.santander.batch.negativefilesrequest.service.CxmService;
import com.santander.batch.negativefilesrequest.service.DecisionRiskService;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * The type Batch configuration.
 */
@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    /**
     * The Job builder factory.
     */
    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    /**
     * The Step builder factory.
     */
    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    /**
     * Reader file reader.
     *
     * @return the file reader
     */
    @Bean
    public FFNNReader reader() {
        return new FFNNReader();
    }

    /**
     * Processor file processor.
     *
     * @param decisionRiskService the pan swap service
     * @param cxmService the pan swap service
     * @return the card processor
     */
    @Bean
    public FFNNProcessor processor(DecisionRiskService decisionRiskService,
                                   CxmService cxmService) {
        return new FFNNProcessor(decisionRiskService, cxmService);
    }

    /**
     * Writer file writer.
     *
     * @return the file writer
     */
    @Bean
    public FFNNWriter writer(){return new FFNNWriter();}

    /**
     * FFNN job job.
     *
     * @param listener the listener
     * @param step     the step
     * @return the job
     */
    @Bean
    public Job ffnnJob(JobCompletionNotificationListener listener, Step step) {
        return jobBuilderFactory.get("ffnnJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step)
                .end()
                .build();
    }

    /**
     * Step step.
     *
     * @param ffnnProcessor the file processor
     * @param ffnnReader    the file reader
     * @param ffnnWriter    the file writer
     * @return the step
     */
    @Bean
    public Step step(FFNNProcessor ffnnProcessor, FFNNReader ffnnReader, FFNNWriter ffnnWriter) {
        return stepBuilderFactory.get("step")
                .<DecisionRisk, CxmInfo> chunk(1)
                .reader(ffnnReader.read())
                .processor(ffnnProcessor)
                .writer(ffnnWriter)
                //.faultTolerant() TODO por el test
                //Politica de reintentos
                //.retryLimit(5)
                //.retry(ConnectTimeoutException.class)
                //.retry(DeadlockLoserDataAccessException.class)
                .build();
    }
}
