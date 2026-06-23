package model;

public class Carrello {
    private Proiezione proiezione;
    private int quantita;
    private double prezzoTotale;

    public Carrello(Proiezione proiezione, int quantita, double prezzoTotale) {
        this.proiezione = proiezione;
        this.quantita = quantita;
        this.prezzoTotale = prezzoTotale;
    }

    public Proiezione getProiezione() { return proiezione; }
    public int getQuantita() { return quantita; }
    public double getPrezzoTotale() { return prezzoTotale; }
}