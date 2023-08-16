package com.qnu.news.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qnu.news.R;
import com.qnu.news.config.ApiServiceClient;
import com.qnu.news.request.LoginRequest;
import com.qnu.news.response.ApiResponse;
import com.qnu.news.response.LoginResponse;
import com.qnu.news.service.ApiService;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpFragment extends Fragment {
    private EditText editTextUsername;
    private EditText editTextPassword;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_signup, container, false);

        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        Button buttonSignUp = view.findViewById(R.id.buttonSignUp);
        Button comeBackLogin = view.findViewById(R.id.buttonComeBackLogin);


        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();

                // Check if username and password are not empty
                if (!username.isEmpty() && !password.isEmpty()) {
                    // Create a LoginRequest object with the entered username and password
                    LoginRequest loginRequest = new LoginRequest(username, password);

                    // Call the login API
                    signUp(loginRequest);
                } else {
                    Toast.makeText(requireContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        comeBackLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new LoginFragment())
                        .commit();
            }
        });

        return view;
    }

    private void signUp(LoginRequest loginRequest) {
        ApiService apiService = ApiServiceClient.getApiService();

        Call<ApiResponse> call = apiService.signup(loginRequest);
        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                if (response.isSuccessful()) {
                    ApiResponse signupResponse = response.body();
                    if (signupResponse != null && signupResponse.getResult().equals("Save success")) {

                        // Show a toast message to indicate successful login
                        Toast.makeText(requireContext(), "Register successful", Toast.LENGTH_SHORT).show();

                        // Navigate to PersonFragment to display the user information
                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new LoginFragment())
                                .commit();
                    } else {
                        // Show a toast message for unsuccessful login
                        Toast.makeText(requireContext(), "Register failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Show a toast message for unsuccessful login
                    Toast.makeText(requireContext(), "Register failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                // Show a toast message for login error
                Toast.makeText(requireContext(), "Register error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
