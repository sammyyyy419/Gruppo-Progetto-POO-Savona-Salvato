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

    // Fare i metodi: Produrre un documento relativo all'acquisto e verificare l'esito del pagamento
}
