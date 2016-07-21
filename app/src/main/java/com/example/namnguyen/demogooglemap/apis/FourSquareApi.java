package com.example.namnguyen.demogooglemap.apis;

import com.example.namnguyen.demogooglemap.models.foursquare.FoursquareResponse;
import com.example.namnguyen.demogooglemap.models.foursquare.photo.PhotoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Nam Nguyen on 04/07/2016.
 */
public interface FourSquareApi {

    @GET("venues/search")
    Call<FoursquareResponse> searchVenue(@Query("v") String ver, @Query("ll") String longLat, @Query("query") String query,@Query("limit") int limit);

    @GET("venues/{VENUE_ID}/photos")
    Call<PhotoResponse> getPhoto(@Path("VENUE_ID")String venueID, @Query("v") String ver,@Query("limit") int limit);
}
