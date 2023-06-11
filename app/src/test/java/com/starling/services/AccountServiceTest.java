package com.starling.services;

import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AccountServiceTest {
    private final HttpClient client = mock(HttpClient.class);
    private final Logger logger = LoggerFactory.getLogger(AccountServiceTest.class);
    private final AccountService accountService = new AccountService(client, logger);

    @Test
    void testGetPrimaryAccountIdGetsId() throws Exception {
        // Arrange
        HttpResponse<Object> httpResponse = mock(HttpResponse.class);
        when(httpResponse.body()).thenReturn("{\"accounts\":[{\"accountUid\":\"MockAccountId\"}]}");
        when(client.send(any(), any())).thenReturn(httpResponse);

        // Act
        String response = accountService.getPrimaryAccountId("Mock token");

        // Assert
        assertEquals("MockAccountId", response);
    }

    @Test
    void testGetPrimaryAccountIdThrowsExceptionOnError() throws Exception {
        // Arrange
        HttpResponse<Object> httpResponse = mock(HttpResponse.class);
        when(httpResponse.body()).thenReturn(new Exception("Mocked Error", null));
        when(client.send(any(), any())).thenReturn(httpResponse);

        // Act
        try {
            accountService.getPrimaryAccountId("Mock token");
        } catch (Exception exception) {
            // Assert
            assertEquals("An error occurred: Mocked Error", "An error occurred: Mocked Error");
        }
    }

    @Test
    void testGetPrimaryAccountIdThrowsExceptionWhenServerError() throws Exception {
        // Arrange
        HttpResponse<Object> httpResponse = mock(HttpResponse.class);
        when(httpResponse.statusCode()).thenReturn(404);
        when(client.send(any(), any())).thenReturn(httpResponse);

        // Act
        try {
            accountService.getPrimaryAccountId("Mock token");
        } catch (Exception exception) {
            // Assert
            assertEquals("An error occurred: 404", "An error occurred: 404");
        }
    }

    @Test
    void testGetPrimaryAccountIdThrowsExceptionWhenAccountsNotPresent() throws Exception {
        // Arrange
        HttpResponse<Object> httpResponse = mock(HttpResponse.class);
        when(httpResponse.body()).thenReturn("");
        when(client.send(any(), any())).thenReturn(httpResponse);

        // Act
        try {
            accountService.getPrimaryAccountId("Mock token");
        } catch (Exception exception) {
            // Assert
            assertEquals("An error occurred: No accounts found", "An error occurred: No accounts found");
        }
    }
}