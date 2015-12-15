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
import com.cardpay.pccredit.customer.model.CustomerInforUpdateBalanceSheet;
import com.cardpay.pccredit.customer.service.CustomerInforService;
import com.cardpay.pccredit.customer.service.CustomerInforUpdateService;
import com.cardpay.pccredit.customer.web.MaintenanceForm;
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
import com.cardpay.pccredit.intopieces.model.Dcddpz;
import com.cardpay.pccredit.intopieces.model.DcddpzForm;
import com.cardpay.pccredit.intopieces.model.Dclrjb;
import com.cardpay.pccredit.intopieces.model.DclrjbForm;
import com.cardpay.pccredit.intopieces.model.Dcsczt;
import com.cardpay.pccredit.intopieces.model.DcscztForm;
import com.cardpay.pccredit.intopieces.model.Dzjbzk;
import com.cardpay.pccredit.intopieces.model.Dzjy;
import com.cardpay.pccredit.intopieces.model.Dzjyzt;
import com.cardpay.pccredit.intopieces.model.DzjyztForm;
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
	
	@Autowired
	private CustomerInforUpdateService customerInforUpdateService;
	
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
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView changewh(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/cframe", request);
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dzjy  dzjy=  addIntoPiecesService.findLocalImageByApplication(customerId,productId);
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
	
	
	/**
	 * 查询  基本状况
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "findJbzk.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView findJbzk(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_jbzk", request);
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dzjbzk jbzk = addIntoPiecesService.findDzjbzk(customerId,productId);
		mv.addObject("jbzk", jbzk);
		mv.addObject("productId", productId);
		mv.addObject("customerId",customerId);
		return mv;
	}
	
	
	/**
	 * add 基本状况
	 */
	@ResponseBody
	@RequestMapping(value = "addJbzk.json", method = { RequestMethod.GET })
	public JRadReturnMap addJbzk(HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			String customerId = request.getParameter("customerId");//客户 id
			String productId = request.getParameter("productId");// 产品Id
			

			 String mx                  = request.getParameter("mx");
			 String zj_type             = request.getParameter("zj_type");
			 String mj                  = request.getParameter("mj");
			 String fr                  = request.getParameter("fr");
			 String kzr                 = request.getParameter("kzr");
			 String zb                  = request.getParameter("zb");
			 String gy                  = request.getParameter("gy");
			 String ywfw                = request.getParameter("ywfw");
			 String yezz                = request.getParameter("yezz");
			 String yezz_select         = request.getParameter("yezz_select");
			 String zm                  = request.getParameter("zm");
			 String ksrq                = request.getParameter("ksrq");
			 String address             = request.getParameter("address");
			 String phone               = request.getParameter("phone");
			 String dp_type             = request.getParameter("dp_type");
			 String dp_select           = request.getParameter("dp_select");
			 String dp_qx               = request.getParameter("dp_qx");
			 String dp_zj               = request.getParameter("dp_zj");
			 String dp_other            = request.getParameter("dp_other");
			 String dp_zx               = request.getParameter("dp_zx");
			 String dp_zx_select        = request.getParameter("dp_zx_select");
			 String dp_qy_rq            = request.getParameter("dp_qy_rq");
			 String zh                  = request.getParameter("zh");
			 String m_bank              = request.getParameter("m_bank");
			 String m_zh                = request.getParameter("m_zh");
			 String bank2               = request.getParameter("bank2");
			 String zh2                 = request.getParameter("zh2");
			 String jl                  = request.getParameter("jl");
			 String ms                  = request.getParameter("ms");
			 String zx_zk               = request.getParameter("zx_zk");
			 String zx_select           = request.getParameter("zx_select");
			 String zx_sm               = request.getParameter("zx_sm");
			 String zx_card_num         = request.getParameter("zx_card_num");
			 String zx_creddit          = request.getParameter("zx_creddit");
			 String zx_yy               = request.getParameter("zx_yy");
			 String zx_tz               = request.getParameter("zx_tz");
			 String zx_yq               = request.getParameter("zx_yq");
			 String zx_ls_num           = request.getParameter("zx_ls_num");
			 String zx_wdq_num          = request.getParameter("zx_wdq_num");
			 String zx_dk_yy            = request.getParameter("zx_dk_yy");
			 String zx_yq_qk            = request.getParameter("zx_yq_qk");
			 String zx_dbr_ornot        = request.getParameter("zx_dbr_ornot");
			 String zx_db_ye            = request.getParameter("zx_db_ye");
			 String zx_db_yt            = request.getParameter("zx_db_yt");
			 String zx_db_qx            = request.getParameter("zx_db_qx");
			 String zx_zx_seach_num     = request.getParameter("zx_zx_seach_num");
			 String sqr_family          = request.getParameter("sqr_family");
			 String sqr_family_relation = request.getParameter("sqr_family_relation");
			 String sqr_eco_num         = request.getParameter("sqr_eco_num");
			 String sqr_bad_hobby       = request.getParameter("sqr_bad_hobby");
			 String sqr_bad_habit       = request.getParameter("sqr_bad_habit");
			 String sqr_work_pl         = request.getParameter("sqr_work_pl");
			 String sqr_zz_change_pl    = request.getParameter("sqr_zz_change_pl");
			 String sqr_mark            = request.getParameter("sqr_mark");
			 String sqr_zz_qk           = request.getParameter("sqr_zz_qk");
			 String sqr_bx              = request.getParameter("sqr_bx");
			 String sqr_child_qk        = request.getParameter("sqr_child_qk");
			 String sqr_child_edu       = request.getParameter("sqr_child_edu");
			 String sqr_child_unit      = request.getParameter("sqr_child_unit");
			 String sqr_society_gx      = request.getParameter("sqr_society_gx");
			 String sqr_soc_gy          = request.getParameter("sqr_soc_gy");
			 String sqr_wf              = request.getParameter("sqr_wf");
			 String sqr_syfm            = request.getParameter("sqr_syfm");
			 String sqr_qq_zk           = request.getParameter("sqr_qq_zk");
			 String sqr_jr_pj           = request.getParameter("sqr_jr_pj");
			 String sqr_lj_pj           = request.getParameter("sqr_lj_pj");
			 String sqr_lxr_pj          = request.getParameter("sqr_lxr_pj");
			 String sqr_sy_pj           = request.getParameter("sqr_sy_pj");
			 String sqr_q_person        = request.getParameter("sqr_q_person");
			 String sqr_q_bus           = request.getParameter("sqr_q_bus");
			 String sqr_w_person        = request.getParameter("sqr_w_person");
			 String sqr_w_bus           = request.getParameter("sqr_w_bus");
			 String sqr_ztpj            = request.getParameter("sqr_ztpj");
			 String hy_work             = request.getParameter("hy_work");
			 String hy_xz               = request.getParameter("hy_xz");
			 String hy_fx               = request.getParameter("hy_fx");
			 String hy_qs               = request.getParameter("hy_qs");
			 String hy_zz               = request.getParameter("hy_zz");
			 String hy_ys_gl            = request.getParameter("hy_ys_gl");
			 String hy_gd_gl            = request.getParameter("hy_gd_gl");
			 String hy_pj_sale          = request.getParameter("hy_pj_sale");
			 String hy_pj_jl            = request.getParameter("hy_pj_jl");
			 String hy_gz               = request.getParameter("hy_gz");
			 String hy_fy               = request.getParameter("hy_fy");
			 String hy_yclje            = request.getParameter("hy_yclje");
			 String hy_policy           = request.getParameter("hy_policy");
			 String hy_desc             = request.getParameter("hy_desc");
			 String hy_pj_ml            = request.getParameter("hy_pj_ml");
			 
			 //查询是否存在
			 Dzjbzk jbzk = addIntoPiecesService.findDzjbzk(customerId,productId);
			 if(jbzk!=null){
				 //update
				 jbzk.setMx(mx);                 
				 jbzk.setZj_type(zj_type);             
				 jbzk.setMj(mj);                  
				 jbzk.setFr(fr);                  
				 jbzk.setKzr(kzr);                 
				 jbzk.setZb(zb);                  
				 jbzk.setGy(sqr_soc_gy);                  
				 jbzk.setYwfw(ywfw);                
				 jbzk.setYezz(yezz_select);                
				 jbzk.setYezz_select(yezz_select);         
				 jbzk.setZm(zm);                  
				 jbzk.setKsrq(ksrq);               
				 jbzk.setAddress(address);             
				 jbzk.setPhone(phone);              
				 jbzk.setDp_type(dp_type);             
				 jbzk.setDp_select(dp_select);           
				 jbzk.setDp_qx(dp_qx);               
				 jbzk.setDp_zj(dp_zj);               
				 jbzk.setDp_other(dp_other);            
				 jbzk.setDp_zx(dp_zx_select);             
				 jbzk.setDp_zx_select(dp_zx_select);       
				 jbzk.setDp_qy_rq(dp_qy_rq);           
				 jbzk.setZh(zh);                  
				 jbzk.setM_bank(m_bank);              
				 jbzk.setM_zh(m_zh);                
				 jbzk.setBank2(bank2);               
				 jbzk.setZh2(zh2);                
				 jbzk.setJl(jl);                  
				 jbzk.setMs(ms);                  
				 jbzk.setZx_zk(zx_zk);               
				 jbzk.setZx_select(zx_select);           
				 jbzk.setZx_sm(zx_sm);               
				 jbzk.setZx_card_num(zx_card_num);         
				 jbzk.setZx_creddit(zx_creddit);         
				 jbzk.setZx_yy(zx_yy);               
				 jbzk.setZx_tz(zx_tz);               
				 jbzk.setZx_yq(zx_yq_qk);               
				 jbzk.setZx_ls_num(zx_ls_num);          
				 jbzk.setZx_wdq_num(zx_wdq_num);          
				 jbzk.setZx_dk_yy(zx_dk_yy);           
				 jbzk.setZx_yq_qk(zx_yq_qk);            
				 jbzk.setZx_dbr_ornot(zx_dbr_ornot);        
				 jbzk.setZx_db_ye(zx_db_ye);            
				 jbzk.setZx_db_yt(zx_db_yt);            
				 jbzk.setZx_db_qx(zx_db_qx);            
				 jbzk.setZx_zx_seach_num(zx_zx_seach_num);     
				 jbzk.setSqr_family(sqr_family);          
				 jbzk.setSqr_family_relation(sqr_family_relation);
				 jbzk.setSqr_eco_num(sqr_eco_num);         
				 jbzk.setSqr_bad_hobby(sqr_bad_hobby);       
				 jbzk.setSqr_bad_habit(sqr_bad_habit);       
				 jbzk.setSqr_work_pl(sqr_work_pl);         
				 jbzk.setSqr_zz_change_pl(sqr_zz_change_pl);    
				 jbzk.setSqr_mark(sqr_mark);            
				 jbzk.setSqr_zz_qk(sqr_zz_qk);           
				 jbzk.setSqr_bx(sqr_bx);              
				 jbzk.setSqr_child_qk(sqr_child_qk);        
				 jbzk.setSqr_child_edu(sqr_child_edu);       
				 jbzk.setSqr_child_unit(sqr_child_unit);     
				 jbzk.setSqr_society_gx(sqr_society_gx);      
				 jbzk.setSqr_soc_gy(sqr_soc_gy);          
				 jbzk.setSqr_wf(sqr_wf);              
				 jbzk.setSqr_syfm(sqr_syfm);            
				 jbzk.setSqr_qq_zk(sqr_qq_zk);           
				 jbzk.setSqr_jr_pj(sqr_jr_pj);           
				 jbzk.setSqr_lj_pj(sqr_lj_pj);           
				 jbzk.setSqr_lxr_pj(sqr_lxr_pj);          
				 jbzk.setSqr_sy_pj(sqr_sy_pj);           
				 jbzk.setSqr_q_person(sqr_q_person);        
				 jbzk.setSqr_q_bus(sqr_q_bus);           
				 jbzk.setSqr_w_person(sqr_w_person);        
				 jbzk.setSqr_w_bus(sqr_w_bus);           
				 jbzk.setSqr_ztpj(sqr_ztpj);            
				 jbzk.setHy_work(hy_work);             
				 jbzk.setHy_xz(hy_xz);               
				 jbzk.setHy_fx(hy_fx);               
				 jbzk.setHy_qs(hy_qs);               
				 jbzk.setHy_zz(hy_zz);               
				 jbzk.setHy_ys_gl(hy_ys_gl);            
				 jbzk.setHy_gd_gl(hy_gd_gl);            
				 jbzk.setHy_pj_sale(hy_pj_sale);          
				 jbzk.setHy_pj_jl(hy_pj_jl);            
				 jbzk.setHy_gz(hy_gz);               
				 jbzk.setHy_fy(hy_fy);               
				 jbzk.setHy_yclje(hy_yclje);            
				 jbzk.setHy_policy(hy_policy);           
				 jbzk.setHy_desc(hy_desc);             
				 jbzk.setHy_pj_ml(hy_pj_ml); 
				//save
				 addIntoPiecesService.updateJbzk(jbzk);
			 }else {
				 //save
				 Dzjbzk jbzk1 = new Dzjbzk();
				 jbzk1.setCustomer_id(customerId);
				 jbzk1.setProduct_id(productId);
				 jbzk1.setMx(mx);                 
				 jbzk1.setZj_type(zj_type);             
				 jbzk1.setMj(mj);                  
				 jbzk1.setFr(fr);                  
				 jbzk1.setKzr(kzr);                 
				 jbzk1.setZb(zb);                  
				 jbzk1.setGy(sqr_soc_gy);                  
				 jbzk1.setYwfw(ywfw);                
				 jbzk1.setYezz(yezz_select);                
				 jbzk1.setYezz_select(yezz_select);         
				 jbzk1.setZm(zm);                  
				 jbzk1.setKsrq(ksrq);               
				 jbzk1.setAddress(address);             
				 jbzk1.setPhone(phone);              
				 jbzk1.setDp_type(dp_type);             
				 jbzk1.setDp_select(dp_select);           
				 jbzk1.setDp_qx(dp_qx);               
				 jbzk1.setDp_zj(dp_zj);               
				 jbzk1.setDp_other(dp_other);            
				 jbzk1.setDp_zx(dp_zx_select);             
				 jbzk1.setDp_zx_select(dp_zx_select);       
				 jbzk1.setDp_qy_rq(dp_qy_rq);           
				 jbzk1.setZh(zh);                  
				 jbzk1.setM_bank(m_bank);              
				 jbzk1.setM_zh(m_zh);                
				 jbzk1.setBank2(bank2);               
				 jbzk1.setZh2(zh2);                
				 jbzk1.setJl(jl);                  
				 jbzk1.setMs(ms);                  
				 jbzk1.setZx_zk(zx_zk);               
				 jbzk1.setZx_select(zx_select);           
				 jbzk1.setZx_sm(zx_sm);               
				 jbzk1.setZx_card_num(zx_card_num);         
				 jbzk1.setZx_creddit(zx_creddit);         
				 jbzk1.setZx_yy(zx_yy);               
				 jbzk1.setZx_tz(zx_tz);               
				 jbzk1.setZx_yq(zx_yq_qk);               
				 jbzk1.setZx_ls_num(zx_ls_num);          
				 jbzk1.setZx_wdq_num(zx_wdq_num);          
				 jbzk1.setZx_dk_yy(zx_dk_yy);           
				 jbzk1.setZx_yq_qk(zx_yq_qk);            
				 jbzk1.setZx_dbr_ornot(zx_dbr_ornot);        
				 jbzk1.setZx_db_ye(zx_db_ye);            
				 jbzk1.setZx_db_yt(zx_db_yt);            
				 jbzk1.setZx_db_qx(zx_db_qx);            
				 jbzk1.setZx_zx_seach_num(zx_zx_seach_num);     
				 jbzk1.setSqr_family(sqr_family);          
				 jbzk1.setSqr_family_relation(sqr_family_relation);
				 jbzk1.setSqr_eco_num(sqr_eco_num);         
				 jbzk1.setSqr_bad_hobby(sqr_bad_hobby);       
				 jbzk1.setSqr_bad_habit(sqr_bad_habit);       
				 jbzk1.setSqr_work_pl(sqr_work_pl);         
				 jbzk1.setSqr_zz_change_pl(sqr_zz_change_pl);    
				 jbzk1.setSqr_mark(sqr_mark);            
				 jbzk1.setSqr_zz_qk(sqr_zz_qk);           
				 jbzk1.setSqr_bx(sqr_bx);              
				 jbzk1.setSqr_child_qk(sqr_child_qk);        
				 jbzk1.setSqr_child_edu(sqr_child_edu);       
				 jbzk1.setSqr_child_unit(sqr_child_unit);     
				 jbzk1.setSqr_society_gx(sqr_society_gx);      
				 jbzk1.setSqr_soc_gy(sqr_soc_gy);          
				 jbzk1.setSqr_wf(sqr_wf);              
				 jbzk1.setSqr_syfm(sqr_syfm);            
				 jbzk1.setSqr_qq_zk(sqr_qq_zk);           
				 jbzk1.setSqr_jr_pj(sqr_jr_pj);           
				 jbzk1.setSqr_lj_pj(sqr_lj_pj);           
				 jbzk1.setSqr_lxr_pj(sqr_lxr_pj);          
				 jbzk1.setSqr_sy_pj(sqr_sy_pj);           
				 jbzk1.setSqr_q_person(sqr_q_person);        
				 jbzk1.setSqr_q_bus(sqr_q_bus);           
				 jbzk1.setSqr_w_person(sqr_w_person);        
				 jbzk1.setSqr_w_bus(sqr_w_bus);           
				 jbzk1.setSqr_ztpj(sqr_ztpj);            
				 jbzk1.setHy_work(hy_work);             
				 jbzk1.setHy_xz(hy_xz);               
				 jbzk1.setHy_fx(hy_fx);               
				 jbzk1.setHy_qs(hy_qs);               
				 jbzk1.setHy_zz(hy_zz);               
				 jbzk1.setHy_ys_gl(hy_ys_gl);            
				 jbzk1.setHy_gd_gl(hy_gd_gl);            
				 jbzk1.setHy_pj_sale(hy_pj_sale);          
				 jbzk1.setHy_pj_jl(hy_pj_jl);            
				 jbzk1.setHy_gz(hy_gz);               
				 jbzk1.setHy_fy(hy_fy);               
				 jbzk1.setHy_yclje(hy_yclje);            
				 jbzk1.setHy_policy(hy_policy);           
				 jbzk1.setHy_desc(hy_desc);             
				 jbzk1.setHy_pj_ml(hy_pj_ml); 
				 //save
				 addIntoPiecesService.saveJbzk(jbzk1);
			 }
			 
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	
	
	/**
	 * 查询  经营状态
	 * @param request
	 * @return
	*/
	@ResponseBody
	@RequestMapping(value = "findJyzt.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView findJyzt(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_jyzt", request);
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dzjyzt jyzt = addIntoPiecesService.findDzjyzt(customerId,productId);
		mv.addObject("jyzt", jyzt);
		mv.addObject("productId", productId);
		mv.addObject("customerId",customerId);
		return mv;
	}
	
	
	/**
	 * save 经营状态
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "addJyzt.json", method = { RequestMethod.GET })
	public JRadReturnMap addJyzt(@ModelAttribute DzjyztForm form,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			Dzjyzt jyzt = form.createModel(Dzjyzt.class);
			Dzjyzt dzjyzt = addIntoPiecesService.findDzjyzt(form.getCustomer_id(),form.getProduct_id());
			if(dzjyzt!= null){
				//update
				dzjyzt.setHyzc_jrhb(jyzt.getHyzc_jrhb());       
				dzjyzt.setHyzc_gjcy(jyzt.getHyzc_gjcy());       
				dzjyzt.setHyzc_ss(jyzt.getHyzc_ss());       
				dzjyzt.setHyxz_gqr(jyzt.getHyxz_gqr());       
				dzjyzt.setHyxz_zqxfx(jyzt.getHyxz_zqxfx());       
				dzjyzt.setHyxz_zcjg(jyzt.getHyxz_zcjg());       
				dzjyzt.setHyxz_ylnl_jl(jyzt.getHyxz_ylnl_jl());       
				dzjyzt.setHyxz_ylnl_szzzts(jyzt.getHyxz_ylnl_szzzts());       
				dzjyzt.setHyxz_ylnl_chzzts(jyzt.getHyxz_ylnl_chzzts());       
				dzjyzt.setHyxz_ylx_sysup(jyzt.getHyxz_ylx_sysup());       
				dzjyzt.setHyxz_ylx_xysup(jyzt.getHyxz_ylx_xysup());       
				dzjyzt.setHyxz_tdx(jyzt.getHyxz_tdx());       
				dzjyzt.setHyxz_fzqs_salerase(jyzt.getHyxz_fzqs_salerase());       
				dzjyzt.setHyxz_fzqs_jlrase(jyzt.getHyxz_fzqs_jlrase());       
				dzjyzt.setHyxz_fxfx(jyzt.getHyxz_fxfx());       
				dzjyzt.setJy_schcp_cy(jyzt.getJy_schcp_cy());       
				dzjyzt.setJy_schcp_xqzq(jyzt.getJy_schcp_xqzq());       
				dzjyzt.setJy_schcp_qbl(jyzt.getJy_schcp_qbl());       
				dzjyzt.setJy_schcp_tysl(jyzt.getJy_schcp_tysl());       
				dzjyzt.setJy_schcp_salebl(jyzt.getJy_schcp_salebl());       
				dzjyzt.setJy_schcp_method(jyzt.getJy_schcp_method());       
				dzjyzt.setJy_schj_zlnum(jyzt.getJy_schj_zlnum());       
				dzjyzt.setJy_schj_jslv(jyzt.getJy_schj_jslv());       
				dzjyzt.setJy_schj_zhyx(jyzt.getJy_schj_zhyx());       
				dzjyzt.setJy_schj_hj(jyzt.getJy_schj_hj());       
				dzjyzt.setJy_schj_lz(jyzt.getJy_schj_lz());       
				dzjyzt.setJy_sxhj_cb(jyzt.getJy_sxhj_cb());       
				dzjyzt.setJy_sxhj_bl(jyzt.getJy_sxhj_bl());       
				dzjyzt.setGy_glwd_bl(jyzt.getGy_glwd_bl());       
				dzjyzt.setGy_glwd_ls(jyzt.getGy_glwd_ls());       
				dzjyzt.setGy_sz_edu(jyzt.getGy_sz_edu());       
				dzjyzt.setGy_sz_aveage(jyzt.getGy_sz_aveage());       
				dzjyzt.setGy_sz_qycysj(jyzt.getGy_sz_qycysj());       
				dzjyzt.setGy_sz_hycysj(jyzt.getGy_sz_hycysj());       
				dzjyzt.setGy_sz_kt(jyzt.getGy_sz_kt());       
				dzjyzt.setGy_sz_td(jyzt.getGy_sz_td());       
				dzjyzt.setGy_sz_dd(jyzt.getGy_sz_dd());       
				dzjyzt.setGy_sx_ty(jyzt.getGy_sx_ty());       
				dzjyzt.setGy_sx_js(jyzt.getGy_sx_js());       
				dzjyzt.setGy_jy_yyqk(jyzt.getGy_jy_yyqk());       
				dzjyzt.setGy_jy_supnum(jyzt.getGy_jy_supnum());       
				dzjyzt.setGy_jy_jxnum(jyzt.getGy_jy_jxnum());       
				dzjyzt.setGy_js_cysj(jyzt.getGy_js_cysj());       
				dzjyzt.setGy_js_lsl(jyzt.getGy_js_lsl());       
				dzjyzt.setGy_team_dzzb(jyzt.getGy_team_dzzb());       
				dzjyzt.setGy_team_cysj(jyzt.getGy_team_cysj());       
				dzjyzt.setGy_kz_jg(jyzt.getGy_kz_jg());       
				dzjyzt.setGy_kz_jc(jyzt.getGy_kz_jc());       
				dzjyzt.setGy_kz_wzjl(jyzt.getGy_kz_wzjl());       
				dzjyzt.setGy_kz_zp(jyzt.getGy_kz_zp());       
				dzjyzt.setGy_kz_jsjl(jyzt.getGy_kz_jsjl());       
				dzjyzt.setGy_kz_rxgl(jyzt.getGy_kz_rxgl());       
				dzjyzt.setGy_kz_ttzd(jyzt.getGy_kz_ttzd());       
				dzjyzt.setGy_kz_fzzl(jyzt.getGy_kz_fzzl());       
				dzjyzt.setGy_cw_cb(jyzt.getGy_cw_cb());       
				dzjyzt.setGy_cw_kz(jyzt.getGy_cw_kz());       
				addIntoPiecesService.updateJyzt(dzjyzt);
			}else{
				//save 
				addIntoPiecesService.saveJyzt(jyzt);
			}
			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}
		return returnMap;
	}
	
	
	/**
	 * 查看生存状态
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findSczt.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView findSczt(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_sczt", request);
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dcsczt sczt = addIntoPiecesService.findDcsczt(customerId, productId);
		mv.addObject("sczt", sczt);
		mv.addObject("productId", productId);
		mv.addObject("customerId",customerId);
		return mv;
	}
	
	//保存建议
	@ResponseBody
	@RequestMapping(value = "addSczt.json", method = { RequestMethod.GET })
	public JRadReturnMap addSczt(@ModelAttribute DcscztForm form,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			Dcsczt dcsczt = form.createModel(Dcsczt.class);
			
			//查询是否exist
			Dcsczt sczt = addIntoPiecesService.findDcsczt(form.getCustomer_id(),form.getProduct_id());
			if(sczt!=null){
				//update
				 sczt.setJc_fcsl_aj(dcsczt.getJc_fcsl_aj());
				 sczt.setJc_fcsj_aj(dcsczt.getJc_fcsj_aj());
				 sczt.setJc_pjmj_aj(dcsczt.getJc_pjmj_aj());
				 sczt.setJc_zxqk_aj(dcsczt.getJc_zxqk_aj());
				 sczt.setJc_jznx_aj(dcsczt.getJc_jznx_aj());
				 sczt.setJc_fcsl_qe(dcsczt.getJc_fcsl_qe());
				 sczt.setJc_fcsj_qe(dcsczt.getJc_fcsj_qe());
				 sczt.setJc_pjmj_qe(dcsczt.getJc_pjmj_qe());
				 sczt.setJc_zxqk_qe(dcsczt.getJc_zxqk_qe());
				 sczt.setJc_jznx_qe(dcsczt.getJc_jznx_qe());
				 sczt.setJc_zlfc_sl(dcsczt.getJc_zlfc_sl());
				 sczt.setJc_zlfc_mj(dcsczt.getJc_zlfc_mj());
				 sczt.setJc_zlfc_zj(dcsczt.getJc_zlfc_zj());
				 sczt.setJc_cl_sl(dcsczt.getJc_cl_sl());
				 sczt.setJc_cl_gmze(dcsczt.getJc_cl_gmze());
				 sczt.setJc_cl_pjsysj(dcsczt.getJc_cl_pjsysj());
				 sczt.setJc_cl_gmfs(dcsczt.getJc_cl_gmfs());
				 sczt.setJc_zhje(dcsczt.getJc_zhje());
				 sczt.setJc_lcje(dcsczt.getJc_lcje());
				 sczt.setJc_sz_qx(dcsczt.getJc_sz_qx());
				 sczt.setJc_sz_zb(dcsczt.getJc_sz_zb());
				 sczt.setJc_sz_sx(dcsczt.getJc_sz_sx());
				 sczt.setHj_aq(dcsczt.getHj_aq());
				 sczt.setHj_jk(dcsczt.getHj_jk());
				 sczt.setHj_bl(dcsczt.getHj_bl());
				 sczt.setHj_ss(dcsczt.getHj_ss());
				 sczt.setHj_ljyh(dcsczt.getHj_ljyh());
				 sczt.setHj_ljpjedu(dcsczt.getHj_ljpjedu());
				 sczt.setHj_ljzy(dcsczt.getHj_ljzy());
				 sczt.setHj_ljeco(dcsczt.getHj_ljeco());
				 sczt.setSj_work(dcsczt.getSj_work());
				 sczt.setSj_rest_yl(dcsczt.getSj_rest_yl());
				 sczt.setSj_rest_hw(dcsczt.getSj_rest_hw());
				 sczt.setWork_income(dcsczt.getWork_income());
				 sczt.setBusiness_income(dcsczt.getBusiness_income());
				 sczt.setProperty_income(dcsczt.getProperty_income());
				 sczt.setSocial_je(dcsczt.getSocial_je());
				 sczt.setSocial_nx(dcsczt.getSocial_nx());
				 sczt.setAccufund_je(dcsczt.getAccufund_je());
				 sczt.setAccufund_nx(dcsczt.getAccufund_nx());
				 sczt.setDistr_sh(dcsczt.getDistr_sh());
				 sczt.setDistr_lc(dcsczt.getDistr_lc());
				 sczt.setDistr_qt(dcsczt.getDistr_qt());
				 sczt.setXfxg(dcsczt.getXfxg());
				 sczt.setLcjg(dcsczt.getLcjg());
				 sczt.setJk_sy(dcsczt.getJk_sy());
				 sczt.setJk_sm(dcsczt.getJk_sm());
				 sczt.setJk_tj(dcsczt.getJk_tj());
				 sczt.setJk_jy(dcsczt.getJk_jy());
				 sczt.setJk_xjgs(dcsczt.getJk_xjgs());
				 sczt.setJt_poqg(dcsczt.getJt_poqg());
				 sczt.setJt_gx(dcsczt.getJt_gx());
				 sczt.setJt_jcnl(dcsczt.getJt_jcnl());
				 sczt.setSj_jhcs(dcsczt.getSj_jhcs());
				 sczt.setSj_pygz(dcsczt.getSj_pygz());
				 sczt.setSj_pyxl(dcsczt.getSj_pyxl());
				 sczt.setFz_hkjh(dcsczt.getFz_hkjh());
				 sczt.setFz_srzb(dcsczt.getFz_srzb());
				 sczt.setFz_xyzb(dcsczt.getFz_xyzb());
				 sczt.setFz_ly(dcsczt.getFz_ly());
				 sczt.setFz_lyhkjh(dcsczt.getFz_lyhkjh());
				 sczt.setFz_lyzb(dcsczt.getFz_lyzb());
				 sczt.setFz_db_je(dcsczt.getFz_db_je());
				 sczt.setFz_db_yt(dcsczt.getFz_db_yt());
				 sczt.setFz_db_qx(dcsczt.getFz_db_qx());
				 addIntoPiecesService.updateSczt(sczt);
			}else{
				//save
				addIntoPiecesService.saveSczt(dcsczt);
			}

			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	
	/**
	 * 查看道德品质
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "findDdpz.page")
	@JRadOperation(JRadOperation.BROWSE)
	public AbstractModelAndView findDdpz(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_ddpz", request);
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dcddpz ddpz = addIntoPiecesService.findDcddpz(customerId, productId);
		mv.addObject("ddpz", ddpz);
		mv.addObject("productId", productId);
		mv.addObject("customerId",customerId);
		return mv;
	}
	
	
	//保存建议
	@ResponseBody
	@RequestMapping(value = "addDdpz.json", method = { RequestMethod.GET })
	public JRadReturnMap addDdpz(@ModelAttribute DcddpzForm form,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			Dcddpz dcddpz = form.createModel(Dcddpz.class);
			//查询是否exist
			Dcddpz ddpz = addIntoPiecesService.findDcddpz(form.getCustomer_id(),form.getProduct_id());
			
			if(ddpz!=null){
				//update
				ddpz.setSzpg_gzxx(dcddpz.getSzpg_gzxx());      
				ddpz.setSzpg_rhjl(dcddpz.getSzpg_rhjl());      
				ddpz.setSzpg_syfm(dcddpz.getSzpg_syfm());      
				ddpz.setSzpg_gyhd(dcddpz.getSzpg_gyhd());      
				ddpz.setSzpg_blsh(dcddpz.getSzpg_blsh());      
				ddpz.setXyjl_wffz(dcddpz.getXyjl_wffz());      
				ddpz.setXyjl_zxbg(dcddpz.getXyjl_zxbg());      
				ddpz.setXyjl_tqfy(dcddpz.getXyjl_tqfy());      
				ddpz.setXyjl_ggsstp(dcddpz.getXyjl_ggsstp());  
				ddpz.setXyjl_jtwz(dcddpz.getXyjl_jtwz());      
				ddpz.setXyjl_dhqf(dcddpz.getXyjl_dhqf());      
				ddpz.setXyjl_qzjq(dcddpz.getXyjl_qzjq());      
				ddpz.setZjxy_zczj(dcddpz.getZjxy_zczj());      
				ddpz.setZjxy_wfzj(dcddpz.getZjxy_wfzj());      
				addIntoPiecesService.updateDdpz(ddpz);
			}else{
				//save
				addIntoPiecesService.saveDdpz(dcddpz);
			}

			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
	
	/**
	 * 显示资产负债
	 * 
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_zcfz.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_zcfz(HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		List<CustomerInforUpdateBalanceSheet> customerInforUpdateBalanceSheet =
                customerInforUpdateService.getCustomerInforUpdateBalanceSheetByCustIdAndProdId(customerId,productId);

		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_zcfz",request);
		mv.addObject("customerInforUpdateBalanceSheet",	customerInforUpdateBalanceSheet);
		mv.addObject("customerId",customerId);
		mv.addObject("productId",productId);
		return mv;
	}
	
	
	/**
	 * 执行添加 资产负债
	 * 
	 * @param customerinfoForm
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "balanceSheetInsert.json")
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView balanceSheetInsert(HttpServletRequest request) {
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_zcfz",request);
		try {
			String balanceSheet = request.getParameter("balanceSheet");
			String customerId=request.getParameter("customerId");
			String productId = request.getParameter("productId");
			customerInforUpdateService.insertCustomerInforUpdateBalanceSheet1(customerId,balanceSheet,productId);

			List<CustomerInforUpdateBalanceSheet> customerInforUpdateBalanceSheet =
                    customerInforUpdateService.getCustomerInforUpdateBalanceSheetByCustIdAndProdId(customerId,productId);

			mv.addObject("customerInforUpdateBalanceSheet",	customerInforUpdateBalanceSheet);
			mv.addObject("customerId",customerId);
			mv.addObject("productId",productId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mv;
	}
	
	
	/**
	 * 显示利润简表
	 * @param filter
	 * @param request
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "create_lrjb.page", method = { RequestMethod.GET })
	@JRadOperation(JRadOperation.CREATE)
	public AbstractModelAndView create_lrjb(HttpServletRequest request) {
		String customerId = request.getParameter("customerId");
		String productId = request.getParameter("productId");
		Dclrjb lrjb = addIntoPiecesService.findDclrjb(customerId,productId);
		JRadModelAndView mv = new JRadModelAndView("/intopieces/dc_lrjb",request);
		mv.addObject("customerId",customerId);
		mv.addObject("productId",productId);
		mv.addObject("lrjb",lrjb);
		return mv;
	}
	
	
	//保存利润简表
	@ResponseBody
	@RequestMapping(value = "addlrjb.json", method = { RequestMethod.GET })
	public JRadReturnMap addlrjb(@ModelAttribute DclrjbForm form,HttpServletRequest request) {
		JRadReturnMap returnMap = new JRadReturnMap();
		try {
			IUser user = Beans.get(LoginManager.class).getLoggedInUser(request);
			String loginId = user.getId();
			Dclrjb lrjb = form.createModel(Dclrjb.class);
			//查询是否exist
			Dclrjb dclrjb = addIntoPiecesService.findDclrjb(form.getCustomer_id(),form.getProduct_id());
			if(dclrjb!=null){
				dclrjb.setRq(lrjb.getRq());     
				dclrjb.setUnit(lrjb.getUnit());     
				dclrjb.setA1(lrjb.getA1());     
				dclrjb.setA2(lrjb.getA2());     
				dclrjb.setA3(lrjb.getA3());     
				dclrjb.setA4(lrjb.getA4());     
				dclrjb.setA5(lrjb.getA5());     
				dclrjb.setA6(lrjb.getA6());     
				dclrjb.setA7(lrjb.getA7());     
				dclrjb.setA8(lrjb.getA8());     
				dclrjb.setA10(lrjb.getA10());     
				dclrjb.setA11(lrjb.getA11());     
				dclrjb.setA12(lrjb.getA12());     
				dclrjb.setA13(lrjb.getA13());     
				dclrjb.setA14(lrjb.getA14());     
				dclrjb.setA15(lrjb.getA15());     
				dclrjb.setA16(lrjb.getA16());     
				dclrjb.setA17(lrjb.getA17());     
				dclrjb.setA18(lrjb.getA18());     
				dclrjb.setA19(lrjb.getA19());     
				dclrjb.setA20(lrjb.getA20());     
				dclrjb.setA21(lrjb.getA21());     
				dclrjb.setA22(lrjb.getA22());     
				dclrjb.setA23(lrjb.getA23());     
				dclrjb.setA24(lrjb.getA24());     
				dclrjb.setA25(lrjb.getA25());     
				dclrjb.setA26(lrjb.getA26());     
				dclrjb.setA27(lrjb.getA27());     
				dclrjb.setA28(lrjb.getA28());     
				dclrjb.setA29(lrjb.getA29());     
				dclrjb.setA30(lrjb.getA30());     
				dclrjb.setA31(lrjb.getA31());     
				dclrjb.setA32(lrjb.getA32());     
				dclrjb.setA33(lrjb.getA33());     
				dclrjb.setA34(lrjb.getA34());     
				dclrjb.setA35(lrjb.getA35());     
				dclrjb.setA36(lrjb.getA36());     
				dclrjb.setA37(lrjb.getA37());     
				dclrjb.setA38(lrjb.getA38());     
				dclrjb.setA39(lrjb.getA39());     
				dclrjb.setA40(lrjb.getA40());     
				dclrjb.setA41(lrjb.getA41());     
				dclrjb.setA42(lrjb.getA42());     
				dclrjb.setA43(lrjb.getA43());     
				dclrjb.setA44(lrjb.getA44());     
				dclrjb.setA45(lrjb.getA45());     
				dclrjb.setA46(lrjb.getA46());     
				dclrjb.setA47(lrjb.getA47());     
				dclrjb.setA48(lrjb.getA48());     
				dclrjb.setA49(lrjb.getA49());     
				dclrjb.setA50(lrjb.getA50());     
				dclrjb.setA51(lrjb.getA51());     
				dclrjb.setA52(lrjb.getA52());     
				dclrjb.setA53(lrjb.getA53());     
				dclrjb.setA54(lrjb.getA54());     
				dclrjb.setA55(lrjb.getA55());     
				dclrjb.setA56(lrjb.getA56());     
				dclrjb.setA57(lrjb.getA57());     
				dclrjb.setA58(lrjb.getA58());     
				dclrjb.setA59(lrjb.getA59());     
				dclrjb.setA60(lrjb.getA60());     
				dclrjb.setA61(lrjb.getA61());     
				dclrjb.setA62(lrjb.getA62());     
				dclrjb.setA63(lrjb.getA63());     
				dclrjb.setA64(lrjb.getA64());     
				dclrjb.setA65(lrjb.getA65());     
				dclrjb.setA66(lrjb.getA66());     
				dclrjb.setA67(lrjb.getA67());     
				dclrjb.setA68(lrjb.getA68());     
				dclrjb.setA69(lrjb.getA69());     
				dclrjb.setA70(lrjb.getA70());     
				dclrjb.setA71(lrjb.getA71());     
				dclrjb.setA72(lrjb.getA72());     
				dclrjb.setA73(lrjb.getA73());     
				dclrjb.setA74(lrjb.getA74());     
				dclrjb.setA75(lrjb.getA75());     
				dclrjb.setA76(lrjb.getA76());     
				dclrjb.setA77(lrjb.getA77());     
				dclrjb.setA78(lrjb.getA78());     
				dclrjb.setA79(lrjb.getA79());     
				dclrjb.setA80(lrjb.getA80());     
				dclrjb.setA81(lrjb.getA81());     
				dclrjb.setA82(lrjb.getA82());     
				dclrjb.setA83(lrjb.getA83());     
				dclrjb.setA84(lrjb.getA84());     
				dclrjb.setA85(lrjb.getA85());     
				dclrjb.setA86(lrjb.getA86());     
				dclrjb.setA87(lrjb.getA87());     
				dclrjb.setA88(lrjb.getA88());     
				dclrjb.setA89(lrjb.getA89());     
				dclrjb.setA90(lrjb.getA90());     
				dclrjb.setA91(lrjb.getA91());     
				dclrjb.setA92(lrjb.getA92());     
				dclrjb.setA93(lrjb.getA93());     
				dclrjb.setA94(lrjb.getA94());     
				dclrjb.setA95(lrjb.getA95());     
				dclrjb.setA96(lrjb.getA96());     
				dclrjb.setA97(lrjb.getA97());     
				dclrjb.setA98(lrjb.getA98());     
				dclrjb.setA99(lrjb.getA99());     
				dclrjb.setA100(lrjb.getA100());     
				dclrjb.setA101(lrjb.getA101());     
				dclrjb.setA102(lrjb.getA102());     
				dclrjb.setA103(lrjb.getA103());     
				dclrjb.setA104(lrjb.getA104());     
				dclrjb.setA105(lrjb.getA105());     
				dclrjb.setA106(lrjb.getA106());     
				dclrjb.setA107(lrjb.getA107());     
				dclrjb.setA108(lrjb.getA108());     
				dclrjb.setA109(lrjb.getA109());     
				dclrjb.setA110(lrjb.getA110());     
				dclrjb.setA111(lrjb.getA111());     
				dclrjb.setA112(lrjb.getA112());     
				dclrjb.setA113(lrjb.getA113());     
				dclrjb.setA114(lrjb.getA114());     
				dclrjb.setA115(lrjb.getA115());     
				dclrjb.setA116(lrjb.getA116());     
				dclrjb.setA117(lrjb.getA117());     
				dclrjb.setA118(lrjb.getA118());     
				dclrjb.setA119(lrjb.getA119());     
				dclrjb.setA120(lrjb.getA120());     
				dclrjb.setA121(lrjb.getA121());     
				dclrjb.setA122(lrjb.getA122());     
				dclrjb.setA123(lrjb.getA123());     
				dclrjb.setA124(lrjb.getA124());     
				dclrjb.setA125(lrjb.getA125());     
				dclrjb.setA126(lrjb.getA126());     
				dclrjb.setA127(lrjb.getA127());     
				dclrjb.setA128(lrjb.getA128());     
				dclrjb.setA129(lrjb.getA129());     
				dclrjb.setA130(lrjb.getA130());     
				dclrjb.setA131(lrjb.getA131());     
				dclrjb.setA132(lrjb.getA132());     
				dclrjb.setA133(lrjb.getA133());     
				dclrjb.setA134(lrjb.getA134());     
				dclrjb.setA135(lrjb.getA135());     
				dclrjb.setA136(lrjb.getA136());     
				dclrjb.setA137(lrjb.getA137());     
				dclrjb.setA138(lrjb.getA138());     
				dclrjb.setA139(lrjb.getA139());     
				dclrjb.setA140(lrjb.getA140());     
				dclrjb.setA141(lrjb.getA141());     
				dclrjb.setA142(lrjb.getA142());     
				dclrjb.setA143(lrjb.getA143());     
				dclrjb.setA144(lrjb.getA144());     
				dclrjb.setA145(lrjb.getA145());     
				dclrjb.setA146(lrjb.getA146());     
				dclrjb.setA147(lrjb.getA147());     
				dclrjb.setA148(lrjb.getA148());     
				dclrjb.setA149(lrjb.getA149());     
				dclrjb.setA150(lrjb.getA150());     
				dclrjb.setA151(lrjb.getA151());     
				dclrjb.setA152(lrjb.getA152());     
				dclrjb.setA153(lrjb.getA153());     
				dclrjb.setA154(lrjb.getA154());     
				dclrjb.setA155(lrjb.getA155());     
				dclrjb.setA156(lrjb.getA156());     
				dclrjb.setA157(lrjb.getA157());     
				dclrjb.setA158(lrjb.getA158());     
				dclrjb.setA159(lrjb.getA159());     
				dclrjb.setA160(lrjb.getA160());     
				dclrjb.setA161(lrjb.getA161());     
				dclrjb.setA162(lrjb.getA162());     
				dclrjb.setA163(lrjb.getA163());     
				dclrjb.setA164(lrjb.getA164());     
				dclrjb.setA165(lrjb.getA165());     
				dclrjb.setA166(lrjb.getA166());     
				dclrjb.setA167(lrjb.getA167());     
				dclrjb.setA168(lrjb.getA168());     
				dclrjb.setA169(lrjb.getA169());     
				dclrjb.setA170(lrjb.getA170());     
				dclrjb.setA171(lrjb.getA171());     
				dclrjb.setA172(lrjb.getA172());     
				dclrjb.setA173(lrjb.getA173());     
				dclrjb.setA174(lrjb.getA174());     
				dclrjb.setA175(lrjb.getA175());     
				dclrjb.setA176(lrjb.getA176());     
				dclrjb.setA177(lrjb.getA177());     
				dclrjb.setA178(lrjb.getA178());     
				dclrjb.setA179(lrjb.getA179());     
				dclrjb.setA180(lrjb.getA180());     
				dclrjb.setA181(lrjb.getA181());     
				dclrjb.setSrjh(lrjb.getSrjh());     
				dclrjb.setSrxs(lrjb.getSrxs());     
				dclrjb.setKbcbxs(lrjb.getKbcbxs());     
				dclrjb.setQtsrfx(lrjb.getQtsrfx());     
				dclrjb.setKz(lrjb.getKz());     
				dclrjb.setLrjs(lrjb.getLrjs());     
				addIntoPiecesService.updateDclrjb(dclrjb);
			}else{
				addIntoPiecesService.saveDclrjb(lrjb);
			}
			
			

			returnMap.addGlobalMessage(CHANGE_SUCCESS);
		} catch (Exception e) {
			return WebRequestHelper.processException(e);
		}

		return returnMap;
	}
	
}
