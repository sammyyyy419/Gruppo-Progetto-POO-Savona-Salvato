package model;

import java.util.Random;

/**
 * The type Biglietto.
 */
public class Biglietto {
    private double prezzoFinale;
    private boolean valido;
    private Posto postoAssegnato;
    private Proiezione proiezione;
    private Prenotazione prenotazione;
    private String codiceUnivoco;

    /**
     * Instantiates a new Biglietto.
     *
     * @param prezzoFinale   the prezzo finale
     * @param postoAssegnato the posto assegnato
     * @param proiezione     the proiezione
     * @param prenotazione   the prenotazione
     */
    public Biglietto(double prezzoFinale, Posto postoAssegnato, Proiezione proiezione, Prenotazione prenotazione) {
        this.prezzoFinale = prezzoFinale;
        this.valido = false;
        this.postoAssegnato = postoAssegnato;
        this.proiezione = proiezione;
        this.prenotazione = prenotazione;
        this.codiceUnivoco = generaCodiceA8Cifre();
    }

    /**
     * Instantiates a new Biglietto.
     *
     * @param prezzoFinale   the prezzo finale
     * @param postoAssegnato the posto assegnato
     */
    public Biglietto(double prezzoFinale, Posto postoAssegnato) {
        this.prezzoFinale = prezzoFinale;
        this.valido = false;
        this.postoAssegnato = postoAssegnato;
        this.codiceUnivoco = generaCodiceA8Cifre();
    }

    private String generaCodiceA8Cifre() {
        Random rand = new Random();
        int num = rand.nextInt(90000000) + 10000000;
        return String.valueOf(num);
    }

    /**
     * Applica sconto.
     *
     * @param percentualeSconto the percentuale sconto
     */
    public void applicaSconto(double percentualeSconto) {
        if(percentualeSconto > 0 && percentualeSconto <= 100) {
            this.prezzoFinale = this.prezzoFinale - (this.prezzoFinale*(percentualeSconto/100));
        }
    }

    /**
     * Genera titolo ingresso string.
     *
     * @return the string
     */
    public String generaTitoloIngresso() {
        String dettagliPosto = (postoAssegnato != null) ?
                "Fila: " + postoAssegnato.getFila() + " | Posto: " + postoAssegnato.getNumeroPosto() : "Non assegnato";
        String infoFilm = (proiezione != null) ? proiezione.getFilm().getTitolo() : "Film N/D";

        return "-----------------------------------\n" +
                "    TICKET ENTERPRISE CINEMA       \n" +
                "-----------------------------------\n" +
                " Film: " + infoFilm + "\n" +
                " " + dettagliPosto + "\n" +
                " Codice Ingresso: " + codiceUnivoco + "\n" +
                " Prezzo: €" + String.format("%.2f", prezzoFinale) + "\n" +
                " Stato: " + (valido ? "CONVALIDATO" : "DA CONVALIDARE") + "\n" +
                "----------------------------------";
    }

    /**
     * Gets posto assegnato.
     *
     * @return the posto assegnato
     */
    public Posto getPostoAssegnato() { return postoAssegnato; }

    /**
     * Sets posto assegnato.
     *
     * @param postoAssegnato the posto assegnato
     */
    public void setPostoAssegnato(Posto postoAssegnato) { this.postoAssegnato = postoAssegnato; }

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

    /**
     * Gets prenotazione.
     *
     * @return the prenotazione
     */
    public Prenotazione getPrenotazione() { return prenotazione; }

    /**
     * Sets prenotazione.
     *
     * @param prenotazione the prenotazione
     */
    public void setPrenotazione(Prenotazione prenotazione) { this.prenotazione = prenotazione; }

    /**
     * Is valido boolean.
     *
     * @return the boolean
     */
    public boolean isValido() { return valido; }

    /**
     * Sets valido.
     *
     * @param valido the valido
     */
    public void setValido(boolean valido) { this.valido = valido; }

    /**
     * Gets prezzo finale.
     *
     * @return the prezzo finale
     */
    public double getPrezzoFinale() { return prezzoFinale; }

    /**
     * Sets prezzo finale.
     *
     * @param prezzoFinale the prezzo finale
     */
    public void setPrezzoFinale(double prezzoFinale) { this.prezzoFinale = prezzoFinale; }

    /**
     * Gets codice univoco.
     *
     * @return the codice univoco
     */
    public String getCodiceUnivoco() { return codiceUnivoco; }

    /**
     * Sets codice univoco.
     *
     * @param codiceUnivoco the codice univoco
     */
    public void setCodiceUnivoco(String codiceUnivoco) {this.codiceUnivoco = codiceUnivoco;}
}