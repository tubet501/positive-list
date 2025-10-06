package com.santander.batch.negativefilesrequest.service.impl;

import com.santander.batch.negativefilesrequest.model.dto.IdAttributes;
import com.santander.batch.negativefilesrequest.model.dto.TokenCredentialsInput;
import com.santander.batch.negativefilesrequest.model.dto.TokenCredentialsOutput;
import com.santander.batch.negativefilesrequest.service.ExternalAPIService;
import com.santander.batch.negativefilesrequest.service.SecurityTokenService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * The type Security token service.
 */
@Service
@Slf4j
public class SecurityTokenServiceImpl implements SecurityTokenService {

    /**
     * The Url base token.
     */
    @Value("${app.token.urlBase}")
    public String urlBaseToken;

    /**
     * The End point token.
     */
    @Value("${app.token.endPoint}")
    public String endPointToken;

    /**
     * The Alias token.
     */
    @Value("${app.token.alias}")
    public String aliasToken;

    /**
     * The Password token.
     */
    @Value("${app.token.password}")
    public String passwordToken;

    /**
     * The Realm token.
     */
    @Value("${app.token.realm}")
    public String realmToken;

    /**
     * The externalAPIService.
     */
    private ExternalAPIService externalAPIService;

    /**
     * Instantiates a new Security token service.
     *
     * @param externalAPIService the external api service
     */
    public SecurityTokenServiceImpl(ExternalAPIService externalAPIService) {
        this.externalAPIService = externalAPIService;
    }

    /**
     * Gets security token.
     *
     * @return the security token
     */
    @Cacheable(value = "tokens")
    @Override
    public String getSecurityToken() {
        //log the call
        log.info("[getSecurityToken] Generating token");
        //Generate new input class
        TokenCredentialsInput tokenCredentialsInput = new TokenCredentialsInput();
        //Generate new credential type list
        List<String> credentialTypeList = new ArrayList<>();
        //Add the jwt type
        credentialTypeList.add("JWT");
        //Set the credential tipe list generated in input class
        tokenCredentialsInput.setCredentialType(credentialTypeList);
        //Set the id alais
        tokenCredentialsInput.setIdAttributes(new IdAttributes(aliasToken));
        //Set the password
        tokenCredentialsInput.setPassword(passwordToken);
        //Set the realm
        tokenCredentialsInput.setRealm(realmToken);
        //Call token api
        return externalAPIService
                .doPost(urlBaseToken, endPointToken, tokenCredentialsInput, TokenCredentialsOutput.class, null, null)
                .getBody()
                .getJwt();
    }
}

