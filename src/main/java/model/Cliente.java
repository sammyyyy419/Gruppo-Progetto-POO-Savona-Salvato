package model;

import java.time.LocalDate;
import java.util.ArrayList;


public class Cliente extends Utente {

    private LocalDate dataRegistrazione;
    private ArrayList<Prenotazione> prenotazioniEffettuate;

    /**
     * Crea una nuova istanza di Cliente.
     * La data di registrazione viene impostata automaticamente alla data odierna.
     *
     * @param nome     il nome del cliente.
     * @param cognome  il cognome del cliente.
     * @param email    l'indirizzo email utilizzato come identificativo.
     * @param password la password associata all'account.
     */
    public Cliente(String nome, String cognome, String email, String password) {
        super(nome, cognome, email, password);
        this.prenotazioniEffettuate = new ArrayList<>();
        this.dataRegistrazione = LocalDate.now();
    }

    /**
     * Aggiunge una nuova prenotazione allo storico del cliente.
     * Associa inoltre il cliente corrente alla prenotazione stessa.
     *
     * @param nuovaPrenotazione l'oggetto {@link Prenotazione} da aggiungere.
     */
    public void effettuaPrenotazione(Prenotazione nuovaPrenotazione) {
        if(nuovaPrenotazione != null) {
            this.getPrenotazioniEffettuate().add(nuovaPrenotazione);
            nuovaPrenotazione.setCliente(this);
        }
    }

    /**
     * Restituisce la lista delle prenotazioni effettuate dal cliente.
     *
     * @return un {@link ArrayList} di oggetti {@link Prenotazione}.
     */
    public ArrayList<Prenotazione> getPrenotazioniEffettuate() {
        return prenotazioniEffettuate;
    }

    /**
     * Imposta lo storico delle prenotazioni del cliente.
     *
     * @param prenotazioniEffettuate la lista di {@link Prenotazione} da assegnare.
     */
    public void setPrenotazioniEffettuate(ArrayList<Prenotazione> prenotazioniEffettuate) {
        this.prenotazioniEffettuate = prenotazioniEffettuate;
    }

    /**
     * Restituisce la data in cui il cliente si è registrato al servizio.
     *
     * @return un oggetto {@link LocalDate} rappresentante la data di registrazione.
     */
    public LocalDate getDataRegistrazione() {
        return dataRegistrazione;
    }
    /**
     * Imposta la data di registrazione del cliente.
     *
     * @param dataRegistrazione l'oggetto {@link LocalDate} da assegnare.
     */
    public void setDataRegistrazione(LocalDate dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }
}