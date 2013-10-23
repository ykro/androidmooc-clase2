package com.ug.telescopio.activities;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.ug.telescopio.R;
import com.ug.telescopio.fragments.AboutFragment;
import com.ug.telescopio.fragments.CountriesContentFragment;

public class MainActivity extends ActionBarActivity {
    private ListView drawerList;
    private String[] drawerOptions;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    
    Fragment[] fragments = new Fragment[]{new CountriesContentFragment(),
                                          new AboutFragment()};
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.left_drawer);
        drawerOptions = getResources().getStringArray(R.array.drawer_options);
        drawerList.setAdapter(new ArrayAdapter<String>(this,
                                                       R.layout.drawer_list_item, 
                                                       drawerOptions));
        drawerList.setItemChecked(0, true);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        drawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.drawable.ic_drawer,
                R.string.drawer_open,
                R.string.drawer_close
                ) {

            public void onDrawerClosed(View view) {
            	ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            	//invalidateOptionsMenu();
            }

            public void onDrawerOpened(View drawerView) {
            	ActivityCompat.invalidateOptionsMenu(MainActivity.this);
            	//invalidateOptionsMenu();
            }
        };

        drawerLayout.setDrawerListener(drawerToggle);        
        
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        
        FragmentManager manager = getSupportFragmentManager();
        manager.beginTransaction()
        	    .add(R.id.contentFrame, fragments[0])
        		.add(R.id.contentFrame, fragments[1])
        	    .commit();	 
        setContent(0);		
	}
	
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
          	 
            if (drawerLayout.isDrawerOpen(drawerList)) {
            	drawerLayout.closeDrawer(drawerList);
            } else {
            	drawerLayout.openDrawer(drawerList);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    
	public void setContent(int index) {
		Fragment toHide = null;
		Fragment toShow = null;
		final ActionBar actionBar = getSupportActionBar();
		switch (index) {
			case 0:
				toHide = fragments[1];
				toShow = fragments[0];				
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
				break;
			case 1:
				toHide = fragments[0];
				toShow = fragments[1];
				actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);				
				break;
		}
		
		FragmentManager manager = getSupportFragmentManager();
		
		manager.beginTransaction()
				.hide(toHide)
				.show(toShow)
				.commit();		
        drawerList.setItemChecked(index, true);
        drawerLayout.closeDrawer(drawerList);	
	}
	
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
    	
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        	setContent(position);
        }
    }	
}
