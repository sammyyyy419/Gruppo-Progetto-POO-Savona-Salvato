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

    /**
     * Crea una nuova istanza di Proiezione.
     *
     * @param dataOraInizio la data e ora di inizio della proiezione.
     * @param dataOraFine   la data e ora di fine della proiezione.
     * @param prezzoBase    il prezzo base del biglietto per questa proiezione.
     * @param film          l'oggetto {@link Film} proiettato.
     * @param sala          l'oggetto {@link Sala} in cui avviene la proiezione.
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
     * Calcola il numero di posti ancora disponibili nella sala per questa proiezione.
     *
     * @return il numero di posti residui disponibili.
     */
    public int controllaPostiDisponibili() {
        if (this.sala == null) {
            return 0;
        }
        return this.sala.controllareCapienzaPostiResidua(this);
    }

    /**
     * Registra una nuova prenotazione confermata per questa proiezione.
     *
     * @param p l'oggetto {@link Prenotazione} da aggiungere.
     */
    public void registraPrenotazione(Prenotazione p) {
        if (p != null) {
            this.prenotazioniRicevute.add(p);
        }
    }

    /** @return la data e ora di inizio. */
    public LocalDateTime getDataOraInizio() { return dataOraInizio; }
    /** @param dataOraInizio imposta la data e ora di inizio. */
    public void setDataOraInizio(LocalDateTime dataOraInizio) { this.dataOraInizio = dataOraInizio; }

    /** @return la data e ora di fine. */
    public LocalDateTime getDataOraFine() { return dataOraFine; }
    /** @param dataOraFine imposta la data e ora di fine. */
    public void setDataOraFine(LocalDateTime dataOraFine) { this.dataOraFine = dataOraFine; }

    /**
     * Restituisce una rappresentazione testuale della data e ora di inizio formattata.
     * @return stringa nel formato "dd/MM/yyyy HH:mm".
     */
    @Override
    public String toString() {
        if (this.dataOraInizio != null) {
            java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
            return this.dataOraInizio.format(formatter);
        }
        return "Data non disponibile";
    }

    /** @return il prezzo base. */
    public double getPrezzoBase() { return prezzoBase; }
    /** @param prezzoBase imposta il prezzo base. */
    public void setPrezzoBase(double prezzoBase) { this.prezzoBase = prezzoBase; }

    /** @return il film proiettato. */
    public Film getFilm() { return film; }
    /** @param film imposta il film proiettato. */
    public void setFilm(Film film) { this.film = film; }

    /** @return la sala della proiezione. */
    public Sala getSala() { return sala; }
    /** @param sala imposta la sala. */
    public void setSala(Sala sala) { this.sala = sala; }

    /** @return la lista delle prenotazioni. */
    public ArrayList<Prenotazione> getPrenotazioniRicevute() { return prenotazioniRicevute; }
    /** @param prenotazioniRicevute imposta la lista delle prenotazioni. */
    public void setPrenotazioniRicevute(ArrayList<Prenotazione> prenotazioniRicevute) { this.prenotazioniRicevute = prenotazioniRicevute; }
}