package dao;

import model.Film;
import java.sql.SQLException;

public interface FilmDAO {
    void inserisciFilmDB(Film film) throws SQLException;
}