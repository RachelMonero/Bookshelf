package com.bookshelf.servlets;

import com.bookshelf.beans.User;
import com.bookshelf.connection.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Enumeration;

@WebServlet("/profile")
public class ProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("ProfileServlet: doGet method called.");
        System.out.println("ProfileServlet: Triggered by navigation.");

        HttpSession session = request.getSession(false); // Retrieve existing session
        if (session == null) {
            System.out.println("ProfileServlet: No session found.");
            request.setAttribute("error", "Session expired. Please log in again.");
            request.getRequestDispatcher("profilePage.jsp").forward(request, response);
            return;
        }

        // Log all session attributes
        System.out.println("ProfileServlet: Logging all session attributes:");
        Enumeration<String> attributeNames = session.getAttributeNames();
        while (attributeNames.hasMoreElements()) {
            String attributeName = attributeNames.nextElement();
            System.out.println("Session Attribute: " + attributeName + " = " + session.getAttribute(attributeName));
        }

        String userId = (String) session.getAttribute("loggedInUserId");
        System.out.println("ProfileServlet: loggedInUserId from session = " + userId);

        if (userId == null) {
            System.out.println("ProfileServlet: User not logged in.");
            request.setAttribute("error", "User not logged in.");
            request.getRequestDispatcher("profilePage.jsp").forward(request, response);
            return;
        }

        // Handle success or error messages from UpdateProfileServlet
        String successMessage = (String) session.getAttribute("success");
        String errorMessage = (String) session.getAttribute("error");
        session.removeAttribute("success");
        session.removeAttribute("error");
        request.setAttribute("success", successMessage);
        request.setAttribute("error", errorMessage);

        try (Connection conn = DBConnection.getDBInstance()) {
            if (conn == null) {
                System.out.println("ProfileServlet: Database connection is null.");
                request.setAttribute("error", "Database connection failed.");
                request.getRequestDispatcher("profilePage.jsp").forward(request, response);
                return;
            }

            // Query to fetch user details
            String sql = "SELECT user_id, username, email, password, first_name, last_name, address_id, is_verified " +
                         "FROM bookshelf_user WHERE user_id = ?";
            System.out.println("ProfileServlet: Executing query for userId = " + userId);

            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, userId);

            System.out.println("ProfileServlet: Executing query...");
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                System.out.println("ProfileServlet: Data found in ResultSet for userId = " + userId);

                // Log all ResultSet fields
                System.out.println("ProfileServlet: username = " + rs.getString("username"));
                System.out.println("ProfileServlet: email = " + rs.getString("email"));
                System.out.println("ProfileServlet: first_name = " + rs.getString("first_name"));
                System.out.println("ProfileServlet: last_name = " + rs.getString("last_name"));

                // Create User object
                User user = new User(
                    rs.getString("user_id"),
                    rs.getString("username"),
                    rs.getString("email"),
                    rs.getString("password"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("address_id"),
                    rs.getBoolean("is_verified")
                );

                // Log the User object
                System.out.println("ProfileServlet: User object created successfully: " + user.getUsername());

                // Set user attribute in request 
                request.setAttribute("user", user);
                System.out.println("ProfileServlet: user attribute set in request: " + request.getAttribute("user"));
            } else {
                System.out.println("ProfileServlet: No data found for userId = " + userId);
                request.setAttribute("error", "User not found.");
            }
        } catch (Exception e) {
            System.out.println("ProfileServlet: Exception while fetching user data.");
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while fetching your profile data.");
        }

     
        System.out.println("ProfileServlet: Forwarding to profilePage.jsp with user = " + request.getAttribute("user"));
        request.getRequestDispatcher("profilePage.jsp").forward(request, response);
    }
}
