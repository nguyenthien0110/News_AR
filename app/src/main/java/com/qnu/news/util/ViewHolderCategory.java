package com.qnu.news.util;

import android.view.View;
import android.widget.TextView;
public class ViewHolderCategory {
    public final TextView categoryNameTextView;

    public ViewHolderCategory(View view) {
        categoryNameTextView = view.findViewById(android.R.id.text1);
    }
}
