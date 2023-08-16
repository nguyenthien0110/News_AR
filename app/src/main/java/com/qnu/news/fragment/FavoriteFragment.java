package com.qnu.news.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
import com.qnu.news.service.SessionManager;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FavoriteFragment extends Fragment {

    private SessionManager sessionManager;
    private SearchResultsAdapter adapter;
    private List<CustomDataTitleImage> favoriteList;

    private ListView listViewFavorite;

    @SuppressLint({"MissingInflatedId", "LocalSuppress"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite, container, false);
        TextView textView = view.findViewById(R.id.textView);
        listViewFavorite = view.findViewById(R.id.listViewFavorite);

        favoriteList = new ArrayList<>();
        adapter = new SearchResultsAdapter(requireContext(), favoriteList);
        listViewFavorite.setAdapter(adapter);

        sessionManager = new SessionManager(requireContext());

        if (sessionManager.isLoggedIn()){
            getPostsByUsernameForFavorite(sessionManager.getLoggedInUsername());
        }
        else {
            showLoginAlertDialog();
        }

        listViewFavorite.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                CustomDataTitleImage clickedPost = favoriteList.get(position);
                String postId = clickedPost.getId();

                // Create a new instance of PostDetailFragment and pass the postId as an argument
                PostDetailFragment postDetailFragment = PostDetailFragment.newInstance(postId);

                // Replace the current fragment (CategoryPostsFragment) with the PostDetailFragment
                FragmentTransaction transaction = requireActivity().getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.fragment_container, postDetailFragment);
                transaction.addToBackStack(null);
                transaction.commit();
            }
        });
        return view;
    }

    private void getPostsByUsernameForFavorite(String username) {
        ApiService apiService = ApiServiceClient.getApiService();

        Call<ApiPostDataResponse> call = apiService.getPostByUsernameForCollection(username);
        call.enqueue(new Callback<ApiPostDataResponse>() {
            @Override
            public void onResponse(Call<ApiPostDataResponse> call, Response<ApiPostDataResponse> response) {
                if (response.isSuccessful()) {
                    ApiPostDataResponse apiDataResponse = response.body();
                    if (apiDataResponse != null) {
                        List<CustomDataTitleImage> customDataList = apiDataResponse.getData();
                        favoriteList.clear();
                        favoriteList.addAll(customDataList);
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

    private void showLoginAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireContext());
        builder.setTitle("Login Required");
        builder.setMessage("You need to log in to view this content.");

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Add LoginFragment to fragment_container
                Fragment loginFragment = new LoginFragment();
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, loginFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });

        builder.setCancelable(false);
        builder.show();
    }

}
