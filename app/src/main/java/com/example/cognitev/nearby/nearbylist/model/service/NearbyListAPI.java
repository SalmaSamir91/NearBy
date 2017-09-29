package com.example.cognitev.nearby.nearbylist.model.service;


import com.example.cognitev.nearby.nearbylist.model.dto.NearbyItemResponseModel;
import com.example.cognitev.nearby.nearbylist.model.dto.NearbyPhotoResponseModel;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Salma on 9/22/2017.
 */

public interface NearbyListAPI {

    @GET("venues/search")
    Observable<NearbyItemResponseModel> getNearbyList(@Query("client_id") String clientID, @Query("client_secret") String clientSecret, @Query("ll") String latLong, @Query("v") String version);


    @GET("venues/{id}/photos")
    Observable<NearbyPhotoResponseModel> getNearbyPhoto(@Path("id") String id,@Query("client_id") String clientID, @Query("client_secret") String clientSecret, @Query("limit") String limit, @Query("v") String version);
}
