package model;

import java.time.LocalDateTime;

/**
 * The type Pagamento.
 */
public class Pagamento {

    private double importo;
    private LocalDateTime dataPagamento;
    private String metodoPagamento;

    /**
     * Crea una nuova istanza di Pagamento.
     *
     * @param importo         il valore monetario della transazione.
     * @param dataPagamento   la data e l'ora esatta in cui è avvenuto il pagamento.
     * @param metodoPagamento il metodo utilizzato per saldare la transazione.
     */
    public Pagamento(double importo, LocalDateTime dataPagamento, String metodoPagamento) {
        this.importo = importo;
        this.dataPagamento = dataPagamento;
        this.metodoPagamento = metodoPagamento;
    }

    /** * Restituisce l'importo del pagamento.
     * @return il valore della transazione.
     */
    public double getImporto() { return importo; }

    /** * Imposta l'importo del pagamento.
     * @param importo il valore della transazione da impostare.
     */
    public void setImporto(double importo) { this.importo = importo; }

    /** * Restituisce la data e l'ora del pagamento.
     * @return un oggetto {@link LocalDateTime} rappresentante il momento del pagamento.
     */
    public LocalDateTime getDataPagamento() { return dataPagamento; }

    /** * Imposta la data e l'ora del pagamento.
     * @param dataPagamento il {@link LocalDateTime} da assegnare.
     */
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }

    /** * Restituisce il metodo di pagamento utilizzato.
     * @return una stringa che identifica il metodo di pagamento.
     */
    public String getMetodoPagamento() { return metodoPagamento; }

    /** * Imposta il metodo di pagamento utilizzato.
     * @param metodoPagamento il metodo di pagamento da assegnare.
     */
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }
}