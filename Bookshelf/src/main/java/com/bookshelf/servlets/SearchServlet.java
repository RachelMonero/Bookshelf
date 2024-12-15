package com.bookshelf.servlets;

import com.bookshelf.dao.BookDao;
import com.bookshelf.dtos.BookDto;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/searchBooks")
public class SearchServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Validate user session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        String userRole = (String) session.getAttribute("role");
        System.out.println("search_userRole:"+userRole);
        boolean is_librarian = "librarian".equalsIgnoreCase(userRole);
        boolean is_admin = "sysAdmin".equalsIgnoreCase(userRole);
        String path = "dashboard.jsp";

        // Retrieve search parameters
        String title = request.getParameter("title") != null ? request.getParameter("title").trim() : null;
        String author = request.getParameter("author") != null ? request.getParameter("author").trim() : null;
        String genre = request.getParameter("genre") != null ? request.getParameter("genre").trim() : "All Genres";
        String availability = request.getParameter("availability");

        // By default, only show available books
        if (availability == null || availability.isEmpty() || "Any".equalsIgnoreCase(availability)) {
            availability = "1"; 
        }

        // Log parameters
        System.out.println("Raw Parameters Received:");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Genre: " + genre);
        System.out.println("Availability: " + availability);

        try {
            // Interact with the DAO to retrieve books
            BookDao bookDao = new BookDao();
            List<BookDto> books = bookDao.searchBooks(title, author, genre, availability);

            // Log the number of books retrieved
            System.out.println("Books retrieved: " + (books != null ? books.size() : "null"));

            // Set books as a request attribute
            request.setAttribute("books", books);

            // set path
            if(is_librarian) {
            	path="libDashboard.jsp";
            } else if(is_admin) {
            	path="adminDashboard.jsp";
            	
            } 
            request.getRequestDispatcher(path).forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
        }
        
        
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
