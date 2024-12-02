package com.bookshelf.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookshelf.beans.User;
import com.bookshelf.dao.UserDao;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("LoginServlet doGet called - Forwarding to login.jsp");
        request.getRequestDispatcher("index.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String email = request.getParameter("email").toLowerCase();
        String password = request.getParameter("password");

        try {
            System.out.println("LoginServlet doPost called - Email: " + email + " | Password: " + (password != null ? "******" : "null"));

            // Validate inputs
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                System.out.println("Login failed - Empty email or password");
                request.setAttribute("message", "Please fill in all fields.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            // Check if the email exists
            if (!UserDao.emailExists(email)) {
                System.out.println("Login failed - Email not found: " + email);
                request.setAttribute("message", "No account found with this email. Please register.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            // Authenticate user
            User user = UserDao.authenticateUser(email, password);
            if (user == null) {
                System.out.println("Login failed - Invalid email or password");
                request.setAttribute("message", "Email and password do not match. Please try again.");
                request.getRequestDispatcher("index.jsp").forward(request, response);
                return;
            }

            // If authentication is successful, start session and redirect to the dashboard
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("email", email);

            System.out.println("Login successful -  User: "+user);
            System.out.println("Login successful -  Email: " + email); 
            
            response.sendRedirect("dashboard.jsp");

        } catch (Exception e) {
            System.out.println("An error occurred during login: " + e.getMessage());
            e.printStackTrace();
            request.setAttribute("message", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
    }
}
