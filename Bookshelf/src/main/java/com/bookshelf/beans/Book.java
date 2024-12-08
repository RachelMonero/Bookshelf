package com.bookshelf.beans;

public class Book {
    private String book_id;
    private String title;
    private String author;
    private String isbn;
    private int publishedYear;
    private String genre;
   // private boolean availability;

    public Book(String book_id, String title, String author, String isbn, int publishedYear, String genre) {
        this.book_id = book_id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.genre = genre;

    }

    // Getters and Setters
    public String getBook_id() { return book_id; }
    public void setBook_id(String book_id) { this.book_id = book_id; }
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
   // public boolean getAvailability() {return availability;}
   // public void setAvailability(boolean availability) {this.availability = availability;}
}
