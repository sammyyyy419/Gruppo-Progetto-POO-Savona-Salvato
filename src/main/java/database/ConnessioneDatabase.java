package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnessioneDatabase {
	private static ConnessioneDatabase instance;
	/**
	 * La connessione.
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
	 * Ottiene l'istanza univoca della classe ConnessioneDatabase, creandola se non esiste o se la connessione è stata chiusa.
	 *
	 * @return l'istanza corrente di {@link ConnessioneDatabase}.
	 * @throws SQLException se si verifica un errore durante la creazione della connessione al database.
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
	 * Restituisce l'oggetto {@link Connection} attualmente gestito dall'istanza Singleton.
	 *
	 * @return l'oggetto {@link Connection} attivo.
	 */
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Metodo statico di utilità per ottenere direttamente l'oggetto {@link Connection} dal database.
	 * Gestisce internamente l'ottenimento dell'istanza Singleton.
	 *
	 * @return l'oggetto {@link Connection} pronto all'uso.
	 * @throws SQLException se si verifica un errore durante il recupero della connessione.
	 */
	public static Connection getConnessione() throws SQLException {
		return getInstance().getConnection();
	}
}