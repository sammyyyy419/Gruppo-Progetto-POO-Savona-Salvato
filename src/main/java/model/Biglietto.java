package model;

import java.util.Random;

public class Biglietto {
    private double prezzoFinale;
    private boolean valido; // false = Da convalidare, true = Convalidato all'ingresso
    private Posto postoAssegnato;
    private Proiezione proiezione;
    private Prenotazione prenotazione;
    private String codiceUnivoco;

    public Biglietto(double prezzoFinale, Posto postoAssegnato, Proiezione proiezione, Prenotazione prenotazione) {
        this.prezzoFinale = prezzoFinale;
        this.valido = false;
        this.postoAssegnato = postoAssegnato;
        this.proiezione = proiezione;
        this.prenotazione = prenotazione;
        this.codiceUnivoco = generaCodiceA8Cifre();
    }

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

    public void applicaSconto(double percentualeSconto) {
        if(percentualeSconto > 0 && percentualeSconto <= 100) {
            this.prezzoFinale = this.prezzoFinale - (this.prezzoFinale*(percentualeSconto/100));
        }
    }

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

    public Posto getPostoAssegnato() { return postoAssegnato; }
    public void setPostoAssegnato(Posto postoAssegnato) { this.postoAssegnato = postoAssegnato; }
    public Proiezione getProiezione() { return proiezione; }
    public void setProiezione(Proiezione proiezione) { this.proiezione = proiezione; }
    public Prenotazione getPrenotazione() { return prenotazione; }
    public void setPrenotazione(Prenotazione prenotazione) { this.prenotazione = prenotazione; }
    public boolean isValido() { return valido; }
    public void setValido(boolean valido) { this.valido = valido; }
    public double getPrezzoFinale() { return prezzoFinale; }
    public void setPrezzoFinale(double prezzoFinale) { this.prezzoFinale = prezzoFinale; }
    public String getCodiceUnivoco() { return codiceUnivoco; }
    public void setCodiceUnivoco(String codiceUnivoco) {this.codiceUnivoco = codiceUnivoco;}
}