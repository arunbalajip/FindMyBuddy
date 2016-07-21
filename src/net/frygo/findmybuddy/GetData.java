package net.frygo.findmybuddy;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;



public class GetData extends AsyncTask<String, Void, String> {
	Context context;
	String key;
	 final ArrayList<NameValuePair> nameValuePairs;
	 String name;
	GetData(Context arg,String k, final ArrayList<NameValuePair> n) {
		context = arg;
		key=k;
		nameValuePairs=n;
	}
	GetData(Context arg,String k, final ArrayList<NameValuePair> n,String username) {
		context = arg;
		key=k;
		nameValuePairs=n;
		name=username;
	}

	ProgressDialog pDialog;
	String data = null;
	@Override
	protected void onPreExecute() {
		super.onPreExecute();

		pDialog = new ProgressDialog(context);
		pDialog.setMessage("Loading...");
		pDialog.setCancelable(true);
		pDialog.show();
	}

	@Override
	protected String doInBackground(String... arg0) {

		BufferedReader reader = null;
		

		try {
			HttpClient client = new DefaultHttpClient();

			// Storing the URI(web address, link) here
			URI uri = new URI(arg0[0]);

			// To retrieve info
			 HttpPost httppost = new HttpPost(uri);
		        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			// Executing the request here on the HTTPClient
	
		        
	    
			HttpResponse response = client.execute(httppost);
			  final int statusCode = response.getStatusLine().getStatusCode();
			  
              if (statusCode != HttpStatus.SC_OK) {
              
            	  return "Internet connection failed";

              }
			// Entity - sent or received with an HTTP message

			InputStream stream = response.getEntity().getContent();

			reader = new BufferedReader(new InputStreamReader(stream));

			StringBuffer buffer = new StringBuffer("");
			String line = "";

			while ((line = reader.readLine()) != null) {
				buffer.append(line);
			}
			data = buffer.toString();

			reader.close();

		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
		return data;
	}
	protected void onCancelled() {
	   ShowToast.showToast("failed to connect", context);
		if (null != pDialog && pDialog.isShowing()) {
			pDialog.dismiss();
		}
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);	
		Log.v("ARUN","1");
		JSONObject json;
		if(result!=null){
			try {
				Log.v("ARUN","2");
				json=new JSONObject(result);
				Log.v("ARUN","3");
				if(!key.equalsIgnoreCase(customlistview.ARRAY_NAME)){
					Log.v("ARUN","4");
				data = json.getString(key);
				Log.v("ARUN","5");
				if(data.equalsIgnoreCase("Passcode matched")){
					Log.v("ARUN",data);
					Log.v("ARUN","--"+json.getString("user_name"));
					Log.v("ARUN",json.getString("user_email"));
					 String name=json.getString("user_name");
					 String email=json.getString("user_email");
					
					new SharedPreference(context).storedata(name, email);
					 
					 Intent i =context.getPackageManager()
							 .getLaunchIntentForPackage(context.getPackageName() );
					 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
					
							 ((Activity) context).finish();
							 context.startActivity(i);
							
							 
				}
				else if(key.equalsIgnoreCase("add_contact")){

					ShowToast.showToast(name+" "+data, context);
					
					
				}
					
				else if(key.equalsIgnoreCase("success")&&data.equalsIgnoreCase("location found")){
					Double d1=json.getDouble("latitude");
					Double d2=json.getDouble("Longitude");
					Intent i = new Intent(context, locationview.class);
					i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);

					i.putExtra("latitude",d1);
					i.putExtra("longitude", d2);
					 context.startActivity(i);
	
					//ShowToast.showToast(d1+","+d2, context);
					//context.startActivity(new Intent(context, locationview.class));
				}
				else if(key.equalsIgnoreCase("add_friend")){
					 Intent i =context.getPackageManager()
							 .getLaunchIntentForPackage(context.getPackageName() );
							 
							 i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK );
							 ((Activity) context).finish();
							 context.startActivity(i);
				}
				else
					ShowToast.showToast(data, context);
				}else {
					JSONArray jsonArray = json.getJSONArray(customlistview.ARRAY_NAME);
					if(jsonArray.length()!=0){
					for (int i = 0; i < jsonArray.length(); i++) {
					       user_listview_handler du=new user_listview_handler(context);
						
						JSONObject objJson = jsonArray.getJSONObject(i);
						String name=objJson.getString(customlistview.NAME);
						String email=objJson.getString(customlistview.EMAIL);
						String imgurl=objJson.getString(customlistview.PHOTOURL);
						
						du.addContact(new userinfo(name,email,String.valueOf(i),imgurl));
						
						 }

					 Intent i =new Intent(context,customlistview.class);
					
						//	 ((Activity) context).finish();
							 context.startActivity(i);
					
				
					}else{
						Log.e("ARUN","10");
				 ArrayList<String> appointment = new ArrayList<String>();
						appointment.add("No Friends found");
				           
						ArrayAdapter<String> aa= new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, 
				                appointment);
				        customlistview.listView.setAdapter(aa);
						
					}
					Log.e("ARUN","11");
				}
				
			} catch (JSONException e) {
				Log.v("ARUN","Exception");
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		if (null != pDialog && pDialog.isShowing()) {
			pDialog.dismiss();
		}
		}//end of result!=null

	}// onpostExecute
	
}// GetData

