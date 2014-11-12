package com.example.checkapp;

import java.net.URLEncoder;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;


import android.support.v7.app.ActionBarActivity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {

	private ListView mainListView ;
	private ArrayAdapter<String> listAdapter ;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Intent i = getIntent();
		if(i.getExtras()!= null)
		{
			String apk = i.getExtras().getString("val");
			TextView tv = (TextView) findViewById(R.id.textView1);
			tv.setText(apk);
		}

	}
	public void getApkFiles(View v)
	{
		Intent intent = new Intent(this, Display.class);
		startActivity(intent);

	}
	public void getPermissions(View v)
	{
		Intent intent = new Intent(this,Permission.class);
		TextView tv = (TextView) findViewById(R.id.textView1);
		String apk = (String)tv.getText();
		intent.putExtra("apk", apk);
		startActivity(intent);
	}

	public void checkApp(View v)
	{
		final String ip="116.202.20.58/permission/";
		final String result;
		final Data d3 = new Data();
		Thread t=new Thread()
		{
			public void run()
			{	
				HttpClient myClient=new DefaultHttpClient();
				TextView tv = (TextView) findViewById(R.id.textView1);
				final String appname= (String) tv.getText();
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
						//Log.d("RESPONSE", res);
						d3.put(res);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						}

				}
			}};
			t.start();
			try {
				Thread.sleep(5000);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//Log.d("testing","done");
			String finres=d3.get();
			TextView tv = (TextView) findViewById(R.id.textView2);
			tv.setText(finres);
			//THIS IS YOUR ANSWER.YOU CAN RETURN OR DO WHAT YOU WANT
			//Toast.makeText(getApplicationContext(), finres, Toast.LENGTH_SHORT).show();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
