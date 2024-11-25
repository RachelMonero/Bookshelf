<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.List, com.bookshelf.dtos.BookDto" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bookshelf Search Results</title>
    <link rel="stylesheet" href="css/styles.css">
</head>
<body>
    <!-- Navbar -->
    <header class="navbar">
        <h1>Bookshelf Search Results</h1>
        <nav>
            <a href="dashboard.jsp" class="nav-link">Back to Dashboard</a>
        </nav>
    </header>

    <!-- Main Content -->
    <main class="results-container">
        <h2>Search Results</h2>
        <ul>
            <%
                List<BookDto> books = (List<BookDto>) request.getAttribute("books");
                if (books != null && !books.isEmpty()) {
                    for (BookDto book : books) {
            %>
                        <li class="book-item">
                            <h3><%= book.getTitle() %></h3>
                            <p><strong>Author:</strong> <%= book.getAuthor() %></p>
                            <p><strong>Genre:</strong> <%= book.getGenre() %></p>
                            <p><strong>ISBN:</strong> <%= book.getIsbn() %></p>
                            <p><strong>Publication Year:</strong> <%= book.getPublishedYear() %></p>
                            <p><strong>Location:</strong> <%= book.getLibrary_name() %></p>
                        </li>
            <%
                    }
                } else {
            %>
                    <p>No books found matching your search criteria.</p>
            <%
                }
            %>
        </ul>
    </main>

    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2024 Bookshelf Application</p>
        <p>Created by the Bookshelf Development Team</p>
    </footer>
</body>
</html>
