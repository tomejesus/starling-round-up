package com.starling.models;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class CreateSavingsGoalResponse {
    public String savingsGoalUid;
    public String success;

    @JsonCreator
    public CreateSavingsGoalResponse(
            @JsonProperty("savingsGoalUid") String savingsGoalUid,
            @JsonProperty("success") String success) {
        this.savingsGoalUid = savingsGoalUid;
        this.success = success;
    }
}
