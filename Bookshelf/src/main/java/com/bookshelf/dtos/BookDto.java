package com.bookshelf.dtos;



public class BookDto {

    private String library_book_id;
    private String title;
    private String author;
    private String isbn;
    private int publishedYear;
    private String genre;
    private String availability;
    private String library_name;
    
    public BookDto(String library_book_id, String title, String author, String isbn, int publishedYear, String genre, String availability, String library_name) {
        
    	this.library_book_id = library_book_id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.genre = genre;
        this.availability = availability;
        this.library_name = library_name;
    }
    // Getters and Setters
    public String getLibrary_Book_id() { return library_book_id; }
    public void setLibrary_Book_id(String library_book_id) { this.library_book_id = library_book_id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }
    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }
    public int getPublishedYear() { return publishedYear; }
    public void setPublishedYear(int publishedYear) { this.publishedYear = publishedYear; }
    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    public String getAvailability() {return availability;}
    public void setAvailability(String availability) {this.availability = availability;}
    public String getLibrary_name() {return library_name;}
    public void setLibrary_name(String library_name) {this.library_name = library_name;}

    
}
