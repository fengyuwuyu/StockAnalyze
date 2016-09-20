package com.stock.model;

import java.sql.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class StockVol extends PageModel implements Comparable<StockVol> {
	private Integer id;

	private String sname;

	private String symbol;

	@JsonIgnore
	private List<StockVolume> volume;

	private Long volumeIncrease;

	private Integer increaseVol;

	private Long volume1;

	private Long volume2;

	private Date date;

	private boolean isPriceDownVolUp;

	public void execute() {
		if (volume.size() >= 2) {
			this.volumeIncrease = this.volume.get(0).getVolume()
					- this.volume.get(1).getVolume();
			this.increaseVol = (int) ((this.volume.get(0).getVolume() - this.volume
					.get(1).getVolume()) * 100 / this.volume.get(1).getVolume());
			this.volume1 = this.volume.get(0).getVolume();
			this.volume2 = this.volume.get(1).getVolume();
		} else {
			this.volumeIncrease = 0l;
			this.increaseVol = 0;
		}
	}

	public Long getVolume1() {
		return volume1;
	}

	public void setVolume1(Long volume1) {
		this.volume1 = volume1;
	}

	public Long getVolume2() {
		return volume2;
	}

	public void setVolume2(Long volume2) {
		this.volume2 = volume2;
	}

	public Integer getIncreaseVol() {
		return increaseVol;
	}

	public void setIncreaseVol(Integer increaseVol) {
		this.increaseVol = increaseVol;
	}

	public Long getVolumeIncrease() {
		return volumeIncrease;
	}

	public void setVolumeIncrease(Long volumeIncrease) {
		this.volumeIncrease = volumeIncrease;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSname() {
		return sname;
	}

	public void setSname(String sname) {
		this.sname = sname == null ? null : sname.trim();
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol == null ? null : symbol.trim();
	}

	public List<StockVolume> getVolume() {
		return volume;
	}

	public void setVolume(List<StockVolume> volume) {
		this.volume = volume;
	}

	public boolean isPriceDownVolUp() {
		return isPriceDownVolUp;
	}

	public void setPriceDownVolUp(boolean isPriceDownVolUp) {
		this.isPriceDownVolUp = isPriceDownVolUp;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "StockVol [id=" + id + ", sname=" + sname + ", symbol=" + symbol
				+ ", volume=" + volume + ", volumeIncrease=" + volumeIncrease
				+ ", increaseVol=" + increaseVol + ", volume1=" + volume1
				+ ", volume2=" + volume2 + ", isPriceDownVolUp="
				+ isPriceDownVolUp + "]";
	}

	@Override
	public int compareTo(StockVol o) {
		Integer l = o.getIncreaseVol() - this.getIncreaseVol();
		return l;
	}

	public void priceDownVolUp() {
		if (volume != null && volume.size() > 3) {
			for (int i = 0; i < volume.size() - 3; i++) {
				StockVolume v1 = volume.get(i);
				StockVolume v2 = volume.get(i + 1);
				StockVolume v3 = volume.get(i + 2);
				StockVolume v4 = volume.get(i + 3);
				if (v1.getPrice1() > v2.getPrice1()
						&& v2.getPrice1() > v3.getPrice1()
						&& v3.getPrice1() > v4.getPrice1()
						&& v1.getVolume() < v2.getVolume()
						&& v2.getVolume() < v3.getVolume()
						&& v3.getVolume() < v4.getVolume()) {
					isPriceDownVolUp = true;
					this.date = v1.getTime();
					break;
				}
			}
		}

		// if(volume!=null&&volume.size()>2){
		// for(int i=0;i<volume.size()-2;i++){
		// StockVolume v1 = volume.get(i);
		// StockVolume v2 = volume.get(i+1);
		// StockVolume v3 = volume.get(i+2);
		// if(v1.getPrice1()>v2.getPrice1()&&v2.getPrice1()>v3.getPrice1()&&v1.getVolume()<v2.getVolume()&&v2.getVolume()<v3.getVolume()){
		// isPriceDownVolUp = true;
		// break;
		// }
		// }
		// }

		// if(volume!=null&&volume.size()>1){
		// for(int i=0;i<volume.size()-1;i++){
		// StockVolume v1 = volume.get(i);
		// StockVolume v2 = volume.get(i+1);
		// if(v1.getPrice1()>v2.getPrice1()&&v1.getVolume()<v2.getVolume()){
		// isPriceDownVolUp = true;
		// break;
		// }
		// }
		// }
	}
	
}