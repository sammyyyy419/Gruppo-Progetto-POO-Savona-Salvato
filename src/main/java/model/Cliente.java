package model;
import java.time.LocalDate;
import java.util.ArrayList;

public class Cliente extends Utente {

    private LocalDate dataRegistrazione;

    public Cliente(String nome, String cognome, String email, String password, ArrayList<Prenotazione> prenotazioniEffettuate, LocalDate dataRegistrazione) {
        super(nome, cognome, email, password, prenotazioniEffettuate);
        this.dataRegistrazione = dataRegistrazione;
    }

    public LocalDate getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(LocalDate dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }


}
