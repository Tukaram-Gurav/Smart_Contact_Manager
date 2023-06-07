package com.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.helper.Email;

@RestController
public  class EmailController 
{
	@Autowired
	private SendEmailService mailservice;
	
	
	@RequestMapping(value="/sendemail" ,method=RequestMethod.POST)
	public ResponseEntity<?> sendEmail(@RequestBody Email email)
	{
		
		boolean res=mailservice.sendEmail(email.getSubject(),email.getMessage(),email.getTo());
		if(res)
		{
			return ResponseEntity.ok("Done..email sent.");
			
		}
		else
		{
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("error...");
		}
		
	}

}
