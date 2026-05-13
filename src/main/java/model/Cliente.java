package model;
import java.time.LocalDate;

public class Cliente extends Utente {

    private LocalDate dataRegistrazione;

    public Cliente (String nomeCliente, String cognomeCliente, String emailCliente, String passwordCliente, LocalDate dataRegistrazioneC)
    {
        super(nomeCliente,cognomeCliente,emailCliente,passwordCliente);
        this.dataRegistrazione = dataRegistrazioneC;
        // this.dataRegistrazione = LocalDate.now();   Questo è per impostare la data di registrazione da oggi.
    }

    public LocalDate getDataRegistrazione() {
        return dataRegistrazione;
    }

    public void setDataRegistrazione(LocalDate dataRegistrazione) {
        this.dataRegistrazione = dataRegistrazione;
    }

    // Fare il metodo per iniziare una procedura per l'acquisto
}
