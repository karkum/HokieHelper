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

public class Info_ParkingListActivitiy extends SherlockListActivity {

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subrestaurant);
		
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle("Parking Information");
		
		String[] options = { "Student without permit",
				"Resident Student with permit",
				"Commuter or Graduate Student with permit",
				"Faculty or Staff with permit", "Visitor" };
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

	private void createNextActivity(int position) {
		Intent i = new Intent(Info_ParkingListActivitiy.this, Info_ParkingActivity.class);
		i.putExtra("chosen", position);
		startActivity(i);
		
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
