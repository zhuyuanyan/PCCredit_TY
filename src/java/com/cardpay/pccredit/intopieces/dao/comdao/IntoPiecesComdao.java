package com.cardpay.pccredit.intopieces.dao.comdao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.MakeCardFilter;
import com.cardpay.pccredit.intopieces.model.ApplicationDataImport;
import com.cardpay.pccredit.intopieces.model.CustomerAccountData;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationCom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.CustomerCreditInfo;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.MakeCard;
import com.cardpay.pccredit.intopieces.model.VideoAccessories;
import com.cardpay.pccredit.intopieces.web.ApproveHistoryForm;
import com.cardpay.pccredit.product.model.AddressAccessories;
import com.cardpay.pccredit.product.model.ManagerProductsConfiguration;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

@Service
public class IntoPiecesComdao {

	@Autowired
	private CommonDao commonDao;

	/* 查询进价信息 */
	public QueryResult<IntoPieces> findintoPiecesByFilter(
			IntoPiecesFilter filter) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String id = filter.getId();
		String chineseName  = filter.getChineseName();
		String productName = filter.getProductName();
		String userId = filter.getUserId();
		String cardId = filter.getCardId();
		String status = filter.getStatus();
		params.put("userId", userId);
		StringBuffer sql = new StringBuffer("select t.id,t.customer_id,b.ty_customer_id,b.chinese_name,t.product_id,p.product_name,b.card_id,t.apply_quota,t.status from customer_application_info t,basic_customer_information b,product_attribute p where t.customer_id=b.id and b.user_id = #{userId} and t.product_id=p.id  ");
		if(StringUtils.trimToNull(productName)!=null){
			params.put("productName", productName);
			 sql.append(" and p.product_name like '%'||#{productName}||'%' ");
			}
		
		if(StringUtils.trimToNull(id)!=null){
			params.put("id", id);
			sql.append(" and t.id like '%'||#{id}||'%' ");
			}
		if(StringUtils.trimToNull(status)!=null){
			params.put("status", status);
			sql.append("and t.status= #{status}");
			}
		if(StringUtils.trimToNull(cardId)!=null||StringUtils.trimToNull(chineseName)!=null){
			if(StringUtils.trimToNull(cardId)!=null&&StringUtils.trimToNull(chineseName)!=null){
			    //sql.append(" and (b.card_id like '%"+cardId+"%' or b.chinese_name like '%"+chineseName+"%' )");
			    sql.append(" and b.card_id like '%"+cardId+"%' and b.chinese_name like '%"+chineseName+"%' ");
			}else if(StringUtils.trimToNull(cardId)!=null&&StringUtils.trimToNull(chineseName)==null){
				params.put("cardId", cardId);
				sql.append(" and b.card_id like '%'||#{cardId}||'%' ");
			}else if(StringUtils.trimToNull(cardId)==null&&StringUtils.trimToNull(chineseName)!=null){
				params.put("chineseName", chineseName);
				sql.append(" and b.chinese_name like '%'||#{chineseName}||'%' ");
			}
		}
		
