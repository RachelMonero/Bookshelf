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

 
        <h2>Book Inventory</h2>
        <c:if test="${not empty error}">
        <p class="error">${error}</p>
        </c:if>
        
        
        <form class="add_book" method="POST" action="AddBookInventory">
         <div> 
          <div>
            <input id="title" type="text" placeholder="Please Enter Title Here" name="title" class="long-input"/>
          </div>  
          <div>
            <label for="author">Author:</label>
            <input id="author" type="text" name="author"/>
            <label for="published_year">Published:</label>
            <input id="published_year" type="text" placeholder="YYYY" name="published_year"/>
            <label for="isbn">ISBN:</label>
            <input id="isbn" type="text" name="isbn"/>
            <label for="genre">Genre:</label>
            <select name="genre" id="genre" class="filter-select">
                    <option value="" disabled selected>Select a genre</option>
                    <option value="1">Adventure</option>
                    <option value="2">Cookbook</option>
                    <option value="3">Economics</option>
                    <option value="4">Fantasy</option>
                    <option value="5">Health</option>
                    <option value="6">History</option>
                    <option value="7">Horror</option>
                    <option value="15">Memoir</option>
                    <option value="8">Mystery</option>
                    <option value="9">Novel</option>
                    <option value="10">Romance</option>
                    <option value="11">Science</option>
                    <option value="12">Science Fiction</option>
                    <option value="13">Thriller</option>
                    <option value="14">Travel</option>
           </select>
           <button type="submit" class="btn-search">Add</button>
          </div>
         </div> 

        </form>
        </br>
			<div class="dashboard-container">
			    <c:forEach var="bookInventoryDto" items="${bookInventoryDtos}">
			        <div class="book-card">
			            <h3>${bookInventoryDto.book.title}</h3>
			            <p><strong>Author:</strong> ${bookInventoryDto.book.author}</p>
			            <p><strong>Published:</strong> ${bookInventoryDto.book.publishedYear}</p>
			            <p><strong>ISBN:</strong> ${bookInventoryDto.book.isbn}</p>
			            <p><strong>Genre:</strong> ${bookInventoryDto.genre_name}</p>
			            <p><strong>Available Copies:</strong> ${bookInventoryDto.num_location}</p>
			            
			            <!-- Manage Buttons -->
			            <form action="BookInventoryManager" method="POST" style="text-align: center;">
			                <input type="hidden" name="num_of_use" value="${bookInventoryDto.num_location}" />
			                <button type="submit" name="edit" class="btn-edit" value="${bookInventoryDto.book.book_id}">EDIT</button>
			                <button type="submit" name="delete" class="btn-delete" value="${bookInventoryDto.book.book_id}">DELETE</button>
			            </form>
			        </div>
			    </c:forEach>
			</div>


    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2024 Bookshelf Application</p>
        <p>Created by the Bookshelf Development Team</p>
    </footer>

</body>
</html>