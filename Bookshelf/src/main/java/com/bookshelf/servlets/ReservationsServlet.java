package com.bookshelf.servlets;

import com.bookshelf.beans.ReservationsHistory;
import com.bookshelf.dao.ReservationsHistoryDao;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/ReservationsServlet")
public class ReservationsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Log the start of the servlet
        System.out.println("ReservationsServlet: Handling GET request.");

        // Retrieve logged-in user ID from the session
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("loggedInUserId") == null) {
            System.out.println("ReservationsServlet: No session or logged-in user found. Redirecting to login.");
            response.sendRedirect("index.jsp");
            return;
        }

        String userId = (String) session.getAttribute("loggedInUserId");
        System.out.println("ReservationsServlet: Logged-in User ID: " + userId);

        try {
            // Fetch reservations from the DAO
            ReservationsHistoryDao dao = new ReservationsHistoryDao();
            List<ReservationsHistory> reservations = dao.getReservationsByUserId(userId);

            // Log the number of reservations fetched (useful for debugging)
            System.out.println("ReservationsServlet: Fetched " + reservations.size() + " reservations.");

            // Set the reservations as a request attribute for JSP
            request.setAttribute("reservations", reservations);
        } catch (Exception e) {
            // Log any exception for debugging
            System.err.println("ReservationsServlet: Error fetching reservations: " + e.getMessage());
            e.printStackTrace();
        }

        // Forward to JSP
        RequestDispatcher dispatcher = request.getRequestDispatcher("reservation.jsp");
        dispatcher.forward(request, response);
    }
}
