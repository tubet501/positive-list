package com.santander.batch.negativefilesrequest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The type Bureau data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BureauResponseData {

    /**
     * The Bureau code.
     */
    @Schema(description = "Bureau Code", example = "MO")
    private String bureauCode;

    /**
     * The Bureau description.
     */
    @Schema(description = "Bureau Code Description", example = "Morosidad")
    private String bureauDescription;
    /**
     * The Inclusion ind.
     */
    @Schema(description = "Inclusion Indicator", example = "Y", allowableValues = {"Y", "N"})
    private String inclusionInd;

    /**
     * The Number of unpaid operations.
     */
    @Schema(description = "Number of Unpaid Operations", example = "2")
    private int numberOfUnpaidOperations;

    /**
     * The Total unpaid payment amount.
     */
    @Schema(description = "Total Unpaid Payment Amount", example = "1250.55")
    private BigDecimal totalUnpaidPaymentAmount;

    /**
     * The Last unpaid date.
     */
    @Schema(description = "Last Unpaid Date", example = "2022-05-25")
    private LocalDate lastUnpaidDate;

    /**
     * The Origin ind.
     */
    @Schema(description = "Origin Indicator", example = "AE")
    private String originInd;

    /**
     * The Origin description.
     */
    @Schema(description = "Origin Description", example = "Experian")
    private String originDescription;
}

