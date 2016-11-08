package com.stock.model;

import java.util.LinkedHashMap;

/**
 *   
	TRADE_TYPE---1
	PRICE_PRE---9.14
	VOLUME_INC---1304200
	PRICE---9.15
	TURNOVER_INC---11933430
	PRICE_INC---0.0099999999999998
	DATE_STR---15:00:06
	TRADE_TYPE_STR---买盘
 * @author ll
 *
 */
public class FBVolume {

	private int TRADE_TYPE;
	private int VOLUME_INC;
	private double PRICE;
	private double PRICE_PRE;
	private double PRICE_INC;
	private String DATE_STR;
	private String TRADE_TYPE_STR;
	
	
	
	public FBVolume() {}
	
	public FBVolume(LinkedHashMap<String,Object> o) {
		this.TRADE_TYPE = (int) o.get("TRADE_TYPE");
		this.VOLUME_INC = (int) o.get("VOLUME_INC");
		try {
			this.PRICE = Double.valueOf(o.get("PRICE")+"");
		} catch (Exception e) {
			this.PRICE = 0;
			e.printStackTrace();
		}
		try {
			this.PRICE_PRE = Double.valueOf(o.get("PRICE_PRE")+"");
		} catch (Exception e) {
			e.printStackTrace();
			this.PRICE_PRE = 0;
		}
		try {
			this.PRICE_INC = Double.valueOf(o.get("PRICE_INC")+"");
		} catch (Exception e) {
			e.printStackTrace();
			this.PRICE_INC = 0;
		}
		this.DATE_STR = (String) o.get("DATE_STR");
		this.TRADE_TYPE_STR = (String) o.get("TRADE_TYPE_STR");
	}
	
	public int getTRADE_TYPE() {
		return TRADE_TYPE;
	}
	public void setTRADE_TYPE(int tRADE_TYPE) {
		TRADE_TYPE = tRADE_TYPE;
	}
	public int getVOLUME_INC() {
		return VOLUME_INC;
	}
	public void setVOLUME_INC(int vOLUME_INC) {
		VOLUME_INC = vOLUME_INC;
	}
	public double getPRICE() {
		return PRICE;
	}
	public void setPRICE(double pRICE) {
		PRICE = pRICE;
	}
	public double getPRICE_PRE() {
		return PRICE_PRE;
	}
	public void setPRICE_PRE(double pRICE_PRE) {
		PRICE_PRE = pRICE_PRE;
	}
	public double getPRICE_INC() {
		return PRICE_INC;
	}
	public void setPRICE_INC(double pRICE_INC) {
		PRICE_INC = pRICE_INC;
	}
	public String getDATE_STR() {
		return DATE_STR;
	}
	public void setDATE_STR(String dATE_STR) {
		DATE_STR = dATE_STR;
	}
	public String getTRADE_TYPE_STR() {
		return TRADE_TYPE_STR;
	}
	public void setTRADE_TYPE_STR(String tRADE_TYPE_STR) {
		TRADE_TYPE_STR = tRADE_TYPE_STR;
	}
	@Override
	public String toString() {
		return "FBVolume [TRADE_TYPE=" + TRADE_TYPE + ", VOLUME_INC="
				+ VOLUME_INC + ", PRICE=" + PRICE + ", PRICE_PRE=" + PRICE_PRE
				+ ", PRICE_INC=" + PRICE_INC + ", DATE_STR=" + DATE_STR
				+ ", TRADE_TYPE_STR=" + TRADE_TYPE_STR + "]";
	}
	
	
}
