package model;
import java.time.LocalDateTime;
public class Pagamento {

    private double importo;
    private LocalDateTime dataPagamento;
    private String metodoPagamento;
    private String statoPagamento;

    public Pagamento (double importo, LocalDateTime dataPagamento, String metodoPagamento, String statoPagamento)
    {
        this.importo = importo;
        this.dataPagamento = dataPagamento;
        this.metodoPagamento = metodoPagamento;
        this.statoPagamento = statoPagamento;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }

    public LocalDateTime getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(LocalDateTime dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public String getMetodoPagamento() {
        return metodoPagamento;
    }

    public void setMetodoPagamento(String metodoPagamento) {
        this.metodoPagamento = metodoPagamento;
    }

    public String getStatoPagamento() {
        return statoPagamento;
    }

    public void setStatoPagamento(String statoPagamento) {
        this.statoPagamento = statoPagamento;
    }

    // Fare i metodi: Produrre un documento relativo all'acquisto e verificare l'esito del pagamento
}
