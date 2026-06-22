package controller;

import exception.RecensioneVuotaException;
import exception.SalaPienaException;
import model.Cliente;
import model.Dipendente;
import model.Utente;
import model.Biglietto;
import model.Posto;
import model.Proiezione;
import model.Prenotazione;
import model.Film;
import model.StatoPrenotazione;

// Import dei vecchi DAO
import dao.FilmDAO;
import dao.ClienteDAO;
import implementazionePostgresDAO.FilmImplementazionePostgresDAO;
import implementazionePostgresDAO.ClienteImplementazionePostgresDAO;
import dao.ProiezioneDAO;
import implementazionePostgresDAO.ProiezioneImplementazionePostgresDAO;

// IMPORT DEI NUOVI DAO
import dao.BigliettoDAO;
import implementazionePostgresDAO.BigliettoImplementazionePostgresDAO;
import dao.RecensioneDAO;
import implementazionePostgresDAO.RecensioneImplementazionePostgresDAO;
import dao.SegnalazioneDAO;
import implementazionePostgresDAO.SegnalazioneImplementazionePostgresDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;

public class Controller {

    private ArrayList<Cliente> listaClienti;
    private ArrayList<Dipendente> listaDipendenti;
    private ArrayList<String> listaSegnalazioni;
    private ArrayList<Biglietto> listaBiglietti;
    private ProiezioneDAO proiezioneDAO;
    private ArrayList<Film> listaFilm;

    private Cliente utenteLoggatoTemporaneo;


    private FilmDAO filmDAO;
    private ClienteDAO clienteDAO;
    private BigliettoDAO bigliettoDAO;
    private RecensioneDAO recensioneDAO;
    private SegnalazioneDAO segnalazioneDAO;

    public Controller() {
        this.listaClienti = new ArrayList<>();
        this.listaDipendenti = new ArrayList<>();
        this.listaSegnalazioni = new ArrayList<>();
        this.listaBiglietti = new ArrayList<>();

        this.filmDAO = new FilmImplementazionePostgresDAO();
        this.clienteDAO = new ClienteImplementazionePostgresDAO();
        this.proiezioneDAO = new ProiezioneImplementazionePostgresDAO();

        this.bigliettoDAO = new BigliettoImplementazionePostgresDAO();
        this.recensioneDAO = new RecensioneImplementazionePostgresDAO();
        this.segnalazioneDAO = new SegnalazioneImplementazionePostgresDAO();

        try {
            this.listaFilm = filmDAO.recuperaTuttiFilm();

            for (Film f : listaFilm) {
                ArrayList<String> recensioniSalvate = recensioneDAO.recuperaRecensioniPerFilm(f.getTitolo());
                f.setRecensioni(recensioniSalvate);
            }

            this.listaBiglietti = bigliettoDAO.recuperaTuttiBiglietti();

        } catch (Exception e) {
            System.out.println("Attenzione: Impossibile caricare dal database. " + e.getMessage());
            this.listaFilm = new ArrayList<>();
        }

        listaDipendenti.add(new Dipendente("Francesca", "Salvato", "salvatofrancesca@enterprise.com", "kekka", "manager"));
        listaDipendenti.add(new Dipendente("Salvatore", "Savona", "savonasammy@enterprise.com", "1236", "manager"));
        listaDipendenti.add(new Dipendente("Bernardo", "Breve", "brevebernardo@enterprise.com", "breve", "cassiere"));
        listaDipendenti.add(new Dipendente("Andrea", "Cali", "caliandrea@enterprise.com", "cali", "proiezionista"));
    }

    public void impostaUtenteCorrente(Cliente c) {
        this.utenteLoggatoTemporaneo = c;
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

    public ArrayList<Film> getListaFilm() { return listaFilm; }

    public boolean aggiungiRecensioneAFilm(Film film, Cliente cliente, int voto, String commento) throws RecensioneVuotaException {
        if (film == null || cliente == null) {
            throw new RecensioneVuotaException("Errore: Impossibile aggiungere la recensione.");
        }
        if (commento == null || commento.trim().isEmpty()) {
            throw new RecensioneVuotaException("Il commento non può essere vuoto.");
        }
        String inizialeCognome = (cliente.getCognome() != null && !cliente.getCognome().isEmpty())
                ? cliente.getCognome().substring(0, 1).toUpperCase() + "."
                : "";
        String autore = cliente.getNome() + " " + inizialeCognome;

        film.aggiungiFeedback(autore, voto, commento);

        try {
            recensioneDAO.inserisciRecensioneDB(film.getTitolo(), autore, voto, commento);
        } catch (Exception e) {
            System.out.println("Errore durante il salvataggio della recensione nel DB: " + e.getMessage());
        }

        return true;
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

        if (utente instanceof Cliente) {
            impostaUtenteCorrente((Cliente) utente);
        }
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

            try {
                segnalazioneDAO.inserisciSegnalazioneDB(mittente.getEmail(), messaggio);
            } catch (Exception e) {
                System.out.println("Errore durante il salvataggio della segnalazione nel DB: " + e.getMessage());
            }
        }
    }

