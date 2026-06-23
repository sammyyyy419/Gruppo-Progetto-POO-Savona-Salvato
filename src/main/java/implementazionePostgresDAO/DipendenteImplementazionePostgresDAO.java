package implementazionePostgresDAO;

import dao.DipendenteDAO;
import database.ConnessioneDatabase;
import model.Dipendente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DipendenteImplementazionePostgresDAO implements DipendenteDAO {

	@Override
	public ArrayList<Dipendente> recuperaTuttiDipendenti() throws SQLException {
		ArrayList<Dipendente> dipendenti = new ArrayList<>();
		String query = "SELECT * FROM dipendente";

		try (Connection con = ConnessioneDatabase.getInstance().getConnection();
		     PreparedStatement ps = con.prepareStatement(query);
		     ResultSet rs = ps.executeQuery()) {

			while (rs.next()) {
				Dipendente d = new Dipendente(
						rs.getString("nome"),
						rs.getString("cognome"),
						rs.getString("email"),
						rs.getString("password"),
						rs.getString("ruolo")
				);
				dipendenti.add(d);
			}
		}
		return dipendenti;
	}

	@Override
	public void aggiornaPasswordDipendenteDB(String email, String nuovaPassword) throws SQLException {
		String query = "UPDATE dipendente SET password = ? WHERE email = ?";
		try (Connection con = ConnessioneDatabase.getInstance().getConnection();
		     PreparedStatement ps = con.prepareStatement(query)) {

			ps.setString(1, nuovaPassword);
			ps.setString(2, email);
			ps.executeUpdate();
		}
	}
}