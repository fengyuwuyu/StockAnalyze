package com.stock.util;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.stock.mail.MailInfo;
import com.stock.mail.SendMail;
import com.stock.model.email.CecepEmail;
import com.stock.dao.email.CecepEmailLogMapper;
import com.stock.dao.email.CecepEmailMapper;

@Component
public class MailUtils {

	private static CecepEmailLogMapper cecepEmailLogMapper = null;
	private static CecepEmailMapper cecepEmailMapper = null;

	@Autowired
	public void setCecepEmailLogMapper(CecepEmailLogMapper emailLogMapper) {
		cecepEmailLogMapper = emailLogMapper;
	}

	@Autowired
	public void setCecepEmailMapper(CecepEmailMapper emailMapper) {
		cecepEmailMapper = emailMapper;
	}

	public static void sendMail(String subject, String msg) throws Exception {
		MailInfo info = new MailInfo();
		info.setSubject(subject);
		String content = CommonsUtil.formatDateToString3(new Date())+" ： "+msg;
		info.setContent(content);
		CecepEmail sender = cecepEmailMapper.selectSender();
		String host = sender.getHost();
		String username = sender.getUsername();
		String password = sender.getPassword();
		List<CecepEmail> receivers = cecepEmailMapper.selectReveivers();
		info.setFrom(sender.getEmailAddr());
		info.setToAddress(receivers.get(0).getEmailAddr());
		if(receivers.size()>1){
			for (int i = 1; i < receivers.size(); i++) {
				info.setCcAddress(receivers.get(i).getEmailAddr());
			}
		}
		SendMail.send(host, username, password, info);
		saveEmailInfo(receivers, new Date(), content, cecepEmailLogMapper);
	}

	/**
	 * 将已发邮件日志添加到cecep_email_log表中
	 * 
	 * @param receivers
	 * @param date
	 * @param content
	 * @param cecepEmailLogMapper
	 */
	private static void saveEmailInfo(List<CecepEmail> receivers, Date date,
			String content, CecepEmailLogMapper cecepEmailLogMapper) {
		cecepEmailLogMapper.insertSelective(MapUtils.createMap("list",
				receivers, "msg", content, "date", date));
	}

}
