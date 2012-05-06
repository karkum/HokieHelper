package org.mad.app.hokiehelper;

import java.util.List;

import com.google.android.maps.GeoPoint;

/**
* The Route class Represents the walking directions path.The route consists of 
* a ordered list of geographical points and major placemarks.
* @author Juilan Adams 
* @author Virginia Tech Mobile Application Development Team
* @version 2.1.12
*/   
public interface Maps_Route
{
	public abstract String getTotalDistance();

	public abstract List<GeoPoint> getGeoPoints();

	public abstract List<Maps_Placemark> getPlacemarks();

}