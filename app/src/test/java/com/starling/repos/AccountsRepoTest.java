package com.starling.repos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import com.starling.client.IHttpClientWrapper;

import org.slf4j.Logger;

public class AccountsRepoTest {
    @Mock
    private IHttpClientWrapper mockHttpClientWrapper;
    @Mock
    private Logger mockLogger;

    private AccountsRepo repo;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        repo = new AccountsRepo(mockHttpClientWrapper, mockLogger);
    }

    @Test
    public void testGetAccounts() throws Exception {
        // Arrange
        when(mockHttpClientWrapper.get(anyString())).thenReturn("response body");

        // Act
        String result = repo.getAccounts();

        // Assert
        assertEquals("response body", result);
    }

    @Test
    public void testGetAccountsExceptionWhenError() throws Exception {
        // Arrange
        when(mockHttpClientWrapper.get(anyString())).thenThrow(RuntimeException.class);

        // Act and Assert
        assertThrows(RuntimeException.class, () -> repo.getAccounts());
    }
}
