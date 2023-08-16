package com.qnu.news.custom;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.qnu.news.R;
import com.qnu.news.util.ViewHolderPost;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SearchResultsAdapter extends ArrayAdapter<CustomDataTitleImage> {
    private List<CustomDataTitleImage> searchDataList;
    private LayoutInflater inflater;

    public SearchResultsAdapter(Context context, List<CustomDataTitleImage> searchDataList) {
        super(context, 0, searchDataList);
        this.searchDataList = searchDataList;
        this.inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = convertView;
        ViewHolderPost viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.list_item_layout, parent, false);
            viewHolder = new ViewHolderPost(view);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolderPost) view.getTag();
        }

        CustomDataTitleImage data = searchDataList.get(position);
        viewHolder.titleTextView.setText(data.getTitle());
        viewHolder.dateTextView.setText(data.getCreatedAt());

        Picasso.get().load(data.getImageUrl()).into(viewHolder.imageView);

        return view;
    }
}

