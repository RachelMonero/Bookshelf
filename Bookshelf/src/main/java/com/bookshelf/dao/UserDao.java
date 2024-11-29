package com.bookshelf.dao;

import com.bookshelf.beans.User;
import com.bookshelf.connection.DBConnection;
import com.bookshelf.connection.DBUtil;
import com.bookshelf.libs.TokenGenerator;
import com.bookshelf.libs.UUIDGenerator;
import com.bookshelf.services.JavaEmailService;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;

import javax.mail.MessagingException;

public class UserDao {

    // Create user
	public static String createUser(String email, String username, String password, String first_name, String last_name, String address_id) {
        UUIDGenerator uuidGenerator = new UUIDGenerator();
        // Create user_id
        String user_id = uuidGenerator.generateUUID();
        Boolean is_verified = false;

        try (Connection connection = DBConnection.getDBInstance()) {
            String create_user_sql = "INSERT INTO bookshelf_user values(?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStmt = connection.prepareStatement(create_user_sql, PreparedStatement.RETURN_GENERATED_KEYS);
            preparedStmt.setString(1, user_id);
            preparedStmt.setString(2, email);
            preparedStmt.setString(3, username);
            preparedStmt.setString(4, password);
            preparedStmt.setString(5, first_name);
            preparedStmt.setString(6, last_name);
            preparedStmt.setString(7, address_id);
            preparedStmt.setBoolean(8, is_verified);

            int rowsInserted = preparedStmt.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("User is created successfully.");
                return user_id;
            }

            ResultSet keys = preparedStmt.getGeneratedKeys();
            if (keys.next()) {
                return keys.getString(1);
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Save verification token
    public static void saveVerificationToken(String user_id, String verification_code) {
        UUIDGenerator uuidGenerator = new UUIDGenerator();
        String verification_id = uuidGenerator.generateUUID();

        try (Connection connection = DBConnection.getDBInstance()) {
            String sql = "INSERT INTO bookshelf_verification (verification_id, user_id, verification_code, verification_type, created_at, status) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, verification_id);
            statement.setString(2, user_id);
            statement.setString(3, verification_code);
            statement.setString(4, "email"); 
            statement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            statement.setString(6, "pending");

            statement.executeUpdate();
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }


    // Verify user by token
    public static boolean verifyUserByToken(String verification_code) {
        try (Connection connection = DBConnection.getDBInstance()) {
            // Find the user ID using the verification code
            String query = "SELECT user_id FROM bookshelf_verification WHERE verification_code = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, verification_code);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String user_id = resultSet.getString("user_id");

                //Update user's is_verified status in bookshelf_user table
                String updateSql = "UPDATE bookshelf_user SET is_verified = TRUE WHERE user_id = ?";
                PreparedStatement updateStatement = connection.prepareStatement(updateSql);
                updateStatement.setString(1, user_id);
                updateStatement.executeUpdate();

                //Mark the verification as completed in bookshelf_verification table
                String updateVerificationSql = "UPDATE bookshelf_verification SET status = 'completed' WHERE verification_code = ?";
                PreparedStatement updateVerificationStmt = connection.prepareStatement(updateVerificationSql);
                updateVerificationStmt.setString(1, verification_code);
                updateVerificationStmt.executeUpdate();

                return true;
            }
        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Check if username exists
    public static boolean usernameExists(String username) {
        boolean exists = false;

        try (Connection connection = DBConnection.getDBInstance()) {
            String username_exists_sql = "SELECT * FROM bookshelf_user WHERE username=?";
            PreparedStatement preparedStmt = connection.prepareStatement(username_exists_sql);
            preparedStmt.setString(1, username);

            ResultSet rs = preparedStmt.executeQuery();
            if (rs != null && rs.next() && rs.getInt(1) > 0) {
                exists = true;
            }

            if (rs != null) rs.close();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return exists;
    }

    // Check if email exists
    public static boolean emailExists(String email) {
        boolean exists = false;

        try (Connection connection = DBConnection.getDBInstance()) {
            String email_exists_sql = "SELECT user_id FROM bookshelf_user WHERE email=?";
            PreparedStatement preparedStmt = connection.prepareStatement(email_exists_sql);
            preparedStmt.setString(1, email);

            ResultSet rs = preparedStmt.executeQuery();
            
            if (rs != null && rs.next()) {
                String userId = rs.getString("user_id");
                if (userId != null && !userId.isEmpty()) {
                    exists = true;
                }
            }

            if (rs != null) rs.close();

        } catch (SQLException e) {
            DBUtil.processException(e);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        return exists;
    }

    
	public static User authenticateUser(String email, String password) {
		try (Connection connection = DBConnection.getDBInstance()) {
	        String query = "SELECT * FROM bookshelf_user WHERE email = ? AND password = ? AND is_verified = TRUE";
	        PreparedStatement statement = connection.prepareStatement(query);
	        statement.setString(1, email);
	        statement.setString(2, password);

	        ResultSet resultSet = statement.executeQuery();
	        if (resultSet.next()) {
	            String userId = resultSet.getString("user_id");
	            String firstName = resultSet.getString("first_name");
	            String lastName = resultSet.getString("last_name");
	            
	            return new User(userId, firstName, lastName, true);
	        }
	    } catch (SQLException e) {
	        DBUtil.processException(e);
	    } catch (ClassNotFoundException e) {
	        e.printStackTrace();
	    }
	    return null;
	}
	
	public static String findIdByEmail(String email) {
	    String userId = null;
	    String query = "SELECT user_id FROM bookshelf_user WHERE email = ?";

	    try (Connection connection = DBConnection.getDBInstance();
	         PreparedStatement statement = connection.prepareStatement(query)) {

	        // Set the email parameter
	        statement.setString(1, email);

	        // Execute the query
	        try (ResultSet rs = statement.executeQuery()) {
	            if (rs.next()) {
	                userId = rs.getString("user_id");
	                if (userId == null || userId.isEmpty()) {
	                    System.out.println("User ID is empty or null for email: " + email);
	                }
	            } else {
	                System.out.println("No user found for email: " + email);
	            }
	        }

	    } catch (SQLException e) {
	        System.err.println("SQL Exception occurred while finding user ID by email.");
	        DBUtil.processException(e); 
	    } catch (ClassNotFoundException e) {
	        System.err.println("Database connection class not found.");
	        e.printStackTrace();
	    }

	    return userId;
	}
}
