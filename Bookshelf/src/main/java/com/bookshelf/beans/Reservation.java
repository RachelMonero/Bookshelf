package com.bookshelf.beans;

import java.sql.Timestamp;

public class Reservation {
	private String reservation_id;
	private String user_id;
	private String library_book_id;
	private Timestamp reserved_date;
	private String status;
	private String username; 
	private String book_name;
    private String isbn;
	
	public Reservation(String reservation_id, String user_id, String library_book_id, Timestamp reserved_date,String status) {
		super();
		this.reservation_id = reservation_id;
		this.user_id = user_id;
		this.library_book_id = library_book_id;
		this.reserved_date = reserved_date;
		this.status = status;
	}
	
	public Reservation(String reservation_id, Timestamp reserved_date, String username, String book_name, String isbn, String status) {
	    this.reservation_id = reservation_id;
	    this.reserved_date = reserved_date;
	    this.username = username;
	    this.book_name = book_name;
	    this.isbn = isbn;
	    this.status = status;
	}
	
	public void setReservation_id(String reservation_id) {
		this.reservation_id = reservation_id;
	}
	public String getReservation_id() {
		return reservation_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setLibrary_book_id(String library_book_id) {
		this.library_book_id = library_book_id;
	}
	public String getLibrary_book_id() {
	    return library_book_id;
	}
	public void setReserved_date(Timestamp reserved_date) {
	    this.reserved_date = reserved_date;
	}
	public Timestamp getReserved_date() {
		return reserved_date;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatus() {
		return status;
	}
	public String getUsername() {
	    return username;
	}
	public void setUsername(String username) {
	    this.username = username;
	}
	public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }
    public String getIsbn() {
        return isbn;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

}
