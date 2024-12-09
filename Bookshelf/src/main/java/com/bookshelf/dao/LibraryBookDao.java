package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.bookshelf.beans.LibraryBook;
import com.bookshelf.beans.User;
import com.bookshelf.connection.DBConnection;
import com.bookshelf.connection.DBUtil;

public class LibraryBookDao {

	public static void setAvailabilityFalse(String library_book_id) {
        String query = "UPDATE bookshelf_library_book SET is_available = false WHERE library_book_id = ?";

        try (Connection connection = DBConnection.getDBInstance();
             PreparedStatement preparedStmt = connection.prepareStatement(query)) {

            preparedStmt.setString(1, library_book_id);
            preparedStmt.executeUpdate();
            System.out.println("Book availability set to false for ID: " + library_book_id);

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
	
	public static LibraryBook getLibBookById(String library_book_id) {
		 try (Connection connection = DBConnection.getDBInstance()) {
		      String query = "SELECT * FROM bookshelf_library_book WHERE library_book_id = ?";
	          PreparedStatement statement = connection.prepareStatement(query);
	          statement.setString(1, library_book_id);
	          
	          ResultSet resultSet = statement.executeQuery();
	            if (resultSet.next()) {
	                String lib_book_id = resultSet.getString("library_book_id");
	                String library_id  = resultSet.getString("library_id");
	                String book_id = resultSet.getString("book_id");
	                Timestamp added_date = resultSet.getTimestamp("added_date");
	                boolean is_available = resultSet.getBoolean("is_available");

	                return new LibraryBook(lib_book_id,library_id,book_id,added_date,is_available);
	            }
      
		 }catch (SQLException e) {
	            DBUtil.processException(e);
	        } catch (ClassNotFoundException e) {
	            e.printStackTrace();
	        }
	        return null;
	}
	public static int countLibByBookId(String book_id) {
	    int count = 0;
	    try (Connection connection = DBConnection.getDBInstance()) {

	        String query = "SELECT COUNT(DISTINCT library_id) AS library_count FROM bookshelf_library_book WHERE book_id = ?";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, book_id);

	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            count = resultSet.getInt("library_count");
	        }
	    } catch (SQLException e) {
	        DBUtil.processException(e);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return count;
	}
	
}