    public void aggiungiSegnalazione(Dipendente mittente, String messaggio) { this.aggiungiSegnalazione(messaggio, mittente); }

    public ArrayList<String> getSegnalazioni() {

        try {
            return segnalazioneDAO.recuperaTutteSegnalazioni();
        } catch (Exception e) {
            System.out.println("Errore recupero segnalazioni dal DB: " + e.getMessage());
            return listaSegnalazioni;
        }
    }

    public Biglietto acquistaBiglietto(double prezzo, Posto posto, Proiezione proiezione, Prenotazione prenotazione) {
        Biglietto nuovoBiglietto = new Biglietto(prezzo, posto, proiezione, prenotazione);
        listaBiglietti.add(nuovoBiglietto);

        try {
            bigliettoDAO.inserisciBigliettoDB(nuovoBiglietto);
        } catch (Exception e) {
            System.out.println("Errore durante il salvataggio del biglietto nel DB: " + e.getMessage());
        }

        return nuovoBiglietto;
    }

    public Biglietto convalidaBigliettoPerCodice(String codiceUnivoco) throws Exception {
        if (codiceUnivoco == null || codiceUnivoco.trim().isEmpty()) {
            throw new Exception("Inserire un codice valido.");
        }
        for (Biglietto b : listaBiglietti) {
            if (b.getCodiceUnivoco() != null && b.getCodiceUnivoco().equals(codiceUnivoco)) {
                if (b.isValido()) {
                    throw new Exception("Attenzione! Questo biglietto (Codice: " + codiceUnivoco + ") risulta GIÀ CONVALIDATO.");
                }
                // PRIMA (buggato credo): b.setValido(true);

                Dipendente dipendenteGenerico = listaDipendenti.get(0);
                if (!dipendenteGenerico.validaBiglietto(b)) {
                    throw new Exception("Biglietto già convalidato.");
                }
                try {
                    bigliettoDAO.aggiornaStatoBigliettoDB(codiceUnivoco, "CONVALIDATO");
                } catch (Exception e) {
                    System.out.println("Errore durante l'aggiornamento del biglietto nel DB: " + e.getMessage());
                }

                return b;
            }
        }
        throw new Exception("Codice inesistente. Nessun biglietto valido trovato per: " + codiceUnivoco);
    }

