package com.example.cognitev.nearby.nearbylist;

import android.arch.lifecycle.LiveData;

import com.example.cognitev.nearby.nearbylist.model.dto.NearbyItemModel;
import com.example.cognitev.nearby.nearbylist.model.dto.NearbyItemResponseModel;
import com.example.cognitev.nearby.nearbylist.model.dto.NearbyPhotoResponseModel;
import com.example.cognitev.nearby.base.BaseContract;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by Salma on 9/21/2017.
 */

public interface NearbyListContract {

    interface NearbyListView extends BaseContract.BaseView{
    }

    interface NearbyListViewModel {

        LiveData<ArrayList<NearbyItemModel>> loadData(String location);

    }

    interface NearbyListService {
        Observable<NearbyItemResponseModel> getNearbyItems(String location);
        Observable<NearbyPhotoResponseModel> getNearbyPhoto(String id);
    }
}
