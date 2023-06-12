package com.starling.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AddToSavingsGoalResponse {
    public String transferUid;
    public String success;

    @JsonCreator
    public AddToSavingsGoalResponse(
            @JsonProperty("transferUid") String transferUid,
            @JsonProperty("success") String success) {
        this.transferUid = transferUid;
        this.success = success;
    }
}
