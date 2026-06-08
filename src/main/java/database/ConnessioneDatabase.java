package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnessioneDatabase {
	private static ConnessioneDatabase instance;
	public Connection connection = null;

	private String nome = "postgres";
	private String password = "password"; // <-- CAMBIA QUESTO SE LA TUA PASSWORD DI PGADMIN È DIVERSA

	// NOTA: Se su pgAdmin il database si chiama "EnterpriseCinema", sostituisci "CinemaDB" con "EnterpriseCinema"
	private String url = "jdbc:postgresql://localhost:5432/Cinema";
	private String driver = "org.postgresql.Driver";

	// Costruttore privato (Pattern Singleton)
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

	// Ritorna l'istanza della classe ConnessioneDatabase (gestisce il riciclo della connessione)
	public static ConnessioneDatabase getInstance() throws SQLException {
		if (instance == null) {
			instance = new ConnessioneDatabase();
		} else if (instance.connection == null || instance.connection.isClosed()) {
			instance = new ConnessioneDatabase();
		}
		return instance;
	}

	// Getter per l'oggetto Connection dell'istanza
	public Connection getConnection() {
		return connection;
	}

	/**
	 * Questo metodo permette ai tuoi DAO (come FilmImplementazionePostgresDAO)
	 * di richiamare la connessione al volo scrivendo semplicemente: ConnessioneDatabase.getConnessione()
	 */
	public static Connection getConnessione() throws SQLException {
		return getInstance().getConnection();
	}
}