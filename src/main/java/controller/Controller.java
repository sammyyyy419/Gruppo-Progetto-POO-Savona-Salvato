package controller;

import model.Cliente;
import model.Dipendente;
import model.Utente;
import model.Biglietto;
import model.Posto;
import model.Proiezione;
import model.Prenotazione;
import model.Film;
import dao.FilmDAO;
import implementazionePostgresDAO.FilmImplementazionePostgresDAO;

import java.util.ArrayList;

public class Controller {

    private ArrayList<Cliente> listaClienti;
    private ArrayList<Dipendente> listaDipendenti;
    private ArrayList<String> listaSegnalazioni;
    private ArrayList<Biglietto> listaBiglietti;
    private ArrayList<Film> listaFilm;
    private FilmDAO filmDAO;

    public Controller() {
        this.listaClienti = new ArrayList<>();
        this.listaDipendenti = new ArrayList<>();
        this.listaSegnalazioni = new ArrayList<>();
        this.listaBiglietti = new ArrayList<>();
        this.listaFilm = new ArrayList<>();

        this.filmDAO = new FilmImplementazionePostgresDAO();

        // Inizializzazione utenti predefiniti
        listaClienti.add(new Cliente("Sammy", "Cliente", "sammy@gmail.com", "password123"));
        listaDipendenti.add(new Dipendente("Francesca", "Volpe", "francesca.volpe@enterprise.com", "sammy", "cassiere"));
    }

    public void aggiungiFilm(Film nuovoFilm) throws Exception {
        if (nuovoFilm != null) {
            listaFilm.add(nuovoFilm);
            filmDAO.inserisciFilmDB(nuovoFilm);
        }
    }

    public ArrayList<Film> getListaFilm() {
        return listaFilm;
    }

    public void aggiungiCliente(Cliente nuovoCliente) {
        if (nuovoCliente != null) {
            listaClienti.add(nuovoCliente);
        }
    }

    // Metodo per convalidare il login (Modificato senza eccezioni custom)
    public boolean validaLogin(String email, String password) throws Exception {
        Utente utente = recuperaUtente(email);

        // Se l'utente non viene trovato nella lista
        if (utente == null) {
            throw new Exception("Utente non trovato con questa email.");
        }

        // Se la password non corrisponde
        if (!utente.getPassword().equals(password)) {
            throw new Exception("Password errata.");
        }

        return true; // Login corretto
    }

    // Metodo per recuperare l'utente tramite email
    public Utente recuperaUtente(String email) {
        for (Cliente c : listaClienti) {
            if (c.getEmail().equalsIgnoreCase(email)) {
                return c;
            }
        }
        for (Dipendente d : listaDipendenti) {
            if (d.getEmail().equalsIgnoreCase(email)) {
                return d;
            }
        }
        return null;
    }

    /**
     * CORREZIONE 1: Metodo richiesto da DashboardDipendente (Messaggio, Dipendente)
     */
    public void aggiungiSegnalazione(String messaggio, Dipendente mittente) {
        if (mittente != null && messaggio != null) {
            String segnalazioneCompleta = mittente.getNome() + " " + mittente.getCognome() + " (" + mittente.getRuolo() + ") - " + messaggio;
            listaSegnalazioni.add(segnalazioneCompleta);
        }
    }

    // Sovraccarico di sicurezza nel caso l'ordine dei parametri fosse invertito altrove
    public void aggiungiSegnalazione(Dipendente mittente, String messaggio) {
        this.aggiungiSegnalazione(messaggio, mittente);
    }

    public ArrayList<String> getSegnalazioni() {
        return listaSegnalazioni;
    }

    public Biglietto acquistaBiglietto(double prezzo, Posto posto, Proiezione proiezione, Prenotazione prenotazione) {
        Biglietto nuovoBiglietto = new Biglietto(prezzo, posto, proiezione, prenotazione);
        listaBiglietti.add(nuovoBiglietto);
        return nuovoBiglietto;
    }

    public Biglietto convalidaBiglietto(String titoloFilm, char fila, int numeroPosto) {
        // Cicliamo sulla lista dei biglietti esistenti
        for (Biglietto b : listaBiglietti) {

            // CONTROLLO DI SICUREZZA: Evita che il programma vada in crash se mancano i collegamenti
            if (b == null || b.getProiezione() == null || b.getProiezione().getFilm() == null || b.getPostoAssegnato() == null) {
                continue; // Se un dato è parziale o null, salta questo biglietto e passa al successivo senza crashare
            }

            // Ora possiamo estrarre i dati in totale sicurezza
            String titoloReale = b.getProiezione().getFilm().getTitolo();
            char filaReale = b.getPostoAssegnato().getFila();
            int numeroPostoReale = b.getPostoAssegnato().getNumeroPosto();

            // Verifichiamo se i dati inseriti nella Dashboard corrispondono a questo biglietto
            if (titoloReale != null && titoloReale.equalsIgnoreCase(titoloFilm) &&
                    Character.toUpperCase(filaReale) == Character.toUpperCase(fila) &&
                    numeroPostoReale == numeroPosto) {

                // Se il biglietto è valido (cioè non è ancora stato obliterato/utilizzato)
                if (!b.isValido()) {
                    b.setValido(true); // Lo marchiamo come OBLITERATO/UTILIZZATO
                    return b; // Ritorna il biglietto convalidato alla dashboard
                }
            }
        }

        return null; // Ritorna null se non trova corrispondenze o se è già stato usato
    }
}