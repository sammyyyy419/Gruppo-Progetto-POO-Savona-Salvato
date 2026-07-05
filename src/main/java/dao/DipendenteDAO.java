package dao;

import model.Dipendente;
import java.sql.SQLException;
import java.util.ArrayList;


public interface DipendenteDAO {

	/**
	 * Recupera l'elenco completo di tutti i dipendenti registrati nel database.
	 *
	 * @return un {@link ArrayList} contenente tutti i dipendenti.
	 * @throws SQLException se si verifica un errore durante l'interazione con il database.
	 */
	ArrayList<Dipendente> recuperaTuttiDipendenti() throws SQLException;

	/**
	 * Aggiorna la password di un dipendente nel database identificandolo tramite la sua email.
	 *
	 * @param email l'indirizzo email del dipendente.
	 * @param nuovaPassword la nuova password da impostare.
	 * @throws SQLException se si verifica un errore durante l'interazione con il database.
	 */
	void aggiornaPasswordDipendenteDB(String email, String nuovaPassword) throws SQLException;
}