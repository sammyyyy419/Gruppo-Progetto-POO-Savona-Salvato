package model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

/**
 * The type Film.
 */
public class Film {
    private String titolo;
    private LocalTime durata;
    private String genere;
    private String classificazioneEta;
    private String trama;
    private ArrayList<String> recensioni = new ArrayList<>();
    private String percorsoCopertina;
    private LocalDate dataInizioProgrammazione;

    private String salaAssegnata;

    /**
     * Instantiates a new Film.
     *
     * @param titolo                   the titolo
     * @param durata                   the durata
     * @param genere                   the genere
     * @param classificazioneEta       the classificazione eta
     * @param trama                    the trama
     * @param recensioni               the recensioni
     * @param percorsoCopertina        the percorso copertina
     * @param dataInizioProgrammazione the data inizio programmazione
     * @param salaAssegnata            the sala assegnata
     */
    public Film(String titolo, LocalTime durata, String genere, String classificazioneEta, String trama, ArrayList<String> recensioni, String percorsoCopertina, LocalDate dataInizioProgrammazione, String salaAssegnata) {
        this.titolo = titolo;
        this.durata = durata;
        this.genere = genere;
        this.classificazioneEta = classificazioneEta;
        this.trama = trama;
        this.recensioni = (recensioni != null) ? recensioni : new ArrayList<>();
        this.percorsoCopertina = percorsoCopertina;
        this.dataInizioProgrammazione = (dataInizioProgrammazione != null) ? dataInizioProgrammazione : LocalDate.now();

        this.salaAssegnata = salaAssegnata;
    }

    /**
     * Get durata minuti int.
     *
     * @return the int
     */
    public int getDurataMinuti(){
        if(this.durata == null){
            return 0;
        }
        return (this.durata.getHour() * 60) + this.durata.getMinute();
    }

    /**
     * Aggiungi feedback.
     *
     * @param autore   the autore
     * @param voto     the voto
     * @param commento the commento
     */
    public void aggiungiFeedback(String autore, int voto, String commento) {
        if (commento != null && voto >= 1 && voto <= 5) {
            String feedback = autore + "||" + voto + "||" + commento;
            this.recensioni.add(feedback);
        }
    }

    /**
     * Gets recensioni clienti.
     *
     * @return the recensioni clienti
     */
    public ArrayList<String> getRecensioniClienti() {
        return this.recensioni;
    }

    /**
     * Gets dettagli.
     *
     * @return the dettagli
     */
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

    /**
     * Gets titolo.
     *
     * @return the titolo
     */
    public String getTitolo() { return titolo; }

    /**
     * Sets titolo.
     *
     * @param titolo the titolo
     */
    public void setTitolo(String titolo) { this.titolo = titolo; }

    /**
     * Gets durata.
     *
     * @return the durata
     */
    public LocalTime getDurata() { return durata; }

    /**
     * Sets durata.
     *
     * @param durata the durata
     */
    public void setDurata(LocalTime durata) { this.durata = durata; }

    /**
     * Gets genere.
     *
     * @return the genere
     */
    public String getGenere() { return genere; }

    /**
     * Sets genere.
     *
     * @param genere the genere
     */
    public void setGenere(String genere) { this.genere = genere; }

    /**
     * Gets classificazione eta.
     *
     * @return the classificazione eta
     */
    public String getClassificazioneEta() { return classificazioneEta; }

    /**
     * Sets classificazione eta.
     *
     * @param classificazioneEta the classificazione eta
     */
    public void setClassificazioneEta(String classificazioneEta) { this.classificazioneEta = classificazioneEta; }

    /**
     * Gets trama.
     *
     * @return the trama
     */
    public String getTrama() { return trama; }

    /**
     * Sets trama.
     *
     * @param trama the trama
     */
    public void setTrama(String trama) { this.trama = trama; }

    /**
     * Gets recensioni.
     *
     * @return the recensioni
     */
    public ArrayList<String> getRecensioni() { return recensioni; }

    /**
     * Sets recensioni.
     *
     * @param recensioni the recensioni
     */
    public void setRecensioni(ArrayList<String> recensioni) { this.recensioni = recensioni; }

    /**
     * Gets percorso copertina.
     *
     * @return the percorso copertina
     */
    public String getPercorsoCopertina() { return percorsoCopertina; }

    /**
     * Sets percorso copertina.
     *
     * @param percorsoCopertina the percorso copertina
     */
    public void setPercorsoCopertina(String percorsoCopertina) { this.percorsoCopertina = percorsoCopertina; }

    /**
     * Gets data inizio programmazione.
     *
     * @return the data inizio programmazione
     */
    public LocalDate getDataInizioProgrammazione() { return dataInizioProgrammazione; }

    /**
     * Sets data inizio programmazione.
     *
     * @param dataInizioProgrammazione the data inizio programmazione
     */
    public void setDataInizioProgrammazione(LocalDate dataInizioProgrammazione) { this.dataInizioProgrammazione = dataInizioProgrammazione; }

    /**
     * Gets sala assegnata.
     *
     * @return the sala assegnata
     */
    public String getSalaAssegnata() { return salaAssegnata; }

    /**
     * Sets sala assegnata.
     *
     * @param salaAssegnata the sala assegnata
     */
    public void setSalaAssegnata(String salaAssegnata) { this.salaAssegnata = salaAssegnata; }
}