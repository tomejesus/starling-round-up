package com.starling.services;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starling.Constants;
import com.starling.models.Account;
import com.starling.models.Accounts;

import org.slf4j.Logger;

public class AccountService {
    private HttpClient client;
    private Logger logger;

    public AccountService(HttpClient client, Logger logger) {
        this.client = client;
        this.logger = logger;
    }

    public String getPrimaryAccountId(String bearerToken) {
        Account[] allAccounts;

        try {
            allAccounts = this.getAccounts(bearerToken).accounts;
        } catch (Exception exception) {
            this.logger.error("An error occurred getting accounts: ", exception);
            throw new RuntimeException("An error occurred accounts: ", exception);
        }

        if (allAccounts.length == 0) {
            this.logger.error("No accounts found");
            throw new RuntimeException("No accounts found");
        }

        for (Account account : allAccounts) {
            if ("PRIMARY".equals(account.accountType)) {
                return account.accountUid;
            }
        }

        this.logger.error("Primary account not found");
        throw new RuntimeException("Primary account not found");
    }

    private Accounts getAccounts(String bearerToken) {
        String rawAccounts = this.getRawAccounts(bearerToken);
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            Accounts accounts = objectMapper.readValue(rawAccounts, Accounts.class);
            return accounts;
        } catch (Exception exception) {
            this.logger.error("An error occurred: ", exception);
            throw new RuntimeException("An error occurred: ", exception);
        }
    }

    private String getRawAccounts(String bearerToken) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(Constants.GET_ACCOUNTS_API))
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
