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
import com.qnu.news.custom.CustomAdapterPost;
import com.qnu.news.custom.CustomDataTitleImage;
import com.qnu.news.response.ApiPostDataResponse;
import com.qnu.news.service.ApiService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ListView listView = view.findViewById(R.id.listView);
        ApiService apiService = ApiServiceClient.getApiService();

        // Call the API to get the data
        Call<ApiPostDataResponse> call = apiService.getAllPosts();
        call.enqueue(new Callback<ApiPostDataResponse>() {
            @Override
            public void onResponse(Call<ApiPostDataResponse> call, Response<ApiPostDataResponse> response) {
                if (response.isSuccessful()) {
                    ApiPostDataResponse apiDataResponse = response.body();
                    if (apiDataResponse != null) {
                        List<CustomDataTitleImage> customDataList = apiDataResponse.getData();

                        // Set up data
                        CustomAdapterPost adapter = new CustomAdapterPost(requireContext(), customDataList);
                        listView.setAdapter(adapter);

                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                PostDetailDialogFragment dialogFragment = PostDetailDialogFragment.newInstance(customDataList.get(position).getId());
                                dialogFragment.show(requireActivity().getSupportFragmentManager(), "post_detail_dialog");
                            }
                        });
                    }
                } else {
                    Toast.makeText(requireContext(), "Failed to get data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiPostDataResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Failed to fetch data: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}