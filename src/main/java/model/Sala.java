package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sala {
    private String numeroSala;
    private int capienza;
    private String tipoSala;
    private ArrayList<Posto> posti;
    private ArrayList<Proiezione> proiezioni;

    /**
     * Crea una nuova istanza di Sala, inizializzando automaticamente la disposizione dei posti
     * in base alla tipologia specificata (IMAX o Standard).
     *
     * @param numeroSala       l'identificativo della sala.
     * @param capienzaGenerica parametro di supporto per la creazione.
     * @param tipoSala         il tipo di tecnologia/configurazione (es. "IMAX").
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
     * Calcola il numero di posti ancora disponibili per una specifica proiezione,
     * sottraendo alla capienza totale i posti già riservati tramite prenotazioni confermate o pendenti.
     *
     * @param proiezioneCorrente la {@link Proiezione} di riferimento.
     * @return il numero di posti liberi residui.
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
     * Verifica se la sala è libera da impegni in un determinato intervallo temporale.
     *
     * @param inizio l'orario di inizio desiderato.
     * @param fine   l'orario di fine desiderato.
     * @return {@code true} se la sala è libera, {@code false} se risulta occupata da un'altra proiezione.
     */
    public boolean isLibera(LocalDateTime inizio, LocalDateTime fine) {
        for (Proiezione p : proiezioni) {
            if (inizio.isBefore(p.getDataOraFine()) && fine.isAfter(p.getDataOraInizio())) {
                return false;
            }
        }
        return true;
    }

    // --- Getter e Setter ---

    /** @return il numero della sala. */
    public String getNumeroSala() { return numeroSala; }
    /** @param numeroSala il numero della sala da impostare. */
    public void setNumeroSala(String numeroSala) { this.numeroSala = numeroSala; }

    /** @return la capienza totale della sala. */
    public int getCapienza() { return capienza; }
    /** @param capienza la capienza da impostare. */
    public void setCapienza(int capienza) { this.capienza = capienza; }

    /** @return il tipo di sala (es. IMAX). */
    public String getTipoSala() { return tipoSala; }
    /** @param tipoSala il tipo di sala da impostare. */
    public void setTipoSala(String tipoSala) { this.tipoSala = tipoSala; }

    /** @return la lista dei posti presenti nella sala. */
    public ArrayList<Posto> getPosti() { return posti; }
    /** @param posti la lista dei posti da assegnare. */
    public void setPosti(ArrayList<Posto> posti) { this.posti = posti; }

    /** @return la lista delle proiezioni programmate in questa sala. */
    public ArrayList<Proiezione> getProiezioni() { return proiezioni; }
    /** @param proiezioni la lista di proiezioni da assegnare. */
    public void setProiezioni(ArrayList<Proiezione> proiezioni) { this.proiezioni = proiezioni; }
}