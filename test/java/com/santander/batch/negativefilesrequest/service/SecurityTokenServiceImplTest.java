package com.santander.batch.negativefilesrequest.service;

import com.santander.batch.negativefilesrequest.model.dto.TokenCredentialsOutput;
import com.santander.batch.negativefilesrequest.service.impl.SecurityTokenServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(SpringExtension.class)
public class SecurityTokenServiceImplTest {

    @InjectMocks
    private SecurityTokenServiceImpl securityTokenService;
    @Mock
    private ExternalAPIService externalAPIService;

    @Test
    public void getSecurityToken() {
        Mockito.when(externalAPIService
                        .doPost(any(), any(), any(), any(), any(), any()))
                .thenReturn(ResponseEntity.ok(new TokenCredentialsOutput("")));
        assertNotNull(securityTokenService.getSecurityToken());
    }
}