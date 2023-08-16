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

public class CustomAdapterPost extends ArrayAdapter<CustomDataTitleImage> {
    private CustomDataTitleImage[] data;
    private final LayoutInflater inflater;


    public CustomAdapterPost(Context context, List<CustomDataTitleImage> data) {
        super(context, R.layout.list_item_layout, data);
        this.data = data.toArray(new CustomDataTitleImage[0]);
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

        viewHolder.titleTextView.setText(data[position].getTitle());
        viewHolder.dateTextView.setText(data[position].getCreatedAt());

        // Use Picasso to load the image from the URL into the ImageView
        Picasso.get().load(data[position].getImageUrl()).into(viewHolder.imageView);

        return view;
    }

    public void setData(List<CustomDataTitleImage> newData) {
        data = newData.toArray(new CustomDataTitleImage[0]);
        notifyDataSetChanged();
    }
}

