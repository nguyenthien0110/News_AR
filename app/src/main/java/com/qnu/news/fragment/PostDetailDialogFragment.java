package com.qnu.news.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import com.qnu.news.R;
import com.qnu.news.config.ApiServiceClient;
import com.qnu.news.model.PostDetails;
import com.qnu.news.service.ApiService;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostDetailDialogFragment extends DialogFragment {
    private static final String ARG_POST_ID = "post_id";

    public static PostDetailDialogFragment newInstance(String postId) {
        PostDetailDialogFragment fragment = new PostDetailDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_POST_ID, postId);
        fragment.setArguments(args);
        return fragment;
    }

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_post_detail_dialog, container, false);
        String postId = getArguments().getString(ARG_POST_ID);

        ApiService apiService = ApiServiceClient.getApiService();
        Call<PostDetails> call = apiService.getPostDetails(postId);
        call.enqueue(new Callback<PostDetails>() {
            @Override
            public void onResponse(Call<PostDetails> call, Response<PostDetails> response) {
                if (response.isSuccessful()) {
                    PostDetails postDetails = response.body();
                    if (postDetails != null) {
                        // Display the post details in the dialog
                        TextView titleTextView = view.findViewById(R.id.titleTextView);
                        TextView contentTextView = view.findViewById(R.id.contentTextView);
                        TextView dateTextView = view.findViewById(R.id.dateTextView);
                        ImageView imageView = view.findViewById(R.id.imageView);
                        ImageButton btnCancel = view.findViewById(R.id.btnCancel);

                        titleTextView.setText(postDetails.getTitle());
                        contentTextView.setText(postDetails.getContent());
                        dateTextView.setText(postDetails.getCreatedAt());
                        Picasso.get().load(postDetails.getImageUrl()).into(imageView);

                        btnCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dismiss();
                            }
                        });
                    }
                } else {
                    // Handle error case
                    Toast.makeText(requireContext(), "Failed to get post details", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<PostDetails> call, Throwable t) {
                // Handle failure case
                Toast.makeText(requireContext(), "Failed to fetch post details: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }
}

