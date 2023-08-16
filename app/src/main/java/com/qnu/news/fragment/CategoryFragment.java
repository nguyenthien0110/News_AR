package com.qnu.news.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qnu.news.R;
import com.qnu.news.config.ApiServiceClient;
import com.qnu.news.custom.CustomAdapterCategory;
import com.qnu.news.model.Category;
import com.qnu.news.response.ApiCategoryDataResponse;
import com.qnu.news.service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        ListView listView = view.findViewById(R.id.listViewCategory);
        ApiService apiService = ApiServiceClient.getApiService();

        // Call the API to get the category
        Call<ApiCategoryDataResponse> call = apiService.getAllCategory();
        call.enqueue(new Callback<ApiCategoryDataResponse>() {
            @Override
            public void onResponse(Call<ApiCategoryDataResponse> call, Response<ApiCategoryDataResponse> response) {
                if (response.isSuccessful()) {
                    ApiCategoryDataResponse apiCategoryDataResponse = response.body();
                    if (apiCategoryDataResponse != null) {
                        List<Category> categoryList = apiCategoryDataResponse.getData();

                        // Set up data on adapter
                        CustomAdapterCategory adapter = new CustomAdapterCategory(requireContext(), categoryList);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Category selectedCategory = categoryList.get(position);
                                String categoryId = selectedCategory.getId();
                                String categoryName = selectedCategory.getName(); // Get the name of the selected category
                                showCategoryPosts(categoryId, categoryName);
                            }
                        });
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to get categories", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiCategoryDataResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed to fetch categories: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        return view;
    }

    private void showCategoryPosts(String categoryId, String categoryName) {
        CategoryPostsFragment categoryPostsFragment = CategoryPostsFragment.newInstance(categoryId, categoryName);
        getParentFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, categoryPostsFragment)
                .addToBackStack(null)
                .commit();
    }
}