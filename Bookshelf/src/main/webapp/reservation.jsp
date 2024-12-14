<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="com.bookshelf.beans.ReservationsHistory" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Reservation History</title>
    <link rel="stylesheet" href="css/dashboard.css">
    <script>
        // Confirmation pop-up for canceling a reservation
        function confirmCancellation(reservationId) {
            if (confirm("Are you sure you want to cancel this reservation?")) {
                window.location.href = "CancelReservationServlet?reservationId=" + reservationId;
            }
        }
    </script>
</head>
<body class="background">

    <!-- Navbar -->
    <header class="navbar">
        <h1 class="navbar-title">Bookshelf</h1>
        <!-- Embedded Nav_bar -->
        <%@include file="mem_navBar.jsp" %>
    </header>

    <div class="dashboard-container">
        <h1>Your Reservation History</h1>

        <!-- If no reservations are found -->
        <%
            // Retrieve the reservations list from the request
            List<ReservationsHistory> reservations = (List<ReservationsHistory>) request.getAttribute("reservations");

            if (reservations == null || reservations.isEmpty()) {
        %>
            <p>No reservations found.</p>
        <% 
            } else { 
        %>

        <!-- Display reservations in card layout -->
        <div class="card-container">
            <% for (ReservationsHistory reservation : reservations) { %>
                <div class="book-card">
                    <h3><strong><%= reservation.getBookTitle() %></strong></h3> <!-- Book Title in Bold -->
                    <p><strong>Reservation ID:</strong> <%= reservation.getReservationId() %></p>
                    <p><strong>Library Name:</strong> <%= reservation.getLibraryName() %></p>
                    <p><strong>Status:</strong> <%= reservation.getStatus() %></p>
                    <p><strong>Reserved Date:</strong> <%= reservation.getReservedDate() %></p>
                    <div style="margin-top: 10px;">
                        <!-- Cancel button -->
                        <button class="btn-edit" onclick="confirmCancellation('<%= reservation.getReservationId() %>')">Cancel Reservation</button>
                    </div>
                </div>
            <% } %>
        </div>
        <% } %>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2024 Bookshelf Application</p>
        <p>Created by the Bookshelf Development Team</p>
    </footer>
</body>
</html>
