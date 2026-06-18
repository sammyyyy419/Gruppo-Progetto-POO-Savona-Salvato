package dao;

import model.Film;
import java.sql.SQLException;
import java.util.ArrayList;

public interface FilmDAO {
    // Salva un nuovo film
    void inserisciFilmDB(Film film) throws SQLException;

    // Novità: Estrae tutti i film dal database
    ArrayList<Film> recuperaTuttiFilm() throws SQLException;
}