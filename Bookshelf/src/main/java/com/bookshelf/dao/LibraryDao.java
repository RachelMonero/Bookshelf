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


}