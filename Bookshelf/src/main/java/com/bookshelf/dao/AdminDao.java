package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.bookshelf.beans.Library;
import com.bookshelf.connection.DBConnection;
import com.bookshelf.libs.UUIDGenerator;

public class AdminDao {

	// Method to add a new library
	public static boolean addLibrary(String libraryName, String libraryAddressId, String libraryEmail, String libraryPhone) {
	    String query = "INSERT INTO bookshelf_library (library_id, library_name, library_address_id, library_email, library_phone) VALUES (?, ?, ?, ?, ?)";

	    try (Connection connection = DBConnection.getDBInstance();
	         PreparedStatement preparedStmt = connection.prepareStatement(query)) {

	        String libraryId = UUIDGenerator.generateUUID(); 
	        preparedStmt.setString(1, libraryId);
	        preparedStmt.setString(2, libraryName);
	        preparedStmt.setString(3, libraryAddressId);
	        preparedStmt.setString(4, libraryEmail);
	        preparedStmt.setString(5, libraryPhone);

	        int rowsInserted = preparedStmt.executeUpdate();
	        return rowsInserted > 0;
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	// Method to edit existing library
	public static boolean editLibrary(String libraryId, String libraryName, String libraryAddressId, String libraryEmail, String libraryPhone, String city, String province, String country, String postalCode) {
	    try (Connection connection = DBConnection.getDBInstance()) {
	        String sql = "UPDATE bookshelf_library SET library_name = ?, library_address_id = ?, library_email = ?, library_phone = ? WHERE library_id = ?";
	        PreparedStatement preparedStmt = connection.prepareStatement(sql);

	        preparedStmt.setString(1, libraryName);
	        preparedStmt.setString(2, libraryAddressId);
	        preparedStmt.setString(3, libraryEmail);
	        preparedStmt.setString(4, libraryPhone);
	        preparedStmt.setString(5, libraryId);

	        int rowsUpdated = preparedStmt.executeUpdate();
	        return rowsUpdated > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	// Method to delete library
	public static boolean deleteLibrary(String libraryId) {
	    try (Connection connection = DBConnection.getDBInstance()) {
	        String sql = "DELETE FROM bookshelf_library WHERE library_id = ?";
	        PreparedStatement preparedStmt = connection.prepareStatement(sql);

	        preparedStmt.setString(1, libraryId);

	        int rowsDeleted = preparedStmt.executeUpdate();
	        return rowsDeleted > 0;
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	// Method to get all libraries
	public static List<Library> getAllLibraries() {
	    List<Library> libraries = new ArrayList<>();
	    String query = "SELECT l.library_id, l.library_name, l.library_email, l.library_phone, " +
	                   "a.address, a.city, a.province, a.country, a.postal_code " +
	                   "FROM bookshelf_library l " +
	                   "JOIN bookshelf_address a ON l.library_address_id = a.address_id";

	    try (Connection connection = DBConnection.getDBInstance();
	         PreparedStatement stmt = connection.prepareStatement(query);
	         ResultSet rs = stmt.executeQuery()) {

	        while (rs.next()) {
	            String fullAddress = rs.getString("address") + ", " + rs.getString("city") + ", " +
	                                 rs.getString("province") + ", " + rs.getString("country") + ", " +
	                                 rs.getString("postal_code");

	            Library library = new Library(
	                rs.getString("library_id"),
	                rs.getString("library_name"),
	                fullAddress, 
	                rs.getString("library_email"),
	                rs.getString("library_phone")
	            );
	            libraries.add(library);
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return libraries;
	}

	// Method to get the library by its id
	public static Library getLibraryById(String libraryId) {
		String query = "SELECT l.library_id, l.library_name, l.library_address_id, l.library_email, l.library_phone " +
	               "FROM bookshelf_library l " +
	               "WHERE l.library_id = ?";

	    try (Connection connection = DBConnection.getDBInstance();
	         PreparedStatement stmt = connection.prepareStatement(query)) {

	        stmt.setString(1, libraryId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	        	Library library = new Library(
	        		    rs.getString("library_id"),
	        		    rs.getString("library_name"),
	        		    rs.getString("library_address_id"),
	        		    rs.getString("library_email"),
	        		    rs.getString("library_phone")
	        		);
	            return library;
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
}
