package org.mad.app.hokiehelper;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.actionbarsherlock.app.SherlockListActivity;
import com.actionbarsherlock.view.MenuItem;

/**
 * Calendar Activity. Shows relevant dates. Also, allows user to add date
 * to their calendar. 
 * @author karthik
 *
 */

public class Info_CalendarActivity extends SherlockListActivity {
	final ArrayList<Info_Event> springData = new ArrayList<Info_Event>();
	final ArrayList<Info_Event> summer1Data = new ArrayList<Info_Event>();
	final ArrayList<Info_Event> summer2Data = new ArrayList<Info_Event>();
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.calendar_main);
		
		// Sets up action bar title/navigation
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		getSupportActionBar().setDisplayUseLogoEnabled(true);
		getSupportActionBar().setLogo(R.drawable.ic_launcher);
		getSupportActionBar().setDisplayShowHomeEnabled(true);
		getSupportActionBar().setTitle("Academic Calendar");
		
		final ArrayList<String> springList = new ArrayList<String>();

		final ArrayList<String> fallList = new ArrayList<String>();
		final ArrayList<String> summer1List = new ArrayList<String>();
		final ArrayList<String> summer2List = new ArrayList<String>();

		populateList("spring.txt", springList);
		populateList("fall.txt", fallList);
		populateList("summer1.txt", summer1List);
		populateList("summer2.txt", summer2List);

		populateData("spring_data", springData);
		populateData("summer1_data", summer1Data);
		populateData("summer2_data", summer2Data);

		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		final int screenWidth = displaymetrics.widthPixels;

		setList(springList, "Spring 2012");

		Button spring = (Button) findViewById(R.id.spring);
		Button fall = (Button) findViewById(R.id.fall);
		Button summer1 = (Button) findViewById(R.id.summer1);
		Button summer2 = (Button) findViewById(R.id.summer2);

		spring.setWidth(screenWidth / 4);
		fall.setWidth(screenWidth / 4);
		summer1.setWidth(screenWidth / 4);
		summer2.setWidth(screenWidth / 4);

		spring.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setList(springList, "Spring 2012");
			}
		});

		fall.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setList(fallList, "Fall 2011");
			}
		});

		summer1.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setList(summer1List, "Summer I 2012");
			}
		});

		summer2.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				setList(summer2List, "Summer II 2012");
			}
		});
	}

	private void populateData(String filename, ArrayList<Info_Event> springData) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this
					.getAssets().open(filename)));

			String line;
			while ((line = br.readLine()) != null) {
				Scanner scn = new Scanner(line);
				int m = scn.nextInt();
				int d = scn.nextInt();
				String t = scn.nextLine();
				t = t.trim();
				springData.add(new Info_Event(t, m, d));

			}
			// list.add(line);

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void populateList(String filename, ArrayList<String> list) {
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(this
					.getAssets().open(filename)));

			String line;
			while ((line = br.readLine()) != null)
				list.add(line);

			br.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void setList(ArrayList<String> list, final String semester) {
		setListAdapter(new ArrayAdapter<String>(this, R.layout.real_list_item,
				list));
		ListView lv = getListView();
		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View v,
					int position, long id) {
				if (semester.equals("Spring 2012"))
					showDialog(springData.get(position), "Add Event");
				else if (semester.equals("Fall 2011"))
					showDialog(null, null);
				else if (semester.equals("Summer I 2012"))
					showDialog(summer1Data.get(position), "Add Event");
				else if (semester.equals("Summer II 2012"))
					showDialog(summer2Data.get(position), "Add Event");
			}
		});
		lv.setTextFilterEnabled(true);

		View parent = (View) lv.getParent();
		parent.setBackgroundResource(R.drawable.bg_tan);
		lv.setCacheColorHint(0);

		TextView tv = (TextView) findViewById(R.id.current);
		tv.setText(semester);
	}

	private void showDialog(final Info_Event event, String title) {
		final AlertDialog.Builder dlgAlert = new AlertDialog.Builder(
				Info_CalendarActivity.this);
		try {
		if (event != null) {
			long now = Calendar.getInstance().getTimeInMillis();
			long eventTime = new GregorianCalendar(2012, event.getMonth() - 1,
					event.getDay()).getTimeInMillis();
			if (now < eventTime) {
				dlgAlert.setPositiveButton("Yes",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {

								Intent intent = new Intent();

								intent.setType("vnd.android.cursor.item/event");
								intent.putExtra("title", event.getTitle());
								GregorianCalendar start = new GregorianCalendar(
										2012, event.getMonth() - 1, event.getDay(),
										10, 00);
								GregorianCalendar end = new GregorianCalendar(
										2012, event.getMonth()- 1, event.getDay(),
										12, 00);

								intent.putExtra("beginTime",
										start.getTimeInMillis());
								intent.putExtra("endTime",
										end.getTimeInMillis());

								intent.setAction(Intent.ACTION_EDIT);
								try {
								startActivity(intent);
								} catch (Exception e) {
									dlgAlert.setPositiveButton("OK",
											new DialogInterface.OnClickListener() {
												public void onClick(DialogInterface dialog,
														int which) {
													// Do nothing
												}
											});

									dlgAlert.setMessage("Sorry, an error has occured");
									dlgAlert.setTitle("Oops!");
									dlgAlert.setCancelable(true);
									dlgAlert.create().show();
									return;
								}
							}
						});
				dlgAlert.setNegativeButton("Cancel",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Do nothing
							}
						});

				dlgAlert.setMessage("Do you want to add this event to your calendar?");
				dlgAlert.setTitle(title);
				dlgAlert.setCancelable(true);
				dlgAlert.create().show();
			} else {
				dlgAlert.setNegativeButton("OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								// Do nothing
							}
						});

				dlgAlert.setMessage("Sorry, this event has already passed");
				dlgAlert.setTitle("Oops!");
				dlgAlert.setCancelable(true);
				dlgAlert.create().show();
			}
		} else {
			dlgAlert.setNegativeButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							// Do nothing
						}
					});

			dlgAlert.setMessage("Sorry, this event has already passed");
			dlgAlert.setTitle("Oops!");
			dlgAlert.setCancelable(true);
			dlgAlert.create().show();
		}
		} catch (Exception e) {
			dlgAlert.setNegativeButton("OK",
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialog,
								int which) {
							// Do nothing
						}
					});

			dlgAlert.setMessage("Sorry, an error has occured");
			dlgAlert.setTitle("Oops!");
			dlgAlert.setCancelable(true);
			dlgAlert.create().show();
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