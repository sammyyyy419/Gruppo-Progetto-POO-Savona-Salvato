package implementazionePostgresDAO;

import dao.FilmDAO;
import database.ConnessioneDatabase;
import model.Film;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;

public class FilmImplementazionePostgresDAO implements FilmDAO {

    @Override
    public void inserisciFilmDB(Film film) throws SQLException {
        String query = "INSERT INTO film (titolo, durata, genere, classificazione_eta, trama) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, film.getTitolo());
            ps.setTime(2, Time.valueOf(film.getDurata()));
            ps.setString(3, film.getGenere());
            ps.setString(4, film.getClassificazioneEta());
            ps.setString(5, film.getTrama());

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
                Film f = new Film(
                        rs.getString("titolo"),
                        rs.getTime("durata").toLocalTime(), // Riconverte da SQL Time a Java LocalTime
                        rs.getString("genere"),
                        rs.getString("classificazione_eta"),
                        rs.getString("trama"),
                        new ArrayList<>() // Lista recensioni inizialmente vuota
                );
                filmEstratti.add(f);
            }
        }
        return filmEstratti;
    }
}