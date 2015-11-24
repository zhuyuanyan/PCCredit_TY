/**
 * 
 */
package com.cardpay.pccredit.postLoan.filter;

import java.util.List;

import com.cardpay.pccredit.manager.web.AccountManagerParameterForm;
import com.wicresoft.jrad.base.web.filter.BaseQueryFilter;

/**
 * @author shaoming
 *
 * 2014年11月11日   下午3:03:58
 */
public class PostLoanFilter extends BaseQueryFilter{
	private String zjhm;
	private String jjh;

	public String getZjhm() {
		return zjhm;
	}

	public void setZjhm(String zjhm) {
		this.zjhm = zjhm;
	}

	public String getJjh() {
		return jjh;
	}

	public void setJjh(String jjh) {
		this.jjh = jjh;
	}
	
}
