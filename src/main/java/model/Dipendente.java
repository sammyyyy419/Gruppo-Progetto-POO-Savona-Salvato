package model;

import java.time.LocalDateTime;

public class Dipendente extends Utente {

    private double stipendio;
    private String ruolo;

    public Dipendente(String nomeDipendente, String cognomeDipendente, String emailDipendente, String passwordDipendente, String ruolo) {
        super(nomeDipendente, cognomeDipendente, emailDipendente, passwordDipendente);
        this.ruolo = ruolo;
        switch (ruolo.toLowerCase()) {
            case "cassiere":
                this.stipendio = 1200.00;
                break;
            case "manager":
                this.stipendio = 1800.00;
                break;
            case "proiezionista":
                this.stipendio = 1400.00;
                break;
            default:
                this.stipendio = 1000.00;
                break;
        }
    }
    public void pianificareProgrammazioneDeiFilm(){}

    public boolean validaBiglietto(Biglietto biglietto, Proiezione proiezione){
        if(biglietto==null || proiezione==null){
            return false;
        }

        if(biglietto.isValido()){
            return false;
        }

        LocalDateTime orarioAttuale= LocalDateTime.now();
        LocalDateTime orarioInizioProiezione=proiezione.getDataOraInizio();
        LocalDateTime orarioIngressoConsentito=orarioInizioProiezione.minusMinutes(15);
        int durataFilm=proiezione.getFilm().getDurataMinuti();
        LocalDateTime orarioFineFilm = orarioInizioProiezione.plusMinutes(durataFilm);

        if(orarioAttuale.isAfter(orarioIngressoConsentito) && orarioAttuale.isBefore(orarioFineFilm)){
            biglietto.setValido(true);
            return true;
        }
        else{
            return false;
        }
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
}