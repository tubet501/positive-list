package com.santander.batch.negativefilesrequest.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The FFNN information for Cxm email.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CxmDataCase {

    /**
     * Description of FFNN
     */
    private String description;

    /**
     * Origin of FFNN
     */
    private String reason;

    /**
     * Last unpaid date
     */
    @JsonProperty(value = "date_1")
    private LocalDate unpaidDate;

    /**
     * Total unpaid Payment Amount
     */
    @JsonProperty(value = "importe_total")
    private BigDecimal totalImport;

    /**
     * Number of unpaid operations
     */
    @JsonProperty(value = "number_operations")
    private int numberOperations; //CONSULTAR A CRM

}

