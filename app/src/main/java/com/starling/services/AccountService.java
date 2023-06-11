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
        String response = this.getAccounts(bearerToken);
        String accountId = JsonPath.read(response, "$.accounts[0].accountUid");
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
