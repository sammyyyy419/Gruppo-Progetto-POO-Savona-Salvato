package model;

/**
 * The type Carrello.
 */
public class Carrello {
    private Proiezione proiezione;
    private int quantita;
    private double prezzoTotale;

    /**
     * Crea una nuova istanza di un elemento Carrello.
     *
     * @param proiezione   l'oggetto {@link Proiezione} relativo ai biglietti selezionati.
     * @param quantita     il numero di biglietti desiderati.
     * @param prezzoTotale il costo complessivo calcolato per questa selezione.
     */
    public Carrello(Proiezione proiezione, int quantita, double prezzoTotale) {
        this.proiezione = proiezione;
        this.quantita = quantita;
        this.prezzoTotale = prezzoTotale;
    }

    /**
     * Restituisce la proiezione associata a questo elemento del carrello.
     * * @return l'oggetto {@link Proiezione} selezionato.
     */
    public Proiezione getProiezione() { return proiezione; }

    /**
     * Restituisce la quantità di biglietti selezionati per questa proiezione.
     * * @return il numero di biglietti.
     */
    public int getQuantita() { return quantita; }

    /**
     * Restituisce il prezzo totale calcolato per questa voce del carrello.
     * * @return il prezzo totale come valore double.
     */
    public double getPrezzoTotale() { return prezzoTotale; }
}