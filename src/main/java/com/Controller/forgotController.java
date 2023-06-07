package com.Controller;

import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import com.Entities.User;
import com.Repositary.UserRepositary;

import jakarta.servlet.http.HttpSession;

@Controller
public class forgotController
{
	@Autowired
	public BCryptPasswordEncoder bCrypt;
	@Autowired
	private SendEmailService emailservice;
	
	@Autowired
	public UserRepositary repo;
	@RequestMapping("/forgot")
	public String forgot()
	{
		return"ForgotPassword";
	}
	@PostMapping("/forgot-process")
	public String forgot_Process(@RequestParam("email") String email,HttpSession session)
	{
		Random random=new Random(1000);
		
		int otp=random.nextInt(99999);
		
		User user=this.repo.getUserByUsername(email);
		
		if(user!=null)
		{
		
		System.out.println(email+" "+otp);
		String subject="otp from Smart Contact Manager";
		String message="<div style='border: 1px solid red; padding:20px;'>" 
		
				+"<h1>"
				+"Smart Contact Manager"
				+" <br>Your OTP IS "
				+"<b>"+otp+"</b>"
				+"</h1>"
				+"</div>";
				
						
		boolean flag = this.emailservice.sendEmail(subject, message, email);
		
		if(flag)
		{
			session.setAttribute("myotp", otp);
			session.setAttribute("email", email);
			
			return "verify_otp";
		}
		else
		{
			session.setAttribute("message", "Please check your email");
			session.setMaxInactiveInterval(2);
			return "ForgotPassword";
		}
		}
		else
		{
			session.setAttribute("message","User Does Not Exist..!");
			return"ForgotPassword";
			
		}
	}
	@PostMapping("/password-checker")
	public String Change_password(@RequestParam("myotp")int otp ,HttpSession session)
	{
		int myotp = (int) session.getAttribute("myotp");
		String email=(String) session.getAttribute("email");
		
		if(myotp==otp)
		{
    	 return "change_password";
      
		}
		else
		{
			session.setAttribute("message", "Please Enter Correct Otp");
			session.setMaxInactiveInterval(2);
			return "verify_otp";
		}
		
		
		
	}
	@PostMapping("/change-password")
	public String confirmPassword(@RequestParam("newpassword")String newpassword,HttpSession session)
	{
		String email=(String) session.getAttribute("email");
		User user = repo.getUserByUsername(email);
		user.setPassword(this.bCrypt.encode(newpassword) );
		repo.save(user);
		// session.setAttribute("message"," Password Changed Successfully");
		return "redirect:/login?change=Password Changed Successfully";
	}

}
