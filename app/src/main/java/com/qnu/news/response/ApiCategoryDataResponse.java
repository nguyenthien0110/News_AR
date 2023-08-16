package com.qnu.news.response;

import com.google.gson.annotations.SerializedName;
import com.qnu.news.model.Category;

import java.util.List;

public class ApiCategoryDataResponse {
    @SerializedName("result")
    private String result;

    @SerializedName("data")
    private List<Category> data;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public List<Category> getData() {
        return data;
    }

    public void setData(List<Category> data) {
        this.data = data;
    }
}
