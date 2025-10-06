package com.santander.batch.negativefilesrequest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Cxm
 * info response.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CxmRes {

    /**
     * The uuid.
     */
    private String uuid;

    /**
     * The channel.
     */
    private String channel;

    /**
     * The message.
     */
    private String message;
}
