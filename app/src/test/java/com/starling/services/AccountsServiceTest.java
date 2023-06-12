package com.starling.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starling.repos.AccountsRepo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AccountsServiceTest {

    private AccountsService accountsService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AccountsRepo accountsRepo;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        accountsService = new AccountsService(accountsRepo, objectMapper, logger);
    }

    @Test
    void testGetPrimaryAccountIdGetsId() throws Exception {
        // Arrange
        when(accountsRepo.getAccounts("MockToken")).thenReturn(("{\"accounts\":[{\"accountUid\":\"MockAccountId\",\"accountType\":\"PRIMARY\"}]}"));

        // Act
        String response = accountsService.getPrimaryAccountId("MockToken");

        // Assert
        assertEquals("MockAccountId", response);
    }

    @Test
    void testGetPrimaryAccountIdThrowsExceptionWhenNoPrimary() throws Exception {
        // Arrange
        when(accountsRepo.getAccounts("MockToken")).thenReturn("{\"accounts\":[{\"accountUid\":\"MockAccountId\"}]}");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> accountsService.getPrimaryAccountId("MockToken"));
    }

    @Test
    void testGetPrimaryAccountIdThrowsExceptionWhenNoAccounts() throws Exception {
        // Arrange
        when(accountsRepo.getAccounts("MockToken")).thenReturn("{\"accounts\":[]}");

        // Act and Assert
        assertThrows(RuntimeException.class, () -> accountsService.getPrimaryAccountId("MockToken"));
    }

    @Test
    void testGetPrimaryAccountIdThrowsExceptionOnError() throws Exception {
        // Arrange
        when(accountsRepo.getAccounts("MockToken")).thenThrow(new RuntimeException("Mocked Error", null));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> accountsService.getPrimaryAccountId("MockToken"));
    }
}