package com.santander.batch.negativefilesrequest.exception;

/**
 * The type FFNN exception.
 */
public class FFNNException extends Exception{

    /** The Constant serialVersionUID. */
    private static final long serialVersionUID = 1L;

    /**
     * Instantiates a new FFNNException
     */
    public FFNNException() {
        super();
    }

    /**
     * Instantiates a new FFNNException
     *
     * @param message the message
     */
    public FFNNException(String message) {
        super(message);
    }

    /**
     * Instantiates a new PanSwapException
     *
     * @param ffnn the FFNN
     */
    public FFNNException(Throwable ffnn) {
        super(ffnn);
    }

    /**
     * Instantiates a new PanSwapException exception.
     *
     * @param mensaje        the mensaje
     * @param ffnn the FFNN
     */
    public FFNNException(String mensaje, Throwable ffnn) {
        super(mensaje, ffnn);
    }


}

