package dao;

import model.Biglietto;
import java.sql.SQLException;
import java.util.ArrayList;

public interface BigliettoDAO {

    void inserisciBigliettoDB(Biglietto biglietto) throws SQLException;

    void aggiornaStatoBigliettoDB(String codiceUnivoco, String nuovoStato) throws SQLException;

    void eliminaBigliettoDB(String codiceUnivoco) throws SQLException;

    ArrayList<Biglietto> recuperaTuttiBiglietti() throws SQLException;
}