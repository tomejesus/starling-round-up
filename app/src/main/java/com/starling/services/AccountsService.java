package com.starling.services;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.starling.models.Account;
import com.starling.models.Accounts;
import com.starling.repos.IAccountsRepo;

import org.slf4j.Logger;

public class AccountsService implements IAccountsService {
    private IAccountsRepo repo;
    private ObjectMapper objectMapper;
    private Logger logger;

    public AccountsService(IAccountsRepo repo, ObjectMapper objectMapper, Logger logger) {
        this.repo = repo;
        this.objectMapper = objectMapper;
        this.logger = logger;
    }

    public String getPrimaryAccountId() {
        Account[] allAccounts;

        try {
            allAccounts = this.getAccounts().accounts;
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

    private Accounts getAccounts() {
        String rawAccounts = null;
        try {
            rawAccounts = this.repo.getAccounts();
        } catch (Exception e) {
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
