package com.stock.model;

public class JunXian {
	private Integer id;

	private String symbol;

	private String time;

	private float day;

	private Float five;

	private Float nine;

	private Float thirteen;

	private Float nineteen;

	private Float twentyseven;

	private Float thirtynine;

	private Float fourtynine;

	private Float sixtyfive;

	public JunXian() {
	}

	public JunXian(String symbol, String time, float day, Float five,
			Float nine, Float thirteen, Float nineteen, Float twentyseven,
			Float thirtynine, Float fourtynine, Float sixtyfive) {
		this.symbol = symbol;
		this.time = time;
		this.day = day;
		this.five = five;
		this.nine = nine;
		this.thirteen = thirteen;
		this.nineteen = nineteen;
		this.twentyseven = twentyseven;
		this.thirtynine = thirtynine;
		this.fourtynine = fourtynine;
		this.sixtyfive = sixtyfive;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public float getDay() {
		return day;
	}

	public void setDay(float day) {
		this.day = day;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getSymbol() {
		return symbol;
	}

	public void setSymbol(String symbol) {
		this.symbol = symbol == null ? null : symbol.trim();
	}

	public Float getFive() {
		return five;
	}

	public void setFive(Float five) {
		this.five = five;
	}

	public Float getNine() {
		return nine;
	}

	public void setNine(Float nine) {
		this.nine = nine;
	}

	public Float getThirteen() {
		return thirteen;
	}

	public void setThirteen(Float thirteen) {
		this.thirteen = thirteen;
	}

	public Float getNineteen() {
		return nineteen;
	}

	public void setNineteen(Float nineteen) {
		this.nineteen = nineteen;
	}

	public Float getTwentyseven() {
		return twentyseven;
	}

	public void setTwentyseven(Float twentyseven) {
		this.twentyseven = twentyseven;
	}

	public Float getThirtynine() {
		return thirtynine;
	}

	public void setThirtynine(Float thirtynine) {
		this.thirtynine = thirtynine;
	}

	public Float getFourtynine() {
		return fourtynine;
	}

	public void setFourtynine(Float fourtynine) {
		this.fourtynine = fourtynine;
	}

	public Float getSixtyfive() {
		return sixtyfive;
	}

	public void setSixtyfive(Float sixtyfive) {
		this.sixtyfive = sixtyfive;
	}

	@Override
	public String toString() {
		return "JunXian [id=" + id + ", symbol=" + symbol + ", five=" + five
				+ ", nine=" + nine + ", thirteen=" + thirteen + ", nineteen="
				+ nineteen + ", twentyseven=" + twentyseven + ", thirtynine="
				+ thirtynine + ", fourtynine=" + fourtynine + ", sixtyfive="
				+ sixtyfive + "]";
	}

}