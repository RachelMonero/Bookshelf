package com.bookshelf.connection;

import java.sql.Connection;
import java.sql.SQLException;

import com.bookshelf.dao.ApplicationDao;
import com.bookshelf.libs.DBType;

public class DBConnection {
	
	private static boolean initializedDB = false;
	
	private static void initDB() {
		if (initializedDB) { return; }
		initializedDB = true;
		ApplicationDao.createDatabase();
		ApplicationDao.getDao().createAddressTable();
		ApplicationDao.getDao().createGenreTable();
		ApplicationDao.getDao().createBooksTable();
		ApplicationDao.getDao().createRoleTable();
		ApplicationDao.getDao().createUserTable();
		ApplicationDao.getDao().createUserRoleTable();
		ApplicationDao.getDao().createVerificationTable();
		ApplicationDao.getDao().createLibraryTable();
		ApplicationDao.getDao().createLibraryBookTable();		
		ApplicationDao.getDao().createReservationsTable();


		
	}	
	
    public static Connection getDBInstance() throws ClassNotFoundException {
    	initDB();
        Connection connection = null;
        try {
        	connection = DBUtil.getConnection(DBType.MYSQL);
        } catch (SQLException e) {
        	DBUtil.processException(e);
        };
        return connection;
    }
}