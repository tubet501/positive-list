package com.santander.batch.negativefilesrequest.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The type Bureau
 * info response processed.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BureauInfo {

    /**
     * The Bureau
     * included indicator.
     */
    @Schema(description = "Bureau Include Indicator", example = "Y", allowableValues = {"Y", "N"})
    private String bureauIncludedIndicator;

    /**
     * The Bureau list.
     */
    private List<BureauResponseData> bureauList;

    /**
     * The register with code person, time and case code.
     */
    private DecisionRisk decisionRisk;

}

