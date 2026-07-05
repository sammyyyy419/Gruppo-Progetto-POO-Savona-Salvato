package dao;

import model.Cliente;
import java.sql.SQLException;


public interface ClienteDAO {

    /**
     * Inserisce un nuovo cliente nel database.
     *
     * @param cliente l'oggetto {@link Cliente} da registrare.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    void inserisciClienteDB(Cliente cliente) throws SQLException;

    /**
     * Recupera le informazioni di un cliente dal database utilizzando l'email come identificativo.
     *
     * @param email l'indirizzo email del cliente da ricercare.
     * @return l'oggetto {@link Cliente} corrispondente, oppure null se non trovato.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    Cliente recuperaClienteDaDB(String email) throws SQLException;

    /**
     * Aggiorna le credenziali di accesso (email e password) di un cliente nel database.
     *
     * @param vecchiaEmail l'indirizzo email attuale del cliente.
     * @param nuovaEmail il nuovo indirizzo email da impostare.
     * @param nuovaPassword la nuova password da impostare.
     * @throws SQLException se si verifica un errore durante l'interazione con il database.
     */
    void aggiornaCredenzialiClienteDB(String vecchiaEmail, String nuovaEmail, String nuovaPassword) throws SQLException;
}