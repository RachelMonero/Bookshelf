package com.bookshelf.servlets;

import com.bookshelf.beans.LibraryBook;
import com.bookshelf.beans.Reservation;
import com.bookshelf.beans.User;
import com.bookshelf.dao.LibraryBookDao;
import com.bookshelf.dao.LibraryDao;
import com.bookshelf.dao.ReservationDao;
import com.bookshelf.dao.UserDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet to handle librarian-specific reservation management.
 * This servlet retrieves and displays reservation details for the librarian's library.
 */

@WebServlet("/LibrarianReservations")
public class LibrarianReservationsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * Handles GET requests to fetch reservations for the librarian's library.
     * Ensures only authenticated librarians can access this functionality.
     */

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null || !session.getAttribute("role").equals("librarian")) {
            response.sendRedirect("index.jsp");
            return;
        }

        String libraryId = (String) session.getAttribute("libraryId"); // Retrieve libraryId from session
        if (libraryId == null) {
            request.setAttribute("error", "Library information not found.");
            request.getRequestDispatcher("error.jsp").forward(request, response);
            return;
        }

        try {
            // Fetch the library name
            String libraryName = LibraryDao.getLibraryNameById(libraryId);

            // Fetch reservations with details
            List<Reservation> reservations = ReservationDao.getReservationsWithDetails(libraryId);

            // Pass data to JSP
            request.setAttribute("library_name", libraryName); // Pass library name to JSP
            request.setAttribute("reservations", reservations); // Pass reservations list to JSP
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Failed to fetch reservations.");
        }

        request.getRequestDispatcher("librarianReservations.jsp").forward(request, response);
    }
}
