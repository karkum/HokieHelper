package org.mad.app.hokiehelper;

import java.util.ArrayList;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
/**
* The GetDDirectionsActivity class handles and displays the the UI for choosing
* the starting and ending locations to map.
* @author Juilan Adams 
* @author Virginia Tech Mobile Application Development Team
* @version 2.1.12
*/    
public class Maps_GetDirectionsActivity extends Activity implements OnItemSelectedListener
{
    private String[] fromMenuTitles;
    private String[] toMenuTitles;

    private Spinner from;
    private Spinner to;
    private Maps_BuildingList list;
    private ArrayList<Maps_BuildingObject> objectList;
    private Maps_BuildingObject fromBuilding;
    private Maps_BuildingObject toBuilding;
    private LocationManager locationManager;
    private boolean isProviderAvailable;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.getdirections_layout);

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

       
        if (locationManager.getLastKnownLocation( LocationManager.NETWORK_PROVIDER ) != null || 
                        locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER ) != null){
            isProviderAvailable = true;
            Maps_BuildingObject tempCurrLocation = new Maps_BuildingObject(true);
            list = new Maps_BuildingList(getResources().getStringArray( R.array.directory_array ), tempCurrLocation);
        }
        else
        {
            isProviderAvailable = false;
            list = new Maps_BuildingList(getResources().getStringArray( R.array.directory_array ));
        }        
        objectList = list.getList();
        fromMenuTitles = new String[objectList.size()];
        toMenuTitles = new String[objectList.size() - 1];
        
        for (int i = 0; i < objectList.size(); i++)
        {
            fromMenuTitles[i] = objectList.get( i ).getTitle();            
            if (i != 0){
                toMenuTitles[i - 1] = objectList.get( i ).getTitle();
            }
        }
              
        Button routeMe = (Button)findViewById(R.id.routeme_button);
        routeMe.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                getStartStopPositions();
            }
        });
        
        ArrayAdapter<String> spinner_from = new ArrayAdapter<String>(this, R.layout.spinner_row, R.id.building_names, fromMenuTitles);
        ArrayAdapter<String> spinner_to = new ArrayAdapter<String>(this, R.layout.spinner_row, R.id.building_names, toMenuTitles);
        from = (Spinner)findViewById(R.id.spinner_from);
        from.setOnItemSelectedListener( this );
        from.setAdapter( spinner_from );
               
        to = (Spinner)findViewById(R.id.spinner_to);
        to.setOnItemSelectedListener( this );
        to.setAdapter( spinner_to );
    }
    
    @Override
    public void onItemSelected(
        AdapterView<?> parent,
        View view,
        int position,
        long id )
    {
        
        switch (parent.getId()) {
            case R.id.spinner_from:
                fromBuilding = objectList.get( position );
                
                break;
            case R.id.spinner_to:
                if (isProviderAvailable){
                    toBuilding = objectList.get( (position + 1) );
                }
                else{
                    toBuilding = objectList.get( ( position + 1) );
                }
                break;
        }
    }

    @Override
    public void onNothingSelected( AdapterView<?> parent )
    {
        // TODO Auto-generated method stub   
    }
    
    public void getStartStopPositions()
    {
        Intent i = null;
        Bundle bundle = new Bundle();

        i = new Intent(this, Maps_MainActivity.class);
        if (fromBuilding.isCurrLocation()){
            bundle.putBoolean( "isCurrLocation", true );  
        }
        else{

            bundle.putBoolean( "isCurrLocation", false );
            bundle.putString("fromBuilding" , fromBuilding.getTitle());
            bundle.putInt("fromLatitude", fromBuilding.getGeoPoint().getLatitudeE6());
            bundle.putInt("fromLongitude", fromBuilding.getGeoPoint().getLongitudeE6());
            bundle.putString("fromSnippet", fromBuilding.getSnippet());
        }


        bundle.putString("toBuilding" , toBuilding.getTitle());
        bundle.putInt("toLatitude", toBuilding.getGeoPoint().getLatitudeE6());
        bundle.putInt("toLongitude", toBuilding.getGeoPoint().getLongitudeE6());
        bundle.putString("toSnippet", toBuilding.getSnippet());
        bundle.putBoolean( "isRoute", true );
        i.putExtras( bundle );
        this.startActivity(i); 
        
    }
    
}
