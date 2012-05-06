package org.mad.app.hokiehelper;

/**
* The DrivingDirectionsFactory class is used in parsing and gathering the 
* directions from google maps.
* @author Borrowed from source code http://code.google.com/p/mapinmypocket/
* @implemented by Juilan Adams 
* @implemented by Virginia Tech Mobile Application Development Team
* @version 2.1.12
*/    
public class Maps_DrivingDirectionsFactory
{
	public static Maps_DrivingDirections createDrivingDirections ()
	{
		return new Maps_DrivingDirectionsGoogleKML ();
	}
}
