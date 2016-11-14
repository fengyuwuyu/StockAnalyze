package com.stock.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dao.StockMainMapper;
import com.stock.model.StockMain;
import com.stock.model.StockMainAnalyse;
import com.stock.model.StockQuery;
import com.stock.service.SearchMachineI;
import com.stock.util.CommonsUtil;
import com.stock.util.MapUtils;

/**
 * 搜索器，针对从该天气之后的5天涨幅超过10%股票进行统计分析
 *  * @author lilei
 */
@Service
public class SearchMachineImpl implements SearchMachineI {
	
	private StockMainMapper stockMainMapper;
	
	@Autowired
	public void setStockMainMapper(StockMainMapper stockMainMapper) {
		this.stockMainMapper = stockMainMapper;
	}

	public Map<String,Object> find(StockQuery query){
		if(query.getBegin()==null){
			return MapUtils.createFailedMap("msg","请选择起始时间。。。");
		}
		String begin = CommonsUtil.formatDateToString1(query.getBegin());
		List<StockMainAnalyse> list = this.stockMainMapper.selectAnalyse(begin);
				List<StockMainAnalyse> inserts = new ArrayList<StockMainAnalyse>();
				if(list!=null&&list.size()>0){
					for(StockMainAnalyse analyse : list){
						boolean insert = analyse.analyse(begin, 0);
						if(insert&&analyse.getLastIncrease()>=20){
							inserts.add(analyse);
						}
					}
				}
//				Collections.sort(list, new Comparator<StockMainAnalyse>() {
//
//					@Override
//					public int compare(StockMainAnalyse o1, StockMainAnalyse o2) {
//						if(o1.getLastIncrease()-o2.getLastIncrease()>0){
//							return 1;
//						}else if(o1.getLastIncrease()-o2.getLastIncrease()<0){
//							return -1;
//						}
//						return 0;
//					}
//				});
		return MapUtils.createSuccessMap("rows",inserts,"total",inserts.size());
	}
	
	public Map<String,Object> searcher(){
		
		
		return MapUtils.createSuccessMap();
	}
	
	private List<StockMain> findDownStock(String begin){
		
		return null;
	}
} 
