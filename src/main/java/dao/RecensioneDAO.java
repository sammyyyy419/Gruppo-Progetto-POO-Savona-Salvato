package dao;

import java.sql.SQLException;
import java.util.ArrayList;

public interface RecensioneDAO {

    void inserisciRecensioneDB(String titoloFilm, String autore, int voto, String commento) throws SQLException;

    ArrayList<String> recuperaRecensioniPerFilm(String titoloFilm) throws SQLException;
}