package com.example.celia.kenya;

import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.view.View;
import android.widget.TextView;

import com.example.celia.kenya.client.*;
import com.example.celia.kenya.tools.Global;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.CameraUpdateFactory;


public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private List<String> list = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        final Button searchButton = (Button) findViewById(R.id.searchBtn);
        final TextView info = (TextView) findViewById(R.id.textView1);
        final Button loginButton = (Button) findViewById(R.id.loginBtn);





        if (Global.userID != null) {
            info.setVisibility(View.VISIBLE);
            info.setText("Welcome " + Global.userID + "!");

        } else if(Global.userID == null){
            info.setVisibility(View.VISIBLE);
            info.setText("Your Search Result: ");

            loginButton.setVisibility(View.VISIBLE);
            loginButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent logIntent = new Intent(MapsActivity.this, LoginActivity.class);
                    startActivity(logIntent);
                }
            });
        }
        if (Global.search == false){
            searchButton.setVisibility(View.VISIBLE);
            searchButton.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent mapIntent = new Intent(MapsActivity.this, SearchActivity.class);
                    startActivity(mapIntent);
                }
            });
        }

        setUpMapIfNeeded();
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                new ValidateNewUserIDTask().execute();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */


    private class ValidateNewUserIDTask extends AsyncTask<String, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(String... params) {
            // TODO Auto-generated method stub
            TrackClient client = new TrackClient("http://kenya-t6ap7bumpv.elasticbeanstalk.com/Track");
            if (Global.search != true && Global.userID != null && Global.sessionID != null)
                return client.getByUsername(Global.userID, Global.sessionID);
            else {
                Global.search = false;
                return client.getByNo(Global.trackingNO);
            }
        }//close doInBackground

        @Override
        protected void onPostExecute(ArrayList<String> result) {
            if (!result.isEmpty()) {
                if (result.size() == 1) {
                    Toast.makeText(MapsActivity.this,
                            "You have 1 package!", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(MapsActivity.this,
                            "You have " + result.size() + " packages!", Toast.LENGTH_LONG).show();
                }
                for (String res : result) {
                    try {
                        JSONObject obj = new JSONObject(res);
                        Global.latitude = Double.parseDouble(obj.getString("latitude"));
                        Global.longitude = Double.parseDouble(obj.getString("longitude"));
                        Global.trackingNO = obj.getString("trackNo");
                        Global.status = obj.getString("status");
                        Global.trackingNO = obj.getString("trackNo");
                        Global.trackingNO = obj.getString("trackNo");
                        Global.trackingNO = obj.getString("trackNo");
                        Global.locationList = obj.getJSONArray("locationList");
                        Global.recordsList = obj.getJSONArray("recordsList");
                        Global.timeList = obj.getJSONArray("timeList");

                    } catch (JSONException e) {
                        //e.printStackTrace();
                    }
                    LatLng poapost = new LatLng(Global.latitude, Global.longitude);

                    mMap.addMarker(new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.treasure_chest))
                            .title("PoaPost")
                            .snippet("Tracking No: " + Global.trackingNO)
                            .position(poapost)
                            .anchor(0.0f, 1.0f) // Anchors the marker on the bottom left

                    );

                    // Setting a custom info window adapter for the google map
                    mMap.setInfoWindowAdapter(new InfoWindowAdapter() {

                        // Use default InfoWindow frame
                        @Override
                        public View getInfoWindow(Marker arg0) {
                            return null;
                        }

                        // Defines the contents of the InfoWindow
                        @Override
                        public View getInfoContents(Marker arg0) {


                            // Getting view from the layout file info_window_layout
                            View v = getLayoutInflater().inflate(R.layout.info_window_layout, null);

                            // Getting the position from the marker
                            LatLng latLng = arg0.getPosition();

                            // Getting reference to the TextView to set latitude
                            TextView tvSta = (TextView) v.findViewById(R.id.tv_sta);

                            // Getting reference to the TextView to set longitude
                            TextView tvTrack = (TextView) v.findViewById(R.id.tv_track);

                            // Getting reference to the TextView to set description

                            TextView tvDes = (TextView) v.findViewById(R.id.tv_des);


                            // Setting the tracking no
                            tvTrack.setText("\nTracking No: \n" + Global.trackingNO + "\n");


                            // Setting the status

                            String receive;

                            if (Global.status.equals("0")) {
                                receive = "Has not been received";
                            } else {
                                receive = "Received";
                            }

                            tvSta.setText("Status: \n" + receive + "\n");

                            try {
                                String description = "";
                                for (int i = 0; i < Global.locationList.length(); i++) {
                                    description += "Location: " + Global.locationList.get(i) + "\nTime: " + Global.timeList.get(i) +
                                            "\nRecord: " + Global.recordsList.get(i) + "\n \n";

                                }

                                tvDes.setText("Tracking Records:\n" + description);


                                // Returning the view containing InfoWindow contents
                            } catch (JSONException e) {
                                //e.printStackTrace();
                            }
                            return v;

                        }

                    });

                }
            } else {
                Toast.makeText(MapsActivity.this,
                        "Sorry! :-(  You don't have any package! ", Toast.LENGTH_LONG).show();
            }
        }//close onPostExecute
    }// close
}
