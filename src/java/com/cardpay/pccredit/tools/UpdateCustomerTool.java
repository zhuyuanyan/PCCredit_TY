package com.cardpay.pccredit.tools;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.SftpException;

/** 
 * @author 贺珈 
 * @version 创建时间：2015年8月19日 下午4:16:36 
 * 每日更新客户原始数据
 */
@Service
public class UpdateCustomerTool {
	public Logger log = Logger.getLogger(UpdateCustomerTool.class);
	
	public String curRemotePath = "";//本次下载服务器目录
	private String[] fileName = {"kkh_grxx.zip","kkh_grjtcy.zip","kkh_grjtcc.zip","kkh_grscjy.zip","kkh_grxxll.zip","kkh_grgzll.zip","kkh_grrbxx.zip","kdk_yehz.zip","kdk_lsz.zip","kdk_tkmx.zip","cxd_dkcpmc.zip","kkh_hmdgl.zip"};
	//客户原始信息
	private String[] fileTxt = {"kkh_grxx.txt","kkh_grjtcy.txt","kkh_grjtcc.txt","kkh_grscjy.txt","kkh_grxxll.txt","kkh_grgzll.txt","kkh_grrbxx.txt"};
	//流水账、余额汇总表、借据表
	private String[] fileTxtRepay ={"kdk_yehz.txt","kdk_lsz.txt","kdk_tkmx.txt"};
	//产品信息、黑名单
	private String[] fileTxtProduct ={"cxd_dkcpmc.txt","kkh_hmdgl.txt"};
	@Autowired
	private CustomerInforService customerInforService;
	
