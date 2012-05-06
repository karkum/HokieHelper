package org.mad.app.hokiehelper;

import android.graphics.drawable.Drawable;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.OverlayItem;

/**
 * The BuildingObject class does exactly what the name implies.  LOL
 * 
 * @author Juilan Adams 
 * @author Virginia Tech Mobile Application Development Team
 * @version 2.1.12
 */
public class Maps_BuildingObject extends OverlayItem
{
    private GeoPoint point;
    private Drawable marker;
    private String name;
    private String info;
    private boolean isCurrLocation;
    
    public Maps_BuildingObject( GeoPoint point, String title, String snippet, Drawable marker )
    {
        super( point, title, snippet );
        this.marker = marker;
        this.name = title;
        this.info = snippet;
        this.point = point;
        this.isCurrLocation = false;
    }

    public Maps_BuildingObject( boolean isCurrLocation )
    {
        super( null, "Current Location", null );
        this.marker = null;
        this.name = "Current Location";
        this.info = null;
        this.point = null;
        this.isCurrLocation = true;
    }
    
    public Maps_BuildingObject( GeoPoint point, String title, String snippet, 
        Drawable marker, boolean isCurrLocation )
    {
        super( point, title, snippet );
        this.marker = marker;
        this.name = title;
        this.info = snippet;
        this.point = point;
        this.isCurrLocation = isCurrLocation;
    }
    
    public boolean isCurrLocation(){
        return isCurrLocation;
    }
    
    public GeoPoint getGeoPoint()
    {
        return point;
    }

    @Override
    public String getTitle()
    {
        return name;
    }

    @Override
    public String getSnippet()
    {
        return info;
    }
    
    public Drawable getMarker()
    {
        return marker;
    }

       
}
