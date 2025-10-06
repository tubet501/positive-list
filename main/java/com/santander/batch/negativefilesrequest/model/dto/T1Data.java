package com.santander.batch.negativefilesrequest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type T 1 data.
 *
 * class with person code and type
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class T1Data {

    /**
     * The Person type.
     *
     * F physical
     * J Juridical
     */
    private String personType;
    /**
     * The Person code.
     *
     */
    private int personCode;
}

