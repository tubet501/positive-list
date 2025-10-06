package com.santander.batch.negativefilesrequest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Bureau data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiskDataInformationRes {

    /**
     * The Participants information.
     *
     * the class with participants response data
     */
    private ParticipantsInformationData participantsInformation;

}
