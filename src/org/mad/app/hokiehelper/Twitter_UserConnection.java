package org.mad.app.hokiehelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;
import twitter4j.internal.http.HttpClientImpl;
import twitter4j.internal.http.HttpParameter;
import twitter4j.internal.http.HttpRequest;
import twitter4j.internal.http.HttpResponse;
import twitter4j.internal.http.RequestMethod;

@SuppressWarnings({ "unused" })
/**
 * The actual connection class that I use to get tweets. Requires the twitter4j library.
 * @author karthik
 *
 */
public class Twitter_UserConnection {

	public String pin = "";
	public Twitter twitter;
	public RequestToken requestToken;

	// GOT these by registering app on twitter:
	private final String CONSUMER_KEY = "HNLPTt93WzEIBWnYccIdSw";
	private final String SECRET_KEY = "vtYhyRIvHYSpe8b3H514MK8wszsJDdbI1WFtq1cPOmw";
	private final String USER_NAME = "app_testing_11";
	private final String PASSWORD = "virginiatech";

	private static byte[] sBuffer = new byte[512];

	/**
	 * This method connects to the Twitter api to get the tweets from an account
	 * created just for the purpose of this app. It sends an authentication
	 * request and silently authenticates without any input from the user. It
	 * gets the HTML page from the authentication request and modifies by adding
	 * in the necessary. Then, it POSTs this back to twitter so we are logged
	 * in. Then, it uses the twitter4j library to get the home timeline.
	 * 
	 * @return
	 */
	public ArrayList<Twitter_Tweet> connect() {
		ArrayList<Twitter_Tweet> list = new ArrayList<Twitter_Tweet>();
		RequestToken rt;
		twitter = new TwitterFactory().getInstance();
		HttpClientImpl http = new HttpClientImpl();
		HttpResponse response;
		String resStr;
		String authorizeURL;
		HttpParameter[] params;
		AccessToken at;
		String cookie;
		Twitter_JSONParser parser = new Twitter_JSONParser();
		try {

			// browser client - not requiring pin / overriding callback url

			twitter = new TwitterFactory().getInstance();
			twitter.setOAuthConsumer(CONSUMER_KEY, SECRET_KEY);
//			twitter.setOAuthAccessToken(new AccessToken("179973427-AkCzCfikrpbRKkb5BuHyO73wFpLYBY5w06J5ZMI8", "rlcuFHmuT8NJfg55xZGnVrqV4289HliVIVsSVeUFw"));
			rt = twitter.getOAuthRequestToken();
//			rt = new RequestToken("179973427-AkCzCfikrpbRKkb5BuHyO73wFpLYBY5w06J5ZMI8", "rlcuFHmuT8NJfg55xZGnVrqV4289HliVIVsSVeUFw");

			response = http.get(rt.getAuthorizationURL());
			Map<String, String> props = new HashMap<String, String>();
			cookie = response.getResponseHeader("Set-Cookie");
			props.put("Cookie", cookie);
			resStr = response.asString();
			// authorizeURL = catchPattern(resStr, "<form action=\"",
			// "\" id=\"login_form\"");
			authorizeURL = catchPattern(resStr, "<form action=\"",
					"\" id=\"oauth_form\"");
			params = new HttpParameter[4];
			params[0] = new HttpParameter("authenticity_token", catchPattern(
					resStr, "\"authenticity_token\" type=\"hidden\" value=\"",
					"\" />"));
			params[1] = new HttpParameter("oauth_token", catchPattern(resStr,
					"name=\"oauth_token\" type=\"hidden\" value=\"", "\" />"));
			params[2] = new HttpParameter("session[username_or_email]",
					USER_NAME);
			params[3] = new HttpParameter("session[password]", PASSWORD);

			response = http.request(new HttpRequest(RequestMethod.POST,
					authorizeURL, params, null, props));
			resStr = response.asString();
			String pin = catchPattern(resStr, "oauth_verifier=", "\"");
			at = twitter.getOAuthAccessToken(rt, pin);

			twitter.setOAuthAccessToken(at);

			ResponseList<Status> tweets = twitter.getHomeTimeline();
			SimpleDateFormat format = new SimpleDateFormat("h:mm a");
			for (int j = 0; j < tweets.size(); j++) {
				Status s = tweets.get(j);
				Date now = new Date();
				Date tweetedAt = s.getCreatedAt();
				long secs = (now.getTime() - tweetedAt.getTime()) / 1000;
				int hDiff = (int) (secs/3600);
				String hoursDiff = "";
				if (hDiff == 1)
					hoursDiff = hDiff + " hour ago";
				else if (hDiff == 0)
					hoursDiff = "< 1 hour ago";
				else 
					hoursDiff = hDiff + " hours ago";
				Twitter_User u = new Twitter_User(s.getUser().getScreenName(), Long.toString(s.getUser().getId()), null, s.getUser().getName());
				Twitter_Tweet t = new Twitter_Tweet(s.getCreatedAt().toString(), u, s.getText(), Long.toString(s.getId()), hoursDiff);
				list.add(t);
			}
			return list;
		} catch (Exception ex) {
			return null;
		}

	}

	private static String catchPattern(String body, String before, String after) {
		int beforeIndex = body.indexOf(before);
		int afterIndex = body.indexOf(after, beforeIndex);
		return body.substring(beforeIndex + before.length(), afterIndex);
	}

}
