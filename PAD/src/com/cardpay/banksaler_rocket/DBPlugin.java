package com.cardpay.banksaler_rocket;

import org.json.JSONArray;

import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;

public class DBPlugin extends Plugin{

	@Override
	public PluginResult execute(String action, JSONArray jsonData, String callbackID) {
		try {
			DBHelper dbHelper = new DBHelper(ctx.getBaseContext());
			dbHelper.open();
			//System.out.println(jsonData.getString(0));//表名
			//System.out.println(jsonData.getString(1));//id
			//System.out.println(jsonData.getString(2));//数据
			String sql = "INSERT INTO "+jsonData.getString(0)+" (id, jsonData) VALUES ('"+jsonData.getString(1)+"', '"+jsonData.getString(2)+"')";
			//System.out.println(sql);
			dbHelper.execSQL(sql);
			//dbHelper.closeConnection();
			return new PluginResult(PluginResult.Status.OK, "success");
		} catch (Exception e) {
			e.printStackTrace();
			return new PluginResult(PluginResult.Status.ERROR, "error");
		}
	}
}
