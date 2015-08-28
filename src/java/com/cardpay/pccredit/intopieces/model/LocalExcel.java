package com.cardpay.pccredit.intopieces.model;

import java.sql.Clob;

import oracle.sql.CLOB;

import com.wicresoft.jrad.base.database.model.BusinessModel;
import com.wicresoft.jrad.base.database.model.ModelParam;

@ModelParam(table = "local_excel")
public class LocalExcel extends BusinessModel {
	
	private static final long serialVersionUID = -8470111754965975277L;
	
	private String customerId;
	private String productId;
	private String applicationId;
	private String attachment;
	private String uri;
	private String sheetFz;
	private String sheetSy;
	private String sheetJl;
	private String sheetJc;
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getAttachment() {
		return attachment;
	}
	public void setAttachment(String attachment) {
		this.attachment = attachment;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getSheetFz() {
		return sheetFz;
	}
	public void setSheetFz(String sheetFz) {
		this.sheetFz = sheetFz;
	}
	public String getSheetSy() {
		return sheetSy;
	}
	public void setSheetSy(String sheetSy) {
		this.sheetSy = sheetSy;
	}
	public String getSheetJl() {
		return sheetJl;
	}
	public void setSheetJl(String sheetJl) {
		this.sheetJl = sheetJl;
	}
	public String getSheetJc() {
		return sheetJc;
	}
	public void setSheetJc(String sheetJc) {
		this.sheetJc = sheetJc;
	}
	
}