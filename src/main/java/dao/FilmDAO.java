package dao;

import model.Film;
import java.sql.SQLException;
import java.util.ArrayList;

public interface FilmDAO {

    /**
     * Inserisce un nuovo film all'interno del database.
     *
     * @param film l'oggetto {@link Film} da registrare.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    void inserisciFilmDB(Film film) throws SQLException;

    /**
     * Recupera l'elenco completo di tutti i film presenti nel database.
     *
     * @return un {@link ArrayList} contenente tutti i film registrati.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    ArrayList<Film> recuperaTuttiFilm() throws SQLException;

    /**
     * Elimina un film specifico dal database.
     *
     * @param film l'oggetto {@link Film} da rimuovere.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    void eliminaFilmDB(Film film) throws SQLException;

    /**
     * Aggiorna le informazioni di un film esistente nel database, identificandolo tramite il suo titolo originale.
     *
     * @param vecchioTitolo il titolo del film prima dell'aggiornamento.
     * @param nuovoFilm l'oggetto {@link Film} contenente i dati aggiornati.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    void aggiornaFilmDB(String vecchioTitolo, Film nuovoFilm) throws SQLException;
}