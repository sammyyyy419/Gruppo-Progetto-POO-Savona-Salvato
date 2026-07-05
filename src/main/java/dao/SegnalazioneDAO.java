package dao;

import java.sql.SQLException;
import java.util.ArrayList;


public interface SegnalazioneDAO {

    /**
     * Inserisce una segnalazione generica nel database.
     *
     * @param mittenteEmail l'indirizzo email del dipendente che effettua la segnalazione.
     * @param messaggio il contenuto del messaggio di segnalazione.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    void inserisciSegnalazioneDB(String mittenteEmail, String messaggio) throws SQLException;

    /**
     * Inserisce una segnalazione specifica riguardante un guasto in una sala nel database.
     *
     * @param mittenteEmail l'indirizzo email del dipendente che effettua la segnalazione.
     * @param messaggio il contenuto del messaggio di segnalazione.
     * @param sala il nome identificativo della sala soggetta a guasto.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    void inserisciSegnalazioneSalaDB(String mittenteEmail, String messaggio, String sala) throws SQLException;

    /**
     * Recupera la descrizione di un problema tecnico attivo per una specifica sala.
     *
     * @param sala il nome identificativo della sala.
     * @return una stringa contenente i dettagli del problema, o null se la sala non presenta segnalazioni attive.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    String ottieniProblemaSala(String sala) throws SQLException;

    /**
     * Aggiorna lo stato di una sala nel database segnando la segnalazione di guasto come risolta.
     *
     * @param sala il nome identificativo della sala da ripristinare.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    void risolviSegnalazioneSalaDB(String sala) throws SQLException;

    /**
     * Recupera l'elenco completo di tutte le segnalazioni presenti nel database.
     *
     * @return un {@link ArrayList} di stringhe contenente il testo di tutte le segnalazioni.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    ArrayList<String> recuperaTutteSegnalazioni() throws SQLException;
}