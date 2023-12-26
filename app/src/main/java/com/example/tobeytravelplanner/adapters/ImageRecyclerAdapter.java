package com.example.tobeytravelplanner.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tobeytravelplanner.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImageHolder> {

    String[] links;
    Context context;

    public ImageRecyclerAdapter(String[] links, Context context) {
        this.links = links;
        this.context = context;
    }

    @NonNull
    @Override
    public ImageHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.image, parent, false);
        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageHolder holder, int position) {
        Picasso.with(context).load(links[position])
                .into(holder.imageview);
    }

    @Override
    public int getItemCount() {
        return links.length;
    }

    public class ImageHolder extends RecyclerView.ViewHolder{

        ImageView imageview;

        public ImageHolder(@NonNull View itemView) {
            super(itemView);
            imageview = itemView.findViewById(R.id.detailedImage);
        }
    }
}
