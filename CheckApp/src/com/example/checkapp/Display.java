package com.example.checkapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.os.Bundle;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class Display extends Activity {

	private ListView mainListView ;
	private ArrayAdapter<String> listAdapter ;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructions);
		Context ctx = getApplicationContext();
		List<PackageInfo> list = ctx.getPackageManager().getInstalledPackages(0);
		ArrayList<String> appList = new ArrayList<String>();
		for(PackageInfo pi : list)
		{
			appList.add(pi.packageName);
		}
		listAdapter = new ArrayAdapter<String>(this, R.layout.simple_rows, appList);
		mainListView = (ListView) findViewById( R.id.mainListView );
		mainListView.setAdapter( listAdapter );     
		mainListView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				// ListView Clicked item index
				int itemPosition     = position;

				// ListView Clicked item value
				String  itemValue    = (String) mainListView.getItemAtPosition(position);
				Intent intent = new Intent(Display.this, MainActivity.class);
				intent.putExtra("val", itemValue);
				startActivity(intent);
			}
		});    
	}
}
