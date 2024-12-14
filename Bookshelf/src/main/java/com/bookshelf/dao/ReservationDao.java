package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;

import com.bookshelf.beans.Reservation;
import com.bookshelf.beans.User;
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
	
	public static List<Reservation> getAllReservation() {
		
		List<Reservation> rvList = new ArrayList<>();
		
		try (Connection connection = DBConnection.getDBInstance()){
			
			String getAllRvSql = "SELECT * FROM bookshelf_reservation";
			PreparedStatement preparedStmt = connection.prepareStatement(getAllRvSql);
			
			ResultSet resultSet = preparedStmt.executeQuery();
			
			while (resultSet.next()) {
                String rv_id = resultSet.getString("reservation_id");
                String user_id = resultSet.getString("user_id");
                String lib_book_id = resultSet.getString("library_book_id");
                Timestamp rv_date = resultSet.getTimestamp("reserved_date");
                String status = resultSet.getString("status");

                Reservation rv = new Reservation(rv_id,user_id,lib_book_id,rv_date,status);
                rvList.add(rv);
            }
			
		}catch (SQLException e) {
	        DBUtil.processException(e);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return rvList;
	}
	
	public static int countReservationsByLibBookId(String library_book_id) {
		
	    int rvCount = 0;

	    try (Connection connection = DBConnection.getDBInstance()) {
	    	
	        String count_reservations_sql = 
	            "SELECT COUNT(*) AS reservation_count FROM bookshelf_reservation " +
	            "WHERE library_book_id = ?";

	        PreparedStatement preparedStmt = connection.prepareStatement(count_reservations_sql);
	        preparedStmt.setString(1, library_book_id);

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
	
	public static List<Reservation> getReservationsWithDetails(String libraryId) {
	    List<Reservation> reservations = new ArrayList<>();
	    String sql = "SELECT r.reservation_id, r.reserved_date, r.status, u.username, b.title AS book_name, b.isbn " +
	                 "FROM bookshelf_reservation r " +
	                 "JOIN bookshelf_library_book lb ON r.library_book_id = lb.library_book_id " +
	                 "JOIN bookshelf_user u ON r.user_id = u.user_id " +
	                 "JOIN bookshelf_book b ON lb.book_id = b.book_id " +
	                 "WHERE lb.library_id = ?";

	    try (Connection connection = DBConnection.getDBInstance();
	         PreparedStatement preparedStmt = connection.prepareStatement(sql)) {

	        preparedStmt.setString(1, libraryId);
	        ResultSet resultSet = preparedStmt.executeQuery();

	        while (resultSet.next()) {
	            String reservationId = resultSet.getString("reservation_id");
	            Timestamp reservedDate = resultSet.getTimestamp("reserved_date");
	            String status = resultSet.getString("status");
	            String username = resultSet.getString("username");
	            String bookName = resultSet.getString("book_name");
	            String isbn = resultSet.getString("isbn");

	            // Create Reservation object
	            Reservation reservation = new Reservation(reservationId, reservedDate, username, bookName, isbn, status);
	            reservations.add(reservation);
	        }
	    } catch (SQLException e) {
	        DBUtil.processException(e);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return reservations;
	}
	
	public static boolean markAsPickedUp(String reservationId) {
	    String sql = "UPDATE bookshelf_reservation SET status = 'picked up' WHERE reservation_id = ?";
	    try (Connection connection = DBConnection.getDBInstance();
	         PreparedStatement preparedStmt = connection.prepareStatement(sql)) {

	        preparedStmt.setString(1, reservationId);

	        int rowsUpdated = preparedStmt.executeUpdate();
	        return rowsUpdated > 0; // Return true if the update was successful
	    } catch (SQLException e) {
	        DBUtil.processException(e);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

}
