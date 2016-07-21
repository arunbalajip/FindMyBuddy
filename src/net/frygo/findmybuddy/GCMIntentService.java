/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.frygo.findmybuddy;



import java.util.ArrayList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;




import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;

import android.content.Intent;

import android.os.PowerManager;
import android.util.Log;

import com.google.android.gcm.GCMBaseIntentService;

/**
 * {@link IntentService} responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {

	
	

	public GCMIntentService() {
		super("1071053356137");
	}
	@Override
	 protected void onError(Context arg0, String arg1) {
	   
	   //Called on registration or unregistration error.
	  Log.v("TAG", "GCMIntentService onError");
	 }
	 
	 @Override
	 protected void onMessage(Context arg0, Intent intent) {
	  // TODO Auto-generated method stub
	   
	  // ---   Called when a cloud message has been received.
	   
	  // --- handle here the push notification e.g. Toast Notification
	  Log.v("TAG", "GCMIntentService onMessage");
	  String message = intent.getStringExtra("ContactNotification");
	  if(message!=null){
		generateNotification(arg0, message);
	
	 }//end of if
		
	
	  else 
	  {
		  message=intent.getStringExtra("Location");
		  if(message!=null){
			  //generateNotification(arg0, message);
			 
				
				double latitude=0.0;
				double longitude=0.0;
				// check if GPS enabled
			
				GPSTracker gps = new GPSTracker(arg0);
			    if(gps.canGetLocation()){
			    	
			    	latitude = gps.getLatitude();
			    	longitude = gps.getLongitude();
			    	gps.stopUsingGPS();
			    
			    }//can get location
			
			
			 final ArrayList<NameValuePair> nameValuePairs=new ArrayList<NameValuePair>();
				
				
				  nameValuePairs.add(new BasicNameValuePair("email_id_2",new SharedPreference(this).retrievedata("email")));
				    nameValuePairs.add(new BasicNameValuePair("latitude",String.valueOf(latitude)));
				    nameValuePairs.add(new BasicNameValuePair("longitude",String.valueOf(longitude)));
				    Getdatawithoutpbar obj = new Getdatawithoutpbar(this,nameValuePairs);
				String ur = URL.url+"updatelatitudelongitude.php";
				obj.execute(ur);
				
		  }//end of if
		  else{
			  message=intent.getStringExtra("Contactrequestaccepted");
			  if(message!=null){
				  generateAcceptfriendNotification(arg0, message,"accept"); 
			  }
			  else
			  {
				  message=intent.getStringExtra("Contactrequestrejected");
				  if(message!=null){
					  generateAcceptfriendNotification(arg0, message,"reject"); 
				  }  
			  }
		  }

	  }//end of else

	 
	 }
	 
	 @Override
	 protected void onRegistered(Context arg0, String arg1) {
	  // TODO Auto-generated method stub
	   
	  // --- Called after a device has been registered.
	   
	  Log.v("TAG", "GCMIntentService Registered");
	 }
	 
	 @Override
	 protected void onUnregistered(Context arg0, String arg1) {
	  // TODO Auto-generated method stub
	   
	    // Called after a device has been unregistered.
	  Log.v("TAG", "GCMIntentService UnRegistered");
	 }
		private static void generateNotification(Context context, String message) {
			
			Random rand=new Random();
			int x=rand.nextInt();
			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			String title = context.getString(R.string.app_name);
			Intent notificationIntent = new Intent(context,
					customlistview.class);
			notificationIntent.putExtra("alert",message);
			message= message+" would like to add you as friend";
			PendingIntent intent = PendingIntent.getActivity(context, 0,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_CANCEL_CURRENT);
			
			Notification notification = new Notification(R.drawable.logo,
					message, System.currentTimeMillis());
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			notification.setLatestEventInfo(context, title,message, intent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notificationManager.notify(x, notification);

			PowerManager pm = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			final PowerManager.WakeLock mWakelock = pm.newWakeLock(
					PowerManager.FULL_WAKE_LOCK
							| PowerManager.ACQUIRE_CAUSES_WAKEUP, title);
			mWakelock.acquire();

			// Timer before putting Android Device to sleep mode.
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {
					mWakelock.release();
				}
			};
			timer.schedule(task, 5000);
		
		}
private static void generateAcceptfriendNotification(Context context, String message,String status) {
			
			Random rand=new Random();
			int x=rand.nextInt();
			NotificationManager notificationManager = (NotificationManager) context
					.getSystemService(Context.NOTIFICATION_SERVICE);
			String title = context.getString(R.string.app_name);
			Intent notificationIntent = new Intent(context,
					customlistview.class);
			if(status.equalsIgnoreCase("accept"))
			message= message+" added you as buddy";
			else
				message= message+" rejected you as buddy";
			PendingIntent intent = PendingIntent.getActivity(context, 0,notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT|PendingIntent.FLAG_CANCEL_CURRENT);
			
			Notification notification = new Notification(R.drawable.logo,
					message, System.currentTimeMillis());
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			notification.setLatestEventInfo(context, title,message, intent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;
			notificationManager.notify(x, notification);

			PowerManager pm = (PowerManager) context
					.getSystemService(Context.POWER_SERVICE);
			final PowerManager.WakeLock mWakelock = pm.newWakeLock(
					PowerManager.FULL_WAKE_LOCK
							| PowerManager.ACQUIRE_CAUSES_WAKEUP, title);
			mWakelock.acquire();

			// Timer before putting Android Device to sleep mode.
			Timer timer = new Timer();
			TimerTask task = new TimerTask() {
				public void run() {
					mWakelock.release();
				}
			};
			timer.schedule(task, 5000);
		
		}


	}

