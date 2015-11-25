package com.cardpay.pccredit.customer.service;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.common.Dictionary;
import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.constant.CustomerInforDStatusEnum;
import com.cardpay.pccredit.customer.constant.WfProcessInfoType;
import com.cardpay.pccredit.customer.dao.CustomerInforDao;
import com.cardpay.pccredit.customer.dao.comdao.CustomerInforCommDao;
import com.cardpay.pccredit.customer.filter.CustomerInfoLszFilter;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.filter.VideoAccessoriesFilter;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBase;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBaseLocal;
import com.cardpay.pccredit.customer.model.CustomerFirsthendFamilyCc;
import com.cardpay.pccredit.customer.model.CustomerFirsthendFamilyCy;
import com.cardpay.pccredit.customer.model.CustomerFirsthendManage;
import com.cardpay.pccredit.customer.model.CustomerFirsthendSafe;
import com.cardpay.pccredit.customer.model.CustomerFirsthendStudy;
import com.cardpay.pccredit.customer.model.CustomerFirsthendWork;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerInforWeb;
import com.cardpay.pccredit.customer.model.MaintenanceLog;
import com.cardpay.pccredit.customer.model.TyProductType;
import com.cardpay.pccredit.customer.model.TyRepayLsz;
import com.cardpay.pccredit.customer.model.TyRepayTkmx;
import com.cardpay.pccredit.customer.model.TyRepayYehz;
import com.cardpay.pccredit.customer.model.TyRepayYehzVo;
import com.cardpay.pccredit.customer.web.MaintenanceForm;
import com.cardpay.pccredit.datapri.service.DataAccessSqlService;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.constant.IntoPiecesException;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationProcess;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.ipad.model.ProductAttribute;
import com.cardpay.pccredit.riskControl.model.RiskCustomer;
import com.cardpay.pccredit.riskControl.xml.XmlUtil;
import com.cardpay.pccredit.system.constants.NodeAuditTypeEnum;
import com.cardpay.pccredit.system.constants.YesNoEnum;
import com.cardpay.pccredit.system.model.Dict;
import com.cardpay.pccredit.system.model.NodeAudit;
import com.cardpay.pccredit.system.model.NodeControl;
import com.cardpay.pccredit.system.model.SystemUser;
import com.cardpay.pccredit.system.service.NodeAuditService;
import com.cardpay.pccredit.tools.CardFtpUtils;
import com.cardpay.pccredit.tools.DataFileConf;
import com.cardpay.pccredit.tools.ImportBankDataFileTools;
import com.cardpay.pccredit.tools.SPTxt;
import com.cardpay.pccredit.tools.UpdateCustomerTool;
import com.cardpay.workflow.models.WfProcessInfo;
import com.cardpay.workflow.models.WfStatusInfo;
import com.cardpay.workflow.models.WfStatusResult;
import com.cardpay.workflow.service.ProcessService;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.modules.privilege.model.User;

/**
 * 
 * @author shaoming
 *
 */
@Service
public class CustomerInforService {
	public Logger log = Logger.getLogger(UpdateCustomerTool.class);
	@Autowired
	private DataAccessSqlService dataAccessSqlService;

	@Autowired
	private CommonDao commonDao;

	@Autowired
	private CustomerInforDao customerInforDao;
	
	@Autowired
	private CustomerInforCommDao customerinforcommDao;
	
	@Autowired
	private NodeAuditService nodeAuditService;
	
	@Autowired
	private ProcessService processService;
	//客户原始信息
	private String[] fileTxt = {"kkh_grxx.txt","kkh_grjtcy.txt","kkh_grjtcc.txt","kkh_grscjy.txt","kkh_grxxll.txt","kkh_grgzll.txt","kkh_grrbxx.txt","cxd_dkcpmc.txt","kkh_hmdgl.txt"};
	//流水账、余额汇总表、借据表
	private String[] fileTxtRepay ={"kdk_yehz.txt","kdk_lsz.txt","kdk_tkmx.txt"};
	/**
	 * 得到该客户经理下的客户数量
	 * @param userId
	 * @return
	 */
	public int findCustomerInforCountByUserId(String userId){
		return customerInforDao.findCustomerInforCountByUserId(userId);
	}
	/**
	 * 查询name
	 * 
	 */
	
	public List<CustomerInfor> findCustomerInforByName(String userId,String name){
		return customerInforDao.findCustomerInforByName(userId,name);
	}
	
	/**
	 * 根据证件号码查询
	 * 
	 */
	public CustomerInfor findCustomerInforByCardId(String cardId){
		return customerinforcommDao.findCustomerInforByCardId(cardId);
	}
	/**
	 * 插入数据
	 * @param customerinfo
	 * @return
	 */
	public String insertCustomerInfor(CustomerInfor customerinfor) {
		String id = IDGenerator.generateID();
		customerinfor.setId(id);
		customerinfor.setCreatedTime(new Date());
		commonDao.insertObject(customerinfor);
		return customerinfor.getId();
	}
	/**
	 * 过滤查询
	 * @param filter
	 * @return
	 */
	public QueryResult<CustomerInfor> findCustomerInforByFilter(CustomerInforFilter filter) {
		/*filter.setSqlString(dataAccessSqlService.getSqlByResTbl(filter.getRequest(), ResourceTableEnum.KEHU));*/
		List<CustomerInfor> ls = customerInforDao.findCustomerOriginaList(filter);
		int size = customerInforDao.findCustomerOriginaCountList(filter);
		QueryResult<CustomerInfor> qr = new QueryResult<CustomerInfor>(size,ls);
		return qr;
	}
	
	public int findCustomerOriginaCountList(CustomerInforFilter filter){
		return customerInforDao.findCustomerOriginaCountList(filter);
	}
	
	//查询未办理过该产品的客户
	public QueryResult<CustomerInfor> findCustomerInforByFilterAndProductId(CustomerInforFilter filter) {
		List<CustomerInfor> ls = customerInforDao.findCustomerInforByFilterAndProductId(filter);
		int size = customerInforDao.findCustomerInforCountByFilterAndProductId(filter);
		QueryResult<CustomerInfor> qr = new QueryResult<CustomerInfor>(size,ls);
		return qr;
	}
	
	/**
	 * 过滤查询影像资料
	 * @param filter
	 * @return
	 */
	public QueryResult<VideoAccessories> findCustomerVideoAccessoriesByFilter(VideoAccessoriesFilter filter) {
		return commonDao.findObjectsByFilter(VideoAccessories.class, filter);
	}
	
	
	
	/**
	 * 按id查找相应的客户基本信息
	 * @param customerInforId
	 * @return
	 */
	public CustomerInfor findCustomerInforById(String customerInforId){
		return commonDao.findObjectById(CustomerInfor.class, customerInforId);
	}
	
	/**
	 * 按经理Id查找相应的客户基本信息
	 * @param customerInforId
	 * @return
	 */
	public  List<CustomerInfor> findCustomerByManagerId(String managerId){
		
		List<CustomerInfor> customerInfors = customerinforcommDao.findCustomerByManagerId(managerId);
           return customerInfors;
	}
	
	/**
	 * 修改客户信息
	 * @param customerInfor
	 * @return
	 */
	public int updateCustomerInfor(CustomerInfor customerInfor) {
		customerInfor.setModifiedTime(Calendar.getInstance().getTime());
		return commonDao.updateObject(customerInfor);
	}
	/**
	 * 删除所属id的客户基本信息
	 * @param customerinforId
	 * @return
	 */
	public int deleteCustomerInfor(String customerinforId){
		return commonDao.deleteObject(CustomerInfor.class, customerinforId);
	}
    //TODO 以下数条获得单一属性方法建议可重构，保留也可
	/**
	 * 获取国籍
	 * @return
	 */
	public List<Dict> findNationality(){
		List<Dict> nationalities = customerInforDao.findNationality();
		return nationalities;
	}
	/**
	 * 获取证件类型
	 * @return
	 */
	public List<Dict> findCardType(){
		List<Dict> cardtypes = customerInforDao.findCardType();
		return cardtypes;
	}
	public String findTypeNameByTypeCode(String typecode){
		return customerInforDao.findTypeNameByTypeCode(typecode);
	}
	/**
	 * 获取婚姻状况
	 * @return
	 */
	public List<Dict> findMaritalStatus(){
		List<Dict> maritalstatuses = customerInforDao.findMaritalStatus();
		return maritalstatuses;
	}
	/**
	 * 获取住宅类型
	 * @return
	 */
	public List<Dict> findResidentialPropertie(){
		List<Dict> residentialproperties = customerInforDao.findResidentialPropertie();
		return residentialproperties;
	}
	
	/**
	 * 获取客户账户信息中的在我行开户情况
	 * @return
	 */
	public List<Dict> findOaccountMybankList(){
		List<Dict> oaccountMybanks = customerinforcommDao.findOaccountMybankList();
		return oaccountMybanks;
	}
	
	/**
	 * 获取客户账户持卡情况
	 * @return
	 */
	public List<Dict> findCreditCardList(){
		List<Dict> oaccountMybanks = customerinforcommDao.findCreditCardList();
		return oaccountMybanks;
	}
	
