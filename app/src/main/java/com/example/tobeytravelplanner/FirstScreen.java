package com.example.tobeytravelplanner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.tobeytravelplanner.functionalClasses.FirebaseHandler;
import com.example.tobeytravelplanner.functionalClasses.ServerHandler;

import java.io.IOException;

public class FirstScreen extends AppCompatActivity {

    Button showPrevSession, createNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_screen);

        showPrevSession = findViewById(R.id.prevSessions);
        createNew = findViewById(R.id.newSession);

        showPrevSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(FirstScreen.this, HomeScreen.class));
            }
        });
        createNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ServerHandler serverHandler = new ServerHandler();
                String[] temp = new String[1];
                temp[0]="";
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(FirstScreen.this, MainActivity.class);
                        intent.putExtra("session", temp[0]);
                        startActivity(intent);
                    }
                };
                try {
                    serverHandler.createSession(createNew, temp, r);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}