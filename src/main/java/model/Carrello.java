package model;

/**
 * The type Carrello.
 */
public class Carrello {
    private Proiezione proiezione;
    private int quantita;
    private double prezzoTotale;

    /**
     * Instantiates a new Carrello.
     *
     * @param proiezione   the proiezione
     * @param quantita     the quantita
     * @param prezzoTotale the prezzo totale
     */
    public Carrello(Proiezione proiezione, int quantita, double prezzoTotale) {
        this.proiezione = proiezione;
        this.quantita = quantita;
        this.prezzoTotale = prezzoTotale;
    }

    /**
     * Gets proiezione.
     *
     * @return the proiezione
     */
    public Proiezione getProiezione() { return proiezione; }

    /**
     * Gets quantita.
     *
     * @return the quantita
     */
    public int getQuantita() { return quantita; }

    /**
     * Gets prezzo totale.
     *
     * @return the prezzo totale
     */
    public double getPrezzoTotale() { return prezzoTotale; }
}