package dao;

import model.Film;
import model.Proiezione;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * The interface Proiezione dao.
 */
public interface ProiezioneDAO {
    /**
     * Recupera proiezioni di un film array list.
     *
     * @param film the film
     * @return the array list
     * @throws SQLException the sql exception
     */
    ArrayList<Proiezione> recuperaProiezioniDiUnFilm(Film film) throws SQLException;
}