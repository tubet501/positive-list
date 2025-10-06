package com.santander.batch.negativefilesrequest.jobs;

import com.santander.batch.negativefilesrequest.model.DecisionRisk;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;

/**
 * The type Card reader.
 */
@StepScope
@Slf4j
public class FFNNReader {

    /**
     * The Input Path.
     */
    @Value("${app.files.input.path}")
    public String inputPath;

    /**
     * The File name.
     */
    @Value("${app.files.input.file-name}")
    public String fileName;

    private static String[] names = {"idEmpr", "idCent", "codCaso", "version", "idSolcas","cdPersona", "dataDatePart" };

    /**
     * Read flat file item reader.
     *
     * @return the flat file item reader
     */
    public FlatFileItemReader<DecisionRisk> read() {
        log.info("Start reader");
        //Generate the field set mapper to map input data
        //to input process object
        BeanWrapperFieldSetMapper<DecisionRisk> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        //The maximum difference that can be tolerated in spelling between input key names and bean property names.
        fieldSetMapper.setDistanceLimit(0);
        fieldSetMapper.setTargetType(DecisionRisk.class);
        //Generate the range to read a line of file
        //Generate the names to
        //String[] names = new String[]{"idEmpr", "idCent", "codCaso", "version", "idSolcas","cdPersona", "dataDatePart" };
        //Return the flat file item reader
        return new FlatFileItemReaderBuilder<DecisionRisk>()
                .name("fileItemReader")
                .resource(new FileSystemResource(inputPath+fileName))
                .targetType(DecisionRisk.class)
                .delimited().delimiter(",")
                .names(names)
                .build();
    }
}

