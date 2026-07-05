package model;

/**
 * The type Posto.
 */
public class Posto {

    private int numeroPosto;
    private char fila;
    private boolean occupato;

    /**
     * Instantiates a new Posto.
     *
     * @param numero the numero
     * @param fila   the fila
     */
    public Posto(int numero, char fila) {
        this.numeroPosto = numero;
        this.fila = fila;
    }

    /**
     * Verificar se la poltrona e occupata boolean.
     *
     * @param proiezioneCorrente the proiezione corrente
     * @return the boolean
     */
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

    /**
     * Gets numero posto.
     *
     * @return the numero posto
     */
    public int getNumeroPosto() { return numeroPosto; }

    /**
     * Sets numero posto.
     *
     * @param numeroPosto the numero posto
     */
    public void setNumeroPosto(int numeroPosto) { this.numeroPosto = numeroPosto; }

    /**
     * Gets fila.
     *
     * @return the fila
     */
    public char getFila() { return fila; }

    /**
     * Sets fila.
     *
     * @param fila the fila
     */
    public void setFila(char fila) { this.fila = fila; }

    /**
     * Is occupato boolean.
     *
     * @return the boolean
     */
    public boolean isOccupato() { return occupato; }

    /**
     * Sets occupato.
     *
     * @param occupato the occupato
     */
    public void setOccupato(boolean occupato) { this.occupato = occupato; }
}