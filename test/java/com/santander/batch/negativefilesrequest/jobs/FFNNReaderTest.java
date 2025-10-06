package com.santander.batch.negativefilesrequest.jobs;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
public class FFNNReaderTest {

    @InjectMocks
    private FFNNReader ffnnReader;

    @Test
    public void read() {
        ffnnReader.read();
        assertNotNull(ffnnReader);
    }
}
