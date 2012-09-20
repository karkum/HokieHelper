package org.mad.app.hokiehelper;

import java.util.Calendar;


public class Dining_DiningHall_Owens extends Dining_DiningHall {

	public Dining_DiningHall_Owens() {
		super("Owens Food Court");
	}

	@Override
	public DiningHallState getDiningHallState() {
		state = DiningHallState.CLOSED;

		Calendar today = Calendar.getInstance();
		// ANY DAY OF THE WEEK
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

		return state;	}

	@Override
	public int getIconId() {
		state = getDiningHallState();
		if (state == DiningHallState.CLOSED || state == DiningHallState.OPEN) {
			return R.drawable.logo_owens;
		} else {
			return R.drawable.logo_owens_ex;
		}
	}

	@Override
	public int getHallId() {
		return 7;
	}
}
