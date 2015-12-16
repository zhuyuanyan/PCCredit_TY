package com.cardpay.pccredit.intopieces.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.dao.LocalExcelDao;
import com.cardpay.pccredit.intopieces.dao.LocalImageDao;
import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.Dcddpz;
import com.cardpay.pccredit.intopieces.model.Dclrjb;
import com.cardpay.pccredit.intopieces.model.Dcsczt;
import com.cardpay.pccredit.intopieces.model.Dzjbzk;
import com.cardpay.pccredit.intopieces.model.Dzjy;
import com.cardpay.pccredit.intopieces.model.Dzjyzt;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.model.LocalImage;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.intopieces.web.AddIntoPiecesForm;
import com.cardpay.pccredit.intopieces.web.LocalExcelForm;
import com.cardpay.pccredit.intopieces.web.LocalImageForm;
import com.cardpay.pccredit.tools.JXLReadExcel;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class AddIntoPiecesService {

	// TODO 路径使用相对路径，首先获得应用所在路径，之后建立上传文件目录，图片类型使用IMG，文件使用DOC

	@Autowired
	private CommonDao commonDao;
	
	@Autowired
	private LocalExcelDao localExcelDao;
	
	@Autowired
	private LocalImageDao localImageDao;
	
	@Autowired
	private CustomerInforService customerInforService;

	
	/* 查询调查报告信息 */
	public QueryResult<LocalExcelForm> findLocalExcelByProductAndCustomer(AddIntoPiecesFilter filter) {
		List<LocalExcelForm> ls = localExcelDao.findByProductAndCustomer(filter);
		int size = localExcelDao.findCountByProductAndCustomer(filter);
		QueryResult<LocalExcelForm> qr = new QueryResult<LocalExcelForm>(size,ls);
		return qr;
	}

	//导入调查报告
	public void importExcel(MultipartFile file,String productId, String customerId) {
		// TODO Auto-generated method stub
		Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file,customerId);
		String fileName = map.get("fileName");
		String url = map.get("url");
		LocalExcel localExcel = new LocalExcel();
		localExcel.setProductId(productId);
		localExcel.setCustomerId(customerId);
		localExcel.setCreatedTime(new Date());
		if (StringUtils.trimToNull(url) != null) {
			localExcel.setUri(url);
		}
		if (StringUtils.trimToNull(fileName) != null) {
			localExcel.setAttachment(fileName);
		}
		
		//读取excel内容
		JXLReadExcel readExcel = new JXLReadExcel();
		String sheet[] = readExcel.readExcelToHtml(url, true);
		for(String str : sheet){
			if(StringUtils.isEmpty(str)){
				throw new RuntimeException("导入失败，请检查excel文件与模板是否一致！");
			}
		}
		localExcel.setSheetJy(sheet[0]);
//		localExcel.setSheetJjbs(sheet[1]);
		localExcel.setSheetJbzk(sheet[1]);
		localExcel.setSheetJyzt(sheet[2]);
		localExcel.setSheetSczt(sheet[3]);
		localExcel.setSheetDdpz(sheet[4]);
		localExcel.setSheetFz(sheet[5]);
		localExcel.setSheetLrjb(sheet[6]);
		localExcel.setSheetBzlrb(sheet[7]);
		localExcel.setSheetXjllb(sheet[8]);
		localExcel.setSheetJc(sheet[9]);
		localExcel.setSheetDhd(sheet[10]);
		localExcel.setSheetGdzc(sheet[11]);
		localExcel.setSheetYfys(sheet[12]);
		localExcel.setSheetYsyf(sheet[13]);
		localExcel.setSheetLsfx(sheet[14]);
		//删除旧模板
		String sql = "delete from local_excel where customer_id='"+customerId+"' and product_id='"+productId+"'";
		commonDao.queryBySql(LocalExcel.class, sql, null);
		//添加模板
		commonDao.insertObject(localExcel);
	}
	
	//补充调查模板先删除原有的调查模板信息再新增
	public void importExcelSupple(MultipartFile file,String productId, String customerId,String appId) {
		// TODO Auto-generated method stub
		Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file,customerId);
		String fileName = map.get("fileName");
		String url = map.get("url");
		//删除
		localImageDao.deleteByProductIdAndCustomerId(productId,customerId);
		
		LocalExcel localExcel = new LocalExcel();
		localExcel.setProductId(productId);
		localExcel.setCustomerId(customerId);
		localExcel.setApplicationId(appId);
		localExcel.setCreatedTime(new Date());
		if (StringUtils.trimToNull(url) != null) {
			localExcel.setUri(url);
		}
		if (StringUtils.trimToNull(fileName) != null) {
			localExcel.setAttachment(fileName);
		}
		
		//读取excel内容
		JXLReadExcel readExcel = new JXLReadExcel();
		String sheet[] = readExcel.readExcelToHtml(url, true);
		for(String str : sheet){
			if(StringUtils.isEmpty(str)){
				throw new RuntimeException("导入失败，请检查excel文件与模板是否一致！");
			}
		}
		localExcel.setSheetJy(sheet[0]);
		localExcel.setSheetJjbs(sheet[1]);
		localExcel.setSheetJbzk(sheet[2]);
		localExcel.setSheetJyzt(sheet[3]);
		localExcel.setSheetSczt(sheet[4]);
		localExcel.setSheetDdpz(sheet[5]);
		localExcel.setSheetFz(sheet[6]);
		localExcel.setSheetLrjb(sheet[7]);
		localExcel.setSheetBzlrb(sheet[8]);
		localExcel.setSheetXjllb(sheet[9]);
		localExcel.setSheetJc(sheet[10]);
		localExcel.setSheetDhd(sheet[11]);
		localExcel.setSheetGdzc(sheet[12]);
		localExcel.setSheetYfys(sheet[13]);
		localExcel.setSheetYsyf(sheet[14]);
		localExcel.setSheetLsfx(sheet[15]);
		
		commonDao.insertObject(localExcel);
		
		//修改进件状态为已申请-audit
		localImageDao.updateCustomerInfoStatus(appId);
		
	}

	/* 查询影像资料信息 */
	public QueryResult<LocalImageForm> findLocalImageByProductAndCustomer(AddIntoPiecesFilter filter) {
		List<LocalImageForm> ls = localImageDao.findByProductAndCustomer(filter);
		int size = localImageDao.findCountByProductAndCustomer(filter);
		QueryResult<LocalImageForm> qr = new QueryResult<LocalImageForm>(size,ls);
		return qr;
	}

	/* 查询影像资料信息 */
	public QueryResult<LocalImageForm> findLocalImageByApplication(AddIntoPiecesFilter filter) {
		List<LocalImageForm> ls = localImageDao.findByApplication(filter);
		int size = localImageDao.findCountByApplication(filter);
		QueryResult<LocalImageForm> qr = new QueryResult<LocalImageForm>(size,ls);
		return qr;
	}
	
	/* 查询影像资料信息 */
	public LocalImage findLocalImageByApplication(String id) {
		LocalImage localImage = localImageDao.findById(id);
		return localImage;
	}
	
	/* 删除资料信息 */
	public void deleteImage(String id) {
		commonDao.deleteObject(LocalImage.class, id);
	}
	
	public void importImage(MultipartFile file, String productId,
			String customerId,String applicationId) {
		// TODO Auto-generated method stub
		Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file,customerId);
		String fileName = map.get("fileName");
		String url = map.get("url");
		LocalImage localImage = new LocalImage();
		localImage.setProductId(productId);
		localImage.setCustomerId(customerId);
		if(applicationId != null){
			localImage.setApplicationId(applicationId);
		}
		localImage.setCreatedTime(new Date());
		if (StringUtils.trimToNull(url) != null) {
			localImage.setUri(url);
		}
		if (StringUtils.trimToNull(fileName) != null) {
			localImage.setAttachment(fileName);
		}
		
		commonDao.insertObject(localImage);
	}

	public void addIntopieces(AddIntoPiecesForm addIntoPiecesForm,String userId) {
		// TODO Auto-generated method stub
		CustomerApplicationInfo app = new CustomerApplicationInfo();
		app.setCustomerId(addIntoPiecesForm.getCustomerId());
		app.setProductId(addIntoPiecesForm.getProductId());
		app.setCreatedBy(userId);
		app.setCreatedTime(new Date());
		app.setStatus(Constant.APPROVE_INTOPICES);
		app.setId(IDGenerator.generateID());
		commonDao.insertObject(app);
		
		//将调查表和影像件 关联到该app
		LocalExcel localExcel = localExcelDao.findById(addIntoPiecesForm.getExcelId());
		localExcel.setApplicationId(app.getId());
		commonDao.updateObject(localExcel);
		List<LocalImage> ls = localImageDao.findAllByProductAndCustomer(addIntoPiecesForm.getProductId(),addIntoPiecesForm.getCustomerId());
		for(LocalImage obj : ls){
			obj.setApplicationId(app.getId());
			commonDao.updateObject(obj);
		}
	}
	
	public LocalExcel findLocalEXcelByApplication(String appId){
		return localExcelDao.findByApplication(appId);
	}

	public void change_localExcel(LocalExcel localExcel) {
		// TODO Auto-generated method stub
		commonDao.updateObject(localExcel);
	}
	
	/**
	 * 下载客户影像资料
	 * @param id
	 * @throws Exception 
	 */
	public void downLoadYxzlById(HttpServletResponse response,String id) throws Exception{
		LocalImage v = commonDao.findObjectById(LocalImage.class, id);
		if(v!=null){
			UploadFileTool.downLoadFile(response, v.getUri(), v.getAttachment());
		}
	}
	
	//save jy
	public void saveJy(Dzjy dzjy){
		commonDao.insertObject(dzjy);
	}
	
	//update jy
    public void updateJy(Dzjy dzjy){
    	commonDao.updateObject(dzjy);
	}
	
	//查询jy
	public Dzjy findLocalImageByApplication(String customerId,String productId) {
		Dzjy dzjy = localImageDao.findJy(customerId,productId);
		return dzjy;
	}
	
	//查询 基本状况
	public Dzjbzk  findDzjbzk(String customerId,String productId){
		Dzjbzk jbzk = localImageDao.findDzjbzk(customerId,productId);
		return jbzk;
	}
	
	//save jbzk
	public void saveJbzk(Dzjbzk jbzk){
		commonDao.insertObject(jbzk);
	}
	
	//update jbzk
	public void updateJbzk(Dzjbzk jbzk){
		commonDao.updateObject(jbzk);
	}
	
	
	//查询经营状态
	public Dzjyzt  findDzjyzt(String customerId,String productId){
		Dzjyzt jyzt = localImageDao.findDzjyzt(customerId,productId);
		return jyzt;
	}
	
	//save 
	public void saveJyzt(Dzjyzt jyzt){
		commonDao.insertObject(jyzt);
	}
	
	//update
	public void updateJyzt(Dzjyzt jyzt){
		commonDao.updateObject(jyzt);
    }
	
	//查询生存状态
	public Dcsczt findDcsczt(String customerId,String productId){
		Dcsczt  sczt = localImageDao.findDcsczt(customerId,productId);
	    return sczt;
	}
	
	//save 
	public void saveSczt(Dcsczt sczt){
		commonDao.insertObject(sczt);
	}
	
	//update
	public void updateSczt(Dcsczt sczt){
		commonDao.updateObject(sczt);
    }
	
	
	//查询道德品质
	public Dcddpz findDcddpz(String customerId,String productId){
		Dcddpz  ddpz = localImageDao.findDcddpz(customerId,productId);
	    return ddpz;
	}
	
	//save 
	public void saveDdpz(Dcddpz ddpz){
		commonDao.insertObject(ddpz);
	}
	
	//update
	public void updateDdpz(Dcddpz ddpz){
		commonDao.updateObject(ddpz);
    }
	
	//查询利润简表
	public Dclrjb findDclrjb(String customerId,String productId){
		Dclrjb  lrjb = localImageDao.findDclrjb(customerId,productId);
	    return lrjb;
	}
	//save
	public void saveDclrjb(Dclrjb lrjb){
		commonDao.insertObject(lrjb);
	}
	
	//update
	public void updateDclrjb(Dclrjb lrjb){
		commonDao.updateObject(lrjb);
	}
}
