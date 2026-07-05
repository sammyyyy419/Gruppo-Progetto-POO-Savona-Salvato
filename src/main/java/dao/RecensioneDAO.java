package dao;

import java.sql.SQLException;
import java.util.ArrayList;


public interface RecensioneDAO {

    /**
     * Inserisce una nuova recensione per un determinato film nel database.
     *
     * @param titoloFilm il titolo del film recensito.
     * @param autore il nome dell'autore della recensione.
     * @param voto il voto assegnato al film (es. da 1 a 5 o da 1 a 10).
     * @param commento il contenuto testuale della recensione.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    void inserisciRecensioneDB(String titoloFilm, String autore, int voto, String commento) throws SQLException;

    /**
     * Recupera tutte le recensioni associate a uno specifico film dal database.
     *
     * @param titoloFilm il titolo del film di cui si desiderano recuperare le recensioni.
     * @return un {@link ArrayList} di stringhe contenente le recensioni trovate.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    ArrayList<String> recuperaRecensioniPerFilm(String titoloFilm) throws SQLException;
}