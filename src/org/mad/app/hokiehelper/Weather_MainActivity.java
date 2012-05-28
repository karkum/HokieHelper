package org.mad.app.hokiehelper;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Third activity. Displays the weather conditions for Blacksburg.
 *
 * @author Karthik Kumar
 * @version 2012.01.05
 */

public class Weather_MainActivity extends Activity
{
	/* The TextView for placing our temperature information */
	private TextView day1;
	private TextView day2;
	private TextView day3;
	private TextView day4;
	private TextView day5;
	private TextView day6;
	private TextView day7;

	private ImageView icon1;
	private ImageView icon2;
	private ImageView icon3;
	private ImageView icon4;
	private ImageView icon5;
	private ImageView icon6;
	private ImageView icon7;

	private TextView day1high;
	private TextView day2high;
	private TextView day3high;
	private TextView day4high;
	private TextView day5high;
	private TextView day6high;
	private TextView day7high;

	private TextView day1low;
	private TextView day2low;
	private TextView day3low;
	private TextView day4low;
	private TextView day5low;
	private TextView day6low;
	private TextView day7low;
	
	private TextView cond1;
	private TextView cond2;
	private TextView cond3;
	private TextView cond4;
	private TextView cond5;
	private TextView cond6;
	private TextView cond7;
	private Handler handler = new Handler();
	/* The location of our requested information */
//	private Location location;

	/* The URL for opening the weather bug mobile website */
	private String webUrl = "http://weather-mobile.weatherbug.com/";

