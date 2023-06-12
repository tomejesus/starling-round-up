package com.starling.services;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starling.models.Account;
import com.starling.models.Accounts;
import com.starling.repos.AccountsRepo;

import org.slf4j.Logger;

public class AccountsService {
    private AccountsRepo repo;
    private ObjectMapper objectMapper;
    private Logger logger;

    public AccountsService(AccountsRepo repo, ObjectMapper objectMapper, Logger logger) {
        this.repo = repo;
        this.objectMapper = objectMapper;
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
                this.logger.info("Primary account found: {}", account.accountUid);
                return account.accountUid;
            }
        }

        this.logger.error("Primary account not found");
        throw new RuntimeException("Primary account not found");
    }

    private Accounts getAccounts(String bearerToken) {
        String rawAccounts = null;
        try {
            rawAccounts = this.repo.getAccounts(bearerToken);
        } catch (IOException | InterruptedException e) {
            this.logger.error("An error occurred when calling repo.getAccounts: ", e);
            throw new RuntimeException("An error occurred when calling repo.getAccounts: ", e);
        }

        try {
            Accounts accounts = this.objectMapper.readValue(rawAccounts, Accounts.class);
            return accounts;
        } catch (IOException e) {
            this.logger.error("An error occurred when parsing the raw accounts data: ", e);
            throw new RuntimeException("An error occurred when parsing the raw accounts data: ", e);
        }
    }
}
