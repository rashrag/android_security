package com.example.checkapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
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

public class Permission extends Activity {

	private ListView mainListView ;
	private ArrayAdapter<String> listAdapter ;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.instructions);
		Intent intent = getIntent();
		String apk = intent.getExtras().getString("apk");
		String [] perm = null;
		ArrayList<String> appList = new ArrayList<String>();
		if(apk!="")
		{
			Context ctx = getApplicationContext();
			try {
				PackageInfo pkg =  ctx.getPackageManager().getPackageInfo(apk, PackageManager.GET_PERMISSIONS);
				Log.d("pkg", pkg.toString());
				perm = pkg.requestedPermissions;
				if(perm!=null)
				{
				for(String pi : perm)
				{
					appList.add(pi);
				}
				}
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
				/*Intent intent = new Intent(Display.this, MainActivity.class);
				intent.putExtra("val", itemValue);
				startActivity(intent);
			*/}
		});    
	}
}
