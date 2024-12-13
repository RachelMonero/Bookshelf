package com.bookshelf.servlets;

import com.bookshelf.beans.Book;
import com.bookshelf.dao.BookDao;
import com.bookshelf.dao.GenreDao;
import com.bookshelf.dao.LibraryBookDao;
import com.bookshelf.dao.LibraryDao;
import com.bookshelf.dao.ReservationDao;
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

@WebServlet("/LibBookInventoryManager")
public class LibBookInventoryManagerServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LibBookInventoryManagerServlet() {
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
        String library_name = LibraryDao.getLibraryNameById(libraryId);
        request.setAttribute("library_name", library_name);
        
        

        // Debugging: Log session attributes
        System.out.println("Session Attributes:");
        System.out.println("Logged In UserId = " + session.getAttribute("loggedInUserId"));
        System.out.println("User Role = " + userRole);
        System.out.println("Library ID = " + libraryId);

        List<BookInventoryDto> bookInventoryDtos = new ArrayList<>();

        try {
            List<Book> books;

            if ("librarian".equalsIgnoreCase(userRole)) {
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
                    boolean libBook_inUse = LibraryBookDao.is_libBookInRev(book_id, libraryId) ;

                    int num_location =libBook_inUse ? 1 : 0;
        

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
        if ("librarian".equalsIgnoreCase(userRole)) {
            request.getRequestDispatcher("librarianBookInventory.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	HttpSession session = request.getSession(false);
    	
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
       
        // for librarian
        String lib_checkInBookId = request.getParameter("lib_edit_in");
        System.out.println("lib_checkInBookId:"+lib_checkInBookId);
        String lib_checkOutBookId = request.getParameter("lib_edit_out");
        System.out.println("lib_checkOutBookId:"+lib_checkOutBookId);
        String lib_deleteBookId = request.getParameter("lib_delete");
        String library_id = (String) session.getAttribute("libraryId");
        // find library_book_id
    	String library_book_id = null;


        // Distinguish the button clicked by end user.
        if (lib_deleteBookId != null) {
        	library_book_id = LibraryBookDao.getLibBookId(lib_deleteBookId, library_id);
        	//check if book is in reservation or not
            int rev_num = ReservationDao.countReservationsByLibBookId(library_book_id);
            if(rev_num > 0) {
            	request.setAttribute("error", "Book is in use. Cannot delete the book.");
            	// throw message book is in use. deletion cannot proceed.
            } else {
            		
        	     boolean is_deleted = LibraryBookDao.deleteLibBookById(library_book_id);
        	     if(is_deleted) {
        	     System.out.println("LibraryBook deleted successfully. Book ID: " + library_book_id);
        	     }
            } 
        	
        } else if(lib_checkInBookId != null){
        	
        	library_book_id = LibraryBookDao.getLibBookId(lib_checkInBookId, library_id);
        	//change availability to true
        	LibraryBookDao.updateBookAvailability(library_book_id, 1);

        	
        }else if(lib_checkOutBookId != null){
        	library_book_id = LibraryBookDao.getLibBookId(lib_checkOutBookId, library_id);
        	LibraryBookDao.updateBookAvailability(library_book_id, 0);
        	//change availability to false
        	try {
        	LibraryBookDao.setAvailabilityFalse(library_book_id);

        	}catch(Exception e) {
        		System.out.println("change availability failed.");
        	}
        }
        response.sendRedirect("LibBookInventoryManager");
    }
}
