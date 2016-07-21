package net.frygo.findmybuddy;
/*
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

public class mapview extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	
	 setContentView(R.layout.map);
		 Double lat = 0.0,lon=0.0;
		 Bundle extras = getIntent().getExtras();
			if(extras != null){
				lat=extras.getDouble("latitude");
				lon=extras.getDouble("longitude");
				}
	     LatLng  CURRENT=new LatLng(lat, lon);
	     FragmentManager fmanager = getSupportFragmentManager();
	     Fragment fragment = fmanager.findFragmentById(R.id.map);
	     SupportMapFragment supportmapfragment = (SupportMapFragment)fragment;
	     GoogleMap map = supportmapfragment.getMap();

			if (map != null) {
				Marker curr = map.addMarker(new MarkerOptions().position(
						CURRENT).title("My Current position"));
				// Move the camera instantly to your current position with a
				// zoom of 15.
				map.moveCamera(CameraUpdateFactory.newLatLngZoom(CURRENT, 15));
				// Zoom in, animating the camera.
				map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

	}
	
		 
		 
}
}*/