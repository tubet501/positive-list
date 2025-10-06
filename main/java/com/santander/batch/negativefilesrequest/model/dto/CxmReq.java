package com.santander.batch.negativefilesrequest.model.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The Cxm info request.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CxmReq {

    /**
     * The application id.
     */
    @JsonProperty(value = "application_id")
    private String applicationId;

    /**
     * The Channel
     */
    private String channel;

    /**
     * The language
     */
    private String language ;

    /**
     * The client_id
     */
    @JsonProperty(value = "client_id")
    private String clientId;

    /**
     * The CXM data
     */
    private CxmData data;
}

