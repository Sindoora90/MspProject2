package com.example.sindoora.mspproject2;

import android.net.wifi.ScanResult;

import java.util.ArrayList;
import java.util.List;

public class DistanceReasoner {

	public Fingerprint compareClosest(ArrayList<Fingerprint> fingerprints, List<ScanResult> aps) {
		//TODO: return closest match in fingerprint space

        for (Fingerprint f: fingerprints){
            for(ScanResult s: aps){
                // f.measures.rss
            }
        }

        return null;
	}
}
