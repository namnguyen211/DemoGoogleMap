package com.example.namnguyen.demogooglemap.apis;

import com.example.namnguyen.demogooglemap.models.FourSquareResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Nam Nguyen on 04/07/2016.
 */
public interface FourSquareApi {

    @GET("venues/search")
    Call<FourSquareResponse> searchVenue(@Query("v") String ver,@Query("ll") String longLat,@Query("query") String query);
}