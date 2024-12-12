package com.bookshelf.servlets;

import com.bookshelf.dao.LibraryBookDao;
import com.bookshelf.dao.ReservationsHistoryDao;
import com.bookshelf.observers.AdminNotificationObserver;
import com.bookshelf.observers.LibrarianNotificationObserver;
import com.bookshelf.observers.LibraryBookNotifier;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet to handle reservation cancellations.
 * Integrates the Observer pattern to notify observers (e.g., admin, librarian)
 * when a reservation is canceled.
 */
@WebServlet("/CancelReservationServlet")
public class CancelReservationServlet extends HttpServlet {

    // Notifier to manage and notify observers for reservation-related events
    private final LibraryBookNotifier libraryBookNotifier = new LibraryBookNotifier();

    /**
     * Initialize the servlet and register observers.
     */
    @Override
    public void init() throws ServletException {
        super.init();
        // Register observers for notifications
        libraryBookNotifier.addObserver(new AdminNotificationObserver());
        libraryBookNotifier.addObserver(new LibrarianNotificationObserver());
    }

    /**
     * Handles HTTP GET requests to cancel a reservation.
     *
     * @param request  The HTTP request.
     * @param response The HTTP response.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve the reservation ID from the request parameters
        String reservationId = request.getParameter("reservationId");

        // Get the logged-in user ID from the session
        HttpSession session = request.getSession(false);
        String userId = (String) session.getAttribute("loggedInUserId");

        // Validate the request parameters and session
        if (reservationId == null || reservationId.isEmpty() || userId == null) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid request: Missing reservation ID or user not logged in.");
            return;
        }

        try {
            ReservationsHistoryDao reservationsDao = new ReservationsHistoryDao();
            LibraryBookDao libraryBookDao = new LibraryBookDao();

            // Fetch the associated library book ID for the reservation
            String libraryBookId = reservationsDao.getLibraryBookIdByReservationId(reservationId);

            if (libraryBookId != null) {
                // Delete the reservation from the database
                reservationsDao.deleteReservation(reservationId);

                // Update the book's availability in the library database
                libraryBookDao.updateBookAvailability(libraryBookId, 1); // Mark book as available

                // Notify registered observers about the cancellation event
                libraryBookNotifier.notifyObservers(reservationId, userId, libraryBookId);
            }

            // Redirect the user back to the reservations page
            response.sendRedirect("ReservationsServlet");
        } catch (Exception e) {
            e.printStackTrace();
            // Respond with an error message in case of failure
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred while canceling the reservation.");
        }
    }
}
