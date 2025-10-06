package com.santander.batch.negativefilesrequest.listener;

import com.santander.batch.negativefilesrequest.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * The type Job completion notification listener.
 */
@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    /**
     * The constant PAN_SWAP_PROCESO_DE_RENOVACION_FINALIZADO.
     */
    public static final String FFNN_PROCESO_DE_NOTIFICACION_FINALIZADO = "FFNN proceso de Notificación Finalizado ";

    /**
     * The constant PAN_SWAP_PROCESO_DE_RENOVACION_FALLIDO.
     */
    public static final String FFNN_PROCESO_DE_NOTIFICACION_FALLIDO = "FFNN proceso de Notificación Fallido ";

    /**
     * The Input Path.
     */
    @Value("${app.files.input.path}")
    public String inputPath;
    /**
     * The Input file name.
     */
    @Value("${app.files.input.file-name}")
    public String inputFileName;

    /**
     * The Output path.
     */
    @Value("${app.files.output.path}")
    public String outputPath;

    /**
     * The file name Ko.
     */
    @Value("${app.files.output.file-ko-name}")
    public String fileNameKo;

    /**
     * The File Name Ok.
     */
    @Value("${app.files.output.file-ok-name}")
    public String fileNameOk;

    /**
     * The File with Ok proccessed
     */
    @Value("${app.files.output.file-processed-name}")
    public String fileNameProcessed;

    /**
     * Method before job method
     * @param jobExecution  the job execution
     */
    @Override
    public void beforeJob(JobExecution jobExecution) {
        log.info("!JOB START! Checking if input file exists and have data");
        //Get the input file
        File inputFile = new File(inputPath +inputFileName);

        //Check if the input file is empty
        if(inputFile.length() == Constants.INT_0 ||
                !Paths.get(outputPath + fileNameProcessed).toFile().exists()){
            log.info("!JOB STOP!Empty file or old file not processed.");
            jobExecution.stop();
        }else{
            deleteOldFiles(jobExecution);
        }

    }

    /**
     * Delete old files.
     *
     * @param jobExecution the job execution
     */
    private void deleteOldFiles(JobExecution jobExecution) {
        //If input file exists, delete the old output files
        try {
            //If exists, will delete
            if (Paths.get(outputPath + fileNameKo).toFile().exists()) {
                Files.delete(Paths.get(outputPath + fileNameKo));
            }
            //If exists, will delete
            if (Paths.get(outputPath + fileNameOk).toFile().exists()) {
                Files.delete(Paths.get(outputPath + fileNameOk));
            }

            //If exists, will delete
            if (Paths.get(outputPath + fileNameOk).toFile().exists()) {
                Files.delete(Paths.get(outputPath + fileNameProcessed));
            }
            log.info("Old files removes");
        } catch (IOException e) {
            //In case of error, stop execution
            log.info("!JOB STOP!Error deleting old files. ", e);
            jobExecution.stop();
        }
    }

    /**
     * Method after job method
     * @param jobExecution  the job execution
     */
    @Override
    public void afterJob(JobExecution jobExecution) {

        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            //If exists, will delete
            try {
                Files.copy(Paths.get(inputPath + inputFileName),Paths.get(outputPath + fileNameProcessed),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                //In case of error, stop execution
                log.info("!JOB STOP!Error copying processed file. ", e);
                jobExecution.stop();
            }
        }
        jobExecution.stop();
    }
}

