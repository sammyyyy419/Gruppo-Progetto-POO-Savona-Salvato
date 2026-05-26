package model;

public abstract class Utente {
    protected String Nome;
    protected String Cognome;
    protected String email;
    protected String password;

    public Utente(String nome, String cognome, String email, String password) {
        this.Nome = nome;
        this.Cognome = cognome;
        this.email = email;
        this.password = password;
    }

    public boolean fareLAutenticazione(String emailInserita, String passwordInserita) {
        return this.email.equals(emailInserita) && this.password.equals(passwordInserita);
    }

    public void aggiornareDatiPersonali(String nuovoNome, String nuovoCognome, String nuovaPassword) {
        this.Nome = nuovoNome;
        this.Cognome = nuovoCognome;
        this.password = nuovaPassword;
    }

    public String getNome() { return Nome; }
    public void setNome(String nome) { Nome = nome; }
    public String getCognome() { return Cognome; }
    public void setCognome(String cognome) { Cognome = cognome; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}