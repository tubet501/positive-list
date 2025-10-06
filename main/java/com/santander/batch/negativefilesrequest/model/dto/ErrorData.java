package com.santander.batch.negativefilesrequest.model.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Error data.
 */
@Getter
@Setter
@NoArgsConstructor
public class ErrorData {

    /**
     * The Error code.
     */
    private String errorCode;
    /**
     * The Error description.
     */
    private String errorDescription;
}
