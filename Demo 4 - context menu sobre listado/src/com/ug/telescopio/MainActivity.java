package com.ug.telescopio;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends FragmentActivity implements OnItemClickListener {
	String country = "";
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		String[] array_countries = new String[]{"Brasil", "MŽxico", "Colombia", "Argentina",
												"Perœ", "Venezuela", "Chile", "Ecuador", 
												"Guatemala", "Cuba"};
		ArrayList<String> countries = new ArrayList<String>(Arrays.asList(array_countries));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
															    android.R.layout.simple_list_item_1, 
															    countries);
		ListView list = (ListView)findViewById(R.id.listView);
		list.setAdapter(adapter);
		list.setOnItemClickListener(this);
		registerForContextMenu(list);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	@Override
	public boolean onPrepareOptionsMenu(Menu menu){
		boolean landscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		MenuItem share = menu.getItem(menu.size()-1);
		share.setVisible(landscape);		
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
			case R.id.action_help:				
				return true;
			case R.id.action_share:
				if (!country.equals("")) {
					String url = "http://es.m.wikipedia.org/wiki/" + country;
					Intent intent = new Intent();
					intent.setAction(Intent.ACTION_SEND);					
					intent.putExtra(Intent.EXTRA_TEXT, getResources().getText(R.string.msg_share) + " " + country + " " + url);
					intent.setType("text/plain");
					startActivity(Intent.createChooser(intent, getResources().getText(R.string.action_share)));
				}
				return true;
			default :
				return super.onOptionsItemSelected(item);
		}		
	}
	
	@Override
	public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {
	    super.onCreateContextMenu(menu, v, menuInfo);
	    AdapterView.AdapterContextMenuInfo info =
	            (AdapterView.AdapterContextMenuInfo) menuInfo;
	    country = ((TextView) info.targetView).getText().toString();	    
	    
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.main, menu);
	}	

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		return onOptionsItemSelected(item);
	}
	
	@Override
	public void onItemClick(AdapterView<?> adapterView, View view, int position, long arg3) {
		country = adapterView.getItemAtPosition(position).toString();
		if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
			FragmentManager manager = getSupportFragmentManager();
			CountryInfoFragment fragment = (CountryInfoFragment) manager.findFragmentById(R.id.fragmentCountryInfo);
			fragment.loadWebViewContent(country);
			invalidateOptionsMenu();
		} else {
			Intent intent = new Intent(getApplicationContext(), CountryDetailActivity.class);
			intent.putExtra(CountryDetailActivity.COUNTRY, country);
			startActivity(intent);			
		}
	}
}
