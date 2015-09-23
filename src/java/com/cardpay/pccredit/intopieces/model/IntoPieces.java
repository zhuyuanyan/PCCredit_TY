package com.cardpay.pccredit.intopieces.model;

import com.wicresoft.jrad.base.database.model.BusinessModel;

public class IntoPieces  extends BusinessModel {
	
	
	private String id;//进件编号
	private String customerId;//客户ID(外键)
	private String chineseName;//客户名称
	
    private String productId;//产品Id
    private String productName; //产品名称
    private String cardId;//证件号码
    private String applyQuota;//申请额度
    private String status;//申请额度
    private String statusName;
    
    private String nodeName;
    
    private String tyCustomerId;
    
    private String decisionRefuseReason; //备注
    
    
    
    
	public String getDecisionRefuseReason() {
		return decisionRefuseReason;
	}
	public void setDecisionRefuseReason(String decisionRefuseReason) {
		this.decisionRefuseReason = decisionRefuseReason;
	}
	//进度
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getChineseName() {
		return chineseName;
	}
	public void setChineseName(String chineseName) {
		this.chineseName = chineseName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getApplyQuota() {
		return applyQuota;
	}
	public void setApplyQuota(String applyQuota) {
		this.applyQuota = applyQuota;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getTyCustomerId() {
		return tyCustomerId;
	}
	public void setTyCustomerId(String tyCustomerId) {
		this.tyCustomerId = tyCustomerId;
	}
}
