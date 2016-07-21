package net.frygo.findmybuddy;

import android.content.Context;
import android.widget.Toast;



public class ShowToast {
	public static void showToast(String msg,Context context) {
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}
}
