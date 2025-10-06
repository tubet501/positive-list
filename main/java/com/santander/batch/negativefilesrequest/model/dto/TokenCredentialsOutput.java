package com.santander.batch.negativefilesrequest.model.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The type Token credentials output.
 *
 * The output object of token api
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenCredentialsOutput {
    /**
     * The jwt token.
     */
    private String jwt;
}
