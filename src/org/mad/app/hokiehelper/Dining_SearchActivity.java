package org.mad.app.hokiehelper;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import java.util.ArrayList;

public class Dining_SearchActivity extends ListActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.subrestaurant);
        ArrayList<String> foodList = new ArrayList<String>();
        final ArrayList<String> nutritionList = new ArrayList<String>();
		Intent intent = getIntent();
		String query = "";
        if (Intent.ACTION_SEARCH.equals(intent.getAction()))
        {
            query = intent.getStringExtra(SearchManager.QUERY).trim().toLowerCase();
		}
		
		foodList.add("Search results for \"" + query + "\":");
		
        Dining_DBAdapter db = new Dining_DBAdapter(this, getResources().getStringArray(R.array.dbFolders), this.getResources());
        db.open();
        Cursor c = db.getAllTitles(Dining_InfoActivity.tableName);
        if (c.moveToFirst())
        {
            do
            {
				String food = c.getString(1);
                if (food.toLowerCase().indexOf(query) != -1 && !foodList.contains(food))
                {
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

		if (foodList.size() == 1)
			foodList.add("It appears that there are no food items named \"" + query
        			+ "\" in this dining hall.");
		
        setListAdapter(new ArrayAdapter<String>(this, R.layout.real_list_item, foodList));
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        
        View parent = (View) lv.getParent();
        parent.setBackgroundResource(R.drawable.bg_tan);
        
		lv.setOnItemClickListener(new OnItemClickListener()
		{
			public void onItemClick(AdapterView<?> parent, View v, int position, long id)
			{
				createNextActivity(nutritionList.get(position));
			}
		});
        lv.setCacheColorHint(0);
    }
    private void createNextActivity(String nutriInfo)
    {
		Intent i;
		i = new Intent(Dining_SearchActivity.this, Dining_NutritionInfoActivity.class);
		i.putExtra("org.mad.app.dining.nutriInfo", nutriInfo);
		startActivity(i);
    }
}