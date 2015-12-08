package com.cardpay.pccredit.intopieces.web;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cardpay.pccredit.common.UploadFileTool;
import com.cardpay.pccredit.customer.constant.CustomerInforConstant;
import com.cardpay.pccredit.customer.filter.CustomerInforFilter;
import com.cardpay.pccredit.customer.model.CustomerCareersInformation;
import com.cardpay.pccredit.customer.model.CustomerInfor;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.intopieces.constant.CardStatus;
import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.constant.IntoPiecesException;
import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.IntoPiecesFilter;
import com.cardpay.pccredit.intopieces.filter.MakeCardFilter;
import com.cardpay.pccredit.intopieces.model.CustomerAccountData;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationCom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContact;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationContactVo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantor;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationGuarantorVo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationInfo;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationOther;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecom;
import com.cardpay.pccredit.intopieces.model.CustomerApplicationRecomVo;
import com.cardpay.pccredit.intopieces.model.Dzjy;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.model.LocalExcel;
import com.cardpay.pccredit.intopieces.model.MakeCard;
import com.cardpay.pccredit.intopieces.service.AddIntoPiecesService;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;
import com.cardpay.pccredit.product.filter.ProductFilter;
import com.cardpay.pccredit.product.model.AddressAccessories;
import com.cardpay.pccredit.product.model.AppendixDict;
import com.cardpay.pccredit.product.model.ProductAttribute;
import com.cardpay.pccredit.product.service.ProductService;
import com.cardpay.pccredit.product.web.ProductAttributeForm;
import com.wicresoft.jrad.base.auth.IUser;
import com.wicresoft.jrad.base.auth.JRadModule;
import com.wicresoft.jrad.base.auth.JRadOperation;
import com.wicresoft.jrad.base.constant.JRadConstants;
import com.wicresoft.jrad.base.database.id.IDGenerator;
import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.QueryResult;
import com.wicresoft.jrad.base.i18n.I18nHelper;
import com.wicresoft.jrad.base.web.DataBindHelper;
import com.wicresoft.jrad.base.web.JRadModelAndView;
import com.wicresoft.jrad.base.web.controller.BaseController;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;
import com.wicresoft.jrad.base.web.result.JRadPagedQueryResult;
import com.wicresoft.jrad.base.web.result.JRadReturnMap;
import com.wicresoft.jrad.base.web.security.LoginManager;
import com.wicresoft.jrad.base.web.utility.WebRequestHelper;
import com.wicresoft.util.spring.Beans;
import com.wicresoft.util.spring.mvc.mv.AbstractModelAndView;

@Controller
@RequestMapping("/intopieces/addIntopieces/*")
@JRadModule("intopieces.addIntopieces")
public class AddIntoPiecesControl extends BaseController {

	@Autowired
	private IntoPiecesService intoPiecesService;

	@Autowired
	private CustomerInforService customerInforService;

	@Autowired
	private ProductService productService;
	
	@Autowired
	private CustomerInforService customerInforservice;
	
	@Autowired
	private AddIntoPiecesService addIntoPiecesService;
	
