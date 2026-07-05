package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The type Connessione database.
 */
public class ConnessioneDatabase {
	private static ConnessioneDatabase instance;
	/**
	 * The Connection.
	 */
	public Connection connection = null;

	private String nome = "postgres";
	private String password = "password";

	private String url = "jdbc:postgresql://localhost:5432/Cinema";
	private String driver = "org.postgresql.Driver";

	private ConnessioneDatabase() throws SQLException {
		try {
			Class.forName(driver);
			connection = DriverManager.getConnection(url, nome, password);
		} catch (ClassNotFoundException ex) {
			System.out.println("Database Connection Creation Failed: " + ex.getMessage());
			ex.printStackTrace();
			throw new SQLException("Driver PostgreSQL non trovato nel progetto!", ex);
		}
	}

	/**
	 * Gets instance.
	 *
	 * @return the instance
	 * @throws SQLException the sql exception
	 */
	public static ConnessioneDatabase getInstance() throws SQLException {
		if (instance == null) {
			instance = new ConnessioneDatabase();
		} else if (instance.connection == null || instance.connection.isClosed()) {
			instance = new ConnessioneDatabase();
		}
		return instance;
	}

	/**
	 * Gets connection.
	 *
	 * @return the connection
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Gets connessione.
	 *
	 * @return the connessione
	 * @throws SQLException the sql exception
	 */
	public static Connection getConnessione() throws SQLException {
		return getInstance().getConnection();
	}
}