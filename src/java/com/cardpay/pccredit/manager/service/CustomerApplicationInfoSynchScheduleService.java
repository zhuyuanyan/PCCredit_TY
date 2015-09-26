/**
 * 
 */
package com.cardpay.pccredit.manager.service;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.cardpay.pccredit.intopieces.constant.Constant;
import com.cardpay.pccredit.intopieces.model.IntoPieces;
import com.cardpay.pccredit.intopieces.service.IntoPiecesService;

/**
 * 描述 ：同步系统中的进件的statusservice
 * @author 宋辰
 */
@Service
public class CustomerApplicationInfoSynchScheduleService {
	
	private Logger logger = Logger.getLogger(CustomerApplicationInfoSynchScheduleService.class);
	
	@Autowired
	private IntoPiecesService intoPiecesService;


	/**
	 * 同步进件状态
	 * @throws IOException 
	 */
	@Scheduled(cron = "0 57 18 * * ?")
	private void dosynchMethod() throws IOException{
		//获取今日日期
		DateFormat format = new SimpleDateFormat("yyyyMMdd");
		String dateString = format.format(new Date());
		logger.info(dateString+"进件状态更新开始**********");
		//查询已经审核通过的进件信息
		List<IntoPieces> intoPiecesList = intoPiecesService.findCustomerApplicationInfo();
		for(IntoPieces intoPieces:intoPiecesList){
			//更新进件申请表 进件状态 status
			IntoPieces  pieces = new IntoPieces();
			pieces.setCustomerId(intoPieces.getCustomerId());
			pieces.setStatus(Constant.END);//放款成功
			pieces.setProductId(intoPieces.getProductId());
			intoPiecesService.updateCustomerApplicationInfo(pieces);
		}
		logger.info(dateString+"进件状态更新结束**********");
	}
	
}
