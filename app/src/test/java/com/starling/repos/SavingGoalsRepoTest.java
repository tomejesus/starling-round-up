package com.starling.repos;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.slf4j.Logger;

import com.starling.client.IHttpClientWrapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.ArgumentMatchers.anyString;

public class SavingGoalsRepoTest {

    private SavingGoalsRepo savingGoalsRepo;

    @Mock
    private IHttpClientWrapper httpClientWrapper;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        savingGoalsRepo = new SavingGoalsRepo(httpClientWrapper, logger);
    }

    @Test
    void testAddMoneyToSavingsGoal() throws Exception {
        // Arrange
        String expectedResponse = "Mocked response";
        when(httpClientWrapper.put(anyString(), anyString())).thenReturn(expectedResponse);

        // Act
        String response = savingGoalsRepo.addMoneyToSavingsGoal("MockAccountId", "MockSavingsGoalId", 1000);

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void testAddMoneyToSavingsGoalThrowsException() throws Exception {
        // Arrange
        when(httpClientWrapper.put(anyString(), anyString())).thenThrow(new RuntimeException("Mocked error"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> savingGoalsRepo.addMoneyToSavingsGoal("MockAccountId", "MockSavingsGoalId", 1000));
    }

    @Test
    void testGetSavingGoalsList() throws Exception {
        // Arrange
        String expectedResponse = "Mocked response";
        when(httpClientWrapper.get(anyString())).thenReturn(expectedResponse);

        // Act
        String response = savingGoalsRepo.getSavingGoalsList("MockAccountId");

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void testGetSavingGoalsListThrowsException() throws Exception {
        // Arrange
        when(httpClientWrapper.get(anyString())).thenThrow(new RuntimeException("Mocked error"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> savingGoalsRepo.getSavingGoalsList("MockAccountId"));
    }

    @Test
    void testCreateSavingsGoal() throws Exception {
        // Arrange
        String expectedResponse = "Mocked response";
        when(httpClientWrapper.put(anyString(), anyString())).thenReturn(expectedResponse);

        // Act
        String response = savingGoalsRepo.createSavingsGoal("MockAccountId");

        // Assert
        assertEquals(expectedResponse, response);
    }

    @Test
    void testCreateSavingsGoalThrowsException() throws Exception {
        // Arrange
        when(httpClientWrapper.put(anyString(), anyString())).thenThrow(new RuntimeException("Mocked error"));

        // Act and Assert
        assertThrows(RuntimeException.class, () -> savingGoalsRepo.createSavingsGoal("MockAccountId"));
    }
}
