package org.mad.app.hokiehelper;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;

public class Dining_FoodListActivity extends SherlockListActivity
{
	ArrayList<String> foodList = new ArrayList<String>();
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subrestaurant);

		// Sets up action bar title/navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle("My Diet");

		//Read from the file
		try
		{
			FileInputStream fis = openFileInput("listFood.txt");
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

			//Add thing to list
			String[] str = s.split("\\|");
			for (int i = 0; i < str.length - 1; i = i + 3)
			{
				Date d = new Date(System.currentTimeMillis());
				Date fromFile = new Date(Long.parseLong(str[i + 1]));
				String[] dString = d.toString().split("\\s+");
				String[] fileString = fromFile.toString().split("\\s+");
				if (dString[1].equals(fileString[1]) && dString[2].equals(fileString[2])
						&& dString[5].equals(fileString[5]))
					foodList.add(str[i] + " - " + str[i + 2] + " cal");
			}
		}
		catch (FileNotFoundException e)
		{
			//Do Nothing
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		if (foodList.size() == 0)
		{
			foodList.add("No food items added");
		}
		setListAdapter(new ArrayAdapter<String>(this, R.layout.real_list_item, foodList));
		ListView lv = getListView();
		lv.setTextFilterEnabled(true);

		View parent = (View) lv.getParent();
		parent.setBackgroundResource(R.drawable.bg_tan);
		lv.setCacheColorHint(0);
	}

	/**
	 * Handles the clicking of action bar icons.
	 */
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}
}
