package com.example.sindoora.mspproject2;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;


public class GPSActivity extends ActionBarActivity implements LocationListener{

    LocationManager locationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_gps);
        setContentView(R.layout.gps_map_layout);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 100, this);


        boolean enabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);

        if (!enabled) {
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            startActivity(intent);
        }

        Location location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);

        Location currentLocationOverNetwork = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
        if(currentLocationOverNetwork!=null) {
//            Marker currentLocationMarker = mMap.addMarker(new MarkerOptions()
//                    .position(new LatLng(currentLocationOverNetwork.getLatitude(), currentLocationOverNetwork.getLongitude()))
//                    .title("currentLoc")
//                    .icon(BitmapDescriptorFactory
//                            .fromResource(R.mipmap.ic_launcher)));

            ImageView oview = new OettingenView(this);
            //float[] convertedLoc = oview.convert((double)currentLocationOverNetwork.getLatitude(), (double)currentLocationOverNetwork.getLongitude());
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_g, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onLocationChanged(Location location) {

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
