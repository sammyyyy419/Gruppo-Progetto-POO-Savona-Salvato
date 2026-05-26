package model;

import java.time.LocalTime;

public class Gestisce {
    private LocalTime orarioInizioTurno;
    private LocalTime orarioFineTurno;
    private String segnalazioni;

    private Dipendente dipendente;
    private Proiezione proiezione;

    public Gestisce(LocalTime orarioInizioTurno, LocalTime orarioFineTurno, String segnalazioni, Dipendente dipendente, Proiezione proiezione) {
        this.orarioInizioTurno = orarioInizioTurno;
        this.orarioFineTurno = orarioFineTurno;
        this.segnalazioni = segnalazioni;
        this.dipendente = dipendente;
        this.proiezione = proiezione;
    }

    public void definireRuoloOperativoDelDipendenteInQuelTurno(String ruolo) {
        this.segnalazioni += " [Ruolo: " + ruolo + "]";
    }

    public void registrareEventualiAnomalieInSala(String anomalia) {
        this.segnalazioni = anomalia;
    }

    public LocalTime getOrarioInizioTurno() { return orarioInizioTurno; }
    public void setOrarioInizioTurno(LocalTime orarioInizioTurno) { this.orarioInizioTurno = orarioInizioTurno; }
    public LocalTime getOrarioFineTurno() { return orarioFineTurno; }
    public void setOrarioFineTurno(LocalTime orarioFineTurno) { this.orarioFineTurno = orarioFineTurno; }
    public String getSegnalazioni() { return segnalazioni; }
    public void setSegnalazioni(String segnalazioni) { this.segnalazioni = segnalazioni; }
    public Dipendente getDipendente() { return dipendente; }
    public void setDipendente(Dipendente dipendente) { this.dipendente = dipendente; }
    public Proiezione getProiezione() { return proiezione; }
    public void setProiezione(Proiezione proiezione) { this.proiezione = proiezione; }
}