package org.mad.app.hokiehelper;

import org.mad.app.hokiehelper.Dining_DiningHall.DiningHallState;

import android.app.AlertDialog;
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

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.Menu;
import com.actionbarsherlock.view.MenuInflater;
import com.actionbarsherlock.view.MenuItem;

//Change: Added a case in two switch statements

public class Dining_InfoActivity extends SherlockListActivity
{
	private String [] information;
	private MyStringAdapter stringAdapter;
	public static String tableName;
	private String tableNumber;
	private int selectedHall;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.hall_info_display);

		// Sets up action bar title/navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle("Dining Halls");

		Intent i = getIntent();
		selectedHall = i.getIntExtra("selectedHall", -1);

		if (selectedHall == 3)
			//d2
			information = getResources().getStringArray(R.array.d2shultzInfo);
		else if (selectedHall == 4 || selectedHall == 5 || selectedHall == 6
				|| selectedHall == 7 || selectedHall == 9)
			//all other restaurants
			information = getResources().getStringArray(R.array.info);
		else
			information = getResources().getStringArray(R.array.lackingInfo);

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

	/**
	 * Creates the action bar icons.
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getSupportMenuInflater();
		inflater.inflate(R.menu.actionbar_dining, menu);
		return true;
	}

	/**
	 * Handles the clicking of action bar icons.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.actionbar_dining_search:
			onSearchRequested();
			return true;
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	private void setTableName()
	{
		switch(selectedHall)
		{
		case 3: // d2
			tableName = "d2"; break;
		case 5: // dx
			tableName = "d2"; break;
		case 4:
			tableName = "deets"; break;
		case 6:
			tableName = "hokieGrill"; break;
		case 7:
			tableName = "owens"; break;
		case 9:
			tableName = "westend"; break;
		default:
			tableName = ""; break;
		}
	}

	private void createNextActivity(int position)
	{
		Intent i;
		String message;
		String title;
		switch (position)
		{
		//		case 0: onSearchRequested(); break;
		case 0:
			message = makeHoursString();
			title = "Hours Of Operation";
			showDialog(message, title); break;
		case 1:
			i = new Intent(Dining_InfoActivity.this, Dining_SubRestaurants.class);
			i.putExtra("tableName", tableName);
			startActivity(i); break;
		case 2:
			message = makeLocationString();
			//						title = "Location";
			//						showDialog(message, title); 
			break;
		case 3:
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
		case 4:
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
		switch(selectedHall)
		{
		case 3: //d2
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
		case 4: //deets
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
		case 5: //dx
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
		case 6: //hokiegrill
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
		case 7: //owens
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
		case 9: //west end
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
		switch(selectedHall)
		{
		case 3: //d2
			hours = "M-F:   7:00am - 9:30am" +
					"\n         11:00am - 2:00pm" +
					"\n           5:00pm - 7:00pm\n" +
					"\nSun:  11:00am - 3:00pm\n" +
					"            3:00pm - 7:00pm"; break;
		case 4: //deets
			hours = "M-F:    7:30am - 12:00am\n" +
					"Sat:   10:00am - 12:00am\n" +
					"Sun:  10:00am - 12:00am"; break;
		case 5: //dx
			hours = "M-F:  7:00am - 2:00am\n" +
					"Sat:   9:00am - 2:00am\n" +
					"Sun:  9:00am - 2:00am"; break;
		case 6: //hokiegrill
			hours = "M-F:  10:30am - 9:00pm\n" +
					"Sat:   12:00pm - 8:00pm"; break;
		case 7: //owens
			hours = "M-F:  10:30am - 8:00pm\n"	+
					"\nSat:   10:30am - 3:00pm\n" +
					"            3:00pm - 8:00pm\n" +
					"\nSun:  10:30am - 3:00pm\n" +
					"           3:00pm - 8:00pm"; break;
		case 9: //west end
			hours = "M-F:  10:30am - 8:00pm\n"	+
					"Sat:   11:00am - 7:00pm\n" + 
					"Sun: 11:00am - 8:00pn"; break;
		default:
			hours = "No information available yet."; break;
		}
		return hours;
	}

	private String makeContactString()
	{
		String info = "";
		switch(selectedHall)
		{
		case 3: //d2
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
		case 4: //deets
			info = "Tel: 540-231-7101\n" +
					"\nManager: Don Harvey\n" +
					"Assistant Manager: Judy Young"; break;
		case 5: //dx
			info = "Tel: 540-231-2184\n" +
					"Fax: 540-231-9046\n"+
					"\nAssistant Director: Kelvin Bergsten\n" +
					"Operations Manager: Dennis Luz\n" +
					"Assistant Manager: Vacant\n" +
					"Student General Manager: Kevin Quinn\n" +
					"Student General Manager: Andrew Sinnes"; break;
		case 6: //hokiegrill
			info = "Tel: 540-231-6187\n" +
					"\nAssistant Director: Steve Opeka\n" +
					"Operations Manager: Jessica Hale\n" +
					"Operations Manager: Jason McAlinden\n" +
					"Office Specialist: Laurie Osborne"; break;
		case 7: //owens
			info = "Tel: 540-231-6187\n" +
					"\nAssistant Director: Steve Swannell\n" +
					"Executive Chef: John Scherer\n" +
					"Operations Manager: Kelli Ballentine Koblish\n" +
					"Operations Manager: Jessia Hale\n" +
					"Operations Manager: Alisha Barker\n" +
					"Operations Manager: Steve Swannell\n" +
					"Food Production Manager: Betty Shields\n" +
					"Stockroom Supervisor: Frank Sheppard\n"; break;
		case 9: //west end
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
		String status = "";
		Dining_DiningHall hall = null;
		switch(selectedHall)
		{
		case 0: //abp GLC
			hall = new Dining_DiningHall_ABPGLC();
			break;
		case 1: //abp squires cafe
			hall = new Dining_DiningHall_ABPSquiresCafe();
			break;
		case 2: //abp squires kiosk
			hall = new Dining_DiningHall_ABPSquiresKiosk();
			break;
		case 3: //d2
			hall = new Dining_DiningHall_D2();
			break;
		case 4: //deets
			hall = new Dining_DiningHall_Deets();
			break;
		case 5: //dx
			hall = new Dining_DiningHall_DX();
			break;
		case 6: //hokie_grill
			hall = new Dining_DiningHall_HokieGrill();
			break;
		case 7: //owens
			hall = new Dining_DiningHall_Owens();
			break;
		case 8: //sbarro
			hall = new Dining_DiningHall_Sbarro();
			break;
		case 9: //west_end
			hall = new Dining_DiningHall_WestEnd();
			break;
		case 10: // Atomic Pizzeria (Turner Place)
			hall = new Dining_DiningHall_TurnerPlace_AtomicPizzeria();
			break;
		case 11: // brueggers bagels
			hall = new Dining_DiningHall_TurnerPlace_BrueggersBagels();
			break;
		case 12: // dolci e cafe
			hall = new Dining_DiningHall_TurnerPlace_DolciCaffe();
			break;
		case 13: // fire grill
			hall = new Dining_DiningHall_TurnerPlace_FireGrill();
			break;
		case 14: // jamba juice
			hall = new Dining_DiningHall_TurnerPlace_JambaJuice();
			break;
		case 15: // origami grill
			hall = new Dining_DiningHall_TurnerPlace_OrigamiGrill();
			break;
		case 16: // origami sushi
			hall = new Dining_DiningHall_TurnerPlace_OrigamiSushi();
			break;
		case 17: // qdoba
			hall = new Dining_DiningHall_TurnerPlace_QdobaMexicanGrill();
			break;
		case 18: // soup garden
			hall = new Dining_DiningHall_TurnerPlace_SoupGarden();
			break;
		}

		//		ImageView ex = (ImageView) findViewById(R.id.exclamation_point);

		if (hall != null) {
			if (hall.getDiningHallState() == DiningHallState.OPEN) {
				status = "This dining hall is currently open.";
				//				ex.setVisibility(ImageView.GONE);
			} else if (hall.getDiningHallState() == DiningHallState.OPEN_CLOSING_SOON) {
				status = "This dining hall is currently open, but will be closing in less than an hour.";
				//				ex.setVisibility(ImageView.VISIBLE);
			} else if (hall.getDiningHallState() == DiningHallState.CLOSED) {
				status = "This dining hall is currently closed.";
				//				ex.setVisibility(ImageView.GONE);
			} else if (hall.getDiningHallState() == DiningHallState.CLOSED_OPENING_SOON) {
				status = "This dining hall is currently closed, but will be opening in less than an hour.";
				//				ex.setVisibility(ImageView.VISIBLE);
			}
		}

		return status;
	}

	private void getHeaderInfo()
	{
		TextView hallName = (TextView) findViewById(R.id.current_restaurant);
		TextView hallStatus = (TextView) findViewById(R.id.current_restaurant_status);
		hallStatus.setText(getStatus());
		ImageView img = (ImageView) findViewById(R.id.restaurant_icon);

		switch(selectedHall)
		{
		case 0: //abp GLC
			hallName.setText("Au Bon Pain (GLC)");
			img.setImageResource(R.drawable.logo_abp_glc);
			break;
		case 1: //abp squires cafe
			hallName.setText("Au Bon Pain\n(Squires Caf\u00E9)");
			img.setImageResource(R.drawable.logo_abp_cafe);
			break;
		case 2: //abp squires kiosk
			hallName.setText("Au Bon Pain\n(Squires Kiosk)");
			img.setImageResource(R.drawable.logo_abp_kiosk);
			break;
		case 3: //d2
			hallName.setText("D2");
			img.setImageResource(R.drawable.logo_d2);
			tableNumber = "540-231-6130"; break;
		case 4: //deets
			hallName.setText("Deet's Place");
			img.setImageResource(R.drawable.logo_deets);
			tableNumber = "540-231-7101"; break;
		case 5: //dx
			hallName.setText("DXpress");
			img.setImageResource(R.drawable.logo_dx);
			tableNumber = "540-231-2184"; break;
		case 6: //hokie_grill
			hallName.setText("Hokie Grill & Co.");
			img.setImageResource(R.drawable.logo_hokie_grill);
			tableNumber = "540-231-6187"; break;
		case 7: //owens
			hallName.setText("Owens Food Court");
			img.setImageResource(R.drawable.logo_owens);
			tableNumber = "540-231-6187"; break;
		case 8: //sbarro
			hallName.setText("Sbarro");
			img.setImageResource(R.drawable.logo_sbarro);
			break;
		case 9: //west_end
			hallName.setText("West End Market");
			img.setImageResource(R.drawable.logo_west_end);
			tableNumber = "540-231-4779"; break;
		case 10: // Atomic Pizzeria (Turner Place)
			hallName.setText("Atomic Pizzeria\n(Turner Place)");
			img.setImageResource(R.drawable.logo_atomic_pizzeria);
			break;
		case 11: // brueggers bagels
			hallName.setText("Bruegger's Bagels\n(Turner Place)");
			img.setImageResource(R.drawable.logo_brueggers_bagels);
			break;
		case 12: // dolci e cafe
			hallName.setText("Dolci e Caffe\n(Turner Place)");
			img.setImageResource(R.drawable.logo_dolci_e_caffe);
			break;
		case 13: // fire grill
			hallName.setText("1872 Fire Grill\n(Turner Place)");
			img.setImageResource(R.drawable.logo_fire_grill);
			break;
		case 14: // jamba juice
			hallName.setText("Jamba Juice\n(Turner Place)");
			img.setImageResource(R.drawable.logo_jamba_juice);
			break;
		case 15: // origami grill
			hallName.setText("Origami Grill\n(Turner Place)");
			img.setImageResource(R.drawable.logo_origami);
			break;
		case 16: // origami sushi
			hallName.setText("Origami Sushi\n(Turner Place)");
			img.setImageResource(R.drawable.logo_origami);
			break;
		case 17: // qdoba
			hallName.setText("Qdoba Mexican Grill\n(Turner Place)");
			img.setImageResource(R.drawable.logo_qdoba);
			break;
		case 18: // soup garden
			hallName.setText("Soup Garden\n(Turner Place)");
			img.setImageResource(R.drawable.logo_soup_garden);
			break;
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
			case 0: iv.setImageResource(R.drawable.icon_clock); break;
			case 1: iv.setImageResource(R.drawable.icon_food); break;
			case 2: iv.setImageResource(R.drawable.icon_map); break;
			case 3: iv.setImageResource(R.drawable.icon_phone); break;
			case 4: iv.setImageResource(R.drawable.icon_dollar_sign);
			}
			return v;
		}
	}
}
