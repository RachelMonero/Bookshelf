<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Bookshelf</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
    <div class="background">
        <div class="overlay">
            <div class="container">
                <div class="welcome-section">
                    <h1>Welcome to Bookshelf</h1>
                    <p>Discover and organize your literary journey.</p>
                    <h4 class="employee-info">New Employee? Contact <a href="mailto:example@algonquin.com" style="color: white; text-decoration: underline;">example@algonquin.com</a> to get registered</h4>
                </div>
                <div class="form-section">
                    <h2>Sign In</h2>
                    <form action="login" method="POST">
                        <%
                            String userEmail = "";
                            String userPassword = "";

                            // Retrieve cookies
                            if (request.getCookies() != null) {
                                for (Cookie cookie : request.getCookies()) {
                                    if ("userEmail".equals(cookie.getName())) {
                                        userEmail = cookie.getValue();
                                    }
                                    if ("userPassword".equals(cookie.getName())) {
                                        userPassword = cookie.getValue();
                                    }
                                }
                            }
                        %>
                        <label for="email">Email</label>
                        <input type="email" id="email" name="email" value="<%= userEmail %>" required>
                        <label for="password">Password</label>
                        <input type="password" id="password" name="password" value="<%= userPassword %>" required>
                        <div class="form-options">
                            <label><input type="checkbox" name="remember" <% if (!userEmail.isEmpty()) { %>checked<% } %>> Remember me</label>
                            <a href="forgotPassword.jsp">Forgot password?</a>
                        </div>
                        <button type="submit" class="btn-login">Sign In</button>
                        <p class="signup-prompt">Don't have an account? <a href="register">Sign up</a></p>
                    </form>
                </div>
            </div>
        </div>
    </div>
</body>
</html>
