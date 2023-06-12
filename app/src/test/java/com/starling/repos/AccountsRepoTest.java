package com.starling.repos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;

import org.slf4j.Logger;

public class AccountsRepoTest {
    @Mock
    private HttpClient mockClient;
    @Mock
    private Logger mockLogger;
    @Mock
    private HttpResponse<String> mockResponse;

    private AccountsRepo repo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        repo = new AccountsRepo(mockClient, mockLogger);
    }

    @Test
    public void testGetAccounts() throws Exception {
        // Arrange
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(200);
        when(mockResponse.body()).thenReturn("response body");

        // Act
        String result = repo.getAccounts("bearerToken");

        // Assert
        assertEquals("response body", result);
    }

    @Test
    public void testGetAccountsExceptionWhenError() throws Exception {
        // Arrange
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenThrow(IOException.class);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> repo.getAccounts("bearerToken"));
    }

    @Test
    public void testGetAccountsExceptionWhenNot200() throws Exception {
        // Arrange
        when(mockClient.send(any(HttpRequest.class), any(HttpResponse.BodyHandler.class))).thenReturn(mockResponse);
        when(mockResponse.statusCode()).thenReturn(500);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> repo.getAccounts("bearerToken"));
    }
}
