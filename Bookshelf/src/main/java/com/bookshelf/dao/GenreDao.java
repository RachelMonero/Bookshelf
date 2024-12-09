package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bookshelf.connection.DBConnection;
import com.bookshelf.connection.DBUtil;

public class GenreDao {
	
	public static String findGenreNameById(String genre_id) {
	    try (Connection connection = DBConnection.getDBInstance()) {
	        String sql = 
	            "SELECT genre_name FROM " + ApplicationDao.GENRE_TABLE + " WHERE genre_id = ?";
	            
	        PreparedStatement preparedStmt = connection.prepareStatement(sql);
	        preparedStmt.setString(1, genre_id);

	        ResultSet resultSet = preparedStmt.executeQuery();

	        if (resultSet.next()) {

	            return resultSet.getString("genre_name");
	        }
	    } catch (SQLException e) {
	        DBUtil.processException(e);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public static String findGenreIdByName(String genre_name) {
	    try (Connection connection = DBConnection.getDBInstance()) {
	        String sql = 
	            "SELECT genre_id FROM " + ApplicationDao.GENRE_TABLE + " WHERE genre_name = ?";
	            
	        PreparedStatement preparedStmt = connection.prepareStatement(sql);
	        preparedStmt.setString(1, genre_name);

	        ResultSet resultSet = preparedStmt.executeQuery();

	        if (resultSet.next()) {

	            return resultSet.getString("genre_id");
	        }
	    } catch (SQLException e) {
	        DBUtil.processException(e);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return null;
	}

}
