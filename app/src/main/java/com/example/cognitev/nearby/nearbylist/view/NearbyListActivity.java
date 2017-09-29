package com.example.cognitev.nearby.nearbylist.view;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.example.cognitev.nearby.NearbyApplication;
import com.example.cognitev.nearby.R;
import com.example.cognitev.nearby.base.BaseActivity;

/**
 * Created by Salma on 9/21/2017.
 */

public class NearbyListActivity extends BaseActivity {

    NearbyListFragment nearbyListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_near_by_list);
        nearbyListFragment = (NearbyListFragment) getSupportFragmentManager().findFragmentById(R.id.nearby_fragment);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_near_by_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_mode) {
            showModeDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void showModeDialog() {
        String mode = NearbyApplication.getAppMode();
        new AlertDialog.Builder(this, R.style.Theme_AppCompat_Light_Dialog)
                .setSingleChoiceItems(R.array.modes, Integer.valueOf(mode), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        dialog.dismiss();
                        int selectedPosition = ((AlertDialog)dialog).getListView().getCheckedItemPosition();
                        NearbyApplication.setAppMode(String.valueOf(selectedPosition));
                        nearbyListFragment.togglePeriodicLocationUpdates(NearbyApplication.isModeRealTime());
                    }
                })
                .show();
    }
}
