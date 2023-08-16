package com.qnu.news.response;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {
    @SerializedName("result")
    private String result;

    @SerializedName("data")
    private UserData data;

    // Getters and setters (if needed)

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public UserData getData() {
        return data;
    }

    public void setData(UserData data) {
        this.data = data;
    }

    // Nested class to represent the user data
    public static class UserData {
        @SerializedName("id")
        private String id;

        @SerializedName("username")
        private String username;

        @SerializedName("password")
        private String password;

        @SerializedName("role")
        private UserRole role;

        @SerializedName("created_at")
        private String createdAt;

        // Getters and setters (if needed)

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public UserRole getRole() {
            return role;
        }

        public void setRole(UserRole role) {
            this.role = role;
        }

        public String getCreatedAt() {
            return createdAt;
        }

        public void setCreatedAt(String createdAt) {
            this.createdAt = createdAt;
        }
    }
}
