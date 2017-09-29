package com.example.cognitev.nearby.nearbylist.model.dto;

import java.io.Serializable;

/**
 * Created by Salma on 9/21/2017.
 */

public class NearbyItemModel implements Serializable {

    String id;
    String name;
    Location location;
    String url;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public class Location implements Serializable{
        String address;

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
