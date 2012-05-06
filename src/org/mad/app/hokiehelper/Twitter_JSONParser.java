package org.mad.app.hokiehelper;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

/**
 * This parses twitter information. It requires a JSON format input and outputs tweets
 * and users.
 * @author karthik
 *
 */
public class Twitter_JSONParser {

	/**
	 * Gets a list of tweets from a certain JSON result that has tweets
	 * @param str
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Twitter_Tweet> parseSearchByString(String str) throws Exception {
		ArrayList<Twitter_Tweet> list = new ArrayList<Twitter_Tweet>();
		JSONObject jObject = new JSONObject(str);
		JSONArray results = jObject.getJSONArray("results");
		for (int i = 0; i < results.length(); i++) {
			Twitter_Tweet tweet;
			Twitter_User user;
			JSONObject tweetInfo = results.getJSONObject(i);
			String created_at = tweetInfo.getString("created_at");

			String screen_name = tweetInfo.getString("from_user");
			String user_id_str = tweetInfo.getString("from_user_id_str");
			String url = "https://api.twitter.com/1/users/profile_image?screen_name=" +
					screen_name + "&size=mini";
			Bitmap bm = downloadFile(url);
			user = new Twitter_User(screen_name, user_id_str, bm, null);

			String text = tweetInfo.getString("text");
			String tweet_id_str = tweetInfo.getString("id_str");
			tweet = new Twitter_Tweet(created_at, user, text, tweet_id_str, null);
			list.add(tweet);
		}
		return list;
	}

	/**
	 * Returns a list of tweets that a certain user has posted.
	 * @param str
	 * @param user
	 * @return
	 * @throws Exception
	 */
	public ArrayList<Twitter_Tweet> parseSearchByUser(String str, Twitter_User user)
			throws Exception {
		ArrayList<Twitter_Tweet> list = new ArrayList<Twitter_Tweet>();
		JSONArray results = new JSONArray(str);
		for (int i = 0; i < results.length(); i++) {
			Twitter_Tweet tweet;
			JSONObject tweetInfo = results.getJSONObject(i);
			String created_at = tweetInfo.getString("created_at");

			String text = tweetInfo.getString("text");
			String tweet_id_str = tweetInfo.getString("id_str");
			tweet = new Twitter_Tweet(created_at, user, text, tweet_id_str, null);
			list.add(tweet);
		}
		return list;
	}

	/**
	 * This method downloads the profile icon for a user given the correct url
	 * 
	 * @param fileUrl
	 *            the url of the file
	 * @return
	 */
	public Bitmap downloadFile(String fileUrl) {
		Bitmap bm = null;
		try {
			URL aURL = new URL(fileUrl);
			URLConnection conn = aURL.openConnection();
			conn.connect();
			InputStream is = conn.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			bm = BitmapFactory.decodeStream(bis);
			bis.close();
			is.close();
		} catch (IOException e) {
			return null;
		}
		return bm;
	}
}
