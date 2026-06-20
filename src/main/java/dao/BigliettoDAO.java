package dao;

import model.Biglietto;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BigliettoDAO {
    // Salva il biglietto quando il cliente lo compra
    void inserisciBigliettoDB(Biglietto biglietto) throws SQLException;

    // Aggiorna lo stato (da "DA CONVALIDARE" a "CONVALIDATO") quando il dipendente lo strappa
    void aggiornaStatoBigliettoDB(String codiceUnivoco, String nuovoStato) throws SQLException;

    // Cancella il biglietto dal database se il cliente chiede il rimborso
    void eliminaBigliettoDB(String codiceUnivoco) throws SQLException;

    // NUOVO METODO AGGIUNTO: Recupera tutti i biglietti all'avvio del programma
    ArrayList<Biglietto> recuperaTuttiBiglietti() throws SQLException;
}