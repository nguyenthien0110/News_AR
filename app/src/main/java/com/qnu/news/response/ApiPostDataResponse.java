package com.qnu.news.response;

import com.google.gson.annotations.SerializedName;
import com.qnu.news.custom.CustomDataTitleImage;

import java.util.List;

public class ApiPostDataResponse {
    @SerializedName("result")
    private String result;

    @SerializedName("data")
    private List<CustomDataTitleImage> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<CustomDataTitleImage> getData() {
        return data;
    }

    public void setData(List<CustomDataTitleImage> data) {
        this.data = data;
    }
}

