<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.bookshelf.beans.Reservation" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bookshelf | Librarian Reservations</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body class="background">

    <!-- Navbar -->
    <header class="navbar">
        <h1 class="navbar-title">Bookshelf</h1>
        <%@include file="lib_navBar.jsp" %> <!-- Embedded Nav_bar -->
    </header>

    <div class="dashboard-container">
        <h2>${library_name}'s Reservations</h2>

        <!-- Display Errors -->
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <!-- Reservations List -->
        <c:if test="${not empty reservations}">
            <div class="card-container">
                <c:forEach var="reservation" items="${reservations}">
                    <div class="book-card">
                        <h3>Reservation Details</h3>
                        <p><strong>Reservation ID:</strong> ${reservation.reservation_id}</p>
                        <p><strong>Date:</strong> ${reservation.reserved_date}</p>
                        <p><strong>Username:</strong> ${reservation.username}</p>
                        <p><strong>Book Name:</strong> ${reservation.book_name}</p>
                        <p><strong>ISBN:</strong> ${reservation.isbn}</p>
                        <p><strong>Status:</strong> ${reservation.status}</p>
                        <c:if test="${reservation.status == 'reserved'}">
                            <form action="MarkAsPickedUp" method="POST" style="margin-top: 10px;">
                                <input type="hidden" name="reservationId" value="${reservation.reservation_id}" />
                                <button type="submit" class="btn btn-edit">Pick Up</button>
                            </form>
                        </c:if>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <!-- If No Reservations Found -->
        <c:if test="${empty reservations}">
            <p>No reservations found.</p>
        </c:if>
    </div>

    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2024 Bookshelf Application</p>
        <p>Created by the Bookshelf Development Team</p>
    </footer>
</body>
</html>
