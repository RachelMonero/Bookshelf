<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bookshelf | Register</title>
    <link rel="stylesheet" href="css/register-style.css">
</head>
<body class="background">
    <div class="overlay">
        <div class="register-container">
            <h2>Register for Bookshelf</h2>

            <!-- Display error message if present -->
            <% String message = (String) request.getAttribute("message"); %>
            <% if (message != null && !message.isEmpty()) { %>
                <p class="error-message" style="color: red;"><%= message %></p>
            <% } %>

            <form class="register-form" method="POST" action="register">
            
                <label for="email">Email:</label>
                <input id="email" type="email" name="email" placeholder="email@example.com" pattern="\S+@\S+\.\S+" required value="<%= request.getParameter("email") != null ? request.getParameter("email") : "" %>" />

                <label for="username">Username:</label>
                <input id="username" type="text" name="username" placeholder="Username" required value="<%= request.getParameter("username") != null ? request.getParameter("username") : "" %>" />

                <label for="password">Password:</label>
                <input id="password" type="password" name="password" placeholder="Enter your password" required />

                <label for="first_name">First Name:</label>
                <input id="first_name" type="text" name="first_name" placeholder="First Name" required value="<%= request.getParameter("first_name") != null ? request.getParameter("first_name") : "" %>" />

                <label for="last_name">Last Name:</label>
                <input id="last_name" type="text" name="last_name" placeholder="Last Name" required value="<%= request.getParameter("last_name") != null ? request.getParameter("last_name") : "" %>" />

                <label for="address">Address:</label>
                <input id="address" type="text" name="address" placeholder="Address" required value="<%= request.getParameter("address") != null ? request.getParameter("address") : "" %>" />

                <input id="city" type="text" name="city" placeholder="City" required value="<%= request.getParameter("city") != null ? request.getParameter("city") : "" %>" />

                <label for="province">Province:</label>
                <select name="province" id="province" required>
                    <option value="" disabled selected>Province</option>
                    <option value="NL" <%= "NL".equals(request.getParameter("province")) ? "selected" : "" %>>NL</option>
                    <option value="PE" <%= "PE".equals(request.getParameter("province")) ? "selected" : "" %>>PE</option>
                    <option value="NS" <%= "NS".equals(request.getParameter("province")) ? "selected" : "" %>>NS</option>
                    <option value="NB" <%= "NB".equals(request.getParameter("province")) ? "selected" : "" %>>NB</option>
                    <option value="QC" <%= "QC".equals(request.getParameter("province")) ? "selected" : "" %>>QC</option>
                    <option value="ON" <%= "ON".equals(request.getParameter("province")) ? "selected" : "" %>>ON</option>
                    <option value="MB" <%= "MB".equals(request.getParameter("province")) ? "selected" : "" %>>MB</option>
                    <option value="SK" <%= "SK".equals(request.getParameter("province")) ? "selected" : "" %>>SK</option>   
                    <option value="AB" <%= "AB".equals(request.getParameter("province")) ? "selected" : "" %>>AB</option>
                    <option value="BC" <%= "BC".equals(request.getParameter("province")) ? "selected" : "" %>>BC</option>
                    <option value="YT" <%= "YT".equals(request.getParameter("province")) ? "selected" : "" %>>YT</option>
                    <option value="NT" <%= "NT".equals(request.getParameter("province")) ? "selected" : "" %>>NT</option>
                    <option value="NU" <%= "NU".equals(request.getParameter("province")) ? "selected" : "" %>>NU</option>
                </select>

                <label for="country">Country:</label>
                <select name="country" id="country" required>
                    <option value="CA" selected>Canada</option>
                </select>

                <label for="postal_code">Postal Code:</label>
                <input id="postal_code" type="text" name="postal_code" placeholder="Postal Code" required value="<%= request.getParameter("postal_code") != null ? request.getParameter("postal_code") : "" %>" />

                <input id="user_role" type="hidden" name="user_role" value="member" />
                <button type="submit" class="btn-register">Register</button>
            </form>

				<p class="login-prompt"><a href="index.jsp">Already have an account? Login Now</a></p>
        </div>
    </div>

    <footer class="footer">
        <p>Created by the Bookshelf Development Team</p>
        <p>&copy; 2024 Bookshelf Application</p>
    </footer>
</body>
</html>
