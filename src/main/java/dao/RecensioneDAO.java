package dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Recensione dao.
 */
public interface RecensioneDAO {

    /**
     * Inserisci recensione db.
     *
     * @param titoloFilm the titolo film
     * @param autore     the autore
     * @param voto       the voto
     * @param commento   the commento
     * @throws SQLException the sql exception
     */
    void inserisciRecensioneDB(String titoloFilm, String autore, int voto, String commento) throws SQLException;

    /**
     * Recupera recensioni per film array list.
     *
     * @param titoloFilm the titolo film
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<String> recuperaRecensioniPerFilm(String titoloFilm) throws SQLException;
}