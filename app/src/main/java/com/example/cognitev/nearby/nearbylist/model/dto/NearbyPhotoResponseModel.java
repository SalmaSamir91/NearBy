package com.example.cognitev.nearby.nearbylist.model.dto;

import com.example.cognitev.nearby.base.BaseResponseModel;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

/**
 * Created by Salma on 9/22/2017.
 */

public class NearbyPhotoResponseModel extends BaseResponseModel {

    Response response;

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public class Response implements Serializable{

        @SerializedName("photos")
        NearbyPhotoModel.Photo photo;

        public NearbyPhotoModel.Photo getNearbyPhotoModels() {
            return photo;
        }

        public void setNearbyPhotoModels(NearbyPhotoModel.Photo nearbyPhotoModels) {
            this.photo = nearbyPhotoModels;
        }
    }
}
