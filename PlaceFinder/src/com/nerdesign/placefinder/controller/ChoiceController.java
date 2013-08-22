package com.nerdesign.placefinder.controller;

import android.content.Intent;

import com.nerdesign.placefinder.ChoiceActivity;
import com.nerdesign.placefinder.GoogleMapActivity;

public class ChoiceController {

	private ChoiceActivity choiceActivity;
	
	public ChoiceController(ChoiceActivity choiceActivity){
		this.choiceActivity = choiceActivity;
	}
	
	public void search(String longitude, String latitude){
		Intent intent = new Intent(choiceActivity.getApplicationContext(), GoogleMapActivity.class);
		intent.putExtra("longitude", longitude);
		intent.putExtra("latitude", latitude);
		choiceActivity.startActivity(intent);
	}
}
