package model;

public class Posto {

    private int numeroPosto;
    private char fila;
    private boolean occupato;

    public Posto(int numero, char fila) {
        this.numeroPosto = numero;
        this.fila = fila;
    }

    public boolean verificarSeLaPoltronaEOccupata(Proiezione proiezioneCorrente) {
        if (proiezioneCorrente == null || proiezioneCorrente.getPrenotazioniRicevute() == null) {
            return false;
        }
        for (Prenotazione p : proiezioneCorrente.getPrenotazioniRicevute()) {
            if (p.getStato() == StatoPrenotazione.CONFERMATO || p.getStato() == StatoPrenotazione.PENDENTE) {
                for (Biglietto b : p.getBiglietti()) {
                    if (b.getPostoAssegnato() != null &&
                            b.getPostoAssegnato().getFila() == this.fila &&
                            b.getPostoAssegnato().getNumeroPosto() == this.numeroPosto) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public int getNumeroPosto() { return numeroPosto; }
    public void setNumeroPosto(int numeroPosto) { this.numeroPosto = numeroPosto; }
    public char getFila() { return fila; }
    public void setFila(char fila) { this.fila = fila; }
    public boolean isOccupato() { return occupato; }
    public void setOccupato(boolean occupato) { this.occupato = occupato; }
}