	/* The degree symbol in unicode */
	private final String DEGREE_SYMBOL = "\u00B0";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.forecast);

		final Weather_XMLParser parser = new Weather_XMLParser();
		new Thread(new Runnable() {
		    public void run() {
		        parser.doForecast();
		        handler.post(new Runnable() {
		            public void run() {
		                ArrayList <Weather> forecast = parser.getForecast();
		                StringBuilder url = new StringBuilder(webUrl);
//		              location = response.getLocation();
		                url.append("VA").append("/").append("Blacksburg/").append("local-forecast/detailed-forecast.aspx?ftype=1&fcurr=0&cid=0");
		                webUrl = url.toString();
		                TextView infoText = (TextView) findViewById(R.id.forecastInfo);
		                infoText.setText("Forecast for " + "Blacksburg" + ", " + "Virginia" + ":");
		                {
		                    day1 = (TextView) findViewById(R.id.day_1);
		                    day1.setText(forecast.get(0).getDay().toUpperCase());
		                    icon1 = (ImageView) findViewById(R.id.day_1_icon);
		                    cond1 = (TextView) findViewById(R.id.cond_1);
		                    icon1.setOnClickListener(new OnClickListener(){
		                        
		                        /**
		                         * Listens for clicks on the image, opens the weather bug website when pressed
		                         * (Required by WeatherBug).
		                         */
		                        public void onClick(View v) 
		                        {
		                            Uri uri = Uri.parse(webUrl);
		                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		                            startActivity(intent);
		                        }
		                    });

		                    // Gets the weather condition icon.
		                    int icon = parser.getImageForCondition((forecast.get(0).getIcon()));

		                    if (icon != -1)
		                    {
		                        icon1.setImageResource(icon);
		                        
		                    }
		                    else 
		                        icon1.setImageResource(R.drawable.unknown);
		                    String desc = forecast.get(0).getShortPrediction();
		                    if (desc != null)
		                        cond1.setText(desc);
		                    day1high = (TextView) findViewById(R.id.day_1_high);
		                    if (forecast.get(0).getHigh() == -1)
		                        day1high.setText("--");
		                    else
		                        day1high.setText(forecast.get(0).getHigh() + DEGREE_SYMBOL);            
		                    day1low = (TextView) findViewById(R.id.day_1_low);
		                    day1low.setText(forecast.get(0).getLow() + DEGREE_SYMBOL);
		                }
		                {
		                    day2 = (TextView) findViewById(R.id.day_2);
		                    day2.setText(forecast.get(1).getDay().toUpperCase());
		                    icon2 = (ImageView) findViewById(R.id.day_2_icon);
		                    cond2 = (TextView) findViewById(R.id.cond_2);
		                    icon2.setOnClickListener(new OnClickListener(){
		                        
		                        /**
		                         * Listens for clicks on the image, opens the weather bug website when pressed
		                         * (Required by WeatherBug).
		                         */
		                        public void onClick(View v) 
		                        {
		                            Uri uri = Uri.parse(webUrl);
		                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		                            startActivity(intent);
		                        }
		                    });

		                    // Gets the weather condition icon.
		                    int icon = parser.getImageForCondition((forecast.get(1).getIcon()));

		                    if (icon != -1)
		                    {
		                        icon2.setImageResource(icon);
		                        
		                    }
		                    else 
		                        icon2.setImageResource(R.drawable.unknown);
		                    String desc = forecast.get(1).getShortPrediction();
		                    if (desc != null)
		                        cond2.setText(desc);
		                    day2high = (TextView) findViewById(R.id.day_2_high);
		                    day2high.setText(forecast.get(1).getHigh() + DEGREE_SYMBOL);
		                    
		                    day2low = (TextView) findViewById(R.id.day_2_low);
		                    day2low.setText(forecast.get(1).getLow() + DEGREE_SYMBOL);
		                }
		                {
		                    day3 = (TextView) findViewById(R.id.day_3);
		                    day3.setText(forecast.get(2).getDay().toUpperCase());
		                    icon3 = (ImageView) findViewById(R.id.day_3_icon);
		                    cond3 = (TextView) findViewById(R.id.cond_3);

		                    icon3.setOnClickListener(new OnClickListener(){
		                        
		                        /**
		                         * Listens for clicks on the image, opens the weather bug website when pressed
		                         * (Required by WeatherBug).
		                         */
		                        public void onClick(View v) 
		                        {
		                            Uri uri = Uri.parse(webUrl);
		                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		                            startActivity(intent);
		                        }
		                    });

		                    // Gets the weather condition icon.
		                    int icon = parser.getImageForCondition((forecast.get(2).getIcon()));

		                    if (icon != -1)
		                    {
		                        icon3.setImageResource(icon);
		                        
		                    }
		                    else 
		                        icon3.setImageResource(R.drawable.unknown);
		                    String desc = forecast.get(2).getShortPrediction();
		                    if (desc != null)
		                        cond3.setText(desc);
		                    day3high = (TextView) findViewById(R.id.day_3_high);
		                    day3high.setText(forecast.get(2).getHigh() + DEGREE_SYMBOL);
		                    
		                    day3low = (TextView) findViewById(R.id.day_3_low);
		                    day3low.setText(forecast.get(2).getLow() + DEGREE_SYMBOL);
		                }

		                {
		                    day4 = (TextView) findViewById(R.id.day_4);
		                    day4.setText(forecast.get(3).getDay().toUpperCase());
		                    icon4 = (ImageView) findViewById(R.id.day_4_icon);
		                    cond4 = (TextView) findViewById(R.id.cond_4);

		                    icon4.setOnClickListener(new OnClickListener(){
		                        
		                        /**
		                         * Listens for clicks on the image, opens the weather bug website when pressed
		                         * (Required by WeatherBug).
		                         */
		                        public void onClick(View v) 
		                        {
		                            Uri uri = Uri.parse(webUrl);
		                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		                            startActivity(intent);
		                        }
		                    });

		                    // Gets the weather condition icon.
		                    int icon = parser.getImageForCondition((forecast.get(3).getIcon()));

		                    if (icon != -1)
		                    {
		                        icon4.setImageResource(icon);
		                        
		                    }
		                    else 
		                        icon4.setImageResource(R.drawable.unknown);
		                    String desc = forecast.get(3).getShortPrediction();
		                    if (desc != null)
		                        cond4.setText(desc);
		                    day4high = (TextView) findViewById(R.id.day_4_high);
		                    day4high.setText(forecast.get(3).getHigh() + DEGREE_SYMBOL);
		                    
		                    day4low = (TextView) findViewById(R.id.day_4_low);
		                    day4low.setText(forecast.get(3).getLow() + DEGREE_SYMBOL);
		                }

		                {
		                    day5 = (TextView) findViewById(R.id.day_5);
		                    day5.setText(forecast.get(4).getDay().toUpperCase());
		                    icon5 = (ImageView) findViewById(R.id.day_5_icon);
		                    cond5 = (TextView) findViewById(R.id.cond_5);

		                    icon5.setOnClickListener(new OnClickListener(){
		                        
		                        /**
		                         * Listens for clicks on the image, opens the weather bug website when pressed
		                         * (Required by WeatherBug).
		                         */
		                        public void onClick(View v) 
		                        {
		                            Uri uri = Uri.parse(webUrl);
		                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		                            startActivity(intent);
		                        }
		                    });

		                    // Gets the weather condition icon.
		                    int icon = parser.getImageForCondition((forecast.get(4).getIcon()));

		                    if (icon != -1)
		                    {
		                        icon5.setImageResource(icon);
		                        
		                    }
		                    else 
		                        icon5.setImageResource(R.drawable.unknown);
		                    String desc = forecast.get(4).getShortPrediction();
		                    if (desc != null)
		                        cond5.setText(desc);
		                    day5high = (TextView) findViewById(R.id.day_5_high);
		                    day5high.setText(forecast.get(4).getHigh() + DEGREE_SYMBOL);
		                    
		                    day5low = (TextView) findViewById(R.id.day_5_low);
		                    day5low.setText(forecast.get(4).getLow() + DEGREE_SYMBOL);
		                }
		                {
		                    day6 = (TextView) findViewById(R.id.day_6);
		                    day6.setText(forecast.get(5).getDay().toUpperCase());
		                    icon6 = (ImageView) findViewById(R.id.day_6_icon);
		                    cond6 = (TextView) findViewById(R.id.cond_6);

		                    icon6.setOnClickListener(new OnClickListener(){
		                        
		                        /**
		                         * Listens for clicks on the image, opens the weather bug website when pressed
		                         * (Required by WeatherBug).
		                         */
		                        public void onClick(View v) 
		                        {
		                            Uri uri = Uri.parse(webUrl);
		                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		                            startActivity(intent);
		                        }
		                    });

		                    // Gets the weather condition icon.
		                    int icon = parser.getImageForCondition((forecast.get(5).getIcon()));

		                    if (icon != -1)
		                    {
		                        icon6.setImageResource(icon);
		                        
		                    }
		                    else 
		                        icon6.setImageResource(R.drawable.unknown);
		                    String desc = forecast.get(5).getShortPrediction();
		                    if (desc != null)
		                        cond6.setText(desc);
		                    day6high = (TextView) findViewById(R.id.day_6_high);
		                    day6high.setText(forecast.get(5).getHigh() + DEGREE_SYMBOL);
		                    
		                    day6low = (TextView) findViewById(R.id.day_6_low);
		                    day6low.setText(forecast.get(5).getLow() + DEGREE_SYMBOL);
		                }

		                {
		                    day7 = (TextView) findViewById(R.id.day_7);
		                    day7.setText(forecast.get(6).getDay().toUpperCase());
		                    icon7 = (ImageView) findViewById(R.id.day_7_icon);
		                    cond7 = (TextView) findViewById(R.id.cond_7);

		                    icon7.setOnClickListener(new OnClickListener(){
		                        
		                        /**
		                         * Listens for clicks on the image, opens the weather bug website when pressed
		                         * (Required by WeatherBug).
		                         */
		                        public void onClick(View v) 
		                        {
		                            Uri uri = Uri.parse(webUrl);
		                            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		                            startActivity(intent);
		                        }
		                    });

		                    // Gets the weather condition icon.
		                    int icon = parser.getImageForCondition((forecast.get(6).getIcon()));

		                    if (icon != -1)
		                    {
		                        icon7.setImageResource(icon);
		                        
		                    }
		                    else 
		                        icon7.setImageResource(R.drawable.unknown);
		                    String desc = forecast.get(6).getShortPrediction();
		                    if (desc != null)
		                        cond7.setText(desc);
		                    day7high = (TextView) findViewById(R.id.day_7_high);
		                    day7high.setText(forecast.get(6).getHigh() + DEGREE_SYMBOL);
		                    
		                    day7low = (TextView) findViewById(R.id.day_7_low);
		                    if (forecast.get(6).getLow() == -1)
		                        day7low.setText("--");
		                    else
		                        day7low.setText(forecast.get(6).getLow()+ DEGREE_SYMBOL);
		                }
		                TextView info = (TextView) findViewById(R.id.weather_info);
		                info.setText("Weather data ©2012 WeatherBug\n" + "Weather icons from Dotvoid");
		                info.setOnClickListener(new OnClickListener() {
		                    
		                    /**
		                     * Listens for clicks on the image, opens the weather bug website when pressed
		                     * (Required by WeatherBug).
		                     */
		                    public void onClick(View v) 
		                    {
		                        Uri uri = Uri.parse("http://www.dotvoid.se");
		                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
		                        startActivity(intent);
		                    }
		                });
		            }
		        });
		    }
		}).start();
	
		
		
	}
}

