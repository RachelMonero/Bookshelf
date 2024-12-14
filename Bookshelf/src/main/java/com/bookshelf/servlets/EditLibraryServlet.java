package com.bookshelf.servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
import com.bookshelf.dao.UserDao;
import com.bookshelf.dao.UserRoleDao;
import com.bookshelf.dtos.LibrarianCandidateDto;

@WebServlet("/EditLibraryServlet")
public class EditLibraryServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public EditLibraryServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
    	
        String libraryId = request.getParameter("libraryId");
        System.out.println("[editLibraryServlet: libraryId:]"+libraryId);

        Library library = AdminDao.getLibraryById(libraryId);
        System.out.println("[editLibraryServlet: library:]"+library);
        
        String librarian_id =  library.getLibrarian_id();
        
        if(librarian_id !=null) {
          String librarian_username = UserDao.findUsernameById(librarian_id);
          request.setAttribute("librarian_username", librarian_username);
        } else {
        	String librarian_username = "";
            request.setAttribute("librarian_username", librarian_username);
        }

        if (library != null) {

            Address address = AddressDao.findAddressById(library.getLibrary_address_id());
            
            // get librarian candidates and map to librarianCandidateDtos
            List<LibrarianCandidateDto> librarianCandidateDtos = new ArrayList<>();
            List<String> librarian_ids = UserRoleDao.findAllLibrarian();
            for (String librarianId:librarian_ids) {
            	String username = UserDao.findUsernameById(librarianId);
            	LibrarianCandidateDto libCandidateDto = new LibrarianCandidateDto (librarianId, username);
            	librarianCandidateDtos.add(libCandidateDto);
            }
            librarianCandidateDtos.forEach(candidate -> 
            System.out.println("Candidate: " + candidate.getLibrarian_id() + ", Username: " + candidate.getLibrarian_username()) );

            if (address != null) {
            	request.setAttribute("libCandidates", librarianCandidateDtos);
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
        
        String librarian_id = request.getParameter("pre_librarian_id");
        String new_librarian_id = request.getParameter("new_librarian_id");
        
     // Treat empty value as null
        if (new_librarian_id != null && new_librarian_id.isEmpty()) {
            new_librarian_id = null; 
        }

        String addressId = request.getParameter("pre_address_id");
        String address = request.getParameter("address");
        String city = request.getParameter("city");
        String province = request.getParameter("province");
        String country = request.getParameter("country");
        String postalCode = request.getParameter("postal_code");
        
        if(librarian_id != new_librarian_id) {
        	librarian_id = new_librarian_id;
        }

        try {
            boolean addressUpdated = AddressDao.updateAddress(addressId, address, city, province, country, postalCode);
   

            if (addressUpdated) {

            	boolean librian_updated = LibraryDao.updateLibrarianId(libraryId, librarian_id);
                boolean libraryUpdated = LibraryDao.updateLibrary(libraryId, libraryName, addressId, libraryEmail, libraryPhone);
                if (libraryUpdated && librian_updated) {
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
