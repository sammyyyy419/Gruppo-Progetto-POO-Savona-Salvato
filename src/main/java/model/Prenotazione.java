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
     * Crea una nuova istanza di Prenotazione.
     *
     * @param dataPrenotazione la data e l'ora in cui viene effettuata la prenotazione.
     * @param stato            lo stato iniziale della prenotazione (es. PENDENTE, CONFERMATO).
     * @param proiezione       la {@link Proiezione} a cui la prenotazione si riferisce.
     * @param biglietti        la lista di oggetti {@link Biglietto} associati.
     * @param cliente          il {@link Cliente} che effettua la prenotazione.
     * @param pagamento        l'oggetto {@link Pagamento} relativo alla transazione.
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
     * Restituisce il numero totale di biglietti contenuti nella prenotazione.
     *
     * @return il conteggio dei biglietti.
     */
    public int getNumeroBiglietti() {
        return this.biglietti.size();
    }

    /**
     * Verifica se la sala associata alla proiezione ha ancora disponibilità di posti
     * sufficienti per soddisfare la quantità di biglietti richiesti.
     * In caso di indisponibilità, lo stato della prenotazione viene impostato ad ANNULLATO.
     *
     * @return {@code true} se i posti sono disponibili, {@code false} altrimenti.
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
     * Aggiunge un biglietto alla lista dei biglietti della prenotazione.
     *
     * @param biglietto l'oggetto {@link Biglietto} da inserire.
     */
    public void inserisciBiglietti(Biglietto biglietto) {
        if (biglietto != null) {
            this.biglietti.add(biglietto);
        }
    }

    /**
     * Conferma la prenotazione, aggiornando il suo stato e registrandola presso la proiezione.
     */
    public void confermaPrenotazione() {
        this.stato = StatoPrenotazione.CONFERMATO;
        if (this.proiezione != null) {
            this.proiezione.registraPrenotazione(this);
        }
    }

    /**
     * Crea un nuovo biglietto basato sul prezzo base della proiezione e lo aggiunge alla prenotazione.
     */
    public void aggiungiPosto() {
        if (this.proiezione != null) {
            double prezzo = this.proiezione.getPrezzoBase();
            Biglietto nuovoBiglietto = new Biglietto(prezzo, null, this.proiezione, this);
            this.inserisciBiglietti(nuovoBiglietto);
        }
    }

    /**
     * Calcola il prezzo totale della prenotazione sommando il prezzo finale di tutti i biglietti inclusi.
     *
     * @return il costo totale della prenotazione.
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

    // Metodi Getter e Setter
    /** @return la data della prenotazione. */
    public LocalDateTime getDataPrenotazione() { return dataPrenotazione; }
    /** @param dataPrenotazione la data da impostare. */
    public void setDataPrenotazione(LocalDateTime dataPrenotazione) { this.dataPrenotazione = dataPrenotazione; }

    /** @return lo stato attuale della prenotazione. */
    public StatoPrenotazione getStato() { return stato; }
    /** @param stato lo stato da impostare. */
    public void setStato(StatoPrenotazione stato) { this.stato = stato; }

    /** @return la proiezione associata. */
    public Proiezione getProiezione() { return proiezione; }
    /** @param proiezione la proiezione da associare. */
    public void setProiezione(Proiezione proiezione) { this.proiezione = proiezione; }

    /** @return la lista dei biglietti. */
    public ArrayList<Biglietto> getBiglietti() { return biglietti; }
    /** @param biglietti la lista di biglietti da impostare. */
    public void setBiglietti(ArrayList<Biglietto> biglietti) { this.biglietti = biglietti; }

    /** @return il cliente che ha prenotato. */
    public Cliente getCliente() { return cliente; }
    /** @param cliente il cliente da associare. */
    public void setCliente(Cliente cliente) { this.cliente = cliente; }

    /** @return l'oggetto pagamento. */
    public Pagamento getPagamento() { return pagamento; }
    /** @param pagamento l'oggetto pagamento da associare. */
    public void setPagamento(Pagamento pagamento) { this.pagamento = pagamento; }
}