package com.example.cognitev.nearby.nearbylist.view;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.cognitev.nearby.NearbyApplication;
import com.example.cognitev.nearby.nearbylist.model.dto.NearbyItemModel;
import com.example.cognitev.nearby.nearbylist.viewmodels.NearbyListViewModel;
import com.example.cognitev.nearby.utils.PermissionUtil;
import com.example.cognitev.nearby.R;
import com.example.cognitev.nearby.base.BaseContract;
import com.example.cognitev.nearby.base.BaseFragment;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by Salma on 9/21/2017.
 */
public class NearbyListFragment extends BaseFragment implements BaseContract.BaseView
        , GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener
{

    @BindView(R.id.nearby_list_recyclerView)
    RecyclerView nearbyListRecyclerView;

    NearbyListAdapter nearbyListAdapter;
    NearbyListViewModel viewModel;

    private final static int PLAY_SERVICES_RESOLUTION_REQUEST = 1000;

    private Location mLastLocation;

    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;

    // boolean flag to toggle periodic location updates
    private boolean mRequestingLocationUpdates = false;

    private LocationRequest mLocationRequest;

    // Location updates intervals in sec
    private static int UPDATE_INTERVAL = 10000; // 10 sec
    private static int FASTEST_INTERVAL = 5000; // 5 sec
    private static int DISPLACEMENT = 500; // 10 meters


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(NearbyListViewModel.class);

        mRequestingLocationUpdates = NearbyApplication.isModeRealTime();
        if (checkPlayServices()) {

            // Building the GoogleApi client
            buildGoogleApiClient();

            createLocationRequest();

        }
    }

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_nearby_list;
    }


    public void setupView() {
        showLoading();
        nearbyListRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        nearbyListAdapter = new NearbyListAdapter(new ArrayList<NearbyItemModel>(), getActivity());
        nearbyListRecyclerView.setAdapter(nearbyListAdapter);


        viewModel.getNearbyData().observe(this, new Observer<ArrayList<NearbyItemModel>>() {
            @Override
            public void onChanged(@Nullable ArrayList<NearbyItemModel> nearbyItemModels) {
                if(nearbyItemModels != null){
                    if(nearbyItemModels.size() > 0){
                        showContent();
                        nearbyListAdapter.addItems(nearbyItemModels);
                    }else{
                        showError(getResources().getString(R.string.no_data_error));
                    }
                } else{
                    showError(getResources().getString(R.string.general_error));
                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        checkPlayServices();

        // Resuming the periodic location updates
        if (mGoogleApiClient.isConnected() && mRequestingLocationUpdates) {
            startLocationUpdates();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mGoogleApiClient.isConnected()) {
            stopLocationUpdates();
        }
    }




    private void updateLocation() {

        boolean permissionGranted = PermissionUtil.checkLocationPermission(this);
        if (permissionGranted) {
            mLastLocation = LocationServices.FusedLocationApi
                    .getLastLocation(mGoogleApiClient);

            if (mLastLocation != null) {
                double latitude = mLastLocation.getLatitude();
                double longitude = mLastLocation.getLongitude();
                viewModel.loadData(String.valueOf(latitude+","+longitude));
            }
        }

    }

    /**
     * Method to toggle periodic location updates
     * */
    public void togglePeriodicLocationUpdates(boolean isRealTime) {
        if (!isRealTime) {
            mRequestingLocationUpdates = true;
            // Starting the location updates
            startLocationUpdates();
        } else {
            mRequestingLocationUpdates = false;
            // Stopping the location updates
            stopLocationUpdates();
        }
    }

    /**
     * Creating google api client object
     * */
    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();
    }

    /**
     * Creating location request object
     * */
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(UPDATE_INTERVAL);
        mLocationRequest.setFastestInterval(FASTEST_INTERVAL);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        mLocationRequest.setSmallestDisplacement(DISPLACEMENT);
    }

    /**
     * Method to verify google play services on the device
     * */
    private boolean checkPlayServices() {
        int resultCode = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(getActivity());
        if (resultCode != ConnectionResult.SUCCESS) {
            if (GooglePlayServicesUtil.isUserRecoverableError(resultCode)) {
                GooglePlayServicesUtil.getErrorDialog(resultCode, getActivity(),
                        PLAY_SERVICES_RESOLUTION_REQUEST).show();
            } else {
                // To DO: Error
            }
            return false;
        }
        return true;
    }

    /**
     * Starting the location updates
     * */
    protected void startLocationUpdates() {

        boolean permissionGranted = PermissionUtil.checkLocationPermission(this);
        if (permissionGranted && mGoogleApiClient.isConnected()) {
            LocationServices.FusedLocationApi.requestLocationUpdates(
                    mGoogleApiClient, mLocationRequest, this);
        }

    }

    /**
     * Stopping location updates
     */
    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (mRequestingLocationUpdates) {
            startLocationUpdates();
        } else {
            updateLocation();
        }
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;

        updateLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length > 0) {
            switch (requestCode) {
                case PermissionUtil.ACCESS_COARSE_LOCATION_REQUEST_CODE:
                    boolean isAllPermessionsAccepted = true;
                    for (String permission : permissions) {
                        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), permission)) {
                            //User Denied The Permession
                            mGoogleApiClient.disconnect();
                            PermissionUtil.showApplicationSettingsDialog(getActivity());
                            isAllPermessionsAccepted = false;
                        } else {
                            if (ActivityCompat.checkSelfPermission(getActivity(), permission) == PackageManager.PERMISSION_GRANTED) {
                                //allowed
                            } else {
                                //set to never ask again
                                mGoogleApiClient.disconnect();
                                PermissionUtil.showApplicationSettingsDialog(mContext);
                                isAllPermessionsAccepted = false;
                            }
                        }
                    }
                    if (isAllPermessionsAccepted) {

                        if(mRequestingLocationUpdates){
                            mGoogleApiClient.connect();
                            startLocationUpdates();
                        }
                    }
                    break;
            }
        }

    }


}




