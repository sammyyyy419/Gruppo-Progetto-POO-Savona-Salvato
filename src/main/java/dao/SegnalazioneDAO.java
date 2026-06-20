package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface SegnalazioneDAO {
    // Il vecchio metodo per le segnalazioni generiche
    void inserisciSegnalazioneDB(String mittenteEmail, String messaggio) throws SQLException;

    // NUOVI METODI PER LA MAPPA SALE
    void inserisciSegnalazioneSalaDB(String mittenteEmail, String messaggio, String sala) throws SQLException;
    String ottieniProblemaSala(String sala) throws SQLException;
    void risolviSegnalazioneSalaDB(String sala) throws SQLException;

    ArrayList<String> recuperaTutteSegnalazioni() throws SQLException;
}