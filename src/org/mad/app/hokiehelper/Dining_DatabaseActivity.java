package org.mad.app.hokiehelper;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.ListActivity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import java.util.ArrayList;

public class Dining_DatabaseActivity extends ListActivity
{
	ArrayList<String> foodList = new ArrayList<String>();
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subrestaurant);
       
        final ArrayList<String> nutritionList = new ArrayList<String>();
        Dining_DBAdapter db = new Dining_DBAdapter(this, getResources().getStringArray(R.array.dbFolders), this.getResources());
        db.open();
        Bundle extras = getIntent().getExtras();
        String subRestaurant = "";
        if (extras != null)
        	subRestaurant = extras.getString("subRestaurant");
        Cursor c = db.getAllTitles(Dining_InfoActivity.tableName);
        if (c.moveToFirst())
        {
            do
            {
                if (c.getString(26).equals(subRestaurant))
                {
                    String food = c.getString(1);
                    foodList.add(food);
                    StringBuilder str = new StringBuilder();
                    for (int i = 2; i < 26; i++)
                    {
                        str.append(c.getString(i).replaceAll("&nbsp;", "-") + "|");
                    }
                    nutritionList.add(str.toString());
                }
            }
            while (c.moveToNext());
        }
        db.close();

        setListAdapter(new ArrayAdapter<String>(this, R.layout.real_list_item, foodList));
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        View parent = (View) lv.getParent();
        parent.setBackgroundResource(R.drawable.bg_tan);
        
		lv.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				createNextActivity(nutritionList.get(position), position);
			}
		});
        lv.setCacheColorHint(0);
    }
    private void createNextActivity(String nutriInfo, int pos)
    {
		Intent i;
		i = new Intent(Dining_DatabaseActivity.this, Dining_NutritionInfoActivity.class);
		i.putExtra("org.mad.app.dining.nutriInfo", nutriInfo);
		i.putExtra("foodName", foodList.get(pos));
		startActivity(i);
    }
}
