package com.example.cognitev.nearby.nearbylist.model.dto;

import com.example.cognitev.nearby.base.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Salma on 9/22/2017.
 */

public class NearbyItemResponseModel extends BaseResponseModel {

    Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response implements Serializable{

        @SerializedName("venues")
        ArrayList<NearbyItemModel> nearbyItemModels;

        public ArrayList<NearbyItemModel> getNearbyItemModels() {
            return nearbyItemModels;
        }

        public void setNearbyItemModels(ArrayList<NearbyItemModel> nearbyItemModels) {
            this.nearbyItemModels = nearbyItemModels;
        }
    }
}
