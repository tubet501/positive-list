package com.santander.batch.negativefilesrequest.jobs;


import com.santander.batch.negativefilesrequest.model.CxmInfo;
import com.santander.batch.negativefilesrequest.utils.Constants;
import com.santander.batch.negativefilesrequest.utils.Utils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The type FFNN writer.
 */
@Slf4j
public class FFNNWriter implements ItemWriter<CxmInfo> {

    /**
     * The Output path.
     */
    @Value("${app.files.output.path}")
    public String outputPath;

    /**
     * The file name of ok cards.
     */
    @Value("${app.files.output.file-ok-name}")
    public String fileNameOk;

    /**
     * The file name of ko cards.
     */
    @Value("${app.files.output.file-ko-name}")
    public String fileNameKo;

    @Override
    public void write(List<? extends CxmInfo> list) {

        //Get the ok file path
        String pathOk = Utils.formatPath(outputPath + fileNameOk);
        //Get the ko file path
        String pathKo = Utils.formatPath(outputPath + fileNameKo);
        //Get the ok processed ffnn
        List<? extends CxmInfo> listOk = list.stream()
                .filter(item -> isOk(item))
                .collect(Collectors.toList());
        //Get the ko processed ffnn
        List<? extends CxmInfo> listKo = list.stream()
                .filter(item -> !isOk(item))
                .collect(Collectors.toList());

        //If ok list is not empty, write in ok output file
        if (!listOk.isEmpty()) {
            fillOkOutputFile(listOk, pathOk);
            log.info("FFNN F-{} processed. File execution OK write correctly", listOk.get(0).getCdPersona());
        }
        //If ko list is not empty, write in ko output file
        if (!listKo.isEmpty()) {
            fillKoOutputFile(listKo, pathKo);
            log.info("FFNN F-{} processed. File execution KO write correctly", listKo.get(0).getCdPersona());
        }
    }

    /**
     * Method to know if a FNN is processed ok or ko
     * @param cxmInfo    the processed card
     * @return              the boolean result
     */
    private boolean isOk(CxmInfo cxmInfo){
        return cxmInfo.getCxmResultIndicator().equals(Constants.STR_Y);
    }

    /**
     * Method to write a card in ok output file
     * @param list  the output list
     * @param path  the file path
     */
    private void fillOkOutputFile(List<? extends CxmInfo> list, String path) {
        //Method to write the ok output file
        for (CxmInfo cxmInfo : list) {
            //for each item, write a line
            Utils.writeLineFile(cxmInfo.toLine(), path);
        }
    }

    /**
     * Method to write a card in ko output file
     * @param list  the output list
     * @param path  the file path
     */
    private void fillKoOutputFile(List<? extends CxmInfo> list, String path) {
        //Method to write the ko output file
        for (CxmInfo cxmInfo : list) {
            //for each item, write a line
            Utils.writeLineFile(cxmInfo.toErrorLine(), path);
        }
    }
}

