package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;

import com.bookshelf.connection.DBConnection;
import com.bookshelf.libs.UUIDGenerator;

public class AddressDao {
	
	// Save Address to Database
	public static String add_address(String address, String city, String province, String country, String postal_code) {
		
		UUIDGenerator uuidGenerator = new UUIDGenerator();
		
		// create address_id
		String address_id = uuidGenerator.generateUUID();
		
		try(Connection connection = DBConnection.getDBInstance()){
			
			String add_address_sql = "INSERT INTO "+ ApplicationDao.ADDRESS_TABLE + " values(?,?,?,?,?,?)";
			
			PreparedStatement preparedStmt =  connection.prepareStatement(add_address_sql);
			
			preparedStmt.setString(1, address_id);
			preparedStmt.setString(2, address);
			preparedStmt.setString(3, city);
			preparedStmt.setString(4, province);
			preparedStmt.setString(5, country);
			preparedStmt.setString(6, postal_code);
			
			int rowsInserted = preparedStmt.executeUpdate();
			
			if (rowsInserted > 0) {
				
	                System.out.println("Address is added successfully.");
	                
	                // bring address_id as result.
	                return address_id;  
	        }
			
		}catch(Exception e) {
			e.printStackTrace();
		} return null;
		
	}
	
	
	
	
	
	
	
	
	
	
}
