/**
 * 
 */
package com.cardpay.pccredit.postLoan.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.TyRepayLshForm;
import com.cardpay.pccredit.customer.model.TyRepayTkmx;
import com.cardpay.pccredit.customer.model.TyRepayTkmxForm;
import com.cardpay.pccredit.customer.web.MaintenanceForm;
import com.cardpay.pccredit.customer.web.MaintenanceWeb;
import com.cardpay.pccredit.manager.web.ManagerBelongMapForm;
import com.cardpay.pccredit.postLoan.filter.PostLoanFilter;
import com.wicresoft.util.annotation.Mapper;

/**
 * @author shaoming
 *
 * 2014年11月11日   下午3:07:50
 */
@Mapper
public interface PostLoanDao {
	/**
	 * 得到借据表
	 * @param filter
	 * @return
	 */
	List<TyRepayTkmxForm> findListByFilter(PostLoanFilter filter);
	int findListCountByFilter(PostLoanFilter filter);
	
	/**
	 * 得到流水表
	 * @param filter
	 * @return
	 */
	List<TyRepayLshForm> findLshListByFilter(PostLoanFilter filter);
	int findLshListCountByFilter(PostLoanFilter filter);
	
}
