package com.Controller;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.security.Principal;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpRequest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.Entities.*;
import com.Repositary.ContactRepositary;
import com.Repositary.UserRepositary;
import com.helper.Messege;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
@RequestMapping("/user")
public class UserController {
	@Autowired
	public BCryptPasswordEncoder bCrypt;
	
	@Autowired
	public UserRepositary repo;

	@Autowired
	public ContactRepositary con;
	
	@ModelAttribute
	public void addCommonData(Model m, Principal p) {

		String username = p.getName();
		User user = repo.getUserByUsername(username);
		m.addAttribute("user", user);

	}

	@RequestMapping("/index")
	public String dashBoard() {
		return "normal/index";
	}

	@GetMapping("/add_contacts")
	public String addContacts(Model m, Principal p) {
		m.addAttribute("tite", "Add contacts");
		m.addAttribute("contact", new Contact());

		return "normal/add_contacts";

	}

	@PostMapping("/process_contact")
	public String processContact(@ModelAttribute Contact contact, @RequestParam("ProfileImage") MultipartFile file,
			Principal p, HttpSession session) {
		try {
			String username = p.getName();
			User user = repo.getUserByUsername(username);
			contact.setUser(user);
// HAndling Image file

			if (file.isEmpty()) {
				//System.out.println("file is empty");
				contact.setImage("contacts.png");

			} else {
				contact.setImage(file.getOriginalFilename());
				File file2 = new ClassPathResource("static/Image").getFile();
				Path path = Paths.get(file2.getAbsolutePath() + File.separator + file.getOriginalFilename());
				Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
				//System.out.println("file is uploaded successfully");

			}

			user.getContact().add(contact);
			this.repo.save(user);
			System.out.println("DATA" + contact);

			session.setAttribute("message", new Messege("Contact is added successfully ! Add More !", "success"));
			session.setMaxInactiveInterval(3);
		} catch (Exception e) {
			e.printStackTrace();

			session.setAttribute("message", new Messege("Something went wrong ! Please check !", "danger"));
			session.setMaxInactiveInterval(3);
		}
		return "normal/add_contacts";
	}

	// Showing Contacts
	@GetMapping("/show-Contacts/{page}")
	public String ShowAllContacts(@PathVariable("page") Integer page, Model m, Principal p) {

		Pageable pageable = PageRequest.of(page, 5);
		String username = p.getName();
		User user = repo.getUserByUsername(username);
		Page<Contact> contact = con.findByUserId(user.getId(), pageable);
		m.addAttribute("contacts", contact);
		m.addAttribute("curentpage", page);
		m.addAttribute("totalpages", contact.getTotalPages());

		return "normal/show_Contacts";
	}

	// Handling single Contact
	@RequestMapping("/{Cid}/contact")
	public String ShowSingleContact(@PathVariable("Cid") Integer Cid, Model m, Principal p) {
		System.out.println("CID" + Cid);
		Optional<Contact> ConbyId = con.findById(Cid);
		String username = p.getName();
		User user = repo.getUserByUsername(username);

		Contact contact = ConbyId.get();

		if (user.getId() == contact.getUser().getId()) {
			m.addAttribute("contact", contact);
			m.addAttribute("title", contact.getName());
		}

		return "normal/Single_Contact";
	}

	// Delete Contact
	@RequestMapping("/delete/{Cid}")
	public String DeleteContact(@PathVariable("Cid") Integer Cid, Principal p, HttpSession session) {
		Optional<Contact> conopt = con.findById(Cid);
		Contact contact = conopt.get();

		String username = p.getName();
		User user = repo.getUserByUsername(username);

		System.out.println("Deleted");

		if (user.getId() == contact.getUser().getId()) {
			con.delete(contact);
			session.setAttribute("message", new Messege("Contact Deleted Successfully", "danger"));
			session.setMaxInactiveInterval(3);
		}

		return "redirect:/user/show-Contacts/0";

	}

	// update process
	@PostMapping("/update-process/{Cid}")
	public String Update_process(@PathVariable("Cid") Integer Cid, Model m) {
		Optional<Contact> conopt = con.findById(Cid);
		Contact contact = conopt.get();
		m.addAttribute("contact", contact);

		return "normal/Update_Contact";
	}
	
	

	// Saving updated Contacts
	
	@PostMapping("/saveupContact")
	public String SaveUpdatedContact(@ModelAttribute Contact contact, Model m,
			@RequestParam("ProfileImage") MultipartFile file, Principal p, HttpSession session) throws IOException {
		// Old Details
		Contact oldcontact = con.findById(contact.getCid()).get();

		if (!file.isEmpty()) {

			// delete old photo
			File deleteFile = new ClassPathResource("static/Image").getFile();
			File file3 = new File(deleteFile, oldcontact.getImage());
			file3.delete();

			// upload new photo

			File file2 = new ClassPathResource("static/Image").getFile();
			Path path = Paths.get(file2.getAbsolutePath() + File.separator + file.getOriginalFilename());
			Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
			contact.setImage(file.getOriginalFilename());
		} else {
			contact.setImage(oldcontact.getImage());
		}

		User user = repo.getUserByUsername(p.getName());
		contact.setUser(user);
		con.save(contact);
		session.setAttribute("message", new Messege("Contact updated successfully", "success"));
		session.setMaxInactiveInterval(3);
		return "redirect:/user/" + contact.getCid() + "/contact";
	}

//Showing Profile
	@RequestMapping("/profile")
	public String profile() {
		return "normal/profile";
	}
	//Showing Settings
	@RequestMapping("/setting")
	public String showSetting()
	{
		return "normal/settings";
		
	}
	
	//Changing Password
	
	@PostMapping("/change-password")
	public String change_password(@RequestParam("old")String old,@RequestParam("new")String newp,Principal p,HttpSession session)
	{
		String name = p.getName();
		User user = repo.getUserByUsername(name);
		String password = user.getPassword();
		if(this.bCrypt.matches(old, password))
		{
			user.setPassword(this.bCrypt.encode(newp));
			repo.save(user);
			session.setAttribute("message",new Messege("Password Changed Successfully","success"));
			session.setMaxInactiveInterval(2);
		}
		else
		{
			session.setAttribute("message",new Messege("Old Password Does Not Match","danger"));
			session.setMaxInactiveInterval(2);
			return "redirect:/user/setting";
		}
		
		return "redirect:/user/index";
	}
	@RequestMapping("/Change")
	public String gotopage()
	{
		return "normal/Change_Password";
	}
	
	
	
}
