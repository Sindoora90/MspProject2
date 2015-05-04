package com.example.sindoora.mspproject2;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class WLANActivity extends Activity {

	private static final int MENU_DELETE = Menu.FIRST + 1;
	private static final int MENU_POSITIONING = MENU_DELETE + 1;
	
	boolean positioning;
	boolean record;
	
	/*TODO: onCreate:
		retrieve view elements
		set OnClickListener
		create and register new WifiReceiver
		init boolean values and create new Reasoner

		onDestroy:
		unregister WifiReceiver
	*/

    WLANView view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gps_map_layout);

        view = new WLANView(this);
        //TODO view.paint();
        setContentView(view);

    }

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		//what happens if an option item is selected		
		super.onOptionsItemSelected(item);
		switch (item.getItemId()) {
		case (MENU_DELETE): {
			//TODO: delete all fingerprints
			return true;
		}
		case (MENU_POSITIONING): {
			positioning = !positioning;
			return true;
		}
		}
		return false;
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
		        if(record){
				//TODO: record a new fingerprint
		        }
		        if(positioning){
		        	//TODO: compute position and show it in the view
		        }
			}
		}
	}

