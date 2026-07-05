package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The type Prenotazione.
 */
public class Prenotazione {

    private LocalDateTime dataPrenotazione;
    private StatoPrenotazione stato;
    private Proiezione proiezione;
    private ArrayList<Biglietto> biglietti;

    private Cliente cliente;
    private Pagamento pagamento;

    /**
     * Instantiates a new Prenotazione.
     *
     * @param dataPrenotazione the data prenotazione
     * @param stato            the stato
     * @param proiezione       the proiezione
     * @param biglietti        the biglietti
     * @param cliente          the cliente
     * @param pagamento        the pagamento
     */
    public Prenotazione(LocalDateTime dataPrenotazione, StatoPrenotazione stato, Proiezione proiezione, ArrayList<Biglietto> biglietti, Cliente cliente, Pagamento pagamento) {
        this.dataPrenotazione = dataPrenotazione;
        this.stato = stato;
        this.proiezione = proiezione;
        this.biglietti = (biglietti != null) ? biglietti : new ArrayList<>();
        this.cliente = cliente;
        this.pagamento = pagamento;
    }

    /**
     * Gets numero biglietti.
     *
     * @return the numero biglietti
     */
    public int getNumeroBiglietti() {
        return this.biglietti.size();
    }

    /**
     * Verifica disponibilita posti in sala boolean.
     *
     * @return the boolean
     */
    public boolean verificaDisponibilitaPostiInSala() {
        if (this.proiezione == null) {
            return false;
        }

        int postiLiberi = this.proiezione.controllaPostiDisponibili();
        int numeroBigliettiRichiesti = this.getNumeroBiglietti();

        if (postiLiberi >= numeroBigliettiRichiesti) {
            return true;
        } else {
            this.stato = StatoPrenotazione.ANNULLATO;
            return false;
        }
    }

    /**
     * Inserisci biglietti.
     *
     * @param biglietto the biglietto
     */
    public void inserisciBiglietti(Biglietto biglietto) {
        if (biglietto != null) {
            this.biglietti.add(biglietto);
        }
    }

    /**
     * Conferma prenotazione.
     */
    public void confermaPrenotazione() {
        this.stato = StatoPrenotazione.CONFERMATO;
        if (this.proiezione != null) {
            this.proiezione.registraPrenotazione(this);
        }
    }

    /**
     * Aggiungi posto.
     */
    public void aggiungiPosto() {
        if (this.proiezione != null) {
            double prezzo = this.proiezione.getPrezzoBase();
            Biglietto nuovoBiglietto = new Biglietto(prezzo, null, this.proiezione, this);
            this.inserisciBiglietti(nuovoBiglietto);
        }
    }

    /**
     * Calcolo prezzo totale double.
     *
     * @return the double
     */
    public double calcoloPrezzoTotale() {
        double totale = 0.0;
        if (this.biglietti != null) {
            for (Biglietto b : this.biglietti) {
                totale += b.getPrezzoFinale();
            }
        }
        return totale;
    }

    /**
     * Gets data prenotazione.
     *
     * @return the data prenotazione
     */
    public LocalDateTime getDataPrenotazione() { return dataPrenotazione; }

    /**
     * Sets data prenotazione.
     *
     * @param dataPrenotazione the data prenotazione
     */
    public void setDataPrenotazione(LocalDateTime dataPrenotazione) { this.dataPrenotazione = dataPrenotazione; }

    /**
     * Gets stato.
     *
     * @return the stato
     */
    public StatoPrenotazione getStato() { return stato; }

    /**
     * Sets stato.
     *
     * @param stato the stato
     */
    public void setStato(StatoPrenotazione stato) { this.stato = stato; }

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
     * Gets biglietti.
     *
     * @return the biglietti
     */
    public ArrayList<Biglietto> getBiglietti() { return biglietti; }

    /**
     * Sets biglietti.
     *
     * @param biglietti the biglietti
     */
    public void setBiglietti(ArrayList<Biglietto> biglietti) { this.biglietti = biglietti; }

    /**
     * Gets cliente.
     *
     * @return the cliente
     */
    public Cliente getCliente() { return cliente; }

    /**
     * Sets cliente.
     *
     * @param cliente the cliente
     */
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    /**
     * Gets pagamento.
     *
     * @return the pagamento
     */
    public Pagamento getPagamento() { return pagamento; }

    /**
     * Sets pagamento.
     *
     * @param pagamento the pagamento
     */
    public void setPagamento(Pagamento pagamento) { this.pagamento = pagamento; }
}