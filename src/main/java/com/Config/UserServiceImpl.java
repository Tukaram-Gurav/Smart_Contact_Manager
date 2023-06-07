package com.Config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.Entities.*;
import com.Repositary.UserRepositary;

public class UserServiceImpl implements UserDetailsService
{
	@Autowired
	private UserRepositary repo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException 
	{
		
		User user=repo.getUserByUsername(username);
		if(user==null)
		{
			throw new UsernameNotFoundException("Could not found User");
		}
		CustomUserDetails customUserDetails=new CustomUserDetails(user);
		
		return customUserDetails;
	}

}
