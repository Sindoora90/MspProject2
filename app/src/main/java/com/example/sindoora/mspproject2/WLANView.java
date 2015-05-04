package com.example.sindoora.mspproject2;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
//import de.lmu.ifi.mdsg.msp.R;

public class WLANView extends TouchImageView {

	private int radius = 5;

	//colors
	Paint fp_paint;
	Paint position_paint;

	//fingerprint database
	public volatile ArrayList<Fingerprint> fingerprints;
	//current position
	public Fingerprint position;

	//Constructors for a view
	public WLANView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public WLANView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public WLANView(Context context) {
		super(context);
		init();
	}

	/**
	 * Initialize paint, image and fingerprint list
	 */
	private void init() {
		Resources res = getResources();
		setImageDrawable(res.getDrawable(R.drawable.mapview0));

		fp_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		fp_paint.setColor(Color.argb(200, 255, 0, 0));
		position_paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		position_paint.setColor(Color.argb(200, 0, 255, 0));

		fingerprints = new ArrayList<Fingerprint>();
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		//draw fingerprints
		if (fingerprints != null) {
			for(Fingerprint f : fingerprints){
				paint(f, canvas, fp_paint);
			}
		}
		//draw position if available
		if (position != null)
		{
			paint(position, canvas, position_paint);
		}
	}

	/**
	 * Draw a single fingerprint at the right position
	 */
	private void paint(Fingerprint f, Canvas canvas, Paint p) {
		int x = f.x;
		int y = f.y;

		float[] image_coords = new float[] { x, y };
		matrix.mapPoints(image_coords);
		float r = matrix.mapRadius(radius);
		
		canvas.drawCircle(image_coords[0], image_coords[1], r, p);
	}

}
