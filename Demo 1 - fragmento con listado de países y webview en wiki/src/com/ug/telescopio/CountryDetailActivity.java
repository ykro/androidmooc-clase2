package com.ug.telescopio;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class CountryDetailActivity extends FragmentActivity {
	private String country = "";
	public final static String COUNTRY = "country";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_country_detail);		
		Intent intent = getIntent();
		country = intent.getStringExtra(COUNTRY);
	}
	
	public String getCountrySelected(){
		return country;
	}
}
