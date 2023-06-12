package com.starling;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.starling.client.StarlingClient;

import static org.mockito.Mockito.*;

class AppTest {
    private App app;

    @Mock
    private StarlingClient starlingClient;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        app = new App(starlingClient);
    }

    @Test
    void runAppWithCorrectArgs() {
        // Arrange
        String weekStart = "2023-01-01";
        String bearerToken = "bearer";
        String[] args = new String[] { weekStart, bearerToken };

        // Act
        app.run(args);

        // Assert
        verify(starlingClient).processSavings(weekStart, bearerToken);
    }
}
