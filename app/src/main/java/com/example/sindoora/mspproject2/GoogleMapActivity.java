package com.example.sindoora.mspproject2;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class GoogleMapActivity extends FragmentActivity implements LocationListener {

    static final LatLng HAMBURG = new LatLng(53.558, 9.927);
    static final LatLng KIEL = new LatLng(53.551, 9.993);

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    private Location curLocOverGPS,currentLocationOverNetwork;
    LocationManager locationManager;
    Marker currentLocationMarkerForGPS, currentLocationMarkerForNetwork;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_google_map);
        // TODO später auskommentieren aber mMap initialisieren..
        setUpMapIfNeeded();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 100, this);


//        locationListener = new LocationListener() {
//
//            // Wird Aufgerufen, wenn eine neue Position durch den LocationProvider bestimmt wurde
//            public void onLocationChanged(Location location) {
//
//                mMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map))
//                        .getMap();
//
//                Log.d("GoogleMap","current Position: Lat: " + location.getLatitude() + " Long: " + location.getLongitude());
////                if (mMap!=null) {
////                    Marker currentPos = mMap.addMarker(new MarkerOptions().position((double)location.getLatitude(),location.getLongitude())
////                            .title("Hamburg"));
////                }
//
//            }
//
//            public void onStatusChanged(String provider, int status, Bundle extras) {}
//
//            public void onProviderEnabled(String provider) {}
//
//            public void onProviderDisabled(String provider) {}
//        };


        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }
        else {
            curLocOverGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (curLocOverGPS != null) {
                currentLocationMarkerForGPS = mMap.addMarker(new MarkerOptions()
                        .position(new LatLng(curLocOverGPS.getLatitude(), curLocOverGPS.getLongitude()))
                        .title("GPS"));

            } else {
                Log.d("Location", "location over GPS not available");
            }
        }


         currentLocationOverNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if (currentLocationOverNetwork != null) {
             currentLocationMarkerForNetwork = mMap.addMarker(new MarkerOptions()
                    .position(new LatLng(currentLocationOverNetwork.getLatitude(), currentLocationOverNetwork.getLongitude()))
                    .title("Network")
                    .icon(BitmapDescriptorFactory
                            .fromResource(R.mipmap.ic_launcher)));

        }
        else{
            Log.d("Location", "location over Network not available");
        }

        Log.d("blabla", "locationmanager getallproviders " + locationManager.getAllProviders());
        Log.d("blabla", "locationmanager getallproviders " + locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER));
        Log.d("blabla", "locationmanager getallproviders " + locationManager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER));
        Log.d("blabla", "locationmanager getallproviders " + locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER));


        // Initialize the location fields
        if (currentLocationOverNetwork != null) {
            Log.d("GoogleMap", "current Position: Lat: " + currentLocationOverNetwork.getLatitude() + " Long: " + currentLocationOverNetwork.getLongitude());
//
        } else {
            Log.d("Location", "location not available");
            // latituteField.setText("Location not available");
            // longitudeField.setText("Location not available");
        }


        Marker kiel = mMap.addMarker(new MarkerOptions()
                .position(KIEL)
                .title("Kiel")
                .snippet("Kiel is cool")
                .icon(BitmapDescriptorFactory
                        .fromResource(R.mipmap.ic_launcher)));

    }

    @Override
    public void onLocationChanged(Location location) {

        Log.d("GoogleMap", "current Position: Lat: " + location.getLatitude() + " Long: " + location.getLongitude());

        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        // check if enabled and if not send user to the GSP settings
        // Better solution would be to display a dialog and suggesting to
        // go to the settings
        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        else {
            curLocOverGPS = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (curLocOverGPS != null) {
//                currentLocationMarkerForGPS = mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(curLocOverGPS.getLatitude(), curLocOverGPS.getLongitude()))
//                        .title("GPS"));

                currentLocationMarkerForGPS.setPosition(new LatLng(curLocOverGPS.getLatitude(), curLocOverGPS.getLongitude()));

            }
            else{
                Log.d("Location", "location over GPS not available");
            }

            if (currentLocationOverNetwork != null) {
//                currentLocationMarkerForNetwork = mMap.addMarker(new MarkerOptions()
//                        .position(new LatLng(currentLocationOverNetwork.getLatitude(), currentLocationOverNetwork.getLongitude()))
//                        .title("Network")
//                        .icon(BitmapDescriptorFactory
//                                .fromResource(R.mipmap.ic_launcher)));

                currentLocationMarkerForNetwork.setPosition(new LatLng(currentLocationOverNetwork.getLatitude(), currentLocationOverNetwork.getLongitude()));


            }
            else{
                Log.d("Location", "location over Network not available");
            }
        }

        Toast.makeText(this, "GPS: Latitude: " + curLocOverGPS.getLatitude() + " Longitude: " + curLocOverGPS.getLongitude(), Toast.LENGTH_LONG).show();
        Toast.makeText(this, "Network: Latitude: " + curLocOverGPS.getLatitude() + " Longitude: " + curLocOverGPS.getLongitude(), Toast.LENGTH_LONG).show();

    }



    /////////////
    ///////////// unwichtig...
    /////////////

    @Override
    protected void onResume() {
        super.onResume();
        //   setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
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
                setUpMap();
            }
        }
    }

    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {
        mMap.addMarker(new MarkerOptions().position(new LatLng(0, 0)).title("Marker"));
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
