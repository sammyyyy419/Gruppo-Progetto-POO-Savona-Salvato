package model;
import java.time.LocalDateTime;

public class Proiezione {

    private LocalDateTime dataOraInizio;
    private LocalDateTime dataOraFine;
    private double prezzoBase;

    public Proiezione(LocalDateTime dataOraInizio,LocalDateTime dataOraFine, double prezzoBase)
    {
        this.dataOraInizio = dataOraInizio;
        this.dataOraFine = dataOraFine;
        this.prezzoBase = prezzoBase;
    }

    // Fare i metodi: Determinare la tariffa e visualizzare la disponibilità dei posti
}
