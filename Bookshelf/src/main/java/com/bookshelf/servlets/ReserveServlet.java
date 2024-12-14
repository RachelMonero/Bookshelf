package com.bookshelf.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.bookshelf.beans.User;
import com.bookshelf.dao.ReservationDao;
import com.bookshelf.dao.UserDao;
import com.bookshelf.dao.LibraryBookDao;

/**
 * Servlet implementation class ReserveServlet
 */
@WebServlet("/reserveBook")
public class ReserveServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		// Validate user session
		HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("user") == null) {
            response.sendRedirect("index.jsp");
            return;
        }
        
        String userRole = (String) session.getAttribute("role");
        System.out.println("search_userRole:"+userRole);
        boolean is_librarian = "librarian".equalsIgnoreCase(userRole);
        boolean is_admin = "sysAdmin".equalsIgnoreCase(userRole);
        String path = "dashboard.jsp";
        
        // Retrieve library_book_id
        String current_book_id = request.getParameter("library_book_id") != null ? request.getParameter("library_book_id") : null;
        System.out.println("reserve book:"+ current_book_id);
        String current_user_email = (String) session.getAttribute("email");
        System.out.println("reserve user email:"+ current_user_email);
        
        String current_user_id =  UserDao.findIdByEmail(current_user_email);
        System.out.println("reserve user id:"+ current_user_id);
        
        // set path
        if(is_librarian) {
        	path="libDashboard.jsp";
        } else if(is_admin) {
        	path="adminDashboard.jsp";
        	
        }

        if (current_book_id !=null && current_user_id != null) {
        	String reservation_id = ReservationDao.createReservation(current_user_id, current_book_id);
        	
        	if (reservation_id != null) {
        		//book availability to not available.
        		LibraryBookDao.setAvailabilityFalse(current_book_id);
        		
                // Success message
                request.setAttribute("message", "The book has been reserved successfully!");
                
            } else {
                // Failure message
                request.setAttribute("message", "Failed to reserve the book. Please try again.");
            }
        	
        	
        }

        request.getRequestDispatcher(path).forward(request, response);

	}

}
