package com.example.uncsupport.myheatmap;

import android.content.Intent;
import android.content.IntentSender;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStates;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import java.lang.reflect.Array;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, LocationListener {
    private GoogleApiClient c = null;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private ArrayList<Double> longs = new ArrayList<Double>();
    private ArrayList<Double> lats = new ArrayList<Double>();
    private ArrayList<Integer> times = new ArrayList<Integer>();
    int time;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        c = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        Log.v("TAG", "We are connected to Google Services");
        try {
            Location loc = LocationServices.FusedLocationApi.getLastLocation(c);
            Log.v("LOC", "" + loc.getLatitude() + ", " + loc.getLongitude());

            LocationRequest mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(10000);
            mLocationRequest.setFastestInterval(5000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

            LocationServices.FusedLocationApi.requestLocationUpdates(c, mLocationRequest, this);

            //check users location settings; prompt to change if unsatisfied
            LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                    .addLocationRequest(mLocationRequest);
            PendingResult<LocationSettingsResult> result =
                    LocationServices.SettingsApi.checkLocationSettings(c, builder.build());
            result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
                @Override
                public void onResult(LocationSettingsResult result) {
                    final Status status = result.getStatus();
                    switch (status.getStatusCode()) {
                        case LocationSettingsStatusCodes.SUCCESS:
                            // All location settings are satisfied. The client can
                            // initialize location requests here.
                            break;
                        case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                            // Location settings are not satisfied, but this can be fixed
                            // by showing the user a dialog.
                            try {
                                // Show the dialog by calling startResolutionForResult(),
                                // and check the result in onActivityResult().
                                status.startResolutionForResult(
                                        MainActivity.this,
                                        REQUEST_CHECK_SETTINGS);
                            } catch (IntentSender.SendIntentException e) {
                                // Ignore the error.
                            }
                            break;
                        case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                            // Location settings are not satisfied. However, we have no way
                            // to fix the settings so we won't show the dialog.
                            break;
                    }
                }
            });
        } catch (SecurityException ex) {
            ex.printStackTrace();
        }


    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    protected void onStart() {
        c.connect();
        super.onStart();
    }

    @Override
    protected void onStop() {
        c.disconnect();
        super.onStop();
    }

    @Override
    public void onLocationChanged(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();
        String mLastUpdateTime = DateFormat.getDateInstance().format(new Date());
        int date = 0;

        String month = mLastUpdateTime.substring(0, mLastUpdateTime.indexOf(" "));
        String c = "" + mLastUpdateTime.charAt(mLastUpdateTime.indexOf(" ") + 1);
        int day = Integer.parseInt(c);
        if (month.equals("January")) {
            date = day;
        }
        else if (month.equals("February")) {
            date = 31 + day;
        }
        else if (month.equals("March")) {
            date = 59 + day;
        }
        else if (month.equals("April")) {
            date = 90 + day;
        }
        else if (month.equals("May")) {
            date = 120 + day;
        }
        else if (month.equals("June")) {
            date = 151 + day;
        }
        else if (month.equals("July")) {
            date = 181 + day;
        }
        else if (month.equals("August")) {
            date = 212 + day;
        }
        else if (month.equals("September")) {
            date = 243 + day;
        }
        else if (month.equals("October")) {
            date = 273 + day;
        }
        else if (month.equals("November")) {
            date = 304 + day;
        }
        else if (month.equals("December")) {
            date = 335 + day;
        }

        lats.add(latitude);
        longs.add(longitude);
        times.add(date);
        Log.v("BK", "location logged" + latitude + ", " + longitude);
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
    }

    @Override
    public void onConnectionSuspended(int i) {
    }

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?

        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        if (view.getTag().equals("1")) {
            if (checked) {
                time = 1;
                Log.d("time", String.valueOf(time));
            }
        } else if (view.getTag().equals("2")) {
            if (checked) {
                time = 2;
                Log.d("time", String.valueOf(time));
            }
        } else if (view.getTag().equals("3")) {
            if (checked) {
                time = 3;
                Log.d("time", String.valueOf(time));
            }
        } else if (view.getTag().equals("4")) {
            if (checked) {
                time = 4;
                Log.d("time", String.valueOf(time));
            }
        }


    }

    public void onClicked(View view) {
        if (view.getTag().equals("heat map")) {
            ArrayList<Double> longsCopy = new ArrayList<Double>();
            ArrayList<Double> latsCopy = new ArrayList<Double>();
            if (time == 1) {
                longsCopy = new ArrayList<Double>();
                latsCopy = new ArrayList<Double>();
                int dateIndex = times.indexOf(times.get(times.size()-1));
                if (times.contains(dateIndex)) {
                    for (int i = dateIndex; i < longs.size(); i++) {
                        longsCopy.add(longs.get(i));
                        latsCopy.add(lats.get(i));
                    }
                }
                else {
                    longsCopy = longs;
                    latsCopy = lats;
                }
            }
            else if (time == 2) {
                longsCopy = new ArrayList<Double>();
                latsCopy = new ArrayList<Double>();
                int dateIndex = times.indexOf(times.get(times.size()-1)-7);
                if (times.contains(dateIndex)) {
                    for (int i = dateIndex; i < longs.size(); i++) {
                        longsCopy.add(longs.get(i));
                        latsCopy.add(lats.get(i));
                    }
                }
                else {
                    longsCopy = longs;
                    latsCopy = lats;
                }
            }
            else if (time == 3) {
                longsCopy = new ArrayList<Double>();
                latsCopy = new ArrayList<Double>();
                int dateIndex = times.indexOf(times.get(times.size()-1)-14);
                if (times.contains(dateIndex)) {
                    for (int i = dateIndex; i < longs.size(); i++) {
                        longsCopy.add(longs.get(i));
                        latsCopy.add(lats.get(i));
                    }
                }
                else {
                    longsCopy = longs;
                    latsCopy = lats;
                }
            }
            else if (time == 4) {
                longsCopy = new ArrayList<Double>();
                latsCopy = new ArrayList<Double>();
                int dateIndex = times.indexOf(times.get(times.size()-1)-30);
                if (times.contains(dateIndex)) {
                    for (int i = dateIndex; i < longs.size(); i++) {
                        longsCopy.add(longs.get(i));
                        latsCopy.add(lats.get(i));
                    }
                }
                else {
                    longsCopy = longs;
                    latsCopy = lats;
                }
            }
            double[] longsExtra = new double[longs.size()];
            double[] latsExtra = new double[longs.size()];
            for (int i = 0; i < longs.size(); i++) {
                longsExtra[i] = longs.get(i);
                latsExtra[i] = lats.get(i);
            }

            Intent x = new Intent(this, Map.class);
            x.putExtra("longs", longsExtra);
            x.putExtra("lats", latsExtra);
            x.putExtra("mode", 1);
            x.putExtra("time", time);
            startActivity(x);
            Log.d("heatmap", "heat map");
        }
        else {
            double[] longsExtra = new double[longs.size()];
            double[] latsExtra = new double[longs.size()];
            for (int i = 0; i < longs.size(); i++) {
                longsExtra[i] = longs.get(i);
                latsExtra[i] = lats.get(i);
            }
            Intent x = new Intent(this, Map.class);
            x.putExtra("longs", longsExtra);
            x.putExtra("lats", latsExtra);
            x.putExtra("mode", 2);
            x.putExtra("time", 5);
            startActivity(x);
            Log.v("currentlocation", "current Location");


        }
    }
}
