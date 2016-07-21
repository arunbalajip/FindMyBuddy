package net.frygo.findmybuddy;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreference extends Activity {
	public static  SharedPreferences setting;
	public static final String PRES_NAME="Findmybuddy";
	Context c;
	public SharedPreference(Context context){
	setting=context.getSharedPreferences(PRES_NAME,0);
	c=context;
	
	}
public  void storedata(String name,String email){
		SharedPreferences.Editor editor=setting.edit();
		editor.putString("name",name);
		editor.putString("email", email);
		editor.commit();
		GCMregistration.getId(c);
		

		
}
public String retrievedata(String key){
	
	return setting.getString(key, "default");
	
}
}
