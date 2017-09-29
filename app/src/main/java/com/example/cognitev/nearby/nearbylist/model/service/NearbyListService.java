package com.example.cognitev.nearby.nearbylist.model.service;



import com.example.cognitev.nearby.nearbylist.model.dto.NearbyItemResponseModel;
import com.example.cognitev.nearby.nearbylist.model.dto.NearbyPhotoResponseModel;
import com.example.cognitev.nearby.utils.NetworkConstants;
import com.example.cognitev.nearby.base.BaseService;

import io.reactivex.Observable;


/**
 * Created by Salma on 9/22/2017.
 */

public class NearbyListService extends BaseService{

    private NearbyListAPI api;

    public NearbyListService(){
        api = retrofit.create(NearbyListAPI.class);
    }

    public Observable<NearbyItemResponseModel> getNearbyItems(String location) {
        return api.getNearbyList(NetworkConstants.CLIENT_ID_VALUE, NetworkConstants.CLIENT_SECRET_VALUE, location, NetworkConstants.VERSION_VALUE);

    }

    public Observable<NearbyPhotoResponseModel> getNearbyItemPhoto(String id) {

        return api.getNearbyPhoto(id,NetworkConstants.CLIENT_ID_VALUE, NetworkConstants.CLIENT_SECRET_VALUE,NetworkConstants.LIMIT_VALUE, NetworkConstants.VERSION_VALUE);

    }
}
