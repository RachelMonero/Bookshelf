package com.bookshelf.beans;

import java.util.UUID;

public class UserRole {
	private String user_role_id;
	private String user_id;
	private String role_id;
	private String status;



    public UserRole(String user_role_id, String user_id, String role_id, String status) {
    	super();
    	this.user_role_id= user_role_id;
    	this.user_id=user_id;
    	this.role_id = role_id;
    	this.status = status;
    }
    public void setUser_role_id() {
    	this.user_role_id=user_role_id;
    }
    
    public String getUser_role_id() {
    	return user_role_id;
    }
    public void setUser_id(String user_id) {
    	this.user_id = user_id;
    }
    public String getUser_id() {
    	return user_id;
    }
    public void setRole_id(String role_id) {
        this.role_id=role_id;
    }
    public String getRole_id() {
    	return role_id;
    }
    public void setStatus(String status) {
        this.status=status;
    }
    public String getStatus() {
    	return status;
    }

}