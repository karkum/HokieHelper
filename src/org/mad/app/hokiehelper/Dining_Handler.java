package org.mad.app.hokiehelper;

import java.util.ArrayList;

import org.mad.app.hokiehelper.Dining_DiningHall.DiningHallState;

public class Dining_Handler
{
	public static final Dining_DiningHall[] allHalls = { new Dining_DiningHall_TurnerPlace_FireGrill(),
		new Dining_DiningHall_ABPGLC(), new Dining_DiningHall_ABPSquiresCafe(),
		new Dining_DiningHall_ABPSquiresKiosk(),  new Dining_DiningHall_TurnerPlace_AtomicPizzeria(),
		new Dining_DiningHall_TurnerPlace_BrueggersBagels(),
		new Dining_DiningHall_D2(), new Dining_DiningHall_Deets(),
		new Dining_DiningHall_TurnerPlace_DolciCaffe(), new Dining_DiningHall_DX(),
		new Dining_DiningHall_HokieGrill(),  new Dining_DiningHall_TurnerPlace_JambaJuice(),
		new Dining_DiningHall_TurnerPlace_OrigamiGrill(),
		new Dining_DiningHall_TurnerPlace_OrigamiSushi(), new Dining_DiningHall_Owens(),
		new Dining_DiningHall_TurnerPlace_QdobaMexicanGrill(), new Dining_DiningHall_Sbarro(),
		new Dining_DiningHall_TurnerPlace_SoupGarden(), new Dining_DiningHall_WestEnd()};

	public Dining_Handler()
	{
		// empty constructor
	}

	public ArrayList<Dining_DiningHall> getHallsForState(DiningHallState state)
	{
		ArrayList<Dining_DiningHall> halls = new ArrayList<Dining_DiningHall>();

		if (state == DiningHallState.CLOSED) {
			for (int i = 0; i < allHalls.length; i++) {
				if (allHalls[i].getDiningHallState() == DiningHallState.CLOSED
						|| allHalls[i].getDiningHallState() == DiningHallState.CLOSED_OPENING_SOON) {
					halls.add(allHalls[i]);
				}
			}
		} else if (state == DiningHallState.OPEN) {
			for (int i = 0; i < allHalls.length; i++) {
				if (allHalls[i].getDiningHallState() == DiningHallState.OPEN
						|| allHalls[i].getDiningHallState() == DiningHallState.OPEN_CLOSING_SOON) {
					halls.add(allHalls[i]);
				}
			}
		}
		return halls;
	}
}
