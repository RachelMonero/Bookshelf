package com.bookshelf.beans;

import java.time.LocalDateTime;

public class ReservationsHistory {
    private String reservationId;
    private LocalDateTime reservedDate;
    private String status;
    private String libraryBookId;
    private String bookTitle;
    private String bookAuthor;
    private String genre;
    private String libraryName;

    // Constructor
    public ReservationsHistory(String reservationId, LocalDateTime reservedDate, String status, String libraryBookId,
                               String bookTitle, String bookAuthor, String genre, String libraryName) {
        this.reservationId = reservationId;
        this.reservedDate = reservedDate;
        this.status = status;
        this.libraryBookId = libraryBookId;
        this.bookTitle = bookTitle;
        this.bookAuthor = bookAuthor;
        this.genre = genre;
        this.libraryName = libraryName;
    }

    // Getters and Setters
    public String getReservationId() {
        return reservationId;
    }

    public void setReservationId(String reservationId) {
        this.reservationId = reservationId;
    }

    public LocalDateTime getReservedDate() {
        return reservedDate;
    }

    public void setReservedDate(LocalDateTime reservedDate) {
        this.reservedDate = reservedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLibraryBookId() {
        return libraryBookId;
    }

    public void setLibraryBookId(String libraryBookId) {
        this.libraryBookId = libraryBookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookAuthor() {
        return bookAuthor;
    }

    public void setBookAuthor(String bookAuthor) {
        this.bookAuthor = bookAuthor;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLibraryName() {
        return libraryName;
    }

    public void setLibraryName(String libraryName) {
        this.libraryName = libraryName;
    }

    @Override
    public String toString() {
        return "ReservationsHistory{" +
                "reservationId='" + reservationId + '\'' +
                ", reservedDate=" + reservedDate +
                ", status='" + status + '\'' +
                ", libraryBookId='" + libraryBookId + '\'' +
                ", bookTitle='" + bookTitle + '\'' +
                ", bookAuthor='" + bookAuthor + '\'' +
                ", genre='" + genre + '\'' +
                ", libraryName='" + libraryName + '\'' +
                '}';
    }
}
