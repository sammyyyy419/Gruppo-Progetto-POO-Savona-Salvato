package model;

public class Biglietto {
    private double prezzoFinale;
    private boolean valido;
    private Posto postoAssegnato;
    private Proiezione proiezione;
    private Prenotazione prenotazione;

    public Biglietto(double prezzoFinale, Posto postoAssegnato, Proiezione proiezione, Prenotazione prenotazione) {
        this.prezzoFinale = prezzoFinale;
        this.valido = false;
        this.postoAssegnato = postoAssegnato;
        this.proiezione = proiezione;
        this.prenotazione = prenotazione;
    }

    public Biglietto(double prezzoFinale, Posto postoAssegnato) {
        this.prezzoFinale = prezzoFinale;
        this.valido = false;
        this.postoAssegnato = postoAssegnato;
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
                " Prezzo: €" + String.format("%.2f", prezzoFinale) + "\n" +
                " Stato: " + (valido ? "OBLITERATO" : "VALIDO") + "\n" +
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
}