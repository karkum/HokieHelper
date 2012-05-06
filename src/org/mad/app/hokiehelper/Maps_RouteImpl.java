package org.mad.app.hokiehelper;

import java.util.ArrayList;
import java.util.List;
import com.google.android.maps.GeoPoint;
/**
* The Route class Represents the implementation of the walking directions path.
* The route consists of a ordered list of geographical points and major 
* placemarks.
* @author Juilan Adams 
* @author Virginia Tech Mobile Application Development Team
* @version 2.1.12
*/   
public class Maps_RouteImpl implements Maps_Route 
{
	private String totalDistance;
	private List<GeoPoint> geoPoints;
	private List<Maps_Placemark> placemarks;

	public void setTotalDistance(String totalDistance) {
		this.totalDistance = totalDistance;
	}

	public String getTotalDistance() {
		return totalDistance;
	}
	
	public void setGeoPoints(List<GeoPoint> geoPoints) {
		this.geoPoints = geoPoints;
	}

	public List<GeoPoint> getGeoPoints() {
		return geoPoints;
	}
	
	public void addGeoPoint (GeoPoint point)
	{
		if (geoPoints == null) {
			geoPoints = new ArrayList<GeoPoint>();
		}
		geoPoints.add(point);
	}

	public void setPlacemarks(List<Maps_Placemark> placemarks) {
		this.placemarks = placemarks;
	}

	public List<Maps_Placemark> getPlacemarks() {
		return placemarks;
	}
	
	public void addPlacemark (Maps_Placemark mark)
	{
		if (placemarks == null) {
			placemarks = new ArrayList<Maps_Placemark>();
		}
		placemarks.add(mark);
	}
}
