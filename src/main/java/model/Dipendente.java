package model;

import java.time.LocalDateTime;

/**
 * The type Dipendente.
 */
public class Dipendente extends Utente {

    private double stipendio;
    private String ruolo;

    /**
     * Instantiates a new Dipendente.
     *
     * @param nomeDipendente     the nome dipendente
     * @param cognomeDipendente  the cognome dipendente
     * @param emailDipendente    the email dipendente
     * @param passwordDipendente the password dipendente
     * @param ruolo              the ruolo
     */
    public Dipendente(String nomeDipendente, String cognomeDipendente, String emailDipendente, String passwordDipendente, String ruolo) {
        super(nomeDipendente, cognomeDipendente, emailDipendente, passwordDipendente);
        this.ruolo = ruolo;
        switch (ruolo.toLowerCase()) {
            case "cassiere":
                this.stipendio = 1200.00;
                break;
            case "manager":
                this.stipendio = 1800.00;
                break;
            case "proiezionista":
                this.stipendio = 1400.00;
                break;
            default:
                this.stipendio = 1000.00;
                break;
        }
    }

    /**
     * Pianificare programmazione dei film.
     */
    public void pianificareProgrammazioneDeiFilm(){}

    /**
     * Valida biglietto boolean.
     *
     * @param biglietto the biglietto
     * @return the boolean
     */
    public boolean validaBiglietto(Biglietto biglietto) {
        if (biglietto == null) return false;
        if (biglietto.isValido()) return false;
        biglietto.setValido(true);
        return true;
    }

    /**
     * Gets stipendio.
     *
     * @return the stipendio
     */
    public double getStipendio() {
        return stipendio;
    }

    /**
     * Sets stipendio.
     *
     * @param stipendio the stipendio
     */
    public void setStipendio(double stipendio) {
        this.stipendio = stipendio;
    }

    /**
     * Gets ruolo.
     *
     * @return the ruolo
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Sets ruolo.
     *
     * @param ruolo the ruolo
     */
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}