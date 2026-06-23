package implementazionePostgresDAO;

import dao.BigliettoDAO;
import database.ConnessioneDatabase;
import model.Biglietto;
import model.Film;
import model.Posto;
import model.Proiezione;
import model.Sala;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class BigliettoImplementazionePostgresDAO implements BigliettoDAO {

    @Override
    public void inserisciBigliettoDB(Biglietto biglietto) throws SQLException {
        String query = "INSERT INTO biglietto (codice_univoco, titolo_film, sala, data_orario_proiezione, fila, numero_posto, prezzo_pagato, stato) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, biglietto.getCodiceUnivoco());
            ps.setString(2, biglietto.getProiezione().getFilm().getTitolo());
            ps.setString(3, biglietto.getProiezione().getSala().getNumeroSala());
            ps.setTimestamp(4, Timestamp.valueOf(biglietto.getProiezione().getDataOraInizio()));
            ps.setString(5, String.valueOf(biglietto.getPostoAssegnato().getFila()));
            ps.setInt(6, biglietto.getPostoAssegnato().getNumeroPosto());
            ps.setDouble(7, biglietto.getPrezzoFinale());
            ps.setString(8, biglietto.isValido() ? "CONVALIDATO" : "DA CONVALIDARE");

            ps.executeUpdate();
        }
    }

    @Override
    public void aggiornaStatoBigliettoDB(String codiceUnivoco, String nuovoStato) throws SQLException {
        String query = "UPDATE biglietto SET stato = ? WHERE codice_univoco = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, nuovoStato);
            ps.setString(2, codiceUnivoco);
            ps.executeUpdate();
        }
    }

    @Override
    public void eliminaBigliettoDB(String codiceUnivoco) throws SQLException {
        String query = "DELETE FROM biglietto WHERE codice_univoco = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, codiceUnivoco);
            ps.executeUpdate();
        }
    }

  @Override
    public ArrayList<Biglietto> recuperaTuttiBiglietti() throws SQLException {
        ArrayList<Biglietto> lista = new ArrayList<>();
        String query = "SELECT * FROM biglietto";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Film fintoFilm = new Film(rs.getString("titolo_film"), java.time.LocalTime.MIDNIGHT, "", "", "", null, "", java.time.LocalDate.now(), rs.getString("sala"));
                Sala fintaSala = new Sala(rs.getString("sala"), 180, "");

                LocalDateTime dataOra = rs.getTimestamp("data_orario_proiezione").toLocalDateTime();
                Proiezione fintaProiezione = new Proiezione(dataOra, dataOra, 8.0, fintoFilm, fintaSala);

                Posto fintoPosto = new Posto(rs.getInt("numero_posto"), rs.getString("fila").charAt(0));

                double prezzo = rs.getDouble("prezzo_pagato");

                Biglietto b = new Biglietto(prezzo, fintoPosto, fintaProiezione, null);
                b.setCodiceUnivoco(rs.getString("codice_univoco")); // Imposta il VERO codice salvato!
                b.setValido(rs.getString("stato").equals("CONVALIDATO"));

                lista.add(b);
            }
        }
        return lista;
    }
}