	@Scheduled(cron = "0 08 16 * * ?")
	public void downloadFiles(){
		log.error("下载文件：");
		CardFtpUtils sftp = new CardFtpUtils();
		try {
			sftp.connect();
			curRemotePath = CardFtpUtils.bank_ftp_path;//上级目录
			//获取今日日期
	      //yyyyMMdd格式
			DateFormat format = new SimpleDateFormat("yyyyMMdd");
			String dateString = format.format(new Date());
			curRemotePath = curRemotePath+File.separator+dateString;
			//获取文件列表
			ArrayList<String> files = sftp.getList(curRemotePath);
			// 处理ftp文件
			processFtpFile(sftp, files);
			
		} catch (JSchException e) {
			e.printStackTrace();
		} catch (SftpException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally{
			if(sftp != null){
				sftp.close();
				System.out.println("success,,/////");
			}
		}

	}
	
	/**
	 * 处理ftp文件
	 * @return
	 * @throws Exception 
	 */
	public void processFtpFile(CardFtpUtils sftp, ArrayList<String> files) throws Exception{
		Iterator<String> pathIterator = files.iterator();
		//获取今日日期
	      //yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		// 下载路径
		String downloadPath =  CardFtpUtils.bank_ftp_down_path;
		downloadPath = downloadPath+File.separator+dateString;
		downloadPath = URLDecoder.decode(downloadPath, "utf-8");
		 File url = new File(downloadPath);
		 //本地创建当日数据文件夹
        if(!url.exists()){ 
        	url.mkdirs();  
        }
		while(pathIterator.hasNext()){
			String file = pathIterator.next();
			try{
				for(int i=0;i<fileName.length;i++){
					if(file.indexOf(fileName[i])>-1){
						//下载文件
						if(sftp.download(curRemotePath, file, downloadPath)){
							log.error("下载文件" + file + "成功");
						}else{
							log.error("下载文件" + file + "失败");
						}
						
					}
				}
			}catch(Exception e){
				log.error("处理文件" + file + "出错", e);
			}
		}
		log.error(dateString+"******************开始解压********************");  
		String gzFile = CardFtpUtils.bank_ftp_down_path+dateString;
		for(int i=0;i<fileName.length;i++){
			String url1 = gzFile+File.separator+fileName[i];
			File fileUrl = new File(url1);
			if(fileUrl.exists()){
				ZipFile zip = new ZipFile(url1);  
				for(Enumeration entries = zip.getEntries();entries.hasMoreElements();){
					ZipEntry entry = (ZipEntry)entries.nextElement();  
					String zipEntryName = entry.getName();  
					InputStream in = zip.getInputStream(entry);  
					String outPath = (gzFile+File.separator+zipEntryName).replaceAll("\\*", "/");
					//判断路径是否存在,不存在则创建文件路径  
					File file = new File(outPath.substring(0, outPath.lastIndexOf('/')));  
					if(!file.exists()){  
						file.mkdirs();  
					}  
					//判断文件全路径是否为文件夹,如果是上面已经上传,不需要解压  
					if(new File(outPath).isDirectory()){  
						continue;  
					}  
					
					OutputStream out = new FileOutputStream(outPath);  
					byte[] buf1 = new byte[1024];  
					int len;  
					while((len=in.read(buf1))>0){
						out.write(buf1,0,len);  
					}  
					in.close();  
					out.close();         
					zip.close();
				}
				//删除压缩包
				fileUrl.delete();
			}
			
		}
		log.error(dateString+"******************解压完毕********************");  
	}
	
	
	/**
	 * 解析（原始信息）
	 * @throws IOException 
	 */
	@Scheduled(cron = "0 12 17 * * ?")
	private void readFile() throws IOException{
		//获取今日日期
	      //yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		log.error(dateString+"******************开始读取原始信息文件********************");  
	        String gzFile = CardFtpUtils.bank_ftp_down_path+dateString;
	        for(int i=0;i<fileTxt.length;i++){
				String url = gzFile+File.separator+fileTxt[i];
				File f = new File(url);
				if(f.exists()){
						List<String> spFile = new ArrayList<String>();
						String fileN = "";
						//判断文件大小，超过50M的先分割
						if (f.exists() && f.isFile()){
							if(f.length()>40000000){
								int spCount = (int) (f.length()/40000000);
								SPTxt.splitTxt(url,spCount);
								int to = fileTxt[i].lastIndexOf('.');
						    	fileN = fileTxt[i].substring(0, to);
								for(int j=0;j<spCount;j++){
									spFile.add(fileN+"_"+j+".txt");
								}
							}else{
								int to = fileTxt[i].lastIndexOf('.');
						    	fileN = fileTxt[i].substring(0, to);
								spFile.add(fileN+".txt");
							}
						}
						for(String fn : spFile){
							try{
								if(fn.contains(fileN)) {
									if(fn.startsWith("kkh_grxx")){
										log.error("*****************客户基本表********************");  
										customerInforService.saveBaseDataFile(gzFile+File.separator+fn);
									}
									if(fn.startsWith("kkh_grjtcy")){
										log.error("*****************客户家庭关系表********************");  
										customerInforService.saveCyDataFile(gzFile+File.separator+fn);
									}
									if(fn.startsWith("kkh_grjtcc")){
										log.error("*****************客户家庭财产表********************");  
										customerInforService.saveCcDataFile(gzFile+File.separator+fn);
									}
									if(fn.startsWith("kkh_grxxll")){
										log.error("*****************客户学习表********************");  
										customerInforService.saveStudyDataFile(gzFile+File.separator+fn);
									}
									if(fn.startsWith("kkh_grgzll")){
										log.error("*****************客户工作履历表********************");  
										customerInforService.saveWorkDataFile(gzFile+File.separator+fn);
									}
									if(fn.startsWith("kkh_grscjy")){
										log.error("*****************客户生产经营表********************");  
									customerInforService.saveManageDataFile(gzFile+File.separator+fn);
								}
									if(fn.startsWith("kkh_grrbxx")){
										log.error("*****************客户入保信息表********************");  
										customerInforService.saveSafeDataFile(gzFile+File.separator+fn);
									}
								} 
							}catch(Exception e){
								e.printStackTrace();
								throw new RuntimeException(e);
							}
						}
				}
	        }
	        log.error(dateString+"******************完成读取原始信息文件********************");

	}
	
	
	/**
	 *解析贷款信息
	 * @throws IOException 
	 */
	@Scheduled(cron = "0 20 17 * * ?")
	private void readFileRepay() throws IOException{
		//获取今日日期
	      //yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		String gzFile = CardFtpUtils.bank_ftp_down_path+dateString;

		log.error(dateString+"******************开始读取贷款文件********************");  
        for(int i=0;i<fileTxtRepay.length;i++){
			String url = gzFile+File.separator+fileTxtRepay[i];
			File f = new File(url);
			if(f.exists()){
					List<String> spFile = new ArrayList<String>();
					String fileN = "";
					//判断文件大小，超过50M的先分割
					if (f.exists() && f.isFile()){
						if(f.length()>40000000){
							int spCount = (int) (f.length()/40000000);
							SPTxt.splitTxt(url,spCount);
							int to = fileTxtRepay[i].lastIndexOf('.');
					    	fileN = fileTxtRepay[i].substring(0, to);
							for(int j=0;j<spCount;j++){
								spFile.add(fileN+"_"+j+".txt");
							}
						}else{
							int to = fileTxtRepay[i].lastIndexOf('.');
					    	fileN = fileTxtRepay[i].substring(0, to);
							spFile.add(fileN+".txt");
						}
					}
					for(String fn : spFile){
						try{
							if(fn.contains(fileN)) {
								if(fn.startsWith("kdk_lsz")){
									log.error("*****************流水账信息********************");  
									customerInforService.saveLSZDataFile(gzFile+File.separator+fn);
								}else if(fn.startsWith("kdk_yehz")){
									log.error("*****************余额汇总信息********************");  
									customerInforService.saveYEHZDataFile(gzFile+File.separator+fn);
								}else if(fn.startsWith("kdk_tkmx")){
									log.error("*****************借据表信息********************");  
									customerInforService.saveTKMXDataFile(gzFile+File.separator+fn);
								}
							} 
						}catch(Exception e){
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					}
					f.delete();
			}
        }
        log.error(dateString+"******************完成读取贷款文件********************");

	}
	
	/**
	 *解析产品信息
	 * @throws IOException 
	 */
	@Scheduled(cron = "0 30 17 * * ?")
	private void readFileProduct() throws IOException{
		//获取今日日期
	      //yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		String gzFile = CardFtpUtils.bank_ftp_down_path+dateString;

		log.error("******************开始读取产品文件********************");  
        for(int i=0;i<fileTxtProduct.length;i++){
			String url = gzFile+File.separator+fileTxtProduct[i];
			File f = new File(url);
			if(f.exists()){
					List<String> spFile = new ArrayList<String>();
					String fileN = "";
					//判断文件大小，超过50M的先分割
					if (f.exists() && f.isFile()){
						if(f.length()>40000000){
							int spCount = (int) (f.length()/40000000);
							SPTxt.splitTxt(url,spCount);
							int to = fileTxtProduct[i].lastIndexOf('.');
					    	fileN = fileTxtProduct[i].substring(0, to);
							for(int j=0;j<spCount;j++){
								spFile.add(fileN+"_"+j+".txt");
							}
						}else{
							int to = fileTxtProduct[i].lastIndexOf('.');
					    	fileN = fileTxtProduct[i].substring(0, to);
							spFile.add(fileN+".txt");
						}
					}
					for(String fn : spFile){
						try{
							if(fn.contains(fileN)) {
								if(fn.startsWith("cxd_dkcpmc")){
									log.error("*****************产品信息********************");  
									customerInforService.saveProductDataFile(gzFile+File.separator+fn);
								}else if(fn.startsWith("kkh_hmdgl")){
									log.error("*****************黑名单********************");  
									customerInforService.saveHMDDataFile(gzFile+File.separator+fn);
								}
							} 
						}catch(Exception e){
							e.printStackTrace();
							throw new RuntimeException(e);
						}
					}
					f.delete();
			}
        }
        log.error("******************完成读取产品文件********************");

	}
	public static void main(String[] args) throws Exception{
		UpdateCustomerTool tool = new UpdateCustomerTool();
		try {
			CardFtpUtils sftp = new CardFtpUtils();
			ArrayList<String> files = null;
			tool.readFileProduct();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
