package model;

public class Film {
    private String titolo;
    private String durata;
    private String genere;
    private String classificazioneEta;
    private String trama;

    public Film(String titolo, String durata, String genere, String classificazioneEta, String trama)
    {
        this.titolo = titolo;
        this.durata = durata;
        this.genere = genere;
        this.classificazioneEta = classificazioneEta;
        this.trama = trama;
    }

    public String getTitolo() {
        return titolo;
    }

    public void setTitolo(String titolo) {
        this.titolo = titolo;
    }

    public String getDurata() {
        return durata;
    }

    public void setDurata(String durata) {
        this.durata = durata;
    }

    public String getGenere() {
        return genere;
    }

    public void setGenere(String genere) {
        this.genere = genere;
    }

    public String getClassificazioneEta() {
        return classificazioneEta;
    }

    public void setClassificazioneEta(String classificazioneEta) {
        this.classificazioneEta = classificazioneEta;
    }

    public String getTrama() {
        return trama;
    }

    public void setTrama(String trama) {
        this.trama = trama;
    }

    // Fare i metodi: Fornire i dettagli del film e Registrare un feedback
}
