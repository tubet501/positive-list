package com.santander.batch.negativefilesrequest.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.santander.batch.negativefilesrequest.service.ExternalAPIService;
import com.santander.darwin.core.exceptions.InternalServerErrorDarwinException;
import com.santander.darwin.core.exceptions.NotFoundDarwinException;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

/**
 * The type External api service.
 */
@Service
@Slf4j
public class ExternalAPIServiceImpl implements ExternalAPIService {

    private static final String TECHNICAL_ERROR = "Technical error";
    private static final String NOT_FOUND = "Not found";

    /**
     * Custom web client
     */
    private WebClient webClient;

    /**
     * Deserialize responses from WebClient
     */
    private ObjectMapper objectMapper;

    /**
     * Instantiates a new External api service.
     *
     * @param webClientBuilder the web client builder
     * @param objectMapper     the object mapper
     */
    public ExternalAPIServiceImpl(WebClient.Builder webClientBuilder, ObjectMapper objectMapper) {
        this.webClient = webClientBuilder.build();
        this.objectMapper = objectMapper;
    }

    /**
     * Method to do HTTP Post with WebClient
     *
     * @param urlBase     url
     * @param path        path
     * @param input       input
     * @param outputClass outputClass
     * @param pathParameters  the call path parameters
     * @param incomingHeaders the call incoming headers
     * @param <T>         Generic Type
     * @return ResponseEntity
     */
    @CircuitBreaker(name = "doGetPost", fallbackMethod = "fallbackDoPost")
    @Override
    public <T> ResponseEntity<T> doPost(String urlBase, String path, Object input, Class<T> outputClass,
                                        Map<String, String> pathParameters, Map<String, String> incomingHeaders) {
        // If params is null we will create an empty HashMap
        if (pathParameters == null) {
            pathParameters = new HashMap<>();
        }

        final Map<String, String> httpHeaders = new HashMap<>();
        if (incomingHeaders != null && !incomingHeaders.isEmpty()) {
            httpHeaders.putAll(incomingHeaders);
        }

        URI uri = UriComponentsBuilder
                .fromUriString(urlBase)
                .path(path)
                .build(pathParameters);

        ClientResponse clientResponse = webClient
                .post()
                .uri(uri)
                .headers(h -> h.setAll(httpHeaders))
                .body(Mono.just(input), input.getClass())
                .exchange()
                .block();

        // Get the body
        return getResponseEntity(uri, outputClass, clientResponse, "[doPost][API]");
    }

    /**
     * Gets response entity.
     *
     * @param <T>              the type parameter
     * @param uri              the uri
     * @param outputClass      the output class
     * @param clientResponse   the client response
     * @param exceptionMessage the exception message
     * @return the response entity
     */
    private <T> ResponseEntity<T> getResponseEntity(URI uri, Class<T> outputClass,
                                                    ClientResponse clientResponse, String exceptionMessage) {
        String bodyResponse = clientResponse.bodyToMono(String.class).block();

        // If there is an 4xx or 5xx error throw a Exception
        HttpStatus httpStatusResponse = clientResponse.statusCode();
        checkIsError(bodyResponse, httpStatusResponse);

        //Check body response
        checkBody(uri, exceptionMessage, bodyResponse, httpStatusResponse);

        try {
            if(uri.getPath().contains("business") || uri.getPath().contains("channel")){
                return new ResponseEntity<>(HttpStatus.OK);
            }else{
                return new ResponseEntity<>(objectMapper.readValue(bodyResponse, outputClass), httpStatusResponse);
            }
        } catch (JsonProcessingException je) {
            log.error("exceptionMessage", je);
            throw new HttpServerErrorException(httpStatusResponse,
                    exceptionMessage + " Incorrect answer from " + uri);
        }
    }

    /**
     * Check is error.
     *
     * @param bodyResponse       the body response
     * @param httpStatusResponse the http status response
     */
    private void checkIsError(String bodyResponse, HttpStatus httpStatusResponse) {
        if (httpStatusResponse.is4xxClientError()) {
            throw new HttpClientErrorException(httpStatusResponse,
                    bodyResponse == null ? "" : bodyResponse);
        }

        if (httpStatusResponse.is5xxServerError()) {
            throw new HttpServerErrorException(httpStatusResponse,
                    bodyResponse == null ? "" : bodyResponse);
        }

        if (httpStatusResponse.is2xxSuccessful() && httpStatusResponse.value() == 204) {
            throw new HttpServerErrorException(httpStatusResponse,
                    bodyResponse == null ? "" : bodyResponse);
        }
    }

    /**
     * Check body.
     *
     * @param uri                the uri
     * @param exceptionMessage   the exception message
     * @param bodyResponse       the body response
     * @param httpStatusResponse the http status response
     */
    private void checkBody(URI uri, String exceptionMessage, String bodyResponse, HttpStatus httpStatusResponse) {
        if ((bodyResponse == null || bodyResponse.isEmpty()) && isPermittedUri(uri)) {
            String msg = exceptionMessage + " Body response is null or empty from " + uri +
                    " - Status code response " + httpStatusResponse.value();
            log.error(msg);
            throw new NotFoundDarwinException(NOT_FOUND);
        }
    }

    /**
     * Is permitted uri boolean.
     *
     * @param uri the uri
     * @return the boolean
     */
    private boolean isPermittedUri(URI uri) {
        return !uri.getPath().contains("business") &&
                !uri.getPath().contains("channel") && !uri.getPath().contains("formalizacionTarjeta")
                && !uri.getPath().contains("client-risk-limits");
    }

    /**
     * Fallback do post t.
     * /**
     * Method to do HTTP Post with WebClient
     *
     * @param <T>            Generic Type
     * @param urlBase        url
     * @param path           path
     * @param input          input
     * @param outputClass    outputClass
     * @param pathParameters the path parameters
     * @param httpHeaders    the http headers
     * @param hse            the hse
     * @return the t
     */
    public <T> ResponseEntity<T> fallbackDoPost(
            String urlBase, String path, Object input, Class<T> outputClass,
            Map<String, String> pathParameters,
            Map<String, String> httpHeaders,
            HttpServerErrorException hse) {
        log.error("[doPost][CB-OPEN][API] Error calling API Service", hse);
        throw hse;
    }

    /**
     * Fallback do get response entity.
     *
     * @param <T>            Generic Type
     * @param urlBase        url
     * @param path           path
     * @param input          input
     * @param outputClass    outputClass
     * @param pathParameters the path parameters
     * @param httpHeaders    the http headers
     * @param ioe            the ioe
     * @return the response entity
     */
    public <T> ResponseEntity<T> fallbackDoPost(
            String urlBase, String path, Object input, Class<T> outputClass,
            Map<String, String> pathParameters,
            Map<String, String> httpHeaders,
            IOException ioe) {
        log.error("[doPost][CB-OPEN][API] Error calling API Service", ioe);
        throw new InternalServerErrorDarwinException(TECHNICAL_ERROR, ioe.getMessage());
    }

}

