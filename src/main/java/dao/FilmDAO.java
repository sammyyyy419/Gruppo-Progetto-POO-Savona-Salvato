package dao;

import model.Film;
import java.sql.SQLException;
import java.util.ArrayList;

public interface FilmDAO {

    void inserisciFilmDB(Film film) throws SQLException;

    ArrayList<Film> recuperaTuttiFilm() throws SQLException;

    void eliminaFilmDB(Film film) throws SQLException;

    void aggiornaFilmDB(String vecchioTitolo, Film nuovoFilm) throws SQLException;
}