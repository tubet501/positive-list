package com.santander.batch.negativefilesrequest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

/**
 * The type Token credentials input.
 *
 * The input object of token api
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TokenCredentialsInput {

    /**
     * The credential type.
     */
    private List<String> credentialType;
    /**
     * The id attributes.
     */
    private IdAttributes idAttributes;
    /**
     * The password.
     */
    private String password;
    /**
     * The realm.
     */
    private String realm;
}