		sql.append(" order by t.id asc");
		return commonDao.queryBySqlInPagination(IntoPieces.class, sql.toString(), params,
				filter.getStart(), filter.getLimit());
	}
	
	/* 查询进价信息 */
	public QueryResult<IntoPieces> findintoApplayPiecesByFilter(
			IntoPiecesFilter filter) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		String sql = "select t.id,t.customer_id,b.chinese_name,t.product_id,p.product_name,b.card_id,t.apply_quota,t.status from customer_application_info t,basic_customer_information b,product_attribute p where t.customer_id=b.id and t.product_id=p.id and t.status='succeed' order by t.id asc";
		return commonDao.queryBySqlInPagination(IntoPieces.class, sql, params,
				filter.getStart(), filter.getLimit());
	}
	
	/* 查询进价信息 */
	public QueryResult<MakeCard> findCardByFilter(
			MakeCardFilter filter) {
		HashMap<String, Object> params = new HashMap<String, Object>();
		StringBuffer sql = new StringBuffer("select * from make_card t where 1=1 ");
		if(filter!=null&&StringUtils.trimToNull(filter.getCardNumber())!=null){
			sql.append(" and card_number="+"'"+StringUtils.trim(filter.getCardNumber())+"'");
		}
		if(filter!=null&&StringUtils.trimToNull(filter.getCardOrganization())!=null){
			sql.append(" and card_organization="+"'"+StringUtils.trim(filter.getCardOrganization())+"'");
		}
		return commonDao.queryBySqlInPagination(MakeCard.class, sql.toString(), params,
				filter.getStart(), filter.getLimit());
	}
	

	/* 客户查询用户名模糊匹配 */
	public void selectLikeByCardId(HttpServletResponse response,
			String tempParam) throws Exception {
		String country = null;
		List<CustomerInfor> allList = commonDao.queryBySql(CustomerInfor.class,
				"select * from basic_customer_information", null);
		List<CustomerInfor> matched = new ArrayList<CustomerInfor>();
		for (CustomerInfor customerInfor : allList) {
			if (customerInfor.getCardId() != null) {
				country = customerInfor.getCardId().toLowerCase();
				if (tempParam != null
						&& country.startsWith(tempParam.toLowerCase())) {
					matched.add(customerInfor);
				}
			}
		}
		if (!matched.isEmpty()) {
			JSONArray obj = JSONArray.fromObject(matched);
			response.getWriter().println(obj.toString());
		}
	}

	/* 根据客户id查询客户职业资料 */
	public CustomerCareersInformation findCustomerCareersInformationByCustomerId(
			String customerId) {
		List<CustomerCareersInformation> list = commonDao.queryBySql(
				CustomerCareersInformation.class,
				"select * from customer_careers_information where customer_id='"
						+ customerId + "'", null);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	/* 根据申请id查询客户联系人资料 */
	public List<CustomerApplicationContact> findCustomerApplicationContactsByApplicationId(
			String applicationId) {
		List<CustomerApplicationContact> list = commonDao
				.queryBySql(
						CustomerApplicationContact.class,
						"select * from customer_application_contact t where t.main_application_form_id ='"
								+ applicationId + "' order by created_time ", null);
		return list;
	}

	/* 根据申请id查询客户推荐人资料 */
	public List<CustomerApplicationRecom> findCustomerApplicationRecomsByApplicationId(
			String applicationId) {
		List<CustomerApplicationRecom> list = commonDao
				.queryBySql(
						CustomerApplicationRecom.class,
						"select * from customer_application_recom t where t.main_application_form_id ='"
								+ applicationId + "' order by created_time ", null);
		return list;
	}

	/* 根据申请id查询担保人资料 */
	public List<CustomerApplicationGuarantor> findCustomerApplicationGuarantorsByApplicationId(
			String applicationId) {
		List<CustomerApplicationGuarantor> list = commonDao
				.queryBySql(
						CustomerApplicationGuarantor.class,
						"select * from customer_application_guarantor t where t.main_application_form_id ='"
								+ applicationId + "' order by created_time ", null);
		return list;
	}
	
	/* 根据申请id查询申请主表信息 */
	public CustomerApplicationInfo findCustomerApplicationInfoByApplicationId(
			String applicationId) {
		CustomerApplicationInfo customerApplicationInfo = commonDao
				.findObjectById(
						CustomerApplicationInfo.class,applicationId);
		return customerApplicationInfo;
	}
	
	/* 根据id查询申请主表信息 的其他资料信息*/
	public CustomerApplicationOther findCustomerApplicationOtherByApplicationId(
			String applicationId) {
		List<CustomerApplicationOther> list = commonDao
				.queryBySql(
						CustomerApplicationOther.class,
						"select * from customer_application_other t where t.main_application_form_id ='"
								+ applicationId + "'", null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/* 根据id查询申请主表信息 的行社专栏信息*/
	public CustomerApplicationCom findCustomerApplicationComByApplicationId(
			String applicationId) {
		List<CustomerApplicationCom> list = commonDao
				.queryBySql(
						CustomerApplicationCom.class,
						"select * from customer_application_com t where t.main_application_form_id ='"
								+ applicationId + "'", null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	/* 根据id查询申请主表信息 的行社专栏信息*/
	public CustomerAccountData findCustomerAccountDataByApplicationId(
			String applicationId) {
		List<CustomerAccountData> list = commonDao
				.queryBySql(
						CustomerAccountData.class,
						"select * from customer_account_data t where t.main_application_form_id ='"
								+ applicationId + "'", null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	
	/* 根据客户id查询客户申请主表信息 的影像资料信息*/
	public VideoAccessories findVideoAccessoriesByCustomerId(
			String customerId) {
		List<VideoAccessories> list = commonDao
				.queryBySql(
						VideoAccessories.class,
						"select * from video_accessories t,customer_application_info f where f.id=t.application_id and f.customer_id='"
								+ customerId + "'", null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}
		return null;
	}
	
	
	
	/* 根据客户信息是否在系统中存在*/
	public CustomerInfor findCustomerInforById(
			String id) {
		CustomerInfor customerInfor = commonDao.findObjectById(CustomerInfor.class, id);
		return customerInfor;
	}
	
	/* 根据客户是否填写过职业信息*/
	public CustomerCareersInformation findCustomerCareersInformationById(
			String id) {
		CustomerCareersInformation customerCareersInformation = commonDao.findObjectById(CustomerCareersInformation.class, id);
		return customerCareersInformation;
	}
	
	/* 根据客户是否申请过申请主表信息*/
	public CustomerApplicationInfo findCustomerApplicationInfoById(
			String id) {
		CustomerApplicationInfo customerApplicationInfo = commonDao.findObjectById(CustomerApplicationInfo.class, id);
		return customerApplicationInfo;
	}
	
	/* 查询申请的某一笔进件申请单中上传的产品的附件*/
	public List<AddressAccessories> findAddressAccessories(
			String applicationId,String productId) {
		List<AddressAccessories> list = commonDao.queryBySql(AddressAccessories.class, "select * from address_accessories t where t.application_id='"+applicationId+"' and t.product_id='"+productId+"'", null);
		return list;
	}
	
	/* 查找接口配置表*/
	public List<ApplicationDataImport> findApplicationDataImport() {
		List<ApplicationDataImport> applicationDataImportList = commonDao.queryBySql(ApplicationDataImport.class, "select * from application_data_import t order by to_number(id) ", null);
		return applicationDataImportList;
	}
	
	public List<CustomerInfor> checkValue(String userId,String valueType){
		return commonDao.queryBySql(CustomerInfor.class," select count(1) from account_manager_parameter a, manager_customer_type c   where  a.level_information = c.level_id  and  a.user_id='"+userId+"' and c.customer_type = '"+valueType+"'", null);
	}
	
	/**
	 * 
	 * @param id
	 * @param dataType application 进件 amountadjustment 调额信息
	 * @return
	 */
	public List<ApproveHistoryForm> findApproveHistoryByDataId(String id, String dataType){
		String sql = "select s.status_name, t.examine_result, su.display_name, t.examine_amount, t.start_examine_time " +
				" from wf_status_queue_record t left join wf_status_info s on t.current_status = s.id " +
				" left join wf_process_record pr on t.current_process = pr.id";
		if(dataType.equals("application")){
			sql += " left join customer_application_process aa on pr.id = aa.serial_number" +
					" left join sys_user su on t.examine_user = su.id " +
					" where aa.application_id = #{id}";
		} else if(dataType.equals("amountadjustment")){
			sql += " left join amount_adjustment_process aa on pr.id = aa.serial_number" +
					" left join sys_user su on t.examine_user = su.id " +
					" where aa.amount_adjustment_id = #{id}";
		}
		sql += " order by t.start_examine_time desc";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		
		return commonDao.queryBySql(ApproveHistoryForm.class, sql, params);
	}
	
	/**
	 * 查找进件的审批中的节点
	 * @param id
	 * @return
	 */
	public String findAprroveProgress(String id){
		String sql = " select status_name from (select s.status_name from wf_status_queue_record t "+
				" left join wf_status_info s on t.current_status = s.id "+
				" left join wf_process_record pr on t.current_process = pr.id "+
				" left join customer_application_process aa on pr.id = aa.serial_number "+
				" where aa.application_id=#{id} " +
				" order by t.start_examine_time desc) where rownum=1";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("id", id);
		List<HashMap<String, Object>> list = commonDao.queryBySql(sql, params);
		if(list != null && list.size() > 0){
			HashMap<String, Object> map = list.get(0);
			return (String) map.get("STATUS_NAME");
		} else {
			return null;
		}
	}
	
	public Float checkApplyQuota(String userId,String productId){
		String sql = "select * from MANAGER_PRODUCTS_CONFIGURATION t where exists (select 1 from ACCOUNT_MANAGER_PARAMETER f where t.customer_manager_level = f.level_information and f.user_id = '"+userId+"' and t.product_id = '"+productId+"')";
		List<ManagerProductsConfiguration> list = commonDao.queryBySql(ManagerProductsConfiguration.class, sql, null);
		if(list!=null&&!list.isEmpty()){
			if(StringUtils.trimToNull(list.get(0).getCreditLine())!=null){
				return Float.valueOf(list.get(0).getCreditLine());
			}else {
				return null;
			}
		}else{
			return null;
		}
	}
	
	
	
	
	public List<IntoPieces> findCustomerApplicationInfo() {
		//String sql = "select * from customer_application_info where status = 'approved'";
		
		String sql =     	"        select a.customer_id,e.id as product_id,e.product_name "+
						    "           from customer_application_info  a,    "+              
						    "                basic_customer_information b,    "+              
						    "                ty_customer_base           c,    "+             
						    "                ty_repay_tkmx              d,    "+
						    "                product_attribute          e     "+    
						    "           where a.customer_id =b.id             "+             
						    "                 and b.ty_customer_id = c.id     "+              
						    "                 and c.khh = d.khh               "+
						    "                 and a.product_id = e.id         "+
						    "                 and e.PRODUCT_TYPE_CODE = d.cpmc     "+                       
						    "                 and a.status ='approved'        "+             
						    "                 and d.sfzf !=1                  ";    

		List<IntoPieces> list = commonDao.queryBySql(IntoPieces.class,sql,null);
		return list;
	}
	
	
	public CustomerCreditInfo findCustCreditInfomation(String appId){
		
	   String sql = "		select tkmz.jzll,							  "+
					"		       tkmz.htll,                             "+
					"		       tkmz.ffje,                             "+
					"		       tkmz.jkrq,                             "+
					"		       tkmz.dqrq,                             "+
					"		       tkmz.dkqx,                             "+
					"		       yehz.jkje,                             "+
					"		       yehz.zhhkr,                            "+
					"		       yehz.shbj,                             "+
					"		       yehz.shlx,                             "+
					" case when  tkmz.DQRQ < to_char(sysdate,'yyyyMMdd') and yehz.DKYE!=0 then yehz.DKYE else '0' end as yqbj"+	
					"		    from customer_application_info app,       "+
					"		         basic_customer_information info,     "+
					"		         product_attribute prod,              "+
					"		         ty_customer_base base,               "+
					"		         ty_repay_tkmx tkmz,                  "+
					"		         ty_repay_yehz yehz                   "+
					"		    where app.customer_id = info.id           "+
					"		          and app.product_id  = prod.id       "+
					"		          and info.ty_customer_id = base.id   "+
					"		          and prod.PRODUCT_TYPE_CODE = tkmz.cpmc   "+
					"		          and base.khh = tkmz.khh             "+
					"		          and tkmz.jjh = yehz.jjh             "+
	   				"				  and app.id = '"+appId+"'		      ";

		List<CustomerCreditInfo> list = commonDao.queryBySql(CustomerCreditInfo.class,sql,null);
		if(list!=null&&!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}
}
