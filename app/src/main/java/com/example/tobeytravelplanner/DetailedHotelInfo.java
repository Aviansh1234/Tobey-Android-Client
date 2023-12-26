package com.example.tobeytravelplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tobeytravelplanner.adapters.ImageRecyclerAdapter;

import java.util.ArrayList;

public class DetailedHotelInfo extends AppCompatActivity {

    RecyclerView recyclerView;
    ImageRecyclerAdapter adapter;
    TextView name, star,price,desc,phonenumber;
    Button book;
    ImageView back;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed_hotel_info);

        recyclerView = findViewById(R.id.imageRecycler);
        name = findViewById(R.id.detailsName);
        star = findViewById(R.id.detailsStarRating);
        price = findViewById(R.id.detailsPrice);
        back = findViewById(R.id.detailsBack);
        desc = findViewById(R.id.detailsCustomDesc);
        phonenumber = findViewById(R.id.detailsPhone);
        book = findViewById(R.id.book);
        adapter = new ImageRecyclerAdapter(getIntent().getStringArrayExtra("imageLinks"), this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);
        name.setText(getIntent().getStringExtra("name"));
        star.setText(getIntent().getIntExtra("starRating", 2)+" ★");
        price.setText(getIntent().getStringExtra("price"));
        desc.setText(getIntent().getStringExtra("description"));
        phonenumber.setText(getIntent().getStringExtra("phoneNumber"));
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailedHotelInfo.this, "Hotel Booked", Toast.LENGTH_SHORT).show();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}