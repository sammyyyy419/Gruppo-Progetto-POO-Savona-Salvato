package model;

import java.time.LocalDateTime;

public class Dipendente extends Utente {

    private double stipendio;
    private String ruolo;

    public Dipendente(String nomeDipendente, String cognomeDipendente, String emailDipendente, String passwordDipendente, double stipendio, String ruolo) {
        super(nomeDipendente, cognomeDipendente, emailDipendente, passwordDipendente);
        this.stipendio = stipendio;
        this.ruolo = ruolo;
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