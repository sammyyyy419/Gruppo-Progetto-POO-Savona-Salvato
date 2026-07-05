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
     * Instantiates a new Pagamento.
     *
     * @param importo         the importo
     * @param dataPagamento   the data pagamento
     * @param metodoPagamento the metodo pagamento
     */
    public Pagamento (double importo, LocalDateTime dataPagamento, String metodoPagamento) {
        this.importo = importo;
        this.dataPagamento = dataPagamento;
        this.metodoPagamento = metodoPagamento;
    }

    /**
     * Gets importo.
     *
     * @return the importo
     */
    public double getImporto() { return importo; }

    /**
     * Sets importo.
     *
     * @param importo the importo
     */
    public void setImporto(double importo) { this.importo = importo; }

    /**
     * Gets data pagamento.
     *
     * @return the data pagamento
     */
    public LocalDateTime getDataPagamento() { return dataPagamento; }

    /**
     * Sets data pagamento.
     *
     * @param dataPagamento the data pagamento
     */
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }

    /**
     * Gets metodo pagamento.
     *
     * @return the metodo pagamento
     */
    public String getMetodoPagamento() { return metodoPagamento; }

    /**
     * Sets metodo pagamento.
     *
     * @param metodoPagamento the metodo pagamento
     */
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }
}