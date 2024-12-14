package com.bookshelf.dtos;

public class LibrarianCandidateDto {

	private String librarian_id;
	private String librarian_username;
	
	
	public LibrarianCandidateDto(String librarian_id,String librarian_username) {
		this.librarian_id = librarian_id;
		this.librarian_username = librarian_username;
	}
	
	public String getLibrarian_id() {
		return librarian_id;
	}
	public void setLibarian_id(String librarian_id) {
		this.librarian_id = librarian_id;
	}
	
	public String getLibrarian_username() {
		return librarian_username;
	}
	public void setLibarian_username(String librarian_username) {
		this.librarian_username = librarian_username;
	}
	
}
