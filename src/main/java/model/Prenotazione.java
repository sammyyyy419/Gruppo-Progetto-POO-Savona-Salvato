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

    // Fare i metodi: calcolo totale, confermare la prenotazione e aggiungere un posto.
}
