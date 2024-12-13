package com.bookshelf.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookshelf.dao.BookDao;
import com.bookshelf.dao.LibraryBookDao;
import com.bookshelf.libs.UUIDGenerator;

@WebServlet("/AddBookInventory")
public class AddBookInventoryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AddBookInventoryServlet() {
        super();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        // Check if the user is logged in
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // Retrieve user role and library ID from session
        String userRole = (String) session.getAttribute("role");
        String libraryId = (String) session.getAttribute("libraryId");

        // Debugging logs
        System.out.println("AddBookInventoryServlet - User Role: " + userRole);
        System.out.println("AddBookInventoryServlet - Library ID: " + libraryId);

        // Gather book information from the form
        String title = request.getParameter("title");
        String author = request.getParameter("author");
        String isbn = request.getParameter("isbn");
        String year = request.getParameter("published_year");
        int publishedYear = Integer.parseInt(year);
        String genreString = request.getParameter("genre");
        int genre = Integer.parseInt(genreString);
        
        String path = "BookInventoryManager";
        boolean is_librarian = "librarian".equalsIgnoreCase(userRole);
        boolean is_admin = "admin".equalsIgnoreCase(userRole);
        
        if(is_librarian) {
        	path="LibBookInventoryManager";                
        }

        try {
            // Check if the book already exists
            boolean exist = BookDao.isBookExist(title, author, publishedYear);

            if (exist) {
                System.out.println("AddBookInventoryServlet - Book already exists: " + title);
                request.setAttribute("error", "Book already exists in inventory.");
                request.getRequestDispatcher(path).forward(request, response);
                return;
            }

            // Generate book ID and create a new book
            String bookId = UUIDGenerator.generateUUID();
            boolean result = BookDao.createBook(bookId, title, author, isbn, publishedYear, genre);

            if (result) {
                System.out.println("AddBookInventoryServlet - Book created successfully.");

                // If the user is a librarian, link the book to their library
                if (is_librarian) {
                	
                    if (libraryId == null || libraryId.isEmpty()) {
                        System.out.println("AddBookInventoryServlet - Failed: Library ID is missing for librarian.");
                        request.setAttribute("error", "Cannot add a book. Library ID is missing.");
     
                    }

                    boolean linkResult = LibraryBookDao.linkBookToLibrary(bookId, libraryId);
                    if (linkResult) {
                        System.out.println("AddBookInventoryServlet - Book linked to library successfully.");
                        request.setAttribute("message", "Book added and linked to your library successfully.");
                    } else {
                        System.out.println("AddBookInventoryServlet - Failed to link book to library.");
                        request.setAttribute("error", "Failed to link book to your library.");

                    }
                } else {
                    request.setAttribute("message", "Book added successfully.");

                }
            } else {
                System.out.println("AddBookInventoryServlet - Failed to add book.");

                
            }
        } catch (Exception e) {
            System.out.println("AddBookInventoryServlet - Exception occurred.");
            e.printStackTrace();
            request.setAttribute("error", "An error occurred. Please try again later.");
        }


        response.sendRedirect(path);
    }
}
