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

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Check extends Activity {

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
			final String ip="116.202.144.250/permission/";
			final String result;
			final Data d3 = new Data();
			Thread t=new Thread()
			{
				public void run()
				{	
					HttpClient myClient=new DefaultHttpClient();
					final String appname= getIntent().getExtras().getString("apk");
					if(appname!="")
					{	
						String result = null;
						Context ctx = getApplicationContext();
						try {
							PackageInfo pkg =  ctx.getPackageManager().getPackageInfo(appname, PackageManager.GET_PERMISSIONS);
							Log.d("pkg", pkg.toString());
							String [] perm = null;
							//Log.d("hello",pkg.requestedPermissions);
							if(pkg.requestedPermissions == null)
							{
								Log.d("permission","none");
							}
							else{

								perm = pkg.requestedPermissions;
								//Log.d("testing",perm.toString());
								StringBuilder builder = new StringBuilder();
								for(String s : perm) {
									builder.append(s);
									builder.append(",");
								}
								result = builder.toString();
							}
							Log.d("testing", result);

						} catch (NameNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						final String permissions = result;
						try {
							String app=URLEncoder.encode(appname,"UTF-8");
							String pl=URLEncoder.encode(permissions,"UTF-8");
							HttpGet get=new HttpGet("http://"+ip+"nbtrainer.php?appname="+app+"&permissions="+pl);
							HttpResponse resp = myClient.execute(get);
							String res=EntityUtils.toString(resp.getEntity());
							Log.d("RESPONSE", res);
							d3.put(res);
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

					}
				}};
				t.start();
				try {
					Thread.sleep(10000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String response = d3.get();
				String [] values = response.split(":");
				Log.d("checking",values[0]);
				String []permissions = values[1].split(",");
				appList.addAll(Arrays.asList(permissions));
				appList.add(values[2]);
				listAdapter = new ArrayAdapter<String>(this, R.layout.simple_rows, appList);
				mainListView = (ListView) findViewById( R.id.mainListView );
				mainListView.setAdapter( listAdapter );  

		}
	}
}
