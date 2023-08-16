package com.qnu.news.util;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qnu.news.R;

public class ViewHolderPost {
    public final TextView titleTextView;
    public final TextView dateTextView;
    public final ImageView imageView;

    public ViewHolderPost(View view) {
        titleTextView = view.findViewById(R.id.titleTextView);
        dateTextView = view.findViewById(R.id.dateTextView); // Initialize the dateTextView
        imageView = view.findViewById(R.id.imageView);
    }
}
