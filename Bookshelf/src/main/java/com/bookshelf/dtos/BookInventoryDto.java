package com.bookshelf.dtos;

import com.bookshelf.beans.Book;

public class BookInventoryDto {
	
	private Book book;
	private String genre_name;
	private int num_location;
	
	public BookInventoryDto(Book book, String genre_name, int num_location) {
		
		this.book = book;
		this.genre_name = genre_name;
		this.num_location = num_location;
	}
	
	public void setBook(Book book) {
		this.book = book;
	}
	public Book getBook(){
		return book;
	}
	public void setGenre_name(String genre_name) {
		this.genre_name = genre_name;
	}
	public String getGenre_name() {
		return genre_name;
	}
	public void setNum_location(int num_location) {
		this.num_location = num_location;
	}
	public int getNum_location() {
		return num_location;
	}


}
