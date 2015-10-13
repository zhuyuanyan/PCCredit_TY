package com.cardpay.pccredit.customer.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.customer.model.CustomerFirsthendBaseLocal;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.model.TyRepayYehz;
import com.cardpay.pccredit.customer.model.TyRepayYehzVo;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.MaintenanceService;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;
import com.wicresoft.util.web.RequestHelper;

/**
 * 客户余额信息
 * @author songchen
 *
 */
@Controller
@RequestMapping("/customer/customerInfor_yexx/*")
@JRadModule("customer.customerInfor_yexx")
public class CustomerInfo_yexx_Controller extends BaseController {
	
	Logger logger = Logger.getLogger(this.getClass());
	
	@Autowired
	private CustomerInforService customerInforService;

	
	
	//显示余额信息
	@ResponseBody
	@RequestMapping(value = "browse.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browse(@ModelAttribute IntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		String userId = user.getId();
		
		//查询客户经理
	/*	List<AccountManagerParameterForm> forms = maintenanceService.findSubListManagerByManagerId(user);
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
		*/
		QueryResult<TyRepayYehzVo> result = customerInforService.findCustomerYexxByFilter(filter);
		JRadPagedQueryResult<TyRepayYehzVo> pagedResult = new JRadPagedQueryResult<TyRepayYehzVo>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/customer/CustomerInfo_yexx/customer_yexx_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);
		return mv;
	}
	
	
	
}

	