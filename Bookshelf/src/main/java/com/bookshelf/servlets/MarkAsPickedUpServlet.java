package com.bookshelf.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookshelf.dao.ReservationDao;

/**
 * Servlet to handle marking a reservation as "picked up".
 * This servlet is invoked by librarians when a reserved book is physically picked up by the user.
 */

@WebServlet("/MarkAsPickedUp")
public class MarkAsPickedUpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    /**
     * Handles POST requests to mark a reservation as "picked up".
     *
     * @param request  The HTTP request containing the reservation ID.
     * @param response The HTTP response indicating the success or failure of the operation.
     * @throws ServletException If a servlet-specific error occurs.
     * @throws IOException      If an I/O error occurs.
     */

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Retrieve the reservation ID from the request
        String reservationId = request.getParameter("reservationId");

        if (reservationId == null || reservationId.isEmpty()) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Invalid reservation ID.");
            return;
        }

        try {
            // Call the DAO to mark the reservation as picked up
            boolean isUpdated = ReservationDao.markAsPickedUp(reservationId);

            if (isUpdated) {
                // Redirect back to the librarian's reservations page
                response.sendRedirect("LibrarianReservations");
            } else {
                // Handle failure (e.g., invalid reservation ID)
                request.setAttribute("error", "Failed to update the reservation status.");
                request.getRequestDispatcher("librarianReservations.jsp").forward(request, response);
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "An error occurred.");
        }
    }
}
