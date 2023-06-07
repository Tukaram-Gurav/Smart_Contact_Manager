package com.Controller;

import java.util.Properties;

import org.springframework.stereotype.Service;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;

@Service
public class SendEmailService {

	public boolean sendEmail(String subject,String message,String to)
	{
		boolean f=false;
		
		String from ="tukaram1030@gmail.com";
		String host="smtp.gmail.com";
		
		Properties prop=System.getProperties();
		
		
		prop.put("mail.smtp.host", host);
		prop.put("mail.smtp.port", "465");

		prop.put("mail.smtp.ssl.enable", true);
		prop.put("mail.smtp.auth", true);
		
		//Step 1 get object from session
		Session session=Session.getInstance(prop,new Authenticator()
				{
			@Override
			protected PasswordAuthentication getPasswordAuthentication()
			{
				return new PasswordAuthentication("tukaram1030@gmail.com","qwghezqlpikzzsqp");
			}
			
				});
		session.setDebug(true);
		
		//step 2:Cmpose the messege
		
		MimeMessage m=new MimeMessage(session);
		
		try
		{
			//from email
			m.setFrom(from);
			
			//adding receiient to message
			m.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
			
			//adding subject to messege
			m.setSubject(subject);
			//adding text to message
			//m.setText(message);
			m.setContent(message,"text/html");
			//send
			
			//step 3: send the message using transport
			
			Transport.send(m);
			
			System.out.println("sent success............");
			f=true;
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		
		
		
		return f;
	}
}
