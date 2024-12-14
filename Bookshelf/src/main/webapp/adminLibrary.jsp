<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html>
<html>
<head>
    <title>Admin Library Management</title>
</head>
<body>
    <h1>Admin Library Management</h1>

    <div>
        ${message}
    </div>

    <form action="AdminLibraryServlet" method="post">
        <h2>Add Library</h2>
        <label>Library Name: <input type="text" name="libraryName" required></label><br>
        <label>Library Email: <input type="email" name="libraryEmail" required></label><br>
        <label>Library Phone: <input type="text" name="libraryPhone" required></label><br>
        <h3>Address Details</h3>
        <label>Address: <input type="text" name="address" required></label><br>
        <label>City: <input type="text" name="city" required></label><br>
        <label>Province: <input type="text" name="province" required></label><br>
        <label>Country: <input type="text" name="country" required></label><br>
        <label>Postal Code: <input type="text" name="postalCode" required></label><br>
        <input type="hidden" name="action" value="add">
        <button type="submit">Add Library</button>
    </form>

    <h2>Existing Libraries</h2>
    <table border="1">
    <thead>
        <tr>
            <th>Library ID</th>
            <th>Name</th>
            <th>Address</th>
            <th>Email</th>
            <th>Phone</th>
            <th>Actions</th>
        </tr>
    </thead>
    <tbody>
        <c:forEach var="library" items="${libraries}">
            <tr>
                <td>${library.library_id}</td>
                <td>${library.library_name}</td>
                <td>${library.library_address_id}</td>
                <td>${library.library_email}</td>
                <td>${library.library_phone}</td>
                <td>
                    <form action="EditLibraryServlet" method="get" style="display:inline;">
                        <input type="hidden" name="libraryId" value="${library.library_id}">
                        <button type="submit">Edit</button>
                    </form>
                    <form action="AdminLibraryServlet" method="post" style="display:inline;">
                        <input type="hidden" name="action" value="delete">
                        <input type="hidden" name="libraryId" value="${library.library_id}">
                        <button type="submit">Delete</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
