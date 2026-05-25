package model;

import java.time.LocalDateTime;

public class Dipendente extends Utente{

    private double stipendio;
    private String ruolo;

    public Dipendente (String nomeDipendente, String cognomeDipendente, String emailDipendente, String passwordDipendente,double stipendio, String ruolo)
    {
        super(nomeDipendente,cognomeDipendente,emailDipendente,passwordDipendente);
        this.stipendio = stipendio;
        this.ruolo = ruolo;
    }

    public boolean validaBiglietto(Biglietto biglietto, Proiezione proiezione){
        if(biglietto==null || proiezione==null){
            return false;
        }

        //se biglietto è valido(true) allora è già stato obliterato quindi restituisce false
        if(biglietto.isValido()){
            return false;
        }

        //ho fatto in modo che le persone possono iniziare a entrare in sala 15 min prima della proiezione
        //non possono entrare se proiezione è terminata

        LocalDateTime orarioAttuale= LocalDateTime.now();
        LocalDateTime orarioInizioProiezione=proiezione.getDataOraInizio();
        LocalDateTime orarioIngressoConsentito=orarioInizioProiezione.minusMinutes(15);
        int durataFilm=proiezione.getFilm().getDurataMinuti();
        LocalDateTime orarioFineFilm = orarioInizioProiezione.plusMinutes(durataFilm);

        if(orarioAttuale.isAfter(orarioIngressoConsentito) && orarioAttuale.isBefore(orarioFineFilm)){
           //valido i biglietti in modo che non possano venire riutilizzati
            biglietto.setValido(true);
            return true;
        }
        else{
            return false;
        }


    }

    // questo forse va nel controller essendo che deve avere accesso ai film
    public boolean aggiungiNuovaProiezione(Film film, Sala sala, LocalDateTime dataOraInizio, double prezzoBase){
        if (film == null || sala == null || dataOraInizio == null) {
            return false;
        }

        int durataMinuti = film.getDurataMinuti();
        LocalDateTime dataOraFine = dataOraInizio.plusMinutes(durataMinuti);

         if (sala.isLibera(dataOraInizio, dataOraFine)) {
             Proiezione nuovaProiezione = new Proiezione(dataOraInizio, dataOraFine, prezzoBase, film);
             sala.aggiungiProiezioneInSala(nuovaProiezione);
             return true;
        } else {
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
