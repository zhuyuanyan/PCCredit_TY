package com.cardpay.banksaler_rocket;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import org.json.JSONObject;
import com.phonegap.DroidGap;
import android.webkit.WebView;
/**
 * Created with IntelliJ IDEA.
 * User: Johnny
 * Date: 13-5-17
 * Time: 上午11:25
 * To change this template use File | Settings | File Templates.
 */
public class GetIdCard extends Activity {
    private WebView mAppView;
    private DroidGap mGap;
    private  static  JSONObject JsonResult;
    private  static  Intent passIntent;

    public GetIdCard(DroidGap gap, WebView view)
    {
        mAppView = view;
        mGap = gap;
    }
    /*
    *函数名:GetIdCardInfo
    * 参数：无
    * 返回值：JSONObject
    * 描述：用来获得IPAD上身份证识别系统的识别结果
     */
    public JSONObject GetIdCardInfo() throws Exception{
        /**
         * 获取身份证识别系统返回信息，存入Intent变量中
         */
        Intent intent = new Intent("com.eshion.readcard");
        startActivityForResult(intent,110);
        /*
        *获得返回数据，用JSON格式打包返回
         */
        JSONObject JsonResult=new JSONObject();
        if (intent != null){
            Bundle b=intent.getExtras();
            int result = b.getInt("result");
            if (result==0){
                JsonResult.put("result","Faild");
                return  JsonResult;
            }
            JsonResult.put("result","Success");
            JsonResult.put("name",b.getString("name"));
            JsonResult.put("sex",b.getString("sex"));
            JsonResult.put("nation",b.getString("nation"));
            JsonResult.put("birthday",b.getString("birthday"));
            JsonResult.put("address",b.getString("address"));
            JsonResult.put("idNum",b.getString("idNum"));
            JsonResult.put("photo",b.getSerializable("photo"));
        }
        return JsonResult;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        passIntent=data;
        super.onActivityResult(requestCode, resultCode, data);
    }
}

