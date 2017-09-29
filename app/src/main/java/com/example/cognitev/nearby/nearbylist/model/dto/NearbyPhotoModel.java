package com.example.cognitev.nearby.nearbylist.model.dto;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Salma on 9/23/2017.
 */

public class NearbyPhotoModel implements Serializable {

    ArrayList<Photo> photos;

    public ArrayList<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(ArrayList<Photo> photos) {
        this.photos = photos;
    }

    public class Photo implements Serializable {
        ArrayList<Item> items;

        public ArrayList<Item> getItems() {
            return items;
        }

        public void setItems(ArrayList<Item> items) {
            this.items = items;
        }
    }



    public class Item implements Serializable{
        String prefix;
        String suffix;
        String width;

        public String getURL() {
            return prefix + width + suffix;
        }

        public String getPrefix() {
            return prefix;
        }

        public void setPrefix(String prefix) {
            this.prefix = prefix;
        }

        public String getSuffix() {
            return suffix;
        }

        public void setSuffix(String suffix) {
            this.suffix = suffix;
        }

        public String getWidth() {
            return width;
        }

        public void setWidth(String width) {
            this.width = width;
        }
    }




}
