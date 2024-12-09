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
import com.bookshelf.dao.BookDao;
import com.bookshelf.dao.GenreDao;
import com.bookshelf.dao.LibraryBookDao;
import com.bookshelf.dtos.BookInventoryDto;


@WebServlet("/BookInventoryManager")
public class BookInventoryManagerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public BookInventoryManagerServlet() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
	     if (session == null || session.getAttribute("user") == null) {
	         response.sendRedirect("index.jsp");
	         return;
	     }
	     
	     List<BookInventoryDto> bookInventoryDtos =  new ArrayList<>();
	     
	     try {
	    	 
	    	 //get all books
	    	 List<Book> allBooks = BookDao.getAllBook();
	    	 
	    	 if(allBooks != null) {
	    	  System.out.println("[BookInventroy]: found all books");	 
	    	  
	    	  for(Book book: allBooks) {
	    		  
	    		  String book_id = book.getBook_id();
	    		  String genre_id = book.getGenre();
	    		  String genre_name = GenreDao.findGenreNameById(genre_id);
	    		  
	    		  int num_location = LibraryBookDao.countLibByBookId(book_id);
	    		  
	    		  BookInventoryDto bookInventroyDto = new BookInventoryDto(book, genre_name, num_location);
	    		  bookInventoryDtos.add(bookInventroyDto);
	    		  
	    	  }
	    	  request.setAttribute("bookInventoryDtos", bookInventoryDtos);
	    	 
	    	 }
	    	 }catch(Exception e) {
		            e.printStackTrace();
		            request.setAttribute("error", "An error occurred. Please try again later.");
		     }
		     
		     request.getRequestDispatcher("adminBookInventory.jsp").forward(request, response);
		}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String editBook_id = request.getParameter("edit");
        String deleteBook_id = request.getParameter("delete");
        String num = request.getParameter("num_of_use");
        int num_of_use =  Integer.parseInt(num);
        System.out.println("No:"+num_of_use);
        

        if (editBook_id != null) {
        	 
        	// redirect to edit page.
        	Book book = BookDao.getBookById(editBook_id);
        	request.setAttribute("book", book);
        	
        	request.getRequestDispatcher("editBook.jsp").forward(request, response);
        	
        	
        } else if (deleteBook_id != null) {   		
        		
          try {       	
        	   if(num_of_use > 0) {
        		request.setAttribute("error", "Failed to delete the book. Book is in use by other libraries.");
        		System.out.println("Can't delete:"+num_of_use);
        	   } else {
        		
        		       boolean is_deleted = BookDao.deleteBookById(deleteBook_id);
        		
           		       if(is_deleted) {
        	    	      request.setAttribute("message", "Book has been deleted successfully.");
        	    	      
        		       } else {
        	    	        request.setAttribute("error", "An error occurred. Please try again later.");
        	           }		
        	   }        	        	             		
        	} catch(Exception e) {
	                e.printStackTrace();
	                request.setAttribute("error", "An error occurred. Please try again later.");
	            
	        } response.sendRedirect("BookInventoryManager");
        }


	
	}
}
