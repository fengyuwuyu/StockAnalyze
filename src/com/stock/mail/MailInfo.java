/**
 * 
 */
package com.stock.mail;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName MailInfo.java
 * @Description
 * @author lilei
 * @Version
 * @Date 2015-12-16 上午9:12:17
 * @see Company:BXXJS All copyright reserved!!!
 */
public class MailInfo {

	private String from;
	private StringBuilder toAddress = new StringBuilder();
	private StringBuilder ccAddress = new StringBuilder();
	private StringBuilder bccAddress;
	private String subject;
	private String content;
	private List<AttachBean> attachBeans = new ArrayList<AttachBean>();

	public MailInfo() {
	}

	public MailInfo(String from, StringBuilder toAddress,
			StringBuilder ccAddress, StringBuilder bccAddress, String subject,
			String content, List<AttachBean> attachBeans) {
		super();
		this.from = from;
		this.toAddress = toAddress;
		this.ccAddress = ccAddress;
		this.bccAddress = bccAddress;
		this.subject = subject;
		this.content = content;
		this.attachBeans = attachBeans;
	}

	public String getFrom() {
		return from;
	}

	public void setFrom(String from) {
		this.from = from;
	}

	public String getToAddress() {
		return toAddress==null?null:toAddress.toString();
	}

	public void setToAddress(String toAddress) {
		if (this.toAddress!=null&&this.toAddress.length() > 0) {
			this.toAddress.append(",");
		}
		this.toAddress.append(toAddress);
	}

	public String getCcAddress() {
		return ccAddress==null?null:ccAddress.toString();
	}

	public void setCcAddress(String ccAddress) {
		if (this.ccAddress.length() > 0) {
			this.ccAddress.append(",");
		}
		this.ccAddress.append(ccAddress);
	}

	public String getBccAddress() {
		return bccAddress==null?null:bccAddress.toString();
	}

	public void setBccAddress(String bccAddress) {
		if (this.bccAddress.length() > 0) {
			this.bccAddress.append(",");
		}
		this.bccAddress.append(bccAddress);
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public List<AttachBean> getAttachBeans() {
		return attachBeans;
	}

	public void setAttachBeans(List<AttachBean> attachBeans) {
		this.attachBeans = attachBeans;
	}

	public void addAttachBeans(AttachBean attachBean) {
		this.attachBeans.add(attachBean);
	}

	@Override
	public String toString() {
		return "MailInfo [from=" + from + ", toAddress=" + toAddress
				+ ", ccAddress=" + ccAddress + ", bccAddress=" + bccAddress
				+ ", subject=" + subject + ", content=" + content
				+ ", attachBeans=" + attachBeans + "]";
	}

}
