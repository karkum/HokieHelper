package org.mad.app.hokiehelper;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;

/**
 * The activity that displays when "Dining" is chosen from the dashboard.
 * Allows the user to jump into "Dining Hall Information" or "Diet Tracker"
 * from here.
 * 
 * @author Corey Buttel
 */
public class Dining_HallDietChooserActivity extends SherlockActivity {

	private Button buttonDiningHallInformation;
	private Button buttonDietTracker;
	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_halldietchooser);
        
		// Sets up action bar title/navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setTitle("Dining");
		
		// When "Dining Hall Information" button is clicked, goes to that activity
		buttonDiningHallInformation = (Button) findViewById(R.id.button_choose_dininghallinfo);
		buttonDiningHallInformation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Dining_HallDietChooserActivity.this, Dining_HallInformationActivity.class));
			}
		});
		
		// When "Diet Tracker" button is clicked, goes to that activity
		buttonDietTracker = (Button) findViewById(R.id.button_choose_diettracker);
		buttonDietTracker.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				startActivity(new Intent(Dining_HallDietChooserActivity.this, Dining_TrackDietActivity.class));
			}
		});
	}

	/**
	 * Sets the listeners for each of the action bar items.
	 */
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
