package org.mad.app.hokiehelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;

/**
 * This is the fouth icon on the main page. It simply displays a list of
 * information available to the user
 * 
 * @author karthik
 * 
 */
public class Info_MainActivity extends SherlockListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subrestaurant);

		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setTitle("Information");


		String[] options = { "Important Contact Information",
				"Academic Calendar", "Library Hours", "Parking Information",
		"About HokieHelper" };
		ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>(this,
				R.layout.real_list_item, options);

		setListAdapter(stringAdapter);
		ListView lv = getListView();

		lv.setTextFilterEnabled(true);

		View parent = (View) lv.getParent();
		parent.setBackgroundResource(R.drawable.bg_tan);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				createNextActivity(position);
			}
		});
		lv.setCacheColorHint(0);
	}

	protected void createNextActivity(int position) {
		Intent i;
		if (position == 0) {
			i = new Intent(Info_MainActivity.this, Info_ContactInfoActivity.class);
			startActivity(i);
		} else if (position == 1) {
			i = new Intent(Info_MainActivity.this, Info_CalendarActivity.class);
			startActivity(i);
		} else if (position == 2) {
			i = new Intent(Info_MainActivity.this, Info_LibraryHoursInformationActivity.class);
			startActivity(i);
		} else if (position == 3) {
			i = new Intent(Info_MainActivity.this, Info_ParkingListActivitiy.class);
			startActivity(i);
		}
		else if (position == 4){
			i = new Intent(Info_MainActivity.this, Info_AboutThisAppActivity.class);
			startActivity(i);
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
