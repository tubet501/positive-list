package com.santander.batch.negativefilesrequest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Bureaus data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BureausData {

    /**
     * The Bureau code.
     */
    private String bureauCode;

    /**
     * The Bureau data.
     */
    private BureauData bureauData;

    /**
     * The Error.
     */
    private ErrorData error;
}

