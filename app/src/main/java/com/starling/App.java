package com.starling;

import java.net.http.HttpClient;

import com.starling.client.HttpClientWrapper;
import com.starling.client.StarlingClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class App {
    private static final Logger LOGGER = LoggerFactory.getLogger(App.class);
    private final StarlingClient starlingClient;

    public App(StarlingClient starlingClient) {
        this.starlingClient = starlingClient;
    }

    public void run(String weekStart) {
        starlingClient.processSavings(weekStart);
    }

    public static void main(String[] args) {
        if (args.length < 2) {
            LOGGER.error("No arguments provided.");
            System.err.println("Please provide the week start and bearer token as a command line argument.");
            System.exit(1);
        }

        HttpClient httpClient = HttpClient.newHttpClient();

        String weekStart = args[0];
        String bearerToken = args[1];
        HttpClientWrapper httpClientWrapper = new HttpClientWrapper(httpClient, bearerToken, LOGGER);
        StarlingClient starlingClient = new StarlingClient(httpClientWrapper);

        new App(starlingClient).run(weekStart);
    }
}
