package com.nerdesign.placefinder;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

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
		
		String latitude = "0";
		String longitude = "0";
		
		try {
			double n1 = Double.parseDouble(((EditText) findViewById(R.id.contactN1)).getText().toString());
			double n2 = Double.parseDouble(((EditText) findViewById(R.id.contactN2)).getText().toString());
			double n3 = Double.parseDouble(((EditText) findViewById(R.id.contactN3)).getText().toString());
			
			double w1 = Double.parseDouble(((EditText) findViewById(R.id.contactW1)).getText().toString());
			double w2 = Double.parseDouble(((EditText) findViewById(R.id.contactW2)).getText().toString());
			double w3 = Double.parseDouble(((EditText) findViewById(R.id.contactW3)).getText().toString());
			
			latitude = "" + (n1 + (n2 + (n3/1000))/60);
			longitude = "-" + (w1 + (w2 + (w3/1000))/60);
		} catch (Exception e) {
			Toast.makeText(this, "Erreur lors de la saisie des coordonnées", Toast.LENGTH_SHORT).show();
		}
		
		controller.search(longitude, latitude);
	}
	
}
