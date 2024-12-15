<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Library Management</title>
    <link rel="stylesheet" href="css/dashboard.css">

<body>
    <!-- Navbar -->
    <header class="navbar">
        <h1 class="navbar-title">Bookshelf</h1>
        
        <!-- Embedded Nav_bar -->
        <%@include file="admin_navBar.jsp" %>
        
    </header>
    
    <h1>Library Management</h1>

    <div>
        ${message}
    </div>
    <div>
      <form action="AdminLibraryServlet" method="post">
        <h2>Add Library</h2>
        <label>Library Name: <input type="text" name="libraryName" required></label><br>
        <label>Library Email: <input type="email" name="libraryEmail" required></label><br>
        <label>Library Phone: <input type="text" name="libraryPhone" required></label><br>
        <h3>Address Details</h3>
        <label>Address: <input type="text" name="address" required></label><br>
        <label>City: <input type="text" name="city" required></label><br>
        <label>Province:                 
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
                </select></label><br>
        <label>Country: 
                <select name="country" id="country" required>
                    <option value="CA" selected>Canada</option>
                </select>
        </label><br>
        <label>Postal Code: <input type="text" name="postalCode" required></label><br>
        <input type="hidden" name="action" value="add">
        <button type="submit">Add Library</button>
      </form>
    </div>

    <h2>Existing Libraries</h2>
    <table border="1">
    <thead>
        <tr>
            <th>Library ID</th>
            <th>Name</th>
            <th>Address</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Librarian</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="library" items="${libraries}">
            <tr>
                <td>${library.library.library_id}</td>
                <td>${library.library.library_name}</td>
                <td>${library.library.library_address_id}</td>
                <td>${library.library.library_email}</td>
                <td>${library.library.library_phone}</td>
                <td>${library.librarian_username}</td>                
                <td>
                    <form action="EditLibraryServlet" method="get" style="display:inline;">
                        <input type="hidden" name="libraryId" value="${library.library.library_id}">
                        <button type="submit">Edit</button>
                    </form>
                    <form action="AdminLibraryServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="libraryId" value="${library.library.library_id}">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
