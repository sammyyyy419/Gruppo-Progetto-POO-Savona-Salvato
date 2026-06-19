package implementazionePostgresDAO;

import dao.FilmDAO;
import database.ConnessioneDatabase;
import model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Date;
import java.util.ArrayList;

public class FilmImplementazionePostgresDAO implements FilmDAO {

    @Override
    public void inserisciFilmDB(Film film) throws SQLException {
        String query = "INSERT INTO film (titolo, durata, genere, classificazione_eta, trama, percorso_copertina, data_inizio_programmazione) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, film.getTitolo());
            ps.setTime(2, Time.valueOf(film.getDurata()));
            ps.setString(3, film.getGenere());
            ps.setString(4, film.getClassificazioneEta());
            ps.setString(5, film.getTrama());
            ps.setString(6, film.getPercorsoCopertina());
            ps.setDate(7, Date.valueOf(film.getDataInizioProgrammazione())); // NUOVO

            ps.executeUpdate();
        }
    }

    @Override
    public ArrayList<Film> recuperaTuttiFilm() throws SQLException {
        ArrayList<Film> filmEstratti = new ArrayList<>();
        String query = "SELECT * FROM film";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                Date sqlDate = rs.getDate("data_inizio_programmazione");
                java.time.LocalDate localDate = (sqlDate != null) ? sqlDate.toLocalDate() : java.time.LocalDate.now();

                Film f = new Film(
                        rs.getString("titolo"),
                        rs.getTime("durata").toLocalTime(),
                        rs.getString("genere"),
                        rs.getString("classificazione_eta"),
                        rs.getString("trama"),
                        new ArrayList<>(),
                        rs.getString("percorso_copertina"),
                        localDate // NUOVO
                );
                filmEstratti.add(f);
            }
        }
        return filmEstratti;
    }

    @Override
    public void eliminaFilmDB(Film film) throws SQLException {
        String query = "DELETE FROM film WHERE titolo = ?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, film.getTitolo());
            ps.executeUpdate();
        }
    }

    @Override
    public void aggiornaFilmDB(String vecchioTitolo, Film nuovoFilm) throws SQLException {
        String query = "UPDATE film SET titolo=?, durata=?, genere=?, classificazione_eta=?, trama=?, percorso_copertina=?, data_inizio_programmazione=? WHERE titolo=?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, nuovoFilm.getTitolo());
            ps.setTime(2, Time.valueOf(nuovoFilm.getDurata()));
            ps.setString(3, nuovoFilm.getGenere());
            ps.setString(4, nuovoFilm.getClassificazioneEta());
            ps.setString(5, nuovoFilm.getTrama());
            ps.setString(6, nuovoFilm.getPercorsoCopertina());
            ps.setDate(7, Date.valueOf(nuovoFilm.getDataInizioProgrammazione())); // NUOVO
            ps.setString(8, vecchioTitolo);
            ps.executeUpdate();
        }
    }
}