package org.mad.app.hokiehelper;

import java.util.List;

import org.mad.app.hokiehelper.Maps_DrivingDirections.IDirectionsListener;
import org.mad.app.hokiehelper.Maps_DrivingDirections.Mode;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;

/**
 * The MapsActivity class handles and displays a custom map interface designed
 * specifically for the Virginia Tech Blacksburg campus
 * 
 * @author Juilan Adams 
 * @author Virginia Tech Mobile Application Development Team
 * @version 2.1.12
 */
public class Maps_MainActivity extends MapActivity
                implements
                IDirectionsListener
{
    private MapController mapController;
    private MapView mapView;
    private LocationManager locationManager;
    private GeoUpdateHandler locationListener;
    private Maps_BuildingOverlay overlay;
    private MyLocationOverlay myLocation;
    private Drawable marker;
    private Bundle bundle;
    private Maps_RouteOverlay routeOverlay;
    private String[] menuTitles;
    private String[] directionsArray;
    @SuppressWarnings("unused")
	private SlidingDrawer slidingDrawer;
    private SlidingDrawer slidingDirections;
    private static final int centerLat = (int)( 37.2277 * 1E6 );
    private static final int centerLng = (int)( -80.422037 * 1E6 );
    private static GeoPoint mapCenter;
    private static ImageView directionsHandleImage;
    private static LinearLayout directionsDrawer;  
    
    @Override
    public void onPause(){
        myLocation.disableMyLocation();
        locationManager.removeUpdates( locationListener );
        myLocation.disableCompass();
        super.onPause();
    }
    @Override
    public void onResume(){
        super.onResume();
        myLocation.enableMyLocation();
        myLocation.enableCompass();
    }
    
    @Override
    public void onCreate( Bundle icicle )
    {
        super.onCreate( icicle );
        bundle = this.getIntent().getExtras();
        // create tab for directions if isRoute is true
        if(bundle != null && bundle.getBoolean( "isRoute" )){
             setContentView( R.layout.new_map_layout_directions ); // bind the layout to the
             slidingDirections = (SlidingDrawer)this.findViewById( R.id.top_drawer );
             directionsHandleImage = (ImageView) findViewById(R.id.handle);
             directionsDrawer = (LinearLayout)findViewById(R.id.content);
             directionsDrawer.getBackground().setAlpha( 190 );
             directionsHandleImage.setAlpha(190); 
         
             slidingDirections.setOnDrawerOpenListener(new OnDrawerOpenListener() {
                 @Override
                 public void onDrawerOpened() {     
                     // some value 0-255 where 0 is fully transparent and 255 is fully opaque
                     directionsHandleImage.setImageResource(R.drawable.tab_right_handle);
                 }
                 });
              
             slidingDirections.setOnDrawerCloseListener(new OnDrawerCloseListener() {
                 @Override
                 public void onDrawerClosed() {    
                     directionsHandleImage.setAlpha(190);   
                     directionsHandleImage.setImageResource(R.drawable.tab_left_handle);
                 }
                 });    
        }
        else{
            setContentView( R.layout.new_main_layout ); // bind the layout to the
        }
  
        marker = getResources().getDrawable( R.drawable.university );
        slidingDrawer = (SlidingDrawer)this.findViewById( R.id.bottom_drawer );
        menuTitles = getResources().getStringArray( R.array.menu_options );      
        ListView lv = (ListView)findViewById( R.id.bottom_slider_listview );
        lv.setAdapter( new ArrayAdapter<String>( this,
            R.layout.menu_item,
            menuTitles ) );
        lv.setTextFilterEnabled( true );
        lv.setCacheColorHint( 0 );
        lv.setOnItemClickListener( new OnItemClickListener()
        {
            @Override
            public void onItemClick(
                AdapterView<?> parent,
                View v,
                int position,
                long id )
            {
                createNextActivity( position );
            }
        } );

        // create a map view
        mapView = (MapView)findViewById( R.id.mapView );
        mapView.setSatellite( true );
        mapController = mapView.getController();
        mapController.setZoom( 17 ); // Zoom 1 is world view   
        mapCenter = new GeoPoint( centerLat, centerLng );
        mapController.setCenter( mapCenter );
        locationListener = new GeoUpdateHandler();             
        locationManager = (LocationManager)getSystemService( Context.LOCATION_SERVICE );
        locationManager.requestLocationUpdates( LocationManager.GPS_PROVIDER,
            10000,
            5,
            locationListener );
        myLocation = new MyLocationOverlay( this, mapView );
        myLocation.enableMyLocation();
        myLocation.enableCompass();
                
        overlay = new Maps_BuildingOverlay( marker,
            this, getResources().getStringArray( R.array.directory_array ) );
        routeOverlay = new Maps_RouteOverlay( getResources().getDrawable
            ( R.drawable.walking_icon ), this );
        // add my location to the map
        mapView.getOverlays().add( myLocation );
        mapView.postInvalidate();
        // display one building on the map
        if ( bundle != null && !( bundle.getBoolean( "isRoute" ) ) )
        {
            if (locationManager.getLastKnownLocation( LocationManager.NETWORK_PROVIDER ) != null || 
                            locationManager.getLastKnownLocation( LocationManager.GPS_PROVIDER ) != null){
                overlay.setIsLocationOn( true );
            }
            overlay.addOverlayItem( compileBuilding( bundle ) );
            mapView.getOverlays().add( overlay );
            mapController.animateTo( compileBuilding( bundle ).getGeoPoint() );
            mapView.invalidate();
        }

        GeoPoint startPoint;
        GeoPoint endPoint;
        // if the get directions activity has requested directions
        if ( bundle != null && bundle.getBoolean( "isRoute" ) )
        {
            if ( bundle.getBoolean( "isCurrLocation" ) )
            {
                List<String> providers = locationManager.getProviders( true );
                Location l = null;
                for ( int i = providers.size() - 1; i >= 0; i-- ){
                    l = locationManager.getLastKnownLocation( providers.get( i ) );
                    if ( l != null ){
                        break;
                    }
                }
                startPoint = new GeoPoint( (int)( l.getLatitude() * 1E6 ),
                    (int)( l.getLongitude() * 1E6 ) );
            }
            else{
                Maps_BuildingObject temp_from = compileFromBuilding( bundle );
                startPoint = temp_from.getGeoPoint();
            }
            Maps_BuildingObject temp_to = compileToBuilding( bundle );
            endPoint = temp_to.getGeoPoint();
            mapController.animateTo( startPoint );
            mapView.invalidate();
            handleDirections( startPoint, endPoint, true );
        }
    }

    private void createNextActivity( int position )
    {
        Intent i = null;
        switch ( position )
        {
            case 0:
                i = new Intent( this, Maps_DirectoryActivity.class );
                startActivity( i );
                break;
            case 1:
                i = new Intent( this, Maps_GetDirectionsActivity.class );
                startActivity( i );
                break;
        }
    }

    public float getDistanceBetween( GeoPoint p1, GeoPoint p2 )
    {
        double lat1 = ( (double)p1.getLatitudeE6() ) / 1e6;
        double lng1 = ( (double)p1.getLongitudeE6() ) / 1e6;
        double lat2 = ( (double)p2.getLatitudeE6() ) / 1e6;
        double lng2 = ( (double)p2.getLongitudeE6() ) / 1e6;
        float[] dist = new float[1];
        Location.distanceBetween( lat1, lng1, lat2, lng2, dist );
        return dist[0];
    }

    /**
     * 
     * @param bundle
     * @return
     */
    public Maps_BuildingObject compileBuilding( Bundle bundle )
    {

        String title = bundle.getString( "BuildingName" );
        int lat = bundle.getInt( "Latitude" );
        int lon = bundle.getInt( "Longitude" );
        String snippet = bundle.getString( "Snippet" );
        GeoPoint point = new GeoPoint( lat, lon );
        Maps_BuildingObject temp = new Maps_BuildingObject( point, title, snippet, marker );
        return temp;
    }


    /**
     * 
     * @param bundle
     * @return
     */
    public Maps_BuildingObject compileFromBuilding( Bundle bundle )
    {

        String title = bundle.getString( "fromBuilding" );
        int lat = bundle.getInt( "fromLatitude" );
        int lon = bundle.getInt( "fromLongitude" );
        String snippet = bundle.getString( "fromSnippet" );
        GeoPoint point = new GeoPoint( lat, lon );
        Maps_BuildingObject temp = new Maps_BuildingObject( point, title, snippet, marker );
        return temp;
    }


    /**
     * 
     * @param bundle
     * @return
     */
    public Maps_BuildingObject compileToBuilding( Bundle bundle )
    {
        String title = bundle.getString( "toBuilding" );
        int lat = bundle.getInt( "toLatitude" );
        int lon = bundle.getInt( "toLongitude" );
        String snippet = bundle.getString( "toSnippet" );
        GeoPoint point = new GeoPoint( lat, lon );
        Maps_BuildingObject temp = new Maps_BuildingObject( point, title, snippet, marker );
        return temp;
    }

    protected void set_directions_listview(){
        ListView lv = (ListView)findViewById( R.id.top_slider_listview );
        lv.setAdapter( new ArrayAdapter<String>( this,
            R.layout.menu_item,
            menuTitles ) );
        lv.setTextFilterEnabled( true );
        lv.setCacheColorHint( 0 );
        lv.setOnItemClickListener( new OnItemClickListener()
        {
            @Override
            public void onItemClick(
                AdapterView<?> parent,
                View v,
                int position,
                long id )
            {
                createNextActivity( position );
            }
        } );
    }
        
    @Override
    protected boolean isRouteDisplayed()
    {
        return false;
    }

    public void handleDirections(
        GeoPoint startPoint,
        GeoPoint endPoint,
        boolean isRoute )
    {
        if ( isRoute ) // if get directions button is called from UI
        {
            Maps_DrivingDirections.Mode mode = Mode.WALKING;
            Maps_DrivingDirections directions = Maps_DrivingDirectionsFactory.
                                                    createDrivingDirections();
            directions.setLocationPoints( startPoint, endPoint, mode, this );
            Toast.makeText( Maps_MainActivity.this,
                "Calculating Shortest Path",
                Toast.LENGTH_SHORT ).show();
            directions.driveTo( startPoint, endPoint, mode, this );
        }
    }


    @Override
    public void onDirectionsAvailable( Maps_Route route, Mode mode )
    {
        routeOverlay.addRoute( route, mode );
        mapView.getOverlays().add( routeOverlay );
        
        Toast.makeText( Maps_MainActivity.this,
            route.getTotalDistance(),
            Toast.LENGTH_LONG).show();
        directionsArray = new String[route.getPlacemarks().size()];
        int listSize = route.getPlacemarks().size();
        for(int i = 0; i < listSize; i++){
           if(i <= listSize - 2){
            int pointDistance = (int) getDistanceBetween(route.getPlacemarks().get( i ).getLocation(),
                route.getPlacemarks().get( i + 1 ).getLocation());
            directionsArray[i] = (i + 1) + ". " + route.getPlacemarks()
            .get( i ).getInstructions() 
            + " (" + pointDistance +"ft" + ")";
           }
           else{
               directionsArray[i] = (i + 1) + ". " + route.getPlacemarks()
               .get( i ).getInstructions();
           }
        }
        
        ListView lv2 = (ListView)findViewById( R.id.top_slider_listview );
        lv2.setAdapter( new ArrayAdapter<String>( this,
            R.layout.menu_item_directions,
            directionsArray) );
        lv2.setTextFilterEnabled( true );
        lv2.setCacheColorHint( 0 );        
        mapView.postInvalidate();
    }


    @Override
    public void onDirectionsNotAvailable()
    {
        Toast.makeText( Maps_MainActivity.this,
            "Directions Not Available",
            Toast.LENGTH_SHORT ).show();
    }


    public class GeoUpdateHandler implements LocationListener
    {
        @Override
        public void onLocationChanged( Location location )
        {
            int lat = (int)( location.getLatitude() * 1E6 );
            int lng = (int)( location.getLongitude() * 1E6 );

            GeoPoint point = new GeoPoint( lat, lng );

            if ( bundle != null )
            {
                if ( bundle.getBoolean( "isCurrLocation" ) )
                {
                    mapController.animateTo( point );
                    mapView.invalidate();
                }
                else if ( !bundle.getBoolean( "isRoute" ) )
                {
                    mapController.animateTo( compileBuilding( bundle ).getGeoPoint() );
                    mapView.invalidate();
                }
            }
        }


        @Override
        public void onProviderDisabled( String provider )
        { 
            Toast.makeText( Maps_MainActivity.this,
                "The provider: " + provider + "is disabled",
                Toast.LENGTH_SHORT ).show();
        }


        @Override
        public void onProviderEnabled( String provider )
        { /* do nothing */
        }

        @Override
        public void onStatusChanged( String provider, int status, Bundle extras )
        { /* do nothing */
        }
    }
}
