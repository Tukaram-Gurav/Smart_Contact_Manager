package com.Entities;

import java.util.*;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity

public class User
{
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@NotBlank(message="Name should not be blank")
	@Size(min=2,max=20,message="Minimum 2 and maximum 20 characters are allowed")
	private String name;
	@Column(unique = true)
	@NotBlank(message="Email shold not be blank")
	@Email(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
	private String email;
	private String role;
	private String password;
	private String image;
	private boolean enabled;
	@Column(length=500)
	private String About;
	@OneToMany(cascade =CascadeType.ALL,fetch=FetchType.LAZY,mappedBy = "user")
	private List<Contact>contact=new ArrayList<>();
	
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
	public User(int id, String name, String email, String role, String password, String image, boolean enabled,
			String about) {
		super();
		this.id = id;
		this.name = name;
		this.email = email;
		this.role = role;
		this.password = password;
		this.image = image;
		this.enabled = enabled;
		About = about;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", email=" + email + ", role=" + role + ", password=" + password
				+ ", image=" + image + ", enabled=" + enabled + ", About=" + About + "]";
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image)
	{
		this.image = image;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public String getAbout() {
		return About;
	}
	public void setAbout(String about) {
		About = about;
	}
	public List<Contact> getContact() {
		return contact;
	}
	public void setContact(List<Contact> contact) {
		this.contact = contact;
	}
	
}
