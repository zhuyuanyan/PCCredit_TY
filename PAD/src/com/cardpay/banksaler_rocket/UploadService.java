package com.cardpay.banksaler_rocket;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.os.IBinder;

public class UploadService extends Service {

	private String actionUrl = "http://192.168.0.249:8000/";
	private String wsImg = "upload/img";
	private ConnectionDetector cd;
	private DBHelper dbHelper;

	@Override
	public void onCreate() {
		super.onCreate();
		cd = new ConnectionDetector(this.getBaseContext());
		dbHelper = new DBHelper(this.getBaseContext());

		// 第一次检查db并建表
		dbHelper.open();
		final List<String> tableNames = dbHelper.getTableNames();
		for (String tableName : tableNames) {
			if (!dbHelper.isTableExist(tableName)) {
				String sql = "CREATE TABLE "
						+ tableName
						+ " (lsh integer primary key autoincrement,id text, jsonData text)";
				dbHelper.execSQL(sql);
			}
		}

		// 具体的upload操作
		new Thread(new Runnable() {
			boolean flag = true;
			@Override
			public void run() {
				while (flag) {
					try {
						Boolean isInternetPresent = cd.isConnectingToInternet();
						//5分钟检查一次网络
						if(!isInternetPresent){
							System.out.println("network error");
						}
						//网络连接时开始上传
						else{
							// upload
							System.out.println("start upload");
							for (String tableName : tableNames) {
								System.out.println("start upload tableName="+tableName);
								Cursor returnCursor = dbHelper.findList(false,
										tableName, new String[] { "lsh", "id", "jsonData" },
										null, null, null, null, "lsh desc", null);
								while (returnCursor.moveToNext()) {
									String lsh = returnCursor.getString(returnCursor.getColumnIndexOrThrow("lsh"));
									String id = returnCursor.getString(returnCursor.getColumnIndexOrThrow("id"));
									String jsonData = returnCursor.getString(returnCursor.getColumnIndexOrThrow("jsonData"));
									System.out.println("id=" + id + ";jsonData=" + jsonData + ";\n");
									
									//解析jsonData并上传文件
									String newjsonData = getJson(jsonData); 
									if(newjsonData.equals("")){
										//上传失败，不做处理下次重试
										
									}
									else{
										//上传到服务端db
										AndroidHttp http = new AndroidHttp();
										System.out.println("newjsonData = " + newjsonData);
										
										JSONObject jsonObj = new JSONObject();
										try {
											jsonObj = new JSONObject(newjsonData);
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										if(!id.equals("-1")){//更新操作
											http.exxcuteHttpPut(actionUrl + tableName + "/" + id, jsonObj.toString());
										}
										else{//新增操作
											http.executeHttpPost(actionUrl + tableName + "/create", jsonObj.toString());
										}
										
										//删除本地db中的数据
										dbHelper.delete(tableName, "lsh=?",new String[]{lsh});
									}
									
								}
								returnCursor.close();
							}
						}
						Thread.sleep(1000 * 20);
					} catch (Exception e) {
						System.out.println("InterruptedException");
						e.printStackTrace();
					}
				}
			}
			
		}).start();

		//dbHelper.closeConnection();
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
	}
	
	private String getJson(String jsonString){  
		JSONObject jsonObj;
		JSONObject newjsonObj = new JSONObject();
		try {
			jsonObj = new JSONObject(jsonString);
			for (Iterator iter = jsonObj.keys(); iter.hasNext();) {
				String key = (String)iter.next();
				System.out.println(jsonObj.getString(key));
				if(jsonObj.getString(key).indexOf("/mnt")>=0){//判断是否为媒体资源
					String newUrl = uploadFile(jsonObj.getString(key));
					newjsonObj.put(key,newUrl);
				}
				else{
					newjsonObj.put(key,jsonObj.getString(key));
				}
			}
			return newjsonObj.toString();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		}
    }  
	
	/* 上传文件至Server的方法 */
	private String uploadFile(String uploadFile) {
		String filename = uploadFile.substring(uploadFile.lastIndexOf('/')+1);
		String end = "\r\n";
		String twoHyphens = "--";
		String boundary = "*****";
		try {
			System.out.println("filename = "+filename);
			URL url =new URL(actionUrl + wsImg + "?file_path=" + filename);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();
			/* 允许Input、Output，不使用Cache */
			con.setDoInput(true);
			con.setDoOutput(true);
			con.setUseCaches(false);
			/* 设置传送的method=POST */
			con.setRequestMethod("POST");
			/* setRequestProperty */
			con.setRequestProperty("Connection", "Keep-Alive");
			con.setRequestProperty("Charset", "UTF-8");
			con.setRequestProperty("Content-Type",
					"multipart/form-data;boundary=" + boundary);
			/* 设置DataOutputStream */
			DataOutputStream ds = new DataOutputStream(con.getOutputStream());
			ds.writeBytes(twoHyphens + boundary + end);
			ds.writeBytes("Content-Disposition: form-data; "
					+ "name=\"file\";filename=\"" + filename + "\"" + end);
			ds.writeBytes(end);
			/* 取得文件的FileInputStream */
			FileInputStream fStream = new FileInputStream(uploadFile);
			/* 设置每次写入1024bytes */
			int bufferSize = 1024;
			byte[] buffer = new byte[bufferSize];
			int length = -1;
			/* 从文件读取数据至缓冲区 */
			while ((length = fStream.read(buffer)) != -1) {
				/* 将资料写入DataOutputStream中 */
				ds.write(buffer, 0, length);
			}
			ds.writeBytes(end);
			ds.writeBytes(twoHyphens + boundary + twoHyphens + end);
			/* close streams */
			fStream.close();
			ds.flush();
			/* 取得Response内容 */
			InputStream is = con.getInputStream();
			int ch;
			StringBuffer b = new StringBuffer();
			while ((ch = is.read()) != -1) {
				b.append((char) ch);
			}
			/* 将Response显示于Dialog */
			//showDialog("上传成功" + b.toString().trim());
			String newUrl = b.toString().trim();
			System.out.println(newUrl);
			/* 关闭DataOutputStream */
			ds.close();
			return newUrl;
		} catch (Exception e) {
			//showDialog("上传失败" + e);
			return "";
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		dbHelper.closeConnection();
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
}
