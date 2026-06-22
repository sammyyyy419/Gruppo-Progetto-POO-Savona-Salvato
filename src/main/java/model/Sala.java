package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sala {
    private String numeroSala;
    private int capienza;
    private String tipoSala;
    private ArrayList<Posto> posti;
    private ArrayList<Proiezione> proiezioni;

    public Sala(String numeroSala, int capienzaGenerica, String tipoSala) {
        this.numeroSala = numeroSala;
        this.tipoSala = tipoSala;

        if ("IMAX".equalsIgnoreCase(tipoSala)) {
            this.capienza = 80;
        } else {
            this.capienza = 180;
        }

        this.posti = new ArrayList<>();
        this.proiezioni = new ArrayList<>();

        int numFile = ("IMAX".equalsIgnoreCase(tipoSala)) ? 8 : 12;
        int postiPerFila = ("IMAX".equalsIgnoreCase(tipoSala)) ? 10 : 15;

        char filaCorrente = 'A';
        for (int i = 0; i < numFile; i++) {
            for (int numPosto = 1; numPosto <= postiPerFila; numPosto++) {
                this.posti.add(new Posto(numPosto, filaCorrente));
            }
            filaCorrente++;
        }
    }

    public int controllareCapienzaPostiResidua(Proiezione proiezioneCorrente) {
        if (proiezioneCorrente == null) return this.capienza;
        int postiOccupati = 0;
        for (Prenotazione p : proiezioneCorrente.getPrenotazioniRicevute()) {
            if (p.getStato() == StatoPrenotazione.CONFERMATO || p.getStato() == StatoPrenotazione.PENDENTE) {
                postiOccupati += p.getBiglietti().size();
            }
        }
        return this.capienza - postiOccupati;
    }

    public boolean isLibera(LocalDateTime inizio, LocalDateTime fine) {
        for (Proiezione p : proiezioni) {
            if (inizio.isBefore(p.getDataOraFine()) && fine.isAfter(p.getDataOraInizio())) {
                return false;
            }
        }
        return true;
    }

    public String getNumeroSala() { return numeroSala; }
    public void setNumeroSala(String numeroSala) { this.numeroSala = numeroSala; }
    public int getCapienza() { return capienza; }
    public void setCapienza(int capienza) { this.capienza = capienza; }
    public String getTipoSala() { return tipoSala; }
    public void setTipoSala(String tipoSala) { this.tipoSala = tipoSala; }
    public ArrayList<Posto> getPosti() { return posti; }
    public void setPosti(ArrayList<Posto> posti) { this.posti = posti; }
    public ArrayList<Proiezione> getProiezioni() { return proiezioni; }
    public void setProiezioni(ArrayList<Proiezione> proiezioni) { this.proiezioni = proiezioni; }
}