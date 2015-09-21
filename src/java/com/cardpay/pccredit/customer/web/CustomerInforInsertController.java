package com.cardpay.pccredit.customer.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.common.IdCardValidate;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.datapri.constant.DataPriConstants;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.jrad.modules.privilege.model.User;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

@Controller
@RequestMapping("/customer/basiccustomerinforCreate/*")
@JRadModule("customer.basiccustomerinforCreate")
public class CustomerInforInsertController extends BaseController{
	
	@Autowired
	private CustomerInforService customerInforService;
	/**
	 * 跳转到增加客户信息页面
	 * 
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "insert.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView create(HttpServletRequest request) {        
		JRadModelAndView mv = new JRadModelAndView("/customer/customerInforInsert/customerinfor_create", request);
		return mv;
	}
	
	/**
	 * 执行添加客户信息
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */

	@ResponseBody
	@RequestMapping(value = "insert.json")
	@JRadOperation(JRadOperation.CREATE)
	public JRadReturnMap insert(@ModelAttribute CustomerInforForm customerinfoForm, HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		if (returnMap.isSuccess()) {
			try {
				CustomerInforFilter filter = new CustomerInforFilter();
				filter.setCardId(customerinfoForm.getCardId());
				//身份证号码验证
				String msg = IdCardValidate.IDCardValidate(customerinfoForm.getCardId());
				if(msg !="" && msg != null){
					returnMap.put(JRadConstants.MESSAGE, msg);
					returnMap.put(JRadConstants.SUCCESS, false);
					return returnMap;
				}
				filter.setCardType(customerinfoForm.getCardType());
				List<CustomerInfor> ls = customerInforService.findCustomerInforByFilter(filter).getItems();
				if(ls != null && ls.size()>0){
					returnMap.put(JRadConstants.MESSAGE, "证件号码已存在");
					returnMap.put(JRadConstants.SUCCESS, false);
					return returnMap;
				}
				CustomerInfor customerinfor = customerinfoForm.createModel(CustomerInfor.class);
				User user = (User) Beans.get(LoginManager.class).getLoggedInUser(request);
				customerinfor.setCreatedBy(user.getId());
				customerinfor.setUserId(user.getId());
				String id = customerInforService.insertCustomerInfor(customerinfor);
				returnMap.put(RECORD_ID, id);
				returnMap.addGlobalMessage(CREATE_SUCCESS);
			}catch (Exception e) {
				returnMap.put(JRadConstants.MESSAGE, DataPriConstants.SYS_EXCEPTION_MSG);
				returnMap.put(JRadConstants.SUCCESS, false);
				return WebRequestHelper.processException(e);
			}
		}else{
			returnMap.setSuccess(false);
			returnMap.addGlobalError(CustomerInforConstant.CREATEERROR);
		}
		return returnMap;
	}
}
