package com.cardpay.banksaler_rocket;
import org.json.JSONObject;

import com.phonegap.DroidGap;

import android.content.Intent;
import android.os.Bundle;

public class MyPhoneGapActivity extends DroidGap {
	Intent intent;  
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		//加载程序
		super.onCreate(savedInstanceState);
		super.loadUrl("file:///android_asset/www/index.html");
		
		//启动后台upload服务
		intent = new Intent(getApplicationContext(),UploadService.class);
		startService(intent);
	}
}
