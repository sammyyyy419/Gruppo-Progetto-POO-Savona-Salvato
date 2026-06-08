package implementazionePostgresDAO;

import dao.ClienteDAO;
import model.Cliente;
import database.ConnessioneDatabase;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ClienteImplementazionePostgresDAO implements ClienteDAO {

    @Override
    public void inserisciClienteDB(Cliente cliente) throws SQLException {
        String query = "INSERT INTO cliente (nome, cognome, email, password) VALUES (?, ?, ?, ?)";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, cliente.getNome());
            ps.setString(2, cliente.getCognome());
            ps.setString(3, cliente.getEmail());
            ps.setString(4, cliente.getPassword());

            ps.executeUpdate();
        }
    }

    @Override
    public Cliente recuperaClienteDaDB(String email) throws SQLException {
        String query = "SELECT * FROM cliente WHERE email = ?";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, email);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return new Cliente(
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                }
            }
        }
        return null; // Ritorna null se l'utente con quella mail non esiste nel DB
    }
}