	/**
	 * 获取客户在我行发工资情况
	 * @return
	 */
	public List<Dict> findPayMybankList(){
		List<Dict> oaccountMybanks = customerinforcommDao.findPayMybankList();
		return oaccountMybanks;
	}
	
	/**
	 * 获取教育程度
	 * @return
	 */
	public List<Dict> findDegreeeducationList(){
		List<Dict> degreeeducationList = customerinforcommDao.findDegreeeducationList();
		return degreeeducationList;
	}
	
	/**
	 * 获取扣款方式
	 * @return
	 */
	public List<Dict> debitWayList(){
		List<Dict> debitWayList = customerinforcommDao.findDegreeeducationList();
		return debitWayList;
	}
	
	/**
	 * 获取性别
	 * @return
	 */
	public List<Dict> findSex(){
		List<Dict> sexs = customerInforDao.findSex();
		return sexs;
	}
	/**
	 * 获取职务
	 * @return
	 */
	public List<Dict> findPositio(){
		return customerInforDao.findPositio();
	}
	/**
	 * 获取职称
	 * @return
	 */
	public List<Dict> findTitle(){
		return customerInforDao.findTitle();
	}
	/**
	 * 获取单位性质
	 * @return
	 */
	public List<Dict> findUnitPropertis(){
		List<Dict> unitPropertis = customerInforDao.findUnitPropertis();
		return unitPropertis;
	}
	/**
	 * 获得行业类型
	 * @return
	 */
	public List<Dict> findIndustryType(){
		List<Dict> industryTypes = customerInforDao.findIndustryType();
		return industryTypes;
	}
	/**
	 * 获得催收方式
	 * @return
	 */
	public List<Dict> findCollectionMethod(){
		List<Dict> collectionMethods = customerInforDao.findCollectionMethod();
		return collectionMethods;
	}
	/**
	 * 获取国籍、证件类型、婚姻状况、住宅类型字段
	 * @return
	 */
	public HashMap<String,List<Dict>> findDict(){
		HashMap<String,List<Dict>> dicts = new HashMap<String,List<Dict>>();
		List<Dict> nationalities = findNationality();
		List<Dict> cardtypes = findCardType();
		List<Dict> maritalstatuses = findMaritalStatus();
		List<Dict> residentialproperties = findResidentialPropertie();
		dicts.put("nationality", nationalities);
		dicts.put("cardtype", cardtypes);
		dicts.put("maritalstatus", maritalstatuses);
		dicts.put("residentialpropertie", residentialproperties);
		dicts.put("sex", this.findSex());
		dicts.put("unitPropertis", this.findUnitPropertis());
		return dicts;
	}	
	/**
	 * 得到页面客户显示信息
	 * @param id
	 * @return
	 */
	public CustomerInforWeb findCustomerInforWebById(String id){
		return customerInforDao.findCustomerInforWebById(id);
	}
	
