package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Proiezione {

    private LocalDateTime dataOraInizio;
    private LocalDateTime dataOraFine;
    private double prezzoBase;
    private Film film;
    private Sala sala;
    private ArrayList<Prenotazione> prenotazioniRicevute;

    public Proiezione(LocalDateTime dataOraInizio, LocalDateTime dataOraFine, double prezzoBase, Film film, Sala sala) {
        this.dataOraInizio = dataOraInizio;
        this.dataOraFine = dataOraFine;
        this.prezzoBase = prezzoBase;
        this.film = film;
        this.sala = sala;
        this.prenotazioniRicevute = new ArrayList<>();
    }

    public int controllaPostiDisponibili() {
        if (this.sala == null) {
            return 0;
        }
        return this.sala.controllareCapienzaPostiResidua(this);
    }

    public void registraPrenotazione(Prenotazione p) {
        if (p != null) {
            this.prenotazioniRicevute.add(p);
        }
    }

    public LocalDateTime getDataOraInizio() { return dataOraInizio; }
    public void setDataOraInizio(LocalDateTime dataOraInizio) { this.dataOraInizio = dataOraInizio; }
    public LocalDateTime getDataOraFine() { return dataOraFine; }
    public void setDataOraFine(LocalDateTime dataOraFine) { this.dataOraFine = dataOraFine; }
    @Override
    public String toString() {
        if (this.dataOraInizio != null) {
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return this.dataOraInizio.format(formatter);
        }
        return "Data non disponibile";
    }
    public double getPrezzoBase() { return prezzoBase; }
    public void setPrezzoBase(double prezzoBase) { this.prezzoBase = prezzoBase; }
    public Film getFilm() { return film; }
    public void setFilm(Film film) { this.film = film; }
    public Sala getSala() { return sala; }
    public void setSala(Sala sala) { this.sala = sala; }
    public ArrayList<Prenotazione> getPrenotazioniRicevute() { return prenotazioniRicevute; }
    public void setPrenotazioniRicevute(ArrayList<Prenotazione> prenotazioniRicevute) { this.prenotazioniRicevute = prenotazioniRicevute; }
}