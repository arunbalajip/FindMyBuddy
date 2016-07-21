package net.frygo.findmybuddy;


import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;


public class GPSTracker extends Service implements LocationListener {
static String modeoftransfer=null;
	private final Context mContext;



	// flag for network status
	boolean isNetworkEnabled = false;
	boolean isGPSEnabled=false;
	// flag for GPS status
	boolean isPassiveEnabled = false;
	//boolean gotlocatio=false;
	Location location=null; // location
	double latitude; // latitude
	double longitude; // longitude

	// The minimum distance to change Updates in meters
	private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 30; // 30 meters

	// The minimum time between updates in milliseconds
	private static final long MIN_TIME_BW_UPDATES =  1000*1;// 60 seconds

	// Declaring a Location Manager
	protected LocationManager locationManager;

	public GPSTracker(Context context) {
		this.mContext = context;
		getLocation();
	}

	public Location getLocation() {
		try {
			locationManager = (LocationManager) mContext
					.getSystemService(LOCATION_SERVICE);
/*			
			Criteria criteria=new Criteria();
			//criteria.setAccuracy((int)MIN_DISTANCE_CHANGE_FOR_UPDATES);
			List<String> providers=locationManager.getProviders(criteria, false);
			for(String provider:providers){
				location=locationManager.getLastKnownLocation(provider);
				if(((System.currentTimeMillis()-location.getTime()))<MIN_TIME_BW_UPDATES*2||location.getAccuracy()<MIN_DISTANCE_CHANGE_FOR_UPDATES*2){
					gotlocatio=true;
					break;
				}
				
			}
	*/		
		//	if(!gotlocatio){
			isNetworkEnabled = locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
			isGPSEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
			isPassiveEnabled=locationManager.isProviderEnabled(LocationManager.PASSIVE_PROVIDER);
	
			
				if (isNetworkEnabled&&location==null) {
					locationManager.requestLocationUpdates(
							LocationManager.NETWORK_PROVIDER,
							MIN_TIME_BW_UPDATES,
							MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
					location = locationManager
							.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
					if (location != null) {
						latitude = location.getLatitude();
						longitude = location.getLongitude();
				modeoftransfer="Network provider";
				
					}
					
				}
				
				
					
				else if (isGPSEnabled){
						
							locationManager.requestLocationUpdates(
									LocationManager.GPS_PROVIDER,
									MIN_TIME_BW_UPDATES,
									MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
							location = locationManager
									.getLastKnownLocation(LocationManager.GPS_PROVIDER);
							if (location != null) {
								latitude = location.getLatitude();
								longitude = location.getLongitude();
						modeoftransfer="Network provider";
						
							}
							
						}
				else 
					location = locationManager
							.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
			
					

		
					
			
			
		//	}//end of gotlocatio
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return location;
	}
	
	/**
	 * Stop using GPS listener
	 * Calling this function will stop using GPS in your app
	 * */
	public void stopUsingGPS(){
		if(locationManager != null){
			locationManager.removeUpdates(GPSTracker.this);
		}		
	}
	   public void onDestroy() {
	        super.onDestroy();

	        stopUsingGPS();
	    }
	
	/**
	 * Function to get latitude
	 * */
	public double getLatitude(){
		if(location != null){
			latitude = location.getLatitude();
		}
		
		// return latitude
		return latitude;
	}
	
	/**
	 * Function to get longitude
	 * */
	public double getLongitude(){
		if(location != null){
			longitude = location.getLongitude();
		}
		
		// return longitude
		return longitude;
	}
	
	/**
	 * Function to check GPS/wifi enabled
	 * @return boolean
	 * */
	public boolean canGetLocation() {
		return true;
	}

	public void onLocationChanged(Location arg0) {
		location=arg0;
	
		
		
	}

	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub
		
	}

	public void onProviderEnabled(String arg0) {
	if(arg0==LocationManager.GPS_PROVIDER){
		stopUsingGPS();		
	}
		
	}

	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Function to show settings alert dialog
	 * On pressing Settings button will lauch Settings Options
	 * */

}