package com.starling.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starling.repos.ISavingGoalsRepo;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

public class SavingGoalsServiceTest {

    private ISavingGoalsService savingGoalsService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private String accountId = "testAccountId";
    private String bearerToken = "testToken";
    private int amount = 1000;
    private String savingsGoalId = "testGoalId";

    @Mock
    private ISavingGoalsRepo repo;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        savingGoalsService = new SavingGoalsService(repo, objectMapper, logger);
    }

    @Test
    void testAddMoneyToSavingsGoal() {
        // Arrange
        when(repo.getSavingGoalsList(accountId, bearerToken))
                .thenReturn("{\"savingsGoalList\": [{\"name\": \"RoundUpSavingsGoal\", \"savingsGoalUid\": \"" + savingsGoalId
                        + "\"}]}");
        when(repo.addMoneyToSavingsGoal(accountId, savingsGoalId, amount, bearerToken))
                .thenReturn("{\"transferUid\":\"transferId\",\"success\": \"true\"}");

        // Act
        Assertions.assertDoesNotThrow(() -> savingGoalsService.addMoneyToSavingsGoal(accountId, bearerToken, amount));

        // Assert
        verify(repo).addMoneyToSavingsGoal(accountId, savingsGoalId, amount, bearerToken);
    }

    @Test
    void testAddMoneyToSavingsGoalFailure() {
        // Arrange
        when(repo.getSavingGoalsList(accountId, bearerToken))
                .thenReturn("{\"savingsGoalList\": [{\"name\": \"RoundUpSavingsGoal\", \"savingsGoalUid\": \"" + savingsGoalId
                        + "\"}]}");
        when(repo.addMoneyToSavingsGoal(accountId, savingsGoalId, amount, bearerToken))
                .thenReturn("{\"success\": \"false\"}");

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> savingGoalsService.addMoneyToSavingsGoal(accountId, bearerToken, amount));

        // Assert
        assertEquals("Unable to add money to savings goal.", exception.getMessage());
    }

    @Test
    void testAddMoneyToSavingsGoalException() {
        // Arrange
        String expectedMessage = "Failed to get savings goal ID";
        when(repo.getSavingGoalsList(accountId, bearerToken))
                .thenThrow(new RuntimeException("An error occurred getting raw savings goal list"));

        // Act
        Exception exception = assertThrows(RuntimeException.class, () -> {
            savingGoalsService.addMoneyToSavingsGoal(accountId, bearerToken, amount);
        });
        String actualMessage = exception.getMessage();

        // Assert
        assertEquals(expectedMessage, actualMessage);
    }
}
