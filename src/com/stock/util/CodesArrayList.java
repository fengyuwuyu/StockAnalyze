package com.stock.util;

import java.util.ArrayList;
import java.util.Collection;

public class CodesArrayList extends ArrayList<String> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4932874049717933148L;
	private String a = "";
	
	@Override
	public boolean addAll(Collection<? extends String> c) {
		a = c.toString().replace(" ", "");
		return super.addAll(c);
	}
	
	public String getValue(){
		return a.substring(1,a.length()-1);
	}
}
