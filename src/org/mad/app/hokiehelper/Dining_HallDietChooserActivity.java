package org.mad.app.hokiehelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Date;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
public class Dining_HallDietChooserActivity extends SherlockActivity implements Runnable {

	private Button buttonDiningHallInformation;
	private Button buttonDietTracker;
	
	private ProgressDialog dialogProgressBar;
	public static Dining_CalWrapper[] calValues = new Dining_CalWrapper[30];

	
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dining_halldietchooser);
        
		// Sets up action bar title/navigation
		// Sets up action bar title/navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle("Dining");
		
		// Runs the database initialization in a foreground thread
		// to let the user know something is happening
		dialogProgressBar = ProgressDialog.show(this, "Loading...",
				"Loading nutrition facts...  This should only take a minute.");
		
		Thread thread = new Thread(this);
		thread.start();
		
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
	 * Loads the nutrition facts into the database.
	 */
	public void run() {
		Dining_DBAdapter db = new Dining_DBAdapter(this, getResources().getStringArray(R.array.dbFolders), 
				this.getResources());
		db.open();
		db.close();

		try
		{
			FileInputStream fis = openFileInput("track.txt");
			String s = "";
			while (true)
			{
				byte[] b = new byte[100];
				int i = fis.read(b, 0, 100);
				s  += new String(b);
				if (i != 100)
					break;
			}
			fis.close();
			String[] str = s.split("\\s+");
			for (int i = 0; i < str.length - 1; i = i + 5)
				calValues[i / 5] = new Dining_CalWrapper(new Date(Long.parseLong(str[i])), 
						Integer.parseInt(str[i + 1]), Integer.parseInt(str[i + 2]), 
						Integer.parseInt(str[i + 3]), Integer.parseInt(str[i + 4]));
			//			deleteFile("track.txt");
		} 
		catch (FileNotFoundException e)
		{
			for (int i = 0; i < calValues.length; i++)
			{
				if (calValues[i] == null)
					calValues[i] = new Dining_CalWrapper(new Date(10000000), -1, -1, -1, -1);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		try
		{
			FileInputStream fis = openFileInput("listFood.txt");
			fis.close();
			//			deleteFile("listFood.txt");
		}
		catch (FileNotFoundException e)
		{
			// Do Nothing
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		handler.sendEmptyMessage(0);
	}

	/**
	 * Closes the progress dialog when the nutrition fact loading is completed.
	 */
	private Handler handler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			dialogProgressBar.dismiss();
		}
	};

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
