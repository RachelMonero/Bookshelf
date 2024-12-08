package com.bookshelf.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookshelf.beans.Book;
import com.bookshelf.beans.LibraryBook;
import com.bookshelf.beans.Reservation;
import com.bookshelf.beans.User;
import com.bookshelf.dao.BookDao;
import com.bookshelf.dao.LibraryBookDao;
import com.bookshelf.dao.LibraryDao;
import com.bookshelf.dao.ReservationDao;
import com.bookshelf.dao.UserDao;
import com.bookshelf.dtos.ReservationListDto;


@WebServlet("/PullReservationList")
public class PullReservationListServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public PullReservationListServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		 HttpSession session = request.getSession(false);
	     if (session == null || session.getAttribute("user") == null) {
	         response.sendRedirect("index.jsp");
	         return;
	        }
	     
	     List<ReservationListDto> rvListDtos = new ArrayList<>();
	    
	     try {
	    	 // get reservation list
	    	 List<Reservation> allReservations = ReservationDao.getAllReservation();
	    	 
	    	 if (allReservations != null) {
	    		 System.out.println("[PullReservationServlet] found all reservations! ");
	    		 
	    		 
	    		 for (Reservation reservation: allReservations) {
	    			//get user 
	    			String current_user_id = reservation.getUser_id();
	    			User user = UserDao.getUserById(current_user_id);
	    			
	    			String current_lib_book_id = reservation.getLibrary_book_id();
	    			//get libraryBook
	    			LibraryBook libraryBook = LibraryBookDao.getLibBookById(current_lib_book_id);
	    			
	    			//get library_id and find library name.
	    			String library_id = libraryBook.getLibrary_id();
	    			String library_name =  LibraryDao.getLibraryNameById(library_id);
	    			System.out.println(library_name);
	    			
	    			//get Book
	    			String current_book_id = libraryBook.getBook_id();
	    			Book book = BookDao.getBookById(current_book_id);

	    			 
	    			ReservationListDto rvListDto= new ReservationListDto(reservation, user,book,library_name);
	    			rvListDtos.add(rvListDto);
	    			 
	    		 }
	    		 
	    		 request.setAttribute("rvListDtos", rvListDtos);
	    		 	    		 
	    		 
	    	 } 
	    	 
	    	 
	    	 
	     }catch(Exception e) {
	            e.printStackTrace();
	            request.setAttribute("error", "An error occurred. Please try again later.");
	     }
	     
	     request.getRequestDispatcher("adminReservation.jsp").forward(request, response);
	}


}
