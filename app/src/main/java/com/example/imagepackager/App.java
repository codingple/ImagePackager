package com.example.imagepackager;

import android.app.Application;
import android.content.ContentResolver;
import android.content.Context;
import android.speech.tts.TextToSpeech.OnInitListener;

public class App extends Application implements OnInitListener {
	private static Context mContext;

	@Override
	public void onInit(int status) {
		// TODO Auto-generated method stub
		
	}
	
	public static Context getContext(){
		return mContext;
	}
	
	public static void setContext(Context con){
		mContext = con;
	}
	
	public static ContentResolver contentresolver(){
		return mContext.getContentResolver();
	}
}
