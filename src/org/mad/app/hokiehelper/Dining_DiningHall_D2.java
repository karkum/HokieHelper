package org.mad.app.hokiehelper;

import java.util.Calendar;

public class Dining_DiningHall_D2 extends Dining_DiningHall {

	public Dining_DiningHall_D2() {
		super("D2");
	}

	@Override
	public DiningHallState getDiningHallState() {
		state = DiningHallState.CLOSED;

		Calendar today = Calendar.getInstance();
		// MONDAY THRU FRIDAY
		if (today.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
				|| today.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
				|| today.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			// breakfast hours
			if (today.get(Calendar.HOUR_OF_DAY) >= 6 && today.get(Calendar.HOUR_OF_DAY) < 7) {
				state = DiningHallState.CLOSED_OPENING_SOON;
			}
			if (today.get(Calendar.HOUR_OF_DAY) == 7 || (today.get(Calendar.HOUR_OF_DAY) == 8 && today.get(Calendar.MINUTE) < 30)) {
				state = DiningHallState.OPEN;
			}
			if ((today.get(Calendar.HOUR_OF_DAY) == 8 && today.get(Calendar.MINUTE) >= 30) || (today.get(Calendar.HOUR_OF_DAY) == 9 && today.get(Calendar.MINUTE) < 30)) {
				state = DiningHallState.OPEN_CLOSING_SOON;
			}
			// lunch hours
			if (today.get(Calendar.HOUR_OF_DAY) >= 10 && today.get(Calendar.HOUR_OF_DAY) < 11) {
				state = DiningHallState.CLOSED_OPENING_SOON;
			}
			if (today.get(Calendar.HOUR_OF_DAY) >= 11 && today.get(Calendar.HOUR_OF_DAY) < 13) {
				state = DiningHallState.OPEN;
			}
			if (today.get(Calendar.HOUR_OF_DAY) >= 13 && today.get(Calendar.HOUR_OF_DAY) < 14) {
				state = DiningHallState.OPEN_CLOSING_SOON;
			}
			// dinner hours
			if (today.get(Calendar.HOUR_OF_DAY) >= 16 && today.get(Calendar.HOUR_OF_DAY) < 17) {
				state = DiningHallState.CLOSED_OPENING_SOON;
			}
			if (today.get(Calendar.HOUR_OF_DAY) >= 17 && today.get(Calendar.HOUR_OF_DAY) < 18) {
				state = DiningHallState.OPEN;
			}
			if (today.get(Calendar.HOUR_OF_DAY) >= 18 && today.get(Calendar.HOUR_OF_DAY) < 19) {
				state = DiningHallState.OPEN_CLOSING_SOON;
			}
		}

		// SATURDAY
		if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
			state = DiningHallState.CLOSED;
		}

		// SUNDAY
		if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
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

		return state;
	}

	@Override
	public int getIconId() {
		state = getDiningHallState();
		if (state == DiningHallState.CLOSED || state == DiningHallState.OPEN) {
			return R.drawable.logo_d2;
		} else {
			return R.drawable.logo_d2_ex;
		}
	}

	@Override
	public int getHallId() {
		return 3;
	}
}
