package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.bookshelf.connection.DBConnection;
import com.bookshelf.connection.DBUtil;
import com.bookshelf.libs.UUIDGenerator;

public class RoleDao {
	
	
	
	
public static void add_role(String role_name, String description) {
		
		UUIDGenerator uuidGenerator = new UUIDGenerator();
		//create role_id
		String role_id = uuidGenerator.generateUUID();

		
		try (Connection connection = DBConnection.getDBInstance()){
            
			String add_role_sql = 
					"INSERT INTO " + ApplicationDao.ROLES_TABLE + " VALUES(?,?,?)";
            
            PreparedStatement preparedStmt = connection.prepareStatement(add_role_sql);             
            preparedStmt.setString(1, role_id);
            preparedStmt.setString(2, role_name);
            preparedStmt.setString(3, description);


            ResultSet rs = preparedStmt.executeQuery();
            if (rs != null && rs.next() && rs.getInt(1) > 0) {
            	System.out.println("Role has been added successfully.");
            }

            if (rs != null) rs.close();

    } catch (SQLException e) {
        DBUtil.processException(e);
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }
	
   }
	
	
	//Search role_id by name
	public static String findRoleIdByName(String role_name) {
		
		String role_id = null;
		
		try (Connection connection = DBConnection.getDBInstance()){
			 String get_roleId_sql = "SELECT role_id FROM " + ApplicationDao.ROLES_TABLE + " WHERE role_name=?";
			
             PreparedStatement preparedStmt = connection.prepareStatement(get_roleId_sql);             
             preparedStmt.setString(1, role_name);
             
				ResultSet rs = preparedStmt.executeQuery();
				
				if (rs.next()) {
		            
		            role_id = rs.getString("role_id");
		        }
				
				if (rs != null) rs.close();
			
	} catch(Exception e) {
		e.printStackTrace();
	}return role_id;
	
	}
	

}
