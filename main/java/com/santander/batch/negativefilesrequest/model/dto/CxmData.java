package com.santander.batch.negativefilesrequest.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The data for Cxm email.
 * This class get information from
 * Cxm API.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CxmData {

    /**
     * The list with ffnn information.
     */
    @JsonProperty(value = "list_1")
    private List<CxmDataCase> cxmDataList;
}
