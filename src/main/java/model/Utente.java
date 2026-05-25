package model;

import java.util.ArrayList;

public class Utente {
    protected String nome;
    protected String cognome;
    protected String email;
    protected String password;
    private ArrayList<Prenotazione> prenotazioniEffettuate;

    public Utente(String nome, String cognome, String email, String password, ArrayList<Prenotazione> prenotazioniEffettuate) {
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.prenotazioniEffettuate = prenotazioniEffettuate;
    }

    public void modificaProfiloUtente(String nomeModificato, String cognomeModificato){
        this.nome=nomeModificato;
        this.cognome=cognomeModificato;
    }

    //collegato al controller per verificrae che credenziali ci siano nel database
    public boolean verificaCredenziali(String email, String password){
        return this.email.equalsIgnoreCase(email) && this.password.equals(password);
    }

    //nell'UML la freccia sulla prenotazione andava solo da cliente... vedere se nel caso spostare questo metodo nella classe cliente o rimanerlo accessibile pure a dipendente
    public void effettuaPrenotazione(Prenotazione nuovaPrenotazione) {
        if (nuovaPrenotazione != null) {
            this.prenotazioniEffettuate.add(nuovaPrenotazione);
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public ArrayList<Prenotazione> getPrenotazioniEffettuate() {
        return prenotazioniEffettuate;
    }

    public void setPrenotazioniEffettuate(ArrayList<Prenotazione> prenotazioniEffettuate) {
        this.prenotazioniEffettuate = prenotazioniEffettuate;
    }
}
