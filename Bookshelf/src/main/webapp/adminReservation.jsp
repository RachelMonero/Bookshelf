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

 
        <h2>Reservation</h2>
        <c:if test="${not empty error}">
        <p class="error">${error}</p>
        </c:if>

         <table >
         <!-- manage button -->
         
           <thead>
             <tr>

                <th>Reservation ID</th>
                <th>Date</th>
                <th>Status</th>
                <th>Username</th>
                <th>Book</th>
                <th>Author</th>
                <th>ISBN</th>
                <th>Library</th>
                <th></th>

             </tr>
           </thead>
         <tbody>
            <c:forEach var="rvListDto" items="${rvListDtos}">
                <tr>

                    <td>${rvListDto.reservation.reservation_id}</td>
                    <td>${rvListDto.reservation.reserved_date}</td>
                    <td>${rvListDto.reservation.status}</td>
                    <td>${rvListDto.user.username}</td>
                    <td>${rvListDto.book.title}</td>
                    <td>${rvListDto.book.author}</td>
                    <td>${rvListDto.book.isbn}</td>                    
                    <td>${rvListDto.library_name}</td>
   
                </tr>

  
            </c:forEach>
         </tbody>
         </form >
       </table>
 

    

    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2024 Bookshelf Application</p>
        <p>Created by the Bookshelf Development Team</p>
    </footer>

</body>
</html>