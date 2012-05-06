package org.mad.app.hokiehelper;

import java.util.List;

import org.mad.app.hokiehelper.Maps_DrivingDirections.Mode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
/**
* The RouteOverlay class Displays the walking directions path.
* @author Juilan Adams 
* @author Virginia Tech Mobile Application Development Team
* @version 2.1.12
*/   
@SuppressWarnings("rawtypes")
public class Maps_RouteOverlay extends ItemizedOverlay
{
    private Maps_Route route;
    private Maps_DrivingDirections.Mode mode;
    private static int startIcon = R.drawable.walking_icon;
    private static int middleIcon = R.drawable.pin;
    private static int endIcon = R.drawable.university;
    // hard coded image offset.  
    private static int xOffset = 16;
    private static int yOffset = 37;
    private static int xpinOffset = 24;
    private static int ypinOffset = 26;
    private Context context;

    public Maps_RouteOverlay( Drawable defaultMarker, Context context )
    {
        super( defaultMarker );
        this.context = context;
        this.route = null;
    }

    
    public void addRoute(Maps_Route route, Mode mode)
    {
        this.route = route;
        this.mode = mode;
        populate();
    }
    
    @Override
    protected OverlayItem createItem( int i )
    {
        return null;
    }

    @Override
    public int size()
    {
        return 0;
    }

    @Override
    public void draw (Canvas canvas, MapView mapView, boolean shadow)
    {
        super.draw(canvas, mapView, shadow);

        if (route != null) {
            drawRoute (route.getGeoPoints(), canvas, mapView);     
            drawPlacemarks (route.getPlacemarks(), canvas, mapView);
        }
        super.draw(canvas, mapView, shadow);
    }

    private void drawRoute(List<GeoPoint> geoPoints, Canvas canvas, MapView mapView)
    {
        for (int i = 0; i < geoPoints.size() - 1; i++)
        {
            // Convert the starting geographical point coordinates to screen coordinates.
            //
            GeoPoint startGeoPoint = (GeoPoint)geoPoints.get (i);
            Point startScreenPoint = new Point ();
            mapView.getProjection().toPixels(startGeoPoint, startScreenPoint);

            // Convert the ending geographical point coordinates to screen coordinates.
            //
            GeoPoint endGeoPoint = (GeoPoint)geoPoints.get (i + 1);
            Point endScreenPoint = new Point ();
            mapView.getProjection().toPixels(endGeoPoint, endScreenPoint);

            // Draw a joining line between the starting and the ending points.
            //
            Paint paint = new Paint();
            // 5
            paint.setStrokeWidth(7);
            paint.setAntiAlias(true);
            if ((mode != null) && (mode == Mode.DRIVING)) {
                              // 65,0,0,255
                paint.setARGB(64, 255, 102, 0);
            }
            else {
                paint.setARGB(96, 102, 0, 0);
                paint.setPathEffect(new DashPathEffect(new float[]{8, 4}, 1));
            }
            canvas.drawLine(startScreenPoint.x, startScreenPoint.y, endScreenPoint.x, endScreenPoint.y, paint);
        }
    }

    private void drawPlacemarks(List<Maps_Placemark> placemarks, Canvas canvas, MapView mapView)
    {

        for (int i = 0; i < placemarks.size(); i++)
        {
            // Convert the placemark geographical location to screen coordinates.
            //
            GeoPoint geoPoint = (GeoPoint)placemarks.get(i).getLocation();
            Point screenPoint = new Point();
            mapView.getProjection().toPixels(geoPoint, screenPoint);
            

            // Select the placemark to use (start, middle or end points) and draw its bitmap
            // on the screen.
            //
            Bitmap place;

            if (i == 0) {
                // Start placemark.
                //
                place = BitmapFactory.decodeResource(context.getResources(), startIcon);
                canvas.drawBitmap(place, screenPoint.x - xOffset, screenPoint.y - yOffset, null);
            }
            else if (i == placemarks.size() - 1) {
                // End placemark.
                //
                place = BitmapFactory.decodeResource(context.getResources(), endIcon);
                canvas.drawBitmap(place, screenPoint.x - xOffset, screenPoint.y - yOffset, null);
            }
            else {
                // Middle placemark.
                //
                if ((i + 1) < placemarks.size()){
                    GeoPoint next_geoPoint = (GeoPoint)placemarks.get(i + 1).getLocation();
                    Point next_screenPoint = new Point();
                    mapView.getProjection().toPixels( next_geoPoint, next_screenPoint );
                    double angle = (Math.atan2(screenPoint.y - next_screenPoint.y, screenPoint.x - next_screenPoint.x) * 180 / Math.PI) -90;
                    place = BitmapFactory.decodeResource(context.getResources(), middleIcon);

                    Matrix matrix = new Matrix();

                    matrix.setRotate( (float)angle, place.getWidth()/2, place.getHeight()/2 );
                    int tempX = screenPoint.x - (place.getWidth()/2);
                    int tempY = screenPoint.y - (place.getHeight()/2);
                                       
                    Bitmap resized_place = Bitmap.createBitmap( place, 0, 0, place.getWidth(), place.getHeight(), matrix, true );
                    canvas.drawBitmap(resized_place, tempX - (xpinOffset/2) , tempY - (ypinOffset/2), null);
                }
                
            }
        }
    }

}
