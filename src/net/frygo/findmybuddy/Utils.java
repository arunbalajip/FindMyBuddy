package net.frygo.findmybuddy;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utils {
	
	public static boolean isNetworkAvailable(Context activity) {
		   ConnectivityManager cm =
			        (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
			    NetworkInfo netInfo = cm.getActiveNetworkInfo();
			    if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			        return true;
			    }
			    return false;
	
	}
	public static boolean isLocationAvailable(Context activity) {
		LocationManager locationManager = (LocationManager) activity.getSystemService(Context.LOCATION_SERVICE);
		boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
		boolean isGPSEnabled=locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
		if(isGPSEnabled)
			return isGPSEnabled;
		if(isNetworkEnabled)
		return isNetworkEnabled;
		return false;
		
	}
	public static void generatenotification(final String Intent, String msg, String title,final Activity activity){
		AlertDialog.Builder builder = new AlertDialog.Builder(activity);
		builder.setCancelable(false)
		
				.setPositiveButton("Settings", new OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						Intent intent = new Intent(Intent);
						activity.startActivity(intent);
						
						dialog.dismiss();


					
					}
				}).setNegativeButton("Cancel", new OnClickListener() {
					
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						dialog.dismiss();
						activity.finish();
					}
				});
		AlertDialog diag = builder.create();

		diag.setIcon(R.drawable.logo);
		 diag.setTitle(title);
		  diag.setMessage(msg);
		diag.show();
		
	}//end of generatenotification
}
