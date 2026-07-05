package dao;

import model.Film;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Film dao.
 */
public interface FilmDAO {

    /**
     * Inserisci film db.
     *
     * @param film the film
     * @throws SQLException the sql exception
     */
    void inserisciFilmDB(Film film) throws SQLException;

    /**
     * Recupera tutti film array list.
     *
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<Film> recuperaTuttiFilm() throws SQLException;

    /**
     * Elimina film db.
     *
     * @param film the film
     * @throws SQLException the sql exception
     */
    void eliminaFilmDB(Film film) throws SQLException;

    /**
     * Aggiorna film db.
     *
     * @param vecchioTitolo the vecchio titolo
     * @param nuovoFilm     the nuovo film
     * @throws SQLException the sql exception
     */
    void aggiornaFilmDB(String vecchioTitolo, Film nuovoFilm) throws SQLException;
}