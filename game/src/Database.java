import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class Database {
	
	private static final String DATABASE_URL = "192.168.2.3";
	public static Connection conn = null;
	public static boolean online;
	
	public static void makeConnection(boolean local) {
		/*
		* Attempts to connect to the database
		* local: Whether to connect to a local or remote server
		*/

		Log.logData("Establishing connection to database.");
		try {
			
			if (local) conn = DriverManager.getConnection("jdbc:mysql://localhost/tss?allowMultiQueries=true", "root", "");
			else conn = DriverManager.getConnection("jdbc:mysql://" + DATABASE_URL + "/tss?allow", "___", "___"); // TODO: credentials
			if (conn.isValid(5)) online = true;
		
		} catch (SQLException ex) {
			
			Log.logData("WARN: Failed to connect to database. Related features are disabled.\n" + ex.toString());
			online = false;
			
		}
		
	}

	public static void closeConnection() {
		/*
		* Close the database connection 
		*/

		try {conn.close();}
		catch (SQLException | NullPointerException ex) {

			Log.logData("WARN: Failed to properly close database connection:\n" + ex.toString());

		}

	}

	public static String makeUnsafeQuery(String term) {
		/*
		* Send an unsafe query
		* term: The user's input
		*/

		Log.logData("Making an unsafe query; user entered: " + term);
		StringBuilder results = new StringBuilder(128);

		try {

			Statement stmt = conn.createStatement();
			stmt.executeQuery("SELECT * FROM enemy WHERE enemy_name LIKE '" + term + "'"); // Injection location

			do {

				ResultSet rs = stmt.getResultSet();

				while (rs != null && rs.next()) {

					ResultSetMetaData rsmd = rs.getMetaData();
					for (int c = 1; c <= rsmd.getColumnCount(); c++) {

						if (c > 1) results.append(", ");
						results.append(rsmd.getColumnName(c) + ": " + rs.getString(c));

					}

					results.append('\n');

				}

			} while (stmt.getMoreResults());

			return results.toString();

		} catch (SQLException ex) {

			return ex.toString();

		}

	}

	public static String getMountainCode() {
		/*
		* Gets the code for the gate between the village and the mountain
		* Called in 'unlock' to verify the user's input is correct
		*/

		String data;
		byte layers;
		
		Log.logData("Pulling mountain code from database.");
		try {

			PreparedStatement stmt = conn.prepareStatement("SELECT * FROM _tss_meta WHERE metaID = 1");
			ResultSet rs = stmt.executeQuery();
			rs.next();
			data = rs.getString(2);
			layers = rs.getByte(3);
			

		} catch (SQLException ex) {

			Log.logData("WARN: Failed to retrieve mountain code!\n" + ex.toString());
			return null;

		}

		for (byte c = 0; c < layers; c++) data = new String(Base64.getDecoder().decode(data));
		return data;

	}

}