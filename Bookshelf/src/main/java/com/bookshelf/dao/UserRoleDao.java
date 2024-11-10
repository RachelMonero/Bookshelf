package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.bookshelf.connection.DBConnection;
import com.bookshelf.connection.DBUtil;
import com.bookshelf.libs.UUIDGenerator;

public class UserRoleDao {

	public static void assign_role(String user_id, String role_id) {
		
		UUIDGenerator uuidGenerator = new UUIDGenerator();
		//create user_role_id
		String user_role_id = uuidGenerator.generateUUID();
		Timestamp assigned_date = new Timestamp(System.currentTimeMillis()); // Current timestamp
		// status options: Active, Pending, Suspend 
		String status = "Pending"; // need to update to Active when user verification is completed.
		
		try (Connection connection = DBConnection.getDBInstance()){
            
			String assign_role_sql = 
					"INSERT INTO " + ApplicationDao.USER_ROLE_TABLE + " VALUES(?,?,?,?,?)";
            
            PreparedStatement preparedStmt = connection.prepareStatement(assign_role_sql);             
            preparedStmt.setString(1, user_role_id);
            preparedStmt.setString(2, user_id);
            preparedStmt.setString(3, role_id);
            preparedStmt.setTimestamp(4, assigned_date);
            preparedStmt.setString(5, status);

            int rowsAffected = preparedStmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Role has been assigned to user successfully.");
            } else {
                System.out.println("Failed to assign role.");
            }

    } catch (SQLException e) {
        DBUtil.processException(e);
    } catch (ClassNotFoundException e) {
        e.printStackTrace();
    }

		
		
	}
}
