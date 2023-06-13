package com.starling.repos;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.io.IOException;
import java.net.http.HttpTimeoutException;

import org.slf4j.Logger;

import com.starling.Constants;

public class AccountsRepo implements IAccountsRepo {
    private HttpClient client;
    private Logger logger;

    public AccountsRepo(HttpClient client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    public String getAccounts(String bearerToken) throws IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Constants.GET_ACCOUNTS_API))
                .header("Authorization", "Bearer " + bearerToken)
                .build();

        logger.info("Sending request to retrieve accounts...");

        try {
            HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() != 200) {
                logger.error("Failed to retrieve accounts. HTTP status code: {}", response.statusCode());
                throw new RuntimeException("Failed to retrieve accounts. HTTP status code: " + response.statusCode());
            }
            logger.info("Successfully retrieved accounts.");
            return response.body();
        } catch (HttpTimeoutException e) {
            logger.error("A timeout occurred while attempting to retrieve accounts.", e);
            throw new RuntimeException("A timeout occurred while attempting to retrieve accounts: ", e);
        } catch (IOException | InterruptedException e) {
            logger.error("An error occurred while attempting to retrieve accounts.", e);
            throw new RuntimeException("An error occurred while attempting to retrieve accounts: ", e);
        }
    }
}
