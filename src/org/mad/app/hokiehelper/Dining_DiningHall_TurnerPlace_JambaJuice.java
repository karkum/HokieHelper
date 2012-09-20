package org.mad.app.hokiehelper;

import java.util.Calendar;

public class Dining_DiningHall_TurnerPlace_JambaJuice extends Dining_DiningHall {

	public Dining_DiningHall_TurnerPlace_JambaJuice() {
		super("Jamba Juice\n(Turner Place)");
	}

	@Override
	public DiningHallState getDiningHallState() {
		state = DiningHallState.CLOSED;

		Calendar today = Calendar.getInstance();
		// MONDAY THRU FRIDAY
		if (today.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY
				|| today.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY || today.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY
				|| today.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
			if (today.get(Calendar.HOUR_OF_DAY) >= 8 && today.get(Calendar.HOUR_OF_DAY) < 9) {
				state = DiningHallState.CLOSED_OPENING_SOON;
			}
			if (today.get(Calendar.HOUR_OF_DAY) >= 9 && today.get(Calendar.HOUR_OF_DAY) < 18) {
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
			state = DiningHallState.CLOSED;
		}

		return state;
	}

	@Override
	public int getIconId() {
		state = getDiningHallState();
		if (state == DiningHallState.CLOSED || state == DiningHallState.OPEN) {
			return R.drawable.logo_jamba_juice;
		} else {
			return R.drawable.logo_jamba_juice_ex;
		}
	}

	@Override
	public int getHallId() {
		return 14;
	}
}
