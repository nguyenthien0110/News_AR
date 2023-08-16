package com.qnu.news.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class PostDetails implements Serializable {
    @SerializedName("id")
    private String id;

    @SerializedName("title")
    private String title;

    @SerializedName("content")
    private String content;

    @SerializedName("urlImage")
    private String imageUrl;

    @SerializedName("category")
    private String category;

    @SerializedName("createdAt")
    private String createdAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public PostDetails(String id, String title, String content, String imageUrl, String category, String createdAt) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.imageUrl = imageUrl;
        this.category = category;
        this.createdAt = createdAt;
    }
}

