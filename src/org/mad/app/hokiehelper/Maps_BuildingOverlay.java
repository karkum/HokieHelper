package org.mad.app.hokiehelper;

import java.util.ArrayList;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;

/**
 * The BuildingOverlay class is the overlay used to display building icons on
 * the map. 
 * 
 * @author Juilan Adams 
 * @author Virginia Tech Mobile Application Development Team
 * @version 2.1.12
 */
public class Maps_BuildingOverlay extends ItemizedOverlay<Maps_BuildingObject>
{
    private ArrayList<Maps_BuildingObject> items;
    private Drawable marker = null;
    private Context context;
    private boolean isLocationOn;
    
    
    public Maps_BuildingOverlay( Drawable marker, Context context, String[] rawArray )
    {
        super( marker );
        this.marker = marker;
        this.context = context;
        isLocationOn = false;       
        items = new ArrayList<Maps_BuildingObject>();
    }
    
    public void setIsLocationOn(boolean isLocationOn){
        this.isLocationOn = isLocationOn;
    }
    
    public void addOverlayItem(Maps_BuildingObject building)
    {
        items.add( building );
        populate();
    }
    
    
    @Override
    protected Maps_BuildingObject createItem( int i )
    {
        return items.get( i );
    }

    @Override
    protected boolean onTap(int index) {
      


          final Maps_BuildingObject building = items.get(index);
          AlertDialog.Builder builder;
          AlertDialog alertDialog;
          LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
          View v = new View(context);
          View layout = inflater.inflate(R.layout.custom_dialog,
                       (ViewGroup) v.findViewById(R.id.layout_dialog));
            
         ImageView image = (ImageView) layout.findViewById(R.id.image);
         image.setScaleType(ImageView.ScaleType.FIT_CENTER);
         setImage(building, image);
         builder = new AlertDialog.Builder(context);
         builder.setTitle( building.getTitle() );
         builder.setIcon( R.drawable.university );
         builder.setView(layout);
         if(isLocationOn){
         builder.setPositiveButton("Get Directions", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int id) {
              Intent i = null;
              Bundle bundle = new Bundle();
              i = new Intent(context, Maps_MainActivity.class);
              bundle.putString("toBuilding" , building.getTitle());
              bundle.putInt("toLatitude", building.getGeoPoint().getLatitudeE6());
              bundle.putInt("toLongitude", building.getGeoPoint().getLongitudeE6());
              bundle.putString("toSnippet", building.getSnippet());
              bundle.putBoolean( "isRoute", true );
              i.putExtras( bundle );
              bundle.putBoolean( "isRoute", true );
              bundle.putBoolean( "isCurrLocation", true );
              i.putExtras( bundle );
              context.startActivity(i); 
          }
         });
          }
         builder.setNegativeButton("Back", new DialogInterface.OnClickListener() {
         @Override
         public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
        }
        });
        
      alertDialog = builder.create();
      alertDialog.show();
      return true;
    
}
    @Override
    public int size()
    {
        return items.size();
    }
    
    @Override
    public void draw(Canvas canvas, MapView mapView, boolean shadow)
    {
        super.draw( canvas, mapView, shadow );
        boundCenterBottom(marker);
    }
    
    private void setImage(Maps_BuildingObject building, ImageView image){
        String base = building.getTitle();
        base = base.toLowerCase();
        base = base.replaceAll( " ", "_" );
        int resource = context.getResources().getIdentifier( base, 
            "drawable", context.getPackageName() );
        if(resource != 0){
            image.setImageResource( resource );
        }
        else{
            image.setImageResource( R.drawable.university );
        }
    }
}
