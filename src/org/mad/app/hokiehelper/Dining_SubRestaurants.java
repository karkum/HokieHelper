package org.mad.app.hokiehelper;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class Dining_SubRestaurants extends ListActivity
{

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.subrestaurant);

		String restaurant = getIntent().getExtras().getString("tableName");
		final String[] subCategories = getResources()
			.getStringArray(getSubRestaurantArrayID(restaurant));
		ArrayAdapter<String> stringAdapter = new ArrayAdapter<String>
			(this, R.layout.real_list_item, subCategories);

		setListAdapter(stringAdapter);
		ListView lv = getListView();

		lv.setTextFilterEnabled(true);
		
        View parent = (View) lv.getParent();
        parent.setBackgroundResource(R.drawable.bg_tan);
		
		lv.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				createNextActivity(parent, position, subCategories);
			}
		});
		lv.setCacheColorHint(0);
	}

	private void createNextActivity(AdapterView<?> parent, int position, String[] category)
	{
	    String subRestaurant = category[position];
		Intent i = new Intent(Dining_SubRestaurants.this, Dining_DatabaseActivity.class);
		i.putExtra("subRestaurant", subRestaurant);
		startActivity(i);
	}

	private int getSubRestaurantArrayID(String subRestaurant) {
		int id;

		if (subRestaurant.equals("deets"))
			id = R.array.deetCategory;
		else if (subRestaurant.equals("hokieGrill"))
			id = R.array.hokieGrillCategory;
		else if (subRestaurant.equals("owens"))
			id = R.array.owensCategory;
		else if (subRestaurant.equals("westend"))
			id = R.array.westendCategory;
		else if (subRestaurant.equals("d2"))
			id = R.array.d2Category;
		else if (subRestaurant.equals("dx"))
			id = R.array.d2Category;
		else
			id = -1;
		return id;
	}
}
