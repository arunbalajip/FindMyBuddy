package net.frygo.findmybuddy;





import java.util.ArrayList;


import net.frygo.findmybuddy.Getdatawithoutpbar;
import net.frygo.findmybuddy.SharedPreference;
import net.frygo.findmybuddy.URL;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NetworkChangeReceiver  extends BroadcastReceiver  {
	 AlarmManager service;
     Intent i;
     PendingIntent pending;
    
     GetData obj;
     long Timestamp=AlarmManager.INTERVAL_FIFTEEN_MINUTES;
     String		ur;

   

	public void onReceive(Context context, Intent intent) {
		//ShowToast.showToast(intent.getAction()+"Arun balaji", context);
		Log.e("Tag", "Networkchangereceiver");
		if(Utils.isNetworkAvailable(context)){
		SetAlarm(context);
		 buildLocation(context);
		}
		else 
			CancelAlarm(context);
		
}

	 public void SetAlarm(Context context)
	 	    {
		 service = (AlarmManager) context
		            .getSystemService(Context.ALARM_SERVICE);
			i = new Intent(context, NetworkChangeReceiver.class);
			pending = PendingIntent.getBroadcast(context, 0, i,
		            0);
			Log.e("Tag", "Networkchangereceiver updating");
			service.setRepeating(AlarmManager.RTC_WAKEUP,
					System.currentTimeMillis()+Timestamp, Timestamp, pending);
	     }	//set Alarm
	 public void CancelAlarm(Context context)
	     {
		 service = (AlarmManager) context
		            .getSystemService(Context.ALARM_SERVICE);
			i = new Intent(context,NetworkChangeReceiver.class);
			pending = PendingIntent.getBroadcast(context, 0, i,
		            0);
			service.cancel(pending);	     
			}


	    private void buildLocation(Context context)  
	    {  	   	
	    	double latitude=0.0;
	    	double longitude=0.0;
	    	// check if GPS enabled

	    	GPSTracker gps = new GPSTracker(context);
	        if(gps.canGetLocation()){
	        	
	        	latitude = gps.getLatitude();
	        	longitude = gps.getLongitude();
	        	gps.stopUsingGPS();
	        
	        }//can get location

	
	    final ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();    	
	    	  nameValuePairs.add(new BasicNameValuePair("email_id_2",new SharedPreference(context).retrievedata("email")));
	    	    nameValuePairs.add(new BasicNameValuePair("latitude",String.valueOf(latitude)));
	    	    nameValuePairs.add(new BasicNameValuePair("longitude",String.valueOf(longitude)));
	    	    Getdatawithoutpbar obj = new Getdatawithoutpbar(context,nameValuePairs);
	    	String ur = URL.url+"updatelatitudelongitude.php";
	    	obj.execute(ur);
	    }  
	  


}
