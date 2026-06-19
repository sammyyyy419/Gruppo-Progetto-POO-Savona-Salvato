package dao;

import model.Film;
import java.sql.SQLException;
import java.util.ArrayList;

public interface FilmDAO {
    // Salva un nuovo film
    void inserisciFilmDB(Film film) throws SQLException;

    // Estrae tutti i film dal database
    ArrayList<Film> recuperaTuttiFilm() throws SQLException;

    // ELIMINA un film dal database
    void eliminaFilmDB(Film film) throws SQLException;

    // AGGIORNA un film esistente nel database
    void aggiornaFilmDB(String vecchioTitolo, Film nuovoFilm) throws SQLException;
}