package org.mad.app.hokiehelper;

import java.util.Date;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

//Change: Added a case in two switch statements

public class Dining_InfoActivity extends ListActivity
{
	private String [] information;
	private MyStringAdapter stringAdapter;
	public static String tableName;
	private String tableNumber;
	private Dining_Handler handler;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hall_info_display);
		handler = new Dining_Handler();
		if (Dining_MainActivity.restaurantChosen == 0 || Dining_MainActivity.restaurantChosen == 6)
			//d2 and shultz
			information = getResources().getStringArray(R.array.d2shultzInfo);
		else
			//all other restaurants
			information = getResources().getStringArray(R.array.info);

		stringAdapter = new MyStringAdapter(this, R.layout.list_item, information);
		setListAdapter(stringAdapter);
		ListView lv = getListView();

		lv.setTextFilterEnabled(true);

		View parent = (View) lv.getParent();
		parent.setBackgroundResource(R.drawable.bg_tan);

		lv.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				createNextActivity(position);
			}
		});
		getHeaderInfo();
		setTableName();
	}

	private void setTableName()
	{
		switch(Dining_MainActivity.restaurantChosen)
		{
		case 0:
			tableName = "d2"; break;
		case 1:
			tableName = "deets"; break;
		case 2:
			tableName = "d2"; break;
		case 3:
			tableName = "hokieGrill"; break;
		case 4:
			tableName = "owens"; break;
		case 5:
			tableName = "shultz"; break;
		case 6:
			tableName = "shultz"; break;
		case 7:
			tableName = "westend"; break;
		}
	}

	private void createNextActivity(int position)
	{
		Intent i;
		String message;
		String title;
		switch (position)
		{
		case 0: onSearchRequested(); break;
		case 1: 
			i = new Intent(Dining_InfoActivity.this, Dining_TrackActivity.class);
			startActivity(i); break; 
		case 2:
			i = new Intent(Dining_InfoActivity.this, Dining_SubRestaurants.class);
			i.putExtra("tableName", tableName);
			startActivity(i); break;
		case 3:
			message = makeHoursString();
			title = "Hours Of Operation";
			showDialog(message, title); break;
		case 4:
			message = makeLocationString();
			//						title = "Location";
			//						showDialog(message, title); 
			break;
		case 5:
			String number = "Call " + tableNumber;
			new AlertDialog.Builder(this)
			.setTitle("Contact Options")
			.setItems(new String[]{number, "Get Contact Information"},
					new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialoginterface, int i)
				{
					executeContactOption(i);
				}
			})
			.show(); break;
		case 6:
			message = "Flex\n" +
					"Breakfast: $2.10\n" +
					"Lunch/Brunch: $3.05\n" +
					"Dinner: $3.75\n" +
					"Special Meal: $4.70\n" +
					"\nCash" +
					"\nBreakfast: $6.30\n" +
					"Lunch/Brunch: $9.15\n" +
					"Dinner: $11.25\n" +
					"Special Meal: $14.15\n" +
					"\nChildren 3 and under eat for free, ages 4 to 12 are half price";;
					title = "Prices";
					showDialog(message, title); break;
		}
	}

	private String makeLocationString()
	{
		String loc = "";
		Bundle bundle = new Bundle();
		bundle.putString("Snippet", null);
		bundle.putBoolean( "isRoute", false );
		bundle.putBoolean("isCurrLocation", false);
		Intent i = new Intent(Dining_InfoActivity.this, Maps_MainActivity.class);
		int lat;
		int lon;
		switch(Dining_MainActivity.restaurantChosen)
		{
		case 0: //d2
			//		        loc = "Located in the Dietrick Dining Center, between " +
			//		        "Ambler Johnston and Pritchard Halls, facing " +
			//		        "Washington Street. The entrance is through the " +
			//		        "doors to the right of Deet's Place and then up the stairs.";
			bundle.putString("BuildingName" , "D2");
			lat = (int) (37.224536 * 1E6);
			lon = (int) (-80.420963 * 1E6);
			bundle.putInt("Latitude", lat);
			bundle.putInt("Longitude", lon);
			i.putExtras( bundle );
			startActivity(i); 
			break;
		case 1: //deets
			//			loc = "Located in the Dietrick Dining Center, between " +
			//					"Ambler Johnston and Pritchard Halls, facing " +
			//					"Washington Street. The entrance is to the left side of" +
			//					"the building next to Dietrick General Store.";
			bundle.putString("BuildingName" , "Deets");
			lat = (int) (37.224237 * 1E6);
			lon = (int) (-80.421327 * 1E6);
			bundle.putInt("Latitude", lat);
			bundle.putInt("Longitude", lon);
			i.putExtras( bundle );
			startActivity(i); 
			break;
		case 2: //dx
			//			loc = "Located in the Dietrick Dining Center, between " +
			//					"Ambler Johnston and Pritchard Halls, facing " +
			//					"Washington Street. The entrance is to the right " +
			//					"side of the building next to Dietrick General Store.";
			bundle.putString("BuildingName" , "DX");
			lat = (int) (37.224605 * 1E6);
			lon = (int) (-80.420769 * 1E6);
			bundle.putInt("Latitude", lat);
			bundle.putInt("Longitude", lon);
			i.putExtras( bundle );
			startActivity(i); 
			break;
		case 3: //hokiegrill
			//			loc = "Located in Owens Dining Center across Kent " +
			//					"Street from the University Bookstore. The " +
			//					"entrance is located on the side of the building " +
			//					"closest to Kent Street.";
			bundle.putString("BuildingName" , "Hokie Grill");
			lat = (int) (37.226937 * 1E6);
			lon = (int) (-80.418624 * 1E6);
			bundle.putInt("Latitude", lat);
			bundle.putInt("Longitude", lon);
			i.putExtras( bundle );
			startActivity(i); 
			break;
		case 4: //owens
			//			loc = "Located in Owens Dining Center across Kent " +
			//					"Street from the University Bookstore. The " +
			//					"entrance is located on the side of the building " +
			//					"closest to West Eggleston Hall.";
			bundle.putString("BuildingName" , "Owens Hall");
			lat = (int) (37.226487 * 1E6);
			lon = (int) (-80.41914 * 1E6);
			bundle.putInt("Latitude", lat);
			bundle.putInt("Longitude", lon);
			i.putExtras( bundle );
			startActivity(i); 
			break;
		case 5: //shultz express
			//			loc = "Located in the mezzanine area of " +
			//					"Shultz Dining Center, in the Upper" +
			//					"Quad near Shanks Hall.";
			bundle.putString("BuildingName" , "Shultz Hall");
			lat = (int) (37.231817 * 1E6);
			lon = (int) (-80.4184 * 1E6);
			bundle.putInt("Latitude", lat);
			bundle.putInt("Longitude", lon);
			i.putExtras( bundle );
			startActivity(i); 
			break;
		case 6: //shultz
			//			loc = "Located in the Shultz Dining Center, in the " +
			//					"Upper Quad near Shanks Hall."; 
			bundle.putString("BuildingName" , "Shultz Hall");
			lat = (int) (37.231817 * 1E6);
			lon = (int) (-80.4184 * 1E6);
			bundle.putInt("Latitude", lat);
			bundle.putInt("Longitude", lon);
			i.putExtras( bundle );
			startActivity(i); break;
		case 7: //west end
			//			String message = "Connected to Cochrane Hall. Located between Ambler-Johnston " +
			//					"and Harper Halls."; 
			//			String title = "Location";
			//			showDialog(message, title);
			bundle.putString("BuildingName" , "West End");
			lat = (int) (37.22264 * 1E6);
			lon = (int) (-80.421939 * 1E6);
			bundle.putInt("Latitude", lat);
			bundle.putInt("Longitude", lon);
			i.putExtras( bundle );
			startActivity(i);
			break;
		}
		return loc;
	}

	private String makeHoursString()
	{
		String hours = "";
		switch(Dining_MainActivity.restaurantChosen)
		{
		case 0: //d2
			hours = "M-F:   7:00am - 9:30am" +
					"\n         11:00am - 2:00pm" +
					"\n           5:00pm - 7:00pm\n" +
					"\nSun:  11:00am - 3:00pm\n" +
					"            3:00pm - 7:00pm"; break;
		case 1: //deets
			hours = "M-F:    7:30am - 12:00am\n" +
					"Sat:   10:00am - 12:00am\n" +
					"Sun:  10:00am - 12:00am"; break;
		case 2: //dx
			hours = "M-F:  7:00am - 2:00am\n" +
					"Sat:   9:00am - 2:00am\n" +
					"Sun:  9:00am - 2:00am"; break;
		case 3: //hokiegrill
			hours = "M-F:  10:30am - 9:00pm\n" +
					"Sat:   12:00pm - 8:00pm"; break;
		case 4: //owens
			hours = "M-F:  10:30am - 8:00pm\n"	+
					"\nSat:   10:30am - 3:00pm\n" +
					"            3:00pm - 8:00pm\n" +
					"\nSun:  10:30am - 3:00pm\n" +
					"           3:00pm - 8:00pm"; break;
		case 5: //shultz express
			hours = "M-Th:  7:00am - 10:15am\n" +
					"           10:30am - 2:00pm\n" +
					"\nFri:      7:00am - 10:15am\n" +
					"          10:30am - 4:00pm"; break;
		case 6: //shultz
			hours = "Closed"; break;
			//		        hours = "M-Th:  5:00pm - 7:00pm"; break;
		case 7: //west end
			hours = "M-F:  10:30am - 8:00pm\n"	+
					"Sat:   11:00am - 7:00pm\n" + 
					"Sun: 11:00am - 8:00pn"; break;
		}
		return hours;
	}

	private String makeContactString()
	{
		String info = "";
		switch(Dining_MainActivity.restaurantChosen)
		{
		case 0: //d2
			info = "Tel: 540-231-6130\n" +
					"Fax: 540-231-9046\n" +
					"\nAssistant Director: Kelvin Bergsten\n" +
					"Executive Chef: Carolyn Bess\n" +
					"Chef de Cuisine: Randy VanDyke\n" +
					"Operation Manager: Lance Mailem\n" +
					"Operation Manager: Gabe Petry\n" +
					"Operation Manager: April Danner\n" +
					"Food Production Manager: Amanda Snediker\n"+
					"Office Specialist: Kevin Quinn\n" +
					"Office Specialist: Andrew Sinnes"; break;
		case 1: //deets
			info = "Tel: 540-231-7101\n" +
					"\nManager: Don Harvey\n" +
					"Assistant Manager: Judy Young"; break;
		case 2: //dx
			info = "Tel: 540-231-2184\n" +
					"Fax: 540-231-9046\n"+
					"\nAssistant Director: Kelvin Bergsten\n" +
					"Operations Manager: Dennis Luz\n" +
					"Assistant Manager: Vacant\n" +
					"Student General Manager: Kevin Quinn\n" +
					"Student General Manager: Andrew Sinnes"; break;
		case 3: //hokiegrill
			info = "Tel: 540-231-6187\n" +
					"\nAssistant Director: Steve Opeka\n" +
					"Operations Manager: Jessica Hale\n" +
					"Operations Manager: Jason McAlinden\n" +
					"Office Specialist: Laurie Osborne"; break;
		case 4: //owens
			info = "Tel: 540-231-6187\n" +
					"\nAssistant Director: Steve Swannell\n" +
					"Executive Chef: John Scherer\n" +
					"Operations Manager: Kelli Ballentine Koblish\n" +
					"Operations Manager: Jessia Hale\n" +
					"Operations Manager: Alisha Barker\n" +
					"Operations Manager: Steve Swannell\n" +
					"Food Production Manager: Betty Shields\n" +
					"Stockroom Supervisor: Frank Sheppard\n"; break;
		case 5: //shultz express
			info = "Tel: 540-231-9818\n" +
					"\nAssistant Manager: Jason Ludy\n" +
					"Food Production Manager: Nona Gabbert\n" +
					"Chef de Cuisine: Charles Morse\n" +
					"Office Specialist: Kezia Johnson"; break;
		case 6: //shultz
			info = "Tel: 540-231-6873\n" +
					"\nAssistant Manager: Jason Ludy\n" +
					"Food Production Manager: Nona Gabbert\n" +
					"Chef de Cuisine: Charles Morse\n" +
					"Office Specialist: Kezia Johnson"; break;
		case 7: //west end
			info = "Tel: 540-231-4779\n" +
					"\nAssistant Director: Steve Garnett\n" +
					"Executive Chef: Mark Bratton (CEC)\n" +
					"Chef de Cuisine: Jonathan Creger (CC)\n" +
					"Assistant Manager: James Leggett\n" +
					"Operation Manager: Jamie Parnell\n" +
					"Operation Manager: Bernard Ross\n" +
					"Food Production Manager: Maxie Riddlebarger\n"+
					"Office Specialist : Janette Perkins\n"; break;
		}
		return info;
	}

	private void showDialog(String message, String title)
	{
		AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Dining_InfoActivity.this);

		dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener()
		{
			public void onClick(DialogInterface dialog, int which)
			{
				//Do Nothing
			}
		});

		dlgAlert.setMessage(message);
		dlgAlert.setTitle(title);
		dlgAlert.setCancelable(true);
		dlgAlert.create().show();
	}

	/**
	 * Returns a String representing the current open/closed status
	 * of the dining hall.
	 *
	 * @return the hall's open/closed status
	 */
	private String getStatus()
	{
		Date date = new Date();
		String[] array = date.toString().split("\\s+");
		String[] time = array[3].split(":");
		int hour = Integer.parseInt(time[0]);
		int minute = Integer.parseInt(time[1]);

		ImageView ex = (ImageView) findViewById(R.id.exclamation_point);
		ex.setVisibility(ImageView.GONE);

		int hallStatus = handler.refreshOpen(Dining_Handler.allHalls[Dining_MainActivity.restaurantChosen]);
		if (hallStatus == Dining_Handler.CLOSED)
			return "This hall is currently closed";
		else if (hallStatus == Dining_Handler.OPEN)
			return "This hall is currently open";

		String status = "";
		ex.setVisibility(ImageView.VISIBLE);
		switch(Dining_MainActivity.restaurantChosen)
		{
			case 0:	// d2
				if (hallStatus == Dining_Handler.OPEN_EX)
				{
					if (array[0].charAt(0) != 'S') //Weekday
					{
						if (hour == 8 || hour == 9) //if between 8:30 and 9:30
							status = "This hall will close at 9:30 a.m." +
									"\n(" + getTimeDifference(hour, minute, 9, 30) +
									" minutes from now)";
						else if (hour == 13) //if between 13:00 and 14:00
							status = "This hall will close at 2:00 p.m." +
									"\n(" + getTimeDifference(hour, minute, 14, 0) +
									" minutes from now)";
						else if (hour == 18) //if between 18:00 and 19:00
							status = "This hall will close at 7:00 p.m." +
									"\n(" + getTimeDifference(hour, minute, 7, 0) +
									" minutes from now)";
					}
					else if (array[0].equals("Sun")) //Sunday
					{
						status = "This hall will close at 7:00 p.m." +
								"\n(" + getTimeDifference(hour, minute, 7, 0) +
								" minutes from now)";
					}
				}
				else if (hallStatus == Dining_Handler.CLOSED_EX)
				{
					if (array[0].charAt(0) != 'S') //Weekday
					{
						if (hour == 6) //if between 6:00 and 7:00
							status = "This hall will open at 7:00 a.m." +
									"\n(" + getTimeDifference(hour, minute, 7, 0) +
									" minutes from now)";
						else if (hour == 10) //if between 10:00 and 11:00
							status = "This hall will open at 11:00 a.m." +
									"\n(" + getTimeDifference(hour, minute, 11, 0) +
									" minutes from now)";
						else if (hour == 16) //if between 16:00 and 17:00
							status = "This hall will open at 5:00 p.m." +
									"\n(" + getTimeDifference(hour, minute, 17, 0) +
									" minutes from now)";
					}
					else if (array[0].equals("Sunday")) //Sunday
					{
						status = "This hall will open at 11:00 a.m." +
								"\n(" + getTimeDifference(hour, minute, 11, 0) +
								" minutes from now)";
					}
				}
				break;
			case 1:	//deets
				if (hallStatus == Dining_Handler.OPEN_EX)
				{
					status = "This hall will close at 12:00 a.m." +
							"\n(" + getTimeDifference(hour, minute, 24, 0) +
							" minutes from now)";
				}
				else if (hallStatus == Dining_Handler.CLOSED_EX)
				{
					if (array[0].charAt(0) != 'S') //Weekday
					{
						status = "This hall will open at 7:30 a.m." +
								"\n(" + getTimeDifference(hour, minute, 7, 30) +
								" minutes from now)";
					}
					else
					{
						status = "This hall will open at 10:00 a.m." +
								"\n(" + getTimeDifference(hour, minute, 10, 0) +
								" minutes from now)";
					}
				}
				break;
			case 2: //dx
				if (hallStatus == Dining_Handler.OPEN_EX)
				{
					status = "This hall will close at 2:00 a.m." +
							"\n(" + getTimeDifference(hour, minute, 2, 0) +
							" minutes from now)";
				}
				else if (hallStatus == Dining_Handler.CLOSED_EX)
				{
					if (array[0].charAt(0) != 'S') //Weekday
					{
						status = "This hall will open at 7:00 a.m." +
								"\n(" + getTimeDifference(hour, minute, 7, 0) +
								" minutes from now)";
					}
					else
					{
						status = "This hall will open at 9:00 a.m." +
								"\n(" + getTimeDifference(hour, minute, 9, 0) +
								" minutes from now)";
					}
				}
				break;
			case 3: //hokie_grill
				if (hallStatus == Dining_Handler.OPEN_EX)
				{
					if (array[0].charAt(0) != 'S') //Weekday
					{
						status = "This hall will close at 9:00 p.m." +
								"\n(" + getTimeDifference(hour, minute, 21, 0) +
								" minutes from now)";
					}
					else if (array[0].equals("Sat"))
					{
						status = "This hall will close at 8:00 p.m." +
								"\n(" + getTimeDifference(hour, minute, 20, 0) +
								" minutes from now)";
					}
				}
				else if (hallStatus == Dining_Handler.CLOSED_EX)
				{
					if (array[0].charAt(0) != 'S')
					{
						status = "This hall will open at 10:30 a.m." +
								"\n(" + getTimeDifference(hour, minute, 10, 30) +
								" minutes from now)";
					}
					else if (array[0].equals("Sat"))
					{
						status = "This hall will open at 12:00 p.m." +
								"\n(" + getTimeDifference(hour, minute, 12, 0) +
								" minutes from now)";
					}
				}
				break;
			case 4: //owens
				if (hallStatus == Dining_Handler.OPEN_EX)
				{
					status = "This hall will close at 8:00 p.m." +
							"\n(" + getTimeDifference(hour, minute, 20, 0) +
							" minutes from now)";
				}
				else if (hallStatus == Dining_Handler.CLOSED_EX)
				{
					status = "This hall will open at 10:30 a.m." +
							"\n(" + getTimeDifference(hour, minute, 10, 30) +
							" minutes from now)";
				}
				break;
			case 5: //shultz_express
				if (hallStatus == Dining_Handler.OPEN_EX)
				{
					if (array[0].charAt(0) != 'S' && array[0].charAt(0) != 'F') //M-R
					{
						status = "This hall will close at 2:00 p.m." +
								"\n(" + getTimeDifference(hour, minute, 14, 0) +
								" minutes from now)";
					}
					else if (array[0].charAt(0) == 'F')
					{
						status = "This hall will close at 4:00 p.m." +
								"\n(" + getTimeDifference(hour, minute, 16, 0) +
								" minutes from now)";
					}
				}
				else if (hallStatus == Dining_Handler.CLOSED_EX)
				{
					status = "This hall will open at 7:00 a.m." +
							"\n(" + getTimeDifference(hour, minute, 7, 0) +
							" minutes from now)";
				}
				break;
			case 6: //shultz
//				if (hallStatus == DiningHandler.OPEN_EX)
//				{
//					status = "This hall will close at 7:00 p.m." +
//							"\n(" + getTimeDifference(hour, minute, 19, 0) +
//							" minutes from now)";
//				}
//				else if (hallStatus == DiningHandler.CLOSED_EX)
//				{
//					status = "This hall will open at 5:00 p.m." +
//							"\n(" + getTimeDifference(hour, minute, 17, 0) +
//							" minutes from now)";
//				}
				break;
			case 7: //west_end
				if (hallStatus == Dining_Handler.OPEN_EX)
				{
					if (!array[0].equals("Sat")) //Weekdays and sunday
					{
						status = "This hall will close at 8:00 p.m." +
								"\n(" + getTimeDifference(hour, minute, 20, 0) +
								" minutes from now)";
					}
					else //Sat
					{
						status = "This hall will close at 7:00 p.m." +
								"\n(" + getTimeDifference(hour, minute, 19, 0) +
								" minutes from now)";
					}
				}
				else if (hallStatus == Dining_Handler.CLOSED_EX)
				{
					if (array[0].charAt(0) != 'S') //Weekday
					{
						status = "This hall will open at 10:30 a.m." +
								"\n(" + getTimeDifference(hour, minute, 10, 30) +
								" minutes from now)";
					}
					else //Weekend
					{
						status = "This hall will open at 11:00 a.m." +
								"\n(" + getTimeDifference(hour, minute, 11, 0) +
								" minutes from now)";
					}
				}
				break;
		}
		return status;
	}

	private int getTimeDifference(int currentHour, int currentMinute,
			int targetHour, int targetMinute)
	{
		int difference = 999;
		if (targetMinute == 0)
			difference = (60 - currentMinute);
		else if (targetMinute == 30)
		{
			if (currentHour == targetHour)
				difference = (30 - currentMinute);
			else
				difference = 30 + (60 - currentMinute);
		}
		return difference;
	}

	private void getHeaderInfo()
	{
		TextView hallName = (TextView) findViewById(R.id.current_restaurant);
		TextView hallStatus = (TextView) findViewById(R.id.current_restaurant_status);
		hallStatus.setText(getStatus());
		ImageView img = (ImageView) findViewById(R.id.restaurant_icon);

		switch(Dining_MainActivity.restaurantChosen)
		{
		case 0: //d2
			hallName.setText("D2");
			img.setImageResource(R.drawable.logo_d2);
			tableNumber = "540-231-6130"; break;
		case 1: //deets
			hallName.setText("Deet's Place");
			img.setImageResource(R.drawable.logo_deets);
			tableNumber = "540-231-7101"; break;
		case 2: //dx
			hallName.setText("DXpress");
			img.setImageResource(R.drawable.logo_dx);
			tableNumber = "540-231-2184"; break;
		case 3: //hokie_grill
			hallName.setText("Hokie Grill & Co.");
			img.setImageResource(R.drawable.logo_hokie_grill);
			tableNumber = "540-231-6187"; break;
		case 4: //owens
			hallName.setText("Owens Food Court");
			img.setImageResource(R.drawable.logo_owens);
			tableNumber = "540-231-6187"; break;
		case 5: //shultz_express
			hallName.setText("Shultz Express");
			img.setImageResource(R.drawable.logo_shultz_express);
			tableNumber = "540-231-9818"; break;
		case 6: //shultz
			hallName.setText("Shultz");
			img.setImageResource(R.drawable.logo_shultz);
			tableNumber = "540-231-6873"; break;
		case 7: //west_end
			hallName.setText("West End Market");
			img.setImageResource(R.drawable.logo_west_end);
			tableNumber = "540-231-4779"; break;
		}
	}

	private void executeContactOption(int i)
	{
		if (i == 0)
		{
			startActivity(new Intent(Intent.ACTION_CALL,
					Uri.parse("tel: " + tableNumber.replaceAll("-", ""))));
		}
		else
		{
			String message = makeContactString();
			String title = "Contact Information";
			showDialog(message, title);
		}
	}

	private class MyStringAdapter extends ArrayAdapter<String>
	{
		private String[] strings;

		public MyStringAdapter(Context context, int textViewResourceId, String[] strings)
		{
			super(context, textViewResourceId, strings);
			this.strings = strings;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent)
		{
			View v = convertView;
			if (v == null)
			{
				LayoutInflater vi = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				v = vi.inflate(R.layout.list_item, null);
			}
			TextView t = (TextView) v.findViewById(R.id.restaurant_option);
			t.setText(strings[position]);
			ImageView iv = (ImageView) v.findViewById(R.id.option_icon);
			switch (position)
			{
			case 0: iv.setImageResource(R.drawable.icon_search); break;
			case 1: iv.setImageResource(R.drawable.icon_track); break;
			case 2: iv.setImageResource(R.drawable.icon_food); break;
			case 3: iv.setImageResource(R.drawable.icon_clock); break;
			case 4: iv.setImageResource(R.drawable.icon_map); break;
			case 5: iv.setImageResource(R.drawable.icon_phone); break;
			case 6: iv.setImageResource(R.drawable.icon_dollar_sign);
			}
			return v;
		}
	}
}
