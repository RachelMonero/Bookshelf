package com.bookshelf.servlets;

import com.bookshelf.connection.DBConnection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

@WebServlet("/updateProfile")
public class UpdateProfileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userId = (String) request.getSession().getAttribute("loggedInUserId");
        if (userId == null) {
            request.getSession().setAttribute("error", "User not logged in.");
            response.sendRedirect("profile");
            return;
        }

        // Retrieve updated profile details from the form
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String firstName = request.getParameter("firstName");
        String lastName = request.getParameter("lastName");
        String newPassword = request.getParameter("password"); // Input from user
        String currentPassword = null;

        try (Connection conn = DBConnection.getDBInstance()) {
            // Check if the new username is already taken by another user
            String checkUsernameSql = "SELECT user_id FROM bookshelf_user WHERE username = ? AND user_id != ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkUsernameSql)) {
                checkStmt.setString(1, username);
                checkStmt.setString(2, userId);

                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next()) {
                        // Username is already taken, set error attribute and redirect back to profile servlet
                        request.getSession().setAttribute("error", "Username is already taken. Please choose another.");
                        response.sendRedirect("profile");
                        return;
                    }
                }
            }

            // Fetch the current password if a new password is not provided
            if (newPassword == null || newPassword.trim().isEmpty()) {
                String fetchPasswordSql = "SELECT password FROM bookshelf_user WHERE user_id = ?";
                try (PreparedStatement fetchStmt = conn.prepareStatement(fetchPasswordSql)) {
                    fetchStmt.setString(1, userId);

                    try (ResultSet rs = fetchStmt.executeQuery()) {
                        if (rs.next()) {
                            currentPassword = rs.getString("password");
                        }
                    }
                }
            } else {
                currentPassword = newPassword;
            }

            // SQL query to update the user's profile
            String updateSql = "UPDATE bookshelf_user SET username = ?, email = ?, first_name = ?, last_name = ?, password = ? WHERE user_id = ?";
            try (PreparedStatement updateStmt = conn.prepareStatement(updateSql)) {
                // Set the parameters
                updateStmt.setString(1, username);
                updateStmt.setString(2, email);
                updateStmt.setString(3, firstName);
                updateStmt.setString(4, lastName);
                updateStmt.setString(5, currentPassword);
                updateStmt.setString(6, userId);

                int rowsUpdated = updateStmt.executeUpdate();

                if (rowsUpdated > 0) {
                    // Successfully updated
                    request.getSession().setAttribute("loggedInUser", username); // Update session username changed
                    request.getSession().setAttribute("success", "Profile updated successfully.");
                } else {
                    // Failed to update
                    request.getSession().setAttribute("error", "Failed to update profile.");
                }
                response.sendRedirect("profile");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.getSession().setAttribute("error", "An error occurred while updating your profile.");
            response.sendRedirect("profile");
        }
    }
}
