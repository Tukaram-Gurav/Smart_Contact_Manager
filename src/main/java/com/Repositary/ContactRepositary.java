package com.Repositary;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.Entities.Contact;
import com.Entities.User;

public interface ContactRepositary extends JpaRepository<Contact, Integer> 
{
	@Query("from Contact as c where c.user.id =:userId")
	public Page<Contact> findByUserId(@Param("userId")int userId,Pageable pageable);
	
	//Searching method
	
	public List<Contact> findByNameContainingAndUser(String query,User user);
	
public List<Contact> findAll();
}
