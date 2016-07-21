package net.frygo.findmybuddy;

import java.io.IOException;
import java.util.List;
import java.util.Locale;


import android.app.Activity;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class locationview extends Activity {
TextView tvloc,tvadd;
Double latitude=0.0;
Double longitude=0.0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Bundle extras = getIntent().getExtras();
		if(extras != null){
			latitude=extras.getDouble("latitude");
			longitude=extras.getDouble("longitude");
			}
		
		setContentView(R.layout.locationviewer);
		tvloc=(TextView)findViewById(R.id.locationview);
		tvadd=(TextView)findViewById(R.id.address);
		
		
		tvadd.setText("Address");
		String d;
		if(latitude==0.0||longitude==0.0)
			d="Failed to fetch the location";
		
		else if((d=showlocale(latitude,longitude)).equalsIgnoreCase("No Address found"))
			d="Failed to fetch the location";
			
		tvloc.setText(d);
		
		
		
		
	}

		 public String showlocale(double latitude,double longitude){
				Geocoder gc=new Geocoder(this,Locale.getDefault());
				
				String addressString="No Address found";		
				 try {
				        List<Address> addresses = gc.getFromLocation(latitude, longitude, 10);
				        
				        if(addresses != null) {
				        StringBuilder sb = new StringBuilder();
				        int k=1;
				        if (addresses.size() > 0) {
				        	for(int j=0;j<2;j++){
				          Address address = addresses.get(j);
				        
				          sb.append((k)+")");
				          for (int i = 0; i < address.getMaxAddressLineIndex(); i++)
				        	  
				            sb.append(address.getAddressLine(i)).append("\n");
				          sb.append(address.getCountryName()).append("\n\n");
				          k++;
				          }
				        	addressString=sb.toString();
				        }
				        }
				
				        
				      } catch (IOException e) {
				      }
			        
				
				
				 return addressString;
				
			}
			@Override
			public boolean onCreateOptionsMenu(Menu menu) {
				// Inflate the menu; this adds items to the action bar if it is present.
				getMenuInflater().inflate(R.menu.googlemap, menu);
				return true;
			}
			public boolean onOptionsItemSelected(MenuItem item) {

				if (item.getItemId() == R.id.mapview) {

					//Intent i = new Intent(this, mapview.class);
					//i.putExtra("latitude", latitude);
					//i.putExtra("longitude", longitude);
					//startActivity(i);
				}
				return false;
			}


}
