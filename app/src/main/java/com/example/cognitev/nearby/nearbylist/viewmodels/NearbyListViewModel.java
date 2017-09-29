package com.example.cognitev.nearby.nearbylist.viewmodels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.example.cognitev.nearby.nearbylist.NearbyListContract;
import com.example.cognitev.nearby.nearbylist.model.dto.NearbyItemModel;
import com.example.cognitev.nearby.nearbylist.model.dto.NearbyPhotoResponseModel;

import com.example.cognitev.nearby.nearbylist.model.dto.NearbyItemResponseModel;
import com.example.cognitev.nearby.nearbylist.model.service.NearbyListService;
import com.example.cognitev.nearby.base.BaseViewModel;


import java.util.ArrayList;
import java.util.List;

import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Salma on 9/21/2017.
 */

public class NearbyListViewModel extends BaseViewModel implements NearbyListContract.NearbyListViewModel{

    private MutableLiveData<ArrayList<NearbyItemModel>> nearByResponse;
    private NearbyListService nearByListService;
    private ArrayList<NearbyItemModel> nearbyListViewModels;

    public NearbyListViewModel() {
        nearByResponse = new MutableLiveData<>();
        nearByListService = new NearbyListService();
    }

    @NonNull
    public LiveData<ArrayList<NearbyItemModel>> getNearbyData() {
        return nearByResponse;
    }


    public LiveData<ArrayList<NearbyItemModel>> loadData(String location) {
        nearByListService.getNearbyItems(location)
                .flatMapIterable(new Function<NearbyItemResponseModel, Iterable<NearbyItemModel>>() {

                    @Override
                    public Iterable<NearbyItemModel> apply(NearbyItemResponseModel nearbyItemResponseModel) throws Exception {
                        nearbyListViewModels = nearbyItemResponseModel.getResponse().getNearbyItemModels();
                        nearByResponse.postValue(nearbyItemResponseModel.getResponse().getNearbyItemModels());
                        return nearbyItemResponseModel.getResponse().getNearbyItemModels();
                    }
                }).flatMap(new Function<NearbyItemModel, ObservableSource<NearbyPhotoResponseModel>>() {
                               @Override
                               public ObservableSource<NearbyPhotoResponseModel> apply(NearbyItemModel model) throws Exception {
                                   return nearByListService.getNearbyItemPhoto(model.getId());
                               }
                           }, new BiFunction<NearbyItemModel, NearbyPhotoResponseModel, NearbyItemModel>() {
                               @Override
                               public NearbyItemModel apply(NearbyItemModel model, NearbyPhotoResponseModel nearbyPhotoResponseModel) throws Exception {
                                   if (nearbyPhotoResponseModel.getResponse().getNearbyPhotoModels() != null && nearbyPhotoResponseModel.getResponse().getNearbyPhotoModels().getItems() != null && nearbyPhotoResponseModel.getResponse().getNearbyPhotoModels().getItems().size() > 0) {
                                       model.setUrl(nearbyPhotoResponseModel.getResponse().getNearbyPhotoModels().getItems().get(0).getURL());
                                   }
                                   return model;
                               }
                           })
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(new DisposableSingleObserver<List<NearbyItemModel>>() {
                    @Override
                    public void onSuccess(List<NearbyItemModel> value) {
                        nearByResponse.postValue((ArrayList<NearbyItemModel>) value);
                    }

                    @Override
                    public void onError(Throwable e) {
                        if(nearbyListViewModels == null)
                           nearByResponse.postValue(null);
                    }
                });

        return nearByResponse;
    }


}