package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The type Sala.
 */
public class Sala {
    private String numeroSala;
    private int capienza;
    private String tipoSala;
    private ArrayList<Posto> posti;
    private ArrayList<Proiezione> proiezioni;

    /**
     * Instantiates a new Sala.
     *
     * @param numeroSala       the numero sala
     * @param capienzaGenerica the capienza generica
     * @param tipoSala         the tipo sala
     */
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

    /**
     * Controllare capienza posti residua int.
     *
     * @param proiezioneCorrente the proiezione corrente
     * @return the int
     */
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

    /**
     * Is libera boolean.
     *
     * @param inizio the inizio
     * @param fine   the fine
     * @return the boolean
     */
    public boolean isLibera(LocalDateTime inizio, LocalDateTime fine) {
        for (Proiezione p : proiezioni) {
            if (inizio.isBefore(p.getDataOraFine()) && fine.isAfter(p.getDataOraInizio())) {
                return false;
            }
        }
        return true;
    }

    /**
     * Gets numero sala.
     *
     * @return the numero sala
     */
    public String getNumeroSala() { return numeroSala; }

    /**
     * Sets numero sala.
     *
     * @param numeroSala the numero sala
     */
    public void setNumeroSala(String numeroSala) { this.numeroSala = numeroSala; }

    /**
     * Gets capienza.
     *
     * @return the capienza
     */
    public int getCapienza() { return capienza; }

    /**
     * Sets capienza.
     *
     * @param capienza the capienza
     */
    public void setCapienza(int capienza) { this.capienza = capienza; }

    /**
     * Gets tipo sala.
     *
     * @return the tipo sala
     */
    public String getTipoSala() { return tipoSala; }

    /**
     * Sets tipo sala.
     *
     * @param tipoSala the tipo sala
     */
    public void setTipoSala(String tipoSala) { this.tipoSala = tipoSala; }

    /**
     * Gets posti.
     *
     * @return the posti
     */
    public ArrayList<Posto> getPosti() { return posti; }

    /**
     * Sets posti.
     *
     * @param posti the posti
     */
    public void setPosti(ArrayList<Posto> posti) { this.posti = posti; }

    /**
     * Gets proiezioni.
     *
     * @return the proiezioni
     */
    public ArrayList<Proiezione> getProiezioni() { return proiezioni; }

    /**
     * Sets proiezioni.
     *
     * @param proiezioni the proiezioni
     */
    public void setProiezioni(ArrayList<Proiezione> proiezioni) { this.proiezioni = proiezioni; }
}