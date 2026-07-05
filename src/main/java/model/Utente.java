package model;

/**
 * The type Utente.
 */
public abstract class Utente {
    /**
     * The Nome.
     */
    protected String Nome;
    /**
     * The Cognome.
     */
    protected String Cognome;
    /**
     * The Email.
     */
    protected String email;
    /**
     * The Password.
     */
    protected String password;

    /**
     * Instantiates a new Utente.
     *
     * @param nome     the nome
     * @param cognome  the cognome
     * @param email    the email
     * @param password the password
     */
    public Utente(String nome, String cognome, String email, String password) {
        this.Nome = nome;
        this.Cognome = cognome;
        this.email = email;
        this.password = password;
    }

    /**
     * Fare l autenticazione boolean.
     *
     * @param emailInserita    the email inserita
     * @param passwordInserita the password inserita
     * @return the boolean
     */
    public boolean fareLAutenticazione(String emailInserita, String passwordInserita) {
        return this.email.equals(emailInserita) && this.password.equals(passwordInserita);
    }

    /**
     * Aggiornare dati personali.
     *
     * @param nuovoNome     the nuovo nome
     * @param nuovoCognome  the nuovo cognome
     * @param nuovaPassword the nuova password
     */
    public void aggiornareDatiPersonali(String nuovoNome, String nuovoCognome, String nuovaPassword) {
        this.Nome = nuovoNome;
        this.Cognome = nuovoCognome;
        this.password = nuovaPassword;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() { return Nome; }

    /**
     * Sets nome.
     *
     * @param nome the nome
     */
    public void setNome(String nome) { Nome = nome; }

    /**
     * Gets cognome.
     *
     * @return the cognome
     */
    public String getCognome() { return Cognome; }

    /**
     * Sets cognome.
     *
     * @param cognome the cognome
     */
    public void setCognome(String cognome) { Cognome = cognome; }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() { return email; }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) { this.email = email; }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() { return password; }

    /**
     * Sets password.
     *
     * @param password the password
     */
    public void setPassword(String password) { this.password = password; }
}