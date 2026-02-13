import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	public static Connection conn;
	
	public static void makeConnection(boolean local) {
		/*
		* Attempts to connect to the database
		* local: Whether to connect to a local or remote server
		*/

		Log.logData("Establishing connection to database.");

		try {
			
			if (local) conn = DriverManager.getConnection("jdbc:mysql://localhost/tss", "root", "");
			else conn = DriverManager.getConnection("null", "user", "pass"); // TODO: Connect
			if (conn.isValid(5)) return;
		
		} catch (SQLException ex) {
			
			ex.printStackTrace();
			System.exit(1);
			
		}
		
	}

	public static void closeConnection() {
		/*
		* Close the database connection 
		*/

		try {

			conn.close();

		} catch (SQLException ex) {

			Log.logData("WARN: Failed to properly close database connection.");

		}

	}

}