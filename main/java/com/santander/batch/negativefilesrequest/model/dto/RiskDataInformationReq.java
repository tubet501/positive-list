package com.santander.batch.negativefilesrequest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The type Risk data information request.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RiskDataInformationReq {

    /**
     * The Matrix.
     */
    private MatrixData matrix;

    /**
     * The Participants.
     */
    private ParticipantsData participants;

    /**
     * The Bureau codes.
     */
    private List<String> bureauCodes;

}
