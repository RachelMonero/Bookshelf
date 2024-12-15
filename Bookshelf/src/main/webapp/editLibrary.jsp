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
        <h1 class="navbar-title">Bookshelf</h1>
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
		            <label for="librarian_id">Librarian</label>
		            <input id="librarian_username" type="text" name="librarian_username" value="${librarian_username}" readonly>	
		            
		         <c:if test="${not empty libCandidates}">
		            <select name="new_librarian_id" id="new_librarian_id" class="filter-select">
                         <option value="" disabled selected>Select a librarian</option>	
                         <option value="" >None</option>		         
		                 <c:forEach var="candidate" items="${libCandidates}">
                                <option value="${candidate.librarian_id}">${candidate.librarian_username}</option>
                         </c:forEach>
		             </select>
		         </c:if>       	            
		            
		        </p>
		              
		        <p>
		            <label for="library_name">Library Name*</label>
		            <input id="library_name" type="text" name="library_name" value="${library.library_name}" required>
		        </p>
		        <p>
		            <label for="library_email">Library Email*</label>
		            <input id="library_email" type="email" name="library_email" value="${library.library_email}" >
		        </p>
		        <p>
		            <label for="library_phone">Library Phone*</label>
		            <input id="library_phone" type="text" name="library_phone" value="${library.library_phone}" >
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
		          
		            <select name="province" id="province" required>
                    <option value="" disabled selected>Province</option>${address.city == 'NL' ? 'selected' : ''}
                    <option value="NL"  ${address.province == 'NL' ? 'selected' : ''} >NL</option>
                    <option value="PE"  ${address.province == 'PE' ? 'selected' : ''}>PE</option>
                    <option value="NS"  ${address.province == 'NS' ? 'selected' : ''}>NS</option>
                    <option value="NB"  ${address.province == 'NB' ? 'selected' : ''}>NB</option>
                    <option value="QC"  ${address.province == 'QC' ? 'selected' : ''}>QC</option>
                    <option value="ON"  ${address.province == 'ON' ? 'selected' : ''}>ON</option>
                    <option value="MB"  ${address.province == 'MB' ? 'selected' : ''}>MB</option>
                    <option value="SK"  ${address.province == 'SK' ? 'selected' : ''}>SK</option>   
                    <option value="AB"  ${address.province == 'AB' ? 'selected' : ''}>AB</option>
                    <option value="BC"  ${address.province == 'BC' ? 'selected' : ''}>BC</option>
                    <option value="YT"  ${address.province == 'YT' ? 'selected' : ''}>YT</option>
                    <option value="NT"  ${address.province == 'NT' ? 'selected' : ''}>NT</option>
                    <option value="NU"  ${address.province == 'NU' ? 'selected' : ''}>NU</option>
                </select>
		        </p>
		        <p>
		            <label for="country">Country*</label>
		            <select name="country" id="country" required>
                        <option value="CA" selected>Canada</option>
                    </select>
		        </p>
		        <p>
		            <label for="postal_code">Postal Code*</label>
		            <input id="postal_code" type="text" name="postal_code" value="${address.postal_code}" required>
		        </p>
		
		        <!-- Hidden fields to store previous values -->
		        <input type="hidden" name="pre_library_name" value="${library.library_name}">
		        <input type="hidden" name="pre_library_email" value="${library.library_email}">
		        <input type="hidden" name="pre_library_phone" value="${library.library_phone}">
		        <input type="hidden" name="pre_librarian_id" value="${library.librarian_id}">
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
