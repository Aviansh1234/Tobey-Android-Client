package com.example.tobeytravelplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.StyleSpan;
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
    TextView name, star, price, desc, phonenumber;
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
        String stars = "";
        for (int i = 0; i < getIntent().getIntExtra("starRating", 2); i++) {
            stars += "★";
        }
        SpannableStringBuilder rating = new SpannableStringBuilder("Rating: " + stars);
        rating.setSpan(new StyleSpan(Typeface.BOLD), 0, 7, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        rating.setSpan(new ForegroundColorSpan(getColor(R.color.orange)), 8, 8+stars.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        star.setText(rating);
        SpannableStringBuilder pricestr = new SpannableStringBuilder("Price: " + getIntent().getStringExtra("price"));
        pricestr.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        price.setText(pricestr);
        desc.setText(getIntent().getStringExtra("description"));
        SpannableStringBuilder phonestr = new SpannableStringBuilder("Phone number: " + getIntent().getStringExtra("phoneNumber"));
        phonestr.setSpan(new StyleSpan(Typeface.BOLD), 0, 13, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        phonenumber.setText(phonestr);
        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(DetailedHotelInfo.this, "Hotel Booked", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(DetailedHotelInfo.this, HomeScreen.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
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