package com.loftschool.moneytracker2.api;

import com.loftschool.moneytracker2.AuthResult;
import com.loftschool.moneytracker2.Item;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {

    @GET("items")
    Call<List<Item>> items(@Query ("type") String type);
    @POST("items/add")
    Call<AddResult> add(@Query("name") String name,
                        @Query("price") int price,
                        @Query("type") String type);
    @GET("auth")
    Call<AuthResult> auth (@Query("social_user_id") String socialUserId);

    @POST("items/remove")
    Call<RemoveResult> remove(@Query("id") int id);
}
