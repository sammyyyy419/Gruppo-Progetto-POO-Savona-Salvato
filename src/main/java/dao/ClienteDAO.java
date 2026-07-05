package dao;

import model.Cliente;
import java.sql.SQLException;

/**
 * The interface Cliente dao.
 */
public interface ClienteDAO {

    /**
     * Inserisci cliente db.
     *
     * @param cliente the cliente
     * @throws SQLException the sql exception
     */
    void inserisciClienteDB(Cliente cliente) throws SQLException;

    /**
     * Recupera cliente da db cliente.
     *
     * @param email the email
     * @return the cliente
     * @throws SQLException the sql exception
     */
    Cliente recuperaClienteDaDB(String email) throws SQLException;

    /**
     * Aggiorna credenziali cliente db.
     *
     * @param vecchiaEmail  the vecchia email
     * @param nuovaEmail    the nuova email
     * @param nuovaPassword the nuova password
     * @throws SQLException the sql exception
     */
    void aggiornaCredenzialiClienteDB(String vecchiaEmail, String nuovaEmail, String nuovaPassword) throws SQLException;
}