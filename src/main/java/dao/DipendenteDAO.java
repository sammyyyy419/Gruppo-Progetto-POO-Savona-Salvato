package dao;

import model.Dipendente;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Dipendente dao.
 */
public interface DipendenteDAO {

	/**
	 * Recupera tutti dipendenti array list.
	 *
	 * @return the array list
	 * @throws SQLException the sql exception
	 */
	ArrayList<Dipendente> recuperaTuttiDipendenti() throws SQLException;

	/**
	 * Aggiorna password dipendente db.
	 *
	 * @param email         the email
	 * @param nuovaPassword the nuova password
	 * @throws SQLException the sql exception
	 */
	void aggiornaPasswordDipendenteDB(String email, String nuovaPassword) throws SQLException;
}