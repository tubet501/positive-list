package com.santander.batch.negativefilesrequest.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Participants information data.
 *
 * class with participants information response
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ParticipantsInformationData {

    /**
     * The T 1.
     *
     * the response with participant info
     */
    @JsonProperty(value = "T1")
    public T1ResponseData t1;
}