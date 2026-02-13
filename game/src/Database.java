import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
	
	public static Connection conn;
	
	public static void makeConnection(boolean local) {

		Log.logData("Establishing connection to database.");

		try {
			
			if (local) conn = DriverManager.getConnection("jdbc:mysql://localhost/tss", "root", "");
			else conn = DriverManager.getConnection("null", "user", "pass");
			if (conn.isValid(5)) return;
		
		} catch (SQLException ex) {
			
			ex.printStackTrace();
			System.exit(1);
			
		}
		
	}

}