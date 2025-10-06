package com.santander.batch.negativefilesrequest.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Participants data.
 *
 * class with participants information
 *
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantsData{

    /**
     * The T 1.
     *
     * The participants info
     */
    @JsonProperty(value = "T1")
    private T1Data t1;
}