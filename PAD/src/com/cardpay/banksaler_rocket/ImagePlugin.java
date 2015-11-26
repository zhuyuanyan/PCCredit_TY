package com.cardpay.banksaler_rocket;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONObject;

import com.phonegap.api.Plugin;
import com.phonegap.api.PluginResult;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.Message;
import android.util.Base64;
import android.util.Log;

public class ImagePlugin extends Plugin{
	// 计算图片的缩放值
	public int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		if (height > reqHeight || width > reqWidth) {
			final int heightRatio = Math.round((float) height
					/ (float) reqHeight);
			final int widthRatio = Math.round((float) width / (float) reqWidth);
			inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
		}

		return inSampleSize;
	}

	// 根据路径获得图片并压缩，返回bitmap用于显示
	public Bitmap getSmallBitmap(String filePath) {
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, 480, 800);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;

		return BitmapFactory.decodeFile(filePath, options);
	}

	// 把bitmap转换成String
	public String bitmapToString(String filePath) {
		Bitmap bm = getSmallBitmap(filePath);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		bm.compress(Bitmap.CompressFormat.JPEG, 80, baos);
		byte[] b = baos.toByteArray();
		//return Base64.encodeToString(b, Base64.DEFAULT);
		SaveFileService server = new SaveFileService(null);
		try {
			filePath = server.saveToSdCard("test.jpg",b);
			return filePath;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return filePath;
		}
	}

//	public void test(String url){
//		BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        // 获取这个图片的宽和高
//        //Bitmap bitmap = BitmapFactory.decodeFile("/sdcard/test.jpg", options); //此时返回bm为空
//        Bitmap bitmap = BitmapFactory.decodeFile(url, options); //此时返回bm为空
//        options.inJustDecodeBounds = false;
//         //计算缩放比
//        int be = (int)(options.outHeight / (float)200);
//        if (be <= 0)
//            be = 1;
//        options.inSampleSize = be;
//        //重新读入图片，注意这次要把options.inJustDecodeBounds 设为 false哦
//        bitmap=BitmapFactory.decodeFile(url, options); //此时返回bm为空
//        File file=new File(Environment.getExternalStorageDirectory(),"test.jpg");
//        try {
//            FileOutputStream out=new FileOutputStream(file);
//            if(bitmap.compress(Bitmap.CompressFormat.JPEG, 30, out)){
//                out.flush();
//                out.close();
//            }
//        } catch (FileNotFoundException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//	}
	
	@Override
	public PluginResult execute(String action, JSONArray filePath, String callbackID) {
		try {
			String sdpath = bitmapToString(filePath.getString(0).toString());
			return new PluginResult(PluginResult.Status.OK, sdpath);
		} catch (Exception e) {
			return new PluginResult(PluginResult.Status.ERROR, "error");
		}
	}
}
