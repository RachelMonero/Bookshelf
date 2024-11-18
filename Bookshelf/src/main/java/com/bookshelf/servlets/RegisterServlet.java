package com.bookshelf.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookshelf.beans.User;
import com.bookshelf.dao.AddressDao;
import com.bookshelf.dao.RoleDao;
import com.bookshelf.dao.UserDao;
import com.bookshelf.dao.UserRoleDao;
import com.bookshelf.libs.TokenGenerator;
import com.bookshelf.services.JavaEmailService;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public RegisterServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        // User input for User instance
        String email = request.getParameter("email").toLowerCase();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String first_name = request.getParameter("first_name");
        String last_name = request.getParameter("last_name");

        // User input for Address instance
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String province = request.getParameter("province");
        String country = request.getParameter("country");
        String postal_code = request.getParameter("postal_code");

        // User role
        String user_role = request.getParameter("user_role");
        System.out.print("User Role: " + user_role);

        try {
            // Validation for email input
            if (email == null || email.isEmpty() || !email.matches("\\S+@\\S+\\.\\S+")) {
                request.setAttribute("message", "Please provide a valid email.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            } else if (UserDao.emailExists(email)) {
                request.setAttribute("message", "The email is already in use.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Validation for username input
            if (username == null || username.isEmpty() || password == null || password.isEmpty()) {
                request.setAttribute("message", "Please fill in all fields.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            } else if (UserDao.usernameExists(username)) {
                request.setAttribute("message", "The username is already in use.");
                request.getRequestDispatcher("register.jsp").forward(request, response);
                return;
            }

            // Need to add all input validation. None of input should be null.

            // Create address and retrieve address_id
            String address_id = AddressDao.add_address(address, city, province, country, postal_code);
            if (address_id != null) {
                // Create user and retrieve user_id
                String user_id = UserDao.createUser(email, username, password, first_name, last_name, address_id);
                User user = new User(user_id, first_name, last_name, false);

                // Verification Token Generator
                String token = TokenGenerator.generatedToken();
                UserDao.saveVerificationToken(user_id, token);

                // Send verification email
                JavaEmailService emailService = new JavaEmailService();
                String verificationLink = "http://localhost:8080/Bookshelf/verifyServlet?verification_code=" + token;
                emailService.sendVerificationEmail(email, verificationLink);

                // Find role_id by name
                String role_id = RoleDao.findRoleIdByName(user_role);

                // Assign role to user as pending status
                UserRoleDao.assign_role(user_id, role_id);

                request.setAttribute("message", "User registered successfully.");

                // Redirect to login page after successful registration
                response.sendRedirect("index.jsp");
            }

        } catch (Exception e) {
            request.setAttribute("message", "An error occurred during registration. Please try again.");
            request.getRequestDispatcher("register.jsp").forward(request, response);
        }
    }
}
