package com.bookshelf.dtos;

import com.bookshelf.beans.Book;
import com.bookshelf.beans.Reservation;
import com.bookshelf.beans.User;

public class ReservationListDto {
	
   private Reservation reservation;
   private User user;
   private Book book;
   private String library_name;
   
   public ReservationListDto(Reservation reservation, User user, Book book, String library_name) {
	   super();
	   this.reservation = reservation;
	   this.user = user;
	   this.book = book;
	   this.library_name = library_name;
   }
   
   public Reservation getReservation() {
	   return reservation;	  
   }
   public void setReservation(Reservation reservation) {
	   this.reservation = reservation;
   }
   public User getUser() {
	   return user;
   }
   public void setUser(User user) {
	   this.user = user;
   }
   public Book getBook() {
	   return book;
   }
   public void setBook(Book book) {
	   this.book = book;
   }
   public String getLibrary_name() {
	   return library_name;
   }
   public void setLibrary_name(String library_name) {
	   this.library_name =  library_name;
   }
   
   


}
