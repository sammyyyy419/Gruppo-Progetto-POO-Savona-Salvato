package model;

public class Utente {
    protected String nome;
    protected String cognome;
    protected String email;
    protected String password;

    public Utente (String nomeUtente, String cognomeUtente, String emailUtente, String passwordUtente)
    {
        this.nome = nomeUtente;
        this.cognome = cognomeUtente;
        this.email = emailUtente;
        this.password = passwordUtente;
    }
    // fai metodo fare l'autenticazione e aggiornare i dati personali

}
