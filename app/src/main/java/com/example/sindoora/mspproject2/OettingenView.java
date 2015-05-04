package com.example.sindoora.mspproject2;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
//import de.lmu.ifi.mdsg.msp.R;

public class OettingenView extends ImageView{

	public static final int MARKER_RADIUS = 5;
	//scale and shift depend on the image

    //scale and shift depend on the image
    // zum berechnen der scale&shift parameter
    // Pixelparameter
    int px1 = 197;
    int py1 = 145; // bushaltestelle richtung gisela
    int px2 = 210;
    int py2 = 205; // erster eingang zum gebäude hinten
    // Locations
    LatLng l1  = new LatLng(48.150828, 11.595015); // bushalte
    LatLng l2  = new LatLng(48.150399, 11.595185); // eingang
    // neue Parameter ausrechnen mit obigen werten

    //Point_WGS84_x = Shift_x + Point_pixel_x * Scale_x

    public static final double scaleX = 0.000085384;
    public static final double scaleY = 0.0000022333;
    public static final double shiftX = 48.148545935;
    public static final double shiftY = 11.594691172;

    /*

    // vorgegeben
	public static final double scaleX = 0.000015278125;
	public static final double scaleY = 0.000022375;
	public static final double shiftX = 48.148283;
	public static final double shiftY = 11.58501;
*/
	
	private Paint markerPaint;
	private Location l;
	
	//Constructors
	public OettingenView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}
	public OettingenView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}
	public OettingenView(Context context) {
		super(context);
		init();
	}
	
	public void setLocation(Location _l){
		l = _l;
	}
	
	/**
	 * Initialize paint and image
	 */
	private void init(){
		markerPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		markerPaint.setColor(Color.BLUE);
		Resources res = getResources();
        // vorgegeben
		//setImageDrawable(res.getDrawable(R.drawable.karte_skaliert));
        setImageDrawable(res.getDrawable(R.drawable.oettingen));
	}
	
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//if we got a location
		if(l != null){
			double longitude = l.getLongitude(); // l1.longitude;
			double latitude = l.getLatitude(); // l1.latitude;
			Log.i(this.getClass().getName(), "coords: " + latitude + " " + longitude);
			//transform coordinates to pixels
			float[] pixelCoords = convert(latitude, longitude);
            // test
            //float[] pixelCoords = convert(l1.longitude, l1.latitude);
			Log.i(this.getClass().getName(), "pixel coords: " + pixelCoords[0] + " " + pixelCoords[1]);
			//if the result is inside the bounds of the image
			if(pixelCoords[0]>=0 && pixelCoords[0]<=320 && pixelCoords[1]>=0 && pixelCoords[1]<=480){
				//draw position on image
				canvas.drawCircle(pixelCoords[0], pixelCoords[1], MARKER_RADIUS, markerPaint);
			} else {
				//log the problem
				Log.e(this.getClass().getName(), "can not display position on map");
			}

            // test
            Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
            p.setColor(Color.RED);
            //canvas.drawCircle(50,50,MARKER_RADIUS,p);
            canvas.drawCircle(100,100,MARKER_RADIUS,p);

		}
		
	}
	
	/**
	 * 
	 * @param x The latitude from a WGS84 coordinate.
	 * @param y The longitude of a WGS84 coordinate.
	 * @return A pair {pixX, pixY} representing the corresponding pixel coordinates of the input.
	 */
	private float[] convert(double x, double y){
		//Point_WGS84_x = Shift_x + Point_pixel_x * Scale_x
		float pixX = (float) ((x-shiftX)/scaleX);
		//Point_WGS84_y = Shift_y + Point_pixel_y * Scale_y
		float pixY = (float) ((y-shiftY)/scaleY);
		float[] pixs = {pixX, pixY};
		return pixs;
	}
	
}
