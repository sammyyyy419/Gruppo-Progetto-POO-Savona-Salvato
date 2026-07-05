package model;

import java.util.Random;


public class Biglietto {
    private double prezzoFinale;
    private boolean valido;
    private Posto postoAssegnato;
    private Proiezione proiezione;
    private Prenotazione prenotazione;
    private String codiceUnivoco;

    /**
     * Crea una nuova istanza di Biglietto associata a una prenotazione e a una proiezione specifica.
     * Genera automaticamente un codice univoco identificativo.
     *
     * @param prezzoFinale   il costo finale del biglietto.
     * @param postoAssegnato l'oggetto {@link Posto} assegnato.
     * @param proiezione     l'oggetto {@link Proiezione} di riferimento.
     * @param prenotazione   l'oggetto {@link Prenotazione} a cui appartiene il biglietto.
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
     * Crea una nuova istanza di Biglietto semplificata, utilizzata per configurazioni preliminari.
     *
     * @param prezzoFinale   il costo finale del biglietto.
     * @param postoAssegnato l'oggetto {@link Posto} assegnato.
     */
    public Biglietto(double prezzoFinale, Posto postoAssegnato) {
        this.prezzoFinale = prezzoFinale;
        this.valido = false;
        this.postoAssegnato = postoAssegnato;
        this.codiceUnivoco = generaCodiceA8Cifre();
    }
    /**
     * Genera un codice numerico casuale composto da 8 cifre per identificare univocamente il biglietto.
     *
     * @return una stringa contenente il codice generato.
     */

    private String generaCodiceA8Cifre() {
        Random rand = new Random();
        int num = rand.nextInt(90000000) + 10000000;
        return String.valueOf(num);
    }
    /**
     * Applica una percentuale di sconto al prezzo del biglietto.
     *
     * @param percentualeSconto la percentuale da sottrarre al prezzo originale (0-100).
     */
    public void applicaSconto(double percentualeSconto) {
        if(percentualeSconto > 0 && percentualeSconto <= 100) {
            this.prezzoFinale = this.prezzoFinale - (this.prezzoFinale*(percentualeSconto/100));
        }
    }

    /**
     * Genera una rappresentazione testuale del titolo di ingresso per la stampa o la visualizzazione.
     *
     * @return una stringa formattata con i dettagli del biglietto.
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
     * Restituisce il posto assegnato al biglietto.
     * @return il {@link Posto} assegnato.
     */
    public Posto getPostoAssegnato() { return postoAssegnato; }

    /**
     * Imposta il posto assegnato al biglietto.
     * @param postoAssegnato il {@link Posto} da assegnare.
     */
    public void setPostoAssegnato(Posto postoAssegnato) { this.postoAssegnato = postoAssegnato; }

    /**
     * Restituisce la proiezione a cui si riferisce il biglietto.
     * @return l'oggetto {@link Proiezione}.
     */
    public Proiezione getProiezione() { return proiezione; }

    /**
     * Imposta la proiezione per questo biglietto.
     * @param proiezione l'oggetto {@link Proiezione} da associare.
     */
    public void setProiezione(Proiezione proiezione) { this.proiezione = proiezione; }

    /**
     * Restituisce la prenotazione associata.
     * @return l'oggetto {@link Prenotazione}.
     */
    public Prenotazione getPrenotazione() { return prenotazione; }

    /**
     * Imposta la prenotazione per questo biglietto.
     * @param prenotazione l'oggetto {@link Prenotazione} da associare.
     */
    public void setPrenotazione(Prenotazione prenotazione) { this.prenotazione = prenotazione; }

    /**
     * Verifica se il biglietto è stato convalidato.
     * @return {@code true} se valido, {@code false} altrimenti.
     */
    public boolean isValido() { return valido; }
    /**
     * Imposta lo stato di validità del biglietto.
     * @param valido lo stato di validità da impostare.
     */
    public void setValido(boolean valido) { this.valido = valido; }

    /**
     * Restituisce il prezzo finale del biglietto.
     * @return il prezzo come valore double.
     */
    public double getPrezzoFinale() { return prezzoFinale; }

    /**
     * Imposta il prezzo finale del biglietto.
     * @param prezzoFinale il nuovo prezzo.
     */
    public void setPrezzoFinale(double prezzoFinale) { this.prezzoFinale = prezzoFinale; }

    /**
     * Restituisce il codice univoco del biglietto.
     * @return il codice come stringa.
     */
    public String getCodiceUnivoco() { return codiceUnivoco; }

    /**
     * Imposta il codice univoco del biglietto.
     * @param codiceUnivoco il nuovo codice univoco.
     */
    public void setCodiceUnivoco(String codiceUnivoco) {this.codiceUnivoco = codiceUnivoco;}
}