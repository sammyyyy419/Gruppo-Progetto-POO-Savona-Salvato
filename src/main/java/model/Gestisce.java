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
     * Crea una nuova istanza di Gestisce per associare un dipendente a una specifica proiezione.
     *
     * @param orarioInizioTurno l'orario di inizio del turno lavorativo.
     * @param orarioFineTurno   l'orario di fine del turno lavorativo.
     * @param segnalazioni      eventuali note o segnalazioni iniziali.
     * @param dipendente        l'oggetto {@link Dipendente} assegnato al turno.
     * @param proiezione        l'oggetto {@link Proiezione} gestita durante il turno.
     */
    public Gestisce(LocalTime orarioInizioTurno, LocalTime orarioFineTurno, String segnalazioni, Dipendente dipendente, Proiezione proiezione) {
        this.orarioInizioTurno = orarioInizioTurno;
        this.orarioFineTurno = orarioFineTurno;
        this.segnalazioni = segnalazioni;
        this.dipendente = dipendente;
        this.proiezione = proiezione;
    }

    /**
     * Definisce e concatena il ruolo operativo ricoperto dal dipendente durante questo specifico turno.
     *
     * @param ruolo il ruolo lavorativo da assegnare (es. "biglietteria", "controllo sala").
     */
    public void definireRuoloOperativoDelDipendenteInQuelTurno(String ruolo) {
        this.segnalazioni += " [Ruolo: " + ruolo + "]";
    }

    /**
     * Registra o sovrascrive eventuali anomalie riscontrate durante il turno di lavoro.
     *
     * @param anomalia una stringa descrittiva dell'anomalia rilevata in sala.
     */
    public void registrareEventualiAnomalieInSala(String anomalia) {
        this.segnalazioni = anomalia;
    }

    /** @return l'orario di inizio del turno. */
    public LocalTime getOrarioInizioTurno() { return orarioInizioTurno; }
    /** @param orarioInizioTurno imposta l'orario di inizio turno. */
    public void setOrarioInizioTurno(LocalTime orarioInizioTurno) { this.orarioInizioTurno = orarioInizioTurno; }

    /** @return l'orario di fine del turno. */
    public LocalTime getOrarioFineTurno() { return orarioFineTurno; }
    /** @param orarioFineTurno imposta l'orario di fine turno. */
    public void setOrarioFineTurno(LocalTime orarioFineTurno) { this.orarioFineTurno = orarioFineTurno; }

    /** @return le segnalazioni registrate. */
    public String getSegnalazioni() { return segnalazioni; }
    /** @param segnalazioni imposta le segnalazioni per il turno. */
    public void setSegnalazioni(String segnalazioni) { this.segnalazioni = segnalazioni; }

    /** @return il dipendente assegnato al turno. */
    public Dipendente getDipendente() { return dipendente; }
    /** @param dipendente associa il dipendente al turno. */
    public void setDipendente(Dipendente dipendente) { this.dipendente = dipendente; }

    /** @return la proiezione gestita. */
    public Proiezione getProiezione() { return proiezione; }
    /** @param proiezione associa la proiezione al turno. */
    public void setProiezione(Proiezione proiezione) { this.proiezione = proiezione; }
}