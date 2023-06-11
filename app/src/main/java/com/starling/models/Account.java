package com.starling.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Account {
    public String accountUid;
    public String accountType;
    public String currency;

    @JsonCreator
    public Account(
            @JsonProperty("accountUid") String accountUid,
            @JsonProperty("accountType") String accountType,
            @JsonProperty("currency") String currency) {
        this.accountUid = accountUid;
        this.accountType = accountType;
        this.currency = currency;
    }
}