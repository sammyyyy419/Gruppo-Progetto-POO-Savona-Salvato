package model;

import java.time.LocalTime;

/**
 * The type Gestisce.
 */
public class Gestisce {
    private LocalTime orarioInizioTurno;
    private LocalTime orarioFineTurno;
    private String segnalazioni;

    private Dipendente dipendente;
    private Proiezione proiezione;

    /**
     * Instantiates a new Gestisce.
     *
     * @param orarioInizioTurno the orario inizio turno
     * @param orarioFineTurno   the orario fine turno
     * @param segnalazioni      the segnalazioni
     * @param dipendente        the dipendente
     * @param proiezione        the proiezione
     */
    public Gestisce(LocalTime orarioInizioTurno, LocalTime orarioFineTurno, String segnalazioni, Dipendente dipendente, Proiezione proiezione) {
        this.orarioInizioTurno = orarioInizioTurno;
        this.orarioFineTurno = orarioFineTurno;
        this.segnalazioni = segnalazioni;
        this.dipendente = dipendente;
        this.proiezione = proiezione;
    }

    /**
     * Definire ruolo operativo del dipendente in quel turno.
     *
     * @param ruolo the ruolo
     */
    public void definireRuoloOperativoDelDipendenteInQuelTurno(String ruolo) {
        this.segnalazioni += " [Ruolo: " + ruolo + "]";
    }

    /**
     * Registrare eventuali anomalie in sala.
     *
     * @param anomalia the anomalia
     */
    public void registrareEventualiAnomalieInSala(String anomalia) {
        this.segnalazioni = anomalia;
    }

    /**
     * Gets orario inizio turno.
     *
     * @return the orario inizio turno
     */
    public LocalTime getOrarioInizioTurno() { return orarioInizioTurno; }

    /**
     * Sets orario inizio turno.
     *
     * @param orarioInizioTurno the orario inizio turno
     */
    public void setOrarioInizioTurno(LocalTime orarioInizioTurno) { this.orarioInizioTurno = orarioInizioTurno; }

    /**
     * Gets orario fine turno.
     *
     * @return the orario fine turno
     */
    public LocalTime getOrarioFineTurno() { return orarioFineTurno; }

    /**
     * Sets orario fine turno.
     *
     * @param orarioFineTurno the orario fine turno
     */
    public void setOrarioFineTurno(LocalTime orarioFineTurno) { this.orarioFineTurno = orarioFineTurno; }

    /**
     * Gets segnalazioni.
     *
     * @return the segnalazioni
     */
    public String getSegnalazioni() { return segnalazioni; }

    /**
     * Sets segnalazioni.
     *
     * @param segnalazioni the segnalazioni
     */
    public void setSegnalazioni(String segnalazioni) { this.segnalazioni = segnalazioni; }

    /**
     * Gets dipendente.
     *
     * @return the dipendente
     */
    public Dipendente getDipendente() { return dipendente; }

    /**
     * Sets dipendente.
     *
     * @param dipendente the dipendente
     */
    public void setDipendente(Dipendente dipendente) { this.dipendente = dipendente; }

    /**
     * Gets proiezione.
     *
     * @return the proiezione
     */
    public Proiezione getProiezione() { return proiezione; }

    /**
     * Sets proiezione.
     *
     * @param proiezione the proiezione
     */
    public void setProiezione(Proiezione proiezione) { this.proiezione = proiezione; }
}