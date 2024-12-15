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
                    <option value="NL" ${address.city == 'NL' ? 'selected' : ''} >NL</option>
                    <option value="PE" ${address.city == 'PE' ? 'selected' : ''}>PE</option>
                    <option value="NS" ${address.city == 'NS' ? 'selected' : ''}>NS</option>
                    <option value="NB" ${address.city == 'NB' ? 'selected' : ''}>NB</option>
                    <option value="QC" ${address.city == 'QC' ? 'selected' : ''}>QC</option>
                    <option value="ON" ${address.city == 'ON' ? 'selected' : ''}>ON</option>
                    <option value="MB" ${address.city == 'MB' ? 'selected' : ''}>MB</option>
                    <option value="SK" ${address.city == 'SK' ? 'selected' : ''}>SK</option>   
                    <option value="AB" ${address.city == 'AB' ? 'selected' : ''}>AB</option>
                    <option value="BC" ${address.city == 'BC' ? 'selected' : ''}>BC</option>
                    <option value="YT" ${address.city == 'YT' ? 'selected' : ''}>YT</option>
                    <option value="NT" ${address.city == 'NT' ? 'selected' : ''}>NT</option>
                    <option value="NU" ${address.city == 'NU' ? 'selected' : ''}>NU</option>
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
