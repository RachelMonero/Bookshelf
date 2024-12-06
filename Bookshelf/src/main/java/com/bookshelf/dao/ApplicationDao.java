package com.bookshelf.dao;

import java.sql.Timestamp;
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
                insertDefaultGenre(conn);// insert default genres
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
                insertDefaultRoles(conn); // insert default roles
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
                insertDefaultAddress(conn); // insert default addresses
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
                insertDefaultUser(conn); // insert default Users (Admin, Ottawa Librarian,Toronto Librarian)
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
                insertDefaultUserRole(conn); // insert default userRoles for Admin and Librarians
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
                        + "verification_id VARCHAR(36) NOT NULL DEFAULT (UUID()) PRIMARY KEY, "
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
                insertDefaultBook(conn); // insert default books
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
                insertDefaultLibrary(conn); //insert default libraries
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
                insertDefaultLibraryBook(conn);
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
    
    /* Insert Default Data */
    
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
    
    // Default Genre data
    private void insertDefaultGenre(Connection conn) throws SQLException {
    	String checkGenreSql = "SELECT COUNT(*) FROM " + GENRE_TABLE + " WHERE genre_name = ?";
        String insertGenreSql = "INSERT INTO " + GENRE_TABLE + " (genre_name) VALUES (?)";

        try (
        	PreparedStatement checkStmt = conn.prepareStatement(checkGenreSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertGenreSql);
        ) {

            String[] defaultGenres = {
            		"adventure",
            	    "cookbook",
            	    "economics",
            	    "fantasy",
            	    "health",
            	    "history",
            	    "horror",
            	    "mystery",
            	    "novel",
            	    "romance",
            	    "science",
            	    "science fiction",
            	    "thriller",
            	    "travel",
            	    "memoir"
                };

            for (String genre_name : defaultGenres) {
            	checkStmt.setString(1, genre_name);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        // Role does not exist; Insert default genres
                        insertStmt.setString(1, genre_name);
                        insertStmt.executeUpdate();

                    }
                }
            }
        }
    }
    // Default Book data
    private void insertDefaultBook(Connection conn) throws SQLException {
    	String checkISBNSql = "SELECT COUNT(*) FROM " + BOOKS_TABLE + " WHERE isbn = ?";
        String insertBookSql = "INSERT INTO " + BOOKS_TABLE + " (book_id, title, author, isbn, published_year, genre_id) VALUES (?,?,?,?,?,?)";

        try (
        	PreparedStatement checkStmt = conn.prepareStatement(checkISBNSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertBookSql);
        ) {

        	String[][] defaultBooks = {
        			{"6369172f-9b5c-429c-b734-bed65324e2e8","The Notebook","Nicholas Sparks","9780446676090","1996","10"},
        			{"c9527ca4-684b-42e0-b95d-2d193cf25d01","Me Before You","Jojo Moyes","9780143124541","2012","10"},
        			{"044d6fac-ac31-4b1f-b5a3-9169b767f5a4","It Ends with Us","Colleen Hoover","9781501110368","2016","10"},
        			{"ab5bf3e8-ba18-42d6-92e7-53c903d072e1","Project Hail Mary","Andy Weir","9780593135204","2021","12"},
        			{"dcc71670-c472-4840-988f-725dc6018140","The Ministry for the Future","Kim Stanley Robinson","9780316300131","2020","12"},
        			{"cb5d09ef-5692-4faa-a425-01f826003c93","To Sleep in a Sea of Stars","Christopher Paolini","9781250762849","2020","12"},
        			{"9c7fa7ec-8e67-4d0d-8de7-b15fea48561b","Goliath","Tochi Onyebuchi","9781250782953","2022","12"},
        			{"9be2892f-9bd1-45cb-84b9-b76372057668","Humankind: A Hopeful History","Rutger Bregman","9780316418539","2020","6"},
        			{"143ea9e6-c077-4114-8afa-dd992daf8649","The Dawn of Everything: A New History of Humanity","David Graeber and David Wengrow","9780374157357","2021","6"},
        			{"5fb2411c-0278-44a5-a74d-ecf149c92e26","Into the Wild","Jon Krakauer","9780385486804","1996","1"},
        			{"9e2fc376-e597-44f6-a2d5-ffb8400052ba","Salt, Fat, Acid, Heat: Mastering the Elements of Good Cooking","Samin Nosrat","9781476753836","2017","2"},
        			{"509a189a-7cae-47e1-a9b1-eb6f2a38bc7d","Capital in the Twenty-First Century","Thomas Piketty","9780674430006","2013","3"},
        			{"8262f5b7-9bb5-4ccf-a8d0-c458e3526496","A Game of Thrones (A Song of Ice and Fire, Book 1)","George R.R. Martin","9780553103540","1996","4"},
        			{"7c022414-7bb2-4790-b3fe-d1424a1880d0","Sapiens: A Brief History of Humankind","Yuval Noah Harari","9780062316097","2011","6"},
        			{"c69b7843-028f-4e42-917b-e04f54eb5e47","The Shining","Stephen King","9780385121675","1977","7"},
        			{"b2f7283a-c369-41c9-8fe2-4095f82b3dd6","The Girl with the Dragon Tattoo","Stieg Larsson","9780307269751","2005","8"},
        			{"39a90f56-6945-45f2-a6f4-b48589642b93","To Kill a Mockingbird","Harper Lee","9780061120084","1960","9"},
        			{"5c7c8d72-93dd-4ccd-9634-719f3e1b82f5","A Brief History of Time","Stephen Hawking","9780553380163","1988","11"},
        			{"87580f51-c3ef-4f67-a1cc-a4a1eb07578f","Gone Girl","Gillian Flynn","9780307588371","2012","13"},
        			{"6529e05a-43ec-4397-ab60-6aa1f93fb4ca","Harry Potter and the Sorcerer's Stone (first book)","J.K. Rowling","9780439708180","1997","4"},
        			{"81c8650d-7e29-44b0-91c3-99b7325903d8","Pretty Little Liars (first book)","Sara Shepard","9780060887322","2006","8"},
        			{"ac06cc43-35d2-4249-9405-c70a5c615271","And Then There Were None","Agatha Christie","9780062073488","1939","13"},
        			{"b12edf9e-dc81-4c5f-bab1-be73e7591616","All the Beauty in the World: The Metropolitan Museum of Art and Me ","Patrick Bringley","9781982163307","2023","15"},
                };

        	for (String[] book : defaultBooks) {
                // Check if book already exists
                checkStmt.setString(1, book[3]);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        // Book does not exist; Insert default books
                        insertStmt.setString(1, book[0]); // book_id
                        insertStmt.setString(2, book[1]); // title
                        insertStmt.setString(3, book[2]); // author
                        insertStmt.setString(4, book[3]); // isbn
                        insertStmt.setString(5, book[4]); // published_year
                        insertStmt.setInt(6, Integer.parseInt(book[5])); // genre_id
                        System.out.println("Inserted default book: " + book[1]);
                                            
                        
                        insertStmt.executeUpdate();

                    }
                }
            }
        } catch (SQLException e) {
            // Log or print the error message for debugging
            System.err.println("Error while inserting book: " + e.getMessage());
            throw e;  // Re-throw the exception to handle it further up the stack
        }

    }
    
    // Default Address data for Library
    private void insertDefaultAddress(Connection conn) throws SQLException {
    	String checkAddressSql = "SELECT COUNT(*) FROM " + ADDRESS_TABLE + " WHERE address_id = ?";
        String insertAddressSql = "INSERT INTO " + ADDRESS_TABLE + " (Address_id, address, city, province, country, postal_code) VALUES (?,?,?,?,?,?)";

        try (
        	PreparedStatement checkStmt = conn.prepareStatement(checkAddressSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertAddressSql);
        ) {

        	String[][] defaultAddresses = {
        			{"8c167ec7-4227-4d8d-8af5-0bc47a801ee2","123 Spark Street","Ottawa","ON","CA","K1PP1K"},
        			{"6db79129-b19e-4c47-972c-edbd0ae38fb2","45 Avenue","Toronto","ON","CA","M5JJ5M"},
        			{"e1cd2ea2-63a4-485f-8610-02b480967ba1","6 King Street","Kitchener","ON","CA","N2GG2N"},
        			{"46fd24c2-ddec-46e7-9ce7-5e463f8ccb2f","78 Oxford Street","London","ON","CA","N5VV5N"},
        			{"629c8672-6afb-4319-b425-fcdf24e6d47f","9 Adam Drive","Barrie","ON","CA","L4MM4L"},
        			
        			{"5ac673d0-b37d-11ef-814d-325096b39f47","109 Spark Street","Ottawa","ON","CA","K1PP1K"}, // Admin user adress
        			{"5ac675b0-b37d-11ef-b64c-325096b39f47","24 Kirkwood Avenue","Ottawa","ON","CA","K1BB1K"}, // Librarian user(Ottawa) address
        			{"5ac67696-b37d-11ef-a2ad-325096b39f47","53 Avenue","Toronto","ON","CA","M5JJ5M"}, // Librarian user(Toronto) address
        			
                };

        	for (String[] address : defaultAddresses) {
                // Check if address already exists
                checkStmt.setString(1, address[0]);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        // Address does not exist; Insert default address
                        insertStmt.setString(1, address[0]); // address_id
                        insertStmt.setString(2, address[1]); // address
                        insertStmt.setString(3, address[2]); // city
                        insertStmt.setString(4, address[3]); // province
                        insertStmt.setString(5, address[4]); // country
                        insertStmt.setString(6, address[5]); // postal_code
                                            
                        
                        insertStmt.executeUpdate();



                    }
                }
            }
        }
    }  
    
 
    // Default Admin & Librarian User data 
    private void insertDefaultUser(Connection conn) throws SQLException {
    	String checkUserSql = "SELECT COUNT(*) FROM " + USERS_TABLE + " WHERE email = ?";
        String insertUserSql = "INSERT INTO " + USERS_TABLE + " (user_id, email, username, password, first_name, last_name, address_id, is_verified) VALUES (?,?,?,?,?,?,?,?)";

        try (
        	PreparedStatement checkStmt = conn.prepareStatement(checkUserSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertUserSql);
        ) {

        	String[][] defaultUsers = {
        			//Admin
        			{"5ac66fc0-b37d-11ef-bf20-325096b39f47","admin@email.com","admin","password ","sys","admin","5ac673d0-b37d-11ef-814d-325096b39f47","true"},
        			//Ottawa Librarian
        			{"5ac67524-b37d-11ef-a678-325096b39f47","ottawa_lib@email.com","ottawa_lib","password ","Ottawa","Librarian","5ac675b0-b37d-11ef-b64c-325096b39f47","true"},
                    //Toronto Librarian
        			{"5ac67628-b37d-11ef-9800-325096b39f47","toronto_lib@email.com","toronto_lib","password ","Toronto","Librarian","5ac67696-b37d-11ef-a2ad-325096b39f47","true"},
        	};

        	for (String[] user : defaultUsers) {
                checkStmt.setString(1, user[1]);
                
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
         
                        insertStmt.setString(1, user[0]); // user_id
                        insertStmt.setString(2, user[1]); // email
                        insertStmt.setString(3, user[2]); // username
                        insertStmt.setString(4, user[3]); // password
                        insertStmt.setString(5, user[4]); // first_name
                        insertStmt.setString(6, user[5]); // last_name
                        insertStmt.setString(7, user[6]); // address_id
                        
                        boolean isVerified = Boolean.parseBoolean(user[7]);
                        insertStmt.setBoolean(8, isVerified); // is_verified
                                                                        
                        insertStmt.executeUpdate();



                    }
                }
            }
        }
    }
    
 // Default User role for Admin and Librarians
    private void insertDefaultUserRole(Connection conn) throws SQLException {
    	String checkUserRoleSql = "SELECT COUNT(*) FROM " + USER_ROLE_TABLE + " WHERE user_id = ?";
        String insertUserRoleSql = "INSERT INTO " + USER_ROLE_TABLE + " (user_role_id, user_id, role_id, assigned_date, status) VALUES (?,?,?,?,?)";

        try (
        	PreparedStatement checkStmt = conn.prepareStatement(checkUserRoleSql);
            PreparedStatement insertStmt = conn.prepareStatement(insertUserRoleSql);
        ) {

        	String[][] defaultUserRole = {
			
        			{"694e6342-b380-11ef-a3c7-325096b39f47","5ac66fc0-b37d-11ef-bf20-325096b39f47","35ea87d3-7df2-4cf3-9e4c-d326027cbe95","Active"}, // Admin user adress
        			{"70c5c1ba-b380-11ef-be12-325096b39f47","5ac67524-b37d-11ef-a678-325096b39f47","f69581bb-5f5b-4950-bcaa-3a8c5a8db3e5","Active"}, // Librarian user(Ottawa) address
        			{"7555dbfc-b380-11ef-aa12-325096b39f47","5ac67628-b37d-11ef-9800-325096b39f47","f69581bb-5f5b-4950-bcaa-3a8c5a8db3e5","Active"}, // Librarian user(Toronto) address
        			
                };

        	for (String[] userRole : defaultUserRole) {

                checkStmt.setString(1, userRole[1]);
                
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) == 0) {
                        // not exist; Insert default userRole
                    	
                        insertStmt.setString(1, userRole[0]); // user_role_id
                        insertStmt.setString(2, userRole[1]); // user_id
                        insertStmt.setString(3, userRole[2]); // role_id
                        Timestamp added_date = new Timestamp(System.currentTimeMillis());
                        insertStmt.setTimestamp(4, added_date);
                        insertStmt.setString(5, userRole[3]); // status

                                            
                        
                        insertStmt.executeUpdate();



                    }
                }
            }
        }
    }  
    
    
    
    // Default Library data
       private void insertDefaultLibrary(Connection conn) throws SQLException {
       	String checkLibrarySql = "SELECT COUNT(*) FROM " + LIBRARY_TABLE + " WHERE library_name = ?";
           String insertLibrarySql = "INSERT INTO " + LIBRARY_TABLE + " (library_id, library_name, library_address_id, library_email, library_phone, librarian_id) VALUES (?,?,?,?,?,?)";

           try (
           	PreparedStatement checkStmt = conn.prepareStatement(checkLibrarySql);
               PreparedStatement insertStmt = conn.prepareStatement(insertLibrarySql);
           ) {

           	String[][] defaultLibraries = {
           			{"8b1b621c-c3f6-4e8a-b0fb-5a75dc4bcb73","Ottawa Library","8c167ec7-4227-4d8d-8af5-0bc47a801ee2"," ottawa.libarary@email.com ","613-123-4567","5ac67524-b37d-11ef-a678-325096b39f47"},
           			{"1ac48991-dc33-495f-a80e-e7bc098d7e83","Toronto Library","6db79129-b19e-4c47-972c-edbd0ae38fb2","toronto.libarary@email.com",null,"5ac67628-b37d-11ef-9800-325096b39f47"},
           			{"046476ec-24ec-49c7-b8d4-02cb63d11af7","Kitchener Library","e1cd2ea2-63a4-485f-8610-02b480967ba1","kitchener.library@email.com", null,null},
           			{"d634c1d1-ac45-46cb-8e6b-2eda466ec305","London Library","46fd24c2-ddec-46e7-9ce7-5e463f8ccb2f","london.library@email.com","516-789-0000",null},
           			{"a8bf7002-81cb-4a74-a065-002d93fc8e92","Barrie Library","629c8672-6afb-4319-b425-fcdf24e6d47f","barrie.libarary@email.com","705-432-1234",null},
           			
                   };

           	for (String[] library : defaultLibraries) {
                   // Check if library already exists
                   checkStmt.setString(1, library[1]);
                   try (ResultSet rs = checkStmt.executeQuery()) {
                       if (rs.next() && rs.getInt(1) == 0) {
                           // library does not exist; Insert default library
                           insertStmt.setString(1, library[0]); // library_id
                           insertStmt.setString(2, library[1]); // library_name
                           insertStmt.setString(3, library[2]); // library_address_id
                           insertStmt.setString(4, library[3]); // library_email
                           insertStmt.setString(5, library[4]); // library_phone
                           insertStmt.setString(6, library[5]); // librarian_id
                                                                           
                           insertStmt.executeUpdate();



                       }
                   }
               }
           }
       }
       
       
       // Default Library Book data
       private void insertDefaultLibraryBook(Connection conn) throws SQLException {
       	String checkLibraryBookSql = "SELECT COUNT(*) FROM " + LIBRARY_BOOK_TABLE + " WHERE library_id = ? AND book_id= ? ";
           String insertLibraryBookSql = "INSERT INTO " + LIBRARY_BOOK_TABLE + " (library_book_id, library_id, book_id, added_date, is_available) VALUES (?,?,?,?,?)";

           try (
           	PreparedStatement checkStmt = conn.prepareStatement(checkLibraryBookSql);
               PreparedStatement insertStmt = conn.prepareStatement(insertLibraryBookSql);
           ) {

           	String[][] defaultLibraryBooks = {
           			{"535f8cfa-540c-4d86-949c-375fbc9b45a2", "8b1b621c-c3f6-4e8a-b0fb-5a75dc4bcb73", "7c022414-7bb2-4790-b3fe-d1424a1880d0","true"}, //Ottawa Library Inventory
           			{"c71b0221-7017-4c3e-bbee-71ca1cb79069", "8b1b621c-c3f6-4e8a-b0fb-5a75dc4bcb73", "9e2fc376-e597-44f6-a2d5-ffb8400052ba","false"},
           			{"b7ee9148-59bb-46b8-8720-c811c2eca610", "8b1b621c-c3f6-4e8a-b0fb-5a75dc4bcb73", "6369172f-9b5c-429c-b734-bed65324e2e8","true"},
           			{"e70beaf1-eff2-46cb-9770-0d9d9509cd86", "8b1b621c-c3f6-4e8a-b0fb-5a75dc4bcb73", "c9527ca4-684b-42e0-b95d-2d193cf25d01","true"},
           			{"ba605c1e-dc59-4234-96cd-ddc69f5efb5f", "8b1b621c-c3f6-4e8a-b0fb-5a75dc4bcb73", "9be2892f-9bd1-45cb-84b9-b76372057668","false"},
           			{"b8115c45-92eb-4114-845f-e43399ec1982", "8b1b621c-c3f6-4e8a-b0fb-5a75dc4bcb73", "b12edf9e-dc81-4c5f-bab1-be73e7591616","true"},
           			{"880964ba-6357-43fd-9c54-cb26b8d1b925", "1ac48991-dc33-495f-a80e-e7bc098d7e83", "143ea9e6-c077-4114-8afa-dd992daf8649","false"},//Toronto Library Inventory
           			{"c11f0b93-fa20-430e-a895-90ad26d98051", "1ac48991-dc33-495f-a80e-e7bc098d7e83", "6369172f-9b5c-429c-b734-bed65324e2e8","true"},
           			{"19583954-b61f-48dd-90bc-3a91a9e8d66d", "1ac48991-dc33-495f-a80e-e7bc098d7e83", "ac06cc43-35d2-4249-9405-c70a5c615271","ture"},
           			{"88d7f4b1-6637-4cdd-b77c-7fa455d4dcf2", "1ac48991-dc33-495f-a80e-e7bc098d7e83", "044d6fac-ac31-4b1f-b5a3-9169b767f5a4","ture"},
           			{"f044d87e-d40c-4c92-9b3f-6f6e028334f2", "1ac48991-dc33-495f-a80e-e7bc098d7e83", "5fb2411c-0278-44a5-a74d-ecf149c92e26","false"},
           			{"5b71a231-c745-4ced-9fb4-3467956a30d8", "1ac48991-dc33-495f-a80e-e7bc098d7e83", "8262f5b7-9bb5-4ccf-a8d0-c458e3526496","true"},
           			{"4bcc4e3c-3ca9-4871-b352-e56781601c6a", "1ac48991-dc33-495f-a80e-e7bc098d7e83", "b12edf9e-dc81-4c5f-bab1-be73e7591616","false"},
           			{"207d23b8-1631-4723-ad0b-6bad2e73135b", "046476ec-24ec-49c7-b8d4-02cb63d11af7", "7c022414-7bb2-4790-b3fe-d1424a1880d0","false"},//Kitchener Library Inventory
           			{"5bfcab69-9498-4332-9d09-62fe59a63550", "046476ec-24ec-49c7-b8d4-02cb63d11af7", "143ea9e6-c077-4114-8afa-dd992daf8649","true"},
           			{"d1760e19-b957-4191-99d5-c3ae6a5bdee2", "046476ec-24ec-49c7-b8d4-02cb63d11af7", "c9527ca4-684b-42e0-b95d-2d193cf25d01","true"},
           			{"d37bf0cf-0a9c-4099-bfa9-b4b838ae1a26", "046476ec-24ec-49c7-b8d4-02cb63d11af7", "9be2892f-9bd1-45cb-84b9-b76372057668","false"},
           			{"81812f26-26fb-47e3-9722-dfeeba28c45d", "046476ec-24ec-49c7-b8d4-02cb63d11af7", "8262f5b7-9bb5-4ccf-a8d0-c458e3526496","true"},
           			{"0d24070d-1c16-4cce-8c91-d24425bcbbfd", "046476ec-24ec-49c7-b8d4-02cb63d11af7", "b12edf9e-dc81-4c5f-bab1-be73e7591616","true"},
           			{"99353241-15da-4ac2-8fdd-2845d3508d28", "d634c1d1-ac45-46cb-8e6b-2eda466ec305", "9e2fc376-e597-44f6-a2d5-ffb8400052ba","true"},//London Library Inventory
           			{"d4f3f9e7-151f-4d4c-9696-9e658aab3ce4", "d634c1d1-ac45-46cb-8e6b-2eda466ec305", "6369172f-9b5c-429c-b734-bed65324e2e8","true"},
           			{"b5682be0-8c72-4722-a206-96c9677ab791", "d634c1d1-ac45-46cb-8e6b-2eda466ec305", "044d6fac-ac31-4b1f-b5a3-9169b767f5a4","true"},
           			{"ac7ffcbc-ba83-4bc6-b0e9-c8c42d7eb66e", "d634c1d1-ac45-46cb-8e6b-2eda466ec305", "5fb2411c-0278-44a5-a74d-ecf149c92e26","false"},
           			{"aeeeec04-ccb6-4d98-b4e7-8721e87d9d4e", "d634c1d1-ac45-46cb-8e6b-2eda466ec305", "9c7fa7ec-8e67-4d0d-8de7-b15fea48561b","false"},
           			{"fd9ee4ca-9127-417c-a01a-c1da2e2a8d92", "a8bf7002-81cb-4a74-a065-002d93fc8e92", "7c022414-7bb2-4790-b3fe-d1424a1880d0","false"},//Barrie Library Inventory
           			{"c2ee7b85-c599-42b9-8cfc-216f77d40233", "a8bf7002-81cb-4a74-a065-002d93fc8e92", "9e2fc376-e597-44f6-a2d5-ffb8400052ba","true"},
           			{"bce81799-76fa-473a-a805-6f8547ba8c39", "a8bf7002-81cb-4a74-a065-002d93fc8e92", "ac06cc43-35d2-4249-9405-c70a5c615271","false"},
           			{"263cbf6a-dbc5-4cc0-92af-ada51da85cf8", "a8bf7002-81cb-4a74-a065-002d93fc8e92", "9be2892f-9bd1-45cb-84b9-b76372057668","true"},
           			{"a365f9c6-2d0b-4132-b2b2-98488d4ce852", "a8bf7002-81cb-4a74-a065-002d93fc8e92", "39a90f56-6945-45f2-a6f4-b48589642b93","true"},
                   };

           	for (String[] libraryBook : defaultLibraryBooks) {
                   // Check if book already exists in the library inventory
                   checkStmt.setString(1, libraryBook[1]);
                   checkStmt.setString(2, libraryBook[2]);
                   try (ResultSet rs = checkStmt.executeQuery()) {
                       if (rs.next() && rs.getInt(1) == 0) {
                           // book does not exists in the library, Insert default library book
                           insertStmt.setString(1, libraryBook[0]); // library_book_id
                           insertStmt.setString(2, libraryBook[1]); // library_id
                           insertStmt.setString(3, libraryBook[2]); // book_id

                           Timestamp added_date = new Timestamp(System.currentTimeMillis());
                           insertStmt.setTimestamp(4, added_date);
                           boolean isAvailable = Boolean.parseBoolean(libraryBook[3]);
                           insertStmt.setBoolean(5, isAvailable);; // is_available
                                                                           
                           insertStmt.executeUpdate();



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

