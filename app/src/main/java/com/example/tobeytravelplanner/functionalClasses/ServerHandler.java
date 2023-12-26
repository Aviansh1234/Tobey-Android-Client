package com.example.tobeytravelplanner.functionalClasses;

import android.widget.Button;

import com.example.tobeytravelplanner.objects.Config;
import com.example.tobeytravelplanner.objects.HotelInfo;
import com.example.tobeytravelplanner.objects.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServerHandler {
    String baseUrl;
    Retrofit retrofit;

    public ServerHandler() {
        baseUrl= Config.baseUrl;
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build();
    }

    public List<HotelInfo> getHotelInfos(String ids) throws IOException {
        ServerApi serverApi = retrofit.create(ServerApi.class);
        Call<List<HotelInfo>> call = serverApi.getHotelInfo(ids);
        Response<List<HotelInfo>> response = call.execute();
        if (response.isSuccessful())
            return response.body();
        return new ArrayList<HotelInfo>();
    }

    public void updateApi(String sessionId) {
        ServerApi serverApi = retrofit.create(ServerApi.class);
        Call<String> call = serverApi.updateNext(sessionId, FirebaseAuth.getInstance().getUid());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {

            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    public void createSession(Button enable) throws IOException {
        ServerApi serverApi = retrofit.create(ServerApi.class);
        Call<String> call = serverApi.createNewSession(FirebaseAuth.getInstance().getUid());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> res) {
                FirebaseHandler handler = new FirebaseHandler();
                System.out.println(res.body());
                handler.addSessionToList(res.body());
                enable.setEnabled(true);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    public void addMessage(String sessionId, Message msg){
        ServerApi serverApi = retrofit.create(ServerApi.class);
        Call<String>call = serverApi.addMessage(FirebaseAuth.getInstance().getUid(), sessionId, msg.getMessageContent());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                updateApi(sessionId);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}