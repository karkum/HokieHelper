package org.mad.app.hokiehelper;

import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

public class Info_LibraryHoursInformationActivity extends SherlockActivity {
	TextView time;
	TextView libStatus;
	TextView libInfo;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.library);

		time = (TextView) (findViewById(R.id.current_time));
		libStatus = (TextView) (findViewById(R.id.lib_status));
		libInfo = (TextView) (findViewById(R.id.lib_info));
		// Sets up action bar title/navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle("Library Hours");
		getHours();

	}

	private void getHours() {
		Calendar cal = Calendar.getInstance();
		Date now = new Date();
		time.setText(DateFormat.getTimeInstance(DateFormat.SHORT).format(now));

		// If during spring break
		  Calendar calstart = Calendar.getInstance();
		  calstart.set(Calendar.YEAR, 2012);
		  calstart.set(Calendar.MONTH, Calendar.MARCH);
		  calstart.set(Calendar.DAY_OF_MONTH, 3);
		  calstart.set(Calendar.HOUR, 0);
		  calstart.set(Calendar.MINUTE, 0);
		  calstart.set(Calendar.SECOND, 0);
		 
		 
		  Calendar calend = Calendar.getInstance();
		  calend.set(Calendar.YEAR, 2012);
		  calend.set(Calendar.MONTH, Calendar.MARCH);
		  calend.set(Calendar.DAY_OF_MONTH, 11);
		  calend.set(Calendar.HOUR, 0);
		  calend.set(Calendar.MINUTE, 0);
		  calend.set(Calendar.SECOND, 0);

		if (now.getTime() > calstart.getTimeInMillis() && now.getTime() < calend.getTimeInMillis()) {
			libStatus.setText("Spring Break Hours:\n Weekdays 7:30am to 8:00pm and Weekends 9:00am to 8:00pm");
			TextView t = (TextView) findViewById(R.id.lib_curr);
			t.setVisibility(View.INVISIBLE);
		} else {
			// Sunday
			if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
				if (cal.get(Calendar.HOUR_OF_DAY) < 9) {
					libStatus.setText("CLOSED");
					libStatus.setTextColor(Color.RED);
					int hours = 8 - cal.get(Calendar.HOUR_OF_DAY);

					int minutes = 60 - cal.get(Calendar.MINUTE);
					libInfo.setText("Library will open  in " + hours
							+ " hours and " + minutes + " minutes");
					return;
				} else {
					libStatus.setText("OPEN");
					libStatus.setTextColor(Color.GREEN);
					return;
				}
			} else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
				if (cal.get(Calendar.HOUR_OF_DAY) < 9
						|| cal.get(Calendar.HOUR_OF_DAY) >= 20) {
					libStatus.setText("CLOSED");
					libStatus.setTextColor(Color.RED);
					int hours = 32 - cal.get(Calendar.HOUR_OF_DAY);
					int mins = 60 - cal.get(Calendar.MINUTE);

					libInfo.setText("Library will open in " + hours
							+ " hours and " + mins + " minutes");

					return;
				} else {
					libStatus.setText("OPEN");
					libStatus.setTextColor(Color.GREEN);
					int hours = 19 - cal.get(Calendar.HOUR_OF_DAY);
					int mins = 60 - cal.get(Calendar.MINUTE);
					libInfo.setText("Library will close in " + hours
							+ " hours and " + mins + " minutes");

					return;
				}
			} else if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
				if (cal.get(Calendar.HOUR_OF_DAY) >= 20) {
					libStatus.setText("CLOSED");
					libStatus.setTextColor(Color.RED);
					int hours = 31 - cal.get(Calendar.HOUR_OF_DAY);
					int minutes = 60 - cal.get(Calendar.MINUTE);
					libInfo.setText("Library will open in " + hours
							+ " hours and " + minutes + " minutes");
					return;
				} else {
					libStatus.setText("OPEN");
					libStatus.setTextColor(Color.GREEN);
					int hours = 19 - cal.get(Calendar.HOUR_OF_DAY);
					int mins = 60 - cal.get(Calendar.MINUTE);
					libInfo.setText("Library will close in " + hours
							+ " hours and " + mins + " minutes");
					return;
				}
			} else {
				libStatus.setText("OPEN");
				libStatus.setTextColor(Color.GREEN);
				libInfo.setText("Library is open for 24 hours today");
				return;
			}
		}
	}
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		}
		return false;
	}
}
