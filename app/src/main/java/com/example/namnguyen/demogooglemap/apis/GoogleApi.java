package com.example.namnguyen.demogooglemap.apis;

import com.example.namnguyen.demogooglemap.models.google.GoogleResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Nam Nguyen on 16/07/2016.
 */
public interface GoogleApi {

    @GET("nearbysearch/json")
    Call<GoogleResponse> searchPlace (@Query("location")String location,@Query("name")String name,@Query("key")String googleServerKey);


}
