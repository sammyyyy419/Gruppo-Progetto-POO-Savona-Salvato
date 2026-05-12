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

    // Fare i metodi: Fornire i dettagli del film e Registrare un feedback
}
