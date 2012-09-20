package org.mad.app.hokiehelper;

import java.util.Calendar;


public class Dining_DiningHall_DX extends Dining_DiningHall {

	public Dining_DiningHall_DX() {
		super("DXpress");
	}

	@Override
	public DiningHallState getDiningHallState() {
		state = DiningHallState.CLOSED;

		Calendar today = Calendar.getInstance();
		// MONDAY THRU FRIDAY
		if (today.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
				|| today.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
				|| today.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			if (today.get(Calendar.HOUR_OF_DAY) >= 6 && today.get(Calendar.HOUR_OF_DAY) < 7) {
				state = DiningHallState.CLOSED_OPENING_SOON;
			}
			if (today.get(Calendar.HOUR_OF_DAY) >= 7) {
				state = DiningHallState.OPEN;
			}
		}

		// SATURDAY & SUNDAY
		if (today.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
			if (today.get(Calendar.HOUR_OF_DAY) >= 8 && today.get(Calendar.HOUR_OF_DAY) < 9) {
				state = DiningHallState.CLOSED_OPENING_SOON;
			}
			if (today.get(Calendar.HOUR_OF_DAY) >= 9) {
				state = DiningHallState.OPEN;
			}
		}

		// Between 12AM & 1AM
		if (today.get(Calendar.HOUR_OF_DAY) >= 0 && today.get(Calendar.HOUR_OF_DAY) < 1) {
			state = DiningHallState.OPEN;
		}

		// Between 1AM & 2AM
		if (today.get(Calendar.HOUR_OF_DAY) >= 1 && today.get(Calendar.HOUR_OF_DAY) < 2) {
			state = DiningHallState.OPEN_CLOSING_SOON;
		}

		return state;	}

	@Override
	public int getIconId() {
		state = getDiningHallState();
		if (state == DiningHallState.CLOSED || state == DiningHallState.OPEN) {
			return R.drawable.logo_dx;
		} else {
			return R.drawable.logo_dx_ex;
		}
	}

	@Override
	public int getHallId() {
		return 5;
	}
}
