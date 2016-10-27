package com.stock.model.email;

import java.util.Date;

public class CecepEmailLog {
	private Integer id;

	private String receiver;

	private String message;

	private Date time;

	private String username;

	public CecepEmailLog() {
	}

	public CecepEmailLog(String receiver, String message, Date time,
			String username) {
		this.receiver = receiver;
		this.message = message;
		this.time = time;
		this.username = username;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getReceiver() {
		return receiver;
	}

	public void setReceiver(String receiver) {
		this.receiver = receiver == null ? null : receiver.trim();
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message == null ? null : message.trim();
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "CecepEmailLog [id=" + id + ", receiver=" + receiver
				+ ", message=" + message + ", time=" + time + ", username="
				+ username + "]";
	}

}