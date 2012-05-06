package org.mad.app.hokiehelper;

import com.google.android.maps.GeoPoint;
/**
* The PlacemarkImpl class Represents the implementation of major placemarks 
* along a walking route.
* @author Juilan Adams 
* @author Virginia Tech Mobile Application Development Team
* @version 2.1.12
*/   
public class Maps_PlacemarkImpl implements Maps_Placemark
{
	private GeoPoint location;
	private String instructions;
	private String distance;
	
	public void setLocation(GeoPoint location) {
		this.location = location;
	}

	public GeoPoint getLocation() {
		return location;
	}
	
	public void setInstructions(String instructions) {
		this.instructions = instructions;
	}

	public String getInstructions() {
		return instructions;
	}

	public void setDistance(String distance) {
		this.distance = distance;
	}
	public String getDistance() {
		return distance;
	}
}
