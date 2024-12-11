<%@ page import="java.util.List" %>
<%@ page import="com.bookshelf.beans.ReservationsHistory" %>
<!DOCTYPE html>
<html>
<head>
    <title>Your Reservation History</title>
    <style>
        table {
            border-collapse: collapse;
            width: 100%;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: black;
            color: white;
        }
        button {
            padding: 5px 10px;
            background-color: red;
            color: white;
            border: none;
            cursor: pointer;
            border-radius: 4px;
        }
        button:hover {
            background-color: darkred;
        }
    </style>
    <script>
        // Confirmation pop-up for canceling a reservation
        function confirmCancellation(reservationId) {
            if (confirm("Are you sure you want to cancel this reservation?")) {
                window.location.href = "CancelReservationServlet?reservationId=" + reservationId;
            }
        }
    </script>
</head>
<body>
    <h1>Your Reservation History</h1>
    <%
        // Retrieve the reservations list from the request
        List<ReservationsHistory> reservations = (List<ReservationsHistory>) request.getAttribute("reservations");

        if (reservations == null || reservations.isEmpty()) {
    %>
        <p>No reservations found.</p>
    <% 
        } else { 
    %>
        <table>
            <tr>
                <th>Reservation ID</th>
                <th>Book Title</th>
                <th>Library Name</th>
                <th>Status</th>
                <th>Reserved Date</th>
                <th>Action</th>
            </tr>
            <% for (ReservationsHistory reservation : reservations) { %>
            <tr>
                <td><%= reservation.getReservationId() %></td>
                <td><%= reservation.getBookTitle() %></td>
                <td><%= reservation.getLibraryName() %></td>
                <td><%= reservation.getStatus() %></td>
                <td><%= reservation.getReservedDate() %></td>
                <td>
                    <!-- Cancel button -->
                    <button onclick="confirmCancellation('<%= reservation.getReservationId() %>')">Cancel Reservation</button>
                </td>
            </tr>
            <% } %>
        </table>
    <% } %>
</body>
</html>
