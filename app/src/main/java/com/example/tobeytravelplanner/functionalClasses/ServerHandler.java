package com.example.tobeytravelplanner.functionalClasses;

import android.widget.Button;

import com.example.tobeytravelplanner.MainActivity;
import com.example.tobeytravelplanner.objects.Config;
import com.example.tobeytravelplanner.objects.HotelInfo;
import com.example.tobeytravelplanner.objects.Message;
import com.google.firebase.auth.FirebaseAuth;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
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
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .callTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .client(okHttpClient)
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

    public void updateApi(String sessionId, MainActivity mainActivity) {
        ServerApi serverApi = retrofit.create(ServerApi.class);
        Call<String> call = serverApi.updateNext(sessionId, FirebaseAuth.getInstance().getUid());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.body().equals("show loading")){
                    mainActivity.showLoading();
                }
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
                handler.addSessionToList(res.body());
                enable.setEnabled(true);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    public void createSession(Button enable, String[] temp, Runnable runnable) throws IOException {
        ServerApi serverApi = retrofit.create(ServerApi.class);
        Call<String> call = serverApi.createNewSession(FirebaseAuth.getInstance().getUid());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> res) {
                FirebaseHandler handler = new FirebaseHandler();
                handler.addSessionToList(res.body());
                enable.setEnabled(true);
                temp[0] = res.body();
                runnable.run();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
    public void addMessage(String sessionId, Message msg, MainActivity mainActivity){
        ServerApi serverApi = retrofit.create(ServerApi.class);
        Call<String>call = serverApi.addMessage(FirebaseAuth.getInstance().getUid(), sessionId, msg.getMessageContent());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                updateApi(sessionId, mainActivity);
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }
}