import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Database {
	
	public static Connection conn;
	
	public static boolean makeConnection() {
			
		try {
			
			conn = DriverManager.getConnection("jdbc:mysql://localhost/tss", "root", "");
			ResultSet rs = conn.prepareStatement("SELECT * FROM data").executeQuery();
			
			while (rs.next()) {
				
				System.out.println(rs.getString("text"));
				
			}
			
			return conn.isValid(5);
		
		} catch (SQLException ex) {
			
			ex.printStackTrace();
			return false;
			
		}
		
	}

}