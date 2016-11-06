package com.stock.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dao.StockMainMapper;
import com.stock.model.StockAnalyseBase;
import com.stock.model.StockMainAnalyse;
import com.stock.service.StockAnalyseJobI;
import com.stock.util.MapUtils;

@Service
public class StockAnalyseJobImpl implements StockAnalyseJobI {
	
	private StockMainMapper stockMainMapper;
	private SqlSessionFactory sqlSessionFactory;
	
	private Logger log = Logger.getLogger(StockAnalyseJobImpl.class);
	
	@Autowired
	public void setStockMainMapper(StockMainMapper stockMainMapper) {
		this.stockMainMapper = stockMainMapper;
	}

	@Autowired
	public void setSqlSessionFactory(SqlSessionFactory sqlSessionFactory) {
		this.sqlSessionFactory = sqlSessionFactory;
	}


	/**
	 * 每天五点后分析现有数据，找出已经跌到底部且将要上涨的股票，并插入到数据库中
	 */
	@Override
	public void findStock() {
		log.info("开始分析当天股票。。。");
		List<StockMainAnalyse> stocks = this.stockMainMapper.findStock();
		List<StockMainAnalyse> result = new ArrayList<StockMainAnalyse>();
		log.info("开始时间： "+stocks.get(0).getDayIncreases().get(0).getDay()+", 结束时间是： "+stocks.get(0).getDayIncreases().get(stocks.get(0).getDayIncreases().size()-1).getDay());
		for (StockMainAnalyse stockMainAnalyse : stocks) {
			if(stockMainAnalyse.analyse1()){
				result.add(stockMainAnalyse);
			}
		}
		Collections.sort(result, new StockAnalyseComparator());
		this.stockMainMapper.insertAnalyse(MapUtils.createMap("list",result));
		log.info("分析结果是---  "+result);
	}

	public void initStockAnalyse(){
		SqlSession session = getSqlSession();
		StockMainMapper stockMainMapper = session.getMapper(StockMainMapper.class);
		List<String> symbols = stockMainMapper.selectSymbols();
		List<StockAnalyseBase> list = new ArrayList<StockAnalyseBase>();
		int index = 0;
		
		for (String symbol : symbols) {
			log.info("正在处理的股票是 : "+symbol+", index = "+(++index));
			StockAnalyseBase analyse = stockMainMapper.selectStockAnalyse(MapUtils.createMap("symbol",symbol));
			if(analyse!=null){
				analyse.initAnalyse(list);
			}
			if(list.size()>=100){
				stockMainMapper.insertStockAnaylseBase(MapUtils.createMap("list",list));
				session.commit(true);
				session.clearCache();
				log.info("插入到数据库的数量是 : "+list.size());
				list.clear();
			}
			log.info("处理完毕的股票是 : "+symbol+", index = "+index);
		}
		if(list.size()>0){
			stockMainMapper.insertStockAnaylseBase(MapUtils.createMap("list",list));
		}
	}
	
	/**
	 * 查找3分钟涨幅最大的10只股票
	 */
	public void find3MinuteStock(){
		
	}
	
	public void find9MinuteStock(){
		
	}
	
	private SqlSession getSqlSession(){
		return this.sqlSessionFactory.openSession(ExecutorType.BATCH,false);
	}
}
