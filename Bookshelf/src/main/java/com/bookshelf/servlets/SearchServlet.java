package com.bookshelf.servlets;

import com.bookshelf.dao.BookDao;
import com.bookshelf.beans.Book;
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
        HttpSession session = request.getSession(false); // Avoid creating a new session
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // Retrieve search parameters
        String title = request.getParameter("title") != null ? request.getParameter("title").trim() : null;
        String author = request.getParameter("author") != null ? request.getParameter("author").trim() : null;
        String genre = request.getParameter("genre") != null ? request.getParameter("genre").trim() : "All Genres";
        String availability = request.getParameter("availability") != null ? request.getParameter("availability").trim() : "Any";

        // Debug: Log parameters
        System.out.println("Search Parameters:");
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Genre: " + genre);
        System.out.println("Availability: " + availability);

        try {
            // Interact with the DAO to retrieve books
            BookDao bookDao = new BookDao();
            List<Book> books = bookDao.searchBooks(title, author, genre, availability);

            // Debug: Log retrieved results
            System.out.println("Books retrieved: " + (books != null ? books.size() : "null"));

            // Set books as a request attribute
            request.setAttribute("books", books);

            // Forward to JSP
            request.getRequestDispatcher("searchResults.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            // Redirect to an error page or display a user-friendly message
            response.sendRedirect("error.jsp");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Delegate POST to GET for simplicity
        doGet(request, response);
    }
}
