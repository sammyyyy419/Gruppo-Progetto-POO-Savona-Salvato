package implementazionePostgresDAO;

import dao.RecensioneDAO;
import database.ConnessioneDatabase;
import java.sql.*;
import java.util.ArrayList;

/**
 * The type Recensione implementazione postgres dao.
 */
public class RecensioneImplementazionePostgresDAO implements RecensioneDAO {

    @Override
    public void inserisciRecensioneDB(String titoloFilm, String autore, int voto, String commento) throws SQLException {
        String query = "INSERT INTO recensione (titolo_film, autore, voto, commento) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, titoloFilm);
            ps.setString(2, autore);
            ps.setInt(3, voto);
            ps.setString(4, commento);
            ps.executeUpdate();
        }
    }

    @Override
    public ArrayList<String> recuperaRecensioniPerFilm(String titoloFilm) throws SQLException {
        ArrayList<String> recensioni = new ArrayList<>();
        String query = "SELECT autore, voto, commento FROM recensione WHERE titolo_film = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, titoloFilm);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    String rec = rs.getString("autore") + "||" + rs.getInt("voto") + "||" + rs.getString("commento");
                    recensioni.add(rec);
                }
            }
        }
        return recensioni;
    }
}