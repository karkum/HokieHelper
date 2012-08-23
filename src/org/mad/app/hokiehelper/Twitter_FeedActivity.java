package org.mad.app.hokiehelper;

import java.util.ArrayList;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

/**
 * Main activity for the twitter feed.
 * 
 * @author karthik
 * 
 */
public class Twitter_FeedActivity extends SherlockListActivity implements Runnable {

	private String[] finalListOfTweets;
	private int[] prof_pics;
	private String[] user_names;
	private String[] times;
	private String[] screen_names;
	@SuppressWarnings("unused")
	private MyArrayAdapter stringAdapter;
	private ArrayList<Twitter_Tweet> list = new ArrayList<Twitter_Tweet>();
	private ProgressDialog dialogProgressBar;
	private ListView lv;

	/** Called when the activity is first created. */
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.twitterfeed_main);

		// Sets up action bar title/navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle("Twitter Feed");
		lv = getListView();
		loadLatestTweets();
	}
	
	/**
	 * Shows a dialog box and calls a thread to load the newest tweets.
	 */
	public void loadLatestTweets() {
		// Runs the tweet loading in a foreground thread
		// to let the user know something is happening
		dialogProgressBar = ProgressDialog.show(this, "Fetching tweets...",
				"Getting the latest Virginia Tech news from Twitter...");

		Thread thread = new Thread(this);
		thread.start();
	}

	/**
	 * Downloads the latest tweets in a foreground thread.
	 */
	public void run() {
		update();
		handler.sendEmptyMessage(0);
	}

	/**
	 * Closes the progress dialog and sets up the listview
	 * once the tweets are finished downloading.
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			setListAdapter(new MyArrayAdapter(
					Twitter_FeedActivity.this,
					R.layout.twitterfeed_list_item,
					finalListOfTweets));
			
			lv.setTextFilterEnabled(true);
			
			View parent = (View) lv.getParent();
			parent.setBackgroundResource(R.drawable.bg_tan);

			lv.setOnItemClickListener(new OnItemClickListener() {
				public void onItemClick(AdapterView<?> parent, View v,
						int position, long id) {
					goToTwitter(position);
				}
			});
			
			dialogProgressBar.dismiss();

		}
	};

	/**
	 * Creates the action bar icons.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.actionbar_twitterfeed, menu);
		return true;
	}

	/**
	 * Handles the clicking of action bar icons.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.actionbar_twitterfeed_refresh:
			loadLatestTweets();
			return true;
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * When you click on a tweet, it takes you to twitter's main page.
	 * 
	 * @param position
	 */
	private void goToTwitter(int position) {
		if (list != null) {
			Twitter_ClientImpl client = new Twitter_ClientImpl();
			String webUrl = client.getLinkToTweet(list.get(position)
					.getFrom_user().getUser_id_str(), list.get(position)
					.getTweet_id_str());
			Uri uri = Uri.parse(webUrl);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}
	}

	/**
	 * Updates the tweets. The commented out stuff show how to use my client for
	 * a search term or getting tweets from a user.
	 */
	protected void update() {
		if (!isNetworkAvailable()) {
			Toast.makeText(Twitter_FeedActivity.this,
					"No Network Connection. Please try again later",
					Toast.LENGTH_LONG).show();
		} else {
			// The below is how to use the client(for reference in future
			// version of app)
			/*
			 * TwitterClientImpl client = new TwitterClientImpl(); final User
			 * COLLEGIATE_TIMES = new User("CollegiateTimes", "45960001",
			 * client.getProfileImage("CollegiateTimes", "mini"));
			 * ArrayList<Tweet> listAboutVT = client.search("virginia tech",
			 * null, "recent", true); ArrayList<Tweet> byCT =
			 * client.getTweetsFromUser(COLLEGIATE_TIMES, 20, true, true);
			 * listAboutVT.addAll(byCT); Date date = new Date();
			 * SimpleDateFormat format = new SimpleDateFormat( "h:mm a");
			 * TextView refreshStatus = (TextView)
			 * findViewById(R.id.lastUpdatedStatus);
			 * refreshStatus.setText("Current as of " + format.format(date));
			 */
			Twitter_UserConnection c = new Twitter_UserConnection();
			list = c.connect();
			if (list != null) {

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

					// prof_pics[i] = u.getProf_img_url();
					user_names[i] = u.getUser_name();
					screen_names[i] = u.getScreen_name();
					times[i] = list.get(i).getTime();
				}
			} else {
				TextView msg = (TextView) findViewById(R.id.lastUpdatedStatus);
				msg.setText("Error. Cound not retrieve tweets");
			}
		}
	}

	/**
	 * Checks whether or not there is a data connection.
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
	 * Converts the obtained tweets into listview items.
	 */
	private class MyArrayAdapter extends ArrayAdapter<String> {
		@SuppressWarnings("unused")
		private String[] strings;

		public MyArrayAdapter(Context context, int textViewResourceId,
				String[] strings) {
			super(context, textViewResourceId, strings);
			this.strings = strings;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View v = convertView;
			if (v == null) {
				LayoutInflater vi = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.twitterfeed_list_item, null);
			}
			TextView tweetText = (TextView) v.findViewById(R.id.tweet_text);
			tweetText.setText(finalListOfTweets[position]);

			TextView tweetedByScreenName = (TextView) v
					.findViewById(R.id.screen_name);
			tweetedByScreenName.setText(screen_names[position]);

			TextView tweetedBy = (TextView) v.findViewById(R.id.user_name);
			tweetedBy.setText(user_names[position]);

			TextView tweetedAt = (TextView) v.findViewById(R.id.when);
			tweetedAt.setText(times[position]);

			ImageView iv = (ImageView) v.findViewById(R.id.prof_pic);
			// iv.setImageDrawable(prof_pics[position]);
			// Bitmap bm = prof_pics[position];
			iv.setImageResource(prof_pics[position]);
			// iv.setImageBitmap(prof_pics[position]);
			return v;
		}
	}
}