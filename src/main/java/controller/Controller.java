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
import dao.ProiezioneDAO;
import implementazionePostgresDAO.ProiezioneImplementazionePostgresDAO;

import java.util.ArrayList;

public class Controller {

    private ArrayList<Cliente> listaClienti;
    private ArrayList<Dipendente> listaDipendenti;
    private ArrayList<String> listaSegnalazioni;
    private ArrayList<Biglietto> listaBiglietti; // Contiene tutti i biglietti venduti!
    private ProiezioneDAO proiezioneDAO;
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
        this.proiezioneDAO = new ProiezioneImplementazionePostgresDAO();

        try {
            this.listaFilm = filmDAO.recuperaTuttiFilm();
        } catch (Exception e) {
            System.out.println("Attenzione: Impossibile caricare i film dal database. " + e.getMessage());
            this.listaFilm = new ArrayList<>();
        }

        listaDipendenti.add(new Dipendente("Francesca", "Volpe", "francesca.volpe@enterprise.com", "sammy", "cassiere"));
        listaDipendenti.add(new Dipendente("Sammy", "Savona", "savonasammy@enterprise.com", "1236", "manager"));
    }

    public void aggiungiFilm(Film nuovoFilm) throws Exception {
        if (nuovoFilm != null) {
            filmDAO.inserisciFilmDB(nuovoFilm);
            listaFilm.add(nuovoFilm);
        }
    }

    public void eliminaFilm(Film filmDaEliminare) throws Exception {
        if (filmDaEliminare != null) {
            filmDAO.eliminaFilmDB(filmDaEliminare);
            listaFilm.remove(filmDaEliminare);
        }
    }

    public void modificaFilm(Film filmAttuale, String nTitolo, java.time.LocalTime nDurata, String nGenere, String nClass, String nTrama, String nPercorso, java.time.LocalDate nDataInizio, String nSala) throws Exception {
        String vecchioTitolo = filmAttuale.getTitolo();

        Film filmAggiornato = new Film(nTitolo, nDurata, nGenere, nClass, nTrama, filmAttuale.getRecensioniClienti(), nPercorso, nDataInizio, nSala);

        filmDAO.aggiornaFilmDB(vecchioTitolo, filmAggiornato);

        filmAttuale.setTitolo(nTitolo);
        filmAttuale.setDurata(nDurata);
        filmAttuale.setGenere(nGenere);
        filmAttuale.setClassificazioneEta(nClass);
        filmAttuale.setTrama(nTrama);
        filmAttuale.setPercorsoCopertina(nPercorso);
        filmAttuale.setDataInizioProgrammazione(nDataInizio);
        filmAttuale.setSalaAssegnata(nSala);
    }

    public ArrayList<Film> getListaFilm() {
        return listaFilm;
    }

    public void aggiungiCliente(Cliente nuovoCliente) throws Exception {
        if (nuovoCliente != null) {
            clienteDAO.inserisciClienteDB(nuovoCliente);
            listaClienti.add(nuovoCliente);
        }
    }

    public boolean validaLogin(String email, String password) throws Exception {
        Utente utente = recuperaUtente(email);
        if (utente == null) throw new Exception("Utente non trovato con questa email.");
        if (!utente.getPassword().equals(password)) throw new Exception("Password errata.");
        return true;
    }

    public Utente recuperaUtente(String email) throws Exception {
        for (Dipendente d : listaDipendenti) {
            if (d.getEmail().equalsIgnoreCase(email)) return d;
        }
        Cliente clienteDalDB = clienteDAO.recuperaClienteDaDB(email);
        if (clienteDalDB != null) return clienteDalDB;
        return null;
    }

    public void aggiungiSegnalazione(String messaggio, Dipendente mittente) {
        if (mittente != null && messaggio != null) {
            String segnalazioneCompleta = mittente.getNome() + " " + mittente.getCognome() + " (" + mittente.getRuolo() + ") - " + messaggio;
            listaSegnalazioni.add(segnalazioneCompleta);
        }
    }

    public void aggiungiSegnalazione(Dipendente mittente, String messaggio) {
        this.aggiungiSegnalazione(messaggio, mittente);
    }

    public ArrayList<String> getSegnalazioni() { return listaSegnalazioni; }

    public Biglietto acquistaBiglietto(double prezzo, Posto posto, Proiezione proiezione, Prenotazione prenotazione) {
        Biglietto nuovoBiglietto = new Biglietto(prezzo, posto, proiezione, prenotazione);
        listaBiglietti.add(nuovoBiglietto);
        return nuovoBiglietto;
    }

    // NUOVO METODO: Trova il biglietto tramite il codice univoco a 8 cifre e lo convalida
    public Biglietto convalidaBigliettoPerCodice(String codiceUnivoco) throws Exception {
        if (codiceUnivoco == null || codiceUnivoco.trim().isEmpty()) {
            throw new Exception("Inserire un codice valido.");
        }

        // Cerca il biglietto in tutta la lista globale dei biglietti acquistati
        for (Biglietto b : listaBiglietti) {
            if (b.getCodiceUnivoco() != null && b.getCodiceUnivoco().equals(codiceUnivoco)) {

                // Se è già stato obliterato, blocca l'ingresso
                if (b.isValido()) {
                    throw new Exception("Attenzione! Questo biglietto (Codice: " + codiceUnivoco + ") risulta GIÀ CONVALIDATO precedentemente.");
                }

                // Convalida il biglietto
                b.setValido(true);
                return b;
            }
        }

        // Se finisce il ciclo senza trovare nulla, il codice non esiste
        throw new Exception("Codice inesistente. Nessun biglietto trovato per: " + codiceUnivoco);
    }

    public ArrayList<Proiezione> getProiezioniPerFilm(Film filmSelezionato) {
        try {
            if (filmSelezionato != null) return proiezioneDAO.recuperaProiezioniDiUnFilm(filmSelezionato);
        } catch (Exception e) {
            System.out.println("Errore nel recupero delle proiezioni: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    public static class ElementoCarrello {
        private Proiezione proiezione;
        private int quantita;
        private double prezzoTotale;
        public ElementoCarrello(Proiezione proiezione, int quantita, double prezzoTotale) {
            this.proiezione = proiezione; this.quantita = quantita; this.prezzoTotale = prezzoTotale;
        }
        public Proiezione getProiezione() { return proiezione; }
        public int getQuantita() { return quantita; }
        public double getPrezzoTotale() { return prezzoTotale; }
    }

    private ArrayList<ElementoCarrello> carrello = new ArrayList<>();
    public void aggiungiAlCarrello(Proiezione proiezione, int quantita, double prezzoTotale) {
        this.carrello.add(new ElementoCarrello(proiezione, quantita, prezzoTotale));
    }
    public ArrayList<ElementoCarrello> getCarrello() { return carrello; }
    public void rimuoviDalCarrello(ElementoCarrello elemento) { if (elemento != null) this.carrello.remove(elemento); }
    public double calcolaTotaleCarrello() {
        double totale = 0;
        for (ElementoCarrello elem : carrello) totale += elem.getPrezzoTotale();
        return totale;
    }
    public void svuotaCarrello() { this.carrello.clear(); }

    // METODO AGGIUNTO PER PERMETTERE ALLA GRAFICA DI VEDERE I BIGLIETTI ACQUISTATI
    public ArrayList<Biglietto> getBigliettiAcquistati() {
        return this.listaBiglietti;
    }

    // --- INIZIO LOGICA ASSEGNAZIONE POSTI ---

    // 1. Controllo se un posto in quella specifica ora/sala è già occupato da un biglietto venduto
    private boolean isPostoOccupato(Proiezione proiezione, Posto posto) {
        for (Biglietto b : listaBiglietti) {
            if (b.getProiezione() != null &&
                    b.getProiezione().getFilm().getTitolo().equals(proiezione.getFilm().getTitolo()) &&
                    b.getProiezione().getDataOraInizio().equals(proiezione.getDataOraInizio()) &&
                    b.getPostoAssegnato() != null &&
                    b.getPostoAssegnato().getFila() == posto.getFila() &&
                    b.getPostoAssegnato().getNumeroPosto() == posto.getNumeroPosto()) {
                return true;
            }
        }
        return false;
    }

    // 2. L'algoritmo che cerca N posti liberi vicini
    private ArrayList<Posto> trovaPostiVicini(Proiezione proiezione, int quantitaRichiesta) {
        ArrayList<Posto> postiScelti = new ArrayList<>();
        char filaAttuale = 'A';
        int consecutivi = 0;

        // TENTATIVO A: Cerchiamo posti consecutivi nella STESSA FILA
        for (Posto p : proiezione.getSala().getPosti()) {
            if (p.getFila() != filaAttuale) {
                filaAttuale = p.getFila();
                consecutivi = 0;
                postiScelti.clear();
            }

            if (!isPostoOccupato(proiezione, p)) {
                postiScelti.add(p);
                consecutivi++;
                if (consecutivi == quantitaRichiesta) {
                    return postiScelti; // Trovati! Esce subito.
                }
            } else {
                consecutivi = 0; // Trovato un ostacolo, si resetta il contatore
                postiScelti.clear();
            }
        }

        postiScelti.clear();
        for (Posto p : proiezione.getSala().getPosti()) {
            if (!isPostoOccupato(proiezione, p)) {
                postiScelti.add(p);
                if (postiScelti.size() == quantitaRichiesta) {
                    return postiScelti;
                }
            }
        }
        return postiScelti;
    }

    // 3. Modifica del checkout per includere i posti calcolati
    public void confermaAcquistoCarrello(String metodoPagamento, double percentualeSconto) {
        double prezzoBase = 8.00;
        double prezzoSingoloScontato = prezzoBase - (prezzoBase * (percentualeSconto / 100.0));

        for (ElementoCarrello elem : carrello) {
            int quantita = elem.getQuantita();
            Proiezione proiezione = elem.getProiezione();

            // Calcoliamo i posti prima di stampare i biglietti!
            ArrayList<Posto> postiAssegnati = trovaPostiVicini(proiezione, quantita);

            for (int i = 0; i < quantita; i++) {
                // Prende il posto corrispondente
                Posto postoEsatto = (i < postiAssegnati.size()) ? postiAssegnati.get(i) : null;

                // Crea fisicamente il biglietto (ora genererà anche il codice univoco automaticamente in base alle tue modifiche)
                acquistaBiglietto(prezzoSingoloScontato, postoEsatto, proiezione, null);
            }
        }
        svuotaCarrello();
    }

    public double valutaCodiceSconto(String codice) {
        if (codice == null) return 0.0;
        String cod = codice.toUpperCase().trim();
        if (cod.equals("ENTERPRISE")) return 10.0;
        if (cod.equals("SENIOR")) return 50.0;
        if (cod.equals("JUNIOR")) return 25.0;
        return 0.0;
    }
}