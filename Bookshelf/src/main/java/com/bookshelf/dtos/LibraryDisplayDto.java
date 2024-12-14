package com.bookshelf.dtos;

import com.bookshelf.beans.Library;

public class LibraryDisplayDto {
	private Library library;
	private String librarian_id;
	private String librarian_username;
	
	
	public LibraryDisplayDto(Library library,String librarian_username) {
		this.library = library;
		this.librarian_username = librarian_username;
	}
	
	public Library getLibrary() {
		return library;
	}
	public void setLibrary(Library library) {
		this.library = library;
	}
	public String getLibrarian_username() {
		return librarian_username;
	}
	public void setLibrarian_username(String librarian_username) {
		this.librarian_username = librarian_username;
	}

}
