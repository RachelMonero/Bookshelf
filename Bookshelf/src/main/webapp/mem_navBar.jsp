<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
        <nav>
            <a href="dashboard.jsp" class="nav-link">Home</a>
            <a href="profile" class="nav-link">Profile</a>
            <a href="<%= request.getContextPath() %>/ReservationsServlet" class="nav-link">Reservations</a>
            <a href="index.jsp" class="nav-link">Logout</a>
        </nav>