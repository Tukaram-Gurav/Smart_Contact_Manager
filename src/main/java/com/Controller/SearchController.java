package com.Controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.Entities.*;
import com.Repositary.ContactRepositary;
import com.Repositary.UserRepositary;

@RestController
public class SearchController 
{
	@Autowired
	public UserRepositary repo;

	@Autowired
	public ContactRepositary con;
	
	
	@GetMapping("/search/{query}")
	public ResponseEntity<?> searching(@PathVariable("query") String query,Principal p) 
	{
		User user=this.repo.getUserByUsername(p.getName());
		List<Contact> contact=this.con.findByNameContainingAndUser(query, user);
		
		return ResponseEntity.ok(contact);
	}

}
