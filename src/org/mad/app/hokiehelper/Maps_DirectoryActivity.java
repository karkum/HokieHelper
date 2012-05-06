package org.mad.app.hokiehelper;

import java.util.ArrayList;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;


/**
 * The DirectoryActivity class is a List Activity that displays a list 
 * of the campus buildings.  
 * 
 * @author Juilan Adams 
 * @author Virginia Tech Mobile Application Development Team
 * @version 2.1.12
 */
public class Maps_DirectoryActivity extends ListActivity
{

    private String[] menuTitles;
    private Maps_BuildingList list;
    private ArrayList<Maps_BuildingObject> objectList;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);


        list = new Maps_BuildingList(getResources().getStringArray( R.array.directory_array ));
        objectList = list.getList();
        menuTitles = new String[objectList.size()];
        for (int i = 0; i < objectList.size(); i++)
        {
            menuTitles[i] = objectList.get( i ).getTitle();
        }


        setListAdapter(new ArrayAdapter<String>(this, R.layout.menu_item, menuTitles));
        ListView lv = getListView();
        lv.setTextFilterEnabled(true);
        lv.setCacheColorHint(0);
        lv.setBackgroundResource(R.drawable.maps_background);
        lv.setOnItemClickListener(new OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id)
            {
                TextView tv = (TextView) v;
                String building = (String)tv.getText();
                createNextActivity(v, building);
            }
        });
    }

    private void createNextActivity(View v, String building)
    {
        Intent i = null;
        Bundle bundle = new Bundle();
        int position = 0;
        for (Maps_BuildingObject build : objectList)
        {
            if (build.getTitle().equals( building ))
            {
                position = objectList.indexOf( build );
            }
        }

        i = new Intent(v.getContext(), Maps_MainActivity.class);
        bundle.putString("BuildingName" , objectList.get( position ).getTitle());
        bundle.putInt("Latitude", objectList.get( position ).getGeoPoint().getLatitudeE6());
        bundle.putInt("Longitude", objectList.get( position ).getGeoPoint().getLongitudeE6());
        bundle.putString("Snippet", objectList.get( position ).getSnippet());
        bundle.putBoolean( "isRoute", false );
        bundle.putBoolean("isCurrLocation", false);
        i.putExtras( bundle );
        startActivity(i); 
    }
}
