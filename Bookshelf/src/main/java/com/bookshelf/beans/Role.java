package com.bookshelf.beans;

public class Role {
	private String role_id;
	private String role_name;
	private String description;
	
	public Role(String role_id, String role_name, String description) {
		this.role_id = role_id;
		this.role_name = role_name;
		this.description =description;
	}
	public void setRole_id(String role_id) {
		this.role_id = role_id;
	}
	public String getRole_id() {
		return role_id;
	}
	
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getRole_name() {
		return role_name;
	}
	
	public void setDescription(String description) {
		this.description =description;
	}
	public String getDescription() {
		return description;
	}

}
