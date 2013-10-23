package com.ug.telescopio.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBar.Tab;
import android.support.v7.app.ActionBar.TabListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ug.telescopio.R;
import com.ug.telescopio.activities.MainActivity;

public class CountriesContentFragment extends Fragment implements TabListener {
	Fragment[] fragments = new Fragment[]{new CountriesListFragment(), 
		      						      new CountriesFlagsFragment()};

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);

		fragments[0].setHasOptionsMenu(true);
		
		final ActionBar actionBar = ((MainActivity) getActivity()).getSupportActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
	    
        actionBar.addTab(
                actionBar.newTab()
                        .setText(getResources().getString(R.string.list_title))
                        .setTabListener(this));
        
        actionBar.addTab(
                actionBar.newTab()
                        .setText(getResources().getString(R.string.flags_title))
                        .setTabListener(this));  
        FragmentManager manager = getActivity().getSupportFragmentManager();
        manager.beginTransaction()
        	    .add(R.id.mainContent, fragments[0])
        		.add(R.id.mainContent, fragments[1])        		        	   
        	    .commit();	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {		
		return inflater.inflate(R.layout.fragment_countries_content, container, false);
	}	
	
	@Override
	public void onTabReselected(Tab arg0, FragmentTransaction arg1) {
	}

	@Override
	public void onTabSelected(Tab tab, FragmentTransaction arg1) {
		setContent(tab.getPosition());
	}

	@Override
	public void onTabUnselected(Tab arg0, FragmentTransaction arg1) {
	} 

	public void setContent(int tab) {		
		Fragment toHide = null;
		Fragment toShow = null;
		switch (tab) {
			case 0:
				toHide = fragments[1];
				toShow = fragments[0];
				break;
			case 1:
				toHide = fragments[0];
				toShow = fragments[1];
				break;
		}
		
		FragmentManager manager = getActivity().getSupportFragmentManager();
		
		manager.beginTransaction()
				.hide(toHide)
				.show(toShow)
				.commit();
	}	
}
