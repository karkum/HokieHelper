package org.mad.app.hokiehelper;

import java.util.ArrayList;

import android.graphics.Bitmap;

/**
 * This class is the client for all twitter related operations. Error cases are not
 * handled gracefully (thats what all the todo's say). This will be used in future
 * versions of the app to give user more control.
 * 
 * @author karthik
 * 
 */
public class Twitter_ClientImpl {
	private Twitter_Connection connect;
	private Twitter_JSONParser parser;
	public static final int SEARCH = 0;
	public static final int USER = 1;
	public static final int LINK = 2;

	public Twitter_ClientImpl() {
		parser = new Twitter_JSONParser();
	}

	/**
	 * Returns tweets that match a specified query.
	 * 
	 * @param query
	 *            Search query. Should be URL encoded. Queries will be limited
	 *            by complexity Example Values: "@noradio"
	 * 
	 * @param geo
	 *            Returns tweets by users located within a given radius of the
	 *            given latitude/longitude. The location is preferentially
	 *            taking from the Geotagging API, but will fall back to their
	 *            Twitter profile. The parameter value is specified by
	 *            "latitude,longitude,radius", where radius units must be
	 *            specified as either "mi" (miles) or "km" (kilometers). Note
	 *            that you cannot use the near operator via the API to geocode
	 *            arbitrary locations; however you can use this geocode
	 *            parameter to search near geocodes directly. Example Values:
	 *            37.781157,-122.398720,1mi
	 * 
	 * @param result_type
	 *            Optional. Specifies what type of search results you would
	 *            prefer to receive. The current default is "mixed." Valid
	 *            values include: mixed: Include both popular and real time
	 *            results in the response. recent: return only the most recent
	 *            results in the response popular: return only the most popular
	 *            results in the response. Example Values: mixed, recent,
	 *            popular
	 * 
	 * @param include_entities
	 *            When set to either true, t or 1, each tweet will include a
	 *            node called "entities,". This node offers a variety of
	 *            metadata about the tweet in a discreet structure, including:
	 *            urls, media and hashtags. Note that user mentions are
	 *            currently not supported for search and there will be no
	 *            "user_mentions" key in the entities map. See Tweet Entities
	 *            for more detail on entities. Please note that entities are
	 *            only available for JSON responses in the Search API. Example
	 *            Values: true
	 * 
	 * @return A list of tweets matching this query
	 */
	public ArrayList<Twitter_Tweet> search(String query, Twitter_Geocode geo,
			String result_type, boolean include_entities) {
		if (query == null || query.length() == 0) {
			// ERROR
			// TODO Handle
			return null;
		} else {
			String url = "http://search.twitter.com/search.json?q=";
			String newQuery = query.replaceAll(" ", "%20").replaceAll("@",
					"%40");
			StringBuilder builder = new StringBuilder(url);
			builder.append(newQuery);
			if (geo != null) {
				builder.append("&geocode=");
				builder.append(geo.getLatitude() + "," + geo.getLongitude()
						+ "," + geo.getRadius());
			}
			if (!result_type.contains("mixed")
					&& !result_type.contains("recent")
					&& !result_type.contains("popular")) {
				// ERROR. //TODO handle
			} else {
				builder.append("&result_type=");
				builder.append(result_type);
			}
			if (include_entities) {
				builder.append("&include_entities=");
				builder.append("t");
			}
			url = builder.toString();
			connect = new Twitter_Connection(url);
			if (connect.getStatus() != 200)
				;// ERROR //TODO
			String result = connect.getResult();
			parser = new Twitter_JSONParser();
			try {
				ArrayList<Twitter_Tweet> list = parser.parseSearchByString(result);
				return list;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}
	}

	/**
	 * Returns the 20 most recent statuses posted by the authenticating user. It
	 * is also possible to request another user's timeline by using the
	 * screen_name or user_id parameter. The other users timeline will only be
	 * visible if they are not protected, or if the authenticating user's follow
	 * request was accepted by the protected user.
	 * 
	 * The timeline returned is the equivalent of the one seen when you view a
	 * user's profile on twitter.com.
	 * 
	 * This method can only return up to 3,200 of a user's most recent statuses.
	 * Native retweets of other statuses by the user is included in this total,
	 * regardless of whether include_rts is specified when requesting this
	 * resource.
	 * 
	 * This method will not include retweets in the XML and JSON responses
	 * unless the include_rts parameter is set. The RSS and Atom responses will
	 * always include retweets as statuses prefixed with RT.
	 * 
	 * @param user_id
	 *            The ID of the user for whom to return results for. Helpful for
	 *            disambiguating when a valid user ID is also a valid screen
	 *            name. Example Values: 12345 Note:: Specifies the ID of the
	 *            user to befriend. Helpful for disambiguating when a valid user
	 *            ID is also a valid screen name.
	 * 
	 * @param screen_name
	 *            The screen name of the user for whom to return results for.
	 *            Helpful for disambiguating when a valid screen name is also a
	 *            user ID. Example Values: noradio
	 * 
	 * @param count
	 *            Specifies the number of tweets to try and retrieve, up to a
	 *            maximum of 200. The value of count is best thought of as a
	 *            limit to the number of tweets to return because suspended or
	 *            deleted content is removed after the count has been applied.
	 *            We include retweets in the count, even if include_rts is not
	 *            supplied. It is recommended you always send include_rts=1 when
	 *            using this API method.
	 * 
	 * @param include_rts
	 *            When set to either true, t or 1,the timeline will contain
	 *            native retweets (if they exist) in addition to the standard
	 *            stream of tweets. The output format of retweeted tweets is
	 *            identical to the representation you see in home_timeline.
	 *            Note: If you're using the trim_user parameter in conjunction
	 *            with include_rts, the retweets will still contain a full user
	 *            object. Example Values: true
	 * 
	 * @param include_entities
	 *            When set to either true, t or 1, each tweet will include a
	 *            node called "entities,". This node offers a variety of
	 *            metadata about the tweet in a discreet structure, including:
	 *            user_mentions, urls, and hashtags. While entities are opt-in
	 *            on timelines at present, they will be made a default component
	 *            of output in the future. See Tweet Entities for more detail on
	 *            entities. Example Values: true
	 * 
	 * @param exclude_replies
	 *            This parameter will prevent replies from appearing in the
	 *            returned timeline. Using exclude_replies with the count
	 *            parameter will mean you will receive up-to count tweets — this
	 *            is because the count parameter retrieves that many tweets
	 *            before filtering out retweets and replies. Example Values:
	 *            true
	 * 
	 * @return A list of tweets by the specified user matching the given
	 *         parameters
	 */
	public ArrayList<Twitter_Tweet> getTweetsFromUser(Twitter_User user, int count,
			boolean include_rts, boolean include_entities) {
		String user_id = user.getUser_id_str();
		String screen_name = user.getScreen_name();
		if (user_id == null && screen_name == null) {
			// ERROR
			// TODO Handle
			return null;
		} else {
			String url = "https://api.twitter.com/1/statuses/user_timeline.json?";
			StringBuilder builder = new StringBuilder(url);

			if (user_id != null) {
				builder.append("&user_id=");
				builder.append(user_id.replaceAll(" ", "%20"));
			} else if (screen_name != null) {
				builder.append("&screen_name=");
				builder.append(screen_name.replaceAll(" ", "%20"));
			}

			if (count != 0) {
				builder.append("&count=").append(count);
			}
			if (include_rts) {
				builder.append("&include_rts=").append("t");
			}
			if (include_entities) {
				builder.append("&include_entities=t");
			}
			url = builder.toString();
			connect = new Twitter_Connection(url);
			if (connect.getStatus() != 200)
				;// ERROR //TODO
			String result = connect.getResult();
			parser = new Twitter_JSONParser();
			try {
				ArrayList<Twitter_Tweet> list = parser.parseSearchByUser(result, user);
				return list;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}

		}

	}

	/**
	 * Access the profile image in various sizes for the user with the indicated
	 * screen_name. If no size is provided the normal image is returned.
	 * 
	 * This resource does not return JSON or XML, but instead returns a 302
	 * redirect to the actual image resource.
	 * 
	 * This method should only be used by application developers to lookup or
	 * check the profile image URL for a user. This method must not be used as
	 * the image source URL presented to users of your application.
	 * 
	 * @param screen_nameThe
	 *            screen name of the user for whom to return results for.
	 *            Helpful for disambiguating when a valid screen name is also a
	 *            user ID.
	 * 
	 *            Example Values: noradio
	 * @param size
	 *            Specifies the size of image to fetch. Not specifying a size
	 *            will give the default, normal size of 48px by 48px. Valid
	 *            options include:
	 * 
	 *            bigger - 73px by 73px normal - 48px by 48px mini - 24px by
	 *            24px original - undefined. This will be the size the image was
	 *            originally uploaded in. The filesize of original images can be
	 *            very big so use this parameter with caution. Example Values:
	 *            bigger
	 * @return Bitmap the image of the user
	 */
	public Bitmap getProfileImage(String screen_name, String size) {
		if (screen_name == null)
			return null; // TODO
		else {
			String url = "https://api.twitter.com/1/users/profile_image?screen_name="
					+ screen_name + "&size=" + size;
			return parser.downloadFile(url);
		}
	}

	/**
	 * Returns a direct link to a tweet. The format is:
	 * http://twitter.com/<user_id_str>/status/<tweet_id>
	 * 
	 * @param user_id_str
	 *            The id of the user who we want the tweet from
	 * @param tweet_id
	 *            The id of the actual tweet
	 * @return Returns the direct linkt to tweet.
	 */
	public String getLinkToTweet(String user_id_str, String tweet_id) {
		return "http://twitter.com/" + user_id_str + "/status/" + tweet_id;
	}

}
