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
import com.qnu.news.response.LoginResponse;
import com.qnu.news.service.ApiService;
import com.qnu.news.service.SessionManager;
import com.qnu.news.util.LoginSuccessListener;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginFragment extends Fragment {

    private EditText editTextUsername;
    private EditText editTextPassword;
    private SessionManager sessionManager;

    private LoginSuccessListener loginSuccessListener;

    public void setLoginSuccessListener(LoginSuccessListener listener) {
        this.loginSuccessListener = listener;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        Button buttonLogin = view.findViewById(R.id.buttonLogin);
        Button buttonSignUp = view.findViewById(R.id.buttonSignUp);

        sessionManager = new SessionManager(requireContext());

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = editTextUsername.getText().toString().trim();
                String password = editTextPassword.getText().toString().trim();


                if (!username.isEmpty() && !password.isEmpty()) {

                    LoginRequest loginRequest = new LoginRequest(username, password);


                    login(loginRequest);
                } else {
                    Toast.makeText(requireContext(), "Please enter username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });

        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requireActivity().getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container, new SignUpFragment())
                        .commit();
            }
        });

        return view;
    }

    private void login(LoginRequest loginRequest) {
        ApiService apiService = ApiServiceClient.getApiService();

        Call<LoginResponse> call = apiService.login(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful()) {
                    LoginResponse loginResponse = response.body();
                    if (loginResponse != null && loginResponse.getResult().equals("Login success")) {

                        sessionManager.saveLoginDetails(loginRequest.getUsername(), loginRequest.getPassword());


                        Toast.makeText(requireContext(), "Login successful", Toast.LENGTH_SHORT).show();


                        requireActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.fragment_container, new PersonFragment())
                                .commit();
                    } else {
                        Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(requireContext(), "Login failed", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(requireContext(), "Login error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}


