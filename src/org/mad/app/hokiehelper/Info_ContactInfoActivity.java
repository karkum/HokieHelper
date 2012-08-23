package org.mad.app.hokiehelper;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;
/**
 * This class displays contact information for both the dining halls or for VT related
 * contacts.
 * @author karthik
 *
 */
public class Info_ContactInfoActivity extends SherlockListActivity {

	private ArrayList<Info_Contact> contacts = new ArrayList<Info_Contact>();

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subrestaurant);
		
		// Sets up action bar title/navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle("Important Contact Information");
		
		generateContacts();
		String[] names = new String[contacts.size()];
		for (int i = 0; i < names.length; i++)
			names[i] = contacts.get(i).getName();

		ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this,
				R.layout.real_list_item, names);

		setListAdapter(stringAdapter);
		ListView lv = getListView();

		lv.setTextFilterEnabled(true);

		View parent = (View) lv.getParent();
		parent.setBackgroundResource(R.drawable.bg_tan);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					final int position, long id) {

				String number = "Call " + contacts.get(position).getName();
				new AlertDialog.Builder(Info_ContactInfoActivity.this)
				.setTitle("Contact Options")
				.setItems(
						new String[] { number,
								"Get Contact Information" },
								new DialogInterface.OnClickListener() {
							public void onClick(
									DialogInterface dialoginterface,
									int i) {
								executeContactOption(i, position);
							}
						}).show();
			}
		});
		lv.setCacheColorHint(0);
	}

	private void generateContacts() {
		Info_Contact police = new Info_Contact("Virginia Tech Police (Non-emergency)",
				"(540) 231-6411",
				"Non-emergency phone only, call 911 in case of emergency");
		Info_Contact switchboard = new Info_Contact("Virginia Tech Switchboard",
				"(540) 231-6000", "Virginia Tech Switchboard");
		Info_Contact envServices = new Info_Contact(
				"Environmental Health and Safety Services", "(540) 231-6411",
				"Via VT Police");
		Info_Contact deanOfStudents = new Info_Contact(
				"Dean of Students Office",
				"(540) 231-3787",
				"Monday-Friday, 8 a.m. - 5 p.m.: (540) 231-3787. \nAfter hours/weekends: (540) 231-6411 (via VT police)");
		Info_Contact schiffert = new Info_Contact(
				"Schiffert Health Center",
				"(540) 231-5313",
				"Monday-Friday, 8 a.m. - 5 p.m. and Saturday, 9 a.m. - Noon: (540) 231-53.\n After hours/weekends: (540) 231-6444 ");
		Info_Contact cook = new Info_Contact(
				"Cook Counseling Center",
				"(540) 231-6557",
				"Monday-Friday, 8 a.m. - 5 p.m. : (540) 231-6557\nAfter hours/weekends: (540) 231-6444");
		Info_Contact registrar = new Info_Contact(
				"Registrar's Office",
				"(540) 231-6252",
				"Registrar's Office\n250 Student Services Building\nBlacksburg, VA 24061\nE-mail: registrar@vt.edu");
		Info_Contact studentAffairs = new Info_Contact(
				"Student Affairs",
				"(540) 231-6272",
				"Student Affiars\n112 Burruss Hall\nBlacksburg, VA 24061\nE-mail: studentaffairs@vt.edu");
		Info_Contact safeRide = new Info_Contact(
				"Safe Ride",
				"(540) 231-7233",
				"The Virginia Tech Police Department sponsors a nighttime safety escort service called â€œSafe Ride.â€� This service is available to all students, faculty, staff, and visitors to the university. Safe Ride operates from dusk until dawn and provides transportation or a walking escort, upon request, to persons who must cross campus during the nighttime alone. Safe Ride may be contacted by calling 540-231-SAFE (7233).");
		Info_Contact parking = new Info_Contact(
				"Virginia Tech Parking Services",
				"(540) 231-3200",
				"455 Tech Center Drive\nBlacksburg, VA 24061\nOr Satellite Office on ground level of the Perry Street Parking Garage");
		Info_Contact facilities = new Info_Contact(
				"Facilities Services",
				"(540) 231-6557",
				"Technicians are available on most shifts to respond to emergency calls until midnight Monday through Friday and from 10 a.m. to 10 p.m. Saturday and Sunday.\nDuring normal business hours, Monday-Friday, 7:30 a.m. - 5 p.m.: (540) 231-4300\nTo report an emergency outside of normal working hours call the Tech Police at 231-6411.");
		Info_Contact women = new Info_Contact(
				"Women's Center at Virginia Tech",
				"(540) 231-7806",
				"Phone: (540) 231-7806, or call the 24-hour crisis hotline operated by the Women's Resource Center of the New River Valley at (540) 639-1123.");
		Info_Contact fourhelp = new Info_Contact(
				"4Help Computer Consulting",
				"(540) 231-4357",
				"HokieSPA/Banner problems\nE-mail blacklist problems\nGeneral computing questions\nVirus help\nComputer requirement");
		Info_Contact recsports = new Info_Contact("Virginia Tech Recreations Sports",
				"(540) 231-6856", "142 McComas Hall recsports@vt.edu");
		contacts.add(fourhelp);
		contacts.add(cook);
		contacts.add(deanOfStudents);
		contacts.add(envServices);
		contacts.add(facilities);
		contacts.add(registrar);
		contacts.add(safeRide);
		contacts.add(schiffert);
		contacts.add(studentAffairs);
		contacts.add(parking);
		contacts.add(police);
		contacts.add(switchboard);
		contacts.add(recsports);
		contacts.add(women);
		
	}

	private void executeContactOption(int i, int chosen) {
		if (i == 0) {
			String n = "tel: "
					+ contacts.get(chosen).getNumber().replace("()-", "");
			startActivity(new Intent(Intent.ACTION_CALL, Uri.parse(n)));
		} else {
//			Intent intent = new Intent(ContactInfoActivity.this,
//					DisplayDiningContactInfoActivity.class);
//			intent.putExtra("info", contacts.get(chosen).getInfo());
//			startActivity(intent);
			AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Info_ContactInfoActivity.this);

			dlgAlert.setPositiveButton("OK", new DialogInterface.OnClickListener()
			{
				public void onClick(DialogInterface dialog, int which)
				{
					//Do Nothing
				}
			});
					
			dlgAlert.setMessage(contacts.get(chosen).getInfo());
			dlgAlert.setTitle("Contact Information");
			dlgAlert.setCancelable(true);
			dlgAlert.create().show();
		}
	}
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return false;
	}

}
