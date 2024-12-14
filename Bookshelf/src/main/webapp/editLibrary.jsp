<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Edit Library</title>
    <link rel="stylesheet" href="css/dashboard.css">
</head>
<body class="background">

    <header class="navbar">
        <h1 class="navbar-title">Edit Library</h1>
        <%@ include file="admin_navBar.jsp" %>
    </header>

    <div>
    	<c:if test="${not empty library && not empty address}">
			<h2>Library Information</h2>
		
		    <form action="EditLibraryServlet" method="post">
		        <p>
		            <label for="library_id">Library ID</label>
		            <input id="library_id" type="text" name="library_id" value="${library.library_id}" readonly>
		        </p>
		        <p>
		            <label for="library_name">Library Name*</label>
		            <input id="library_name" type="text" name="library_name" value="${library.library_name}" required>
		        </p>
		        <p>
		            <label for="library_email">Library Email*</label>
		            <input id="library_email" type="email" name="library_email" value="${library.library_email}" required>
		        </p>
		        <p>
		            <label for="library_phone">Library Phone*</label>
		            <input id="library_phone" type="text" name="library_phone" value="${library.library_phone}" required>
		        </p>
		        <h3>Library Address</h3>
		        <p>
		            <label for="address">Street Address*</label>
		            <input id="address" type="text" name="address" value="${address.address}" required>
		        </p>
		        <p>
		            <label for="city">City*</label>
		            <input id="city" type="text" name="city" value="${address.city}" required>
		        </p>
		        <p>
		            <label for="province">Province*</label>
		            <input id="province" type="text" name="province" value="${address.province}" required>
		        </p>
		        <p>
		            <label for="country">Country*</label>
		            <input id="country" type="text" name="country" value="${address.country}" required>
		        </p>
		        <p>
		            <label for="postal_code">Postal Code*</label>
		            <input id="postal_code" type="text" name="postal_code" value="${address.postal_code}" required>
		        </p>
		
		        <!-- Hidden fields to store previous values -->
		        <input type="hidden" name="pre_library_name" value="${library.library_name}">
		        <input type="hidden" name="pre_library_email" value="${library.library_email}">
		        <input type="hidden" name="pre_library_phone" value="${library.library_phone}">
		        <input type="hidden" name="pre_address_id" value="${address.address_id}">
		        <input type="hidden" name="pre_address" value="${address.address}">
		        <input type="hidden" name="pre_city" value="${address.city}">
		        <input type="hidden" name="pre_province" value="${address.province}">
		        <input type="hidden" name="pre_country" value="${address.country}">
		        <input type="hidden" name="pre_postal_code" value="${address.postal_code}">
		
		        <p>
		            <button type="submit">Save Changes</button>
		        </p>
		    </form>
		</c:if>

    </div>

    <footer class="footer">
        <p>&copy; 2024 Bookshelf Application</p>
        <p>Created by the Bookshelf Development Team</p>
    </footer>

</body>
</html>
