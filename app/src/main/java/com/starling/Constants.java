package com.starling;

public class Constants {

    private Constants() {
        // Prevent instantiation
    }

    public static final String STARLING_API_URL = "https://api-sandbox.starlingbank.com/api/v2";

    public static final String FEED_REQUEST_STRING_FORMAT = "%s/feed/account/%s/settled-transactions-between?minTransactionTimestamp=%s&maxTransactionTimestamp=%s";

}
