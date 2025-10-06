package com.santander.batch.negativefilesrequest.service;

import org.springframework.http.ResponseEntity;

import java.util.Map;

/**
 * The interface External api service.
 */
public interface ExternalAPIService {

    /**
     * Method to do HTTP Post with WebClient
     *
     * @param <T>             Generic Type
     * @param urlBase         the call urlBase
     * @param path            the callurl path
     * @param input           input
     * @param outputClass     outputClass
     * @param pathParameters  the call path parameters
     * @param incomingHeaders the call incoming headers
     * @return response entity
     */
    <T> ResponseEntity<T> doPost(String urlBase, String path, Object input, Class<T> outputClass,
                                 Map<String, String> pathParameters,
                                 Map<String, String> incomingHeaders);
}
