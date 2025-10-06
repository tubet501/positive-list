package com.santander.batch.negativefilesrequest.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Matrix data.
 */
@Getter
@Setter
@NoArgsConstructor
public class MatrixData {

    /**
     * The Spid.
     */
    private int spid;
    /**
     * The Call type.
     */
    private String callType;
    /**
     * The Circuit.
     */
    private String circuit;
    /**
     * The Calling process.
     */
    private String callingProcess;
}

