<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.bookshelf.dtos.BookDto" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bookshelf | Admin Reservations</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body class="background">

    <!-- Navbar -->
    <header class="navbar">
        <h1 class="navbar-title">Bookshelf</h1>
        <!-- Embedded Nav_bar -->
        <%@include file="admin_navBar.jsp" %>
    </header></br>
    
    <h2>Reservations</h2>
    <div class="dashboard-container">        

        <!-- Display Errors -->
        <c:if test="${not empty error}">
            <p class="error">${error}</p>
        </c:if>

        <!-- Reservation Cards -->
        <c:if test="${not empty rvListDtos}">
            <div class="card-container">
                <c:forEach var="rvListDto" items="${rvListDtos}">
                    <div class="book-card">
                        <h3> ${rvListDto.book.title}</h3>
                        <p><strong>Reservation ID:</strong> ${rvListDto.reservation.reservation_id}</p>
                        <p><strong>Date:</strong> ${rvListDto.reservation.reserved_date}</p>
                        <p><strong>Status:</strong> ${rvListDto.reservation.status}</p>
                        <p><strong>Username:</strong> ${rvListDto.user.username}</p>
                        <p><strong>Author:</strong> ${rvListDto.book.author}</p>
                        <p><strong>ISBN:</strong> ${rvListDto.book.isbn}</p>
                        <p><strong>Library:</strong> ${rvListDto.library_name}</p>
                    </div>
                </c:forEach>
            </div>
        </c:if>

        <!-- If No Reservations Found -->
        <c:if test="${empty rvListDtos}">
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
