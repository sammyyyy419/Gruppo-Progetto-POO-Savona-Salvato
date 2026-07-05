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

    private String salaAssegnata;

    /**
     * Crea una nuova istanza di Film con tutti i dettagli necessari.
     *
     * @param titolo                   il titolo del film.
     * @param durata                   la durata espressa come {@link LocalTime}.
     * @param genere                   il genere cinematografico.
     * @param classificazioneEta       l'età minima consigliata.
     * @param trama                    la sinossi del film.
     * @param recensioni               una lista di feedback esistenti.
     * @param percorsoCopertina        il path al file dell'immagine di copertina.
     * @param dataInizioProgrammazione la data di inizio programmazione.
     * @param salaAssegnata            l'identificativo della sala in cui è proiettato.
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
     * Calcola e restituisce la durata del film convertita in minuti totali.
     *
     * @return la durata totale in minuti, o 0 se la durata non è definita.
     */
    public int getDurataMinuti(){
        if(this.durata == null){
            return 0;
        }
        return (this.durata.getHour() * 60) + this.durata.getMinute();
    }

    /**
     * Aggiunge un feedback al film se il voto è compreso tra 1 e 5 e il commento è valido.
     * Il formato salvato sarà: "Autore||Voto||Commento".
     *
     * @param autore   l'autore della recensione.
     * @param voto     il voto dato al film (1-5).
     * @param commento il testo del feedback.
     */
    public void aggiungiFeedback(String autore, int voto, String commento) {
        if (commento != null && voto >= 1 && voto <= 5) {
            String feedback = autore + "||" + voto + "||" + commento;
            this.recensioni.add(feedback);
        }
    }

    /**
     * Restituisce la lista completa delle recensioni ricevute.
     *
     * @return un {@link ArrayList} di stringhe contenenti i feedback.
     */
    public ArrayList<String> getRecensioniClienti() {
        return this.recensioni;
    }

    /**
     * Genera una stringa formattata con tutti i dettagli principali del film.
     *
     * @return una stringa contenente titolo, genere, durata, sala e trama.
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

    /** @return il titolo del film. */
    public String getTitolo() { return titolo; }
    /** @param titolo il nuovo titolo da assegnare. */
    public void setTitolo(String titolo) { this.titolo = titolo; }

    /** @return la durata del film. */
    public LocalTime getDurata() { return durata; }
    /** @param durata la nuova durata da assegnare. */
    public void setDurata(LocalTime durata) { this.durata = durata; }

    /** @return il genere del film. */
    public String getGenere() { return genere; }
    /** @param genere il nuovo genere da assegnare. */
    public void setGenere(String genere) { this.genere = genere; }

    /** @return la classificazione di età. */
    public String getClassificazioneEta() { return classificazioneEta; }
    /** @param classificazioneEta la nuova classificazione da assegnare. */
    public void setClassificazioneEta(String classificazioneEta) { this.classificazioneEta = classificazioneEta; }

    /** @return la trama del film. */
    public String getTrama() { return trama; }
    /** @param trama la nuova trama da assegnare. */
    public void setTrama(String trama) { this.trama = trama; }

    /** @return la lista dei feedback. */
    public ArrayList<String> getRecensioni() { return recensioni; }
    /** @param recensioni la nuova lista di feedback. */
    public void setRecensioni(ArrayList<String> recensioni) { this.recensioni = recensioni; }

    /** @return il percorso del file copertina. */
    public String getPercorsoCopertina() { return percorsoCopertina; }
    /** @param percorsoCopertina il nuovo path della copertina. */
    public void setPercorsoCopertina(String percorsoCopertina) { this.percorsoCopertina = percorsoCopertina; }

    /** @return la data di inizio programmazione. */
    public LocalDate getDataInizioProgrammazione() { return dataInizioProgrammazione; }
    /** @param dataInizioProgrammazione la nuova data di inizio programmazione. */
    public void setDataInizioProgrammazione(LocalDate dataInizioProgrammazione) { this.dataInizioProgrammazione = dataInizioProgrammazione; }

    /** @return la sala assegnata. */
    public String getSalaAssegnata() { return salaAssegnata; }
    /** @param salaAssegnata la nuova sala da assegnare. */
    public void setSalaAssegnata(String salaAssegnata) { this.salaAssegnata = salaAssegnata; }
}