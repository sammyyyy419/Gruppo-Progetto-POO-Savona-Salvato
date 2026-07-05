package implementazionePostgresDAO;

import dao.FilmDAO;
import database.ConnessioneDatabase;
import model.Film;
import java.sql.*;
import java.util.ArrayList;

public class FilmImplementazionePostgresDAO implements FilmDAO {

    @Override
    public void inserisciFilmDB(Film film) throws SQLException {
        String query = "INSERT INTO film (titolo, durata, genere, classificazione_eta, trama, percorso_copertina, data_inizio_programmazione, sala_assegnata) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, film.getTitolo());
            ps.setTime(2, Time.valueOf(film.getDurata()));
            ps.setString(3, film.getGenere());
            ps.setString(4, film.getClassificazioneEta());
            ps.setString(5, film.getTrama());
            ps.setString(6, film.getPercorsoCopertina());
            ps.setDate(7, Date.valueOf(film.getDataInizioProgrammazione()));
            ps.setString(8, film.getSalaAssegnata());
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
                String salaDB = rs.getString("sala_assegnata");

                Film f = new Film(
                        rs.getString("titolo"), rs.getTime("durata").toLocalTime(),
                        rs.getString("genere"), rs.getString("classificazione_eta"),
                        rs.getString("trama"), new ArrayList<>(), rs.getString("percorso_copertina"),
                        localDate, (salaDB != null && !salaDB.isEmpty()) ? salaDB : "Sala 1"
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
        String query = "UPDATE film SET titolo=?, durata=?, genere=?, classificazione_eta=?, trama=?, percorso_copertina=?, data_inizio_programmazione=?, sala_assegnata=? WHERE titolo=?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, nuovoFilm.getTitolo());
            ps.setTime(2, Time.valueOf(nuovoFilm.getDurata()));
            ps.setString(3, nuovoFilm.getGenere());
            ps.setString(4, nuovoFilm.getClassificazioneEta());
            ps.setString(5, nuovoFilm.getTrama());
            ps.setString(6, nuovoFilm.getPercorsoCopertina());
            ps.setDate(7, Date.valueOf(nuovoFilm.getDataInizioProgrammazione()));
            ps.setString(8, nuovoFilm.getSalaAssegnata());
            ps.setString(9, vecchioTitolo);
            ps.executeUpdate();
        }
    }
}