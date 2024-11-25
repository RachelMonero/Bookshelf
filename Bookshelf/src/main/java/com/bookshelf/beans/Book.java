package com.bookshelf.beans;

public class Book {
    private String id;
    private String title;
    private String author;
    private String isbn;
    private int publishedYear;
    private String genre;
    private String availability;

    public Book(String id, String title, String author, String isbn, int publishedYear, String genre, String availability) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.publishedYear = publishedYear;
        this.genre = genre;
        this.availability = availability;
    }

    // Getters and Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
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
}
