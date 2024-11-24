package com.bookshelf.dao;

import com.bookshelf.beans.Book;
import com.bookshelf.connection.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookDao {

    // Method for searching books based on filters
    public List<Book> searchBooks(String title, String author, String genre, String availability) {
        List<Book> books = new ArrayList<>();

        // SQL query with debug statements
        String query = "SELECT b.book_id, b.title, b.author, b.isbn, b.published_year, g.genre_name AS genre_name " +
                       "FROM bookshelf_book b " +
                       "LEFT JOIN bookshelf_genre g ON b.genre_id = g.genre_id " +
                       "WHERE (? IS NULL OR LOWER(b.title) LIKE LOWER(?)) " +
                       "AND (? IS NULL OR LOWER(b.author) LIKE LOWER(?)) " +
                       "AND (? = 'All Genres' OR g.genre_id = ?)";

        try (Connection conn = DBConnection.getDBInstance();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            // Set parameters for the query
            stmt.setString(1, title == null || title.isEmpty() ? null : "%" + title + "%");
            stmt.setString(2, title == null || title.isEmpty() ? null : "%" + title + "%");
            stmt.setString(3, author == null || author.isEmpty() ? null : "%" + author + "%");
            stmt.setString(4, author == null || author.isEmpty() ? null : "%" + author + "%");
            stmt.setString(5, genre);
            stmt.setInt(6, genre.equals("All Genres") ? -1 : Integer.parseInt(genre));

            // Debugging: Print query and parameters
            System.out.println("Executing SQL Query: " + query);
            System.out.println("Title Parameter: " + (title == null || title.isEmpty() ? "NULL" : "%" + title + "%"));
            System.out.println("Author Parameter: " + (author == null || author.isEmpty() ? "NULL" : "%" + author + "%"));
            System.out.println("Genre Parameter: " + genre);
            System.out.println("Genre ID Parameter: " + (genre.equals("All Genres") ? "-1" : genre));

            // Execute the query
            ResultSet rs = stmt.executeQuery();

            // Process the result set
            while (rs.next()) {
                books.add(new Book(
                        rs.getString("book_id"),
                        rs.getString("title"),
                        rs.getString("author"),
                        rs.getString("isbn"),
                        rs.getInt("published_year"),
                        rs.getString("genre_name")
                ));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return books;
    }
}
