package com.bookshelf.beans;

import java.sql.Timestamp;

public class LibraryBook {
	private String library_book_id;
	private String library_id;
	private String book_id;
	private Timestamp added_date;
	private boolean is_available;
	
	public LibraryBook(String library_book_id, String library_id, String book_id, Timestamp added_date, boolean is_available) {
		this.library_book_id = library_book_id;
		this.library_id = library_id;
		this.book_id = book_id;
		this.added_date = added_date;
		this.is_available = is_available;
	}
	
	public void setLibrary_book_id(String library_book_id) {
		this.library_book_id = library_book_id;
	}
	public String getLibrary_book_id() {
		return library_book_id;
	}
	
	public void setLibrary_id(String library_id) {
		this.library_id = library_id;
	}
	public String getLibrary_id() {
		return library_id;
	}
	
	public void setBook_id(String book_id) {
		this.book_id = book_id;
	}
	public String getBook_id() {
		return book_id;
	}
	
	public void setAdded_date(Timestamp added_date) {
		this.added_date = added_date;
	}
	public Timestamp getAdded_date() {
		return added_date;
	}
	
	public void setIs_available(boolean is_available) {
		this.is_available = is_available;
	}
	public boolean getIs_available() {
		return is_available;
	}

}
