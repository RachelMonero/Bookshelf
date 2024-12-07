package com.bookshelf.dtos;

public class UserRoleDto {
	
	private String user_role_id;
	private String role_name;
	private String status;
	
	public UserRoleDto(String user_role_id, String role_name, String status) {
		this.user_role_id = user_role_id;
		this.role_name = role_name;
		this.status = status;
	}
	

	public UserRoleDto() {
		// TODO Auto-generated constructor stub
	}


	public void setUser_role_id(String user_role_id) {
		this.user_role_id = user_role_id;
	}
	public String getUser_role_id() {
		return user_role_id;
	}
	
	public void setRole_name(String role_name) {
		this.role_name = role_name;
	}
	public String getRole_name() {
		return role_name;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}

}
