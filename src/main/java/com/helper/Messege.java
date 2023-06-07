package com.helper;

public class Messege
{
public String content;
public String type;

public Messege(String messege, String type) {
	super();
	content = messege;
	this.type = type;
}

public String getMessege() {
	return content;
}

public void setMessege(String messege) {
	content = messege;
}

public String getType() {
	return type;
}

public void setType(String type) {
	this.type = type;
}

public Messege() {
	super();
	// TODO Auto-generated constructor stub
}

@Override
public String toString() {
	return "Messege [Messege=" + content + ", type=" + type + "]";
}



}
