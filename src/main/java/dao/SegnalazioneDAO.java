package dao;

import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Segnalazione dao.
 */
public interface SegnalazioneDAO {

    /**
     * Inserisci segnalazione db.
     *
     * @param mittenteEmail the mittente email
     * @param messaggio     the messaggio
     * @throws SQLException the sql exception
     */
    void inserisciSegnalazioneDB(String mittenteEmail, String messaggio) throws SQLException;

    /**
     * Inserisci segnalazione sala db.
     *
     * @param mittenteEmail the mittente email
     * @param messaggio     the messaggio
     * @param sala          the sala
     * @throws SQLException the sql exception
     */
    void inserisciSegnalazioneSalaDB(String mittenteEmail, String messaggio, String sala) throws SQLException;

    /**
     * Ottieni problema sala string.
     *
     * @param sala the sala
     * @return the string
     * @throws SQLException the sql exception
     */
    String ottieniProblemaSala(String sala) throws SQLException;

    /**
     * Risolvi segnalazione sala db.
     *
     * @param sala the sala
     * @throws SQLException the sql exception
     */
    void risolviSegnalazioneSalaDB(String sala) throws SQLException;

    /**
     * Recupera tutte segnalazioni array list.
     *
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<String> recuperaTutteSegnalazioni() throws SQLException;
}