package dao;

import model.Biglietto;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Biglietto dao.
 */
public interface BigliettoDAO {

    /**
     * Inserisci biglietto db.
     *
     * @param biglietto the biglietto
     * @throws SQLException the sql exception
     */
    void inserisciBigliettoDB(Biglietto biglietto) throws SQLException;

    /**
     * Aggiorna stato biglietto db.
     *
     * @param codiceUnivoco the codice univoco
     * @param nuovoStato    the nuovo stato
     * @throws SQLException the sql exception
     */
    void aggiornaStatoBigliettoDB(String codiceUnivoco, String nuovoStato) throws SQLException;

    /**
     * Elimina biglietto db.
     *
     * @param codiceUnivoco the codice univoco
     * @throws SQLException the sql exception
     */
    void eliminaBigliettoDB(String codiceUnivoco) throws SQLException;

    /**
     * Recupera tutti biglietti array list.
     *
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<Biglietto> recuperaTuttiBiglietti() throws SQLException;
}