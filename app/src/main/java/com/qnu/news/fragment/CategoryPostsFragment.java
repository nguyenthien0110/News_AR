package com.qnu.news.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.qnu.news.R;
import com.qnu.news.config.ApiServiceClient;
import com.qnu.news.custom.CustomAdapterPost;
import com.qnu.news.custom.CustomDataTitleImage;
import com.qnu.news.response.ApiPostDataResponse;
import com.qnu.news.service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryPostsFragment extends Fragment {

    private static final String ARG_CATEGORY_ID = "category_id";
    private static final String ARG_CATEGORY_NAME = "category_name";

    public static CategoryPostsFragment newInstance(String categoryId, String categoryName) {
        CategoryPostsFragment fragment = new CategoryPostsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY_ID, categoryId);
        args.putString(ARG_CATEGORY_NAME, categoryName);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_posts, container, false);

        ListView listView = view.findViewById(R.id.listViewCategoryPosts);
        TextView categoryNameTextView = view.findViewById(R.id.categoryNameTextView);

        Bundle args = getArguments();
        if (args != null && args.containsKey(ARG_CATEGORY_ID) && args.containsKey(ARG_CATEGORY_NAME)) {
            String categoryId = args.getString(ARG_CATEGORY_ID);
            String categoryName = args.getString(ARG_CATEGORY_NAME);
            categoryNameTextView.setText("Media Trends > " + categoryName);

            fetchPostsByCategory(categoryId, listView);
        }

        return view;
    }

    private void fetchPostsByCategory(String categoryId, ListView listView) {
        ApiService apiService = ApiServiceClient.getApiService();
        Call<ApiPostDataResponse> call = apiService.getPostByCategory(categoryId);
        call.enqueue(new Callback<ApiPostDataResponse>() {
            @Override
            public void onResponse(Call<ApiPostDataResponse> call, Response<ApiPostDataResponse> response) {
                if (response.isSuccessful()) {
                    ApiPostDataResponse apiPostDataResponse = response.body();
                    if (apiPostDataResponse != null) {
                        List<CustomDataTitleImage> postList = apiPostDataResponse.getData();

                        // Set up the custom adapter
                        CustomAdapterPost adapter = new CustomAdapterPost(requireContext(), postList);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                CustomDataTitleImage clickedPost = postList.get(position);
                                String postId = clickedPost.getId();

                                // Create a new instance of PostDetailFragment
                                PostDetailFragment postDetailFragment = PostDetailFragment.newInstance(postId);

                                //
                                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                                transaction.replace(R.id.fragment_container, postDetailFragment);
                                transaction.addToBackStack(null);
                                transaction.commit();
                            }
                        });
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to get posts", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiPostDataResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed to fetch posts: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}
