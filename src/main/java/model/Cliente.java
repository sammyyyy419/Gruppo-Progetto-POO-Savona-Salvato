package model;

import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente extends Utente {

    private LocalDate dataRegistrazione;
    private ArrayList<Prenotazione> prenotazioniEffettuate;

    public Cliente(String nome, String cognome, String email, String password, LocalDate dataRegistrazione) {
        super(nome, cognome, email, password);
        this.dataRegistrazione = dataRegistrazione;
        this.prenotazioniEffettuate = new ArrayList<>();
    }

    public void effettuaPrenotazione(Prenotazione nuovaPrenotazione) {
        if(nuovaPrenotazione != null) {
            this.getPrenotazioniEffettuate().add(nuovaPrenotazione);
            nuovaPrenotazione.setCliente(this);
        }
    }

    public ArrayList<Prenotazione> getPrenotazioniEffettuate() {
        return prenotazioniEffettuate;
    }

    public void setPrenotazioniEffettuate(ArrayList<Prenotazione> prenotazioniEffettuate) {
        this.prenotazioniEffettuate = prenotazioniEffettuate;
    }

    public LocalDate getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(LocalDate dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }
}