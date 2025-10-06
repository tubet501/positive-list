package com.santander.batch.negativefilesrequest.config;

import static org.junit.jupiter.api.Assertions.*;

import com.santander.batch.negativefilesrequest.config.BatchConfiguration;
import com.santander.batch.negativefilesrequest.jobs.FFNNProcessor;
import com.santander.batch.negativefilesrequest.jobs.FFNNReader;
import com.santander.batch.negativefilesrequest.jobs.FFNNWriter;
import com.santander.batch.negativefilesrequest.listener.JobCompletionNotificationListener;
import io.netty.channel.ConnectTimeoutException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowJobBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.builder.JobFlowBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.step.builder.FaultTolerantStepBuilder;
import org.springframework.batch.core.step.builder.SimpleStepBuilder;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class BatchConfigurationTest {

    @InjectMocks
    private BatchConfiguration batchConfiguration;

    @Mock
    private JobBuilderFactory jobBuilderFactory;

    @Mock
    private JobBuilder jobBuilder;

    @Mock
    private JobFlowBuilder jobFlowBuilder;

    @Mock
    private FlowJobBuilder flowJobBuilder;

    @Mock
    private Job job;

    //Proyecto
    @Mock
    private JobCompletionNotificationListener listener;

    @Mock
    private Step step;

    @Mock
    private StepBuilderFactory stepBuilderFactory;

    @Mock
    private StepBuilder stepBuilder;

    @Mock
    private SimpleStepBuilder simpleStepBuilder;

    @Mock
    private FFNNReader ffnnReader;

    @Mock
    private FlatFileItemReader flatFileItemReader;

    @Mock
    private FFNNProcessor ffnnProcessor;

    @Mock
    private FFNNWriter ffnnWriter;

    @Mock
    private TaskletStep task;

    @Mock
    private FaultTolerantStepBuilder faultTolerant;

    @Test
    void ffnnJobTest() {

        when(jobBuilderFactory.get(Mockito.anyString())).thenReturn(jobBuilder);
        when(jobBuilder.incrementer(Mockito.any(RunIdIncrementer.class))).thenReturn(jobBuilder);
        when(jobBuilder.listener(Mockito.any(JobExecutionListener.class))).thenReturn(jobBuilder);
        when(jobBuilder.flow(Mockito.any(Step.class))).thenReturn(jobFlowBuilder);
        when(jobFlowBuilder.end()).thenReturn(flowJobBuilder);
        when(flowJobBuilder.build()).thenReturn(job);
        Assertions.assertNotNull(batchConfiguration.ffnnJob(listener, step));
    }

    @Test
    void stepTest() {

        when(stepBuilderFactory.get(Mockito.anyString())).thenReturn(stepBuilder);
        when(stepBuilder.chunk(1)).thenReturn(simpleStepBuilder);
        when(ffnnReader.read()).thenReturn(flatFileItemReader);
        when(simpleStepBuilder.reader(flatFileItemReader)).thenReturn(simpleStepBuilder);
        when(simpleStepBuilder.processor(ffnnProcessor)).thenReturn(simpleStepBuilder);
        when(simpleStepBuilder.writer(ffnnWriter)).thenReturn(simpleStepBuilder);
        when(simpleStepBuilder.build()).thenReturn(task);
        when(simpleStepBuilder.faultTolerant()).thenReturn(faultTolerant);
        Assertions.assertNotNull(batchConfiguration.step(ffnnProcessor, ffnnReader, ffnnWriter));
    }


}