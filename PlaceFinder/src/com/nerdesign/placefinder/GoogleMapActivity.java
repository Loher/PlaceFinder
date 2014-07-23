package com.nerdesign.placefinder;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.Menu;


import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.nerdesign.placefinder.MySupportMapFragment.MapViewCreatedListener;

public class GoogleMapActivity extends FragmentActivity implements
		LocationListener {

	private LocationManager locationManager;
	private GoogleMap googleMap;
	private Marker marker;
	private Marker markerTarget;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_google_map);
		
			
			googleMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
			
			Intent intent = getIntent();
			
			if(intent != null){
				String longitude = intent.getStringExtra("longitude");
				String latitude = intent.getStringExtra("latitude");
				
				markerTarget = googleMap.addMarker(new MarkerOptions().title("Target!")
						.position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))));
				
				googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
						Double.parseDouble(latitude), Double.parseDouble(longitude)), 15));
			}
			
			
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.google_map, menu);
		return true;
	}

	@Override
	public void onResume() {
		super.onResume();

		// Obtention de la r�f�rence du service
		locationManager = (LocationManager) this
				.getSystemService(LOCATION_SERVICE);

		// Si le GPS est disponible, on s'y abonne
		if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
			abonnementGPS();
		}
	}

	@Override
	public void onPause() {
		super.onPause();

		// On appelle la m�thode pour se d�sabonner
		desabonnementGPS();
	}

	/**
	 * M�thode permettant de s'abonner � la localisation par GPS.
	 */
	public void abonnementGPS() {
		// On s'abonne
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				5000, 10, this);
	}

	/**
	 * M�thode permettant de se d�sabonner de la localisation par GPS.
	 */
	public void desabonnementGPS() {
		// Si le GPS est disponible, on s'y abonne
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(final Location location) {
		
		if(marker != null){
			marker.setPosition(new LatLng(location.getLatitude(), location
					.getLongitude()));
		}
		else{
			marker = googleMap.addMarker(new MarkerOptions().title("Me")
					.position(new LatLng(location.getLatitude(), location
							.getLongitude())));
		}	
	}

	@Override
	public void onProviderDisabled(final String provider) {
		// Si le GPS est d�sactiv� on se d�sabonne
		if ("gps".equals(provider)) {
			desabonnementGPS();
		}
	}

	@Override
	public void onProviderEnabled(final String provider) {
		// Si le GPS est activ� on s'abonne
		if ("gps".equals(provider)) {
			abonnementGPS();
		}
	}

	@Override
	public void onStatusChanged(final String provider, final int status,
			final Bundle extras) {
	}

}
