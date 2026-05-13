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

    public LocalDateTime getDataOraInizio() {
        return dataOraInizio;
    }

    public void setDataOraInizio(LocalDateTime dataOraInizio) {
        this.dataOraInizio = dataOraInizio;
    }

    public LocalDateTime getDataOraFine() {
        return dataOraFine;
    }

    public void setDataOraFine(LocalDateTime dataOraFine) {
        this.dataOraFine = dataOraFine;
    }

    public double getPrezzoBase() {
        return prezzoBase;
    }

    public void setPrezzoBase(double prezzoBase) {
        this.prezzoBase = prezzoBase;
    }

    // Fare i metodi: Determinare la tariffa e visualizzare la disponibilità dei posti
}
