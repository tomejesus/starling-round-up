package com.starling;

public class Constants {

    private Constants() {
        // Prevent instantiation
    }

    public static final String ACCOUNTS_API = "https://api-sandbox.starlingbank.com/api/v2/accounts";

    public static final String FEED_API_STRING_FORMAT = "https://api-sandbox.starlingbank.com/api/v2/feed/account/%s/settled-transactions-between?minTransactionTimestamp=%s&maxTransactionTimestamp=%s";

}
