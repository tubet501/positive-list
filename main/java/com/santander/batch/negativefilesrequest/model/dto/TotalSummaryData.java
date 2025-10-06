package com.santander.batch.negativefilesrequest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * The type Total summary data.
 *
 * Class with inclusion bureau indicator
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TotalSummaryData {

    /**
     * The Inclusion ind.
     */
    private String inclusionInd;

    /**
     * The Number of unpaid operations.
     */
    private int numberOfUnpaidOperations;

    /**
     * The Total unpaid payment amount.
     */
    private BigDecimal totalUnpaidPaymentAmount;

    /**
     * The Last unpaid date.
     */
    private LocalDate lastUnpaidDate;

    /**
     * The Origin ind.
     */
    private String originInd;
}
