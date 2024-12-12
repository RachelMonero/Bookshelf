<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.bookshelf.beans.User" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bookshelf | Profile</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body class="background">

    <!-- Navbar -->
    <header class="navbar">
        <h1 class="navbar-title">Bookshelf</h1>
        
        <!-- Embedded Nav_bar -->
        <%@include file="mem_navBar.jsp" %>
        
    </header>

    <!-- Main Content -->
    <main class="dashboard-container">
        <div class="card">
            <h2>Your Profile</h2>
            <% 
                // Retrieve the user object 
                User user = (User) request.getAttribute("user");
                String success = (String) request.getAttribute("success");
                String error = (String) request.getAttribute("error");
            %>

            <!-- Feedback Messages -->
            <% if (success != null) { %>
                <p class="success-message"><%= success %></p>
            <% } %>
            <% if (error != null) { %>
                <p class="error-message"><%= error %></p>
            <% } %>

            <% if (user != null) { %>
            <form action="updateProfile" method="post" class="profile-form">
                <div class="form-group">
                    <label for="username">Username:</label>
                    <span id="display-username" class="display-text"><%= user.getUsername() %></span>
                    <input type="text" id="username" name="username" value="<%= user.getUsername() %>" class="form-control" readonly>
                    <button type="button" class="btn-edit" onclick="enableEdit('username', 'display-username')">Edit</button>
                </div>
                <div class="form-group">
                    <label for="email">Email:</label>
                    <span id="display-email" class="display-text"><%= user.getEmail() %></span>
                    <input type="email" id="email" name="email" value="<%= user.getEmail() %>" class="form-control" readonly>
                </div>
                <div class="form-group">
                    <label for="firstName">First Name:</label>
                    <span id="display-firstName" class="display-text"><%= user.getFirst_name() %></span>
                    <input type="text" id="firstName" name="firstName" value="<%= user.getFirst_name() %>" class="form-control" readonly>
                </div>
                <div class="form-group">
                    <label for="lastName">Last Name:</label>
                    <span id="display-lastName" class="display-text"><%= user.getLast_name() %></span>
                    <input type="text" id="lastName" name="lastName" value="<%= user.getLast_name() %>" class="form-control" readonly>
                </div>
                <div class="form-group">
                    <label for="password">Password:</label>
                    <span id="display-password" class="display-text">********</span>
                    <input type="password" id="password" name="password" placeholder="Enter new password" class="form-control" readonly>
                    <button type="button" class="btn-edit" onclick="enableEdit('password', 'display-password')">Edit</button>
                </div>
                <button type="submit" class="btn-update">Update Profile</button>
            </form>
            <% } else { %>
                <p class="error-message">User not found or not logged in.</p>
            <% } %>
        </div>
    </main>

    <!-- Footer -->
    <footer class="footer">
        <p>&copy; 2024 Bookshelf Application</p>
        <p>Created by the Bookshelf Development Team</p>
    </footer>

    <!-- JavaScript -->
    <script>
        // Function to toggle editing
        function enableEdit(fieldId, displayId) {
            const displayText = document.getElementById(displayId);
            const inputField = document.getElementById(fieldId);

            if (displayText && inputField) {
                displayText.style.display = 'none'; // Hide the text display
                inputField.style.display = 'inline-block'; // Show the input field
                inputField.removeAttribute('readonly'); // Enable editing
                inputField.focus(); 
            }
        }
    </script>
</body>
</html>
