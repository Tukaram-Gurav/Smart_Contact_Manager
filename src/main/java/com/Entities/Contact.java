package com.Entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
public class Contact {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int Cid;
	private String name;
	private String NickName;
	private String Work;
	private String Email;
	private String Image;
	private String Phone;
	private String Description;

	@ManyToOne
	@JsonIgnore
	private User user;

	public Contact() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Contact(int cid, String name1, String nickName, String work, String email, String image, String phone,
			String description, User user) {
		super();
		this.Cid = cid;
		this.name = name1;
		this.NickName = nickName;
		this.Work = work;
		this.Email = email;
		this.Image = image;
		this.Phone = phone;
		this.Description = description;
		this.user = user;
	}

	public int getCid() {
		return Cid;
	}

	public void setCid(int cid) {
		Cid = cid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name2) {
		this.name = name2;
	}

	public String getNickName() {
		return NickName;
	}

	public void setNickName(String nickName) {
		NickName = nickName;
	}

	public String getWork() {
		return Work;
	}

	public void setWork(String work) {
		Work = work;
	}

	public String getEmail() {
		return Email;
	}

	public void setEmail(String email) {
		Email = email;
	}

	public String getImage() {
		return Image;
	}

	public void setImage(String image) {
		Image = image;
	}

	public String getPhone() {
		return Phone;
	}

	public void setPhone(String phone) {
		Phone = phone;
	}

	public String getDescription() {
		return Description;
	}

	public void setDescription(String description) {
		Description = description;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Contact [Cid=" + Cid + ", Name=" + name + ", NickName=" + NickName + ", Work=" + Work + ", Email="
				+ Email + ", Image=" + Image + ", Phone=" + Phone + ", Description=" + Description + ", user=" + user
				+ "]";
	}

}
