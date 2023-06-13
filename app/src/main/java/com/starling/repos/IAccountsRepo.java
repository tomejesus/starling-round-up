package com.starling.repos;

import java.io.IOException;

public interface IAccountsRepo {
    String getAccounts(String bearerToken) throws IOException, InterruptedException;
}
