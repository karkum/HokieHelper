package org.mad.app.hokiehelper;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class Dining_TrackDietActivity extends Activity
{
	private Dining_TrackCanvas pcc;
	private int fat = 0;
	private int carbs = 0;
	private int protein = 0;
	private String todayString;
	private String weekString;
	private String monthString;

	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.track_layout);

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		final int screenHeight = displaymetrics.heightPixels;
		final int screenWidth = displaymetrics.widthPixels;

		//Tidy up the array
		Dining_CalWrapper[] calArray = Dining_HallDietChooserActivity.calValues;
		Date d = new Date(System.currentTimeMillis());
		String[] today = d.toString().split("\\s+");
		for (int i = 0; i < calArray.length; i++)
		{
			if (calArray[i].getCal() != -1)
			{
				String[] temp = calArray[i].getDate().toString().split("\\s+");
				int diff = getDifference(today, temp);
				if (diff <= 29)
				{
					calArray[calArray.length - diff - 1] = calArray[i];
					if (calArray.length - diff - 1 != i)
						calArray[i] = new Dining_CalWrapper(new Date(10000000), -1, -1, -1, -1);
				}
				else
					calArray[i] = new Dining_CalWrapper(new Date(10000000), -1, -1, -1, -1);
			}
		}

		//Update the changes in the file
		try
		{
			FileOutputStream fos = openFileOutput("track.txt", MODE_PRIVATE);
			for (int i = 0; i < calArray.length; i++)
				fos.write(calArray[i].toString().getBytes());
			fos.close();
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		//Calculate values
		int[] caloriesArray = new int[3];

		if (calArray[calArray.length - 1].getCal() != -1)
		{
			caloriesArray[0] = calArray[calArray.length - 1].getCal();
			fat = calArray[calArray.length - 1].getFat();
			carbs = calArray[calArray.length - 1].getCarbs();
			protein = calArray[calArray.length - 1].getProtein();
		}

		for (int i = 23; i < calArray.length; i++)
		{
			if (calArray[i].getCal() != -1)
			{
				caloriesArray[1] += calArray[i].getCal();
			}
		}

		for (int i = 0; i < calArray.length; i++)
		{
			if (calArray[i].getCal() != -1)
			{
				caloriesArray[2] += calArray[i].getCal();
			}
		}

		todayString = caloriesArray[0] + " cal";
		weekString = caloriesArray[1] + " cal";
		monthString = caloriesArray[2] + " cal";

		TextView tv = (TextView) findViewById(R.id.label);
		tv.setText("Today: " + todayString);

		setUpCanvas(0, fat, carbs, protein, screenWidth, screenHeight);

		//Listeners for buttons
		final Button b1 = (Button) findViewById(R.id.dailyButton);
		final Button b2 = (Button) findViewById(R.id.weeklyButton);
		final Button b3 = (Button) findViewById(R.id.monthlyButton);
		Button info = (Button) findViewById(R.id.info_button);

		b1.setWidth(screenWidth / 3);
		b2.setWidth(screenWidth / 3);
		b3.setWidth(screenWidth / 3);
		tv.setWidth((int) (screenWidth * 0.80));
		info.setWidth((int) (screenWidth * 0.07));
		info.setHeight((int) (screenWidth * 0.07));

		b1.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Button info_button = (Button) findViewById(R.id.info_button);
				info_button.setVisibility(View.VISIBLE);
				TextView tv = (TextView) findViewById(R.id.label);
				tv.setText("Today: " + todayString);
				LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
				layout.removeView(pcc);
				setUpCanvas(0, fat, carbs, protein, screenWidth, screenHeight);
			}
		});

		b2.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Button info_button = (Button) findViewById(R.id.info_button);
				info_button.setVisibility(View.INVISIBLE);
				TextView tv = (TextView) findViewById(R.id.label);
				tv.setText("Last 7 days: " + weekString);
				LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
				layout.removeView(pcc);
				setUpCanvas(1, fat, carbs, protein, screenWidth, screenHeight);
			}
		});

		b3.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				Button info_button = (Button) findViewById(R.id.info_button);
				info_button.setVisibility(View.INVISIBLE);
				TextView tv = (TextView) findViewById(R.id.label);
				tv.setText("Last 30 days: " + monthString);
				LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
				layout.removeView(pcc);
				setUpCanvas(2, fat, carbs, protein, screenWidth, screenHeight);
			}
		});

		info.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(Dining_TrackDietActivity.this);

				dlgAlert.setPositiveButton("Reset",	new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						try
						{
							FileOutputStream fos = openFileOutput("track.txt", MODE_PRIVATE);
							for (int i = 0; i < Dining_HallDietChooserActivity.calValues.length; i++)
							{
								Dining_HallDietChooserActivity.calValues[i].setCal(-1);
								Dining_HallDietChooserActivity.calValues[i].setCarbs(-1);
								Dining_HallDietChooserActivity.calValues[i].setProtein(-1);
								Dining_HallDietChooserActivity.calValues[i].setFat(-1);
								Dining_HallDietChooserActivity.calValues[i].setDate(new Date(10000000));
								fos.write(Dining_HallDietChooserActivity.calValues[i].toString().getBytes());
							}
							fos.close();
						}
						catch (FileNotFoundException e)
						{
							e.printStackTrace();
						}
						catch (IOException e)
						{
							e.printStackTrace();
						}
						
						fat = 0;
						carbs = 0;
						protein = 0;
						todayString = "0 cal";
						weekString = "0 cal";
						monthString = "0 cal";
						TextView tv = (TextView) findViewById(R.id.label);
						tv.setText("Today: " + todayString);
						LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
						layout.removeView(pcc);
						setUpCanvas(0, fat, carbs, protein, screenWidth, screenHeight);
						
						deleteFile("listFood.txt");
					}
				});
				
				dlgAlert.setNegativeButton("OK", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						//Do Nothing
					}
				});
				
				dlgAlert.setNeutralButton("My Diet", new DialogInterface.OnClickListener()
				{
					public void onClick(DialogInterface dialog, int which)
					{
						Intent i = new Intent(Dining_TrackDietActivity.this, Dining_FoodListActivity.class);
						startActivity(i);
					}
				});
				
				dlgAlert.setMessage("Total fat should be less than 30%\nTotal carbs should " +
						"be 50% to 60%\nTotal protein should be 10% to 20%");
				dlgAlert.setTitle("Ideal Daily Diet");
				dlgAlert.setCancelable(true);
				dlgAlert.create().show();
			}
		});
	}

	public void setUpCanvas(int mode, int fat, int carbs, int protein, int width, int height)
	{
		pcc = new Dining_TrackCanvas(this, mode, fat, carbs, protein, width, height);
		LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
		Bitmap result = Bitmap.createBitmap(1000, 1000, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(result);
		pcc.draw(canvas);
		pcc.setLayoutParams(new LayoutParams(1000, 1000));
		layout.addView(pcc);
	}

	public int getDifference(String[] s1, String[] s2)
	{
		int s1Year = Integer.parseInt(s1[5]);
		int s2Year = Integer.parseInt(s2[5]);
		int s1Month = getMonth(s1[1]);
		int s2Month = getMonth(s2[1]);
		int s1Day = Integer.parseInt(s1[2]);
		int s2Day = Integer.parseInt(s2[2]);
		if (s1Year - s2Year < 2)
		{
			if (s1Year == s2Year)
			{
				if (s1Month == s2Month)
					return s1Day - s2Day;
				else if (s1Month - s2Month < 2)
					return s1Day + (getDays(s2Month, s2Year) - s2Day);
				else
					return 100;
			}
			else if (s1Month == 1 && s2Month == 12)
			{
				return s1Day + (getDays(s2Month, s2Year) - s2Day);
			}
			else
				return 100;
		}
		else
			return 100;
	}

	public int getMonth(String s)
	{
		if (s.equals("Jan"))
			return 1;
		if (s.equals("Feb"))
			return 2;
		if (s.equals("Mar"))
			return 3;
		if (s.equals("Apr"))
			return 4;
		if (s.equals("May"))
			return 5;
		if (s.equals("Jun"))
			return 6;
		if (s.equals("Jul"))
			return 7;
		if (s.equals("Aug"))
			return 8;
		if (s.equals("Sep"))
			return 9;
		if (s.equals("Oct"))
			return 10;
		if (s.equals("Nov"))
			return 11;
		if (s.equals("Dec"))
			return 12;
		return -1;
	}

	public int getDays(int month, int year)
	{
		switch (month)
		{
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12: return 31;
		case 4:
		case 6:
		case 9:
		case 11: return 30;
		case 2: if (year % 4 == 0) return 29; else return 28;
		}
		return -1;
	}
}
