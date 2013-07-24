package com.nerdesign.placefinder;

import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

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

		googleMap = ((SupportMapFragment) getSupportFragmentManager()
				.findFragmentById(R.id.map)).getMap();
		marker = googleMap.addMarker(new MarkerOptions().title("Vous êtes ici")
				.position(new LatLng(0, 0)));
		
		Intent intent = getIntent();
		
		if(intent != null){
			String longitude = intent.getStringExtra("longitude");
			String latitude = intent.getStringExtra("latitude");
			
			System.err.println("longitude : " + longitude);
			System.err.println("latitude : " + latitude);
			
			markerTarget = googleMap.addMarker(new MarkerOptions().title("Cible")
					.position(new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude))));
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

		// Obtention de la référence du service
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

		// On appelle la méthode pour se désabonner
		desabonnementGPS();
	}

	/**
	 * Méthode permettant de s'abonner à la localisation par GPS.
	 */
	public void abonnementGPS() {
		// On s'abonne
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				5000, 10, this);
	}

	/**
	 * Méthode permettant de se désabonner de la localisation par GPS.
	 */
	public void desabonnementGPS() {
		// Si le GPS est disponible, on s'y abonne
		locationManager.removeUpdates(this);
	}

	@Override
	public void onLocationChanged(final Location location) {
		// On affiche dans un Toat la nouvelle Localisation
		final StringBuilder msg = new StringBuilder("lat : ");
		msg.append(location.getLatitude());
		msg.append("; lng : ");
		msg.append(location.getLongitude());
		googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(
				location.getLatitude(), location.getLongitude()), 15));
		marker.setPosition(new LatLng(location.getLatitude(), location
				.getLongitude()));
		Toast.makeText(this, msg.toString(), Toast.LENGTH_SHORT).show();

	}

	@Override
	public void onProviderDisabled(final String provider) {
		// Si le GPS est désactivé on se désabonne
		if ("gps".equals(provider)) {
			desabonnementGPS();
		}
	}

	@Override
	public void onProviderEnabled(final String provider) {
		// Si le GPS est activé on s'abonne
		if ("gps".equals(provider)) {
			abonnementGPS();
		}
	}

	@Override
	public void onStatusChanged(final String provider, final int status,
			final Bundle extras) {
	}

}
