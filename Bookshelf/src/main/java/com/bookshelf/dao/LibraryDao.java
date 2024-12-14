package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bookshelf.beans.Address;
import com.bookshelf.beans.Library;
import com.bookshelf.connection.DBConnection;

public class LibraryDao {
	
	public static String getLibraryId(String user_id) {

		
		try (Connection connection = DBConnection.getDBInstance()) {
	        String find_library_sql = 
	            "SELECT library_id FROM "+ ApplicationDao.LIBRARY_TABLE + " WHERE librarian_id = ?";
	            
	        PreparedStatement preparedStmt = connection.prepareStatement(find_library_sql);
	        preparedStmt.setString(1, user_id);
	        
	       
	        ResultSet resultSet = preparedStmt.executeQuery();

	        if (resultSet.next()) {

	            String library_id = resultSet.getString("library_id");

	            return library_id;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public static boolean isLibrarian(String user_id) {
	    String query = "SELECT COUNT(*) AS librarian_count FROM " + ApplicationDao.LIBRARY_TABLE + " WHERE librarian_id = ?";
	    
	    try (Connection connection = DBConnection.getDBInstance();
	         PreparedStatement preparedStmt = connection.prepareStatement(query)) {
	        
	        preparedStmt.setString(1, user_id);

	        ResultSet resultSet = preparedStmt.executeQuery();

	        if (resultSet.next()) {
	            int librarianCount = resultSet.getInt("librarian_count");
	            return librarianCount > 0; 
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return false; 
	}
	
	public static boolean updateLibrarianId(String library_id, String user_id) {
	    String query = "UPDATE " + ApplicationDao.LIBRARY_TABLE + " SET librarian_id = ? WHERE library_id = ?";

	    try (Connection connection = DBConnection.getDBInstance();
	         PreparedStatement preparedStmt = connection.prepareStatement(query)) {

	        preparedStmt.setString(1, user_id);
	        preparedStmt.setString(2, library_id);

	        int rowsUpdated = preparedStmt.executeUpdate();


	        return rowsUpdated > 0;

	    } catch (SQLException e) {
	    	e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	
	public static String getLibraryNameById(String library_id) {
		String query = "SELECT library_name FROM "+ ApplicationDao.LIBRARY_TABLE + " WHERE library_id = ?";
		
		try (Connection connection = DBConnection.getDBInstance()) {
	            
	        PreparedStatement preparedStmt = connection.prepareStatement(query);
	        preparedStmt.setString(1, library_id);
	       
	       
	        ResultSet resultSet = preparedStmt.executeQuery();

	        if (resultSet.next()) {

	            String library_name = resultSet.getString("library_name");

	            return library_name;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return null;
	}

	public static Library getLibraryById(String libraryId) {
	    String query = "SELECT l.library_id, l.library_name, l.library_email, l.library_phone, " +
	                   "a.address, a.city, a.province, a.country, a.postal_code " +
	                   "FROM bookshelf_library l " +
	                   "JOIN bookshelf_address a ON l.library_address_id = a.address_id " +
	                   "WHERE l.library_id = ?";

	    try (Connection connection = DBConnection.getDBInstance();
	         PreparedStatement stmt = connection.prepareStatement(query)) {

	        stmt.setString(1, libraryId);
	        ResultSet rs = stmt.executeQuery();

	        if (rs.next()) {
	            String fullAddress = rs.getString("address") + ", " + rs.getString("city") + ", " +
	                                 rs.getString("province") + ", " + rs.getString("country") + ", " +
	                                 rs.getString("postal_code");

	            return new Library(
	                rs.getString("library_id"),
	                rs.getString("library_name"),
	                fullAddress, // Use the full address here
	                rs.getString("library_email"),
	                rs.getString("library_phone")
	            );
	        }
	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	// Method to update the library
	public static boolean updateLibrary(String libraryId, String libraryName, String libraryAddressId, String libraryEmail, String libraryPhone) {
	    String query = "UPDATE " + ApplicationDao.LIBRARY_TABLE + 
	                   " SET library_name = ?, library_address_id = ?, library_email = ?, library_phone = ? WHERE library_id = ?";

	    try (Connection connection = DBConnection.getDBInstance();
	         PreparedStatement preparedStmt = connection.prepareStatement(query)) {

	        preparedStmt.setString(1, libraryName);
	        preparedStmt.setString(2, libraryAddressId);
	        preparedStmt.setString(3, libraryEmail);
	        preparedStmt.setString(4, libraryPhone);
	        preparedStmt.setString(5, libraryId);

	        int rowsUpdated = preparedStmt.executeUpdate();
	        return rowsUpdated > 0;

	    } catch (SQLException | ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return false;
	}
	// find address_id by library_id
	public static String getAddress_idByLibraryId(String library_id) {
		String library_address_id =null;
		String query = "SELECT library_address_id FROM "+ ApplicationDao.LIBRARY_TABLE + " WHERE library_id = ?";
		
		try (Connection connection = DBConnection.getDBInstance()) {
	            
	        PreparedStatement preparedStmt = connection.prepareStatement(query);
	        preparedStmt.setString(1, library_id);
	       
	       
	        ResultSet resultSet = preparedStmt.executeQuery();

	        if (resultSet.next()) {

	            library_address_id = resultSet.getString("library_address_id");

	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return library_address_id;
	}
	
	public static boolean removeLibrarianFromLibrary(String library_id) {
	    String query = "UPDATE " + ApplicationDao.LIBRARY_TABLE + " SET librarian_id = NULL WHERE library_id = ?";

	    try (Connection connection = DBConnection.getDBInstance();
	         PreparedStatement preparedStmt = connection.prepareStatement(query)) {

	        preparedStmt.setString(1, library_id);

	        int rowsUpdated = preparedStmt.executeUpdate();

	        return rowsUpdated > 0; 
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return false; 
	}



}
