package org.mad.app.hokiehelper;

public abstract class Dining_DiningHall {
	protected String name;
	protected DiningHallState state;
	public enum DiningHallState { OPEN, OPEN_CLOSING_SOON, CLOSED, CLOSED_OPENING_SOON };
	
	public Dining_DiningHall(String name) {
		this.name = name;
	}
	
	public abstract DiningHallState getDiningHallState();
	public abstract int getIconId();
	public abstract int getHallId();
	
	public String getName() {
		return name;
	}
}
