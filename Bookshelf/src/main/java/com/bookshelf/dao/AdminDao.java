package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import com.bookshelf.connection.DBConnection;
import com.bookshelf.libs.UUIDGenerator;

public class AdminDao {

	public static String addLibrary(String libraryName, String libraryAddressId, String libraryEmail, String libraryPhone) {
	    UUIDGenerator uuidGenerator = new UUIDGenerator();
	    String libraryId = uuidGenerator.generateUUID();

	    try (Connection connection = DBConnection.getDBInstance()) {
	        String sql = "INSERT INTO bookshelf_library (library_id, library_name, library_address_id, library_email, library_phone) VALUES (?, ?, ?, ?, ?)";
	        PreparedStatement preparedStmt = connection.prepareStatement(sql);

	        preparedStmt.setString(1, libraryId);
	        preparedStmt.setString(2, libraryName);
	        preparedStmt.setString(3, libraryAddressId);
	        preparedStmt.setString(4, libraryEmail);
	        preparedStmt.setString(5, libraryPhone);

	        int rowsInserted = preparedStmt.executeUpdate();
	        if (rowsInserted > 0) {
	            System.out.println("Library added successfully.");
	            return libraryId;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public static boolean editLibrary(String libraryId, String libraryName, String libraryAddressId, String libraryEmail, String libraryPhone) {
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
}
