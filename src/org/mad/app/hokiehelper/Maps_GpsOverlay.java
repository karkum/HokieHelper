package org.mad.app.hokiehelper;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.drawable.Drawable;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.OverlayItem;

/**
* The gpsOverlay class is the overlay of the users GPS location on to be 
* displayed on the map directions from google maps.
* @author Juilan Adams 
* @author Virginia Tech Mobile Application Development Team
* @version 2.1.12
*/    
public class Maps_GpsOverlay extends ItemizedOverlay<OverlayItem>
{
    @SuppressWarnings("unused")
	private Context context;
    @SuppressWarnings("unused")
	private Drawable marker;
    private ArrayList<OverlayItem> gpsOverlays = new ArrayList<OverlayItem>();
    
    public Maps_GpsOverlay( Drawable marker, Context context )
    {
        super( boundCenterBottom (marker) );
        this.context = context;
        this.marker = marker;
    }
    
    public void addOverlay(OverlayItem overlay) {
        gpsOverlays.add(overlay);
        populate();
    }
    
    @Override
    protected OverlayItem createItem( int i )
    {
        return gpsOverlays.get(i);
    }

    @Override
    public int size()
    {
        return gpsOverlays.size();
    }

}
