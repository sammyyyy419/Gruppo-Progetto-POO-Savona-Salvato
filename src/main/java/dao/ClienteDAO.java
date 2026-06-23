package dao;

import model.Cliente;
import java.sql.SQLException;

public interface ClienteDAO {

    void inserisciClienteDB(Cliente cliente) throws SQLException;

    Cliente recuperaClienteDaDB(String email) throws SQLException;

    void aggiornaCredenzialiClienteDB(String vecchiaEmail, String nuovaEmail, String nuovaPassword) throws SQLException;
}