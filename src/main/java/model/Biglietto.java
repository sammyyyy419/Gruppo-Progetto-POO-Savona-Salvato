package model;

public class Biglietto {

    private double prezzoFinale;
    //per validare il bigletto ho aggiunto attributo e metodo che controlla che biglietto non sia stato già obliterato
    private boolean valido;


    public Biglietto (double prezzoFinale)
    {
        this.prezzoFinale = prezzoFinale;
        this.valido=false;
    }

    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public double getPrezzoFinale() {
        return prezzoFinale;
    }

    public void setPrezzoFinale(double prezzoFinale) {
        this.prezzoFinale = prezzoFinale;
    }


    // Fare i metodi: Applicare lo sconto e generare un titolo di ingresso
}
