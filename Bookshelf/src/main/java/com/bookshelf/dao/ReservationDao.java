package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import com.bookshelf.connection.DBConnection;
import com.bookshelf.connection.DBUtil;
import com.bookshelf.libs.UUIDGenerator;

public class ReservationDao {
	
	//create reservation
	public static String createReservation(String user_id, String library_book_id) {
		
		UUIDGenerator uuidGenerator = new UUIDGenerator();
		String reservation_id = uuidGenerator.generateUUID();
		String status = "reserved";
		Timestamp reserved_date = new Timestamp(System.currentTimeMillis());
		
		try(Connection connection = DBConnection.getDBInstance()){
			String create_reservation = "INSERT INTO bookshelf_reservation values(?,?,?,?,?)";
			PreparedStatement preparedStmt = connection.prepareStatement(create_reservation, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, reservation_id);
            preparedStmt.setString(2, user_id);
            preparedStmt.setString(3, library_book_id);            
			preparedStmt.setTimestamp(4, reserved_date);
            preparedStmt.setString(5, status);
            
            int rowsInserted = preparedStmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("The book has been reserved successfully.");
                return reservation_id;
            }
           
		} catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
     }
	
	
	public static int countReservationsByUserId(String user_id) {
		
	    int rvCount = 0;

	    try (Connection connection = DBConnection.getDBInstance()) {
	    	
	        String count_reservations_sql = 
	            "SELECT COUNT(*) AS reservation_count FROM bookshelf_reservation " +
	            "WHERE user_id = ?";

	        PreparedStatement preparedStmt = connection.prepareStatement(count_reservations_sql);
	        preparedStmt.setString(1, user_id);

	        ResultSet resultSet = preparedStmt.executeQuery();

	        if (resultSet.next()) {
	            rvCount = resultSet.getInt("reservation_count");
	        }
	    } catch (SQLException e) {
	        DBUtil.processException(e);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }

	    return rvCount;
	}
	

	public static boolean deleteReservationsByUserId(String user_id) {
	    try (Connection connection = DBConnection.getDBInstance()) {

	        String delete_reservation_sql = "DELETE FROM bookshelf_reservation WHERE user_id = ?";
	        
	        PreparedStatement preparedStmt = connection.prepareStatement(delete_reservation_sql);
	        preparedStmt.setString(1, user_id);


	        int rowsDeleted = preparedStmt.executeUpdate();

	        return rowsDeleted > 0;
	        
	    } catch (SQLException e) {
	        DBUtil.processException(e);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

}
