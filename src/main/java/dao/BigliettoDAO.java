package dao;

import model.Biglietto;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BigliettoDAO {

    /**
     * Inserisce un nuovo biglietto nel database.
     *
     * @param biglietto il {@link Biglietto} da inserire.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    void inserisciBigliettoDB(Biglietto biglietto) throws SQLException;

    /**
     * Aggiorna lo stato di un biglietto nel database identificandolo tramite il codice univoco.
     *
     * @param codiceUnivoco il codice identificativo del biglietto.
     * @param nuovoStato la stringa rappresentante il nuovo stato da assegnare.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    void aggiornaStatoBigliettoDB(String codiceUnivoco, String nuovoStato) throws SQLException;

    /**
     * Elimina un biglietto dal database identificandolo tramite il codice univoco.
     *
     * @param codiceUnivoco il codice identificativo del biglietto da eliminare.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    void eliminaBigliettoDB(String codiceUnivoco) throws SQLException;

    /**
     * Recupera l'elenco completo di tutti i biglietti presenti nel database.
     *
     * @return un {@link ArrayList} contenente tutti i biglietti registrati.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    ArrayList<Biglietto> recuperaTuttiBiglietti() throws SQLException;
}