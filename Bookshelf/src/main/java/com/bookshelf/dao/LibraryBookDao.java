package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
	
	
}
