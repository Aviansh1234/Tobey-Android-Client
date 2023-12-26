package com.example.tobeytravelplanner.functionalClasses;

import com.example.tobeytravelplanner.objects.HotelInfo;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ServerApi {
    @GET("getHotelInfo")
    Call<List<HotelInfo>> getHotelInfo(@Query("hotelId") String hotelId);

    @GET("updateForNext")
    Call<String>updateNext(@Query("sessionId")String sessionId, @Query("userId")String userId);

    @GET("createNewSession")
    Call<String> createNewSession(@Query("userId")String userId);

    @GET("addMessage")
    Call<String> addMessage(@Query("userId")String userId, @Query("sessionId")String sessionId, @Query("message")String message);
}
