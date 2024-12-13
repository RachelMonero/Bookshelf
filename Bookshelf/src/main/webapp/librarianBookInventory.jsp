<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.bookshelf.dtos.BookDto" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bookshelf | Librarian Dashboard</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body class="background">

    <!-- Navbar -->
    <header class="navbar">
        <h1 class="navbar-title">Bookshelf</h1>
        <nav>
            <a href="libDashboard.jsp" class="nav-link">Home</a>
            <a href="BookInventoryManager" class="nav-link">Books</a>
            <a href="reservation" class="nav-link">Reservations</a>
            <a href="index.jsp" class="nav-link">Logout</a>
        </nav>
    </header>

    <h2>Your Library's Book Inventory</h2>
    <c:if test="${not empty error}">
        <p class="error">${error}</p>
    </c:if>

    <!-- Add Book Form -->
    <form class="add_book" method="POST" action="AddBookInventory">
        <div>
            <div>
                <input id="title" type="text" placeholder="Enter Book Title" name="title" required />
            </div>
            <div>
                <label for="author">Author:</label>
                <input id="author" type="text" name="author" required />
                <label for="published_year">Published Year:</label>
                <input id="published_year" type="number" placeholder="YYYY" name="published_year" required />
                <label for="isbn">ISBN:</label>
                <input id="isbn" type="text" name="isbn" required />
                <label for="genre">Genre:</label>
                <select name="genre" id="genre" class="filter-select" required>
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
                <button type="submit" class="btn-search">Add Book</button>
            </div>
        </div>
    </form>

    <br />

    <!-- Book Inventory Table -->
    <table>
        <thead>
            <tr>
                <th>Book ID</th>
                <th>Title</th>
                <th>Author</th>
                <th>Published Year</th>
                <th>ISBN</th>
                <th>Genre</th>
                <th>Availability</th>
                <th>Actions</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="bookInventoryDto" items="${bookInventoryDtos}">
                <tr>
                    <td>${bookInventoryDto.book.book_id}</td>
                    <td>${bookInventoryDto.book.title}</td>
                    <td>${bookInventoryDto.book.author}</td>
                    <td>${bookInventoryDto.book.publishedYear}</td>
                    <td>${bookInventoryDto.book.isbn}</td>
                    <td>${bookInventoryDto.genre_name}</td>
                    <td>${bookInventoryDto.num_location > 0 ? 'Available' : 'Not Available'}</td>
                    <td>
                        <form action="BookInventoryManager" method="POST" style="display:inline;">
                            <input type="hidden" name="num_of_use" value="${bookInventoryDto.num_location}" />
                            <button type="submit" name="edit" value="${bookInventoryDto.book.book_id}">Edit</button>
                            <button type="submit" name="delete" value="${bookInventoryDto.book.book_id}">Delete</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2024 Bookshelf Application</p>
        <p>Created by the Bookshelf Development Team</p>
    </footer>
</body>
</html>
