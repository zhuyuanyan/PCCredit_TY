package com.cardpay.pccredit.intopieces.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cardpay.pccredit.intopieces.filter.AddIntoPiecesFilter;
import com.cardpay.pccredit.intopieces.model.LocalImage;
import com.cardpay.pccredit.intopieces.web.LocalImageForm;
import com.wicresoft.util.annotation.Mapper;

@Mapper
public interface LocalImageDao {
	public List<LocalImageForm> findByProductAndCustomer(AddIntoPiecesFilter filter);
	public List<LocalImage> findAllByProductAndCustomer(@Param("productId") String productId,@Param("customerId") String customerId);
	public LocalImage findById(@Param("id") String id);
	public int findCountByProductAndCustomer(AddIntoPiecesFilter filter);
	public List<LocalImageForm> findByApplication(AddIntoPiecesFilter filter);
	public int findCountByApplication(AddIntoPiecesFilter filter);
	public void deleteByProductIdAndCustomerId(@Param("productId") String productId,@Param("customerId") String customerId);
	
}