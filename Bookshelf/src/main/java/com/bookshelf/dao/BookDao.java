package com.bookshelf.dao;


import com.bookshelf.connection.DBConnection;
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
}
