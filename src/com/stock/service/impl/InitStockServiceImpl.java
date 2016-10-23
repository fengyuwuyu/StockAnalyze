package com.stock.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stock.connection.HttpClientUtil;
import com.stock.dao.HolidayMapper;
import com.stock.dao.StockDetailMapper;
import com.stock.service.InitStockServiceI;
import com.stock.util.MapUtils;

@Service
public class InitStockServiceImpl implements InitStockServiceI {
	private static ObjectMapper mapper = new ObjectMapper();
	private StockDetailMapper stockDetailMapper;
	private String timeBak = "";

	@Autowired
	public void setStockDetailMapper(StockDetailMapper stockDetailMapper) {
		this.stockDetailMapper = stockDetailMapper;
	}

	/**
	 * 每30秒执行一次
	 * 用于获取股票的详细信息
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> initStock() {
		String url = "http://quotes.money.163.com/hs/service/diyrank.php?"
				+ "host=http%3A%2F%2Fquotes.money.163.com%2Fhs%2Fservice%2Fdiyrank.php&"
				+ "page=0&query=STYPE%3AEQA&fields=NO%2CSYMBOL%2CNAME%2CPRICE%2CPERCENT%2CUPDOWN%2CFIVE_MINUTE%2COPEN%"
				+ "2CYESTCLOSE%2CHIGH%2CLOW%2CVOLUME%2CTURNOVER%2CHS%2CLB%2CWB%2CZF%2CPE%2CMCAP%2CTCAP%2CMFSUM%"
				+ "2CMFRATIO.MFRATIO2%2CMFRATIO.MFRATIO10%2CSNAME%2CCODE%2CANNOUNMT%2CUVSNEWS&s"
				+ "ort=PERCENT&order=desc&count=2953&type=query";
		try {
			HttpEntity entity = HttpClientUtil.get(url);
			if (entity != null) {
				LinkedHashMap<String, Object> detail = mapper.readValue(
						EntityUtils.toString(entity, "utf-8"), LinkedHashMap.class);
				String time = (String) detail.get("time");
				if(!checkTime(time)){
					return null;
				}
				timeBak = time;
				List<LinkedHashMap<String, Object>> list= (List<LinkedHashMap<String, Object>>) detail.get("list");
				 for (LinkedHashMap<String, Object> map : list) {
					 map.put("TIME", time);
				 }
				 Map<String,Object> map = MapUtils.createMap("list",list);
				 this.stockDetailMapper.insert(map);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return MapUtils.createSuccessMap();
	}

	/**
	 * 判断股市此时是否是可交易时间
	 * @param time
	 * @return
	 */
	private boolean checkTime(String time) {
		if("".equals(timeBak)){
			int count = this.stockDetailMapper.selectCountByTime(time);
			return count==0?true:false;
		}
		return timeBak.equals(time);
	}

	public static void main(String[] args) {
		InitStockServiceImpl impl = new InitStockServiceImpl();
		impl.initStock();
	}
}
