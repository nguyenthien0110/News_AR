package com.qnu.news.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.qnu.news.R;
import com.qnu.news.service.SessionManager;

public class PersonFragment extends Fragment {

    private TextView usernameTextView;
    private TextView passwordTextView;
    private ImageButton logoutButton;
    private SessionManager sessionManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_person, container, false);

        usernameTextView = view.findViewById(R.id.usernameTextView);
        passwordTextView = view.findViewById(R.id.passwordTextView);
        logoutButton = view.findViewById(R.id.logoutButton);

        // Initialize SessionManager
        sessionManager = new SessionManager(requireContext());

        // Check if the user is logged in
        if (sessionManager.isLoggedIn()) {
            // Show user information
            showUserInfo(sessionManager.getLoggedInUsername(), sessionManager.getLoggedInPassword());
            // Show the Logout button (ImageButton)
            logoutButton.setVisibility(View.VISIBLE);
            // Set click listener for Logout button
            logoutButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    logout();
                }
            });
        } else {
            // Show login AlertDialog
            showLoginAlertDialog();
        }

        return view;
    }

    // Method to show user information in TextViews
    private void showUserInfo(String username, String password) {
        usernameTextView.setText("Username: " + username);
        passwordTextView.setText("Password: " + getMaskedPassword(password));
    }

    // Method to mask the password with *
    private String getMaskedPassword(String password) {
        StringBuilder maskedPassword = new StringBuilder();
        for (int i = 0; i < password.length(); i++) {
            maskedPassword.append("*");
        }
        return maskedPassword.toString();
    }

    // Method to handle logout
    private void logout() {
        // Clear the session and navigate back to LoginFragment
        sessionManager.logout();
        requireActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment_container, new LoginFragment())
                .commit();
    }

    // Method to show login AlertDialog
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

