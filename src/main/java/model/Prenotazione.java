package model;
import java.time.LocalDateTime;

public class Prenotazione {

    private LocalDateTime dataPrenotazione;
    private StatoPrenotazione stato;

    public Prenotazione(LocalDateTime data, StatoPrenotazione stato)
    {
        this.dataPrenotazione = data;
        this.stato = stato;
    }

    public LocalDateTime getDataPrenotazione() {
        return dataPrenotazione;
    }

    public void setDataPrenotazione(LocalDateTime dataPrenotazione) {
        this.dataPrenotazione = dataPrenotazione;
    }

    public StatoPrenotazione getStato() {
        return stato;
    }

    public void setStato(StatoPrenotazione stato) {
        this.stato = stato;
    }

    // Fare i metodi: calcolo totale, confermare la prenotazione e aggiungere un posto.
}
