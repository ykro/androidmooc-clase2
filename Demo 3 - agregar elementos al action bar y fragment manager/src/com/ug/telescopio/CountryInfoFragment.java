package com.ug.telescopio;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class CountryInfoFragment extends Fragment {
	WebView webView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_country_info, container, false);
		webView = (WebView)view.findViewById(R.id.webView);
		return view;
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		if (getActivity() instanceof CountryDetailActivity) {
			String country = ((CountryDetailActivity)getActivity()).getCountrySelected();
			loadWebViewContent(country);
		}
	}
	
	public void loadWebViewContent(String country) {
		webView.loadUrl("http://es.m.wikipedia.org/wiki/" + country);
		webView.setWebViewClient(new WebViewClient(){
			@Override
		    public boolean shouldOverrideUrlLoading(WebView view, String url) {
		        view.loadUrl(url);
		        return true;
		    }			
		});		
		
	}
}
