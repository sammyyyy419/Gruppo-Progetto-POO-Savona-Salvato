package implementazionePostgresDAO;

import dao.ClienteDAO;
import database.ConnessioneDatabase;
import model.Cliente;

import java.sql.*;


public class ClienteImplementazionePostgresDAO implements ClienteDAO {

    @Override
    public void inserisciClienteDB(Cliente cliente) throws SQLException {
        String query = "INSERT INTO cliente (email, nome, cognome, password, data_registrazione) VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, cliente.getEmail());
            ps.setString(2, cliente.getNome());
            ps.setString(3, cliente.getCognome());
            ps.setString(4, cliente.getPassword());
            ps.setDate(5, Date.valueOf(cliente.getDataRegistrazione()));

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
                    Cliente cliente = new Cliente(
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("email"),
                            rs.getString("password")
                    );
                    cliente.setDataRegistrazione(rs.getDate("data_registrazione").toLocalDate());
                    return cliente;
                }
            }
        }
        return null;
    }
    @Override
    public void aggiornaCredenzialiClienteDB(String vecchiaEmail, String nuovaEmail, String nuovaPassword) throws SQLException {
        String query = "UPDATE cliente SET email = ?, password = ? WHERE email = ?";
        try (Connection con = ConnessioneDatabase.getInstance().getConnection();
             PreparedStatement ps = con.prepareStatement(query)) {

            ps.setString(1, nuovaEmail);
            ps.setString(2, nuovaPassword);
            ps.setString(3, vecchiaEmail);
            ps.executeUpdate();
        }
    }
}