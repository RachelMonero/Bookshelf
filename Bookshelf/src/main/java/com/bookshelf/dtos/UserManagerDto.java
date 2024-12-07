package com.bookshelf.dtos;

import java.util.List;

import com.bookshelf.beans.Address;
import com.bookshelf.beans.Reservation;
import com.bookshelf.beans.User;
import com.bookshelf.beans.UserRole;


public class UserManagerDto {
	
	private User user;
	private Address address;
	private UserRoleDto userRole;
	private int totalReservation;
	
	public UserManagerDto(User user, Address address, UserRoleDto userRole, int totalReservation){
		super();
		this.user = user;
		this.address = address;
		this.userRole = userRole;
		this.totalReservation = totalReservation;
		
	}

    public UserManagerDto() {
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public UserRoleDto getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRoleDto userRole) {
        this.userRole = userRole;
    }

    public int getTotalReservation() {
        return totalReservation;
    }

    public void setTotalReservation(int totalReservation) {
        this.totalReservation = totalReservation;
    }	
	

}

