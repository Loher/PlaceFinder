package com.nerdesign.placefinder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

import com.google.android.gms.maps.model.LatLng;
import com.nerdesign.placefinder.controller.ChoiceController;

public class ChoiceActivity extends Activity{

	private ChoiceController controller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.choice_activity);
		
		controller = new ChoiceController(this);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		return true;
	}
	
	public void search(View view){
		
		String latitude;
		String longitude;
		
		double n1 = Double.parseDouble(((EditText) findViewById(R.id.contactN1)).getText().toString());
		double n2 = Double.parseDouble(((EditText) findViewById(R.id.contactN2)).getText().toString());
		double n3 = Double.parseDouble(((EditText) findViewById(R.id.contactN3)).getText().toString());
		
		double w1 = Double.parseDouble(((EditText) findViewById(R.id.contactW1)).getText().toString());
		double w2 = Double.parseDouble(((EditText) findViewById(R.id.contactW2)).getText().toString());
		double w3 = Double.parseDouble(((EditText) findViewById(R.id.contactW3)).getText().toString());
		
		latitude = "" + (n1 + ((n3 / 60) + n2) / 60);
		longitude = "-" + (w1 + ((w3 / 60) + w2) / 60);
		
//		Degrees = D + ((S/60)+M)/60
		
		controller.search(longitude, latitude);
	}
	
}
