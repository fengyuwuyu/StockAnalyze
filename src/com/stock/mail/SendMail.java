/**
 * 
 */
package com.stock.mail;

import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message.RecipientType;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * @ClassName SendMail.java
 * @Description
 * @author lilei
 * @Version
 * @Date 2015-12-16 上午9:13:50
 * @see Company:BXXJS All copyright reserved!!!
 */
public class SendMail {
	private static final String SSL_FACTORY = "javax.net.ssl.SSLSocketFactory";

	private static Session createSession(String host, final String username,
			final String password) {
//		Properties props = new Properties();
//		props.setProperty( "mail.smtp.user" , username );
//		props.setProperty("mail.transport.protocol", "smtp");
//		props.setProperty("mail.password", password);
////		props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");  
//		props.setProperty("mail.smtp.host", host);
////		props.setProperty("mail.smtp.socketFactory.fallback", "false"); 
//		props.setProperty("mail.smtp.port", "25");  
////		props.setProperty("mail.smtp.socketFactory.port", "465");  
//		props.setProperty("mail.smtp.auth", "true");
		
		Properties properties = new Properties();
		properties.put("mail.smtp.host", host);
		properties.put("mail.smtp.auth", "true");
		properties.put("mail.smtp.socketFactory.class", SSL_FACTORY);  //使用JSSE的SSL socketfactory来取代默认的socketfactory
		properties.put("mail.smtp.socketFactory.fallback", "false");  // 只处理SSL的连接,对于非SSL的连接不做处理
																
		properties.put("mail.smtp.port", "465");
		properties.put("mail.smtp.socketFactory.port", "465");
		Authenticator authenticator = new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		};
		return Session.getInstance(properties, authenticator);
	}

	public static void send(String host, final String username,
			final String password, MailInfo mailInfo)
			throws Exception {
//		Security.addProvider(new com.sun.net.ssl.internal.ssl.Provider());
		Session session = createSession(host, username, password);
		System.out.println("session  created "+session);
		MimeMessage message = new MimeMessage(session);
		String from = mailInfo.getFrom();
		if ( isEmpty(from)) {
			throw new Exception("发件人地址不能为空！");
		}
		message.setFrom(new InternetAddress(from));
		String toAddress = mailInfo.getToAddress();
		if ( isEmpty(toAddress)) {
			throw new Exception("收件人地址不能为空！");
		}
		// 添加收件人
		message.addRecipients(RecipientType.TO, toAddress);
		// 添加抄送人
		String ccAddress = mailInfo.getCcAddress();
		if (!isEmpty(ccAddress)) {
			message.addRecipients(RecipientType.CC, ccAddress);
		}
		// 添加暗送人
		String bccAddress = mailInfo.getBccAddress();
		if (!isEmpty(bccAddress)) {
			message.addRecipients(RecipientType.BCC, bccAddress);
		}
		//设置主题
		message.setSubject(mailInfo.getSubject());
		
		MimeMultipart multiparts = new MimeMultipart();
		MimeBodyPart contentBodyPart = new MimeBodyPart();
		contentBodyPart.setContent(mailInfo.getContent(), "text/html;charset=utf-8");
		multiparts.addBodyPart(contentBodyPart);
		List<AttachBean> attachBeans = mailInfo.getAttachBeans();
		if(attachBeans!=null&&!attachBeans.isEmpty()){
			for(AttachBean attachBean:attachBeans){
				MimeBodyPart bodyPart = new MimeBodyPart();
				bodyPart.attachFile(attachBean.getFile());
				bodyPart.setFileName(MimeUtility.encodeText(attachBean.getFilename()));
				multiparts.addBodyPart(bodyPart);
			}
		}
		
		//设置内容及附件
		message.setContent(multiparts);
		Transport.send(message);
	}
	
	private static boolean isEmpty(String str){
		return str==null||str.isEmpty();
	}
}
