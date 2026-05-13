package model;

public class Posto {

    private int numeroPosto;
    private char fila;

    public Posto(int numero, char fila)
    {
        this.numeroPosto = numero;
        this.fila = fila;
    }

    public int getNumeroPosto() {
        return numeroPosto;
    }

    public void setNumeroPosto(int numeroPosto) {
        this.numeroPosto = numeroPosto;
    }

    public char getFila() {
        return fila;
    }

    public void setFila(char fila) {
        this.fila = fila;
    }
    // Fare il metodo per verificare se la poltrona è occupata
}
