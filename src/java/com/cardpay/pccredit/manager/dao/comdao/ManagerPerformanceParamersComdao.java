package com.cardpay.pccredit.manager.dao.comdao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.manager.model.TyPerformanceParameters;
import com.wicresoft.jrad.base.database.dao.common.CommonDao;
/**
 * 
 * @author 季东晓
 *
 * 2014-11-21 下午4:28:09
 */
@Service
public class ManagerPerformanceParamersComdao {
	

	@Autowired
	private CommonDao commonDao;
	/**
	 * 查询客户经理绩效参数
	 * @return
	 */
	public List<TyPerformanceParameters> getManagerPerformanceParamers(){
		String sql = "select * from ty_performance_parameters";
		List<TyPerformanceParameters> list = commonDao.queryBySql(TyPerformanceParameters.class, sql, null);
			return list;	
	}
	/**
	 * 保存前先删除记录
	 * @return
	 */
	public void deleteList(){
		String sql = "delete from ty_performance_parameters";
		commonDao.queryBySql(TyPerformanceParameters.class, sql, null);
	}

}
