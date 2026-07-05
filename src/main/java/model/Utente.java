package model;

/**
 * The type Utente.
 */
public abstract class Utente {
    /** Il nome dell'utente. */
    protected String Nome;
    /** Il cognome dell'utente. */
    protected String Cognome;
    /** L'indirizzo email utilizzato per l'identificazione. */
    protected String email;
    /** La password di accesso associata all'account. */
    protected String password;

    /**
     * Costruttore protetto per le classi derivate.
     *
     * @param nome     il nome dell'utente.
     * @param cognome  il cognome dell'utente.
     * @param email    l'indirizzo email univoco.
     * @param password la password di sistema.
     */
    public Utente(String nome, String cognome, String email, String password) {
        this.Nome = nome;
        this.Cognome = cognome;
        this.email = email;
        this.password = password;
    }

    /**
     * Verifica se le credenziali fornite corrispondono a quelle dell'utente.
     *
     * @param emailInserita    l'email inserita per l'accesso.
     * @param passwordInserita la password inserita per l'accesso.
     * @return {@code true} se le credenziali coincidono, {@code false} altrimenti.
     */
    public boolean fareLAutenticazione(String emailInserita, String passwordInserita) {
        return this.email.equals(emailInserita) && this.password.equals(passwordInserita);
    }

    /**
     * Aggiorna le informazioni personali dell'utente.
     *
     * @param nuovoNome     il nuovo nome da impostare.
     * @param nuovoCognome  il nuovo cognome da impostare.
     * @param nuovaPassword la nuova password da impostare.
     */
    public void aggiornareDatiPersonali(String nuovoNome, String nuovoCognome, String nuovaPassword) {
        this.Nome = nuovoNome;
        this.Cognome = nuovoCognome;
        this.password = nuovaPassword;
    }

    // --- Getter e Setter ---

    /** @return il nome dell'utente. */
    public String getNome() { return Nome; }
    /** @param nome il nome da impostare. */
    public void setNome(String nome) { Nome = nome; }

    /** @return il cognome dell'utente. */
    public String getCognome() { return Cognome; }
    /** @param cognome il cognome da impostare. */
    public void setCognome(String cognome) { Cognome = cognome; }

    /** @return l'email dell'utente. */
    public String getEmail() { return email; }
    /** @param email l'email da impostare. */
    public void setEmail(String email) { this.email = email; }

    /** @return la password dell'utente. */
    public String getPassword() { return password; }
    /** @param password la password da impostare. */
    public void setPassword(String password) { this.password = password; }
}