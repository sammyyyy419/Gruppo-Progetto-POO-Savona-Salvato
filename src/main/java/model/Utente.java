package model;

public class Utente {
    private String nome;
    private String cognome;
    private String email;
    private String password;

    public Utente (String nomeUtente, String cognomeUtente, String emailUtente, String passwordUtente)
    {
        this.nome = nomeUtente;
        this.cognome = cognomeUtente;
        this.email = emailUtente;
        this.password = passwordUtente;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    // fai metodo fare l'autenticazione e aggiornare i dati personali

}
