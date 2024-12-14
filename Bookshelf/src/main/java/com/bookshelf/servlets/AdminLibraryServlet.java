package com.bookshelf.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookshelf.beans.Library;
import com.bookshelf.dao.AddressDao;
import com.bookshelf.dao.AdminDao;

@WebServlet("/AdminLibraryServlet")
public class AdminLibraryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdminLibraryServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Library> libraries = AdminDao.getAllLibraries();

        // Debugging: Log the libraries
        System.out.println("Number of libraries fetched: " + (libraries != null ? libraries.size() : 0));

        // Pass the library list to the JSP
        request.setAttribute("libraries", libraries);

        // Forward to JSP
        request.getRequestDispatcher("adminLibrary.jsp").forward(request, response);
    }


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("add".equalsIgnoreCase(action)) {
            // Handle adding a library
            String libraryName = request.getParameter("libraryName");
            String libraryEmail = request.getParameter("libraryEmail");
            String libraryPhone = request.getParameter("libraryPhone");

            String address = request.getParameter("address");
            String city = request.getParameter("city");
            String province = request.getParameter("province");
            String country = request.getParameter("country");
            String postalCode = request.getParameter("postalCode");

            try {
                String addressId = AddressDao.add_address(address, city, province, country, postalCode);
                if (addressId != null) {
                    boolean success = AdminDao.addLibrary(libraryName, addressId, libraryEmail, libraryPhone);
                    if (success) {
                        request.setAttribute("message", "Library added successfully.");
                    } else {
                        request.setAttribute("message", "Failed to add library.");
                    }
                } else {
                    request.setAttribute("message", "Failed to add address.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "An error occurred while adding the library.");
            }
        } else if ("delete".equalsIgnoreCase(action)) {
            // Handle deleting a library
            String libraryId = request.getParameter("libraryId");
            try {
                boolean success = AdminDao.deleteLibrary(libraryId);
                if (success) {
                    request.setAttribute("message", "Library deleted successfully.");
                } else {
                    request.setAttribute("message", "Failed to delete library.");
                }
            } catch (Exception e) {
                e.printStackTrace();
                request.setAttribute("message", "An error occurred while deleting the library.");
            }
        }

        // Redirect back to doGet for a fresh view
        response.sendRedirect("AdminLibraryServlet");
    }
}
