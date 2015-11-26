package com.cardpay.banksaler_rocket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;

public class AndroidHttp {
	public String executeHttpGet(String serverUrl) {
		String result = null;
		URL url = null;
		HttpURLConnection connection = null;
		InputStreamReader in = null;
		try {
			url = new URL(serverUrl);
			connection = (HttpURLConnection) url.openConnection();
			in = new InputStreamReader(connection.getInputStream());
			BufferedReader bufferedReader = new BufferedReader(in);
			StringBuffer strBuffer = new StringBuffer();
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				strBuffer.append(line);
			}
			result = strBuffer.toString();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				connection.disconnect();
			}
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

		}
		return result;
	}

	public void executeHttpPost(String serverUrl, String data) {
		HttpClient httpclient = new DefaultHttpClient();
		// 你的URL
		HttpPost httppost = new HttpPost(serverUrl);

		try {
			HttpEntity entity = new StringEntity(data);  
			httppost.setEntity(entity);

			HttpResponse response;
			response = httpclient.execute(httppost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void exxcuteHttpPut(String serverUrl, String data) {
		HttpClient httpclient = new DefaultHttpClient();
		// 你的URL
		HttpPut httpput = new HttpPut(serverUrl);
		
		try {
			HttpEntity entity = new StringEntity(data);  
			httpput.setEntity(entity);

			HttpResponse response;
			response = httpclient.execute(httpput);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
