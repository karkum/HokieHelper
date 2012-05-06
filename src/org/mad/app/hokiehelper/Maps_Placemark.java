package org.mad.app.hokiehelper;

import com.google.android.maps.GeoPoint;

/**
* The Placemark class Represents a major placemark along a walkingroute.
* @author Juilan Adams 
* @author Virginia Tech Mobile Application Development Team
* @version 2.1.12
*/    
public interface Maps_Placemark
{
	public abstract GeoPoint getLocation();

	public abstract String getInstructions();

	public abstract String getDistance();

}