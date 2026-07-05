package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The type Proiezione.
 */
public class Proiezione {

    private LocalDateTime dataOraInizio;
    private LocalDateTime dataOraFine;
    private double prezzoBase;
    private Film film;
    private Sala sala;
    private ArrayList<Prenotazione> prenotazioniRicevute;

    /**
     * Instantiates a new Proiezione.
     *
     * @param dataOraInizio the data ora inizio
     * @param dataOraFine   the data ora fine
     * @param prezzoBase    the prezzo base
     * @param film          the film
     * @param sala          the sala
     */
    public Proiezione(LocalDateTime dataOraInizio, LocalDateTime dataOraFine, double prezzoBase, Film film, Sala sala) {
        this.dataOraInizio = dataOraInizio;
        this.dataOraFine = dataOraFine;
        this.prezzoBase = prezzoBase;
        this.film = film;
        this.sala = sala;
        this.prenotazioniRicevute = new ArrayList<>();
    }

    /**
     * Controlla posti disponibili int.
     *
     * @return the int
     */
    public int controllaPostiDisponibili() {
        if (this.sala == null) {
            return 0;
        }
        return this.sala.controllareCapienzaPostiResidua(this);
    }

    /**
     * Registra prenotazione.
     *
     * @param p the p
     */
    public void registraPrenotazione(Prenotazione p) {
        if (p != null) {
            this.prenotazioniRicevute.add(p);
        }
    }

    /**
     * Gets data ora inizio.
     *
     * @return the data ora inizio
     */
    public LocalDateTime getDataOraInizio() { return dataOraInizio; }

    /**
     * Sets data ora inizio.
     *
     * @param dataOraInizio the data ora inizio
     */
    public void setDataOraInizio(LocalDateTime dataOraInizio) { this.dataOraInizio = dataOraInizio; }

    /**
     * Gets data ora fine.
     *
     * @return the data ora fine
     */
    public LocalDateTime getDataOraFine() { return dataOraFine; }

    /**
     * Sets data ora fine.
     *
     * @param dataOraFine the data ora fine
     */
    public void setDataOraFine(LocalDateTime dataOraFine) { this.dataOraFine = dataOraFine; }
    @Override
    public String toString() {
        if (this.dataOraInizio != null) {
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return this.dataOraInizio.format(formatter);
        }
        return "Data non disponibile";
    }

    /**
     * Gets prezzo base.
     *
     * @return the prezzo base
     */
    public double getPrezzoBase() { return prezzoBase; }

    /**
     * Sets prezzo base.
     *
     * @param prezzoBase the prezzo base
     */
    public void setPrezzoBase(double prezzoBase) { this.prezzoBase = prezzoBase; }

    /**
     * Gets film.
     *
     * @return the film
     */
    public Film getFilm() { return film; }

    /**
     * Sets film.
     *
     * @param film the film
     */
    public void setFilm(Film film) { this.film = film; }

    /**
     * Gets sala.
     *
     * @return the sala
     */
    public Sala getSala() { return sala; }

    /**
     * Sets sala.
     *
     * @param sala the sala
     */
    public void setSala(Sala sala) { this.sala = sala; }

    /**
     * Gets prenotazioni ricevute.
     *
     * @return the prenotazioni ricevute
     */
    public ArrayList<Prenotazione> getPrenotazioniRicevute() { return prenotazioniRicevute; }

    /**
     * Sets prenotazioni ricevute.
     *
     * @param prenotazioniRicevute the prenotazioni ricevute
     */
    public void setPrenotazioniRicevute(ArrayList<Prenotazione> prenotazioniRicevute) { this.prenotazioniRicevute = prenotazioniRicevute; }
}