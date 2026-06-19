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
        // Imponiamo la tua regola fissa: 180 posti totali per ogni sala!
        this.capienza = 180;
        this.tipoSala = tipoSala;
        this.posti = new ArrayList<>();
        this.proiezioni = new ArrayList<>();

        // Generazione esatta delle poltrone: File da 'A' ad 'L' (12 file) e Posti da 1 a 15
        char filaCorrente = 'A';
        for (int i = 0; i < 12; i++) { // Ciclo per le 12 file
            for (int numPosto = 1; numPosto <= 15; numPosto++) { // Ciclo per i 15 posti per fila
                this.posti.add(new Posto(numPosto, filaCorrente));
            }
            filaCorrente++; // Passa alla lettera successiva (A -> B -> C...)
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