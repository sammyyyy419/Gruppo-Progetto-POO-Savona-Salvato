package dao;

import model.Cliente;
import java.sql.SQLException;

public interface ClienteDAO {
    // Salva un nuovo cliente nel database
    void inserisciClienteDB(Cliente cliente) throws SQLException;

    // Cerca un cliente nel database partendo dalla sua email
    Cliente recuperaClienteDaDB(String email) throws SQLException;
}