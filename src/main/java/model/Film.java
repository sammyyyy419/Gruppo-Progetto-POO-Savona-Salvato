package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Film {
    private String titolo;
    private LocalTime durata;
    private String genere;
    private String classificazioneEta;
    private String trama;
    private ArrayList<String> recensioni = new ArrayList<>();
    private String percorsoCopertina;
    private LocalDate dataInizioProgrammazione;

    // NUOVO: Aggiunto l'attributo per la sala
    private String salaAssegnata;

    public Film(String titolo, LocalTime durata, String genere, String classificazioneEta, String trama, ArrayList<String> recensioni, String percorsoCopertina, LocalDate dataInizioProgrammazione, String salaAssegnata) {
        this.titolo = titolo;
        this.durata = durata;
        this.genere = genere;
        this.classificazioneEta = classificazioneEta;
        this.trama = trama;
        this.recensioni = (recensioni != null) ? recensioni : new ArrayList<>();
        this.percorsoCopertina = percorsoCopertina;
        this.dataInizioProgrammazione = (dataInizioProgrammazione != null) ? dataInizioProgrammazione : LocalDate.now();

        // NUOVO: Inizializzazione della sala
        this.salaAssegnata = salaAssegnata;
    }

    public int getDurataMinuti(){
        if(this.durata == null){
            return 0;
        }
        return (this.durata.getHour() * 60) + this.durata.getMinute();
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
                "• Classificazione: " + this.classificazioneEta + "\n" +
                "• Sala Assegnata: " + this.salaAssegnata + "\n\n" + // NUOVO: Mostriamo la sala nei dettagli
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
    public String getPercorsoCopertina() { return percorsoCopertina; }
    public void setPercorsoCopertina(String percorsoCopertina) { this.percorsoCopertina = percorsoCopertina; }
    public LocalDate getDataInizioProgrammazione() { return dataInizioProgrammazione; }
    public void setDataInizioProgrammazione(LocalDate dataInizioProgrammazione) { this.dataInizioProgrammazione = dataInizioProgrammazione; }

    // NUOVO: Getter e Setter per la sala
    public String getSalaAssegnata() { return salaAssegnata; }
    public void setSalaAssegnata(String salaAssegnata) { this.salaAssegnata = salaAssegnata; }
}