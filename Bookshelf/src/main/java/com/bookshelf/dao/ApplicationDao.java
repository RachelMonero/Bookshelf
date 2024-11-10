package com.bookshelf.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bookshelf.connection.DBConnection;
import com.bookshelf.connection.DBUtil;



public class ApplicationDao {
    private static ApplicationDao dao;
    public static final String DB_NAME = "bookshelf";
    public static final String USERS_TABLE = "bookshelf_user";
    public static final String ROLES_TABLE = "bookshelf_role";
    public static final String USER_ROLE_TABLE = "bookshelf_user_role";
    public static final String VERIFICATION_TABLE = "bookshelf_verification";
    public static final String BOOKS_TABLE = "bookshelf_book";
    public static final String LIBRARY_TABLE = "bookshelf_library";
    public static final String LIBRARY_BOOK_TABLE = "bookshelf_library_book";
    public static final String GENRE_TABLE = "bookshelf_genre";
    public static final String RESERVATIONS_TABLE = "bookshelf_reservation";
    public static final String ADDRESS_TABLE = "bookshelf_address";

    private ApplicationDao() {}

    public static synchronized ApplicationDao getDao() {
        if (dao == null) dao = new ApplicationDao();
        return dao;
    }

    public static void createDatabase() {
        try (
            Connection conn = DBConnection.getDBInstance();
            ResultSet resultSet = conn.getMetaData().getCatalogs();
            Statement stmt = conn.createStatement();
        ) {
            if (!dbExists(DB_NAME, resultSet)) {
                System.out.print("Creating DB...");
                String sql = "CREATE DATABASE IF NOT EXISTS " + DB_NAME + " DEFAULT CHARACTER SET utf8 COLLATE utf8_unicode_ci";
                stmt.executeUpdate(sql);
                System.out.println("Created DB");
            }
            DBUtil.setConnStr();
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createGenreTable() {
        try (
            Connection conn = DBConnection.getDBInstance();
            Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, GENRE_TABLE)) {
                System.out.print("Creating Genre Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + GENRE_TABLE + " ("
                        + "genre_id INT NOT NULL AUTO_INCREMENT PRIMARY KEY, "
                        + "genre_name VARCHAR(45) NOT NULL UNIQUE)";
                stmt.executeUpdate(sql);
                System.out.println("Created Genre Table");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createRoleTable() {
        try (
            Connection conn = DBConnection.getDBInstance();
            Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, ROLES_TABLE)) {
                System.out.print("Creating Role Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + ROLES_TABLE + " ("
                        + "role_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                        + "role_name VARCHAR(25) NOT NULL UNIQUE, "
                        + "description VARCHAR(255) DEFAULT NULL)";
                stmt.executeUpdate(sql);
                insertDefaultRoles(conn);
                System.out.println("Created Role Table");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void createAddressTable() {
        try (
            Connection conn = DBConnection.getDBInstance();
            Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, ADDRESS_TABLE)) {
                System.out.print("Creating Address Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + ADDRESS_TABLE + " ("
                        + "address_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                        + "address VARCHAR(50), "
                        + "city VARCHAR(20), "
                        + "province VARCHAR(2), "
                        + "country VARCHAR(10), "
                        + "postal_code VARCHAR(6))";
                stmt.executeUpdate(sql);
                System.out.println("Created Address Table");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public void createUserTable() {
        try (
            Connection conn = DBConnection.getDBInstance();
            Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, USERS_TABLE)) {
                System.out.print("Creating User Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + USERS_TABLE + " ("
                        + "user_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                        + "email VARCHAR(255) NOT NULL UNIQUE, "
                        + "username VARCHAR(30) NOT NULL UNIQUE, "
                        + "password VARCHAR(128) NOT NULL, "
                        + "first_name VARCHAR(35) NOT NULL, "
                        + "last_name VARCHAR(35) NOT NULL, "
                        + "address_id VARCHAR(36) , "
                        + "is_verified TINYINT NOT NULL DEFAULT 0, "
                        + "FOREIGN KEY (address_id) REFERENCES " + ADDRESS_TABLE + "(address_id) ON DELETE SET NULL)";
                stmt.executeUpdate(sql);
                System.out.println("Created User Table");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    public void createUserRoleTable() {
        try (
            Connection conn = DBConnection.getDBInstance();
            Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, USER_ROLE_TABLE)) {
                System.out.print("Creating UserRole Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + USER_ROLE_TABLE + " ("
                        + "user_role_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                		+ "user_id VARCHAR(36) NOT NULL, "
                        + "role_id VARCHAR(36) NOT NULL, "
                		+ "assigned_date DATETIME NOT NULL, "
                        + "status VARCHAR(15) NOT NULL, "
                        + "FOREIGN KEY (user_id) REFERENCES " + USERS_TABLE + "(user_id) ON DELETE CASCADE, "
                        + "FOREIGN KEY (role_id) REFERENCES " + ROLES_TABLE + "(role_id) ON DELETE CASCADE)";
                stmt.executeUpdate(sql);
                System.out.println("Created UserRole Table");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createVerificationTable() {
        try (
            Connection conn = DBConnection.getDBInstance();
            Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, VERIFICATION_TABLE)) {
                System.out.print("Creating Verification Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + VERIFICATION_TABLE + " ("
                        + "verification_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                        + "user_id VARCHAR(36) NOT NULL, "
                        + "verification_type VARCHAR(20) NOT NULL, "
                        + "verification_code VARCHAR(36) NOT NULL, "
                        + "created_at DATETIME NOT NULL, "
                        + "status VARCHAR(15) NOT NULL, "
                        + "FOREIGN KEY (user_id) REFERENCES " + USERS_TABLE + "(user_id) ON DELETE CASCADE)";
                stmt.executeUpdate(sql);
                System.out.println("Created Verification Table");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createBooksTable() {
        try (
            Connection conn = DBConnection.getDBInstance();
            Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, BOOKS_TABLE)) {
                System.out.print("Creating Books Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + BOOKS_TABLE + " ("
                        + "book_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                        + "title VARCHAR(255) NOT NULL, "
                        + "author VARCHAR(100) NOT NULL, "
                        + "isbn VARCHAR(13) UNIQUE, "
                        + "published_year YEAR DEFAULT NULL, "
                        + "genre_id INT NOT NULL, "
                        + "FOREIGN KEY (genre_id) REFERENCES " + GENRE_TABLE + "(genre_id) ON DELETE CASCADE)";
                stmt.executeUpdate(sql);
                System.out.println("Created Books Table");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createLibraryTable() {
        try (
            Connection conn = DBConnection.getDBInstance();
            Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, LIBRARY_TABLE)) {
                System.out.print("Creating Library Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + LIBRARY_TABLE + " ("
                        + "library_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                        + "library_name VARCHAR(45) NOT NULL, "
                        + "library_address_id VARCHAR(36) , "
                        + "library_email VARCHAR(255) DEFAULT NULL, "
                        + "library_phone VARCHAR(14) DEFAULT NULL, "
                        + "librarian_id VARCHAR(36), "
                        + "FOREIGN KEY (librarian_id) REFERENCES " + USERS_TABLE + "(user_id) ON DELETE SET NULL, "
                        + "FOREIGN KEY (library_address_id) REFERENCES " + ADDRESS_TABLE + "(address_id) ON DELETE SET NULL)";
                stmt.executeUpdate(sql);
                System.out.println("Created Library Table");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void createLibraryBookTable() {
        try (
            Connection conn = DBConnection.getDBInstance();
            Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, LIBRARY_BOOK_TABLE)) {
                System.out.print("Creating LibraryBook Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + LIBRARY_BOOK_TABLE + " ("
                        + "library_book_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                        + "library_id VARCHAR(36) NOT NULL, "
                        + "book_id VARCHAR(36) NOT NULL, "
                        + "added_date DATETIME NOT NULL, "
                        + "is_available TINYINT NOT NULL, "
                        + "FOREIGN KEY (library_id) REFERENCES " + LIBRARY_TABLE + "(library_id) ON DELETE CASCADE, "
                        + "FOREIGN KEY (book_id) REFERENCES " + BOOKS_TABLE + "(book_id) ON DELETE CASCADE)";
                stmt.executeUpdate(sql);
                System.out.println("Created LibraryBook Table");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    

    public void createReservationsTable() {
        try (
            Connection conn = DBConnection.getDBInstance();
            Statement stmt = conn.createStatement();
        ) {
            if (!tableExists(conn, RESERVATIONS_TABLE)) {
                System.out.print("Creating Reservations Table...");
                String sql = "CREATE TABLE IF NOT EXISTS " + RESERVATIONS_TABLE + " ("
                        + "reservation_id VARCHAR(36) NOT NULL PRIMARY KEY, "
                        + "user_id VARCHAR(36) NOT NULL, "
                        + "library_book_id VARCHAR(36) NOT NULL, "
                        + "reserved_date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP, "
                        + "status VARCHAR(15) NOT NULL, "
                        + "FOREIGN KEY (user_id) REFERENCES " + USERS_TABLE + "(user_id) ON DELETE CASCADE, "
                        + "FOREIGN KEY (library_book_id) REFERENCES " + LIBRARY_BOOK_TABLE + "(library_book_id) ON DELETE CASCADE)";
                stmt.executeUpdate(sql);
                System.out.println("Created Reservations Table");
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    // Default Role data
    private void insertDefaultRoles(Connection conn) throws SQLException {
        String checkRoleSql = "SELECT COUNT(*) FROM " + ROLES_TABLE + " WHERE role_name = ?";
        String insertRoleSql = "INSERT INTO " + ROLES_TABLE + " (role_id, role_name, description) VALUES (?, ?, ?)";

        try (
            PreparedStatement checkStmt = conn.prepareStatement(checkRoleSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertRoleSql);
        ) {
            // Array of default roles to insert
            String[][] defaultRoles = {
                {"35ea87d3-7df2-4cf3-9e4c-d326027cbe95", "sysAdmin", "Administrator role with full access"},
                {"f69581bb-5f5b-4950-bcaa-3a8c5a8db3e5", "librarian", "Librarian role with access to manage library items"},
                {"e45cfed3-ed61-49ba-9215-2c44ee23bdae", "member", "Regular user role with access to view and reserve books"}
            };

            for (String[] role : defaultRoles) {
                // Check if role already exists
                checkStmt.setString(1, role[1]);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        // Role does not exist; Insert default roles
                        insertStmt.setString(1, role[0]); // role_id
                        insertStmt.setString(2, role[1]); // role_name
                        insertStmt.setString(3, role[2]); // description
                        insertStmt.executeUpdate();
                        System.out.println("Inserted default role: " + role[1]);
                    }
                }
            }
        }
    }


    private static boolean dbExists(String dbName, ResultSet resultSet) throws SQLException {
        while (resultSet.next()) {
            if (resultSet.getString(1).equals(dbName)) return true;
        }
        return false;
    }

    public boolean tableExists(Connection conn, String tableName) throws SQLException {
        return conn.getMetaData().getTables(null, null, tableName, new String[]{"TABLE"}).next();
    }
}

