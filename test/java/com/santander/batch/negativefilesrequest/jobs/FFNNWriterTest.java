package com.santander.batch.negativefilesrequest.jobs;

import com.santander.batch.negativefilesrequest.model.CxmInfo;
import com.santander.batch.negativefilesrequest.model.dto.CxmRes;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class FFNNWriterTest {

    @InjectMocks
    private FFNNWriter ffnnWriter;

    @Test
    public void write() {

        CxmInfo cxmInfoOK = new CxmInfo("Y", "11111", "2022-11-21",
                new CxmRes("uuid", "EMAIL", "Prueba"));
        CxmInfo cxmInfoKO = new CxmInfo("N", "11111", "2022-11-21",
                null);

        List<CxmInfo> list = new ArrayList<>(2);
        list.add(cxmInfoOK);
        list.add(cxmInfoKO);

        ffnnWriter.write(list);
        assertNotNull(cxmInfoOK);
    }

    @Test
    public void write_EmptyLists() {

        CxmInfo cxmInfoOK = new CxmInfo("Y", "11111", "2022-11-21",
                new CxmRes("uuid", "EMAIL", "Prueba"));
        List<CxmInfo> list = new ArrayList<>(2);
        ffnnWriter.write(list);
        assertNotNull(cxmInfoOK);
    }
}
