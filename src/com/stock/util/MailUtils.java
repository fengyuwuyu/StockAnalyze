package com.stock.util;

import java.util.Date;
import java.util.List;

import com.stock.mail.MailInfo;
import com.stock.mail.SendMail;
import com.stock.model.email.CecepEmail;
import com.stock.dao.eamil.CecepEmailLogMapper;
import com.stock.dao.eamil.CecepEmailMapper;

public class MailUtils {

	private static CecepEmailLogMapper cecepEmailLogMapper = null;
	private static CecepEmailMapper cecepEmailMapper = null;

	public void setCecepEmailLogMapper(CecepEmailLogMapper emailLogMapper) {
		cecepEmailLogMapper = emailLogMapper;
	}

	public void setCecepEmailMapper(CecepEmailMapper emailMapper) {
		cecepEmailMapper = emailMapper;
	}

	public static void sendErrorMsgToAdmin(String excInfo) throws Exception {
		MailInfo info = new MailInfo();
		info.setSubject("一卡通系统异常");
		String content = "您好，股票系统"+CommonsUtil.formatDateToString3(new Date())+"出现异常，请及时查看系统日志或exception_log表！ \n\r"+excInfo;
		info.setContent(content);
		CecepEmail sender = cecepEmailMapper.selectSender();
		String host = sender.getHost();
		String username = sender.getUsername();
		String password = sender.getPassword();
		List<CecepEmail> receivers = cecepEmailMapper.selectReveivers();
		info.setFrom(sender.getEamilAddr());
		info.setToAddress(receivers.get(0).getEamilAddr());
		for (int i = 1; i < receivers.size(); i++) {
			info.setCcAddress(receivers.get(i).getEamilAddr());
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
