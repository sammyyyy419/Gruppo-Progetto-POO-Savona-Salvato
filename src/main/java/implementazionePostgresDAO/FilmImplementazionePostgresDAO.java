package implementazionePostgresDAO;

import dao.FilmDAO;
import model.Film;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;

public class FilmImplementazionePostgresDAO implements FilmDAO {

    @Override
    public void inserisciFilmDB(Film film) throws SQLException {
        String query = "INSERT INTO film (titolo, durata, genere, classificazione, trama) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, film.getTitolo());

            if (film.getDurata() != null) {
                ps.setTime(2, Time.valueOf(film.getDurata()));
            } else {
                ps.setNull(2, java.sql.Types.TIME);
            }

            ps.setString(3, film.getGenere());

            ps.setString(4, film.getClassificazioneEta());

            ps.setString(5, film.getTrama());

            ps.executeUpdate();
        }
    }
}