package com.example.cognitev.nearby.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import com.example.cognitev.nearby.R;

import java.util.ArrayList;


/**
 * Created by Salma on 9/23/2017.
 */

public class PermissionUtil {
    public static final int ACCESS_COARSE_LOCATION_REQUEST_CODE = 120;
    private static AlertDialog permissionDialog;

    public static boolean checkLocationPermission(Fragment fragment) {
        ArrayList<String> permissionsNeeded = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.ACCESS_COARSE_LOCATION);
        }

        if (ActivityCompat.checkSelfPermission(fragment.getActivity(), Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            permissionsNeeded.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (!permissionsNeeded.isEmpty()) {
            requestPermission(fragment , permissionsNeeded.toArray(new String[permissionsNeeded.size()]), ACCESS_COARSE_LOCATION_REQUEST_CODE);
            return false;
        }
        return true;
    }

    private static void requestPermission(Fragment fragment , String[] permissions , int requestCode) {
        fragment.requestPermissions(permissions, requestCode);
    }

    public static void showApplicationSettingsDialog(final Context context){
        if (permissionDialog == null) {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle(context.getResources().getString(R.string.permission_alert_title))
                    .setMessage(context.getResources().getString(R.string.permission_alert_description))
                    .setPositiveButton(context.getResources().getString(R.string.permission_alert_settings_button), new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            // continue with delete
                            Intent intent = new Intent();
                            intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", context.getPackageName(), null);
                            intent.setData(uri);
                            context.startActivity(intent);
                        }
                    });
            permissionDialog = builder.create();
            permissionDialog.setCancelable(false);
        }
        if (!permissionDialog.isShowing() ) {
            permissionDialog.show();
            permissionDialog.getButton(DialogInterface.BUTTON_POSITIVE).setTextColor(context.getResources().getColor(R.color.colorPrimary));
        }
    }
}
