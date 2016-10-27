/**
 * 
 */
package com.stock.mail;

import java.io.File;

/**
 * @ClassName AttachBean.java
 * @Description
 * @author lilei
 * @Version
 * @Date 2015-12-16 上午9:52:45
 * @see Company:BXXJS All copyright reserved!!!
 */
public class AttachBean {

	public AttachBean(String filename, File file) {
		this.filename = filename;
		this.file = file;
	}

	public AttachBean() {
	}

	private String filename;
	private File file;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}

	@Override
	public String toString() {
		return "AttachBean [filename=" + filename + ", fileAbsolutePath="
				+ file.getAbsolutePath() + "]";
	}

}
