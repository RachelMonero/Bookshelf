package com.bookshelf.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookshelf.beans.Book;
import com.bookshelf.dao.BookDao;


@WebServlet("/editBook")
public class editBookServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public editBookServlet() {
        super();
 
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// compare input values with the previous values and save.
	
		String current_book_id = request.getParameter("book_id");

		
		String title = request.getParameter("pre_title");
		String author = request.getParameter("pre_author");
		String isbn = request.getParameter("pre_isbn");
		int published_year = Integer.parseInt(request.getParameter("pre_published_year"));
		int genre = Integer.parseInt(request.getParameter("pre_genre"));
		

		String edit_title = request.getParameter("title");
		String edit_author = request.getParameter("author");
		String edit_isbn = request.getParameter("isbn");
		int edit_published_year = Integer.parseInt(request.getParameter("published_year"));
		int edit_genre = Integer.parseInt(request.getParameter("genre"));
		
		// if any changes have detected, set it as a main value.
	    if (title != edit_title) {
	    	title =  edit_title;	    	
	    }
	    if (author != edit_author) {
	    	author = edit_author;
	    }
		if (isbn != edit_isbn) {
			isbn = edit_isbn;
		}
		if (published_year != edit_published_year) {
			published_year = edit_published_year;
		}
		if (genre != edit_genre) {
			genre = edit_genre;
		}
		
		// update the book record with new value.
	    boolean is_updated =  BookDao.updateBookById(current_book_id, title, author, isbn, published_year, genre);
			  
	    if (is_updated) {
				  request.setAttribute("message", "Book has been updated successfully.");
		}else {
			    	request.setAttribute("error", "An error occurred. Please try again later.");
		}
	    response.sendRedirect("BookInventoryManager");

	}

}
