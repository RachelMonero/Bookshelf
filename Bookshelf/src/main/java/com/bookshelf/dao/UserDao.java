package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bookshelf.connection.DBConnection;
import com.bookshelf.connection.DBUtil;
import com.bookshelf.libs.UUIDGenerator;



public class UserDao {

	//create user
	public static String createUser(String email, String username, String password, String first_name, String last_name, String address_id) {
		
		UUIDGenerator uuidGenerator = new UUIDGenerator();
		//create user_id
		String user_id = uuidGenerator.generateUUID();
		Boolean is_verified = false;
	
		
		try(Connection connection = DBConnection.getDBInstance()){
			
			
			String create_user_sql = "INSERT INTO " + ApplicationDao.USERS_TABLE + " values(?,?,?,?,?,?,?,?)";
            
			PreparedStatement preparedStmt = connection.prepareStatement(create_user_sql);
            preparedStmt.setString(1, user_id);
            preparedStmt.setString(2, email);
            preparedStmt.setString(3, username);
            preparedStmt.setString(4, password);
            preparedStmt.setString(5, first_name);
            preparedStmt.setString(6, last_name);
            preparedStmt.setString(7, address_id);
            preparedStmt.setBoolean(8, is_verified);
			
			int rowsInserted = preparedStmt.executeUpdate();
			
			if (rowsInserted > 0) {
				
	                System.out.println("User is created successfully.");	
	                return user_id;  
 
	        }
			
		} catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } return null;
    }
	
	
	//method to check username is in use
	public static boolean usernameExists(String username) {
		
		boolean exists = false;
		
        try(Connection connection = DBConnection.getDBInstance()) {
                
                
				String username_exists_sql = 
						"SELECT * FROM " + ApplicationDao.USERS_TABLE + " WHERE username=?";
                
                PreparedStatement preparedStmt = connection.prepareStatement(username_exists_sql);             
                preparedStmt.setString(1, username);

                ResultSet rs = preparedStmt.executeQuery();
                if (rs != null && rs.next() && rs.getInt(1) > 0) {
                   exists = true;
                }

                if (rs != null) rs.close();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return exists;
    }
	
	//method to check email is in use
	public static boolean emailExists(String email) {
		
		boolean exists = false;
		
        try (Connection connection = DBConnection.getDBInstance()){
                
				String email_exists_sql = 
						"SELECT * FROM " + ApplicationDao.USERS_TABLE + " WHERE email=?";
                
                PreparedStatement preparedStmt = connection.prepareStatement(email_exists_sql);             
                preparedStmt.setString(1, email);

                ResultSet rs = preparedStmt.executeQuery();
                if (rs != null && rs.next() && rs.getInt(1) > 0) {
                   exists = true;
                }

                if (rs != null) rs.close();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return exists;
    }
	
	
}
