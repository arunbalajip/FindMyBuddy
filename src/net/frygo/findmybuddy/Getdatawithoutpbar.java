package net.frygo.findmybuddy;



import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;

import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import android.content.Context;
import android.content.Intent;

import android.os.AsyncTask;



public class Getdatawithoutpbar extends AsyncTask<String, Void, String> {
	Context context;
	String triggerpoint=null;
	 final ArrayList<NameValuePair> nameValuePairs;
	 public Getdatawithoutpbar(Context arg, final ArrayList<NameValuePair> n) {
		context = arg;
		nameValuePairs=n;
	}
	 public Getdatawithoutpbar(Context arg, final ArrayList<NameValuePair> n,String trigger) {
		context = arg;
		nameValuePairs=n;
		triggerpoint=trigger;
	}

	String data = null;



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

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);	

	}// onpostExecute

}// GetData

