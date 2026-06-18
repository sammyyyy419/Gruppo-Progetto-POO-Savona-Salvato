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
import dao.ClienteDAO;
import implementazionePostgresDAO.FilmImplementazionePostgresDAO;
import implementazionePostgresDAO.ClienteImplementazionePostgresDAO;

import java.util.ArrayList;

public class Controller {

    private ArrayList<Cliente> listaClienti;
    private ArrayList<Dipendente> listaDipendenti;
    private ArrayList<String> listaSegnalazioni;
    private ArrayList<Biglietto> listaBiglietti;
    private ArrayList<Film> listaFilm;

    private FilmDAO filmDAO;
    private ClienteDAO clienteDAO;

    public Controller() {
        this.listaClienti = new ArrayList<>();
        this.listaDipendenti = new ArrayList<>();
        this.listaSegnalazioni = new ArrayList<>();
        this.listaBiglietti = new ArrayList<>();

        this.filmDAO = new FilmImplementazionePostgresDAO();
        this.clienteDAO = new ClienteImplementazionePostgresDAO();

        // ------------------------------------------------------------------
        // NOVITÀ: All'avvio, il Controller carica tutti i film da pgAdmin!
        // ------------------------------------------------------------------
        try {
            this.listaFilm = filmDAO.recuperaTuttiFilm();
        } catch (Exception e) {
            System.out.println("Attenzione: Impossibile caricare i film dal database. " + e.getMessage());
            this.listaFilm = new ArrayList<>(); // Se c'è un errore, crea almeno la lista vuota
        }

        // Inizializzazione dipendenti predefiniti (I dipendenti al momento restano in RAM)
        listaDipendenti.add(new Dipendente("Francesca", "Volpe", "francesca.volpe@enterprise.com", "sammy", "cassiere"));

        // NUOVO DIPENDENTE: Sammy Savona (Manager) con accesso a tutti i pulsanti
        listaDipendenti.add(new Dipendente("Sammy", "Savona", "savonasammy@enterprise.com", "1236", "manager"));
    }

    public void aggiungiFilm(Film nuovoFilm) throws Exception {
        if (nuovoFilm != null) {
            filmDAO.inserisciFilmDB(nuovoFilm); // PRIMA Salva su database
            listaFilm.add(nuovoFilm);           // POI Aggiorna la memoria RAM
        }
    }

    public ArrayList<Film> getListaFilm() {
        return listaFilm;
    }

    // ORA SALVA NEL DATABASE!
    public void aggiungiCliente(Cliente nuovoCliente) throws Exception {
        if (nuovoCliente != null) {
            clienteDAO.inserisciClienteDB(nuovoCliente); // Salva su pgAdmin
            listaClienti.add(nuovoCliente); // Aggiorna anche la RAM
        }
    }

    // ORA CONTROLLA IL DATABASE!
    public boolean validaLogin(String email, String password) throws Exception {
        Utente utente = recuperaUtente(email);

        // Se l'utente non viene trovato nel DB né tra i dipendenti
        if (utente == null) {
            throw new Exception("Utente non trovato con questa email.");
        }

        // Se la password non corrisponde
        if (!utente.getPassword().equals(password)) {
            throw new Exception("Password errata.");
        }

        return true; // Login corretto
    }

    // ORA ESTRAE DAL DATABASE!
    public Utente recuperaUtente(String email) throws Exception {
        // 1. Prima cerca tra i dipendenti
        for (Dipendente d : listaDipendenti) {
            if (d.getEmail().equalsIgnoreCase(email)) {
                return d;
            }
        }

        // 2. Se non è un dipendente, cerca nel database clienti
        Cliente clienteDalDB = clienteDAO.recuperaClienteDaDB(email);
        if (clienteDalDB != null) {
            return clienteDalDB;
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