package com.stock.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stock.dao.StockMainMapper;
import com.stock.model.StockAnalyse;
import com.stock.model.StockAnalyseResult;
import com.stock.model.StockConstant;
import com.stock.model.StockMain;
import com.stock.model.StockQuery;
import com.stock.service.StockMainServiceI;
import com.stock.util.MapUtils;

@Service
public class StockMainServiceImpl implements StockMainServiceI {

	private StockMainMapper stockMainMapper;
	private static Integer index = 0;

	@Autowired
	public void setStockMainMapper(StockMainMapper stockMainMapper) {
		this.stockMainMapper = stockMainMapper;
	}

	public Map<String, Object> dataList(StockQuery query) {
		if (query.getBegin() == null || query.getEnd() == null) {
			return MapUtils.createSuccessMap("rows",
					new ArrayList<StockMain>(), "total", 0);
		}
		List<StockMain> list = this.stockMainMapper.dataList(query);
		int total = this.stockMainMapper.getTotal(query);
		return MapUtils.createSuccessMap("rows", list, "total", total);
	}
	
	public Map<String, Object> dataList1(StockQuery query) {
		if (query.getBegin() == null) {
			return MapUtils.createSuccessMap("rows",
					new ArrayList<StockMain>(), "total", 0);
		}
		List<StockMain> list = this.stockMainMapper.dataList1(query);
		int total = this.stockMainMapper.getTotal1(query);
		return MapUtils.createSuccessMap("rows", list, "total", total);
	}
	
	

	public Map<String, Object> showChart(StockQuery query) {
		List<StockMain> list = this.stockMainMapper.showChart(query);
		return MapUtils.createSuccessMap("data", list);
	}

	/**
	 * <ul>
	 * <li>初始化股票数据，将其分类</li>
	 * <li>1、遍历单一股票数据，分析某一段时间内该股走势属于震荡、上涨、下跌中的哪一类
	 * <ul>
	 * <li>1）若此段时间该股振幅在-0.15至0.15之间，则判断其为震荡走势</li>
	 * <li>2）若此段时间该股振幅小于-0.15，下跌趋势</li>
	 * <li>3）若此段时间该股振幅大于0.15，上涨趋势</li>
	 * </ul>
	 * </li>
	 * <li>2、寻找各个阶段的临界点：
	 * <ul>
	 * <li>1）震荡转上涨：以最低点为震荡的结束点，上涨的起始点</li>
	 * <li>2）震荡转下跌：以最高点为震荡的结束点，下跌的起始点</li>
	 * <li>3）上涨转震荡：以最高点为上涨的结束点，震荡的起始点</li>
	 * <li>4）上涨转下跌：以最高点为上涨的结束点，下跌的起始点</li>
	 * <li>5）下跌转震荡：以最低点为下跌的结束点，震荡的起始点</li>
	 * <li>6）下跌转上涨：以最低点为下跌的结束点，上涨的起始点</li>
	 * </ul>
	 * </li>
	 * <li>3、获得每20天的最高价和最低价，与前20天的最高价和最低价对比
	 * <ul>
	 * <li>1）当前20天的最低价比前20最高价高：</li>
	 * <li>2）当前20天最高价比前20最低价低</li>
	 * <li>3）当前20天最低价比前20最低价高，比其最高价低</li>
	 * <li>4）上涨转下跌：以最高点为上涨的结束点，下跌的起始点</li>
	 * <li>5）下跌转震荡：以最低点为下跌的结束点，震荡的起始点</li>
	 * <li>6）下跌转上涨：以最低点为下跌的结束点，上涨的起始点</li>
	 * </ul>
	 * </li>
	 * </ul>
	 */
	public void initStock() {
		List<String> symbols = this.stockMainMapper.selectSymbols();
		if (symbols != null && symbols.size() > 0) {
			StockQuery query = new StockQuery();
			for (String symbol : symbols) {
				query.setSymbol(symbol);
				List<StockMain> stocks = this.stockMainMapper.showChart(query);
				if (stocks != null && stocks.size() > 0) {
					StockAnalyse analyseFirst = this.getStockAnalyse(stocks);
					while (index < stocks.size() - StockConstant.COUNT) {
						StockAnalyse analyseSecond = this
								.getStockAnalyse(stocks);
						this.initStockAnalyse(analyseFirst, analyseSecond);
						analyseFirst = analyseSecond;
					}
					index = 0;
				}
			}
		}
	}