    public void rimborsaSingoloBiglietto(Biglietto bigliettoDaRimborsare) throws Exception {
        if (bigliettoDaRimborsare == null) throw new Exception("Biglietto inesistente.");
        if (bigliettoDaRimborsare.isValido()) throw new Exception("Impossibile rimborsare un biglietto già obliterato.");

        bigliettoDAO.eliminaBigliettoDB(bigliettoDaRimborsare.getCodiceUnivoco());

        listaBiglietti.remove(bigliettoDaRimborsare);
        Prenotazione pren = bigliettoDaRimborsare.getPrenotazione();
        if (pren != null && pren.getBiglietti() != null) {
            pren.getBiglietti().remove(bigliettoDaRimborsare);
            if (pren.getBiglietti().isEmpty()) {
                pren.setStato(StatoPrenotazione.RIMBORSATO);
            }
        }
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
    public void aggiungiAlCarrello(Proiezione proiezione, int quantita, double prezzoTotale) { this.carrello.add(new ElementoCarrello(proiezione, quantita, prezzoTotale)); }
    public ArrayList<ElementoCarrello> getCarrello() { return carrello; }
    public void rimuoviDalCarrello(ElementoCarrello elemento) { if (elemento != null) this.carrello.remove(elemento); }
    public double calcolaTotaleCarrello() {
        double totale = 0;
        for (ElementoCarrello elem : carrello) totale += elem.getPrezzoTotale();
        return totale;
    }
    public void svuotaCarrello() { this.carrello.clear(); }

    public ArrayList<Biglietto> getBigliettiAcquistati() { return this.listaBiglietti; }

    private boolean isPostoOccupato(Proiezione proiezione, Posto posto) {
        for (Biglietto b : listaBiglietti) {
            if (b.getProiezione() != null &&
                    b.getProiezione().getFilm().getTitolo().equals(proiezione.getFilm().getTitolo()) &&
                    b.getProiezione().getDataOraInizio().equals(proiezione.getDataOraInizio()) &&
                    b.getPostoAssegnato() != null &&
                    b.getPostoAssegnato().getFila() == posto.getFila() &&
                    b.getPostoAssegnato().getNumeroPosto() == posto.getNumeroPosto()) {
                return true; // Se il biglietto è nella lista, il posto è occupato
            }
        }
        return false;
    }
    private ArrayList<Posto> trovaPostiVicini(Proiezione proiezione, int quantitaRichiesta) throws SalaPienaException {
        ArrayList<Posto> postiScelti = new ArrayList<>();
        char filaAttuale = 'A';
        int consecutivi = 0;

        for (Posto p : proiezione.getSala().getPosti()) {
            if (p.getFila() != filaAttuale) {
                filaAttuale = p.getFila();
                consecutivi = 0;
                postiScelti.clear();
            }

            if (!isPostoOccupato(proiezione, p)) {
                postiScelti.add(p);
                consecutivi++;
                if (consecutivi == quantitaRichiesta) return postiScelti;
            } else {
                consecutivi = 0;
                postiScelti.clear();
            }
        }

        postiScelti.clear();
        for (Posto p : proiezione.getSala().getPosti()) {
            if (!isPostoOccupato(proiezione, p)) {
                postiScelti.add(p);
                if (postiScelti.size() == quantitaRichiesta) return postiScelti;
            }
        }

        throw new SalaPienaException("SALA PIENA! Impossibile acquistare " + quantitaRichiesta +
                " biglietti. Posti rimanenti in questa sala: " + postiScelti.size());
    }


    public void modificaCredenzialiCliente(Cliente cliente, String nuovaEmail, String nuovaPassword) throws Exception {

        clienteDAO.aggiornaCredenzialiClienteDB(cliente.getEmail(), nuovaEmail, nuovaPassword);

        cliente.setEmail(nuovaEmail);
        cliente.setPassword(nuovaPassword);
    }

    public void modificaPasswordDipendente(Dipendente dipendente, String nuovaPassword) {
       dipendente.setPassword(nuovaPassword);
    }

    public void confermaAcquistoCarrello(String metodoPagamento, double percentualeSconto) throws SalaPienaException {
        double prezzoBase = 8.00;
        double prezzoSingoloScontato = prezzoBase - (prezzoBase * (percentualeSconto / 100.0));

        for (ElementoCarrello elem : carrello) {
            int quantita = elem.getQuantita();
            Proiezione proiezione = elem.getProiezione();

            if ("IMAX".equalsIgnoreCase(elem.getProiezione().getSala().getTipoSala())) {
                prezzoBase = 12.00;
            }

            ArrayList<Posto> postiAssegnati = trovaPostiVicini(proiezione, quantita);

            Prenotazione nuovaPrenotazione = new Prenotazione(LocalDateTime.now(), StatoPrenotazione.CONFERMATO, proiezione, new ArrayList<>(), utenteLoggatoTemporaneo, null);

            for (int i = 0; i < quantita; i++) {
                Posto postoEsatto = postiAssegnati.get(i);
                Biglietto b = acquistaBiglietto(prezzoSingoloScontato, postoEsatto, proiezione, nuovaPrenotazione);
                nuovaPrenotazione.getBiglietti().add(b);
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

    public ArrayList<String> ottieniRecensioniLiveDalDB(String titoloFilm) {
        try {
            return recensioneDAO.recuperaRecensioniPerFilm(titoloFilm);
        } catch (Exception e) {
            System.out.println("Errore caricamento recensioni live: " + e.getMessage());
            return new ArrayList<>();
        }
    }


    public String verificaStatoSala(String nomeSala) {
        try {
            return segnalazioneDAO.ottieniProblemaSala(nomeSala);
        } catch (Exception e) {
            return null;
        }
    }

    public void segnalaSalaGuasta(Dipendente mittente, String nomeSala, String messaggio) {
        try {
            segnalazioneDAO.inserisciSegnalazioneSalaDB(mittente.getEmail(), messaggio, nomeSala);
        } catch (Exception e) {
            System.out.println("Errore segnalazione sala: " + e.getMessage());
        }
    }

    public void riparaSala(String nomeSala) {
        try {
            segnalazioneDAO.risolviSegnalazioneSalaDB(nomeSala);
        } catch (Exception e) {
            System.out.println("Errore risoluzione sala: " + e.getMessage());
        }
    }
}