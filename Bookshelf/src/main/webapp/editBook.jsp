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

         <nav>
            <a href="adminDashboard.jsp" class="nav-link">Home</a>
            <a href="UserManager" class="nav-link">Users</a>
            <a href="books.jsp" class="nav-link">Libraries</a>
            <a href="PullReservationList" class="nav-link">Reservations</a>
            <a href="BookInventoryManager" class="nav-link">Books</a>
            <a href="index.jsp" class="nav-link">Logout</a>
         </nav>

    </header>

         <c:if test="${not empty book}">

            <h2>Book Information</h2>

                <form action="editBook" method="post">

                 <p>
                   <label for="book_id">Book ID</label>
                   <input id="book_id" type="text" name="book_id" value="${book.book_id}" readonly>
                 </p>
                 <p>
                  <label for="title">Title*</label>
                  <input id="title" type="text" name="title" value="${book.title}" >
                 </p>
                 <p>
                  <label for="author">Author*</label>
                  <input id="author" type="text" name="author" value="${book.author}" >
                 </p>
                 <p>
                  <label for="isbn">ISBN*</label>
                  <input id="isbn" type="text" name="isbn" value="${book.isbn}" >
                 </p>
                 <p>
                  <label for="published_year">Published*</label>
                  <input id="published_year" type="text" name="published_year" value="${book.publishedYear}" >
                 </p>
                 <p>
                 <label for="genre">Genre*</label>
                   <select name="genre" id="genre" class="filter-select">
                          <option value="" disabled ${empty book.genre ? 'selected' : ''}>Select a genre</option>
                          <option value="1" ${book.genre == '1' ? 'selected' : ''}>Adventure</option>
                          <option value="2" ${book.genre == '2' ? 'selected' : ''}>Cookbook</option>
                          <option value="3" ${book.genre == '3' ? 'selected' : ''}>Economics</option>
                          <option value="4" ${book.genre == '4' ? 'selected' : ''}>Fantasy</option>
                          <option value="5" ${book.genre == '5' ? 'selected' : ''}>Health</option>
                          <option value="6" ${book.genre == '6' ? 'selected' : ''}>History</option>
                          <option value="7" ${book.genre == '7' ? 'selected' : ''}>Horror</option>
                          <option value="15" ${book.genre == '15' ? 'selected' : ''}>Memoir</option>
                          <option value="8" ${book.genre == '8' ? 'selected' : ''}>Mystery</option>
                          <option value="9" ${book.genre == '9' ? 'selected' : ''}>Novel</option>
                          <option value="10" ${book.genre == '10' ? 'selected' : ''}>Romance</option>
                          <option value="11" ${book.genre == '11' ? 'selected' : ''}>Science</option>
                          <option value="12" ${book.genre == '12' ? 'selected' : ''}>Science Fiction</option>
                          <option value="13" ${book.genre == '13' ? 'selected' : ''}>Thriller</option>
                          <option value="14" ${book.genre == '14' ? 'selected' : ''}>Travel</option>
                 </select> 
                </p>
                               
                 <input id="book_id" type="hidden" name="book_id" value="${book.book_id}" />
                 <input id="pre_title" type="hidden" name="pre_title" value="${book.title}" />
                 <input id="pre_author" type="hidden" name="pre_author" value="${book.author}" />
                 <input id="pre_isbn" type="hidden" name="pre_isbn" value="${book.isbn}" />
                 <input id="pre_published_year" type="hidden" name="pre_published_year" value="${book.publishedYear}" />
                 <input id="pre_genre" type="hidden" name="pre_genre" value="${book.genre}" />
                 
                 <p>
                   <button type="submit">Save Changes</button>
                 </p>
                </form>

         </c:if>

             <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2024 Bookshelf Application</p>
        <p>Created by the Bookshelf Development Team</p>
    </footer>

</body>
</html>