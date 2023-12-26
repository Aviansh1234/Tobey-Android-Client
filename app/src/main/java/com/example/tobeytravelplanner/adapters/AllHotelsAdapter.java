package com.example.tobeytravelplanner.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tobeytravelplanner.DetailedHotelInfo;
import com.example.tobeytravelplanner.R;
import com.example.tobeytravelplanner.objects.HotelInfo;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AllHotelsAdapter extends RecyclerView.Adapter<AllHotelsAdapter.HotelHolder> {

    ArrayList<HotelInfo>hotels;
    ArrayList<String> customDescriptions;
    ArrayList<String> hotelPrices;
    AppCompatActivity context;

    public AllHotelsAdapter(ArrayList<HotelInfo> hotels, ArrayList<String> customDescriptions, ArrayList<String> hotelPrices, AppCompatActivity context) {
        this.hotels = hotels;
        this.customDescriptions = customDescriptions;
        this.hotelPrices = hotelPrices;
        this.context = context;
    }

    @NonNull
    @Override
    public HotelHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.hotel_view, parent, false);
        return new HotelHolder(view);
    }

    @Override
    public int getItemCount() {
        System.out.println(hotels.size());
        return hotels.size();
    }

    @Override
    public void onBindViewHolder(@NonNull HotelHolder holder, int position) {
        Picasso.with(context).load(hotels.get(position).getImages().get(0))
                .into(holder.image);
        holder.desc.setText(customDescriptions.get(position));
        holder.name.setText(hotels.get(position).getHotelName());
        holder.price.setText(hotelPrices.get(position));
        holder.star.setText(hotels.get(position).getHotelRating()+" ★");
        holder.hotelRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailedHotelInfo.class);
                HotelInfo curr = hotels.get(holder.getAdapterPosition());
                intent.putExtra("starRating", curr.getHotelRating());
                intent.putExtra("address", curr.getAddress());
                intent.putExtra("name", curr.getHotelName());
                intent.putExtra("description", customDescriptions.get(holder.getAdapterPosition()));
                intent.putExtra("phoneNumber", curr.getPhoneNumber());
                String[] imagelinkarray = new String[curr.getImages().size()];
                for (int i = 0;i<curr.getImages().size();i++){
                    imagelinkarray[i]=curr.getImages().get(i);
                }
                intent.putExtra("imageLinks", imagelinkarray);
                intent.putExtra("price", hotelPrices.get(holder.getAdapterPosition()));
                context.startActivity(intent);
            }
        });
    }

    public class HotelHolder extends RecyclerView.ViewHolder{

        ImageView image;
        CardView hotelRoot;
        TextView desc, name, price, star;

        public HotelHolder(@NonNull View itemView) {
            super(itemView);

            image = itemView.findViewById(R.id.hotelImage);
            desc = itemView.findViewById(R.id.hotelDesc);
            price = itemView.findViewById(R.id.hotelPrice);
            name = itemView.findViewById(R.id.hotelName);
            star = itemView.findViewById(R.id.starRating);
            hotelRoot = itemView.findViewById(R.id.hotelRoot);
        }
    }
}
