package org.mad.app.hokiehelper;

import android.os.Bundle;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockActivity;
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
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setTitle("About HokieHelper");

		TextView info = (TextView) findViewById(R.id.about_text);
		info.setText(R.string.aboutapp);
		
	}
}
