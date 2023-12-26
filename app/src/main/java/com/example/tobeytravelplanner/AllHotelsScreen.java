package com.example.tobeytravelplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.tobeytravelplanner.adapters.AllHotelsAdapter;
import com.example.tobeytravelplanner.functionalClasses.FirebaseHandler;
import com.example.tobeytravelplanner.functionalClasses.ServerHandler;
import com.example.tobeytravelplanner.objects.HotelInfo;

import java.io.IOException;
import java.util.ArrayList;

public class AllHotelsScreen extends AppCompatActivity {

    RecyclerView recyclerView;
    AllHotelsAdapter adapter;
    ImageView back;
    ArrayList<String> customDescs = new ArrayList<>();
    String[] tempIdList = new String[1];
    FirebaseHandler firebaseHandler = new FirebaseHandler();
    ArrayList<HotelInfo> hotelInfos = new ArrayList<>();
    ArrayList<String>prices = new ArrayList<>();
    Dialog dialog;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_hotels_screen);

        recyclerView = findViewById(R.id.hotels);
        back = findViewById(R.id.allBack);
        tempIdList[0]="";
        adapter = new AllHotelsAdapter(hotelInfos, customDescs, prices, this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dialog = new Dialog(this);
        dialog.setContentView(R.layout.loading_animation);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        Runnable updateHotelInfos = new Runnable() {
            @Override
            public void run() {
                ServerHandler serverHandler = new ServerHandler();
                Runnable runnable1 = new Runnable() {
                    @Override
                    public void run() {
                        try {
                            hotelInfos.addAll((ArrayList<HotelInfo>) serverHandler.getHotelInfos(tempIdList[0]));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                };
                Thread thread = new Thread(runnable1);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                adapter.notifyDataSetChanged();
                dialog.dismiss();
            }
        };
        firebaseHandler.getHotelsFromSession(getIntent().getStringExtra("session"),updateHotelInfos, tempIdList, customDescs, prices);
    }
}