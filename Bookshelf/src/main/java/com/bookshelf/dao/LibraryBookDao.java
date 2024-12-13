package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.bookshelf.beans.Book;
import com.bookshelf.beans.LibraryBook;
import com.bookshelf.beans.User;
import com.bookshelf.connection.DBConnection;
import com.bookshelf.connection.DBUtil;
import com.bookshelf.libs.UUIDGenerator;


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
    


    public static LibraryBook getLibBookById(String library_book_id) {
        try (Connection connection = DBConnection.getDBInstance()) {
            String query = "SELECT * FROM bookshelf_library_book WHERE library_book_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, library_book_id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String lib_book_id = resultSet.getString("library_book_id");
                String library_id  = resultSet.getString("library_id");
                String book_id = resultSet.getString("book_id");
                Timestamp added_date = resultSet.getTimestamp("added_date");
                boolean is_available = resultSet.getBoolean("is_available");

                return new LibraryBook(lib_book_id, library_id, book_id, added_date, is_available);
            }

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static int countLibByBookId(String book_id) {
        int count = 0;
        try (Connection connection = DBConnection.getDBInstance()) {

            String query = "SELECT COUNT(DISTINCT library_id) AS library_count FROM bookshelf_library_book WHERE book_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book_id);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                count = resultSet.getInt("library_count");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * Updates the availability status of a book in the library.
     *
     * @param libraryBookId The ID of the library book to update.
     * @param isAvailable   The new availability status (1 for available, 0 for unavailable).
     */
    public static void updateBookAvailability(String libraryBookId, int isAvailable) {
        String query = "UPDATE bookshelf_library_book SET is_available = ? WHERE library_book_id = ?";

        try (Connection connection = DBConnection.getDBInstance();
             PreparedStatement preparedStmt = connection.prepareStatement(query)) {

            preparedStmt.setInt(1, isAvailable); // 1 = available, 0 = not available
            preparedStmt.setString(2, libraryBookId);
            preparedStmt.executeUpdate();

            System.out.println("Book availability updated for ID: " + libraryBookId + " to: " + isAvailable);

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
    }
    
    public static List<Book> getBooksByLibraryId(String libraryId) {
        List<Book> books = new ArrayList<>();
        String query = "SELECT b.book_id, b.title, b.author, b.isbn, b.published_year, b.genre_id " +
                       "FROM bookshelf_library_book lb " +
                       "JOIN bookshelf_book b ON lb.book_id = b.book_id " +
                       "WHERE lb.library_id = ?";
        try (Connection connection = DBConnection.getDBInstance();
             PreparedStatement preparedStmt = connection.prepareStatement(query)) {
            preparedStmt.setString(1, libraryId);
            ResultSet resultSet = preparedStmt.executeQuery();
            while (resultSet.next()) {
                books.add(new Book(
                    resultSet.getString("book_id"),
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("isbn"),
                    resultSet.getInt("published_year"),
                    resultSet.getString("genre_id") // Updated to fetch the correct column
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return books;
    }


    public static boolean is_libBookInRev(String book_id, String libraryId) {
        boolean libBook_inUse = false;
        
        String query = "SELECT is_available FROM bookshelf_library_book " +
                       "WHERE book_id = ? AND library_id = ?";
        try (Connection connection = DBConnection.getDBInstance();
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, book_id);
            statement.setString(2, libraryId);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
            	libBook_inUse = resultSet.getBoolean("is_available");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return libBook_inUse;
    }

    public static boolean linkBookToLibrary(String bookId, String libraryId) {
        String query = "INSERT INTO bookshelf_library_book (library_book_id, library_id, book_id, added_date, is_available) VALUES (?, ?, ?, NOW(), true)";
        try (Connection connection = DBConnection.getDBInstance();
             PreparedStatement preparedStmt = connection.prepareStatement(query)) {
            // Generate a unique ID for library_book_id
            String libraryBookId = UUIDGenerator.generateUUID();
            preparedStmt.setString(1, libraryBookId); // Set library_book_id
            preparedStmt.setString(2, libraryId);     // Set library_id
            preparedStmt.setString(3, bookId);        // Set book_id
            int rowsInserted = preparedStmt.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean deleteLibBookById(String library_book_id) {
        String query = "DELETE FROM bookshelf_library_book WHERE library_book_id = ?";
        try (Connection connection = DBConnection.getDBInstance();
             PreparedStatement preparedStmt = connection.prepareStatement(query)) {

            preparedStmt.setString(1, library_book_id);

            int rowsAffected = preparedStmt.executeUpdate();

            // Log the deletion status
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully from library. Book ID: " + library_book_id);
                return true;
            } else {
                System.out.println("No rows affected. Library Book deletion failed for Book ID: " + library_book_id);
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error occurred while deleting the book with ID: " + library_book_id);
            e.printStackTrace();
        }
        return false;
    }
    
    public static String getLibBookId(String bookId, String library_id) {
    	String library_book_id = null;
        try (Connection connection = DBConnection.getDBInstance()) {
            String query = "SELECT library_book_id FROM bookshelf_library_book WHERE book_id = ? AND library_id = ?";
            PreparedStatement preparedStmt = connection.prepareStatement(query);
            preparedStmt.setString(1, bookId);
            preparedStmt.setString(2, library_id);

           
            ResultSet resultSet = preparedStmt.executeQuery();
            if (resultSet.next()) {
            	library_book_id = resultSet.getString("library_book_id");
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return library_book_id;
    
    }

}
