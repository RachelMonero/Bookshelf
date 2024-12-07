package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bookshelf.beans.Address;
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
	
	public static Address findAddressById(String address_id) {
	    try (Connection connection = DBConnection.getDBInstance()) {
	        String find_address_sql = "SELECT address, city, province, country, postal_code FROM " 
	                                   + ApplicationDao.ADDRESS_TABLE + " WHERE address_id = ?";
	        PreparedStatement preparedStmt = connection.prepareStatement(find_address_sql);
	        preparedStmt.setString(1, address_id);

	        ResultSet resultSet = preparedStmt.executeQuery();

	        if (resultSet.next()) {

	            String address = resultSet.getString("address");
	            String city = resultSet.getString("city");
	            String province = resultSet.getString("province");
	            String country = resultSet.getString("country");
	            String postal_code = resultSet.getString("postal_code");


	            return new Address(address_id, address, city, province, country, postal_code);
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return null;
	}
	
	public static boolean deleteAddressById(String address_id) {
	    String query = "DELETE FROM " + ApplicationDao.ADDRESS_TABLE + " WHERE address_id = ?";

	    try (Connection connection = DBConnection.getDBInstance();
	         PreparedStatement preparedStmt = connection.prepareStatement(query)) {

	        preparedStmt.setString(1, address_id);

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
