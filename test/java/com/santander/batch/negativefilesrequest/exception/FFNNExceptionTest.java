package com.santander.batch.negativefilesrequest.exception;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;

/**
 * The Class FFNNExceptionTest.
 */
@ExtendWith(SpringExtension.class)
public class FFNNExceptionTest {

    /** The one liquidacion exception. */
    @Mock
    FFNNException ffnnException;

    /**
     * Setup
     */
    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    /**
     * Exception constructor test.
     */
    @Test
    public void exceptionConstructorTest() {
        ffnnException = new FFNNException(new Exception());
        assertNotNull(ffnnException);
    }

    /**
     * Exception string test.
     */
    @Test
    public void exceptionStringTest() {
        ffnnException = new FFNNException("Exception Msg");
        assertNotNull(ffnnException);
    }

    /**
     * Exception constructor vacio test.
     */
    @Test
    public void exceptionConstructorVacioTest() {
        ffnnException = new FFNNException();
        assertNotNull(ffnnException);
    }

    /**
     * Exception string and constructor test.
     */
    @Test
    public void exceptionStringAndConstructorTest() {
        ffnnException = new FFNNException("Exception Msg", new Exception());
        assertNotNull(ffnnException);
    }
}
