<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.bookshelf.dtos.BookDto" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bookshelf | Dashboard</title>
    <link rel="stylesheet" href="css/dashboard.css">

</head>
<body class="background">

    <!-- Navbar -->
    <header class="navbar">
        <h1 class="navbar-title">Bookshelf</h1>
        
        <!-- Embedded Nav_bar -->
        <%@include file="admin_navBar.jsp" %>
 
    </header>

 
        <h2>User Manager</h2>
        <c:if test="${not empty error}">
        <p class="error">${error}</p>
        </c:if>

         <!-- manage button -->
         <tbody>
			<div class="user-card-container">
			    <c:forEach var="userManagerDto" items="${userManagerDtos}">
			        <div class="user-card">
			            <h3>User Information</h3>
			            <p><strong>Username:</strong> ${userManagerDto.user.username}</p>
			            <p><strong>Email:</strong> ${userManagerDto.user.email}</p>
			            <p><strong>Name:</strong> ${userManagerDto.user.first_name} ${userManagerDto.user.last_name}</p>
			            <p><strong>Address:</strong> ${userManagerDto.address.address}, ${userManagerDto.address.city}, 
			               ${userManagerDto.address.province}, ${userManagerDto.address.country}, ${userManagerDto.address.postal_code}
			            </p>
			            <p><strong>Role:</strong> ${userManagerDto.userRole.role_name}</p>
			            <p><strong>Status:</strong> ${userManagerDto.userRole.status}</p>
			            <p><strong>Reservations:</strong> ${userManagerDto.totalReservation}</p>
			
			            <form action="UserManager" method="POST">
			                <button type="submit" name="edit" value="${userManagerDto.user.user_id}">EDIT</button>
			                <button type="submit" name="delete" value="${userManagerDto.user.user_id}" class="btn-delete">DELETE</button>
			            </form>
			        </div>
			    </c:forEach>
			</div>
         </tbody>

    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2024 Bookshelf Application</p>
        <p>Created by the Bookshelf Development Team</p>
    </footer>

</body>
</html>