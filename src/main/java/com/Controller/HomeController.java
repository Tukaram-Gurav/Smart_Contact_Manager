package com.Controller;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

//import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Entities.User;
import com.Repositary.UserRepositary;
import com.helper.Messege;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionEvent;
import jakarta.validation.Valid;


@Controller
public class HomeController
{
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	@Autowired
    private UserRepositary repo;
	
	
	 	
	
	@RequestMapping("/")
	public String home(Model m)
	{
		m.addAttribute("title", "Smart Contact Manager");
		return "Home";
	}
	
	@RequestMapping("/login")
	public String Login(Model m)
	{
		m.addAttribute("title", "Smart Contact Manager");
		return "Login";
	}
	
	@RequestMapping("/about")
	public String ABOUT(Model m)
	{
		
		return "About";
	}
	
	@RequestMapping("/signup")
	public String SignUp(Model m)
	{
		m.addAttribute("user", new User());
		
		return "Signup";
	}
	@RequestMapping(value="/do_register",method=RequestMethod.POST)
	public String RegisterUser(@Valid @ModelAttribute("user") User user,BindingResult result1,@RequestParam(value="agreement",defaultValue="false") boolean agreement,@RequestParam("UserImage") MultipartFile file,Model m,HttpSession session)
	{
		try
		{
			if(!agreement)
			{
				System.out.println("Accept the terms abd conditions");
				throw new Exception("Accept the terms abd conditions");
			}
			if(result1.hasErrors())
			{
				System.out.println("Errors"+result1.toString());
				m.addAttribute("user",user);
				return "Signup";
			}
			
			
			//Image 
			
			if (file.isEmpty()) {
				System.out.println("file is empty");
				user.setImage("contacts.png");

			} else {
				user.setImage(file.getOriginalFilename());
				File file2 = new ClassPathResource("static/Image").getFile();
				Path path = Paths.get(file2.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				//System.out.println("file is uploaded successfully");

			}
			
			System.out.println(user.getPassword());
		
			user.setRole("USER");
			user.setEnabled(true);
			user.setPassword(passwordEncoder.encode(user.getPassword()));
			System.out.println("Agreement"+agreement);
			System.out.println("User"+user);
			User result = repo.save(user);
	        m.addAttribute("user", result);
	        session.setAttribute("messege",new Messege("Successfully Registered","alert-success"));
	       session.setMaxInactiveInterval(3);
	        return "Signup";
		}
		catch(Exception e)
		{
			e.printStackTrace();
			m.addAttribute("user", user);
			session.setAttribute("messege",new Messege("Something went wrong"+e.getMessage(), "alert-danger"));
			session.setMaxInactiveInterval(3);
			return "Signup";
		}
	}
}