	/* 批量导入客户数据 excel2003或者excel2007格式*/
	public void importBatchCustomerInfoByExcel(MultipartFile file,String userId) throws IOException,IntoPiecesException, ParseException {
		String fileName = file.getOriginalFilename();
		List<BusinessModel> customerInforList = new ArrayList<BusinessModel>();
		List<BusinessModel> customerCareersInformationList = new ArrayList<BusinessModel>();
		List<BusinessModel> customerApplicationInfoList = new ArrayList<BusinessModel>();
		List<BusinessModel> customerApplicationGuarantorList = new ArrayList<BusinessModel>();
		List<BusinessModel> customerApplicationContactList = new ArrayList<BusinessModel>();
		List<BusinessModel> customerApplicationRecomList = new ArrayList<BusinessModel>();
		List<BusinessModel> customerApplicationOtherList = new ArrayList<BusinessModel>();
		HashMap<String,List<Dict>> dicts  = this.findDict();
		List<ProductAttribute> productAttributeList = commonDao.queryBySql(ProductAttribute.class, "select * from PRODUCT_ATTRIBUTE", null);
		if(fileName.endsWith(CustomerInforConstant.EXCEL_2003)){
			HSSFWorkbook hssfWorkbook = new HSSFWorkbook(file.getInputStream());
			/*循环工作表Sheet*/
			for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
				HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
				if (hssfSheet == null) {
					continue;
				}
				switch(numSheet){
					case 0:this.getExcel2003Content(hssfSheet,numSheet,customerInforList);break;
					case 1:this.getExcel2003Content(hssfSheet,numSheet,customerCareersInformationList);break;
					case 2:this.getExcel2003Content(hssfSheet,numSheet,customerApplicationInfoList);break;
					case 3:this.getExcel2003Content(hssfSheet,numSheet,customerApplicationGuarantorList);break;
					case 4:this.getExcel2003Content(hssfSheet,numSheet,customerApplicationContactList);break;
					case 5:this.getExcel2003Content(hssfSheet,numSheet,customerApplicationRecomList);break;
					case 6:this.getExcel2003Content(hssfSheet,numSheet,customerApplicationOtherList);break;
					default:break;
				}
			}
		}else if(fileName.endsWith(CustomerInforConstant.EXCEL_2007)){
			XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file.getInputStream());
			/*循环工作表Sheet*/
			for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
				XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
				if (xssfSheet == null) {
					continue;
				}
				switch(numSheet){
				case 0:this.getExcel2007Content(xssfSheet,numSheet,customerInforList);break;
				case 1:this.getExcel2007Content(xssfSheet,numSheet,customerCareersInformationList);break;
				case 2:this.getExcel2007Content(xssfSheet,numSheet,customerApplicationInfoList);break;
				case 3:this.getExcel2007Content(xssfSheet,numSheet,customerApplicationGuarantorList);break;
				case 4:this.getExcel2007Content(xssfSheet,numSheet,customerApplicationContactList);break;
				case 5:this.getExcel2007Content(xssfSheet,numSheet,customerApplicationRecomList);break;
				case 6:this.getExcel2007Content(xssfSheet,numSheet,customerApplicationOtherList);break;
				default:break;
			   }
			}
		}else{
			throw new IntoPiecesException(CustomerInforConstant.EXCEL_FORMAT_ERROR);
		}
		
		/*如果excel中有重复的数据抛出提示信息*/ 
		for(int i=0;i<customerInforList.size();i++){
			CustomerInfor customerInforOuter = (CustomerInfor)customerInforList.get(i);
			if(StringUtils.trimToNull(customerInforOuter.getCode())==null){
				throw new IntoPiecesException("客户基本信息中编号不能为空");
			}
			if(StringUtils.trimToNull(customerInforOuter.getChineseName())==null){
				throw new IntoPiecesException("客户基本信息中姓名不能为空");
			}
			if(StringUtils.trimToNull(customerInforOuter.getCardType())==null){
				throw new IntoPiecesException("客户基本信息中证件类型不能为空");
			}
			if(StringUtils.trimToNull(customerInforOuter.getCardId())==null){
				throw new IntoPiecesException("客户基本信息中证件号码不能为空");
			}
			String outerString = StringUtils.trim(customerInforOuter.getCardType())+StringUtils.trim(customerInforOuter.getCardId());
			for(int j=i+1;j<customerInforList.size();j++){
				CustomerInfor customerInforInner = (CustomerInfor)customerInforList.get(j);
				String innerString = StringUtils.trim(customerInforInner.getCardType())+StringUtils.trim(customerInforInner.getCardId());
				if(outerString.equalsIgnoreCase(innerString)){
					throw new IntoPiecesException("客户基本信息中："+customerInforInner.getChineseName()+"的证件类型和证件号码与别人重复!");
				}
			}
		}
		
		/*验证申请表编号是否为空*/
		for(int i=0;i<customerInforList.size();i++){
			CustomerInfor customerInfor = (CustomerInfor)customerInforList.get(i);
			if(StringUtils.trimToNull(customerInfor.getCode())==null){
				throw new IntoPiecesException("客户申请信息中编号不能为空!");
			}
			CustomerApplicationInfo customerApplicationInfo = (CustomerApplicationInfo)customerApplicationInfoList.get(i);
			customerApplicationInfo.setCustomerId(customerInfor.getId());
		}
		
		/*剔除职业表中客户编号跟客户基本信息表中编号不匹配的数据*/
		for(int i=0;i<customerCareersInformationList.size();i++){
			int count = 0;
			String customerId = null;
			CustomerCareersInformation customerCareersInformation = (CustomerCareersInformation)customerCareersInformationList.get(i);
			if(StringUtils.trimToNull(customerCareersInformation.getCustomerCode())==null){
				throw new IntoPiecesException("客户职业资料中客户编号不能为空 !");
			}
			for(int j=0;j<customerInforList.size();j++){
				CustomerInfor customerInfor =(CustomerInfor)customerInforList.get(j);
				if(!customerCareersInformation.getCustomerCode().equalsIgnoreCase(customerInfor.getCode())){
					count++;
				}else{
					customerId = customerInfor.getId();
			    	break;
			    }
			}
			if(count==customerInforList.size()){
				throw new IntoPiecesException("客户职业信息表中客户编号为"+customerCareersInformation.getCustomerCode()+"与客户信息表的编号匹配不上请检查!");
			}else{
				customerCareersInformation.setCustomerId(customerId);
				customerCareersInformation.setCustomerCode(null);
			}
		}
		/*剔除担保人,联系人,推荐人,其他资料中进件编号跟申请表信息不相符合的数据*/
		for(int i=0;i<customerApplicationGuarantorList.size();i++){
			int count = 0;
			String applicationId = null;
			CustomerApplicationGuarantor customerApplicationGuarantor = (CustomerApplicationGuarantor)customerApplicationGuarantorList.get(i);
			if(StringUtils.trimToNull(customerApplicationGuarantor.getMainApplicationFormCode())==null){
				throw new IntoPiecesException("担保人资料申请表编号不能为空 !");
			}
			for(int j=0;j<customerApplicationInfoList.size();j++){
				CustomerApplicationInfo customerApplicationInfo = (CustomerApplicationInfo)customerApplicationInfoList.get(j);
			    if(!customerApplicationGuarantor.getMainApplicationFormCode().equalsIgnoreCase(customerApplicationInfo.getCode())){
			    	count++;
			    }else{
			    	applicationId = customerApplicationInfo.getId();
			    	break;
			    }
			}
			if(count==customerApplicationInfoList.size()){
				throw new IntoPiecesException("担保人信息表中申请件编号为:"+customerApplicationGuarantor.getMainApplicationFormCode()+"与申请信息表中的编号匹配不上请检查!");
			}else{
				customerApplicationGuarantor.setMainApplicationFormId(applicationId);
				customerApplicationGuarantor.setMainApplicationFormCode(null);
			}
		}
		
		/*联系人*/
		for(int i=0;i<customerApplicationContactList.size();i++){
			int count = 0;
			String applicationId = null;
			CustomerApplicationContact customerApplicationContact = (CustomerApplicationContact)customerApplicationContactList.get(i);
			if(StringUtils.trimToNull(customerApplicationContact.getMainApplicationFormCode())==null){
				throw new IntoPiecesException("联系人资料申请表编号不能为空 !");
			}
			for(int j=0;j<customerApplicationInfoList.size();j++){
				CustomerApplicationInfo customerApplicationInfo = (CustomerApplicationInfo)customerApplicationInfoList.get(j);
			    if(!customerApplicationContact.getMainApplicationFormCode().equalsIgnoreCase(customerApplicationInfo.getCode())){
			    	count++;
			    }else{
			    	applicationId = customerApplicationInfo.getId();
			    	break;
			    }
			}
			if(count==customerApplicationInfoList.size()){
				throw new IntoPiecesException("联系人信息表中申请件编号为:"+customerApplicationContact.getMainApplicationFormCode()+"与申请信息表中的编号匹配不上请检查!");
			}else{
				customerApplicationContact.setMainApplicationFormId(applicationId);
				customerApplicationContact.setMainApplicationFormCode(null);
			}
		}
		
		/*推荐人*/
		for(int i=0;i<customerApplicationRecomList.size();i++){
			int count = 0;
			String applicationId = null;
			CustomerApplicationRecom customerApplicationRecom = (CustomerApplicationRecom)customerApplicationRecomList.get(i);
			if(StringUtils.trimToNull(customerApplicationRecom.getMainApplicationFormCode())==null){
				throw new IntoPiecesException("推荐人资料申请表编号不能为空 !");
			}
			for(int j=0;j<customerApplicationInfoList.size();j++){
				CustomerApplicationInfo customerApplicationInfo = (CustomerApplicationInfo)customerApplicationInfoList.get(j);
			    if(!customerApplicationRecom.getMainApplicationFormCode().equalsIgnoreCase(customerApplicationInfo.getCode())){
			    	count++;
			    }else{
			    	applicationId = customerApplicationInfo.getId();
			    	break;
			    }
			}
			if(count==customerApplicationInfoList.size()){
				throw new IntoPiecesException("推荐人信息表中申请件编号为:"+customerApplicationRecom.getMainApplicationFormCode()+"与申请信息表中的编号匹配不上请检查!");
			}else{
				customerApplicationRecom.setMainApplicationFormId(applicationId);
				customerApplicationRecom.setMainApplicationFormCode(null);
			}
		}
		
		/*其他*/
		for(int i=0;i<customerApplicationOtherList.size();i++){
			int count = 0;
			String applicationId = null;
			CustomerApplicationOther customerApplicationOther = (CustomerApplicationOther)customerApplicationOtherList.get(i);
			if(StringUtils.trimToNull(customerApplicationOther.getMainApplicationFormCode())==null){
				throw new IntoPiecesException("其他资料申请表编号不能为空 !");
			}
			for(int j=0;j<customerApplicationInfoList.size();j++){
				CustomerApplicationInfo customerApplicationInfo = (CustomerApplicationInfo)customerApplicationInfoList.get(j);
			    if(!customerApplicationOther.getMainApplicationFormCode().equalsIgnoreCase(customerApplicationInfo.getCode())){
			    	count++;
			    }else{
			    	applicationId = customerApplicationInfo.getId();
			    	break;
			    }
			}
			if(count==customerApplicationInfoList.size()){
				throw new IntoPiecesException("其他信息表中申请件编号为:"+customerApplicationOther.getMainApplicationFormCode()+"与申请信息表中的编号匹配不上请检查!");
			}else{
				customerApplicationOther.setMainApplicationFormId(applicationId);
				customerApplicationOther.setMainApplicationFormCode(null);
			}
		}
		
		/*检查重复上传的数据(与数据库对比)*/
		List<String> allCustorInfo = this.checkCustomerInfo();
		for(int i=0;i<customerInforList.size();i++){
			boolean isRemove = false;
			CustomerInfor obj = (CustomerInfor)customerInforList.get(i);
			if(allCustorInfo!=null&&!allCustorInfo.isEmpty()){
				for(String str :allCustorInfo){
					if(str.equalsIgnoreCase(StringUtils.trim(obj.getCardType())+StringUtils.trim(obj.getCardId()))){
						isRemove = true;
						break;
					}
				}
			}
			if(isRemove){
				throw new IntoPiecesException("系统检测到"+obj.getChineseName()+"的证件类型和证件号码已经在系统中存在,请勿重复上传!");
			}
		}
		
		for(BusinessModel obj : customerInforList){
			CustomerInfor customerInfor = (CustomerInfor)obj;
			customerInfor.setCreatedBy(userId);
			customerInfor.setCreatedTime(new Date());
			if(customerInfor!=null&&StringUtils.trimToNull(customerInfor.getBirthday())!=null){
				customerInfor.setBirthday(customerInfor.getBirthday().replaceAll("/", "-"));
			}
			for(String key:dicts.keySet()){
				List<Dict> list = dicts.get(key);
				for(Dict dict:list){
					if(dict.getTypeName().equalsIgnoreCase(customerInfor.getNationality())){
						customerInfor.setNationality(dict.getTypeCode());
						break;
					}else if(dict.getTypeName().equalsIgnoreCase(customerInfor.getCardType())){
						customerInfor.setCardType(dict.getTypeCode());
						break;
					}else if(dict.getTypeName().equalsIgnoreCase(customerInfor.getResidentialPropertie())){
						customerInfor.setResidentialPropertie(dict.getTypeCode());
						break;
					}else if(dict.getTypeName().equalsIgnoreCase(customerInfor.getMaritalStatus())){
						customerInfor.setMaritalStatus(dict.getTypeCode());
						break;
					}else if(dict.getTypeName().equalsIgnoreCase(customerInfor.getSex())){
						customerInfor.setSex(dict.getTypeCode());
						break;
					}
				}
			}
			
			for(String key:Dictionary.degreeeducationList.keySet()){
				if(Dictionary.degreeeducationList.get(key).equalsIgnoreCase(customerInfor.getDegreeEducation())){
					customerInfor.setDegreeEducation(key);
					break;
				}
			}
			/*验证身份证号码是否正确的代码
			if(CustomerInforConstant.ID_CODE.equals(customerInfor.getCardType())){
				String errorMessage = IdCardValidate.IDCardValidate(customerInfor.getCardId());
				if(StringUtils.trimToNull(errorMessage)!=null){
					throw new IntoPiecesException(customerInfor.getChineseName()+":"+errorMessage);
				}
			}*/
			/*根据客户经理编号查找客户经理id*/
			List<SystemUser> userList = commonDao.queryBySql(SystemUser.class, "select * from sys_user s where s.external_id='"+customerInfor.getUserId()+"'", null);
			if(userList!=null&&!userList.isEmpty()){
				customerInfor.setUserId(userList.get(0).getId());
			}else{
				customerInfor.setUserId(null);
			}
			commonDao.insertObject(obj);
		}
		
		for(BusinessModel obj : customerCareersInformationList){
			CustomerCareersInformation customerCareersInformation = (CustomerCareersInformation)obj;
			customerCareersInformation.setCreatedBy(userId);
			customerCareersInformation.setCreatedTime(new Date());
			/*单位性质*/
			for(String key:Dictionary.unitPropertisList.keySet()){
				if(Dictionary.unitPropertisList.get(key).equalsIgnoreCase(customerCareersInformation.getUnitNature())){
					customerCareersInformation.setUnitNature(key);
					break;
				}
			}
			/*行业类别*/
			for(String key:Dictionary.industryTypeList.keySet()){
				if(Dictionary.industryTypeList.get(key).equalsIgnoreCase(customerCareersInformation.getIndustryType())){
					customerCareersInformation.setIndustryType(key);
					break;
				}
			}
			/*职务*/
			for(String key:Dictionary.positioList.keySet()){
				if(Dictionary.positioList.get(key).equalsIgnoreCase(customerCareersInformation.getPositio())){
					customerCareersInformation.setPositio(key);
					break;
				}
			}
			/*职称*/
			for(String key:Dictionary.titleList.keySet()){
				if(Dictionary.titleList.get(key).equalsIgnoreCase(customerCareersInformation.getTitle())){
					customerCareersInformation.setTitle(key);
					break;
				}
			}
			commonDao.insertObject(obj);
		}
		
		for(BusinessModel obj : customerApplicationInfoList){
			CustomerApplicationInfo customerApplicationInfo = (CustomerApplicationInfo)obj;
			customerApplicationInfo.setCreatedBy(userId);
			customerApplicationInfo.setCreatedTime(new Date());
			for(String key:Dictionary.debitWayList.keySet()){
				if(Dictionary.debitWayList.get(key).equalsIgnoreCase(customerApplicationInfo.getDebitWay())){
					customerApplicationInfo.setDebitWay(key);
				}
			}
			if(productAttributeList!=null&&!productAttributeList.isEmpty()){
				int i =0;
				for(ProductAttribute productAttribute:productAttributeList){
					if(productAttribute.getProductName().equalsIgnoreCase(customerApplicationInfo.getProductId())){
						customerApplicationInfo.setProductId(productAttribute.getId());
						break;
					}else{
						i++;
					}
				}
				if(i==productAttributeList.size()){
					throw new IntoPiecesException(customerApplicationInfo.getProductId()+"不是合法的产品,请检查!");
				}
			}
			commonDao.insertObject(obj);
		}
		
		for(BusinessModel obj : customerApplicationGuarantorList){
			CustomerApplicationGuarantor customerApplicationGuarantor = (CustomerApplicationGuarantor)obj;
			customerApplicationGuarantor.setCreatedBy(userId);
			customerApplicationGuarantor.setCreatedTime(new Date());
			for(String key:dicts.keySet()){
				List<Dict> list = dicts.get(key);
				for(Dict dict:list){
					if(dict.getTypeName().equalsIgnoreCase(customerApplicationGuarantor.getSex())){
						customerApplicationGuarantor.setSex(dict.getTypeCode());
						break;
					}
				}
			}
			commonDao.insertObject(obj);
		}
		
		for(BusinessModel obj : customerApplicationContactList){
			CustomerApplicationContact customerApplicationContact = (CustomerApplicationContact)obj;
			customerApplicationContact.setCreatedBy(userId);
			customerApplicationContact.setCreatedTime(new Date());
			for(String key:dicts.keySet()){
				List<Dict> list = dicts.get(key);
				for(Dict dict:list){
					if(dict.getTypeName().equalsIgnoreCase(customerApplicationContact.getSex())){
						customerApplicationContact.setSex(dict.getTypeCode());
						break;
					}
				}
			}
			commonDao.insertObject(obj);
		}
		
		for(BusinessModel obj : customerApplicationRecomList){
			CustomerApplicationRecom customerApplicationRecom = (CustomerApplicationRecom)obj;
			customerApplicationRecom.setCreatedBy(userId);
			customerApplicationRecom.setCreatedTime(new Date());
			commonDao.insertObject(obj);
		}
		
		for(BusinessModel obj : customerApplicationOtherList){
			CustomerApplicationOther customerApplicationOther = (CustomerApplicationOther)obj;
			customerApplicationOther.setCreatedBy(userId);
			customerApplicationOther.setCreatedTime(new Date());
			if("使用密码".equalsIgnoreCase(customerApplicationOther.getConsumptionUsePassword())){
				customerApplicationOther.setConsumptionUsePassword("1");
			}else{
				customerApplicationOther.setConsumptionUsePassword("0");
			}
			if("开通".equalsIgnoreCase(customerApplicationOther.getSmsOpeningTrading())){
				customerApplicationOther.setSmsOpeningTrading("1");
			}else{
				customerApplicationOther.setSmsOpeningTrading("0");
			}
			
			if("纸质账单".equalsIgnoreCase(customerApplicationOther.getBillingMethod())){
				customerApplicationOther.setBillingMethod("0");
			}else if("电子账单".equalsIgnoreCase(customerApplicationOther.getBillingMethod())){
				customerApplicationOther.setBillingMethod("1");
			}
			if("到网点领取".equalsIgnoreCase(customerApplicationOther.getCollarCardMode())){
				customerApplicationOther.setCollarCardMode("0");
			}else if("普通邮寄".equalsIgnoreCase(customerApplicationOther.getCollarCardMode())){
				customerApplicationOther.setCollarCardMode("1");
			}else if("快递".equalsIgnoreCase(customerApplicationOther.getCollarCardMode())){
				customerApplicationOther.setCollarCardMode("2");
			}
			if("是".equalsIgnoreCase(customerApplicationOther.getUseSecondCard())){
				customerApplicationOther.setUseSecondCard("1");
			}else{
				customerApplicationOther.setUseSecondCard("0");
			}
			commonDao.insertObject(obj);
		}
	}
	
	/*读取excel2003*/
	private void getExcel2003Content(HSSFSheet hssfSheet, int i,List<BusinessModel> list) {
		if (i == 0) {
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerInfor customerInfor = new CustomerInfor();
				customerInfor.setId(IDGenerator.generateID());
                for(int cellNum=0;cellNum<=18;cellNum++){
                	switch(cellNum){
                	   case 0:customerInfor.setCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerInfor.setChineseName(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerInfor.setNationality(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerInfor.setSex(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerInfor.setPinyinenglishName(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   case 5:customerInfor.setBirthday(this.getExcel2003Value(hssfRow.getCell(5))); break;
                	   case 6:customerInfor.setCardType(this.getExcel2003Value(hssfRow.getCell(6))); break;
                	   case 7:customerInfor.setCardId(this.getExcel2003Value(hssfRow.getCell(7))); break;
                	   case 8:customerInfor.setResidentialAddress(this.getExcel2003Value(hssfRow.getCell(8))); break;
                	   case 9:customerInfor.setZipCode(this.getExcel2003Value(hssfRow.getCell(9))); break;
                	   case 10:customerInfor.setHomePhone(this.getExcel2003Value(hssfRow.getCell(10))); break;
                	   case 11:customerInfor.setTelephone(this.getExcel2003Value(hssfRow.getCell(11))); break;
                	   case 12:customerInfor.setMail(this.getExcel2003Value(hssfRow.getCell(12))); break;
                	   case 13:customerInfor.setResidentialPropertie(this.getExcel2003Value(hssfRow.getCell(13))); break;
                	   case 14:customerInfor.setMaritalStatus(this.getExcel2003Value(hssfRow.getCell(14))); break;
                	   case 15:customerInfor.setDegreeEducation(this.getExcel2003Value(hssfRow.getCell(15))); break;
                	   case 16:customerInfor.setHouseholdAddress(this.getExcel2003Value(hssfRow.getCell(16))); break;
                	   case 17:customerInfor.setZipCodeAddress(this.getExcel2003Value(hssfRow.getCell(17))); break;
                	   case 18:customerInfor.setUserId(this.getExcel2003Value(hssfRow.getCell(18))); break;
                	   default:break;
                	}
                }
                list.add(customerInfor);
			}
		}else if(i == 1){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerCareersInformation customerCareersInformation = new CustomerCareersInformation();
                for(int cellNum=0;cellNum<=11;cellNum++){
                	switch(cellNum){
                	   case 0:customerCareersInformation.setCustomerCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerCareersInformation.setNameUnit(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerCareersInformation.setDepartmentName(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerCareersInformation.setUnitAddress(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerCareersInformation.setZipCode(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   case 5:customerCareersInformation.setUnitPhone(this.getExcel2003Value(hssfRow.getCell(5))); break;
                	   case 6:customerCareersInformation.setUnitNature(this.getExcel2003Value(hssfRow.getCell(6))); break;
                	   case 7:customerCareersInformation.setIndustryType(this.getExcel2003Value(hssfRow.getCell(7))); break;
                	   case 8:customerCareersInformation.setPositio(this.getExcel2003Value(hssfRow.getCell(8))); break;
                	   case 9:customerCareersInformation.setTitle(this.getExcel2003Value(hssfRow.getCell(9))); break;
                	   case 10:customerCareersInformation.setAnnualIncome(this.getExcel2003Value(hssfRow.getCell(10))); break;
                	   case 11:customerCareersInformation.setYearWorkUnit(this.getExcel2003Value(hssfRow.getCell(11))); break;
                	   default:break;
                	}
                }
                list.add(customerCareersInformation);
			}
		}else if(i==2){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
				customerApplicationInfo.setId(IDGenerator.generateID());
				customerApplicationInfo.setStatus(Constant.SAVE_INTOPICES);
                for(int cellNum=0;cellNum<=5;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationInfo.setCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationInfo.setProductId(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationInfo.setApplyQuota(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationInfo.setDebitWay(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationInfo.setAutomaticRepaymentAgreement(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationInfo.setRepaymentCardNumber(this.getExcel2003Value(hssfRow.getCell(5))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationInfo);
			}
		}else if(i==3){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationGuarantor customerApplicationGuarantor = new CustomerApplicationGuarantor();
                for(int cellNum=0;cellNum<=8;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationGuarantor.setMainApplicationFormCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationGuarantor.setGuarantorMortgagorPledge(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationGuarantor.setSex(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationGuarantor.setRelationshipWithApplicant(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationGuarantor.setUnitName(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationGuarantor.setDepartment(this.getExcel2003Value(hssfRow.getCell(5))); break;
                	   case 6:customerApplicationGuarantor.setContactPhone(this.getExcel2003Value(hssfRow.getCell(6))); break;
                	   case 7:customerApplicationGuarantor.setCellPhone(this.getExcel2003Value(hssfRow.getCell(7))); break;
                	   case 8:customerApplicationGuarantor.setDocumentNumber(this.getExcel2003Value(hssfRow.getCell(8))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationGuarantor);
			}
		}else if(i==4){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationContact customerApplicationContact = new CustomerApplicationContact();
                for(int cellNum=0;cellNum<=7;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationContact.setMainApplicationFormCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationContact.setContactName(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationContact.setSex(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationContact.setRelationshipWithApplicant(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationContact.setUnitName(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationContact.setDepartment(this.getExcel2003Value(hssfRow.getCell(5))); break;
                	   case 6:customerApplicationContact.setContactPhone(this.getExcel2003Value(hssfRow.getCell(6))); break;
                	   case 7:customerApplicationContact.setCellPhone(this.getExcel2003Value(hssfRow.getCell(7))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationContact);
			}
		}else if(i==5){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationRecom customerApplicationRecom = new CustomerApplicationRecom();
                for(int cellNum=0;cellNum<=4;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationRecom.setMainApplicationFormCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationRecom.setName(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationRecom.setOutlet(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationRecom.setContactPhone(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationRecom.setRecommendedIdentityCardNumb(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationRecom);
			}
		}else if(i==6){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationOther customerApplicationOther = new CustomerApplicationOther();
                for(int cellNum=0;cellNum<=5;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationOther.setMainApplicationFormCode(this.getExcel2003Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationOther.setBillingMethod(this.getExcel2003Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationOther.setPaperBillingShippingAddress(this.getExcel2003Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationOther.setCollarCardMode(this.getExcel2003Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationOther.setConsumptionUsePassword(this.getExcel2003Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationOther.setSmsOpeningTrading(this.getExcel2003Value(hssfRow.getCell(5))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationOther);
			}
		}
	}
	
	/*读取excel2007*/
	private void getExcel2007Content(XSSFSheet xssfSheet, int i,List<BusinessModel> list) {
		if (i == 0) {
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerInfor customerInfor = new CustomerInfor();
				customerInfor.setId(IDGenerator.generateID());
                for(int cellNum=0;cellNum<=18;cellNum++){
                	switch(cellNum){
                	   case 0:customerInfor.setCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerInfor.setChineseName(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerInfor.setNationality(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerInfor.setSex(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerInfor.setPinyinenglishName(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   case 5:customerInfor.setBirthday(this.getExcel2007Value(hssfRow.getCell(5))); break;
                	   case 6:customerInfor.setCardType(this.getExcel2007Value(hssfRow.getCell(6))); break;
                	   case 7:customerInfor.setCardId(this.getExcel2007Value(hssfRow.getCell(7))); break;
                	   case 8:customerInfor.setResidentialAddress(this.getExcel2007Value(hssfRow.getCell(8))); break;
                	   case 9:customerInfor.setZipCode(this.getExcel2007Value(hssfRow.getCell(9))); break;
                	   case 10:customerInfor.setHomePhone(this.getExcel2007Value(hssfRow.getCell(10))); break;
                	   case 11:customerInfor.setTelephone(this.getExcel2007Value(hssfRow.getCell(11))); break;
                	   case 12:customerInfor.setMail(this.getExcel2007Value(hssfRow.getCell(12))); break;
                	   case 13:customerInfor.setResidentialPropertie(this.getExcel2007Value(hssfRow.getCell(13))); break;
                	   case 14:customerInfor.setMaritalStatus(this.getExcel2007Value(hssfRow.getCell(14))); break;
                	   case 15:customerInfor.setDegreeEducation(this.getExcel2007Value(hssfRow.getCell(15))); break;
                	   case 16:customerInfor.setHouseholdAddress(this.getExcel2007Value(hssfRow.getCell(16))); break;
                	   case 17:customerInfor.setZipCodeAddress(this.getExcel2007Value(hssfRow.getCell(17))); break;
                	   case 18:customerInfor.setUserId(this.getExcel2007Value(hssfRow.getCell(18))); break;
                	   default:break;
                	}
                }
                list.add(customerInfor);
			}
		}else if(i == 1){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerCareersInformation customerCareersInformation = new CustomerCareersInformation();
                for(int cellNum=0;cellNum<=11;cellNum++){
                	switch(cellNum){
                	   case 0:customerCareersInformation.setCustomerCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerCareersInformation.setNameUnit(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerCareersInformation.setDepartmentName(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerCareersInformation.setUnitAddress(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerCareersInformation.setZipCode(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   case 5:customerCareersInformation.setUnitPhone(this.getExcel2007Value(hssfRow.getCell(5))); break;
                	   case 6:customerCareersInformation.setUnitNature(this.getExcel2007Value(hssfRow.getCell(6))); break;
                	   case 7:customerCareersInformation.setIndustryType(this.getExcel2007Value(hssfRow.getCell(7))); break;
                	   case 8:customerCareersInformation.setPositio(this.getExcel2007Value(hssfRow.getCell(8))); break;
                	   case 9:customerCareersInformation.setTitle(this.getExcel2007Value(hssfRow.getCell(9))); break;
                	   case 10:customerCareersInformation.setAnnualIncome(this.getExcel2007Value(hssfRow.getCell(10))); break;
                	   case 11:customerCareersInformation.setYearWorkUnit(this.getExcel2007Value(hssfRow.getCell(11))); break;
                	   default:break;
                	}
                }
                list.add(customerCareersInformation);
			}
		}else if(i==2){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationInfo customerApplicationInfo = new CustomerApplicationInfo();
				customerApplicationInfo.setId(IDGenerator.generateID());
				customerApplicationInfo.setStatus(Constant.SAVE_INTOPICES);
                for(int cellNum=0;cellNum<=5;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationInfo.setCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationInfo.setProductId(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationInfo.setApplyQuota(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationInfo.setDebitWay(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationInfo.setAutomaticRepaymentAgreement(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationInfo.setRepaymentCardNumber(this.getExcel2007Value(hssfRow.getCell(5))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationInfo);
			}
		}else if(i==3){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationGuarantor customerApplicationGuarantor = new CustomerApplicationGuarantor();
                for(int cellNum=0;cellNum<=8;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationGuarantor.setMainApplicationFormCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationGuarantor.setGuarantorMortgagorPledge(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationGuarantor.setSex(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationGuarantor.setRelationshipWithApplicant(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationGuarantor.setUnitName(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationGuarantor.setDepartment(this.getExcel2007Value(hssfRow.getCell(5))); break;
                	   case 6:customerApplicationGuarantor.setContactPhone(this.getExcel2007Value(hssfRow.getCell(6))); break;
                	   case 7:customerApplicationGuarantor.setCellPhone(this.getExcel2007Value(hssfRow.getCell(7))); break;
                	   case 8:customerApplicationGuarantor.setDocumentNumber(this.getExcel2007Value(hssfRow.getCell(8))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationGuarantor);
			}
		}else if(i==4){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationContact customerApplicationContact = new CustomerApplicationContact();
                for(int cellNum=0;cellNum<=7;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationContact.setMainApplicationFormCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationContact.setContactName(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationContact.setSex(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationContact.setRelationshipWithApplicant(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationContact.setUnitName(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationContact.setDepartment(this.getExcel2007Value(hssfRow.getCell(5))); break;
                	   case 6:customerApplicationContact.setContactPhone(this.getExcel2007Value(hssfRow.getCell(6))); break;
                	   case 7:customerApplicationContact.setCellPhone(this.getExcel2007Value(hssfRow.getCell(7))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationContact);
			}
		}else if(i==5){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationRecom customerApplicationRecom = new CustomerApplicationRecom();
                for(int cellNum=0;cellNum<=4;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationRecom.setMainApplicationFormCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationRecom.setName(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationRecom.setOutlet(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationRecom.setContactPhone(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationRecom.setRecommendedIdentityCardNumb(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationRecom);
			}
		}else if(i==6){
			/* 第一行是表头, 所以从第二行开始遍历 */
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow hssfRow = xssfSheet.getRow(rowNum);
				if (hssfRow == null) {
					continue;
				}
				CustomerApplicationOther customerApplicationOther = new CustomerApplicationOther();
                for(int cellNum=0;cellNum<=5;cellNum++){
                	switch(cellNum){
                	   case 0:customerApplicationOther.setMainApplicationFormCode(this.getExcel2007Value(hssfRow.getCell(0))); break;
                	   case 1:customerApplicationOther.setBillingMethod(this.getExcel2007Value(hssfRow.getCell(1))); break;
                	   case 2:customerApplicationOther.setPaperBillingShippingAddress(this.getExcel2007Value(hssfRow.getCell(2))); break;
                	   case 3:customerApplicationOther.setCollarCardMode(this.getExcel2007Value(hssfRow.getCell(3))); break;
                	   case 4:customerApplicationOther.setConsumptionUsePassword(this.getExcel2007Value(hssfRow.getCell(4))); break;
                	   case 5:customerApplicationOther.setSmsOpeningTrading(this.getExcel2007Value(hssfRow.getCell(5))); break;
                	   default:break;
                	}
                }
                list.add(customerApplicationOther);
			}
		}
	}
	
	
	private String getExcel2003Value(HSSFCell hssfCell) {
		if(hssfCell==null){
			return null;
		}else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }
	
	private String getExcel2007Value(XSSFCell hssfCell) {
		if(hssfCell==null){
			return null;
		}else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            // 返回布尔类型的值
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            // 返回数值类型的值
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            // 返回字符串类型的值
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

	/**
	 * 修改客户信息移交状态
	 * @param id
	 * @param status
	 * @return
	 */
	public boolean updateCustomerInforDivisionalStatus(String id,CustomerInforDStatusEnum status){
		int i = customerInforDao.updateCustomerInforDivisionalStatus(id, status.toString());
		return i>0?true:false;
	}
	/**
	 * 得到客户信息移交状态
	 * @param id
	 * @return
	 */
	public boolean findCustomerInforDivisionalStatus(String id){
		String result = customerInforDao.getCustomerInforDivisionalStatus(id);
		if(result.equals(CustomerInforDStatusEnum.turn)){
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * 进件申请提交时 客户维护资料做快照  7张表对应客户的数据快照
	 * @param customerId 客户id
	 * @param applicationId 进件申请id
	 * @throws SQLException 
	 */
	public void insertCustomerInfoBySubmitApp(String customerId, String applicationId,String productId){
		customerInforDao.cloneBasicCustomerInfo(customerId, applicationId);
		customerInforDao.cloneCustomerCareersInf(customerId, applicationId);
		customerInforDao.cloneCustomerMainManager(customerId, applicationId);
		customerInforDao.cloneCustomerQuestionInfo(customerId, applicationId);
		customerInforDao.cloneCustomerWorkshipInfo(customerId, applicationId);
		customerInforDao.cloneDimensionalModelCredit(customerId, applicationId);
		customerInforDao.cloneCustomerVideoAccessories(customerId, applicationId);
		//添加申请件流程
		WfProcessInfo wf=new WfProcessInfo();
		wf.setProcessType(WfProcessInfoType.process_type);
		wf.setVersion("1");
		commonDao.insertObject(wf);
		List<NodeAudit> list=nodeAuditService.findByNodeTypeAndProductId(NodeAuditTypeEnum.Product.name(),productId);
		boolean startBool=false;
		boolean endBool=false;
		//节点id和WfStatusInfo id的映射
		Map<String, String> nodeWfStatusMap = new HashMap<String, String>();
		for(NodeAudit nodeAudit:list){
			if(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())){
				startBool=true;
			}
			
			if(startBool&&!endBool){
				WfStatusInfo wfStatusInfo=new WfStatusInfo();
				wfStatusInfo.setIsStart(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())?"1":"0");
				wfStatusInfo.setIsClosed(nodeAudit.getIsend().equals(YesNoEnum.YES.name())?"1":"0");
				wfStatusInfo.setRelationedProcess(wf.getId());
				wfStatusInfo.setStatusName(nodeAudit.getNodeName());
				wfStatusInfo.setStatusCode(nodeAudit.getId());
				commonDao.insertObject(wfStatusInfo);
				
				nodeWfStatusMap.put(nodeAudit.getId(), wfStatusInfo.getId());
				
				if(nodeAudit.getIsstart().equals(YesNoEnum.YES.name())){
					//添加初始审核
					CustomerApplicationProcess customerApplicationProcess=new CustomerApplicationProcess();
					String serialNumber = processService.start(wf.getId());
					customerApplicationProcess.setSerialNumber(serialNumber);
					customerApplicationProcess.setNextNodeId(nodeAudit.getId()); 
					customerApplicationProcess.setApplicationId(applicationId);
					commonDao.insertObject(customerApplicationProcess);
					
					CustomerApplicationInfo applicationInfo = commonDao.findObjectById(CustomerApplicationInfo.class, applicationId);
					applicationInfo.setSerialNumber(serialNumber);
					commonDao.updateObject(applicationInfo);
				}
			}
			
			if(nodeAudit.getIsend().equals(YesNoEnum.YES.name())){
				endBool=true;
			}
		}
		//节点关系
		List<NodeControl> nodeControls = nodeAuditService.findNodeControlByNodeTypeAndProductId(NodeAuditTypeEnum.Product.name(), productId);
		for(NodeControl control : nodeControls){
			WfStatusResult wfStatusResult = new WfStatusResult();
			wfStatusResult.setCurrentStatus(nodeWfStatusMap.get(control.getCurrentNode()));
			wfStatusResult.setNextStatus(nodeWfStatusMap.get(control.getNextNode()));
			wfStatusResult.setExamineResult(control.getCurrentStatus());
			commonDao.insertObject(wfStatusResult);
		}
	}
	
	/**
	 * 进件申请退回时 删除备份表中该单据的信息
	 * @param customerId
	 * @param applicationId
	 */
	public void deleteCloneSubmitAppByReturn(String customerId, String applicationId){
		customerInforDao.deleteCloneBasicCustomerInfo(customerId, applicationId);
		customerInforDao.deleteCloneCustomerCareersInf(customerId, applicationId);
		customerInforDao.deleteCloneCustomerMainManager(customerId, applicationId);
		customerInforDao.deleteCloneCustomerQuestionInfo(customerId, applicationId);
		customerInforDao.deleteCloneCustomerWorkshipInfo(customerId, applicationId);
		customerInforDao.deleteCloneDimensionalModelCredit(customerId, applicationId);
		customerInforDao.deleteCloneCustomerVideoAccessories(customerId, applicationId);
	}
	
	/**
	 * 生成流水
	 */
	
	/**
	 * 客户信息批量上传校验不能上传重复的客户信息,判断条件为证件类型和证件号码组合
	 */
	private List<String> checkCustomerInfo(){
		return customerinforcommDao.checkCustomerInfo();
	}

	/**
	 * 根据进件申请Id查询客户维护资料快照
	 * @param applicationId
	 */
	public CustomerInfor findCloneCustomerInforByAppId(String applicationId) {
		return customerInforDao.findCloneCustomerInforByAppId(applicationId);
	}
	
	/**
	 * 保存客户影像资料
	 * @param customerId
	 */
	public void saveYxzlByCustomerId(String customerId,String remark, MultipartFile file) {
		Map<String, String> map = UploadFileTool.uploadYxzlFileBySpring(file);
		String fileName = map.get("fileName");
		String url = map.get("url");
		VideoAccessories videoAccessories = new VideoAccessories();
		videoAccessories.setId(IDGenerator.generateID());
		videoAccessories.setCustomerId(customerId);
		videoAccessories.setRemark(remark);
		videoAccessories.setCreatedTime(new Date());
		if (StringUtils.trimToNull(url) != null) {
			videoAccessories.setServerUrlPath(url);
		}
		if (StringUtils.trimToNull(fileName) != null) {
			videoAccessories.setFileName(fileName);
		}
		commonDao.insertObject(videoAccessories);
	}
	
	/**
	 * 删除客户影像资料
	 * @param id
	 */
	public void deleteYxzlById(String id){
		VideoAccessories v = commonDao.findObjectById(VideoAccessories.class, id);
		if(v!=null){
			UploadFileTool.deleteFile(v.getServerUrlPath());
		}
		commonDao.deleteObject(VideoAccessories.class, id);
	}
	
	/**
	 * 下载客户影像资料
	 * @param id
	 * @throws Exception 
	 */
	public void downLoadYxzlById(HttpServletResponse response,String id) throws Exception{
		VideoAccessories v = commonDao.findObjectById(VideoAccessories.class, id);
		if(v!=null){
			UploadFileTool.downLoadFile(response, v.getServerUrlPath(), v.getFileName());
		}
	}
	
	
	/**
	 * 生成影像资料缩略图,返回缩略图和原始图的图片的url
	 * @param id
	 * @throws Exception 
	 */
	public Map<String,String> createThumbnail(String id) throws Exception{
		Map<String,String> map = null;
		VideoAccessories v = commonDao.findObjectById(VideoAccessories.class, id);
		if(v!=null){
			map = UploadFileTool.CreateThumbnail(Constant.FILE_PATH, v.getServerUrlPath(), v.getFileName());
		}
		return map;
	}
	
	
	/**
	 * 按申请表id查找相应的客户拍照信息
	 * @param applicationId
	 * @return CustomerInfor
	 */
	public CustomerInfor findCustomerInforsById(String applicationId){
		return customerInforDao.findCloneCustomerInforByAppId(applicationId);
	}
	
	/**
	 * 按客户id查找相应的客户置业拍照信息
	 * @param customerId
	 * @return CustomerCareersInformation
	 */
	public CustomerCareersInformation findCustomerCareersByCustomerId(String customerId, String applicationId){
		return customerInforDao.findcloneCustomerCareersByCustomerId(customerId, applicationId);
	}
	
	/**
	 * 按客户id查找其对应的影像附件资料
	 * @param customerId
	 * @return List<CustomerCareersInformation>
	 */
	public List<VideoAccessories> findCustomerVideoAccessoriesByCustomerId(String customerId){
		return customerinforcommDao.findCustomerVideoAccessoriesByCustomerId(customerId);
	}

	
	/**
	 * 按id查找相应的客户基本信息（太原）
	 * @param customerInforId
	 * @return
	 */
	public CustomerFirsthendBase findCustomerFirsthendById(String customerInforId){
		return commonDao.findObjectById(CustomerFirsthendBase.class,customerInforId);
	}
	
	/**
	 * 按客户内码id查找相应的客户基本信息（太原）
	 * @param customerInforId
	 * @return
	 */
	public CustomerFirsthendBase findCustomerFirsthendByNm(String nmId){
		String sql = "select * from ty_customer_base where khnm='"+nmId+"'";
		CustomerFirsthendBase info = commonDao.queryBySql(CustomerFirsthendBase.class,sql , null).get(0);
		return info;
	}
	
	public CustomerFirsthendBaseLocal findCustomerFirsthendLocalByNm(String nmId){
		String sql = "select * from ty_customer_base_local where khnm='"+nmId+"'";
		List<CustomerFirsthendBaseLocal> infolist = commonDao.queryBySql(CustomerFirsthendBaseLocal.class,sql , null);
		if(infolist.size()>0){
			return infolist.get(0);
		}
		return null;
	}
	
	public void updateCustomerFirsthendLocal(CustomerFirsthendBaseLocal baseLocal){
		commonDao.updateObject(baseLocal);
	}
	
	/**
	 * 获取指定客户经理的客户列表
	 * @param user
	 * @return
	 */
	public QueryResult<MaintenanceLog> findCustomerByFilter(CustomerInforFilter filter){
		List<MaintenanceLog> plans = customerInforDao.findCustomerByFilter(filter);
		int size = customerInforDao.findCustomerCountByFilter(filter);
		QueryResult<MaintenanceLog> qr = new QueryResult<MaintenanceLog>(size,plans);
		return qr;
	}
	
	
	
	
	
	/**
	 * 按客户内码id查找相应的客户家庭信息（太原）
	 * @param customerInforId
	 * @return
	 */
	public List<CustomerFirsthendFamilyCy> findFamilyByNm(String nmId,String type){
		String sql = "select * from ty_customer_family_cy where khnm='"+nmId+"' and gxfl='"+type+"'";

		List<CustomerFirsthendFamilyCy> info = commonDao.queryBySql(CustomerFirsthendFamilyCy.class,sql , null);
		return info;
	}
	/**
	 * 按客户内码id查找相应的客户家庭财产信息（太原）
	 * @param customerInforId
	 * @return
	 */
	public List<CustomerFirsthendFamilyCc> findFamilyCcByNm(String nmId){
		String sql = "select * from ty_customer_family_cc where khnm='"+nmId+"'";

		List<CustomerFirsthendFamilyCc> info = commonDao.queryBySql(CustomerFirsthendFamilyCc.class,sql , null);
		return info;
	}
	/**
	 * 按客户内码id查找相应的客户生产经营（太原）
	 * @param customerInforId
	 * @return
	 */
	public List<CustomerFirsthendManage> findManageByNm(String nmId){
		String sql = "select * from ty_customer_manage where khnm='"+nmId+"'";

		List<CustomerFirsthendManage> info = commonDao.queryBySql(CustomerFirsthendManage.class,sql , null);
		return info;
	}
	/**
	 * 按客户内码id查找相应的客户学校情况（太原）
	 * @param customerInforId
	 * @return
	 */
	public List<CustomerFirsthendStudy> findStudyByNm(String nmId){
		String sql = "select * from ty_customer_study where khnm='"+nmId+"'";

		List<CustomerFirsthendStudy> info = commonDao.queryBySql(CustomerFirsthendStudy.class,sql , null);
		return info;
	}
	/**
	 * 按客户内码id查找相应的客户工作情况（太原）
	 * @param customerInforId
	 * @return
	 */
	public List<CustomerFirsthendWork> findWorkByNm(String nmId){
		String sql = "select * from ty_customer_work where khnm='"+nmId+"'";

		List<CustomerFirsthendWork> info = commonDao.queryBySql(CustomerFirsthendWork.class,sql , null);
		return info;
	}
	/**
	 * 按客户内码id查找相应的入保信息（太原）
	 * @param customerInforId
	 * @return
	 */
	public List<CustomerFirsthendSafe> findSafeByNm(String nmId){
		String sql = "select * from ty_customer_safe where khnm='"+nmId+"'";

		List<CustomerFirsthendSafe> info = commonDao.queryBySql(CustomerFirsthendSafe.class,sql , null);
		return info;
	}
	
	
	/**
	 * 保数据文件到”客户原始资料“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveBaseDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerBase.xml");

			// 解析”客户基础表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList);
			int count=0;
			for(Map<String, Object> map : datas){
				map.put("createTime", date);
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询客户原始表，存在则更新否则插入(更新原始信息表)
				String sql = "select * from ty_customer_base where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendBase> list = commonDao.queryBySql(CustomerFirsthendBase.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerBase(map);
				}else{
					customerInforDao.insertCustomerBase(map);
					//同步系统客户主表
					String card_id = map.get("zjhm").toString();
					String name = map.get("khmc").toString();
					String id = map.get("id").toString();
					//客户经理工号
					String khjl = map.get("khjl").toString();
					//获取客户经理id
					String user_id=null;
					List<SystemUser> users = commonDao.queryBySql(SystemUser.class, "select * from sys_user where external_id='"+khjl.trim()+"'", null);
					//银行工号匹配本系统uuid，存在则替换，不存在则插入银行工号
					if(users.size()>0){
						user_id = users.get(0).getId();
					}else{
						user_id = khjl.trim();
					}
					List<CustomerInfor> infoList = commonDao.queryBySql(CustomerInfor.class, "select * from basic_customer_information where card_id='"+card_id+"'", null);
					//不存在则插入
					if(infoList.size()==0){
						CustomerInfor info = new CustomerInfor();
						info.setCardId(card_id);
						info.setChineseName(name);
						info.setTyCustomerId(id);
						info.setId(IDGenerator.generateID());
						info.setUserId(user_id);
						//身份证
						info.setCardType("CST0000000000A");
						commonDao.insertObject(info);
					}else{
						//存在则作关联
						CustomerInfor info = infoList.get(0);
						info.setTyCustomerId(id);
						commonDao.updateObject(info);
					}
				}
				//先查询客户维护表，存在不操作否则插入(新增原始信息维护表)(触发器执行)
//				String sql1 = "select * from ty_customer_base_local where khnm='"+map.get("khnm").toString()+"'";
//				List<CustomerFirsthendBase> list1 = commonDao.queryBySql(CustomerFirsthendBase.class, sql1, null);
//				if(list1.size()==0){
//					customerInforDao.insertCustomerBaseLocal(map);
//				}
				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户家庭关系“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveCyDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerFamilyCy.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList);
			int count=0;
			for(Map<String, Object> map : datas){
				map.put("createTime", date);
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询家庭关系表，存在则更新否则插入
				String sql = "select * from ty_customer_family_cy where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendFamilyCy> list = commonDao.queryBySql(CustomerFirsthendFamilyCy.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerFamilyCy(map);
				}else{
					customerInforDao.insertCustomerFamilyCy(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户家庭关系“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveCcDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerFamilyCc.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList);
			int count=0;
			for(Map<String, Object> map : datas){
				map.put("createTime", date);
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询家庭关系表，存在则更新否则插入
				String sql = "select * from ty_customer_family_cc where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendFamilyCc> list = commonDao.queryBySql(CustomerFirsthendFamilyCc.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerFamilyCc(map);
				}else{
					customerInforDao.insertCustomerFamilyCc(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户学习履历“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveStudyDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerStudy.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList);
			int count=0;
			for(Map<String, Object> map : datas){
				map.put("createTime", date);
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询家庭关系表，存在则更新否则插入
				String sql = "select * from ty_customer_study where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendStudy> list = commonDao.queryBySql(CustomerFirsthendStudy.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerStudy(map);
				}else{
					customerInforDao.insertCustomerStudy(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户工作履历“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveWorkDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerWork.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList);
			int count=0;
			for(Map<String, Object> map : datas){
				map.put("createTime", date);
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询工作履历表，存在则更新否则插入
				String sql = "select * from ty_customer_work where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendWork> list = commonDao.queryBySql(CustomerFirsthendWork.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerWork(map);
				}else{
					customerInforDao.insertCustomerWork(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户工作履历“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveManageDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerManage.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList);
			int count=0;
			for(Map<String, Object> map : datas){
				map.put("createTime", date);
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询生产经营表，存在则更新否则插入
				String sql = "select * from ty_customer_manage where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendManage> list = commonDao.queryBySql(CustomerFirsthendManage.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerManage(map);
				}else{
					customerInforDao.insertCustomerManage(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”客户工作履历“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveSafeDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyCustomerSafe.xml");

			// 解析”帐单记录表“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList);
			int count=0;
			for(Map<String, Object> map : datas){
				map.put("createTime", date);
				count++;
//				System.out.println(count);
				// 保存数据
				//先查询生产经营表，存在则更新否则插入
				String sql = "select * from ty_customer_safe where khnm='"+map.get("khnm").toString()+"'";
				List<CustomerFirsthendSafe> list = commonDao.queryBySql(CustomerFirsthendSafe.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateCustomerSafe(map);
				}else{
					customerInforDao.insertCustomerSafe(map);
				}

				
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”流水账“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveLSZDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayLSZ.xml");

			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList);
			int count=0;
			for(Map<String, Object> map : datas){
				map.put("createTime", date);
				count++;
//				System.out.println(count);
				// 保存数据
				customerInforDao.insertRepayLSZ(map);
				}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”余额“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveYEHZDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayYEHZ.xml");

			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList);
			int count=0;
			for(Map<String, Object> map : datas){
				map.put("createTime", date);
				count++;
//				System.out.println(count);
				// 保存数据
				String sql = "select * from ty_repay_yehz where jjh='"+map.get("jjh").toString()+"'";
				List<TyRepayYehz> list = commonDao.queryBySql(TyRepayYehz.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateRepayYEHZ(map);
				}else{
					customerInforDao.insertRepayYEHZ(map);
				}
				//历史表新增(用触发器)
//				customerInforDao.insertRepayYEHZHistory(map);
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 保数据文件到”借据表“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveTKMXDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayTKMX.xml");

			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList);
			int count=0;
			for(Map<String, Object> map : datas){
				map.put("createTime", date);
				count++;
//				System.out.println(count);
				// 保存数据
				String sql = "select * from ty_repay_tkmx where jjh='"+map.get("jjh").toString()+"'";
				List<TyRepayTkmx> list = commonDao.queryBySql(TyRepayTkmx.class, sql, null);
				if(list.size()>0){
					customerInforDao.updateRepayTKMX(map);
				}else{
					customerInforDao.insertRepayTKMX(map);
				}
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保数据文件到”产品信息“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveProductDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayProduct.xml");
			
			// 解析”流水号“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList);
			int count=0;
			for(Map<String, Object> map : datas){
				map.put("createTime", date);
				count++;
//				System.out.println(count);
				// 保存数据
				String sql = "select * from ty_product_type where product_code='"+map.get("productCode").toString()+"'";
				List<TyProductType> list = commonDao.queryBySql(TyProductType.class, sql, null);
				if(list.size()==0){
					customerInforDao.insertProduct(map);
				}
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 保数据文件到”黑名单“表
	 * @param fileName
	 * @return
	 * @throws Exception 
	 */
	public void saveHMDDataFile(String fileName,String date) {
		try {
			ImportBankDataFileTools tools = new ImportBankDataFileTools();
			// 解析数据文件配置
			
			List<DataFileConf> confList = tools.parseDataFileConf("/mapping/tyRepayHMD.xml");

			// 解析”黑名单“数据文件
			List<Map<String, Object>> datas = tools.parseDataFile(fileName, confList);
			int count=0;
			//删除历史数据
			String sql = "delete from f_agr_crd_xyk_cuneg where created_time !='"+date+"'";
			commonDao.queryBySql(sql, null);
			for(Map<String, Object> map : datas){
				map.put("createTime", date);
				count++;
//				System.out.println(count);
				// 保存数据
				customerInforDao.insertHmd(map);
			}
			//释放空间
			datas=null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 解析（原始信息）
	 * @throws IOException 
	 */
	public void readFile() throws IOException{
		//获取今日日期
	      //yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		log.info(dateString+"******************开始读取原始信息文件********************");  
	        String gzFile = CardFtpUtils.bank_ftp_down_path+dateString;
	        for(int i=0;i<fileTxt.length;i++){
				String url = gzFile+File.separator+fileTxt[i];
				File f = new File(url);
				if(f.exists()){
						List<String> spFile = new ArrayList<String>();
						String fileN = "";
						//判断文件大小，超过50M的先分割
						if (f.exists() && f.isFile()){
							if(f.length()>20000000){
								int spCount = (int) (f.length()/20000000);
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
										log.info("*****************客户基本表********************");  
										saveBaseDataFile(gzFile+File.separator+fn,dateString);
										System.gc();
									}
									if(fn.startsWith("kkh_grjtcy")){
										log.info("*****************客户家庭关系表********************");  
										saveCyDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grjtcc")){
										log.info("*****************客户家庭财产表********************");  
										saveCcDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grxxll")){
										log.info("*****************客户学习表********************");  
										saveStudyDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grgzll")){
										log.info("*****************客户工作履历表********************");  
										saveWorkDataFile(gzFile+File.separator+fn,dateString);
									}
									if(fn.startsWith("kkh_grscjy")){
										log.info("*****************客户生产经营表********************");  
									saveManageDataFile(gzFile+File.separator+fn,dateString);
								}
									if(fn.startsWith("kkh_grrbxx")){
										log.info("*****************客户入保信息表********************");  
										saveSafeDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("cxd_dkcpmc")){
										log.info("*****************产品信息********************");  
										saveProductDataFile(gzFile+File.separator+fn,dateString);
									}else if(fn.startsWith("kkh_hmdgl")){
										log.info("*****************黑名单********************");  
										saveHMDDataFile(gzFile+File.separator+fn,dateString);
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
	        log.info(dateString+"******************完成读取原始信息文件********************");

	}
	
	/**
	 *解析贷款信息
	 * @throws IOException 
	 */
	public void readFileRepay() throws IOException{
		//获取今日日期
	      //yyyyMMdd格式
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		String gzFile = CardFtpUtils.bank_ftp_down_path+dateString;

		log.info(dateString+"******************开始读取贷款文件********************");  
        for(int i=0;i<fileTxtRepay.length;i++){
			String url = gzFile+File.separator+fileTxtRepay[i];
			File f = new File(url);
			if(f.exists()){
					List<String> spFile = new ArrayList<String>();
					String fileN = "";
					//判断文件大小，超过50M的先分割
					if (f.exists() && f.isFile()){
						if(f.length()>20000000){
							int spCount = (int) (f.length()/20000000);
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
									log.info("*****************流水账信息********************");  
									//删除历史数据
									String sql = " truncate   table   ty_repay_lsz";
									commonDao.queryBySql(sql, null);
									saveLSZDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_yehz")){
									log.info("*****************余额汇总信息********************");  
									saveYEHZDataFile(gzFile+File.separator+fn,dateString);
								}else if(fn.startsWith("kdk_tkmx")){
									log.info("*****************借据表信息********************");  
									saveTKMXDataFile(gzFile+File.separator+fn,dateString);
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
        log.info(dateString+"******************完成读取贷款文件********************");

	}
	
	/*
	 * 根据cardId获取user
	 */
	public List<CustomerInfor> findCustomerManagerIdById(String cardId){
		String sql = "select * from basic_customer_information where card_id='"+cardId+"'";
		List<CustomerInfor> list = commonDao.queryBySql(CustomerInfor.class, sql, null);
		return list;
	}
	/*
	 * 根据userid获取客户经理
	 */
	public String getUserInform(String id){
		SystemUser user = commonDao.findObjectById(SystemUser.class, id);
		if(user==null){
			return null;
		}else{
			return user.getExternalId();
		}
	}
	/*
	 * 根据cardId获取风险名单
	 */
	public List<RiskCustomer> findRiskByCardId(String cardId){
		String sql = "select * from risk_list where CUSTOMER_ID in (select id from BASIC_CUSTOMER_INFORMATION where card_id='"+cardId+"')";
		List<RiskCustomer> list = commonDao.queryBySql(RiskCustomer.class, sql, null);
		return list;
	}
	/*
	 * 根据userid获取user
	 */
	public SystemUser getUseById(String id){
		SystemUser user = commonDao.findObjectById(SystemUser.class, id);
		if(user==null){
			return null;
		}else{
			return user;
		}
	}
	
	/**
	 * 查询贷款信息
	 * @param filter
	 * @return
	 */
	public QueryResult<TyRepayYehzVo> findCustomerYexxByFilter(IntoPiecesFilter filter) {
		List<TyRepayYehzVo> plans = customerInforDao.findCustomerYexxList(filter);
		int size = customerInforDao.findCustomerYexxCountList(filter);
		QueryResult<TyRepayYehzVo> queryResult = new QueryResult<TyRepayYehzVo>(size,plans);
		return queryResult;
	}
	
	
	/**
	 * 查询流水信息
	 */
	
	public QueryResult<TyRepayLsz> findRepayLszByFilter(CustomerInfoLszFilter filter) {
		List<TyRepayLsz> plans = customerInforDao.findRepayLszList(filter);
		int size = customerInforDao.findRepayLszCountList(filter);
		QueryResult<TyRepayLsz> queryResult = new QueryResult<TyRepayLsz>(size,plans);
		return queryResult;
	}
	
	
}
