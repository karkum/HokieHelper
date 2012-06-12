package org.mad.app.hokiehelper;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.actionbarsherlock.app.SherlockActivity;

/**
 * The main "Dining Hall Information" view, which shows the state of all
 * dining halls and allows the user to select one for further information.
 * 
 * @author Corey Buttel
 *
 */
public class Dining_HallInformationActivity extends SherlockActivity
{
	public static int restaurantChosen;
	private Dining_Handler diningHandler;
	public static boolean flag;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dining_main);

		// Sets up action bar title/navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayShowHomeEnabled(false);
		getSupportActionBar().setTitle("Dining Hall Information");

		diningHandler = new Dining_Handler();
		update();
		ImageView refresh = (ImageView) findViewById(R.id.refresh);
		refresh.setOnClickListener(new OnClickListener()
		{
			public void onClick(View v)
			{
				update();
			}
		});
	}



	@Override
	protected void onResume()
	{
		update();
		super.onResume();
	};

	@Override
	protected void onStart()
	{
		update();
		super.onStart();
	};

	public void update()
	{
		clearHallLogos();
		diningHandler.resetMap();
		buildOpen();
		buildClosed();
		Date date = new Date();
		SimpleDateFormat format = new SimpleDateFormat("h:mm a");
		TextView refreshStatus = (TextView) findViewById(R.id.lastUpdatedStatus);
		refreshStatus.setText("Current as of " + format.format(date));
	}

	private void createInformationList()
	{
		Intent i = new Intent(Dining_HallInformationActivity.this, Dining_InfoActivity.class);
		startActivity(i);
	}

	/**
	 * Makes all of te hall icon locatons blank to prevnt duplicates.
	 */
	public void clearHallLogos() {
		for (int i = 1; i < 13; i++) {
			for (int j = 0; j <= 1; j++) {
				ImageView image = getCurrentImageViewIteration(i, j);
				image.setVisibility(ImageView.GONE);
			}
		}
	}

	/**
	 * Generates all of the hall icons for "open"
	 */
	public void buildOpen()
	{
		int blockNum = 1;
		ArrayList<String> halls = diningHandler.getHallsForState("open");

		// determines whether or not to display "no halls are open" text
		if (halls.isEmpty())
			((TextView)findViewById(R.id.no_open_text)).setVisibility(TextView.VISIBLE);
		else
			((TextView)findViewById(R.id.no_open_text)).setVisibility(TextView.GONE);

		for (int i = 0; i < halls.size(); i++)
		{
			ImageView image = getCurrentImageViewIteration(blockNum, 1);
			setIcon(image, halls.get(i));
			blockNum++;
		}
	}

	/**
	 * Generates all of the hall icons for "closed"
	 */
	public void buildClosed()
	{
		int blockNum = 1;
		ArrayList<String> halls = diningHandler.getHallsForState("closed");

		// determines whether or not to display "no halls are closed" text
		if (halls.isEmpty())
			((TextView)findViewById(R.id.no_closed_text)).setVisibility(TextView.VISIBLE);
		else
			((TextView) findViewById(R.id.no_closed_text)).setVisibility(TextView.GONE);

		for (int i = 0; i < halls.size(); i++)
		{
			ImageView image = getCurrentImageViewIteration(blockNum, 0);
			setIcon(image, halls.get(i));
			blockNum++;
		}
	}

	/**
	 * Sets the icon and onClickListener of a given ImageView to
	 * whatever hall is given.
	 */
	public void setIcon(ImageView image, String hall)
	{
		if(hall.equals("d2"))
		{
			image.setImageResource(R.drawable.logo_d2);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 0;
					createInformationList();
				}
			});
		}
		if(hall.equals("d2_ex"))
		{
			image.setImageResource(R.drawable.logo_d2_ex);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 0;
					createInformationList();
				}
			});
		}
		if(hall.equals("deets"))
		{
			image.setImageResource(R.drawable.logo_deets);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 1;
					createInformationList();
				}
			});
		}
		if(hall.equals("deets_ex"))
		{
			image.setImageResource(R.drawable.logo_deets_ex);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 1;
					createInformationList();
				}
			});
		}
		if(hall.equals("dx"))
		{
			image.setImageResource(R.drawable.logo_dx);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 2;
					createInformationList();
				}
			});
		}
		if(hall.equals("dx_ex"))
		{
			image.setImageResource(R.drawable.logo_dx_ex);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 2;
					createInformationList();
				}
			});
		}
		if(hall.equals("hokie_grill"))
		{
			image.setImageResource(R.drawable.logo_hokie_grill);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 3;
					createInformationList();
				}
			});
		}
		if(hall.equals("hokie_grill_ex"))
		{
			image.setImageResource(R.drawable.logo_hokie_grill_ex);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 3;
					createInformationList();
				}
			});
		}
		if(hall.equals("owens"))
		{
			image.setImageResource(R.drawable.logo_owens);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 4;
					createInformationList();
				}
			});
		}
		if(hall.equals("owens_ex"))
		{
			image.setImageResource(R.drawable.logo_owens_ex);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 4;
					createInformationList();
				}
			});
		}
		if(hall.equals("west_end"))
		{
			image.setImageResource(R.drawable.logo_west_end);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 7;
					createInformationList();
				}
			});
		}
		if(hall.equals("west_end_ex"))
		{
			image.setImageResource(R.drawable.logo_west_end_ex);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 7;
					createInformationList();
				}
			});
		}
		if(hall.equals("abp_glc"))
		{
			image.setImageResource(R.drawable.logo_abp_glc);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 8;
					Toast.makeText(Dining_HallInformationActivity.this, "No information available...yet", Toast.LENGTH_SHORT).show();
				}
			});
		}
		if(hall.equals("abp_glc_ex"))
		{
			image.setImageResource(R.drawable.logo_abp_glc_ex);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 8;
					Toast.makeText(Dining_HallInformationActivity.this, "No information available...yet", Toast.LENGTH_SHORT).show();
				}
			});
		}
		if(hall.equals("abp_cafe"))
		{
			image.setImageResource(R.drawable.logo_abp_cafe);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 9;
					Toast.makeText(Dining_HallInformationActivity.this, "No information available...yet", Toast.LENGTH_SHORT).show();

				}
			});
		}
		if(hall.equals("abp_cafe_ex"))
		{
			image.setImageResource(R.drawable.logo_abp_cafe_ex);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 9;
					Toast.makeText(Dining_HallInformationActivity.this, "No information available...yet", Toast.LENGTH_SHORT).show();

				}
			});
		}
		if(hall.equals("abp_kiosk"))
		{
			image.setImageResource(R.drawable.logo_abp_kiosk);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 10;
					Toast.makeText(Dining_HallInformationActivity.this, "No information available...yet", Toast.LENGTH_SHORT).show();

				}
			});
		}
		if(hall.equals("abp_kiosk_ex"))
		{
			image.setImageResource(R.drawable.logo_abp_kiosk_ex);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 10;
					Toast.makeText(Dining_HallInformationActivity.this, "No information available...yet", Toast.LENGTH_SHORT).show();

				}
			});
		}
		if(hall.equals("sbarro"))
		{
			image.setImageResource(R.drawable.logo_sbarro);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 11;
					Toast.makeText(Dining_HallInformationActivity.this, "No information available...yet", Toast.LENGTH_SHORT).show();

				}
			});
		}
		if(hall.equals("sbarro_ex"))
		{
			image.setImageResource(R.drawable.logo_sbarro);
			image.setVisibility(0);
			image.setOnClickListener(new OnClickListener()
			{
				public void onClick(View v)
				{
					restaurantChosen = 11;
					Toast.makeText(Dining_HallInformationActivity.this, "No information available...yet", Toast.LENGTH_SHORT).show();

				}
			});
		}
	}

	/**
	 * Returns the ImageView which should be updated with hall logo
	 * and onClick reference.
	 *
	 * @param i The number of the block to return
	 * @param state 0 means hall is closed, 1 means hall is open
	 * @return
	 */
	public ImageView getCurrentImageViewIteration(int i, int state)
	{
		switch(i)
		{
		case 1:
			if (state == 1)
				return ((ImageView)findViewById(R.id.open_halls_block1));
			if (state == 0)
				return ((ImageView)findViewById(R.id.closed_halls_block1));
		case 2:
			if (state == 1)
				return ((ImageView)findViewById(R.id.open_halls_block2));
			if (state == 0)
				return ((ImageView)findViewById(R.id.closed_halls_block2));
		case 3:
			if (state == 1)
				return ((ImageView)findViewById(R.id.open_halls_block3));
			if (state == 0)
				return ((ImageView)findViewById(R.id.closed_halls_block3));
		case 4:
			if (state == 1)
				return ((ImageView)findViewById(R.id.open_halls_block4));
			if (state == 0)
				return ((ImageView)findViewById(R.id.closed_halls_block4));
		case 5:
			if (state == 1)
				return ((ImageView)findViewById(R.id.open_halls_block5));
			if (state == 0)
				return ((ImageView)findViewById(R.id.closed_halls_block5));
		case 6:
			if (state == 1)
				return ((ImageView)findViewById(R.id.open_halls_block6));
			if (state == 0)
				return ((ImageView)findViewById(R.id.closed_halls_block6));
		case 7:
			if (state == 1)
				return ((ImageView)findViewById(R.id.open_halls_block7));
			if (state == 0)
				return ((ImageView)findViewById(R.id.closed_halls_block7));
		case 8:
			if (state == 1)
				return ((ImageView)findViewById(R.id.open_halls_block8));
			if (state == 0)
				return ((ImageView)findViewById(R.id.closed_halls_block8));
		case 9:
			if (state == 1)
				return ((ImageView)findViewById(R.id.open_halls_block9));
			if (state == 0)
				return ((ImageView)findViewById(R.id.closed_halls_block9));
		case 10:
			if (state == 1)
				return ((ImageView)findViewById(R.id.open_halls_block10));
			if (state == 0)
				return ((ImageView)findViewById(R.id.closed_halls_block10));
		case 11:
			if (state == 1)
				return ((ImageView)findViewById(R.id.open_halls_block11));
			if (state == 0)
				return ((ImageView)findViewById(R.id.closed_halls_block11));
		case 12:
			if (state == 1)
				return ((ImageView)findViewById(R.id.open_halls_block12));
			if (state == 0)
				return ((ImageView)findViewById(R.id.closed_halls_block12));
		}
		return null;
	}
}
