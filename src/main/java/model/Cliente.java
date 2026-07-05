package model;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * The type Cliente.
 */
public class Cliente extends Utente {

    private LocalDate dataRegistrazione;
    private ArrayList<Prenotazione> prenotazioniEffettuate;

    /**
     * Instantiates a new Cliente.
     *
     * @param nome     the nome
     * @param cognome  the cognome
     * @param email    the email
     * @param password the password
     */
    public Cliente(String nome, String cognome, String email, String password) {
        super(nome, cognome, email, password);
        this.prenotazioniEffettuate = new ArrayList<>();
        this.dataRegistrazione = LocalDate.now();
    }

    /**
     * Effettua prenotazione.
     *
     * @param nuovaPrenotazione the nuova prenotazione
     */
    public void effettuaPrenotazione(Prenotazione nuovaPrenotazione) {
        if(nuovaPrenotazione != null) {
            this.getPrenotazioniEffettuate().add(nuovaPrenotazione);
            nuovaPrenotazione.setCliente(this);
        }
    }

    /**
     * Gets prenotazioni effettuate.
     *
     * @return the prenotazioni effettuate
     */
    public ArrayList<Prenotazione> getPrenotazioniEffettuate() {
        return prenotazioniEffettuate;
    }

    /**
     * Sets prenotazioni effettuate.
     *
     * @param prenotazioniEffettuate the prenotazioni effettuate
     */
    public void setPrenotazioniEffettuate(ArrayList<Prenotazione> prenotazioniEffettuate) {
        this.prenotazioniEffettuate = prenotazioniEffettuate;
    }

    /**
     * Gets data registrazione.
     *
     * @return the data registrazione
     */
    public LocalDate getDataRegistrazione() {
        return dataRegistrazione;
    }

    /**
     * Sets data registrazione.
     *
     * @param dataRegistrazione the data registrazione
     */
    public void setDataRegistrazione(LocalDate dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }
}