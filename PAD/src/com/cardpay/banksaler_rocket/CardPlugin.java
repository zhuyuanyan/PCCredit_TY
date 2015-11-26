package com.cardpay.banksaler_rocket;

import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;

public class CardPlugin extends Plugin{
	private Object synObj = new Object();
	public Bundle b;
	public String photoPath = "";
	@Override
	public PluginResult execute(String action, JSONArray data, String callbackID) {
		PluginResult result = null;

		try {
			PluginResult.Status status = PluginResult.Status.OK;
			if (action.equals("startActivity")) {
				result = new PluginResult(status, data.getString(0));
				//Intent intent = new Intent(data.getString(0));
				//ctx.startActivityForResult(intent, 110);
				
				Message msg = new Message();
				msg.what = 1;
				msg.obj = data.getString(0);
				handler.sendMessage(msg);
				sleep();
				JSONObject JsonResult=new JSONObject();
				JsonResult.put("result","Success");
	            JsonResult.put("name",b.getString("name"));
	            JsonResult.put("sex",b.getString("sex"));
	            JsonResult.put("nation",b.getString("nation"));
	            JsonResult.put("birthday",b.getString("birthday"));
	            JsonResult.put("address",b.getString("address"));
	            JsonResult.put("idNum",b.getString("idNum"));
	            JsonResult.put("photoPath",photoPath);
	            
				return new PluginResult(status, JsonResult.toString());
			}
			else{
				return new PluginResult(PluginResult.Status.ERROR,"ttttt");
			}
		} catch (Exception e) {
			StackTraceElement[] stackTraceElements = e.getStackTrace();
			  String res = e.toString() + "\n";
			  for (int index = stackTraceElements.length - 1; index >= 0; --index) {
				  res += "at [" + stackTraceElements[index].getClassName() + ",";
				  res += stackTraceElements[index].getFileName() + ",";
				  res += stackTraceElements[index].getMethodName() + ",";
				  res += stackTraceElements[index].getLineNumber() + "]\n";
			  }
			return new PluginResult(PluginResult.Status.ERROR, "b="+b+" exception=" + res);
		}
	}

	private Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			if (msg == null) {
				return;
			}
			switch (msg.what) {
			case 1:
				String className = msg.obj.toString();
				try {
					//Class activityClass = Class.forName(className);
					Intent intent = new Intent(className);
					ctx.startActivityForResult(CardPlugin.this,intent, 110);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			}
		};
	};

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent intent) {
		// TODO Auto-generated method stub
		if (requestCode == 1) {
			if (resultCode == Activity.RESULT_CANCELED) {
				
			}
		} else {
			b = intent.getExtras();
			int result = b.getInt("result");
			if (result == 0) {
				// tv.setText("读卡失败");
				return;
			}
			String name = b.getString("name");
			String sex = b.getString("sex");
			String nation = b.getString("nation");
			String birthday = b.getString("birthday");
			String address = b.getString("address");
			String idNum = b.getString("idNum");
			// String authority = b.getString("authority");
			// String validStart = b.getString("validStart");
			// String validEnd = b.getString("validEnd");
			// String sexCode = b.getString("sexCode");
			// String nationCode = b.getString("nationCode");
			// String birthday2 = b.getString("birthday2");
			// String validDate2 = b.getString("validDate2");
			byte[] bytes = (byte[]) b.getSerializable("photo");
			if (bytes == null) {
				System.out.println("bytes null");
			} else{
				System.out.println("bytes:" + bytes.length);
			}
			
			SaveFileService save = new SaveFileService(null);
			try {
				photoPath = save.saveToSdCard(idNum +  ".jpg", bytes);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			//String image64 = Base64.encodeToString(bytes, Base64.NO_WRAP);
			//String photo = "data:image/jpeg;base64," + image64.trim();
			// tv.setText("读卡成功"+"\n"+name+"\n"+sex+"\n"+nation+"\n"+birthday+"\n"+address+"\n"+idNum+"\n"+
			// authority+"\n"+validStart+"\n"+validEnd+"\n"+sexCode+"\n"+nationCode+"\n"+
			// birthday2+"\n"+validDate2);
				
			// Bitmap photo = BitmapFactory.decodeByteArray(bytes, 0,
			// bytes.length);
			// img.setImageBitmap(photo);
			super.onActivityResult(requestCode, resultCode, intent);
			weakup();
		}
	}

	private void sleep()
    {
        try
        {
            synchronized(synObj)
            {
                synObj.wait();
            }
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
        }
    }
    
    private void weakup()
    {
        synchronized(synObj)
        {
            synObj.notify();
        }
    }
}
