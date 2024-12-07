<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.bookshelf.dtos.BookDto" %>
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
        
        <!-- Embedded Navigation Bar -->
        <%@ include file="navbar_admin.jsp" %>
 
    </header>

    <!-- Search Bar and Filter Section -->
    <section class="search-section">
        <h2>Search for Books</h2>
        <form action="searchBooks" method="get" class="search-form">
            <input type="text" name="title" placeholder="Search for books..." class="search-input">
            <div class="filters">
                <label for="genre">Genre:</label>
                <select name="genre" id="genre" class="filter-select">
                    <option value="All Genres">All Genres</option>
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
                <label for="author">Author:</label>
                <select name="author" id="author" class="filter-select">
                    <option value="">All Authors</option>
                    <option value="Yuval Noah Harari">Yuval Noah Harari</option>
                    <option value="Tochi Onyebuchi">Tochi Onyebuchi</option>
                    <option value="Thomas Piketty">Thomas Piketty</option>
                    <option value="Stieg Larsson">Stieg Larsson</option>
                    <option value="Stephen King">Stephen King</option>
                    <option value="Stephen Hawking">Stephen Hawking</option>
                    <option value="Sara Shepard">Sara Shepard</option>
                    <option value="Samin Nosrat">Samin Nosrat</option>
                    <option value="Rutger Bregman">Rutger Bregman</option>
                    <option value="Patrick Bringley">Patrick Bringley</option>
                    <option value="Nicholas Sparks">Nicholas Sparks</option>
                    <option value="Kim Stanley Robinson">Kim Stanley Robinson</option>
                    <option value="Jon Krakauer">Jon Krakauer</option>
                    <option value="Jojo Moyes">Jojo Moyes</option>
                    <option value="J.K. Rowling">J.K. Rowling</option>
                    <option value="Harper Lee">Harper Lee</option>
                    <option value="Gillian Flynn">Gillian Flynn</option>
                    <option value="George R.R. Martin">George R.R. Martin</option>
                    <option value="David Graeber and David Wengrow">David Graeber and David Wengrow</option>
                    <option value="Colleen Hoover">Colleen Hoover</option>
                    <option value="Christopher Paolini">Christopher Paolini</option>
                    <option value="Andy Weir">Andy Weir</option>
                    <option value="Agatha Christie">Agatha Christie</option>
                </select>
         
            </div>
            <button type="submit" class="btn-search">Search</button>
        </form>
    </section>

    <!-- Main Content -->
    <main class="dashboard-container">
        <h2>Your Dashboard</h2>
        
          <% String message = (String) request.getAttribute("message"); %>

          <% if (message != null) { %>
               <p class="message">
                 <%= message %>
               </p>
          <% } %>

        <!-- Display Search Results -->
		<div class="search-results">
		    <%
		        List<BookDto> books = (List<BookDto>) request.getAttribute("books");
		        if (books == null ) { 
		        	if (message == null){
		    %>
		        <!-- No search has been performed yet -->
		        <p>Use the search bar above to find books.</p>
		    <%
		        	}
		        } else if (books.isEmpty()) {
		    %>
		        <!-- Search performed but no results found -->
		        <p>No books found matching your search criteria.</p>
		    <%
		        } else {
		            for (BookDto book : books) {
		    %>
		                <div class="book-card">
		                    <h3><%= book.getTitle() %></h3>
		                    <p><strong>Author:</strong> <%= book.getAuthor() %></p>
		                    <p><strong>Genre:</strong> <%= book.getGenre() %></p>
		                    <p><strong>ISBN:</strong> <%= book.getIsbn() %></p>
		                    <p><strong>Publication Year:</strong> <%= book.getPublishedYear() %></p>
		                    <p><strong>Location:</strong> <%= book.getLibrary_name() %></p>
		
		                    <form action="reserveBook" method="post">
		                        <input type="hidden" name="library_book_id" value="<%= book.getLibrary_Book_id() %>">
		                        <button type="submit" class="btn-reserve">Reserve</button>
		                    </form>
		                </div>
		    <%
		            }
		        }
		    %>
		</div>

    </main>

    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2024 Bookshelf Application</p>
        <p>Created by the Bookshelf Development Team</p>
    </footer>

</body>
</html>
