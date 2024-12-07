package com.bookshelf.beans;

public class Address {
	private String address_id;
	private String address;
	private String city;
	private String province;
	private String country;
	private String postal_code;

	public Address(String address_id, String address, String city, String province, String country, String postal_code) {
		super();
		this.address_id = address_id;
		this.address = address;
		this.city = city;
		this.province = province;
		this.country = country;
		this.postal_code = postal_code;
	}
	
	public String getAddress_id() {
		return address_id;
	}
	public void setAddress_id(String address_id) {
		this.address_id = address_id;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public String getPostal_code() {
		return postal_code;
	}
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

}
