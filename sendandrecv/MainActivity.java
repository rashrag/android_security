package com.example.posttest;

import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Build;

public class MainActivity extends Activity {

	   private EditText  username=null;
	   private EditText  password=null;
	   private TextView attempts;
	   private Button login;
	   int counter = 3;
	   @Override
	   protected void onCreate(Bundle savedInstanceState) {
	      super.onCreate(savedInstanceState);
	      setContentView(R.layout.activity_main);
	    //  username = (EditText)findViewById(R.id.editText1);
	     // password = (EditText)findViewById(R.id.editText2);
	      login = (Button)findViewById(R.id.button1);
	   }

	   public void login(View view)
	   {
		   Toast.makeText(getApplicationContext(), "hmm",Toast.LENGTH_SHORT).show();
		   final String ip="192.168.1.6/permission/";
		   final String result;
		   final Data d3 = new Data();
		   Thread t=new Thread()
	    	{
	        	public void run()
	        	{	
	        		Log.d("testing", "Came to run");
	        		HttpClient myClient=new DefaultHttpClient();
	        		final String appname="Rash";
	        		final String permissions ="android.permission.GET_ACCOUNTS, android.permission.USE_CREDENTIALS, android.permission.RECORD_AUDIO, android.permission.INTERNET, android.permission.ACCESS_NETWORK_STATE, android.permission.WRITE_EXTERNAL_STORAGE, com.google.android.providers.gsf.permission.READ_GSERVICES, android.permission.READ_SYNC_SETTINGS, android.permission.READ_SYNC_STATS, android.permission.WRITE_SYNC_SETTINGS, android.permission.SUBSCRIBED_FEEDS_READ, android.permission.SUBSCRIBED_FEEDS_WRITE, android.permission.READ_EXTERNAL_STORAGE";
	        	//	final String cardval = buttonId;
	        		String test="fsf";
	        	
	        		try {
	    			String app=URLEncoder.encode(appname,"UTF-8");
	    			String pl=URLEncoder.encode(permissions,"UTF-8");
	    			HttpGet get=new HttpGet(ip+"nbtrainer.php?appname="+app+"&permissions="+pl);
	    			HttpResponse resp = myClient.execute(get);
	    			String res=EntityUtils.toString(resp.getEntity());
	    			Log.d("RESPONSE", res);
	    			d3.put(res);
	    		} catch (Exception e) {
	    			// TODO Auto-generated catch block
	    			e.printStackTrace();
	    			test=e.getMessage();
	    			//flag=1;
	    		//Toast.maeText(getBaseContext(),"exc",Toast.LENGTH_SHORT).show();
	    		}
	        	
	        	
	        	//Toast.makeText(getBaseContext(),res,Toast.LENGTH_SHORT).show();
	        	}};
	        	t.start();
	        	try {
					Thread.sleep(5000);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
	        	Log.d("testing","done");
	        	String finres=d3.get();
	        	//THIS IS YOUR ANSWER.YOU CAN RETURN OR DO WHAT YOU WANT
	        	
	   }
}
