package com.bookshelf.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.bookshelf.dao.UserDao;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public LoginServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        String email = request.getParameter("email").toLowerCase();
        String password = request.getParameter("password");

        try {
            // Validate inputs
            if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
                request.setAttribute("message", "Please fill in all fields.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            // Check if the email exists
            if (!UserDao.emailExists(email)) {
                request.setAttribute("message", "No account found with this email. Please register.");
                request.getRequestDispatcher("login.jsp").forward(request, response);
                return;
            }

            /* 
             * Authenticate user - Mike
             *  User user = UserDao.authenticateUser(email, password);
	            if (user == null) {
	                request.setAttribute("message", "Email and password do not match. Please try again.");
	                request.getRequestDispatcher("login.jsp").forward(request, response);
	                return;
	            } EXAMPLE

             * 
             * 
             * */
       
           
            /* 
             * If authentication is successful, start session and redirect to the dashboard - MIKE
	            HttpSession session = request.getSession();
	            session.setAttribute("user", user); EXAMPLE
             * 
             * */
            
            response.sendRedirect("dashboard.jsp");

        } catch (Exception e) {
            request.setAttribute("message", "An error occurred during login. Please try again.");
            request.getRequestDispatcher("login.jsp").forward(request, response);
        }
    }
}
