package com.starling;

public class Constants {

    private Constants() {
        // Prevent instantiation
    }

    public static final String GET_ACCOUNTS_API = "https://api-sandbox.starlingbank.com/api/v2/accounts";

    public static final String GET_FEED_API_STRING_FORMAT = "https://api-sandbox.starlingbank.com/api/v2/feed/account/%s/settled-transactions-between?minTransactionTimestamp=%s&maxTransactionTimestamp=%s";

    public static final String SAVINGS_GOALS_API_STRING_FORMAT = "https://api-sandbox.starlingbank.com/api/v2/account/%s/savings-goals";

    public static final String ADD_MONEY_TO_SAVINGS_GOAL_API_STRING_FORMAT = "https://api-sandbox.starlingbank.com/api/v2/account/%s/savings-goals/%s/add-money/%s";

    public static final String SAVINGS_GOAL_REQUEST_BODY = "{"
            + "\"name\": \"RoundUpSavingsGoal\","
            + "\"currency\": \"GBP\","
            + "\"target\": {"
            + "\"currency\": \"GBP\","
            + "\"minorUnits\": 0"
            + "},"
            + "\"base64EncodedPhoto\": \"string\""
            + "}";

    public static final String ADD_MONEY_TO_SAVINGS_GOAL_REQUEST_BODY_FORMAT = "{"
            + "\"amount\": {"
            + "\"currency\": \"GBP\","
            + "\"minorUnits\": %s"
            + "}"
            + "}";

    public static final String OUTGOING_TRANSACTION = "OUT";

    public static final String DATE_FORMAT = "%sT00:00:00.000Z";

}
