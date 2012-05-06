package org.mad.app.hokiehelper;


public class Info_Event {
	private String title;
	private int month;
	private int day;
	public Info_Event (String t, int m, int d) {
		title = t;
		month = m;
		day = d;
	}

	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}
	public int getDay() {
		return day;
	}
	public void setDay(int day) {
		this.day = day;
	}
	
	
}
