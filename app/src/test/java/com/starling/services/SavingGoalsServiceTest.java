package com.starling.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starling.repos.SavingGoalsRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

public class SavingGoalsServiceTest {

    private SavingGoalsService savingGoalsService;
    private ObjectMapper objectMapper = new ObjectMapper();
    private String accountId = "testAccountId";
    private String bearerToken = "testToken";
    private int amount = 1000;
    private String savingsGoalId = "testGoalId";

    @Mock
    private SavingGoalsRepo repo;

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
                .thenReturn("{\"savingsGoalList\": [{\"name\": \"RoundUp1\", \"savingsGoalUid\": \"" + savingsGoalId
                        + "\"}]}");
        when(repo.addMoneyToSavingsGoal(accountId, savingsGoalId, amount, bearerToken))
                .thenReturn("{\"transferUid\":\"transferId\",\"success\": \"true\"}");

        // Act
        String response = savingGoalsService.addMoneyToSavingsGoal(accountId, bearerToken, amount);

        // Assert
        assertEquals("GBP 10.00 added to savings goal.", response);
    }

    @Test
    void testAddMoneyToSavingsGoalFailure() {
        // Arrange
        when(repo.getSavingGoalsList(accountId, bearerToken))
                .thenReturn("{\"savingsGoalList\": [{\"name\": \"RoundUp1\", \"savingsGoalUid\": \"" + savingsGoalId
                        + "\"}]}");
        when(repo.addMoneyToSavingsGoal(accountId, savingsGoalId, amount, bearerToken))
                .thenReturn("{\"success\": \"false\"}");

        // Act
        String response = savingGoalsService.addMoneyToSavingsGoal(accountId, bearerToken, amount);

        // Assert
        assertEquals("Unable to add money to savings goal.", response);
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
