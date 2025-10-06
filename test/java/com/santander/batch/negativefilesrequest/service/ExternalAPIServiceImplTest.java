package com.santander.batch.negativefilesrequest.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.extension.responsetemplating.ResponseTemplateTransformer;
import com.santander.batch.negativefilesrequest.service.impl.ExternalAPIServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.reactive.function.client.WebClient;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.options;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
public class ExternalAPIServiceImplTest {

    private ExternalAPIServiceImpl externalAPIService;

    private WireMockServer wireMockServer;

    private WebClient webClient;

    private String urlBase;

    private String endpoint;

    private String endpointAdn;

    private String endpointAdnQuery;

    private String doPostResponse="";

    private String response;

    @BeforeEach
    public void setup() {

        urlBase = "http://localhost:8082";
        endpoint = "/solicitudes/simulacionCuadroAmortizacion";

        endpointAdn= "/bills/{id}";
        endpointAdnQuery= "/bills";

        this.webClient = WebClient.create(urlBase+endpoint);

        externalAPIService = new ExternalAPIServiceImpl(WebClient.builder(), new ObjectMapper());
        this.wireMockServer = new WireMockServer(options()
                .extensions(new ResponseTemplateTransformer(false))
                .port(8082));

        this.wireMockServer.start();
    }

    @AfterEach
    public void tearDown() {
        wireMockServer.stop();
    }
    @Test
    public void doPostTest() {

        String request = "Request";

        ObjectMapper mapper = new ObjectMapper();
        response = "Response";
        try {
            doPostResponse = mapper.writeValueAsString(response);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        this.wireMockServer.stubFor(post(endpoint).willReturn(okJson(doPostResponse)));

        assertEquals(response, externalAPIService.doPost(
                urlBase,endpoint,request,
                String.class,
                null,
                null).getBody());
    }


    @Test
    public void doPostErrorTest() {
        Assertions.assertThrows(HttpClientErrorException.class, () -> {
            String request = "Request";

            this.wireMockServer.stubFor(post(endpoint).willReturn(status(400)));
            externalAPIService.doPost(
                    urlBase,
                    endpoint,
                    request,
                    String.class,
                    null,
                    null);
        });
    }

    @Test
    public void doPostServerErrorTest() {
        Assertions.assertThrows(HttpServerErrorException.class, () -> {
            String request = "Request";
            String response = "Response";

            this.wireMockServer.stubFor(post(endpoint).willReturn(status(500)));
            externalAPIService.doPost(
                    urlBase,
                    endpoint,
                    request,
                    String.class,
                    null,
                    null);
        });
    }
}
