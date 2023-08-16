package com.qnu.news.response;

import com.google.gson.annotations.SerializedName;

public class UserRole {
    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    // Getters and setters (if needed)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
