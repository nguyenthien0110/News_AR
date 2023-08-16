package com.qnu.news.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;

import com.qnu.news.model.Category;
import com.qnu.news.util.ViewHolderCategory;

import java.util.List;

public class CustomAdapterCategory extends ArrayAdapter<Category> {

    private final LayoutInflater inflater;

    public CustomAdapterCategory(Context context, List<Category> categoryList) {
        super(context, 0, categoryList);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolderCategory viewHolder;

        if (view == null) {
            view = inflater.inflate(android.R.layout.simple_list_item_1, parent, false);
            viewHolder = new ViewHolderCategory(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderCategory) view.getTag();
        }

        Category category = getItem(position);
        if (category != null) {
            viewHolder.categoryNameTextView.setText(category.getName());
        }

        return view;
    }
}
