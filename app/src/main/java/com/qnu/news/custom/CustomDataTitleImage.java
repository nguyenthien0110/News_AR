package com.qnu.news.custom;

import com.google.gson.annotations.SerializedName;

public class CustomDataTitleImage {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("urlImage")
    private String imageUrl;

    @SerializedName("createdAt")
    private String createdAt;

    public CustomDataTitleImage(String id, String title, String imageUrl, String createdAt) {
        this.id = id;
        this.title = title;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getCreatedAt() {
        return createdAt;
    }
}
