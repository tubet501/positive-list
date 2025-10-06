package com.santander.batch.negativefilesrequest.utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.File;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
public class UtilsTest {

    @InjectMocks
    private Utils utils;

    @Test
    void removeLastLineFromFileTest() {

        File file = mock(File.class);
        when(file.isFile()).thenReturn(true);
        utils.removeLastLineFromFile("./src/test/resources/readerTest");
        assertNotNull(utils);
    }
}
