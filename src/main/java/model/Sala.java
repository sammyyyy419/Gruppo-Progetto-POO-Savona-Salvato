package model;

public class Sala {
    private String numeroSala;
    private int capienza;
    private String tipoSala;

    public Sala(String numeroSala, int capienza, String tipoSala)
    {
        this.numeroSala = numeroSala;
        this.capienza = capienza;
        this.tipoSala = tipoSala;
    }

    public String getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(String numeroSala) {
        this.numeroSala = numeroSala;
    }

    public int getCapienza() {
        return capienza;
    }

    public void setCapienza(int capienza) {
        this.capienza = capienza;
    }

    public String getTipoSala() {
        return tipoSala;
    }

    public void setTipoSala(String tipoSala) {
        this.tipoSala = tipoSala;
    }

    // Fare il metodo che controlla la capienza della sala
}
