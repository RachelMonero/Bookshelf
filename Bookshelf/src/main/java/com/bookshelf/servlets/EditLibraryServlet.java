package com.bookshelf.servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookshelf.beans.Address;
import com.bookshelf.beans.Library;
import com.bookshelf.dao.AddressDao;
import com.bookshelf.dao.AdminDao;
import com.bookshelf.dao.LibraryDao;

@WebServlet("/EditLibraryServlet")
public class EditLibraryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditLibraryServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String libraryId = request.getParameter("libraryId");

        Library library = AdminDao.getLibraryById(libraryId);

        if (library != null) {

            Address address = AddressDao.findAddressById(library.getLibrary_address_id());

            if (address != null) {
                request.setAttribute("library", library);
                request.setAttribute("address", address);
                request.getRequestDispatcher("editLibrary.jsp").forward(request, response);
            } else {
                request.setAttribute("message", "Address not found for the selected library.");
                response.sendRedirect("AdminLibraryServlet");
            }
        } else {
            request.setAttribute("message", "Library not found.");
            response.sendRedirect("AdminLibraryServlet");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String libraryId = request.getParameter("library_id");
        String libraryName = request.getParameter("library_name");
        String libraryEmail = request.getParameter("library_email");
        String libraryPhone = request.getParameter("library_phone");

        String addressId = request.getParameter("pre_address_id");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String province = request.getParameter("province");
        String country = request.getParameter("country");
        String postalCode = request.getParameter("postal_code");

        try {
            boolean addressUpdated = AddressDao.updateAddress(addressId, address, city, province, country, postalCode);

            if (addressUpdated) {
                boolean libraryUpdated = LibraryDao.updateLibrary(libraryId, libraryName, addressId, libraryEmail, libraryPhone);
                if (libraryUpdated) {
                    request.setAttribute("message", "Library updated successfully.");
                } else {
                    request.setAttribute("message", "Failed to update library.");
                }
            } else {
                request.setAttribute("message", "Failed to update address.");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("message", "An error occurred while updating the library.");
        }

        response.sendRedirect("AdminLibraryServlet");
    }

}
