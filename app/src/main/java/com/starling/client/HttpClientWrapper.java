package com.starling.client;

import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.slf4j.Logger;

import java.net.URI;

public class HttpClientWrapper implements IHttpClientWrapper {
    private HttpClient httpClient;
    private String bearerToken;
    private Logger logger;

    public HttpClientWrapper(HttpClient httpClient, String bearerToken, Logger logger) {
        this.httpClient = httpClient;
        this.bearerToken = bearerToken;
        this.logger = logger;
    }

    public String get(String url) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Authorization", "Bearer " + this.bearerToken)
                .build();

        try {
            logger.info("Sending GET request to {}", url);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Response status code: {}", response.statusCode());
            return response.body();
        } catch (Exception e) {
            logger.error("Failed to execute GET request", e);
            throw new RuntimeException("Failed to execute GET request", e);
        }
    }

    public String put(String url, String payload) {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .PUT(HttpRequest.BodyPublishers.ofString(payload))
                .header("Authorization", "Bearer " + this.bearerToken)
                .header("Content-Type", "application/json")
                .build();

        try {
            logger.info("Sending PUT request to {}", url);
            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            logger.info("Response status code: {}", response.statusCode());
            return response.body();
        } catch (Exception e) {
            logger.error("Failed to execute PUT request", e);
            throw new RuntimeException("Failed to execute PUT request", e);
        }
    }
}
