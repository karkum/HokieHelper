package org.mad.app.hokiehelper;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
/**
 * This activity represents what the user sees when they click About This App in the 
 * "Information" part of the app. It simply gives credit where it is due.
 * @author Karthik Kumar
 *
 */
public class Info_AboutThisAppActivity extends Activity {
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.aboutapp);

		TextView info = (TextView) findViewById(R.id.about_text);
		info.setText(R.string.aboutapp);
		
	}
}
