package model;

/**
 * The type Posto.
 */
public class Posto {

    private int numeroPosto;
    private char fila;
    private boolean occupato;

    /**
     * Crea una nuova istanza di Posto.
     *
     * @param numero il numero identificativo del posto nella fila.
     * @param fila   il carattere identificativo della fila (es. 'A', 'B').
     */
    public Posto(int numero, char fila) {
        this.numeroPosto = numero;
        this.fila = fila;
    }

    /**
     * Verifica se il posto risulta occupato per una specifica proiezione.
     * Scansiona le prenotazioni confermate o pendenti associate alla proiezione
     * per controllare se il posto è già stato assegnato.
     *
     * @param proiezioneCorrente l'oggetto {@link Proiezione} di riferimento.
     * @return {@code true} se il posto è occupato, {@code false} altrimenti.
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
     * Restituisce il numero del posto.
     * @return il numero identificativo.
     */
    public int getNumeroPosto() { return numeroPosto; }

    /**
     * Imposta il numero del posto.
     * @param numeroPosto il numero da assegnare.
     */
    public void setNumeroPosto(int numeroPosto) { this.numeroPosto = numeroPosto; }

    /**
     * Restituisce la fila del posto.
     * @return il carattere della fila.
     */
    public char getFila() { return fila; }

    /**
     * Imposta la fila del posto.
     * @param fila il carattere della fila da assegnare.
     */
    public void setFila(char fila) { this.fila = fila; }

    /**
     * Verifica lo stato di occupazione locale del posto.
     * @return {@code true} se il posto è contrassegnato come occupato.
     */
    public boolean isOccupato() { return occupato; }

    /**
     * Imposta lo stato di occupazione locale del posto.
     * @param occupato lo stato da assegnare.
     */
    public void setOccupato(boolean occupato) { this.occupato = occupato; }
}