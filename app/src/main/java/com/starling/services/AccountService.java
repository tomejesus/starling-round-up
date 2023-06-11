package com.starling.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.jayway.jsonpath.JsonPath;
import com.starling.Constants;

import org.slf4j.Logger;

public class AccountService {
    private HttpClient client;
    private Logger logger;

    public AccountService(HttpClient client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    public String getPrimaryAccountId(String bearerToken) {
        String accounts;

        try {
            accounts = this.getAccounts(bearerToken);
        } catch (Exception exception) {
            this.logger.error("An error occurred getting accounts: ", exception);
            throw new RuntimeException("An error occurred accounts: ", exception);
        }
        String accountId;

        // TODO: Create a model for the response and use that instead of JsonPath
        try {
            accountId = JsonPath.read(accounts, "$.accounts[0].accountUid");
            this.logger.info("Primary account ID: " + accountId);
        } catch (Exception exception) {
            this.logger.error("An error occurred getting primary account ID: ", exception);
            throw new RuntimeException("An error occurred getting primary account ID: ", exception);
        }

        return accountId;
    }

    private String getAccounts(String bearerToken) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Constants.ACCOUNTS_API))
                .header("Authorization", "Bearer " + bearerToken)
                .build();
        try {
            HttpResponse<String> response = this.client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (Exception exception) {
            this.logger.error("An error occurred: ", exception);
            throw new RuntimeException("An error occurred: ", exception);
        }
    }

}
