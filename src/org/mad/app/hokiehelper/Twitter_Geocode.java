package org.mad.app.hokiehelper;
/**
 * A simple class that provides support for searching for tweets around a certain point in
 * the world.
 * @author karthik
 *
 */
public class Twitter_Geocode {
	private double latitude;
	private double longitude;
	private String radius;
	/**
	 * @return the latitude
	 */
	public double getLatitude() {
		return latitude;
	}
	/**
	 * @param latitude the latitude to set
	 */
	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}
	/**
	 * @return the longitude
	 */
	public double getLongitude() {
		return longitude;
	}
	/**
	 * @param longitude the longitude to set
	 */
	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}
	/**
	 * @return the radius
	 */
	public String getRadius() {
		return radius;
	}
	/**
	 * @param radius the radius to set
	 */
	public void setRadius(String radius) {
		this.radius = radius;
	}
}
