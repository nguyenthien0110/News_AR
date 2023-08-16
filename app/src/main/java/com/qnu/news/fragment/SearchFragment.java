package com.qnu.news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.qnu.news.R;
import com.qnu.news.config.ApiServiceClient;
import com.qnu.news.custom.CustomDataTitleImage;
import com.qnu.news.custom.SearchResultsAdapter;
import com.qnu.news.response.ApiPostDataResponse;
import com.qnu.news.service.ApiService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {

    private EditText editTextSearch;
    private ImageView imageViewSearch;
    private ListView listViewSearchResults;
    private SearchResultsAdapter adapter;
    private List<CustomDataTitleImage> searchDataList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        editTextSearch = view.findViewById(R.id.editTextSearch);
        imageViewSearch = view.findViewById(R.id.imageViewSearch);
        listViewSearchResults = view.findViewById(R.id.listViewSearchResults);

        searchDataList = new ArrayList<>();
        adapter = new SearchResultsAdapter(requireContext(), searchDataList);
        listViewSearchResults.setAdapter(adapter);

        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = editTextSearch.getText().toString().trim();
                if (!keyword.isEmpty()) {
                    searchPostsByTitle(keyword);
                } else {
                    Toast.makeText(requireContext(), "Please enter a search keyword", Toast.LENGTH_SHORT).show();
                }
            }
        });

        imageViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch();
            }
        });

        // Thêm sự kiện onEditorAction cho EditText
        editTextSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });

        listViewSearchResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomDataTitleImage clickedPost = searchDataList.get(position);
                String postId = clickedPost.getId();


                PostDetailFragment postDetailFragment = PostDetailFragment.newInstance(postId);


                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, postDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });

        return view;
    }

    private void searchPostsByTitle(String keyword) {
        ApiService apiService = ApiServiceClient.getApiService();

        Call<ApiPostDataResponse> call = apiService.search(keyword);
        call.enqueue(new Callback<ApiPostDataResponse>() {
            @Override
            public void onResponse(Call<ApiPostDataResponse> call, Response<ApiPostDataResponse> response) {
                if (response.isSuccessful()) {
                    ApiPostDataResponse apiDataResponse = response.body();
                    if (apiDataResponse != null) {
                        List<CustomDataTitleImage> customDataList = apiDataResponse.getData();
                        searchDataList.clear();
                        searchDataList.addAll(customDataList);
                        adapter.notifyDataSetChanged();
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
    }

    private void performSearch() {
        String keyword = editTextSearch.getText().toString().trim();
        if (!keyword.isEmpty()) {
            searchPostsByTitle(keyword);
            //ẩn bàn phím đi
            InputMethodManager imm = (InputMethodManager) requireContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(editTextSearch.getWindowToken(), 0);
        } else {
            Toast.makeText(requireContext(), "Please enter a search keyword", Toast.LENGTH_SHORT).show();
        }
    }

}
