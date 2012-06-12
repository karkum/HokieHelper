package org.mad.app.hokiehelper;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * Initial dashboard activity that is shown to user when the app
 * is launched.
 * 
 * @author Karthik
 */
public class HokieHelperActivity extends SherlockActivity {

    private final String DEGREE_SYMBOL = "\u00B0";

    public static ArrayList<Twitter_Tweet> list = new ArrayList<Twitter_Tweet>();
    private String[] finalListOfTweets;
    private int[] prof_pics;
    private String[] user_names;
    private String[] times;
    private String[] screen_names;
    private ProgressBar progBar;
    private TextView loading;
    private Handler handler = new Handler();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        View l = findViewById(R.id.lin_layout);
        l.setBackgroundResource(R.drawable.mainpage_background);
        list = new ArrayList<Twitter_Tweet>();
        
        // Hide the action bar
        getSupportActionBar().hide();

        ImageView topPic = (ImageView)findViewById(R.id.topimage);
        topPic.setVisibility(View.INVISIBLE);
        ImageView dd = (ImageView)findViewById(R.id.img_dining);
        ImageView inf = (ImageView)findViewById(R.id.img_info);
        ImageView twf = (ImageView)findViewById(R.id.img_twitterfeed);
        ImageView maps = (ImageView)findViewById(R.id.img_maps);
        progBar = (ProgressBar) findViewById(R.id.progress_bar_main);
        loading = (TextView) findViewById(R.id.loading_text);
        dd.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                launchActivity(0);
            }
        });

        maps.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                launchActivity(1);
            }
        });

        twf.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                launchActivity(2);
            }
        });

        inf.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                launchActivity(3);
            }
        });

        if (isNetworkAvailable()) {
            final LinearLayout weatherLayout = (LinearLayout) findViewById(R.id.weatherLayout);
            final Weather_XMLParser parser = new Weather_XMLParser();
            new Thread(new Runnable() {
                public void run() {
                    parser.doCurrentConditions();

                    handler.post(new Runnable() {
                        public void run() {
                            String iconURL = parser.getCurrentWeather().getIcon();
                            ImageView imView = (ImageView) findViewById(R.id.weatherIcon);
                            TextView conditionTv = (TextView) findViewById(R.id.weatherCondition);
                            TextView temperatureTv = (TextView) findViewById(R.id.weatherHighLow);
                            weatherLayout.setOnClickListener(new OnClickListener() {

                                /**
                                 * Listens for clicks on the image, opens the weather bug
                                 * website when pressed (Required by WeatherBug).
                                 */
                                public void onClick(View v) {
                                    createWeather();
                                }
                            });

                            // Gets the weather condition icon.
                            int icon = parser.getImageForCondition(iconURL);
                            String condition = parser.getCurrentWeather().getDescription();
                            int temperature = parser.getCurrentWeather().getTemperature();

                            if (icon != R.drawable.unknown) {
                                imView.setImageResource(icon);

                            } else {
                                // if you cant find an images, just dont show anything.
                                imView.setVisibility(View.INVISIBLE);
                            }
                            if (condition != null) {
                                conditionTv.setText(condition);
                                temperatureTv.setText(temperature + DEGREE_SYMBOL);

                            }
                        }
                    });
                }
            }).start();
        }
    }
    /**
     * Private method that fires an intent to start the weather display activity
     */
    private void createWeather() {
        if (isNetworkAvailable()) {
            Intent i = new Intent(HokieHelperActivity.this,
                    Weather_MainActivity.class);
            double[] infoArr = new double[2];
            infoArr[0] = Weather_XMLParser.LAT;
            infoArr[1] = Weather_XMLParser.LONG;
            i.putExtra("new_weather_request", infoArr);
            startActivity(i);
        }
        else {
            Toast.makeText(HokieHelperActivity.this, "No network connection available", Toast.LENGTH_SHORT).show();
        }
    }

    private void launchActivity(int position) {
        Intent i;
        switch (position) {
        case 0:
            i = new Intent(HokieHelperActivity.this, Dining_HallDietChooserActivity.class);
            startActivity(i);
            break;
        case 1:
            i = new Intent(HokieHelperActivity.this, Maps_MainActivity.class);
            startActivity(i);
            break;
        case 2:
            // When user clicks on the TwitterFeed icon, we spawn a new thread
            // to get the tweets.
            if (isNetworkAvailable()) {
                new Thread(new Runnable() {
                    public void run() {
                        handler.post(new Runnable() {
                            public void run() {
                                // First open up the loading bar, to show user we are getting tweets
                                progBar.setVisibility(0);
                                loading.setVisibility(0);
                            }
                        });
                        // We get tweets only every 10 minutes (minimizes network usage)
                        //						if (lastPinged == null || list == null || list.size() == 0
                        //								|| now.getSeconds() - lastPinged.getSeconds() > WAIT_TIME) {
                        //							lastPinged = new Date();
                        doWork();
                        //						}

                        // ---hides the progress bar---
                        handler.post(new Runnable() {
                            public void run() {
                                // ---0 - VISIBLE; 4 - INVISIBLE; 8 - GONE---
                                progBar.setVisibility(View.INVISIBLE);
                                loading.setVisibility(View.INVISIBLE);
                                if (list == null || list.size() != 0){
                                    Intent i = new Intent(HokieHelperActivity.this,
                                            Twitter_FeedActivity.class);
                                    // Creates a new activity with the tweets
                                    //							if (list.size() != 0) {
                                    i.putExtra("tweets", finalListOfTweets);
                                    i.putExtra("pics", prof_pics);
                                    i.putExtra("user_names", user_names);
                                    i.putExtra("times", times);
                                    i.putExtra("screen_names", screen_names);
                                    startActivity(i);
                                }
                                else {
                                    Toast.makeText(HokieHelperActivity.this,
                                            "Error. Could not retrieve tweets.", Toast.LENGTH_SHORT).show();
                                }
                                //							}
                            }
                        });
                    }

                    private boolean doWork() {
                        try {
                            update();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                }).start();
            }
            else {
                Toast.makeText(HokieHelperActivity.this, "No Network Connection Available", Toast.LENGTH_LONG).show();
            }
            break;
        case 3:
            i = new Intent(HokieHelperActivity.this, Info_MainActivity.class);
            startActivity(i);
            break;
        }

    }

    /**
     * Checks if the network is available
     * 
     * @return
     */
    private boolean isNetworkAvailable() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        // if no network is available networkInfo will be null, otherwise check
        // if we are connected
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;
        }
        return false;
    }

    /**
     * Updates the tweets. The commented out stuff show how to use my client for
     * a search term or getting tweets from a user. After getting tweets, we update
     * our arrays. We also look for poof. pics in our app, we dont download each time.
     */
    protected void update() {
        if (!isNetworkAvailable()) {
            Toast.makeText(HokieHelperActivity.this,
                    "No Network Connection. Please try again later",
                    Toast.LENGTH_SHORT).show();
        } else {
            Twitter_UserConnection c = new Twitter_UserConnection();
            list = c.connect();
            if (list.size() != 0) {
                finalListOfTweets = new String[list.size()];
                prof_pics = new int[list.size()];
                user_names = new String[list.size()];
                screen_names = new String[list.size()];
                times = new String[list.size()];
                for (int i = 0; i < list.size(); i++) {
                    Twitter_User u = list.get(i).getFrom_user();
                    finalListOfTweets[i] = list.get(i).getText();
                    if (u.getScreen_name().equals("@CollegiateTimes"))
                        prof_pics[i] = R.drawable.collegiate_times;
                    else if (u.getScreen_name().equals("@vtnews"))
                        prof_pics[i] = R.drawable.vt_news;
                    else if (u.getScreen_name().equals("@hokiesbuzztap"))
                        prof_pics[i] = R.drawable.hokies_buzz_tap;
                    else if (u.getScreen_name().equals("@BR_VTHokies"))
                        prof_pics[i] = R.drawable.br_vt_hokies;
                    else if (u.getScreen_name().equals("@virginia_tech"))
                        prof_pics[i] = R.drawable.virginia_tech;
                    else if (u.getScreen_name().equals("@VT_Football"))
                        prof_pics[i] = R.drawable.vt_football;
                    else if (u.getScreen_name().equals("@vt_edu"))
                        prof_pics[i] = R.drawable.vt_edu;
                    else if (u.getScreen_name().equals("@TechSideline"))
                        prof_pics[i] = R.drawable.tech_sideline;
                    else if (u.getScreen_name().equals("@vtsga"))
                        prof_pics[i] = R.drawable.vt_sga;
                    else if (u.getScreen_name().equals("@VTEngineering"))
                        prof_pics[i] = R.drawable.vt_engineering;
                    else if (u.getScreen_name().equals("@VTHokiesSports"))
                        prof_pics[i] = R.drawable.vt_hokies_sports;
                    else if (u.getScreen_name().equals("@Blacksburg_Gov"))
                        prof_pics[i] = R.drawable.blacksburg_gov;
                    else if (u.getScreen_name().equals("@thevtu"))
                        prof_pics[i] = R.drawable.the_vtu;
                    else if (u.getScreen_name().equals("@VTRHF"))
                        prof_pics[i] = R.drawable.vt_rhf;
                    else if (u.getScreen_name().equals("@BlacksburgStuff"))
                        prof_pics[i] = R.drawable.blacksburg_stuff;

                    user_names[i] = u.getUser_name();
                    screen_names[i] = u.getScreen_name();
                    times[i] = list.get(i).getTime();
                }
            } else {
                Toast.makeText(HokieHelperActivity.this,
                        "Error. Could not retrieve tweets.", Toast.LENGTH_SHORT).show();
            }
        }
    }

}