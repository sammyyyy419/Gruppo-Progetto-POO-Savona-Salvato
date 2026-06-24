package model;

import java.time.LocalDateTime;

public class Pagamento {

    private double importo;
    private LocalDateTime dataPagamento;
    private String metodoPagamento;

    public Pagamento (double importo, LocalDateTime dataPagamento, String metodoPagamento) {
        this.importo = importo;
        this.dataPagamento = dataPagamento;
        this.metodoPagamento = metodoPagamento;
    }

    public double getImporto() { return importo; }
    public void setImporto(double importo) { this.importo = importo; }
    public LocalDateTime getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDateTime dataPagamento) { this.dataPagamento = dataPagamento; }
    public String getMetodoPagamento() { return metodoPagamento; }
    public void setMetodoPagamento(String metodoPagamento) { this.metodoPagamento = metodoPagamento; }
}