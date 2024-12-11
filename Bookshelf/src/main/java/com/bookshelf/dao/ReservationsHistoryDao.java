package com.bookshelf.dao;

import com.bookshelf.beans.ReservationsHistory;
import com.bookshelf.connection.DBConnection;
import com.bookshelf.connection.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ReservationsHistoryDao {

    //  method to fetch reservations by user ID
    public List<ReservationsHistory> getReservationsByUserId(String userId) {
        List<ReservationsHistory> reservations = new ArrayList<>();
        String query = "SELECT r.reservation_id, r.reserved_date, r.status, " +
                "lb.library_book_id, b.title AS book_title, b.author AS book_author, " +
                "g.genre_name AS genre, l.library_name AS library_name " +
                "FROM bookshelf_reservation r " +
                "JOIN bookshelf_library_book lb ON r.library_book_id = lb.library_book_id " +
                "JOIN bookshelf_book b ON lb.book_id = b.book_id " +
                "LEFT JOIN bookshelf_genre g ON b.genre_id = g.genre_id " +
                "JOIN bookshelf_library l ON lb.library_id = l.library_id " +
                "WHERE r.user_id = ?";

        try (Connection connection = DBConnection.getDBInstance();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {

            preparedStatement.setString(1, userId);
            System.out.println("DEBUG: Executing query for user ID: " + userId);

            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                while (resultSet.next()) {
                    ReservationsHistory reservation = new ReservationsHistory(
                            resultSet.getString("reservation_id"),
                            resultSet.getTimestamp("reserved_date").toLocalDateTime(),
                            resultSet.getString("status"),
                            resultSet.getString("library_book_id"),
                            resultSet.getString("book_title"),
                            resultSet.getString("book_author"),
                            resultSet.getString("genre"),
                            resultSet.getString("library_name"));
                    reservations.add(reservation);
                }
            }
        } catch (SQLException | ClassNotFoundException e) {
            System.err.println("DEBUG: Error executing query: " + e.getMessage());
            throw new RuntimeException("Error fetching reservations", e);
        }

        System.out.println("DEBUG: Reservations fetched: " + reservations);
        return reservations;
    }

    //  method to fetch library_book_id by reservation ID
    /**
     * Fetches the library book ID associated with a reservation.
     *
     * @param reservationId The reservation ID.
     * @return The associated library book ID, or null if not found.
     */
    public String getLibraryBookIdByReservationId(String reservationId) {
        String query = "SELECT library_book_id FROM bookshelf_reservation WHERE reservation_id = ?";
        try (Connection connection = DBConnection.getDBInstance();
             PreparedStatement preparedStmt = connection.prepareStatement(query)) {
            preparedStmt.setString(1, reservationId);
            ResultSet resultSet = preparedStmt.executeQuery();

            if (resultSet.next()) {
                return resultSet.getString("library_book_id");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // method to delete a reservation by ID
    /**
     * Deletes a reservation from the database.
     *
     * @param reservationId The ID of the reservation to delete.
     */
    public void deleteReservation(String reservationId) {
        String query = "DELETE FROM bookshelf_reservation WHERE reservation_id = ?";
        try (Connection connection = DBConnection.getDBInstance();
             PreparedStatement preparedStmt = connection.prepareStatement(query)) {

            preparedStmt.setString(1, reservationId);
            int rowsAffected = preparedStmt.executeUpdate();

            if (rowsAffected > 0) {
                // Log success message
                System.out.println("Reservation with ID: " + reservationId + " has been successfully deleted.");
            } else {
                // Log if no rows were deleted
                System.out.println("No reservation found with ID: " + reservationId + " to delete.");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
    }
    }
    }