	private void initStockAnalyse(StockAnalyse analyseFirst,
			StockAnalyse analyseSecond) {
		int type1 = analyseFirst.getZsType();
		int type2 = analyseSecond.getZsType();
		switch (type1) {
		case StockConstant.BEGIN_MIN_MAX_END:
		case StockConstant.BEGIN_MAX_END:
		case StockConstant.MAX_MIN:
			switch (type2) {
			case StockConstant.BEGIN_MIN_MAX_END:
			case StockConstant.MAX_MIN:
			case StockConstant.BEGIN_MIN_END:
				this.insertStockAyalyseResult(analyseFirst.getMaxStock(),
						analyseSecond.getMinStock());
				if (type2 == StockConstant.BEGIN_MIN_MAX_END) {
					this.insertStockAyalyseResult(analyseSecond.getMinStock(),
							analyseSecond.getMaxStock());
				}
				break;
			case StockConstant.BEGIN_MAX_END:
			case StockConstant.BEGIN_MAX_MIN_END:
			case StockConstant.MIN_MAX:
				this.insertStockAyalyseResult(analyseFirst.getMaxStock(),
						analyseFirst.getEndStock());
				this.insertStockAyalyseResult(analyseSecond.getBeginStock(),
						analyseSecond.getMaxStock());
				if (type2 == StockConstant.BEGIN_MAX_MIN_END) {
					this.insertStockAyalyseResult(analyseSecond.getMaxStock(),
							analyseSecond.getMinStock());
				}
				break;
			}
			break;
		case StockConstant.BEGIN_MAX_MIN_END:
		case StockConstant.BEGIN_MIN_END:
		case StockConstant.MIN_MAX:
			switch (type2) {
			case StockConstant.BEGIN_MAX_END:
			case StockConstant.BEGIN_MAX_MIN_END:
			case StockConstant.MIN_MAX:
				this.insertStockAyalyseResult(analyseFirst.getMinStock(),
						analyseSecond.getMaxStock());
				if (type2 == StockConstant.BEGIN_MAX_MIN_END) {
					this.insertStockAyalyseResult(analyseSecond.getMaxStock(),
							analyseSecond.getMinStock());
				}
				break;
			case StockConstant.BEGIN_MIN_MAX_END:
			case StockConstant.MAX_MIN:
			case StockConstant.BEGIN_MIN_END:
				this.insertStockAyalyseResult(analyseFirst.getMinStock(),
						analyseFirst.getEndStock());
				this.insertStockAyalyseResult(analyseSecond.getBeginStock(),
						analyseSecond.getMinStock());
				if (type2 == StockConstant.BEGIN_MIN_MAX_END) {
					this.insertStockAyalyseResult(analyseSecond.getMinStock(),
							analyseSecond.getMaxStock());
				}
				break;
			}
			break;
		}
	}

	/**
	 * 找到list中股价最高的
	 * 
	 * @param list
	 * @return
	 */
	private StockAnalyse getStockAnalyse(List<StockMain> list) {
		StockMain maxStock = null;
		StockMain minStock = null;
		Integer begin = index;
		Integer end = index + StockConstant.COUNT-1;
		Integer max = 0;
		Integer min = 0;
		if (end < list.size()) {
			maxStock = list.get(begin);
			minStock = list.get(begin);
			for (int i = begin + 1; i < end; i++) {
				if (list.get(i).getClose() > maxStock.getClose()) {
					maxStock = list.get(i);
					max = i;
				} else if (list.get(i).getClose() > minStock.getClose()) {
					minStock = list.get(i);
					min = i;
				}
			}
		}
		int qsType = 0;
		StockMain beginStock = list.get(begin);
		StockMain endStock = list.get(end);
		if (max == 0) {
			if (min == 0) {
				if (endStock.getClose() < beginStock.getClose()) {
					qsType = StockConstant.MAX_MIN;
				} else {
					qsType = StockConstant.MIN_MAX;
				}
			} else {
				qsType = StockConstant.BEGIN_MIN_END;

			}
		} else {
			if (min == 0) {
				qsType = StockConstant.BEGIN_MAX_END;
			} else {
				if (max > min) {
					qsType = StockConstant.BEGIN_MIN_MAX_END;
				} else {
					qsType = StockConstant.BEGIN_MAX_MIN_END;
				}
			}
		}
		index += StockConstant.COUNT;
		return new StockAnalyse(begin, end, maxStock, minStock, max, min,
				qsType, beginStock, endStock);
	}

	private void insertStockAyalyseResult(StockMain minStock, StockMain maxStock) {
		float sub = maxStock.getClose() - minStock.getClose();
		int type = 0;
		if (sub > 0.1) {
			type = StockConstant.UP;
		} else if (sub < -0.1) {
			type = StockConstant.DOWN;
		} else {
			type = StockConstant.LINE;
		}
		float minPrice = minStock.getClose();
		float maxPrice = maxStock.getClose();
		if (minStock.getClose() > maxStock.getClose()) {
			minPrice = maxStock.getClose();
			maxPrice = minStock.getClose();
		}
		this.stockMainMapper.insertStockAyalyseResult(new StockAnalyseResult(
				minStock.getSymbol(), minStock.getDay(), maxStock.getDay(),
				type, (maxStock.getClose() - minStock.getClose())
						/ maxStock.getClose(), minPrice, maxPrice));
	}


	public Map<String, Object> rankStock(StockQuery query) {
		int type = query.getType();
		List<StockMain> list = null;
		switch (type) {
		case StockConstant.LINE_UP:
			list = this.getLineUp(query);
			break;
		case StockConstant.LINE_DOWN:
			list = this.getDownLineUp(query);
			break;
		case StockConstant.DOWN_UP:
			list = this.getDownUp(query);
			break;
		case StockConstant.DOWN_LINE:
			list = this.getUpLineOrDown(query);
			break;
		default:
			throw new IllegalArgumentException("query type exception...");
		}
		return MapUtils.createSuccessMap("data", list);
	}

	private List<StockMain> getUpLineOrDown(StockQuery query) {

		return null;
	}

	private List<StockMain> getDownUp(StockQuery query) {

		return null;
	}

	private List<StockMain> getDownLineUp(StockQuery query) {

		return null;
	}

	private List<StockMain> getLineUp(StockQuery query) {

		return null;
	}

	@Override
	public Map<String, Object> updateIncreaseRate() {
		// TODO Auto-generated method stub
		
		return null;
	}

}
