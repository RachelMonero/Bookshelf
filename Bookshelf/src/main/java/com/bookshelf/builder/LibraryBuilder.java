package com.bookshelf.builder;

import com.bookshelf.beans.Library;

public class LibraryBuilder {
    private String library_id;
    private String library_name;
    private String library_address_id;
    private String library_email;
    private String library_phone;
    private String librarian_id;
    
    public LibraryBuilder(String library_id, String library_name, String library_address_id) {
    	this.librarian_id = librarian_id;
    	this.library_name = library_name;
    	this.library_address_id =library_address_id;
    }

    public LibraryBuilder library_email(String library_email) {
        this.library_email = library_email;
        return this;
    }

    public LibraryBuilder library_phone(String library_phone) {
        this.library_phone = library_phone;
        return this;
    }

    public LibraryBuilder librarian_id(String librarian_id) {
        this.librarian_id = librarian_id;
        return this;
    }

    public Library build() {
        return new Library(library_id, library_name, library_address_id, library_email, library_phone, librarian_id);
        
    }
}
