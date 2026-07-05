package model;

import java.time.LocalDateTime;


public class Dipendente extends Utente {

    private double stipendio;
    private String ruolo;

    /**
     * Crea una nuova istanza di Dipendente.
     * Il valore dello stipendio viene assegnato automaticamente in base al ruolo specificato.
     *
     * @param nomeDipendente     il nome del dipendente.
     * @param cognomeDipendente  il cognome del dipendente.
     * @param emailDipendente    l'email aziendale del dipendente.
     * @param passwordDipendente la password di accesso.
     * @param ruolo              il ruolo lavorativo (es. "cassiere", "manager", "proiezionista").
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
     * Valida un biglietto d'ingresso se non è già stato convalidato.
     *
     * @param biglietto l'oggetto {@link Biglietto} da convalidare.
     * @return {@code true} se la validazione è avvenuta con successo, {@code false} se il biglietto è nullo o già valido.
     */
    public boolean validaBiglietto(Biglietto biglietto) {
        if (biglietto == null) return false;
        if (biglietto.isValido()) return false;
        biglietto.setValido(true);
        return true;
    }

    /**
     * Restituisce lo stipendio del dipendente.
     * @return il valore dello stipendio.
     */
    public double getStipendio() {
        return stipendio;
    }

    /**
     * Imposta un nuovo stipendio per il dipendente.
     * @param stipendio il valore dello stipendio da assegnare.
     */
    public void setStipendio(double stipendio) {
        this.stipendio = stipendio;
    }

    /**
     * Restituisce il ruolo lavorativo del dipendente.
     * @return una stringa rappresentante il ruolo.
     */
    public String getRuolo() {
        return ruolo;
    }

    /**
     * Imposta il ruolo lavorativo del dipendente.
     * @param ruolo il nuovo ruolo da assegnare.
     */
    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }
}