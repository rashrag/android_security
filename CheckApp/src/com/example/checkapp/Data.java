package com.example.checkapp;
import android.util.Log;

public class Data 
{
	String t;
	String [] c;
	Data()
	{
		t="pls";
	}
	public void put(String val)
	{
		t=val;
		Log.d("test", t);
	}
	public String get()
	{
		Log.d("testget", t);
		return t;
	}
	
	public void setPlayer(String [] cards)
	{
		c = cards;
	}
	
	public String[] getPlayer()
	{
		return c;
	}
}
