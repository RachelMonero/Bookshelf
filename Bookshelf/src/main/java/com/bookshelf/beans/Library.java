package com.bookshelf.beans;

import com.bookshelf.builder.LibraryBuilder;

public class Library {
	private String library_id;
	private String library_name;
	private String library_address_id;
	private String library_email;
	private String library_phone;
	private String librarian_id;
	
	public Library(String library_id, String library_name, String library_address_id, String library_email, String library_phone, String librarian_id) {
        this.library_id = library_id;
        this.library_name = library_name;
        this.library_address_id = library_address_id;
        this.library_email = library_email;
        this.library_phone = library_phone;
        this.librarian_id = librarian_id;
    }

	public Library(String library_id, String library_name, String library_address_id, String library_email, String library_phone) {
        this.library_id = library_id;
        this.library_name = library_name;
        this.library_address_id = library_address_id;
        this.library_email = library_email;
        this.library_phone = library_phone;
	}
	
    public String getLibrary_id() {
        return library_id;
    }

    public String getLibrary_name() {
        return library_name;
    }

    public String getLibrary_address_id() {
        return library_address_id;
    }

    public String getLibrary_email() {
        return library_email;
    }

    public String getLibrary_phone() {
        return library_phone;
    }

    public String getLibrarian_id() {
        return librarian_id;
    }


}
