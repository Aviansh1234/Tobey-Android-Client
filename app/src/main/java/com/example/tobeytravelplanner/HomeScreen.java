package com.example.tobeytravelplanner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.tobeytravelplanner.adapters.conversationsAdapter;
import com.example.tobeytravelplanner.functionalClasses.FirebaseHandler;
import com.example.tobeytravelplanner.functionalClasses.ServerHandler;

import java.io.IOException;
import java.util.ArrayList;

public class HomeScreen extends AppCompatActivity {

    RecyclerView recyclerView;
    Button addSession;
    ImageView logout;
    ArrayList<String>ids = new ArrayList<>();
    ArrayList<String>locations = new ArrayList<>();
    ArrayList<String>departTimes = new ArrayList<>();
    conversationsAdapter adapter = new conversationsAdapter(ids, locations, departTimes, this);
    FirebaseHandler firebaseHandler = new FirebaseHandler();
    ServerHandler serverHandler = new ServerHandler();

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        recyclerView = findViewById(R.id.conversationRecycler);
        addSession = findViewById(R.id.addSession);
        logout = findViewById(R.id.logout);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        firebaseHandler.updateConversations(adapter, ids, locations, departTimes);
        addSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    addSession.setEnabled(false);
                    serverHandler.createSession(addSession);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseHandler.logout();
                startActivity(new Intent(HomeScreen.this, SplashScreen.class));
                finish();
            }
        });
    }
}