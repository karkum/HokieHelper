package org.mad.app.hokiehelper;

import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
import com.actionbarsherlock.view.MenuItem;
/**
 * This activity represents what the user sees when they click About This App in the 
 * "Information" part of the app. It simply gives credit where it is due.
 * @author Karthik Kumar
 *
 */
public class Info_AboutThisAppActivity extends SherlockActivity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutapp);
		
		// Sets up action bar title/navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle("About");

		TextView info = (TextView) findViewById(R.id.about_text);
		info.setText(R.string.aboutapp);
		
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
