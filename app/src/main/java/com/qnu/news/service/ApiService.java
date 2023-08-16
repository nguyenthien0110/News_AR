package com.qnu.news.service;

import com.qnu.news.model.PostDetails;
import com.qnu.news.request.LoginRequest;
import com.qnu.news.response.ApiCategoryDataResponse;
import com.qnu.news.response.ApiPostDataResponse;
import com.qnu.news.response.ApiResponse;
import com.qnu.news.response.LoginResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @GET("api/v1/post/get-all")
    Call<ApiPostDataResponse> getAllPosts();

    @GET("api/v1/post/seach")
    Call<ApiPostDataResponse> search(@Query("query") String title);

    @GET("api/v1/category/get-all")
    Call<ApiCategoryDataResponse> getAllCategory();

    @GET("api/v1/post/{postId}")
    Call<PostDetails> getPostDetails(@Path("postId") String postId);

    @GET("/api/v1/post/get-by-category/{categoryId}")
    Call<ApiPostDataResponse> getPostByCategory(@Path("categoryId") String categoryId);

    @GET("api/v1/collection/{username}")
    Call<ApiPostDataResponse> getPostByUsernameForCollection(@Path("username") String username);

    @POST("api/v1/user/login")
    Call<LoginResponse> login(@Body LoginRequest loginRequest);

    @POST("api/v1/user/signup")
    Call<ApiResponse> signup(@Body LoginRequest loginRequest);
}
