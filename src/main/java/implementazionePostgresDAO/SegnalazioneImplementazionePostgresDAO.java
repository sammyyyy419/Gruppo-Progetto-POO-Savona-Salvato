package implementazionePostgresDAO;

import dao.SegnalazioneDAO;
import database.ConnessioneDatabase;
import java.sql.*;
import java.util.ArrayList;

public class SegnalazioneImplementazionePostgresDAO implements SegnalazioneDAO {

    @Override
    public void inserisciSegnalazioneDB(String mittenteEmail, String messaggio) throws SQLException {
        String query = "INSERT INTO segnalazione (mittente_email, messaggio) VALUES (?, ?)";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, mittenteEmail);
            ps.setString(2, messaggio);
            ps.executeUpdate();
        }
    }

    @Override
    public void inserisciSegnalazioneSalaDB(String mittenteEmail, String messaggio, String sala) throws SQLException {
        String query = "INSERT INTO segnalazione (mittente_email, messaggio, sala, risolta) VALUES (?, ?, ?, FALSE)";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, mittenteEmail);
            ps.setString(2, messaggio);
            ps.setString(3, sala);
            ps.executeUpdate();
        }
    }

    @Override
    public String ottieniProblemaSala(String sala) throws SQLException {
        String query = "SELECT messaggio FROM segnalazione WHERE sala = ? AND risolta = FALSE LIMIT 1";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, sala);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) return rs.getString("messaggio");
            }
        }
        return null;
    }

    @Override
    public void risolviSegnalazioneSalaDB(String sala) throws SQLException {
        String query = "UPDATE segnalazione SET risolta = TRUE WHERE sala = ?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {
            ps.setString(1, sala);
            ps.executeUpdate();
        }
    }

    @Override
    public ArrayList<String> recuperaTutteSegnalazioni() throws SQLException {
        ArrayList<String> segnalazioni = new ArrayList<>();
        String query = "SELECT * FROM segnalazione ORDER BY data_invio DESC";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                String salaText = (rs.getString("sala") != null) ? " [" + rs.getString("sala") + "]" : "";
                String seg = "[" + rs.getTimestamp("data_invio") + "]" + salaText + " Da: " + rs.getString("mittente_email") + " - " + rs.getString("messaggio");
                segnalazioni.add(seg);
            }
        }
        return segnalazioni;
    }
}