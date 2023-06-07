package com.Controller;

import java.io.ByteArrayInputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Entities.Contact;
import com.Repositary.ContactRepositary;
import com.helper.XMlHelper;

@Service
public class XLService 
{
	@Autowired
	private ContactRepositary con;

	public ByteArrayInputStream getData()
	{
		List<Contact> contact=con.findAll();
		ByteArrayInputStream xldata = XMlHelper.xldata(contact);
		return xldata;
	}
}
