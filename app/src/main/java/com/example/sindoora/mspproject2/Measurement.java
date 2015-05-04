package com.example.sindoora.mspproject2;

public class Measurement {
	
	public String mac;
	public int rssi;

	public Measurement(String mac, int rssi){
		this.mac = mac;
		this.rssi = rssi;
	}
	
	@Override
	public String toString() {
		String s = "measure: ";
		s+= mac + "," + rssi; 
		return s;
	}
}
