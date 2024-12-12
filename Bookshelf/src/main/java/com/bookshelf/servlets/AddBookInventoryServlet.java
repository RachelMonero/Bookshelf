package com.bookshelf.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookshelf.dao.BookDao;
import com.bookshelf.libs.UUIDGenerator;


@WebServlet("/AddBookInventory")
public class AddBookInventoryServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public AddBookInventoryServlet() {
        super();
    
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    // get new book info to create book.
		
		String title = request.getParameter("title");
		String author = request.getParameter("author");
		String isbn = request.getParameter("isbn");
		String year = request.getParameter("published_year");
		int published_year = Integer.parseInt(year);
		String genre_string = request.getParameter("genre");
		int genre = Integer.parseInt(genre_string);
		
		// check if book exists
		boolean exist = BookDao.isBookExist(title, author, published_year);
		
		if(exist) {
			request.setAttribute("error", "Book already exists in inventory");
		    request.getRequestDispatcher("BookInventoryManager").forward(request, response);
		    return;
		}
		// generate book_id and create a new book
		String book_id = UUIDGenerator.generateUUID();
		boolean result = BookDao.createBook(book_id, title, author, isbn, published_year, genre);
			
		if (result) {
			request.setAttribute("message", "Book has been add successfully.");
		}else {
	    	     request.setAttribute("error", "An error occurred. Please try again later.");
	    }
		response.sendRedirect("BookInventoryManager");
   } 
    
}