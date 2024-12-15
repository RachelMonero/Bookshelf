<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Library Management</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body>
    <!-- Navbar -->
    <header class="navbar">
        <h1 class="navbar-title">Bookshelf</h1>
        <%@include file="admin_navBar.jsp" %>
    </header>

    <!-- Main Heading -->
    <h2 class="library-heading">Library Management</h2>

    <!-- Add Library Card -->
    <div class="add-library-card">
        <h3 class="center-content">Add Library</h3>
        <form action="AdminLibraryServlet" method="post" class="add-library-form">
            <!-- Library Name -->
            <div class="form-group">
                <label for="libraryName">Library Name:</label>
                <input type="text" id="libraryName" name="libraryName" required>
            </div>
            <!-- Library Email -->
            <div class="form-group">
                <label for="libraryEmail">Library Email:</label>
                <input type="email" id="libraryEmail" name="libraryEmail" required>
            </div>
            <!-- Library Phone -->
            <div class="form-group">
                <label for="libraryPhone">Library Phone:</label>
                <input type="text" id="libraryPhone" name="libraryPhone" required>
            </div>
            <!-- Address -->
            <div class="form-group">
                <label for="address">Address:</label>
                <input type="text" id="address" name="address" required>
            </div>
            <!-- City -->
            <div class="form-group">
                <label for="city">City:</label>
                <input type="text" id="city" name="city" required>
            </div>
            <!-- Province -->
            <div class="form-group">
                <label for="province">Province:</label>
                <select name="province" id="province" required>
                    <option value="" disabled selected>Select Province</option>
                    <option value="AB">AB</option>
                    <option value="BC">BC</option>
                    <option value="MB">MB</option>
                    <option value="NB">NB</option>
                    <option value="NL">NL</option>
                    <option value="NS">NS</option>
                    <option value="NT">NT</option>
                    <option value="NU">NU</option>
                    <option value="ON">ON</option>
                    <option value="PE">PE</option>
                    <option value="QC">QC</option>
                    <option value="SK">SK</option>
                    <option value="YT">YT</option>
                </select>
            </div>
            <!-- Country -->
            <div class="form-group">
                <label for="country">Country:</label>
                <select name="country" id="country" required>
                    <option value="CA" selected>Canada</option>
                </select>
            </div>
            <!-- Postal Code -->
            <div class="form-group">
                <label for="postalCode">Postal Code:</label>
                <input type="text" id="postalCode" name="postalCode" required>
            </div>
            <!-- Submit Button -->
            <input type="hidden" name="action" value="add">
            <button type="submit">Add Library</button>
        </form>
    </div>

    <!-- Existing Libraries Section -->
    <h3 class="library-heading">Existing Libraries</h3>
    <div class="library-card-container">
        <c:forEach var="library" items="${libraries}">
            <div class="library-card">
                <h3>${library.library.library_name}</h3>
                <p><strong>Library ID:</strong> ${library.library.library_id}</p>
                <p><strong>Address:</strong> ${library.library.library_address_id}</p>
                <p><strong>Email:</strong> ${library.library.library_email}</p>
                <p><strong>Phone:</strong> ${library.library.library_phone}</p>
                <p><strong>Librarian:</strong> ${library.librarian_username}</p>
                <form action="EditLibraryServlet" method="get" style="display:inline;">
                    <input type="hidden" name="libraryId" value="${library.library.library_id}">
                    <button type="submit" class="btn-edit">Edit</button>
                </form>
                <form action="AdminLibraryServlet" method="post" style="display:inline;">
                    <input type="hidden" name="action" value="delete">
                    <input type="hidden" name="libraryId" value="${library.library.library_id}">
                    <button type="submit" class="btn-delete">Delete</button>
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
