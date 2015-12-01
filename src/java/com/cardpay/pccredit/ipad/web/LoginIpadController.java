package com.cardpay.pccredit.ipad.web;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cardpay.pccredit.ipad.util.JsonDateValueProcessor;
import com.cardpay.pccredit.product.model.AppendixDict;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.wicresoft.jrad.base.auth.AuthResult;
import com.wicresoft.jrad.base.auth.IAuthMgr;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.auth.AuthResult.AuthResultType;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.message.Errors;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.modules.log.business.LoginLogManager;

@Controller
public class LoginIpadController extends BaseController{
	
	@Autowired
	private IAuthMgr authMgr;
	@Autowired
	private LoginManager loginManager;
	/**
	 * 登录
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ipad/login/browse.json", method = { RequestMethod.GET })
	public String browse(HttpServletRequest request) {
		String username=request.getParameter("user_name");
		String password=request.getParameter("pass_word");
		AuthResult authResult = authMgr.authUserByLogin(username, password);
		AuthResultType authResultType = authResult.getResultType();
		String errorCode = null;
		if (AuthResultType.AUTH_ACCOUNT_NOT_EXIST.equals(authResultType)) {
			errorCode = "system.auth.accountNotExist";
		}
		else if (AuthResultType.AUTH_ACCOUNT_PASSWORD_ERROR.equals(authResultType)) {
			errorCode = "system.auth.passwordError";
		}
		else if (AuthResultType.AUTH_ACCOUNT_NOT_ACTIVE.equals(authResultType)) {
			errorCode = "system.auth.accountNotActive";
		}
		else if (AuthResultType.AUTH_ACCOUNT_NOT_LOCAL.equals(authResultType)) {
//			errorCode = "system.auth.accountNotLocal";
		}
		Map<String,Object> result = new LinkedHashMap<String,Object>();
		if (errorCode != null) {
			result.put("result", "fail");
		}else{
			result.put("result", "success");
			IUser user = authResult.getUser();
			result.put("displayName", user.getDisplayName());
			result.put("userId", user.getId());
		}
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.registerJsonValueProcessor(Date.class,new JsonDateValueProcessor());
		JSONObject json = JSONObject.fromObject(result, jsonConfig);
		return json.toString();
	}
	
}
