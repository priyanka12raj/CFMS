package com.cfms.server.mail;
import java.util.*;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * Mailer to send java mails for CFMS
 * @author Priyanka , Chaitra, Samta
 *
 */
public class CFMSMailer
{
	/**
	 * recepient mail id
	 */
	private String mail_recepients_TO;
	/**
	 * cc recepient mail id
	 */
	private String mail_recepients_CC;
	/**
	 * is cc mail id set
	 */
	boolean isCCset;
	/**
	 * subject of the mail
	 */
	private String mail_subject;
	/**
	 * content of the mail
	 */
	private String mail_content;
	/**
	 * sets the cc recepients of this mail
	 * @param mail_recepients_CC the mail_recepients to set
	 */
	public void setMail_recepients_TO(String mail_recepients) {
		this.mail_recepients_TO = mail_recepients;
	}
	/**
	 * sets the recepients of this mail
	 * @param mail_recepients_CC the mail_recepients to set
	 */
	public void setMail_recepients_CC(String mail_recepients) {
		this.mail_recepients_CC = mail_recepients;
		isCCset=true;
	}
	/**
	 * sets the subject of the mail
	 * @param mail_subject the mail_subject to set
	 */
	public void setMail_subject(String mail_subject) {
		this.mail_subject = mail_subject;
	}
	/**
	 * sets the content/body of the mail
	 * @param mail_content the mail_content to set
	 */
	public void setMail_content(String mail_content) {
		this.mail_content = mail_content;
	}
	/**
	 * sends the mail to the recepients and also to cc recepients
	 */
	public void sendMail()
	{
		final String username="noreply.cfms@gmail.com";
		final String password="cfmscfms";
			Properties props = new Properties();
			/**
			 * put the properties to connect to the smtp server
			 */
			props.put("mail.smtp.host", "smtp.gmail.com");
			props.put("mail.smtp.socketFactory.port", "465");
			props.put("mail.smtp.socketFactory.class",
					"javax.net.ssl.SSLSocketFactory");
			props.put("mail.smtp.auth", "true");
			props.put("mail.smtp.port", "465");
			
			/**
			 * using the properties open a session and to connect mail server. 
			 * Sign in into the username account by authenticating using password
			 */
			Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username,password);
					}
				});

			/**
			 * try to send the mail
			 */
			try {
				/**
				 * Create a message for the session and prepare the mail
				 */
				Message message = new MimeMessage(session); 
				message.setFrom(new InternetAddress(username));
				message.setRecipients(Message.RecipientType.CC,
						InternetAddress.parse(mail_recepients_TO));
				if(isCCset)
					message.setRecipients(Message.RecipientType.CC, InternetAddress.parse(mail_recepients_CC));
				message.setSubject(mail_subject);
				message.setText(mail_content);
				/**
				 * send the mail
				 */
				Transport.send(message);

				System.out.println("Done");

			} catch (MessagingException e) {
				//throw new RuntimeException(e);
				System.out.print("IGNORED \"");
				e.printStackTrace();
				System.out.println("Mail could not be sent");
				
			}
		
	}
	
}