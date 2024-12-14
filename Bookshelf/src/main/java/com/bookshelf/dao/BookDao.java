package com.bookshelf.dao;


import com.bookshelf.beans.Book;
import com.bookshelf.beans.User;
import com.bookshelf.connection.DBConnection;
import com.bookshelf.connection.DBUtil;
import com.bookshelf.dtos.BookDto;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    public List<BookDto> searchBooks(String title, String author, String genre, String availability) {
        List<BookDto> books = new ArrayList<>();

        // Build query dynamically based on parameters
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT lb.library_book_id, b.title, b.author, b.isbn, b.published_year, ");
        queryBuilder.append("g.genre_name AS genre_name, lb.is_available, l.library_name ");
        queryBuilder.append("FROM bookshelf_library_book lb ");
        queryBuilder.append("INNER JOIN bookshelf_book b ON lb.book_id = b.book_id ");
        queryBuilder.append("LEFT JOIN bookshelf_genre g ON b.genre_id = g.genre_id ");
        queryBuilder.append("INNER JOIN bookshelf_library l ON lb.library_id = l.library_id "); // Joining the library table
        queryBuilder.append("WHERE 1=1 "); // Always true, simplifies appending conditions

        if (title != null && !title.isEmpty()) {
            queryBuilder.append("AND LOWER(b.title) LIKE ? ");
        }
        if (author != null && !author.isEmpty()) {
            queryBuilder.append("AND LOWER(b.author) LIKE ? ");
        }
        if (!"All Genres".equalsIgnoreCase(genre)) {
            queryBuilder.append("AND g.genre_id = ? ");
        }
        if (!"Any".equalsIgnoreCase(availability)) {
            queryBuilder.append("AND lb.is_available = ? ");
        }

        String query = queryBuilder.toString();

        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters dynamically
            int paramIndex = 1;
            if (title != null && !title.isEmpty()) {
                stmt.setString(paramIndex++, "%" + title.toLowerCase() + "%");
            }
            if (author != null && !author.isEmpty()) {
                stmt.setString(paramIndex++, "%" + author.toLowerCase() + "%");
            }
            if (!"All Genres".equalsIgnoreCase(genre)) {
                stmt.setString(paramIndex++, genre);
            }
            if (!"Any".equalsIgnoreCase(availability)) {
                stmt.setInt(paramIndex++, Integer.parseInt(availability));
            }

            // Debugging: Log the query and parameters
            System.out.println("Executing SQL Query: " + query);
            System.out.println("Parameters:");
            if (title != null && !title.isEmpty()) {
                System.out.println("Title: %" + title.toLowerCase() + "%");
            } else {
                System.out.println("Title: NULL");
            }
            if (author != null && !author.isEmpty()) {
                System.out.println("Author: %" + author.toLowerCase() + "%");
            } else {
                System.out.println("Author: NULL");
            }
            if (!"All Genres".equalsIgnoreCase(genre)) {
                System.out.println("Genre: " + genre);
            } else {
                System.out.println("Genre: All Genres");
            }
            if (!"Any".equalsIgnoreCase(availability)) {
                System.out.println("Availability: " + availability);
            } else {
                System.out.println("Availability: Any");
            }

            ResultSet rs = stmt.executeQuery();
           

            // Process the result set
            while (rs.next()) {
            	
            	String rs_library_book_id =  rs.getString("library_book_id");
            	String rs_title = rs.getString("title");
            	String rs_author = rs.getString("author");
            	String rs_isbn = rs.getString("isbn");
            	int rs_year = rs.getInt("published_year");
            	String rs_genre_name = rs.getString("genre_name");
            	String rs_availability = rs.getInt("is_available") == 1 ? "Available" : "Not Available";
            	String rs_library_name = rs.getString("library_name");
            	
            	System.out.println(rs_library_book_id+","+ rs_title+","+ rs_author+","+rs_isbn+","+ rs_year+","+ rs_genre_name+","+ rs_availability+","+ rs_library_name);
            	 

            	
            	
                books.add(new BookDto(
                		
                		 
                		rs_library_book_id, rs_title, rs_author,rs_isbn, rs_year, rs_genre_name, rs_availability, rs_library_name
                       // rs.getString("library_book_id"),
                       // rs.getString("title"),
                       // rs.getString("author"),
                       //rs.getString("isbn"),
                       //rs.getInt("published_year"),
                       //rs.getString("genre_name"),
                       //rs.getInt("is_available") == 1 ? "Available" : "Not Available",
                       //rs.getString("library_name")		
                ));
            }

            // Debugging: Log the number of books retrieved
            System.out.println("Books found: " + books.size());

        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return books;
    }
    
    public static Book getBookById(String book_id) {
    	try (Connection connection = DBConnection.getDBInstance()) {
            String query = "SELECT * FROM bookshelf_book WHERE book_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, book_id);


            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String current_book_id = resultSet.getString("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                int publishedYear = resultSet.getInt("published_year");
                String genre = resultSet.getString("genre_id");;
                

                return new Book(current_book_id, title, author, isbn, publishedYear,genre);
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
    
    public static List<Book> getAllBook(){
    	List<Book> allBooks = new ArrayList<>();
    	try (Connection connection = DBConnection.getDBInstance()) {
            String query = "SELECT * FROM bookshelf_book";
            PreparedStatement statement = connection.prepareStatement(query);
            
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next())  {
                String current_book_id = resultSet.getString("book_id");
                String title = resultSet.getString("title");
                String author = resultSet.getString("author");
                String isbn = resultSet.getString("isbn");
                int publishedYear = resultSet.getInt("published_year");
                String genre = resultSet.getString("genre_id");
                

                Book book = new Book(current_book_id, title, author, isbn, publishedYear,genre);
                allBooks.add(book);
                
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    	return allBooks;
    }
            
    public static boolean deleteBookById(String bookId) {
        String query = "DELETE FROM bookshelf_book WHERE book_id = ?";
        try (Connection connection = DBConnection.getDBInstance();
             PreparedStatement preparedStmt = connection.prepareStatement(query)) {

            preparedStmt.setString(1, bookId);
            int rowsAffected = preparedStmt.executeUpdate();

            // Log the deletion status
            if (rowsAffected > 0) {
                System.out.println("Book deleted successfully. Book ID: " + bookId);
                return true;
            } else {
                System.out.println("No rows affected. Book deletion failed for Book ID: " + bookId);
                return false;
            }

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println("Error occurred while deleting the book with ID: " + bookId);
            e.printStackTrace();
        }
        return false;
    }

    
    
    public static boolean updateBookById(String book_id, String title, String author, String isbn, int publishedYear, int genre_id) {
    	
    	boolean result = false;
    	
    	try (Connection connection = DBConnection.getDBInstance()) {        	
        	
            String query = "UPDATE bookshelf_book SET title = ?, author = ?, isbn = ?, published_year = ?, genre_id = ? WHERE book_id = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, title);
            statement.setString(2, author);
            statement.setString(3, isbn);
            statement.setInt(4, publishedYear);
            statement.setInt(5, genre_id);
            statement.setString(6, book_id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Book has been updated successfully.");
                result= true;
            } else {
                System.out.println("No book found.");
                
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }
    
    public static boolean isBookExist(String title, String author, int publishedYear) {
        boolean exists = false;

        try (Connection connection = DBConnection.getDBInstance()) {
            String query = "SELECT COUNT(*) AS count FROM bookshelf_book WHERE title = ? AND author = ? AND published_year = ?";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, title);
            statement.setString(2, author);
            statement.setInt(3, publishedYear);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                int count = resultSet.getInt("count");
                exists = count > 0;
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return exists;
    }
    
    public static boolean createBook(String book_id, String title, String author, String isbn, int publishedYear, int genre_id) {
        boolean result = false;

        try (Connection connection = DBConnection.getDBInstance()) {

            String query = "INSERT INTO bookshelf_book (book_id, title, author, isbn, published_year, genre_id) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);

            statement.setString(1, book_id);
            statement.setString(2, title);
            statement.setString(3, author);
            statement.setString(4, isbn);
            statement.setInt(5, publishedYear);
            statement.setInt(6, genre_id);

            // Execute the query
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Book has been added successfully.");
                result = true;
            } else {
                System.out.println("Failed to add the book.");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return result;
    }

    
}
