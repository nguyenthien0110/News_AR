package com.qnu.news.response;

import com.google.gson.annotations.SerializedName;

public class ApiResponse {
    @SerializedName("result")
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
