<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
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
            <a href="dashboard.jsp" class="nav-link">Home</a>
            <a href="profile.jsp" class="nav-link">Profile</a>
            <a href="books.jsp" class="nav-link">Reservations</a>
            <a href="index.jsp" class="nav-link">Logout</a>
        </nav>
    </header>

    <!-- Search Bar and Filter Section -->
    <section class="search-section">
        <h2>Search for Books</h2>
        <form action="search" method="get" class="search-form">
        
            <input type="text" name="query" placeholder="Search for books..." class="search-input">

            <!-- Filter Options -->
            <div class="filters">
                <label for="genre">Genre:</label>
                <select name="genre" id="genre" class="filter-select">
                    <option value="">All Genres</option>
                    <option value="fiction">Fiction</option>
                    <option value="nonfiction">Nonfiction</option>
                    <!-- More genres tbd-->
                </select>

                <label for="author">Author:</label>
                <input type="text" name="author" id="author" placeholder="Author's name" class="filter-input">

                <label for="availability">Availability:</label>
                <select name="availability" id="availability" class="filter-select">
                    <option value="">Any</option>
                    <option value="yes">Yes</option>
                    <option value="no">No</option>
                </select>
            </div>

            <!-- Search Button -->
            <button type="submit" class="btn-search">Search</button>
        </form>
    </section>

    <!-- Main Content -->
    <main class="dashboard-container">
        <h2>Your Dashboard</h2>
        <p class="welcome-message">Hello! Welcome to your Bookshelf dashboard.</p>
        <p>Here’s what you can do:</p>
        <ul class="actions-list">
            <li><a href="profile.jsp">View and edit your profile</a></li>
            <li><a href="reservations.jsp">Browse and manage your book reservations</a></li>
        </ul>
    </main>

    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2024 Bookshelf Application</p>
        <p>Created by the Bookshelf Development Team</p>
    </footer>

</body>
</html>
