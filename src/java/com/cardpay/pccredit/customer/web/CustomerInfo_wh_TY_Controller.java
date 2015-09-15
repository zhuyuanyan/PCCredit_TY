package com.cardpay.pccredit.customer.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import sun.misc.BASE64Decoder;

import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.model.CustomerFirsthendBaseLocal;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.CustomerMarketing;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.model.LocalImage;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.intopieces.web.AddIntoPiecesForm;
import com.cardpay.pccredit.intopieces.web.LocalImageForm;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

@Controller
@RequestMapping("/customer/customerInfor_wh_ty/*")
@JRadModule("customer.customerInfor_wh_ty")
public class CustomerInfo_wh_TY_Controller extends BaseController {
	
	final public static String AREA_SEPARATOR  = "_";

	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	
	@Autowired
	private MaintenanceService maintenanceService;
	
	
	//太原维护信息
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		//查询客户经理
		List<AccountManagerParameterForm> forms = maintenanceService.findSubListManagerByManagerId(user);
		if(forms != null && forms.size() > 0){
			StringBuffer userIds = new StringBuffer();
			userIds.append("(");
			for(AccountManagerParameterForm form : forms){
				userIds.append("'").append(form.getUserId()).append("'").append(",");
			}
			userIds = userIds.deleteCharAt(userIds.length() - 1);
			userIds.append(")");
			filter.setCustManagerIds(userIds.toString());
		}else{
			filter.setUserId(userId);
		}
		QueryResult<IntoPieces> result = intoPiecesService.findintoPiecesByFilter(filter);
		JRadPagedQueryResult<IntoPieces> pagedResult = new JRadPagedQueryResult<IntoPieces>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/intopieces_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	//显示原始信息
	@ResponseBody
	@RequestMapping(value = "showInfo.page")
	public AbstractModelAndView showInfo(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/customer_iframe_1_change", request);
		String customerInforId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor info = customerInforService.findCustomerInforById(customerInforId);
			CustomerFirsthendBaseLocal baseLocal = customerInforService.findCustomerFirsthendLocalByNm(info.getTyCustomerId());
			mv.addObject("customerInfor", baseLocal);
			mv.addObject("customerNm", baseLocal.getKhnm());
			mv.addObject("customerInforId", customerInforId);
			
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(customerInforId).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//显示原始信息
	@ResponseBody
	@RequestMapping(value = "saveTyLocal.json")
	public JRadReturnMap saveTyLocal(@ModelAttribute CustomerFirsthendBaseLocalForm form,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			CustomerFirsthendBaseLocal customerFirsthendBaseLocal = form.createModel(CustomerFirsthendBaseLocal.class);
			customerInforService.updateCustomerFirsthendLocal(customerFirsthendBaseLocal);
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
		
	//显示维护信息--负债
	@ResponseBody
	@RequestMapping(value = "report_fz.page")
	public AbstractModelAndView report_fz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_fz", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetFz()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//显示维护信息--损益
	@ResponseBody
	@RequestMapping(value = "report_sy.page")
	public AbstractModelAndView report_sy(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_sy", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetSy()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//显示维护信息--金流
	@ResponseBody
	@RequestMapping(value = "report_jl.page")
	public AbstractModelAndView report_jl(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_jl", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetJl()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//显示负债信息--交叉
	@ResponseBody
	@RequestMapping(value = "report_jc.page")
	public AbstractModelAndView report_jc(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/report_jc", request);
		String appId = RequestHelper.getStringValue(request, "appId");
		if (StringUtils.isNotEmpty(appId)) {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			String tableContent = getFromBASE64(localExcel.getSheetJc()).replaceAll("\n", "<br>").replace("><br><", "><");
			mv.addObject("tableContent", tableContent);
			mv.addObject("appId", appId);
			
			//查询权限 非本人只能查看 不能操作
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String userId = user.getId();
			boolean lock = false;
			if(!customerInforService.findCustomerInforById(localExcel.getCustomerId()).getUserId().equals(userId)){
				lock = true;
			}
			mv.addObject("lock", lock);
		}
		return mv;
	}
	
	//修改资负
	@ResponseBody
	@RequestMapping(value = "change_report_fz.json")
	public JRadReturnMap change_report_fz(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetFz(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
		
	//修改损益
	@ResponseBody
	@RequestMapping(value = "change_report_sy.json")
	public JRadReturnMap change_report_sy(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetSy(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
		
	//修改金流
	@ResponseBody
	@RequestMapping(value = "change_report_jl.json")
	public JRadReturnMap change_report_jl(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetJl(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
		
	//修改交叉
	@ResponseBody
	@RequestMapping(value = "change_report_jc.json")
	public JRadReturnMap change_report_jc(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		String appId = RequestHelper.getStringValue(request, "appId");
		try {
			LocalExcel localExcel = addIntoPiecesService.findLocalEXcelByApplication(appId);
			localExcel.setSheetJc(request.getParameter("content"));
			addIntoPiecesService.change_localExcel(localExcel);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	//修改影像资料
	@ResponseBody
	@RequestMapping(value = "imageImport.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView imageImport(@ModelAttribute AddIntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<LocalImageForm> result = addIntoPiecesService.findLocalImageByApplication(filter);
		JRadPagedQueryResult<LocalImageForm> pagedResult = new JRadPagedQueryResult<LocalImageForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInfor_wh_ty/image_import",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		
		//查询权限 非本人只能查看 不能操作
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		boolean lock = false;
		CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoByApplicationId(filter.getApplicationId());
		if(!customerInforService.findCustomerInforById(customerApplicationInfo.getCustomerId()).getUserId().equals(userId)){
			lock = true;
		}
		mv.addObject("lock", lock);
		
		return mv;
	}
	
	//上传影像资料
	@ResponseBody
	@RequestMapping(value = "imageImport.json")
	public Map<String, Object> imageImport(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
				return map;
			}
			String applicationId = request.getParameter("applicationId");
			CustomerApplicationInfo customerApplicationInfo = intoPiecesService.findCustomerApplicationInfoById(applicationId);
			addIntoPiecesService.importImage(file,customerApplicationInfo.getProductId(),customerApplicationInfo.getCustomerId(),applicationId);
			map.put(JRadConstants.SUCCESS, true);
			map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTSUCCESS);
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			map.put(JRadConstants.SUCCESS, false);
			map.put(JRadConstants.MESSAGE, "上传失败:"+e.getMessage());
			JSONObject obj = JSONObject.fromObject(map);
			response.getWriter().print(obj.toString());
		}
		return null;
	}
	
	//删除影像资料
	@ResponseBody
	@RequestMapping(value = "deleteImage.json")
	public JRadReturnMap deleteImage(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			String id = request.getParameter("id");
			addIntoPiecesService.deleteImage(id);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	//base64解码
	public static String getFromBASE64(String s) { 
    	if (s == null) return null; 
    	BASE64Decoder decoder = new BASE64Decoder(); 
    	try { 
    	byte[] b = decoder.decodeBuffer(s); 
    	return new String(b); 
    	} catch (Exception e) { 
    	return null; 
    	} 
	} 
}

	