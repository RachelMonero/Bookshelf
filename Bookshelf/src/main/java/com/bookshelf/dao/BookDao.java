package com.bookshelf.dao;

import com.bookshelf.beans.Book;
import com.bookshelf.connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    public List<Book> searchBooks(String title, String author, String genre, String availability) {
        List<Book> books = new ArrayList<>();

        // Build query dynamically based on parameters
        StringBuilder queryBuilder = new StringBuilder();
        queryBuilder.append("SELECT lb.library_book_id, b.title, b.author, b.isbn, b.published_year, g.genre_name AS genre_name, ");
        queryBuilder.append("lb.is_available ");
        queryBuilder.append("FROM bookshelf_library_book lb ");
        queryBuilder.append("INNER JOIN bookshelf_book b ON lb.book_id = b.book_id ");
        queryBuilder.append("LEFT JOIN bookshelf_genre g ON b.genre_id = g.genre_id ");
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
                books.add(new Book(
                        rs.getString("library_book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getInt("published_year"),
                        rs.getString("genre_name"),
                        rs.getInt("is_available") == 1 ? "Available" : "Not Available"
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
