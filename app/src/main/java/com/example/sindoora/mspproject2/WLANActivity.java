package com.example.sindoora.mspproject2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WLANActivity extends ActionBarActivity {

    private static final int MENU_DELETE = Menu.FIRST + 1;
    private static final int MENU_POSITIONING = MENU_DELETE + 1;

    boolean positioning;
    boolean record;

	/* onCreate:
		--retrieve view elements
		--set OnClickListener
		--create and register new WifiReceiver
		TODO init boolean values and create new Reasoner

		onDestroy:
		--unregister WifiReceiver
	*/

    WLANView view;
    WifiManager mainWifiObj;
    WifiReceiver wifiReciever;
    List<ScanResult> wifiScanList;
    int count = 0;

    ArrayList<Fingerprint> fingerprints;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_wlan_layout);

        fingerprints = new ArrayList<Fingerprint>();

        record = true;
        positioning = false;

        view = new WLANView(this);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainWifiObj = (WifiManager) getSystemService(Context.WIFI_SERVICE);

                wifiReciever = new WifiReceiver();

                registerReceiver(wifiReciever, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));

                mainWifiObj.startScan();


                Log.d("WLANActivity", "position: " + view.click_x + " , " + view.click_y);

            }
        });

        //TODO view refresh..
        //view.refreshDrawableState();
        setContentView(view);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //what happens if an option item is selected
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case (MENU_DELETE): {
                // --delete all fingerprints
                fingerprints.clear();
                view.fingerprints.clear();
                count = 0;
                // view.refreshDrawableState();

                Log.d("test", "fingerprints sollte jz leer sein: " + fingerprints);
                return true;
            }
            case (MENU_POSITIONING): {
                record = !record; // braucht man das ?
                positioning = !positioning;
                return true;
            }
        }
        return false;
    }

    @Override
    public void onDestroy() {
        unregisterReceiver(wifiReciever);
        super.onDestroy();
        Toast.makeText(getApplicationContext(),"16. onDestroy()", Toast.LENGTH_SHORT).show();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // how to create an options menu
        super.onCreateOptionsMenu(menu);
        menu.add(0, MENU_DELETE, Menu.NONE, "Delete all Fingerprints");
        menu.add(0, MENU_POSITIONING, Menu.NONE, "Turn Positioning On/Off");
        return true;
    }

    class WifiReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

                if (record) {
                // -- record a new fingerprint
                Log.d("wifireceiver", "es sollte neuer fingerpringt wieder möglich sein");
                    Log.d("wifireceiver", "testtesttest");
                    wifiScanList = mainWifiObj.getScanResults();

                    if (wifiScanList.size() != 0) {
                        String data = wifiScanList.get(0).toString();
                        Log.d("test", "data: " + data);
                        Log.d("test", "wifiScanList size : " + wifiScanList.size());
                        //Measurement(String mac, int rssi)
                        ArrayList<Measurement> measures = new ArrayList<Measurement>();

                        for (int i = 0; i < wifiScanList.size(); i++) {
                            Measurement m = new Measurement(wifiScanList.get(i).BSSID, wifiScanList.get(i).level);
                            measures.add(i, m);
                        }
                        //Measurement m = new Measurement(wifiScanList.get(0).BSSID, wifiScanList.get(0).level);
                        //Fingerprint(int x, int y, int e, int d, ArrayList<Measurement> measures)
                        Fingerprint testFP = new Fingerprint((int) view.click_x, (int) view.click_y, 0, 0, measures);
                        fingerprints.add(count, testFP);
                        view.fingerprints.add(count,testFP);
                        count++;
                        Log.d("testtesttest", "test fingerprint: " + testFP.toString());
                        Log.d("testtesttesttest", "fingerprints anzahl " + fingerprints.size());
                        Log.d("testtesttesttest", "fingerprints anzahl " + view.fingerprints.size());
                    } else {
                        Log.d("test", "wifiScanList is leer");
                    }

                    // TODO refresh view with new fingerprint
                    view.refreshDrawableState();

            }
            if (positioning) {
                //TODO: compute position and show it in the view
                wifiScanList = mainWifiObj.getScanResults();
               if(wifiScanList.size()!=0) {
                   DistanceReasoner dr = new DistanceReasoner();
                   Fingerprint nn = dr.compareClosest(fingerprints, wifiScanList);
                   view.position = nn;
               }
                else{
                   Log.d("receiver", "wifiscanlist is leer :(");
               }
            }
        }
    }
}

