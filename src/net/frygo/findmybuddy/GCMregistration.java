package net.frygo.findmybuddy;


import java.util.ArrayList;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import com.google.android.gcm.GCMRegistrar;


import android.content.Context;
import android.util.Log;


public class GCMregistration {
	static Context c;
public static String getId(Context context){
	try{
		Log.e("ERROR", "1");
		GCMRegistrar.checkDevice(context);
		Log.e("ERROR", "Check manigiest");
		GCMRegistrar.checkManifest(context);
		c=context;
		Log.e("ERROR", "3");
		String regId ="";
				regId=GCMRegistrar.getRegistrationId(context);
		if (regId.equals("")) {
			GCMRegistrar.register(context, "1071053356137");
			regId=(GCMRegistrar.getRegistrationId(context));		
		} 
		
		String url=URL.url+"gcm_register.php";
		  ArrayList<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		    nameValuePairs.add(new BasicNameValuePair("reg_Id",regId));
		    nameValuePairs.add(new BasicNameValuePair("email_Id",new SharedPreference(context).retrievedata("email")));
		    Getdatawithoutpbar obj=new Getdatawithoutpbar(context,nameValuePairs);
		    obj.execute(url);	
		    Log.e("gcm", regId);
		
		
		    return regId;
		
		}catch (Exception e){
			Log.e("ERROR", "2");
			e.printStackTrace();
			return "0";
		}
	
}
}
