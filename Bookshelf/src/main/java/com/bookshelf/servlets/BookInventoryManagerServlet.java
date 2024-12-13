package com.bookshelf.servlets;

import com.bookshelf.beans.Book;
import com.bookshelf.dao.BookDao;
import com.bookshelf.dao.GenreDao;
import com.bookshelf.dao.LibraryBookDao;
import com.bookshelf.dtos.BookInventoryDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/BookInventoryManager")
public class BookInventoryManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public BookInventoryManagerServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);

        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }

        // Retrieve user role and library ID from session
        String userRole = (String) session.getAttribute("role");
        String libraryId = (String) session.getAttribute("libraryId");

        // Debugging: Log session attributes
        System.out.println("Session Attributes:");
        System.out.println("Logged In UserId = " + session.getAttribute("loggedInUserId"));
        System.out.println("User Role = " + userRole);
        System.out.println("Library ID = " + libraryId);

        List<BookInventoryDto> bookInventoryDtos = new ArrayList<>();

        try {
            List<Book> books;

            if ("sysAdmin".equalsIgnoreCase(userRole)) {
                // System Administrator: Retrieve all books
                books = BookDao.getAllBook();
                System.out.println("Role identified as sysAdmin. Fetching all books.");
            } else if ("librarian".equalsIgnoreCase(userRole)) {
                // Librarian: Retrieve books associated with their library
                if (libraryId == null) {
                    throw new ServletException("Library ID is missing for librarian.");
                }
                books = LibraryBookDao.getBooksByLibraryId(libraryId);
                System.out.println("Role identified as librarian. Fetching books for library ID: " + libraryId);
            } else {
                System.out.println("Invalid user role: " + userRole);
                throw new ServletException("Invalid user role.");
            }

            if (books != null) {
                System.out.println("[BookInventory]: Found books for role " + userRole);

                for (Book book : books) {
                    String book_id = book.getBook_id();
                    String genre_id = book.getGenre();
                    String genre_name = GenreDao.findGenreNameById(genre_id);

                    int num_location = "librarian".equalsIgnoreCase(userRole) 
                        ? LibraryBookDao.countBooksInLibrary(book_id, libraryId) 
                        : LibraryBookDao.countLibByBookId(book_id);

                    BookInventoryDto bookInventoryDto = new BookInventoryDto(book, genre_name, num_location);
                    bookInventoryDtos.add(bookInventoryDto);
                }

                request.setAttribute("bookInventoryDtos", bookInventoryDtos);
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while fetching books. Please try again later.");
        }

        // Forward to the appropriate JSP
        if ("sysAdmin".equalsIgnoreCase(userRole)) {
            request.getRequestDispatcher("adminBookInventory.jsp").forward(request, response);
        } else if ("librarian".equalsIgnoreCase(userRole)) {
            request.getRequestDispatcher("librarianBookInventory.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String editBookId = request.getParameter("edit");
        String deleteBookId = request.getParameter("delete");
        String numOfUse = request.getParameter("num_of_use");

        // Debugging
        System.out.println("Edit Book ID: " + editBookId);
        System.out.println("Delete Book ID: " + deleteBookId);
        System.out.println("Number of Use: " + numOfUse);

        // Distinguish the button clicked by end user.
        if (editBookId != null) {
            // Redirect to editBook page.
            try {
                Book book = BookDao.getBookById(editBookId);
                if (book != null) {
                    request.setAttribute("book", book);
                    request.getRequestDispatcher("editBook.jsp").forward(request, response);
                    System.out.println("Redirecting to editBook.jsp for Book ID: " + editBookId);
                } else {
                    request.setAttribute("error", "Book not found for editing.");
                    System.out.println("Book not found for editing. Book ID: " + editBookId);
                    response.sendRedirect("BookInventoryManager");
                }
            } catch (Exception e) {
                System.out.println("Error occurred while processing edit request for Book ID: " + editBookId);
                e.printStackTrace();
                request.setAttribute("error", "An error occurred while editing the book.");
                response.sendRedirect("BookInventoryManager");
            }
        } else if (deleteBookId != null) {
            try {
                int numOfUseInt = Integer.parseInt(numOfUse);
                if (numOfUseInt > 0) {
                    // If the book is in use, return an error message
                    request.setAttribute("error", "Cannot delete the book. It is in use by other libraries.");
                    System.out.println("Book is in use. Cannot delete Book ID: " + deleteBookId);
                } else {
                    // Attempt to delete the book
                    boolean isDeleted = BookDao.deleteBookById(deleteBookId);
                    if (isDeleted) {
                        request.setAttribute("message", "Book deleted successfully.");
                        System.out.println("Book deleted successfully. Book ID: " + deleteBookId);
                    } else {
                        request.setAttribute("error", "An error occurred while deleting the book.");
                        System.out.println("Failed to delete the book. Book ID: " + deleteBookId);
                    }
                }
            } catch (Exception e) {
                System.out.println("Error occurred while processing delete request for Book ID: " + deleteBookId);
                e.printStackTrace();
                request.setAttribute("error", "An error occurred. Please try again later.");
            }
            response.sendRedirect("BookInventoryManager");
        }
    }
}
