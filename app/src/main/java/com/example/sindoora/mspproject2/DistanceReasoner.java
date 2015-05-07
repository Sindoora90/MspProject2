package com.example.sindoora.mspproject2;

import android.net.wifi.ScanResult;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class DistanceReasoner {

	public Fingerprint compareClosest(ArrayList<Fingerprint> fingerprints, List<ScanResult> aps) {
		//TODO: return closest match in fingerprint space


        double nnDist = 1000;
        double levelDist;
        Fingerprint nnFP = null;// = fingerprints.get(0); //new Fingerprint(0,0,0,0,null);

        // Alle Fingerprints
        for (Fingerprint f: fingerprints){
            levelDist = 0;
            // Alle Messungen pro Fingerprint
            for(Measurement m: f.measures) {
                for (ScanResult s : aps) {
                    Log.d("Distance", "test: current measurement " + m.toString() + " / scanresult " + s.toString());
                    // f.measures.rss
                    // wenn gleiche mac dann level unterschied berechnen
                    if(m.mac == s.BSSID){
                        // berechne distanz..

                        levelDist += ((m.rssi-s.level)*(m.rssi-s.level));
                      //  Log.d("DistanceTest","test ob distanz richtig: Measure  " + m.toString() + " , current sum " + levelDist);
                    }
                    else{
                        levelDist += 1000;
                    }
                }
            }
            levelDist = Math.sqrt(levelDist);
//            if(levelDist == 0){
//                nnDist = levelDist;
//                nnFP = f;
//                break;
//            }
            if(levelDist < nnDist){
                nnDist = levelDist;
                nnFP = f;
            }
            // TODO if leveldist < current than update dist+fingerprint

        }

            return nnFP;

	}
}
