package com.starling.repos;

import org.slf4j.Logger;

import com.starling.Constants;
import com.starling.client.IHttpClientWrapper;

public class AccountsRepo implements IAccountsRepo {
    private IHttpClientWrapper httpClientWrapper;
    private Logger logger;

    public AccountsRepo(IHttpClientWrapper httpClientWrapper, Logger logger) {
        this.httpClientWrapper = httpClientWrapper;
        this.logger = logger;
    }

    public String getAccounts() {
        logger.info("Sending request to retrieve accounts...");

        try {
            String response = this.httpClientWrapper.get(Constants.GET_ACCOUNTS_API);
            logger.info("Successfully retrieved accounts.");
            return response;
        } catch (Exception e) {
            logger.error("An error occurred while attempting to retrieve accounts.", e);
            throw new RuntimeException("An error occurred while attempting to retrieve accounts: ", e);
        }
    }
}
