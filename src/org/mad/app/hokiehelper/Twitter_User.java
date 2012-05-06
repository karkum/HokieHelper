package org.mad.app.hokiehelper;

import android.graphics.Bitmap;
/**
 * Represents a twitter user
 * @author karthik
 *
 */
public class Twitter_User {
	private String screen_name;
	private String user_id_str;
	private Bitmap prof_img_url;
	private String user_name;
	public Twitter_User(String screen_name2, String user_id_str2, Bitmap prof_img_url2, String user_name) {
		setScreen_name("@" + screen_name2);
		setUser_id_str(user_id_str2);
		setProf_img_url(prof_img_url2);
		setUser_name(user_name);
	}
	/**
	 * @return the user_name
	 */
	public String getUser_name() {
		return user_name;
	}
	/**
	 * @param user_name the user_name to set
	 */
	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}
	/**
	 * @return the screen_name
	 */
	public String getScreen_name() {
		return screen_name;
	}
	/**
	 * @param screen_name the screen_name to set
	 */
	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}
	/**
	 * @return the user_id_str
	 */
	public String getUser_id_str() {
		return user_id_str;
	}
	/**
	 * @param user_id_str the user_id_str to set
	 */
	public void setUser_id_str(String user_id_str) {
		this.user_id_str = user_id_str;
	}
	/**
	 * @return the prof_img_url
	 */
	public Bitmap getProf_img_url() {
		return prof_img_url;
	}
	/**
	 * @param prof_img_url2 the prof_img_url to set
	 */
	public void setProf_img_url(Bitmap prof_img_url2) {
		this.prof_img_url = prof_img_url2;
	}
	
}
