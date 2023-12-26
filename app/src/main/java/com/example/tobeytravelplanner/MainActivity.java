package com.example.tobeytravelplanner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tobeytravelplanner.adapters.MessageAdapter;
import com.example.tobeytravelplanner.functionalClasses.FirebaseHandler;
import com.example.tobeytravelplanner.functionalClasses.ServerHandler;
import com.example.tobeytravelplanner.objects.HotelInfo;
import com.example.tobeytravelplanner.objects.Message;
import com.intuit.sdp.BuildConfig;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    public ImageView send, mic;
    public EditText message;
    public ImageView back;
    public RecyclerView messages;
    public ArrayList<Message> messageArrayList = new ArrayList<Message>();
    public MessageAdapter adapter = new MessageAdapter(messageArrayList, this);
    public ServerHandler serverHandler;
    public FirebaseHandler firebaseHandler;
    public TextView showMore;
    final int SPEECH_REQUEST_CODE = 1;
    String sessionId;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        send = findViewById(R.id.send);
        back = findViewById(R.id.mainBack);
        mic = findViewById(R.id.mic);
        showMore = findViewById(R.id.showMore);
        message = findViewById(R.id.message);
        messages = findViewById(R.id.messages);
        firebaseHandler = new FirebaseHandler();
        serverHandler = new ServerHandler();
        sessionId = getIntent().getStringExtra("session");
        firebaseHandler.updateMessages(this, sessionId, messageArrayList);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (message.getText().toString().trim().isEmpty()) {
                    Toast.makeText(MainActivity.this, "You must enter a message", Toast.LENGTH_SHORT).show();
                    return;
                }
                Message curr = new Message();
                curr.setMessageContent(message.getText().toString());
                curr.setAuthor("user");
                serverHandler.addMessage(sessionId, curr);
                message.setText("");
            }
        });
        mic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Convert speech to text");
                startActivityForResult(intent, SPEECH_REQUEST_CODE);
            }
        });
        messages.setAdapter(adapter);
        messages.setLayoutManager(new LinearLayoutManager(this));
    }

    public void completedChat() {
        send.setVisibility(View.GONE);
        message.setVisibility(View.GONE);
        mic.setVisibility(View.GONE);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                showMore.setVisibility(View.VISIBLE);
                showMore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(MainActivity.this, AllHotelsScreen.class);
                        intent.putExtra("session", sessionId);
                        startActivity(intent);
                    }
                });
            }
        };
        firebaseHandler.hotelSessionExists(sessionId, runnable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SPEECH_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                if (data != null) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    message.setText(Objects.requireNonNull(result).get(0));
                }
            }
        }
    }
}