package com.bookshelf.beans;

import java.sql.Timestamp;

public class Verification {
	private String verification_id;
	private String user_id;
	private String verification_type;
	private String verification_code;
	private Timestamp created_at;
	private String status;
	
	public Verification(String verification_id, String user_id, String verification_type,String verification_code, Timestamp created_at, String status) {
		this.verification_id = verification_id;
		this.user_id = user_id;
		this.verification_type = verification_type;
		this.verification_code = verification_code;
		this.created_at = created_at;
		this.status = status;
		
	}
	
	public void setVerification_id(String verification_id) {
		this.verification_id = verification_id;
	}
	public String getVerification_id() {
		return verification_id;
	}
	
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_id() {
		return user_id;
	}
	
	public void setVerification_type(String verification_type) {
	    this.verification_type = verification_type;
	}
	public String getVerification_type() {
		return verification_type;
	}
	
	public void setVerification_code(String verification_code) {
		this.verification_code =  verification_code;
	}
	public String getVerification_code() {
		return verification_code;
	}
	
	public void setCreated_at(Timestamp created_at) {
		this.created_at = created_at;
	}
	public Timestamp getCreated_at() {
		return created_at;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}


}
