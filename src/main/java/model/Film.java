package model;

import java.time.LocalTime;
import java.util.ArrayList;

public class Film {
    private String titolo;
    private LocalTime durata;
    private String genere;
    private String classificazioneEta;
    private String trama;
    private ArrayList<String> recensioni=new ArrayList<>();

    public Film(String titolo, LocalTime durata, String genere, String classificazioneEta, String trama, ArrayList<String> recensioni) {
        this.titolo = titolo;
        this.durata = durata;
        this.genere = genere;
        this.classificazioneEta = classificazioneEta;
        this.trama = trama;
        this.recensioni = (recensioni != null) ? recensioni : new ArrayList<>();
    }

    public int getDurataMinuti(){
        if(this.durata==null){
            return 0;
        }
        return (this.durata.getHour()*60)+this.durata.getMinute();
    }

    public void aggiungiFeedback(String commento, int voto) {
        if (commento != null && voto >= 1 && voto <= 5) {
            String feedback = "Voto: " + voto + "/5 - " + commento;
            this.recensioni.add(feedback);
        }
    }

    public ArrayList<String> getRecensioniClienti() {
        return this.recensioni;
    }

    public String getDettagli() {
        return "  " + this.titolo + "  \n" +
                "--------------------------------------------------\n" +
                "• Genere: " + this.genere + "\n" +
                "• Durata: " + this.getDurataMinuti() + " min\n" +
                "• Classificazione: " + this.classificazioneEta + "\n\n" +
                "• TRAMA:\n" + this.trama + "\n" +
                "--------------------------------------------------";
    }

    public String getTitolo() { return titolo; }
    public void setTitolo(String titolo) { this.titolo = titolo; }
    public LocalTime getDurata() { return durata; }
    public void setDurata(LocalTime durata) { this.durata = durata; }
    public String getGenere() { return genere; }
    public void setGenere(String genere) { this.genere = genere; }
    public String getClassificazioneEta() { return classificazioneEta; }
    public void setClassificazioneEta(String classificazioneEta) { this.classificazioneEta = classificazioneEta; }
    public String getTrama() { return trama; }
    public void setTrama(String trama) { this.trama = trama; }
    public ArrayList<String> getRecensioni() { return recensioni; }
    public void setRecensioni(ArrayList<String> recensioni) { this.recensioni = recensioni; }
}