package model;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Sala {
    private String numeroSala;
    private int capienza;
    private String tipoSala;
    private ArrayList<Proiezione> listaProiezioni=new ArrayList<>();

    public Sala(String numeroSala, int capienza, String tipoSala, ArrayList<Proiezione> listaProiezioni) {
        this.numeroSala = numeroSala;
        this.capienza = capienza;
        this.tipoSala = tipoSala;
        this.listaProiezioni = listaProiezioni;
    }

    public void aggiungiProiezioneInSala(Proiezione nuovaProiezione){
        this.listaProiezioni.add(nuovaProiezione);
    }

    public boolean isLibera(LocalDateTime inizioNuovaProiezione, LocalDateTime fineNuovaProiezione){
        for(Proiezione temp:listaProiezioni){
            if(inizioNuovaProiezione.isBefore(temp.getDataOraFine()) && fineNuovaProiezione.isAfter(temp.getDataOraInizio())){
                return false;
            }
        }
        return true;
    }

    public int getPostiOccupati(Proiezione proiezione){
        if(proiezione==null){
            return 0;
        }

        ArrayList<Prenotazione> prenotazioni=proiezione.getPrenotazioniRicevute();
        int postiOccupati=0;
        if(prenotazioni != null){
            for(Prenotazione temp:prenotazioni){
                postiOccupati+=temp.getNumeroBiglietti();
            }
        }

        return postiOccupati;
    }

    public int postiLiberiInSala(Proiezione proiezione){
        return this.capienza-this.getPostiOccupati(proiezione);
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

    public ArrayList<Proiezione> getListaProiezioni() {
        return listaProiezioni;
    }

    public void setListaProiezioni(ArrayList<Proiezione> listaProiezioni) {
        this.listaProiezioni = listaProiezioni;
    }

    // Fare il metodo che controlla la capienza della sala
}
