import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Base64;

public class Database {
	
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
			else conn = DriverManager.getConnection("null", "user", "pass"); // TODO: Connect
			if (conn.isValid(5)) online = true;
		
		} catch (SQLException ex) {
			
			online = false;
			
		}
		
	}

	public static void closeConnection() {
		/*
		* Close the database connection 
		*/

		try {

			conn.close();

		} catch (SQLException | NullPointerException ex) {

			Log.logData("WARN: Failed to properly close database connection.");

		}

	}

	public static String makeUnsafeQuery(String term) {
		/*
		* Send an unsafe query
		* term: The user's search term (or an injection)
		* a';SELECT * FROM item; --
		*/

		StringBuilder results = new StringBuilder(128);

		try {

			Statement stmt = conn.createStatement();
			stmt.executeQuery("SELECT * FROM enemy WHERE enemy_name LIKE '" + term + "'");

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

			Log.logData("WARN: Failed to retrieve mountain code!");
			ex.printStackTrace();
			return null;

		}

		byte[] raw_bytes;
		for (int c = 0; c < layers; c++) {

			raw_bytes = Base64.getDecoder().decode(data);
			data = new String(raw_bytes);

		}

		System.out.println(data);
		return data;

	}

}