	//选择产品
	@ResponseBody
	@RequestMapping(value = "browseProduct.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browseProduct(@ModelAttribute ProductFilter filter, HttpServletRequest request) {
		filter.setRequest(request);

		QueryResult<ProductAttribute> result = productService.findProductsByFilter(filter);
		JRadPagedQueryResult<ProductAttribute> pagedResult = new JRadPagedQueryResult<ProductAttribute>(filter, result);

		JRadModelAndView mv = new JRadModelAndView("/intopieces/product_browse", request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}

	//选择客户
	@ResponseBody
	@RequestMapping(value = "browseCustomer.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView browseCustomer(@ModelAttribute CustomerInforFilter filter,HttpServletRequest request) {
        filter.setRequest(request);
        IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
		filter.setUserId(user.getId());
		QueryResult<CustomerInfor> result = customerInforservice.findCustomerInforByFilterAndProductId(filter);
		JRadPagedQueryResult<CustomerInfor> pagedResult = new JRadPagedQueryResult<CustomerInfor>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/customer_browse",request);
		mv.addObject(PAGED_RESULT, pagedResult);

		return mv;
	}
	
	//导入调查报告
	@ResponseBody
	@RequestMapping(value = "reportImport.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView reportImport(@ModelAttribute AddIntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<LocalExcelForm> result = addIntoPiecesService.findLocalExcelByProductAndCustomer(filter);
		JRadPagedQueryResult<LocalExcelForm> pagedResult = new JRadPagedQueryResult<LocalExcelForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/report_import",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		
		return mv;
	}
	
	//导入调查报告
	@ResponseBody
	@RequestMapping(value = "reportImport.json")
	public Map<String, Object> reportImport_json(@RequestParam(value = "file", required = false) MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception {        
		response.setContentType("text/html;charset=utf-8");
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			if(file==null||file.isEmpty()){
				map.put(JRadConstants.SUCCESS, false);
				map.put(JRadConstants.MESSAGE, CustomerInforConstant.IMPORTEMPTY);
				return map;
			}
			String productId = request.getParameter("productId");
			String customerId = request.getParameter("customerId");
			addIntoPiecesService.importExcel(file,productId,customerId);
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
	
	//上传影像资料
	@ResponseBody
	@RequestMapping(value = "imageImport.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView imageImport(@ModelAttribute AddIntoPiecesFilter filter,HttpServletRequest request) {
		filter.setRequest(request);
		QueryResult<LocalImageForm> result = addIntoPiecesService.findLocalImageByProductAndCustomer(filter);
		JRadPagedQueryResult<LocalImageForm> pagedResult = new JRadPagedQueryResult<LocalImageForm>(filter, result);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/image_import",request);
		mv.addObject(PAGED_RESULT, pagedResult);
		
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
			String productId = request.getParameter("productId");
			String customerId = request.getParameter("customerId");
			addIntoPiecesService.importImage(file,productId,customerId,null);
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
	
	//提交申请
	@ResponseBody
	@RequestMapping(value = "addIntopieces.json", method = { RequestMethod.GET })
	public JRadReturnMap addIntopieces(@ModelAttribute AddIntoPiecesForm addIntoPiecesForm,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			addIntoPiecesService.addIntopieces(addIntoPiecesForm,loginId);
			
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	/**
	 * 查询 建议
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "changewh.page")
	@JRadOperation(JRadOperation.MAINTENANCE)
	public AbstractModelAndView changewh(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/cframe", request);
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dzjy  dzjy=  addIntoPiecesService.findLocalImageByApplication(customerId,productId);
		/*String customerInforId = RequestHelper.getStringValue(request, ID);
		if (StringUtils.isNotEmpty(customerInforId)) {
			CustomerInfor customerInfor = customerInforservice.findCustomerInforById(customerInforId);
			mv.addObject("customerInfor", customerInfor);
			mv.addObject("customerId", customerInfor.getId());
		}*/
		mv.addObject("dzjy", dzjy);
		mv.addObject("productId", productId);
		mv.addObject("customerId",customerId);
		return mv;
	}
	
	
	//保存建议
	@ResponseBody
	@RequestMapping(value = "addJy.json", method = { RequestMethod.GET })
	public JRadReturnMap addJy(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			
			String customerId = request.getParameter("customerId");//客户 id
			String productId = request.getParameter("productId");//产品id
			String sqrxm = request.getParameter("sqrxm");
			String sqje = request.getParameter("sqje");
			String dkyt = request.getParameter("dkyt");
			String sqqx = request.getParameter("sqqx");
			String xmzje = request.getParameter("xmzje");
			String zyzj = request.getParameter("zyzj");
			String zyzjly = request.getParameter("zyzjly");
			String change = request.getParameter("change");
			String fxfx_ys = request.getParameter("fxfx_ys");
			String fxfx_ns = request.getParameter("fxfx_ns");
			String fxfx_lxfx = request.getParameter("fxfx_lxfx");
			String khjljy = request.getParameter("khjljy");
			String reason1 = request.getParameter("reason1");
			String jytg_je = request.getParameter("jytg_je");
			String jytg_qx = request.getParameter("jytg_qx");
			String jytg_cp = request.getParameter("jytg_cp");
			String jytg_lv = request.getParameter("jytg_lv");
			String jytg_myhk = request.getParameter("jytg_myhk");
			String jytg_bl = request.getParameter("jytg_bl");
			String jytg_jkr = request.getParameter("jytg_jkr");
			String jytg_khgx = request.getParameter("jytg_khgx");
			String jytg_dbr = request.getParameter("jytg_dbr");
			String jytg_dbrgx = request.getParameter("jytg_dbrgx");
			String jytg_dy = request.getParameter("jytg_dy");
			String jytg_wq = request.getParameter("jytg_wq");
			String zbkhjl_sign = request.getParameter("zbkhjl_sign");
			String xbkhjl_sign = request.getParameter("xbkhjl_sign");
			String rq = request.getParameter("rq");
			String sqr_xm = request.getParameter("sqr_xm");
			String sqr_sex = request.getParameter("sqr_sex");
			String sqr_zjhm = request.getParameter("sqr_zjhm");
			String sqr_hy = request.getParameter("sqr_hy");
			String sqr_hjd = request.getParameter("sqr_hjd");
			String sqr_hjxx = request.getParameter("sqr_hjxx");
			String sqr_xl = request.getParameter("sqr_xl");
			String sqr_mobile = request.getParameter("sqr_mobile");
			String sqr_address = request.getParameter("sqr_address");
			String jzhj_type = request.getParameter("jzhj_type");
			String jzhj_type4_qx = request.getParameter("jzhj_type4_qx");
			String jzhj_type4_zj = request.getParameter("jzhj_type4_zj");
			String jzhj_type_other = request.getParameter("jzhj_type_other");
			String jzhj_mj1 = request.getParameter("jzhj_mj1");
			String jzhj_mj2 = request.getParameter("jzhj_mj2");
			String jzhj_mj3 = request.getParameter("jzhj_mj3");
			String jzhj_zf = request.getParameter("jzhj_zf");
			String jzhj_zf_select = request.getParameter("jzhj_zf_select");
			String jzhj_gj = request.getParameter("jzhj_gj");
			String jzhj_gj_room = request.getParameter("jzhj_gj_room");
			String jzhj_gj_ting = request.getParameter("jzhj_gj_ting");
			String jzhj_jzrq = request.getParameter("jzhj_jzrq");
			String jzhj_dzfs = request.getParameter("jzhj_dzfs");
			String jzhj_select = request.getParameter("jzhj_select");
			String zyfc_num = request.getParameter("zyfc_num");
			String aj_num = request.getParameter("aj_num");
			String fcgmrq = request.getParameter("fcgmrq");
			String fcgmjg = request.getParameter("fcgmjg");
			String fcmj = request.getParameter("fcmj");
			String ajdkye = request.getParameter("ajdkye");
			String address = request.getParameter("address");
			String zycl_num = request.getParameter("zycl_num");
			String dk_num = request.getParameter("dk_num");
			String gmrq = request.getParameter("gmrq");
			String gmjg = request.getParameter("gmjg");
			String cp = request.getParameter("cp");
			String other_work = request.getParameter("other_work");
			String other_income = request.getParameter("other_income");
			String po_name = request.getParameter("po_name");
			String po_code = request.getParameter("po_code");
			String po_mobile = request.getParameter("po_mobile");
			String po_yicome = request.getParameter("po_yicome");
			String po_unit = request.getParameter("po_unit");
			String p_bank_numm = request.getParameter("p_bank_numm");
			String khh_1 = request.getParameter("khh_1");
			String zh1 = request.getParameter("zh1");
			String fzc = request.getParameter("fzc");
			String khh_2 = request.getParameter("khh_2");
			String zh2 = request.getParameter("zh2");
			String fzfz = request.getParameter("fzfz");
			
			String jzhj_type_select = request.getParameter("jzhj_type_select");
			
			//查询建议是否存在customerId和productId
			Dzjy dzjy =  addIntoPiecesService.findLocalImageByApplication(customerId,productId);
			
			if(dzjy != null){
//				dzjy.setCustomer_id(request.getParameter("customerId"));
//				dzjy.setProduct_id(request.getParameter("productId"));
				dzjy.setSqrxm(sqrxm);
				dzjy.setSqje(sqje);
				dzjy.setDkyt(dkyt);
				dzjy.setSqqx(sqqx);
				dzjy.setXmzje(xmzje);
				dzjy.setZyzj(zyzj);
				dzjy.setZyzjly(zyzjly);
				dzjy.setChange(change);
				dzjy.setFxfx_ys(fxfx_ys);
				dzjy.setFxfx_ns(fxfx_ns);
				dzjy.setFxfx_lxfx(fxfx_lxfx);
				dzjy.setKhjljy(khjljy);
				dzjy.setReason1(reason1);
				dzjy.setJytg_je(jytg_je);
				dzjy.setJytg_qx(jytg_qx);
				dzjy.setJytg_cp(jytg_cp);
				dzjy.setJytg_lv(jytg_lv);
				dzjy.setJytg_myhk(jytg_myhk);
				dzjy.setJytg_bl(jytg_bl);
				dzjy.setJytg_jkr(jytg_jkr);
				dzjy.setJytg_khgx(jytg_khgx);
				dzjy.setJytg_dbr(jytg_dbr);
				dzjy.setJytg_dbrgx(jytg_dbrgx);
				dzjy.setJytg_dy(jytg_dy);
				dzjy.setJytg_wq(jytg_wq);
				dzjy.setZbkhjl_sign(zbkhjl_sign);
				dzjy.setXbkhjl_sign(xbkhjl_sign);
				dzjy.setRq(rq);
				dzjy.setSqr_xm(sqr_xm);
				dzjy.setSqr_sex(sqr_sex);
				dzjy.setSqr_zjhm(sqr_zjhm);
				dzjy.setSqr_hy(sqr_hy);
				dzjy.setSqr_hjd(sqr_hjd);
				dzjy.setSqr_hjxx(sqr_hjxx);
				dzjy.setSqr_xl(sqr_xl);
				dzjy.setSqr_mobile(sqr_mobile);
				dzjy.setSqr_address(sqr_address);
				dzjy.setJzhj_type(jzhj_type);
				dzjy.setJzhj_type4_qx(jzhj_type4_qx);
				dzjy.setJzhj_type4_zj(jzhj_type4_zj);
				dzjy.setJzhj_type_other(jzhj_type_other);
				dzjy.setJzhj_mj1(jzhj_mj1);
				dzjy.setJzhj_mj2(jzhj_mj2);
				dzjy.setJzhj_mj3(jzhj_mj3);
				dzjy.setJzhj_zf(jzhj_zf);
				dzjy.setJzhj_zf_select(jzhj_zf_select);
				dzjy.setJzhj_gj(jzhj_gj);
				dzjy.setJzhj_gj_room(jzhj_gj_room);
				dzjy.setJzhj_gj_ting(jzhj_gj_ting);
				dzjy.setJzhj_jzrq(jzhj_jzrq);
				dzjy.setJzhj_dzfs(jzhj_dzfs);
				dzjy.setJzhj_select(jzhj_select);
				dzjy.setZyfc_num(zyfc_num);
				dzjy.setAj_num(aj_num);
				dzjy.setFcgmrq(fcgmrq);
				dzjy.setFcgmjg(fcgmjg);
				dzjy.setFcmj(fcmj);
				dzjy.setAjdkye(ajdkye);
				dzjy.setAddress(address);
				dzjy.setZycl_num(zycl_num);
				dzjy.setDk_num(dk_num);
				dzjy.setGmrq(gmrq);
				dzjy.setGmjg(gmjg);
				dzjy.setCp(cp);
				dzjy.setOther_work(other_work);
				dzjy.setOther_income(other_income);
				dzjy.setPo_name(po_name);
				dzjy.setPo_code(po_code);
				dzjy.setPo_mobile(po_mobile);
				dzjy.setPo_yicome(po_yicome);
				dzjy.setPo_unit(po_unit);
				dzjy.setP_bank_numm(p_bank_numm);
				dzjy.setKhh_1(khh_1);
				dzjy.setZh1(zh1);
				dzjy.setFzc(fzc);
				dzjy.setKhh_2(khh_2);
				dzjy.setZh2(zh2);
				dzjy.setFzfz(fzfz);
				dzjy.setJzhj_type_select(jzhj_type_select);
				//update
				addIntoPiecesService.updateJy(dzjy);
			}else{
				Dzjy dzjy1 = new Dzjy();
				dzjy1.setCustomer_id(request.getParameter("customerId"));
				dzjy1.setProduct_id(request.getParameter("productId"));
				dzjy1.setSqrxm(sqrxm);
				dzjy1.setSqje(sqje);
				dzjy1.setDkyt(dkyt);
				dzjy1.setSqqx(sqqx);
				dzjy1.setXmzje(xmzje);
				dzjy1.setZyzj(zyzj);
				dzjy1.setZyzjly(zyzjly);
				dzjy1.setChange(change);
				dzjy1.setFxfx_ys(fxfx_ys);
				dzjy1.setFxfx_ns(fxfx_ns);
				dzjy1.setFxfx_lxfx(fxfx_lxfx);
				dzjy1.setKhjljy(khjljy);
				dzjy1.setReason1(reason1);
				dzjy1.setJytg_je(jytg_je);
				dzjy1.setJytg_qx(jytg_qx);
				dzjy1.setJytg_cp(jytg_cp);
				dzjy1.setJytg_lv(jytg_lv);
				dzjy1.setJytg_myhk(jytg_myhk);
				dzjy1.setJytg_bl(jytg_bl);
				dzjy1.setJytg_jkr(jytg_jkr);
				dzjy1.setJytg_khgx(jytg_khgx);
				dzjy1.setJytg_dbr(jytg_dbr);
				dzjy1.setJytg_dbrgx(jytg_dbrgx);
				dzjy1.setJytg_dy(jytg_dy);
				dzjy1.setJytg_wq(jytg_wq);
				dzjy1.setZbkhjl_sign(zbkhjl_sign);
				dzjy1.setXbkhjl_sign(xbkhjl_sign);
				dzjy1.setRq(rq);
				dzjy1.setSqr_xm(sqr_xm);
				dzjy1.setSqr_sex(sqr_sex);
				dzjy1.setSqr_zjhm(sqr_zjhm);
				dzjy1.setSqr_hy(sqr_hy);
				dzjy1.setSqr_hjd(sqr_hjd);
				dzjy1.setSqr_hjxx(sqr_hjxx);
				dzjy1.setSqr_xl(sqr_xl);
				dzjy1.setSqr_mobile(sqr_mobile);
				dzjy1.setSqr_address(sqr_address);
				dzjy1.setJzhj_type(jzhj_type);
				dzjy1.setJzhj_type4_qx(jzhj_type4_qx);
				dzjy1.setJzhj_type4_zj(jzhj_type4_zj);
				dzjy1.setJzhj_type_other(jzhj_type_other);
				dzjy1.setJzhj_mj1(jzhj_mj1);
				dzjy1.setJzhj_mj2(jzhj_mj2);
				dzjy1.setJzhj_mj3(jzhj_mj3);
				dzjy1.setJzhj_zf(jzhj_zf);
				dzjy1.setJzhj_zf_select(jzhj_zf_select);
				dzjy1.setJzhj_gj(jzhj_gj);
				dzjy1.setJzhj_gj_room(jzhj_gj_room);
				dzjy1.setJzhj_gj_ting(jzhj_gj_ting);
				dzjy1.setJzhj_jzrq(jzhj_jzrq);
				dzjy1.setJzhj_dzfs(jzhj_dzfs);
				dzjy1.setJzhj_select(jzhj_select);
				dzjy1.setZyfc_num(zyfc_num);
				dzjy1.setAj_num(aj_num);
				dzjy1.setFcgmrq(fcgmrq);
				dzjy1.setFcgmjg(fcgmjg);
				dzjy1.setFcmj(fcmj);
				dzjy1.setAjdkye(ajdkye);
				dzjy1.setAddress(address);
				dzjy1.setZycl_num(zycl_num);
				dzjy1.setDk_num(dk_num);
				dzjy1.setGmrq(gmrq);
				dzjy1.setGmjg(gmjg);
				dzjy1.setCp(cp);
				dzjy1.setOther_work(other_work);
				dzjy1.setOther_income(other_income);
				dzjy1.setPo_name(po_name);
				dzjy1.setPo_code(po_code);
				dzjy1.setPo_mobile(po_mobile);
				dzjy1.setPo_yicome(po_yicome);
				dzjy1.setPo_unit(po_unit);
				dzjy1.setP_bank_numm(p_bank_numm);
				dzjy1.setKhh_1(khh_1);
				dzjy1.setZh1(zh1);
				dzjy1.setFzc(fzc);
				dzjy1.setKhh_2(khh_2);
				dzjy1.setZh2(zh2);
				dzjy1.setFzfz(fzfz);
				dzjy1.setJzhj_type_select(jzhj_type_select);
				//save
				addIntoPiecesService.saveJy(dzjy1);
			}
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
}
