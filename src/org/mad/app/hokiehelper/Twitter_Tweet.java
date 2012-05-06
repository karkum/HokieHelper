package org.mad.app.hokiehelper;

/**
 * A simple class that represents a tweet object. It has information about the tweet.
 * @author karthik
 *
 */
public class Twitter_Tweet {
	private String created_at;
	private Twitter_User from_user;
	private String text;
	private String tweet_id_str;
	private String time;
	public Twitter_Tweet(String created_at2, Twitter_User user, String text2,
			String tweet_id_str2, String time) {
		setCreated_at(created_at2);
		setFrom_user(user);
		setText(text2);
		setTweet_id_str(tweet_id_str2);
		setTime(time);
	}
	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}
	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}
	/**
	 * @return the created_at
	 */
	public String getCreated_at() {
		return created_at;
	}
	/**
	 * @param created_at the created_at to set
	 */
	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
	/**
	 * @return the from_user
	 */
	public Twitter_User getFrom_user() {
		return from_user;
	}
	/**
	 * @param from_user the from_user to set
	 */
	public void setFrom_user(Twitter_User from_user) {
		this.from_user = from_user;
	}
	/**
	 * @return the text
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text the text to set
	 */
	public void setText(String text) {
		this.text = text;
	}
	/**
	 * @return the tweet_id_str
	 */
	public String getTweet_id_str() {
		return tweet_id_str;
	}
	/**
	 * @param tweet_id_str the tweet_id_str to set
	 */
	public void setTweet_id_str(String tweet_id_str) {
		this.tweet_id_str = tweet_id_str;
	}
	
}
