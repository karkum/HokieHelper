package org.mad.app.hokiehelper;


/**
 * Parse WeatherBug XML data.
 *
 * @author Karthik Kumar
 * @version 2011.01.05
 */

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
/**
 * Parses the weather information from weatherbug
 * @author karthik
 *
 */
public class Weather_XMLParser {

	private String result;
	private ArrayList <Weather> forecast;
	private Weather currentWeather;

	public static final double LAT = 37.224793;
	public static final double LONG = -80.42726;

	private final String API_KEY = "A2431210355";
	private static byte[] sBuffer = new byte[512];

	public void doForecast() {
		String url = "http://" + API_KEY + ".api.wxbug.net/getForecastRSS.aspx?ACode=";

		StringBuilder str = new StringBuilder(url);
		str.append(API_KEY + "&");
		str.append("lat=").append(LAT);
		str.append("&long=").append(LONG);
		str.append("&UnitType=0").append("OutputType=1");
		url = str.toString();

		try 
		{
			parseForecast(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void doCurrentConditions() {
		String url = "http://" + API_KEY + ".api.wxbug.net/getLiveCompactWeatherRSS.aspx?ACode=";

		StringBuilder str = new StringBuilder(url);
		str.append(API_KEY + "&");
		str.append("lat=").append(LAT);
		str.append("&long=").append(LONG);
		str.append("&UnitType=0").append("OutputType=1");
		url = str.toString();
		try 
		{
			parseCurrentConditions(url);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public Weather_XMLParser()
	{
		forecast = new ArrayList<Weather> (7);
	}

	/**
	 * Parses a url pointing to XML object to a Weather object.
	 * @throws Exception
	 */
	private void parseForecast(String url) throws Exception 
	{		
		String response = getUrlContent(url);	
		InputStream stream = new ByteArrayInputStream(response.getBytes());

		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(stream);
		Element root = doc.getDocumentElement();
		NodeList items = root.getElementsByTagName("aws:weather");
		for (int i = 0; i < items.getLength(); i++) {
			Node property = items.item(i);
			NodeList weatherItems = property.getChildNodes();
			for (int j = 0; j < weatherItems.getLength(); j++) {
				Node n = weatherItems.item(j);
				String nm = n.getNodeName();
				if (nm.equals("aws:forecasts")) {
					NodeList actualInfo = n.getChildNodes();
					for (int k = 0; k < actualInfo.getLength(); k++) {
						Node pr = actualInfo.item(k);
						String testing = pr.getNodeName();

						if (testing.equals("aws:forecast")) {
							Weather weat = new Weather();
							NodeList listOfForecasts = pr.getChildNodes();
//							for (int m = 0; m < actualInfo.getLength(); m++) {
								String day = listOfForecasts.item(0).getTextContent();
								String shortPrediction = listOfForecasts.item(1).getTextContent();
								String icon = listOfForecasts.item(2).getTextContent();
								String prediction = listOfForecasts.item(4).getTextContent();
								String high = listOfForecasts.item(5).getTextContent();
								String low = listOfForecasts.item(6).getTextContent();
								weat.setDescription(prediction);
								weat.setDay(day);
								int h, l = 0;
								try {
									h = Integer.valueOf(high);
								} catch (Exception e) {
									h = -1;
								}
								try {
									l = Integer.valueOf(low);
								} catch (Exception e) {
									l = -1;
								}
								weat.setHigh(h);
								weat.setLow(l);
								weat.setIcon(icon);
								weat.setShortPrediction(shortPrediction);
								forecast.add(weat);
//							}
						}
						System.out.println(testing);
					}
				}
			}
		}
	}
	/**
	 * Gets the current conditions
	 */
	private void parseCurrentConditions(String url) throws Exception 
	{		
		String response = getUrlContent(url);	
		InputStream stream = new ByteArrayInputStream(response.getBytes());

		currentWeather = new Weather();
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		Document doc = db.parse(stream);
		Element root = doc.getDocumentElement();
		NodeList items = root.getElementsByTagName("aws:weather");
		for (int i = 0; i < items.getLength(); i++) {
			Node property = items.item(i);
			NodeList weatherItems = property.getChildNodes();
			for (int j = 0; j < weatherItems.getLength(); j++) {
				Node n = weatherItems.item(j);
				String nm = n.getNodeName();
				if (nm.equals("aws:current-condition")) {
					NamedNodeMap attributes = n.getAttributes();
					Node icon = attributes.getNamedItem("icon");
					String iconURL = icon.getNodeValue();
					String condition = n.getChildNodes().item(0).getNodeValue();
					currentWeather.setIcon(iconURL);
					currentWeather.setDescription(condition);
				}
				else if(nm.equals("aws:temp"))	{
					String temp = n.getChildNodes().item(0).getNodeValue();
					try {
						currentWeather.setTemperature((int)Double.parseDouble(temp));
					} catch (Exception e) {
						currentWeather.setTemperature(-1);
					}
					
				}
			}
		}
	}

	/**
	 * Uses a HTTP client to get the content from WeatherBug
	 * @param url the link to get conditions.
	 * @return String conditions for this trip 
	 * @throws ApiException the exception thrown if any problem occurs.
	 */
	private String getUrlContent(String url) throws ApiException {

		// Create client and set our specific user-agent string
		HttpClient client = new DefaultHttpClient();
		HttpGet request = new HttpGet(url);
		//request.setHeader("User-Agent", sUserAgent);

		try {
			HttpResponse response = client.execute(request);

			// Pull content stream from response
			HttpEntity entity = response.getEntity();
			InputStream inputStream = entity.getContent();

			ByteArrayOutputStream content = new ByteArrayOutputStream();

			// Read response into a buffered stream
			int readBytes = 0;
			while ((readBytes = inputStream.read(sBuffer)) != -1) {
				content.write(sBuffer, 0, readBytes);
			}

			// Return result from buffered stream
			return new String(content.toByteArray());
		} catch (IOException e) {
			throw new ApiException("Problem communicating with API", e);
		}
	}
	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public void setForecast(ArrayList<Weather> forecast) {
		this.forecast = forecast;
	}
	
	public ArrayList<Weather> getForecast() {
		return forecast;
	}
	/**
	 * Exception thrown when there is an error with getting info from API.
	 *
	 */
	@SuppressWarnings("serial")
	public static class ApiException extends Exception 
	{
		public ApiException(String detailMessage, Throwable throwable) 
		{
			super(detailMessage, throwable);
		}
		public ApiException(String detailMessage) 
		{
			super(detailMessage);
		}
	}
	public Weather getCurrentWeather() {
		return currentWeather;
	}
	/**
	 * Converts the weatherbug icons (which are bad) to better icons which are stored
	 * on the phone. Look here for more info: http://developer.weatherbug.com/docs/read/List_of_Icons
	 */
	public int getImageForCondition(String url) {
		int id = R.drawable.unknown;
		if (url == null)
			return id;
		int index = url.indexOf("cond");
		String pic = url.substring(index + 4, index + 7);
		int cond;
		try {
			cond = Integer.valueOf(pic);
		} catch (Exception e) {
			return id;
		}

		if (cond == 0 || cond == 7 || cond == 64 || cond == 65 || cond == 69
				|| cond == 75) {
			id = R.drawable.d01;
		} else if (cond == 1 || cond == 2 || cond == 68)
			id = R.drawable.x04;
		else if (cond == 3 || cond == 4 || cond == 23 || cond == 26
				|| cond == 66)
			id = R.drawable.d02;
		else if (cond == 5 || cond == 41 || cond == 45 || cond == 63
				|| cond == 150 || cond == 163)
			id = R.drawable.x10;
		else if (cond == 6 || cond == 22 || cond == 53 || cond == 93
				|| cond == 94 || cond == 95)
			id = R.drawable.d06;
		else if (cond == 8 || cond == 32 || cond == 40 || cond == 44
				|| cond == 55 || cond == 62 || cond == 84 || cond == 85
				|| cond == 86 || cond == 96 || cond == 97 || cond == 98
				|| cond == 126 || cond == 127 || cond == 128 || cond == 138
				|| cond == 140 || cond == 144 || cond == 146 || cond == 149
				|| cond == 151 || cond == 176 || cond == 160 || cond == 154)
			id = R.drawable.x13;
		else if (cond == 9 || cond == 11)
			id = R.drawable.x19;
		else if (cond == 12 || cond == 29 || cond == 43 || cond == 102
				|| cond == 103 || cond == 104 || cond == 117 || cond == 118
				|| cond == 119)
			id = R.drawable.n08;
		else if (cond == 13 || cond == 34 || cond == 71 || cond == 73)
			id = R.drawable.n03;
		else if (cond == 14 || cond == 15 || cond == 42 || cond == 81
				|| cond == 82 || cond == 83 || cond == 114 || cond == 115
				|| cond == 116)
			id = R.drawable.n05;
		else if (cond == 16 || cond == 35 || cond == 70 || cond == 72)
			id = R.drawable.n02;
		else if (cond == 17 || cond == 31 || cond == 33 || cond == 74)
			id = R.drawable.n01;
		else if (cond == 18 || cond == 30 || cond == 105 || cond == 106
				|| cond == 107)
			id = R.drawable.n06;
		else if (cond == 19)
			id = R.drawable.d07;
		else if (cond == 20 || cond == 58 || cond == 59 || cond == 132
				|| cond == 133 || cond == 134 || cond == 139 || cond == 141
				|| cond == 148 || cond == 150 || cond == 173 || cond == 170 || cond == 167 || cond == 165
				|| cond == 162 || cond == 156)
			id = R.drawable.x09;
		else if (cond == 21 || cond == 25 || cond == 28 || cond == 46
				|| cond == 47 || cond == 48 || cond == 49 || cond == 56
				|| cond == 57 || cond == 60 || cond == 61 || cond == 90
				|| cond == 91 || cond == 92 || cond == 99 || cond == 100
				|| cond == 101 || (cond <= 125 && cond >= 120) || cond == 129
				|| cond == 130 || cond == 131 || cond == 135 || cond == 136
				|| cond == 137 || cond == 142 || cond == 145 || cond == 152
				|| cond == 153 || cond == 175 || cond == 174 || cond == 172
				|| cond == 171 || cond == 169 || cond == 168 || cond == 166
				|| cond == 164 || cond == 161 || cond == 157 || cond == 155)
			id = R.drawable.x12;
		else if (cond == 24 || cond == 67)
			id = R.drawable.d03;
		else if (cond == 27 || cond == 36)
			id = R.drawable.n07;
		else if (cond == 38 || cond == 52 || cond == 87 || cond == 88
				|| cond == 89 || cond == 108 || cond == 109 || cond == 110)
			id = R.drawable.d05;
		else if (cond == 39 || cond == 54 || cond == 78 || cond == 79
				|| cond == 80 || cond == 111 || cond == 112 || cond == 113)
			id = R.drawable.d08;
		else if (cond == 50 || cond == 158)
			id = R.drawable.x17;
		else if (cond == 51 || cond == 159)
			id = R.drawable.x15;
		else if (cond == 147 || cond == 143)
			id = R.drawable.x11;
		return id;
	}
}
