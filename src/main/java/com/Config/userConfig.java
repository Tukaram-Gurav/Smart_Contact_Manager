 package com.Config;


import org.springframework.context.annotation.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.*;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class userConfig 
{
	
	
    @Bean
     public UserDetailsService getUserDetailService()
     {
	     return new UserServiceImpl();
     }

    @Bean
    public BCryptPasswordEncoder passwordEncoderee()
	{
		return new BCryptPasswordEncoder();
	}
    
    
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception
    {
    	return configuration.getAuthenticationManager();
    }
    
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception
    {
    	http.authorizeHttpRequests()
    	.requestMatchers("/admin/**")
    	.hasAuthority("ADMIN")
		.requestMatchers("/user/**")
		.hasAuthority("USER")
		.requestMatchers("/**")
		.permitAll().and().formLogin().loginPage("/login").defaultSuccessUrl("/user/index")
		.and().csrf()
		.disable();
    	
    	http.authenticationProvider(authenticationProvider());
    	DefaultSecurityFilterChain securityfilter = http.build();
		return securityfilter;
    } 
    

    @Bean
    public DaoAuthenticationProvider authenticationProvider()
	{
		DaoAuthenticationProvider auth=new DaoAuthenticationProvider();
		auth.setUserDetailsService(this.getUserDetailService());
		auth.setPasswordEncoder(passwordEncoderee());
		return auth;
		
	}
    
   
    
    //configure
	
    
	/*
	 * protected void configure(AuthenticationManagerBuilder auth2) throws Exception
	 * { auth2.authenticationProvider(authenticationProvider()); }
	 */
	
	
	/*
	 * protected void configure(HttpSecurity http) throws Exception {
	 * http.authorizeRequests().requestMatchers("/admin/**").hasRole("ADMIN")
	 * .requestMatchers("/user/**").hasRole("USER")
	 * .requestMatchers("/**").permitAll().and().formLogin().and().csrf().disable();
	 * 
	 * }
	 */
	
	
	
	
	
	
	
}
