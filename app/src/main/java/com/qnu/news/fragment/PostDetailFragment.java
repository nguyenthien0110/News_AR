package com.qnu.news.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qnu.news.R;
import com.qnu.news.config.ApiServiceClient;
import com.qnu.news.model.PostDetails;
import com.qnu.news.service.ApiService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailFragment extends Fragment {

    private static final String ARG_POST_ID = "post_id";

    public static PostDetailFragment newInstance(String postId) {
        PostDetailFragment fragment = new PostDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_POST_ID, postId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail, container, false);

        TextView titleTextView = view.findViewById(R.id.titleTextView);
        ImageView imageView = view.findViewById(R.id.imageView);
        TextView dateTextView = view.findViewById(R.id.dateTextView);
        TextView contentTextView = view.findViewById(R.id.contentTextView);

        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_POST_ID)) {
            String postId = args.getString(ARG_POST_ID);
            fetchPostDetails(postId, titleTextView, imageView, dateTextView, contentTextView);
        }

        return view;
    }

    private void fetchPostDetails(String postId, TextView titleTextView, ImageView imageView, TextView dateTextView, TextView contentTextView) {
        ApiService apiService = ApiServiceClient.getApiService();
        //CAll api
        Call<PostDetails> call = apiService.getPostDetails(postId);
        call.enqueue(new Callback<PostDetails>() {
            @Override
            public void onResponse(Call<PostDetails> call, Response<PostDetails> response) {
                if (response.isSuccessful()) {
                    PostDetails postDetails = response.body();
                    if (postDetails != null) {
                        titleTextView.setText(postDetails.getTitle());
                        dateTextView.setText(postDetails.getCreatedAt());
                        contentTextView.setText(postDetails.getContent());
                        Picasso.get().load(postDetails.getImageUrl()).into(imageView);

                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to get post details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostDetails> call, Throwable t) {
                // Handle failure case
                Toast.makeText(requireContext(), "Failed to fetch post details: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}

