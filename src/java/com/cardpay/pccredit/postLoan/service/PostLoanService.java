/**
 * 
 */
package com.cardpay.pccredit.postLoan.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.customer.filter.MaintenanceFilter;
import com.cardpay.pccredit.customer.model.TyRepayLshForm;
import com.cardpay.pccredit.customer.model.TyRepayTkmx;
import com.cardpay.pccredit.customer.model.TyRepayTkmxForm;
import com.cardpay.pccredit.postLoan.dao.PostLoanDao;
import com.cardpay.pccredit.postLoan.filter.PostLoanFilter;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
import com.wicresoft.jrad.base.database.model.QueryResult;

/**
 * @author shaoming
 *
 * 2014年11月11日   下午3:05:12
 */
@Service
public class PostLoanService {
	
	@Autowired
	private CommonDao commonDao;
	@Autowired
	private PostLoanDao postLoanDao;
	/**
	 * 得到借据表
	 * @param filter
	 * @return
	 */
	public QueryResult<TyRepayTkmxForm> findListByFilter(PostLoanFilter filter){
		List<TyRepayTkmxForm> lists = postLoanDao.findListByFilter(filter);
		int size = postLoanDao.findListCountByFilter(filter);
		QueryResult<TyRepayTkmxForm> qr = new QueryResult<TyRepayTkmxForm>(size,lists);
		return qr;
	}
	/**
	 * 得到流水表
	 * @param filter
	 * @return
	 */
	public QueryResult<TyRepayLshForm> findLshListByFilter(PostLoanFilter filter){
		List<TyRepayLshForm> lists = postLoanDao.findLshListByFilter(filter);
		int size = postLoanDao.findLshListCountByFilter(filter);
		QueryResult<TyRepayLshForm> qr = new QueryResult<TyRepayLshForm>(size,lists);
		return qr;
	}
}
