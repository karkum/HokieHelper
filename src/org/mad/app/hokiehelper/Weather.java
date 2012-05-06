package org.mad.app.hokiehelper;


/**
 * Represents a Weather object. This class encapsulates all the information about 
 * the weather conditions.
 * 
 * @author Karthik Kumar
 * @version 2012.01.06
 *
 */

public class Weather 
{
	private String icon;
	private String description;
	private int temperature;
	private String skyCover;
	private int chanceOfPrecipatation;
	private int feelsLike;
	private String windDirection;
	private int windSpeed;
	
	private String shortPrediction;
	private String day;
	private int high;
	private int low;
	/**
	 * @param icon the icon to set
	 */
	public void setIcon(String icon) 
	{
		this.icon = icon;
	}
	/**
	 * @return the icon
	 */
	public String getIcon() 
	{
		return icon;
	}
	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) 
	{
		this.description = description;
	}
	/**
	 * @return the description
	 */
	public String getDescription() 
	{
		return description;
	}
	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(int temperature) 
	{
		this.temperature = temperature;
	}
	/**
	 * @return the temperature
	 */
	public int getTemperature() 
	{
		return temperature;
	}
	/**
	 * @param skyCover the skyCover to set
	 */
	public void setSkyCover(String skyCover) 
	{
		this.skyCover = skyCover;
	}
	/**
	 * @return the skyCover
	 */
	public String getSkyCover() 
	{
		return skyCover;
	}
	/**
	 * @param chanceOfPrecipatation the chanceOfPrecipatation to set
	 */
	public void setChanceOfPrecipatation(int chanceOfPrecipatation) 
	{
		this.chanceOfPrecipatation = chanceOfPrecipatation;
	}
	/**
	 * @return the chanceOfPrecipatation
	 */
	public int getChanceOfPrecipatation() 
	{
		return chanceOfPrecipatation;
	}
	/**
	 * @param feelsLike the feelsLike to set
	 */
	public void setFeelsLike(int feelsLike) 
	{
		this.feelsLike = feelsLike;
	}
	/**
	 * @return the feelsLike
	 */
	public int getFeelsLike() 
	{
		return feelsLike;
	}
	/**
	 * @param windDirection the windDirection to set
	 */
	public void setWindDirection(String windDirection) 
	{
		this.windDirection = windDirection;
	}
	/**
	 * @return the windDirection
	 */
	public String getWindDirection() 
	{
		return windDirection;
	}
	/**
	 * @param windSpeed the windSpeed to set
	 */
	public void setWindSpeed(int windSpeed) 
	{
		this.windSpeed = windSpeed;
	}
	/**
	 * @return the windSpeed
	 */
	public int getWindSpeed() 
	{
		return windSpeed;
	}
	/**
	 * @param day the day to set
	 */
	public void setDay(String day) {
		this.day = day;
	}
	/**
	 * @return the day
	 */
	public String getDay() {
		return day;
	}
	/**
	 * @param high the high to set
	 */
	public void setHigh(int high) {
		this.high = high;
	}
	/**
	 * @return the high
	 */
	public int getHigh() {
		return high;
	}
	/**
	 * @param low the low to set
	 */
	public void setLow(int low) {
		this.low = low;
	}
	/**
	 * @return the low
	 */
	public int getLow() {
		return low;
	}
	/**
	 * @param shortPrediction the shortPrediction to set
	 */
	public void setShortPrediction(String shortPrediction) {
		this.shortPrediction = shortPrediction;
	}
	/**
	 * @return the shortPrediction
	 */
	public String getShortPrediction() {
		return shortPrediction;
	}
}
