package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Prenotazione {

    private LocalDateTime dataPrenotazione;
    private StatoPrenotazione stato;
    private Proiezione proiezione;
    private ArrayList<Biglietto> biglietti;

    private Cliente cliente;
    private Pagamento pagamento;

    public Prenotazione(LocalDateTime dataPrenotazione, StatoPrenotazione stato, Proiezione proiezione, ArrayList<Biglietto> biglietti, Cliente cliente, Pagamento pagamento) {
        this.dataPrenotazione = dataPrenotazione;
        this.stato = stato;
        this.proiezione = proiezione;
        this.biglietti = (biglietti != null) ? biglietti : new ArrayList<>();
        this.cliente = cliente;
        this.pagamento = pagamento;
    }

    public int getNumeroBiglietti() {
        return this.biglietti.size();
    }

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

    public void inserisciBiglietti(Biglietto biglietto) {
        if (biglietto != null) {
            this.biglietti.add(biglietto);
        }
    }

    public void confermaPrenotazione() {
        this.stato = StatoPrenotazione.CONFERMATO;
        if (this.proiezione != null) {
            this.proiezione.registraPrenotazione(this);
        }
    }

    public void aggiungiPosto() {
        if (this.proiezione != null) {
            double prezzo = this.proiezione.getPrezzoBase();
            Biglietto nuovoBiglietto = new Biglietto(prezzo, null, this.proiezione, this);
            this.inserisciBiglietti(nuovoBiglietto);
        }
    }

    public double calcoloPrezzoTotale() {
        double totale = 0.0;
        if (this.biglietti != null) {
            for (Biglietto b : this.biglietti) {
                totale += b.getPrezzoFinale();
            }
        }
        return totale;
    }

    public LocalDateTime getDataPrenotazione() { return dataPrenotazione; }
    public void setDataPrenotazione(LocalDateTime dataPrenotazione) { this.dataPrenotazione = dataPrenotazione; }
    public StatoPrenotazione getStato() { return stato; }
    public void setStato(StatoPrenotazione stato) { this.stato = stato; }
    public Proiezione getProiezione() { return proiezione; }
    public void setProiezione(Proiezione proiezione) { this.proiezione = proiezione; }
    public ArrayList<Biglietto> getBiglietti() { return biglietti; }
    public void setBiglietti(ArrayList<Biglietto> biglietti) { this.biglietti = biglietti; }
    public Cliente getCliente() { return cliente; }
    public void setCliente(Cliente cliente) { this.cliente = cliente; }
    public Pagamento getPagamento() { return pagamento; }
    public void setPagamento(Pagamento pagamento) { this.pagamento = pagamento; }
}