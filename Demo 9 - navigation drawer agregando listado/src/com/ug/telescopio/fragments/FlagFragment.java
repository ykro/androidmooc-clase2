package com.ug.telescopio.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ug.telescopio.R;

public class FlagFragment extends Fragment {
	public static final String RESOURCE = "drawable";
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
	    View view = inflater.inflate(R.layout.fragment_flag, container, false);
        Bundle args = getArguments();
        
        ImageView img = ((ImageView) view.findViewById(R.id.imageView1));        
        img.setImageResource(args.getInt(RESOURCE));        
		return view;
	}

}
