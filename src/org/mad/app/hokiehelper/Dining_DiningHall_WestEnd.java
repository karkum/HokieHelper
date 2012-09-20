package org.mad.app.hokiehelper;

import java.util.Calendar;


public class Dining_DiningHall_WestEnd extends Dining_DiningHall {

	public Dining_DiningHall_WestEnd() {
		super("West End Market");
	}

	@Override
	public DiningHallState getDiningHallState() {
		state = DiningHallState.CLOSED;

		Calendar today = Calendar.getInstance();
		// MONDAY THRU FRIDAY
		if (today.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
				|| today.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
				|| today.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			if ((today.get(Calendar.HOUR_OF_DAY) == 9 && today.get(Calendar.MINUTE) >= 30)
					|| (today.get(Calendar.HOUR_OF_DAY) == 10 && today.get(Calendar.MINUTE) < 30)) {
				state = DiningHallState.CLOSED_OPENING_SOON;
			}
			if ((today.get(Calendar.HOUR_OF_DAY) == 10 && today.get(Calendar.MINUTE) >= 30)
					|| (today.get(Calendar.HOUR_OF_DAY) >= 11 && today.get(Calendar.HOUR_OF_DAY) < 19)) {
				state = DiningHallState.OPEN;
			}
			if (today.get(Calendar.HOUR_OF_DAY) >= 19 && today.get(Calendar.HOUR_OF_DAY) < 20) {
				state = DiningHallState.OPEN_CLOSING_SOON;
			}
		}

		// SATURDAY
		if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			if (today.get(Calendar.HOUR_OF_DAY) >= 10 && today.get(Calendar.HOUR_OF_DAY) < 11) {
				state = DiningHallState.CLOSED_OPENING_SOON;
			}
			if (today.get(Calendar.HOUR_OF_DAY) >= 11 && today.get(Calendar.HOUR_OF_DAY) < 18) {
				state = DiningHallState.OPEN;
			}
			if (today.get(Calendar.HOUR_OF_DAY) >= 18 && today.get(Calendar.HOUR_OF_DAY) < 19) {
				state = DiningHallState.OPEN_CLOSING_SOON;
			}
		}

		// SUNDAY
		if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			if (today.get(Calendar.HOUR_OF_DAY) >= 10 && today.get(Calendar.HOUR_OF_DAY) < 11) {
				state = DiningHallState.CLOSED_OPENING_SOON;
			}
			if (today.get(Calendar.HOUR_OF_DAY) >= 11 && today.get(Calendar.HOUR_OF_DAY) < 19) {
				state = DiningHallState.OPEN;
			}
			if (today.get(Calendar.HOUR_OF_DAY) >= 19 && today.get(Calendar.HOUR_OF_DAY) < 20) {
				state = DiningHallState.OPEN_CLOSING_SOON;
			}
		}

		return state;	}

	@Override
	public int getIconId() {
		state = getDiningHallState();
		if (state == DiningHallState.CLOSED || state == DiningHallState.OPEN) {
			return R.drawable.logo_west_end;
		} else {
			return R.drawable.logo_west_end_ex;
		}
	}

	@Override
	public int getHallId() {
		return 9;
	}
}
