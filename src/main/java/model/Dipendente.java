package model;

public class Dipendente extends Utente{

    private double stipendio;
    private String ruolo;

    public Dipendente (String nomeDipendente, String cognomeDipendente, String emailDipendente, String passwordDipendente,double stipendio, String ruolo)
    {
        super(nomeDipendente,cognomeDipendente,emailDipendente,passwordDipendente);
        this.stipendio = stipendio;
        this.ruolo = ruolo;
    }

    public double getStipendio() {
        return stipendio;
    }

    public void setStipendio(double stipendio) {
        this.stipendio = stipendio;
    }

    public String getRuolo() {
        return ruolo;
    }

    public void setRuolo(String ruolo) {
        this.ruolo = ruolo;
    }

    // Fare i metodi:  pianificare progrmmazione dei film, verificare validita del biglietto di ingresso
}
