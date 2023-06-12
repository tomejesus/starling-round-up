package com.starling.repos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;

import org.slf4j.Logger;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.any;

public class SavingGoalsRepoTest {

    private SavingGoalsRepo savingGoalsRepo;

    @Mock
    private HttpClient httpClient;

    @Mock
    private HttpResponse<Object> httpResponse;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        savingGoalsRepo = new SavingGoalsRepo(httpClient, logger);
    }

    @Test
    void testAddMoneyToSavingsGoal() throws Exception {
        // Arrange
        String expectedResponse = "Mocked response";
        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(expectedResponse);
        when(httpResponse.statusCode()).thenReturn(200);

        // Act
        String response = savingGoalsRepo.addMoneyToSavingsGoal("MockAccountId", "MockSavingsGoalId", 1000,
                "MockToken");

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void testAddMoneyToSavingsGoalThrowsException() throws Exception {
        // Arrange
        when(httpClient.send(any(), any())).thenThrow(new IOException("Mocked error"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> savingGoalsRepo.addMoneyToSavingsGoal("MockAccountId", "MockSavingsGoalId", 1000, "MockToken"));
    }

    @Test
    void testGetSavingGoalsList() throws Exception {
        // Arrange
        String expectedResponse = "Mocked response";
        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(expectedResponse);
        when(httpResponse.statusCode()).thenReturn(200);

        // Act
        String response = savingGoalsRepo.getSavingGoalsList("MockAccountId", "MockToken");

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetSavingGoalsListThrowsException() throws Exception {
        // Arrange
        when(httpClient.send(any(), any())).thenThrow(new IOException("Mocked error"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> savingGoalsRepo.getSavingGoalsList("MockAccountId", "MockToken"));
    }

    @Test
    void testCreateSavingsGoal() throws Exception {
        // Arrange
        String expectedResponse = "Mocked response";
        when(httpClient.send(any(), any())).thenReturn(httpResponse);
        when(httpResponse.body()).thenReturn(expectedResponse);
        when(httpResponse.statusCode()).thenReturn(200);

        // Act
        String response = savingGoalsRepo.createSavingsGoal("MockAccountId", "MockToken");

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void testCreateSavingsGoalThrowsException() throws Exception {
        // Arrange
        when(httpClient.send(any(), any())).thenThrow(new IOException("Mocked error"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> savingGoalsRepo.createSavingsGoal("MockAccountId", "MockToken"));
    }
}
