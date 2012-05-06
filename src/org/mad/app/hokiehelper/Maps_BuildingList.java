package org.mad.app.hokiehelper;

import java.util.ArrayList;
import com.google.android.maps.GeoPoint;

/**
 * The BuildingList class parses the Virginia Tech Blacksburg campus buildings 
 * xml file and creates an array of building objects.  
 * 
 * @author Juilan Adams 
 * @author Virginia Tech Mobile Application Development Team
 * @version 2.1.12
 */
public class Maps_BuildingList 
{

    private ArrayList<Maps_BuildingObject> list;
    private String[] rawArray;

    public Maps_BuildingList(String[] rawArray)
    {
        this.rawArray = rawArray;
        list = new ArrayList<Maps_BuildingObject>();
        readInBuildings();
    }

    public Maps_BuildingList(String[] rawArray, Maps_BuildingObject currLocation)
    {
        this.rawArray = rawArray;
        list = new ArrayList<Maps_BuildingObject>();
        list.add( currLocation );
        readInBuildings();
    }

    public void readInBuildings()
    {
        for ( int i = 0; i < rawArray.length; i = i + 4 )
        {
            String name = rawArray[i];
            double lat = Double.parseDouble( rawArray[i + 1] );
            double lng = Double.parseDouble( rawArray[i + 2] );
            String info = rawArray[i + 3];
            Maps_BuildingObject temp = new Maps_BuildingObject( new GeoPoint( (int)( lat * 1E6 ),
                (int)( lng * 1E6 ) ),
                name,
                info,
                null );
            list.add( temp );
        }
    }

    public ArrayList<Maps_BuildingObject> getList()
    {
        return list;
    }

}
