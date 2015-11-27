package com.cardpay.banksaler_rocket;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

public class SaveFileService 
{
	private Context context=null;
	public SaveFileService(Context context)
	{
		this.context=context;
	}
	//保存文件到手机
	public void saveFile(String fileName,byte[] textContent) throws IOException 
	{
		//实例化fileName文件(输出流)对象
		FileOutputStream outStream=context.openFileOutput(fileName, Context.MODE_PRIVATE);
		/*
		 * 操作模式
		 * Context.MODE_PRIVATE：代表该文件是私有数据，只能被应用本身访问
		 * Context.MODE_APPEND：模式会检查文件是否存在，存在就往文件追加内容，否则就创建新文件
		 * MODE_WORLD_READABLE：表示当前文件可以被其他应用读取；
		 * MODE_WORLD_WRITEABLE：表示当前文件可以被其他应用写入。
		 * Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE读和写
		 * Context.MODE_WORLD_READABLE + Context.MODE_WORLD_WRITEABLE+Context.MODE_APPEND读写追加
		 * */
		//写入数据
		outStream.write(textContent);
		//关闭输出流
		outStream.close();
	}
	//从手机读取文件
	public String readFile(String fileName) throws IOException 
	{
		//实例化fileName文件(输入流)对象
		FileInputStream fis=context.openFileInput(fileName);
		//定义byte存储空间
		byte[] b=new byte[1024];
		//定义读取字节长度
		int n=0;
		//实例化字节数组流(可以捕获内存缓冲区的数据，转换成字节数组。)
		ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
		//读取数据读到byte中
		while((n=fis.read(b))!=-1)
		{
			//把数据写到byte中
			byteArrayOutputStream.write(b);
		}
		//重缓冲区中拿取二进制数据并转换成字节数组
		byte content[]=byteArrayOutputStream.toByteArray();
		//返回String
		return new String(content);
	}
	//保存文件到SD卡
	public String saveToSdCard(String filename,byte[] content) throws IOException 
	{
		//得到手机默认存储目录。并实例化
		File file=new File(Environment.getExternalStorageDirectory(),filename);
		FileOutputStream fos=new FileOutputStream(file);
		fos.write(content);
		fos.close();
		return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator + filename;
	}
	//重SD卡中读取文件内容
	public String readContentForSdcard(String filename) throws IOException
	{
		//Environment.getExternalStorageDirectory() 得到外部存储目录
		File file=new File(Environment.getExternalStorageDirectory(),filename);
		FileInputStream sdstream=new FileInputStream(file);
		byte b[]=new byte[1024];
		int n=0;
		ByteArrayOutputStream byteArrayOS=new ByteArrayOutputStream();
		while((n=sdstream.read(b))!=-1)
		{
			byteArrayOS.write(b);
		}
		byte sdContent[]=byteArrayOS.toByteArray();
		return new String(sdContent);
	}
}
