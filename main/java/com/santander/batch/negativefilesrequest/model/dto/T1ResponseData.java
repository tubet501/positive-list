package com.santander.batch.negativefilesrequest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The type T 1
 * response data.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class T1ResponseData extends T1Data {

    /**
     * The Bureaus data.
     *
     * The bureau info list
     */
    private List<BureausData> bureausData;
}
