package com.ug.telescopio.fragments;

import java.util.ArrayList;
import java.util.Arrays;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.ug.telescopio.R;
import com.ug.telescopio.activities.CountryDetailActivity;

public class CountriesListFragment extends Fragment implements OnItemClickListener {
	String country = "";
	ListView list;
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		String[] array_countries = new String[]{"Brasil", "MŽxico", "Colombia", "Argentina",
												"Perœ", "Venezuela", "Chile", "Ecuador", 
												"Guatemala", "Cuba"};
		ArrayList<String> countries = new ArrayList<String>(Arrays.asList(array_countries));
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), 
									    					    android.R.layout.simple_list_item_1, 
									    					    countries);
		
		list.setAdapter(adapter);		
		list.setOnItemClickListener(this);
		registerForContextMenu(list);
	}	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_countries_list, container, false);
		list = (ListView)view.findViewById(R.id.listView);
		return view;
	}


	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		inflater.inflate(R.menu.main, menu);
	}
	
	@Override
	public void onPrepareOptionsMenu(Menu menu){
		boolean landscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
		MenuItem share = menu.getItem(menu.size()-1);
		share.setVisible(landscape);
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
	    
	    MenuInflater inflater = getActivity().getMenuInflater();
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
			FragmentManager manager = getActivity().getSupportFragmentManager();
			CountryInfoFragment fragment = (CountryInfoFragment) manager.findFragmentById(R.id.fragmentCountryInfo);
			fragment.loadWebViewContent(country);
			getActivity().invalidateOptionsMenu();
		} else {
			Intent intent = new Intent(getActivity().getApplicationContext(), CountryDetailActivity.class);
			intent.putExtra(CountryDetailActivity.COUNTRY, country);
			startActivity(intent);			
		}
	}
	
	
}
