package dao;

import model.Film;
import model.Proiezione;
import java.sql.SQLException;
import java.util.ArrayList;

public interface ProiezioneDAO {
    /**
     * Recupera l'elenco di tutte le proiezioni associate a un determinato film dal database.
     *
     * @param film l'oggetto {@link Film} di cui si desiderano conoscere le proiezioni.
     * @return un {@link ArrayList} contenente le proiezioni del film specificato.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    ArrayList<Proiezione> recuperaProiezioniDiUnFilm(Film film) throws SQLException;
}