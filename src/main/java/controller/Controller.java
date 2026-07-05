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
import model.Carrello;

import dao.FilmDAO;
import dao.ClienteDAO;
import implementazionePostgresDAO.FilmImplementazionePostgresDAO;
import implementazionePostgresDAO.ClienteImplementazionePostgresDAO;
import dao.ProiezioneDAO;
import implementazionePostgresDAO.ProiezioneImplementazionePostgresDAO;
import dao.BigliettoDAO;
import implementazionePostgresDAO.BigliettoImplementazionePostgresDAO;
import dao.RecensioneDAO;
import implementazionePostgresDAO.RecensioneImplementazionePostgresDAO;
import dao.SegnalazioneDAO;
import implementazionePostgresDAO.SegnalazioneImplementazionePostgresDAO;
import dao.DipendenteDAO;
import implementazionePostgresDAO.DipendenteImplementazionePostgresDAO;

import java.time.LocalDateTime;
import java.util.ArrayList;

/**
 * The type Controller.
 */
public class Controller {

    private ArrayList<Cliente> listaClienti;
    private ArrayList<Dipendente> listaDipendenti;
    private ArrayList<String> listaSegnalazioni;
    private ArrayList<Biglietto> listaBiglietti;
    private ProiezioneDAO proiezioneDAO;
    private ArrayList<Film> listaFilm;

    private Cliente utenteLoggatoTemporaneo;
    private DipendenteDAO dipendenteDAO;

    private FilmDAO filmDAO;
    private ClienteDAO clienteDAO;
    private BigliettoDAO bigliettoDAO;
    private RecensioneDAO recensioneDAO;
    private SegnalazioneDAO segnalazioneDAO;

    private ArrayList<Carrello> listaCarrello = new ArrayList<>();

    /**
     * Instantiates a new Controller.
     */
    public Controller() {
        this.listaClienti = new ArrayList<>();
        this.listaDipendenti = new ArrayList<>();
        this.listaSegnalazioni = new ArrayList<>();
        this.listaBiglietti = new ArrayList<>();

        this.filmDAO = new FilmImplementazionePostgresDAO();
        this.clienteDAO = new ClienteImplementazionePostgresDAO();
        this.proiezioneDAO = new ProiezioneImplementazionePostgresDAO();
        this.dipendenteDAO = new DipendenteImplementazionePostgresDAO();
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
            this.listaDipendenti = dipendenteDAO.recuperaTuttiDipendenti();

        } catch (Exception e) {
            System.out.println("Attenzione: Impossibile caricare dal database. " + e.getMessage());
            this.listaFilm = new ArrayList<>();
        }
    }

    /**
     * Imposta utente corrente.
     *
     * @param c the c
     */
    public void impostaUtenteCorrente(Cliente c) {
        this.utenteLoggatoTemporaneo = c;
    }

    /**
     * Aggiungi film.
     *
     * @param nuovoFilm the nuovo film
     * @throws Exception the exception
     */
    public void aggiungiFilm(Film nuovoFilm) throws Exception {
        if (nuovoFilm != null) {
            filmDAO.inserisciFilmDB(nuovoFilm);
            listaFilm.add(nuovoFilm);
        }
    }

    /**
     * Elimina film.
     *
     * @param filmDaEliminare the film da eliminare
     * @throws Exception the exception
     */
    public void eliminaFilm(Film filmDaEliminare) throws Exception {
        if (filmDaEliminare != null) {
            filmDAO.eliminaFilmDB(filmDaEliminare);
            listaFilm.remove(filmDaEliminare);
        }
    }

    /**
     * Modifica film.
     *
     * @param filmAttuale the film attuale
     * @param nTitolo     the n titolo
     * @param nDurata     the n durata
     * @param nGenere     the n genere
     * @param nClass      the n class
     * @param nTrama      the n trama
     * @param nPercorso   the n percorso
     * @param nDataInizio the n data inizio
     * @param nSala       the n sala
     * @throws Exception the exception
     */
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

    /**
     * Gets lista film.
     *
     * @return the lista film
     */
    public ArrayList<Film> getListaFilm() { return listaFilm; }

    /**
     * Aggiungi recensione a film boolean.
     *
     * @param film     the film
     * @param cliente  the cliente
     * @param voto     the voto
     * @param commento the commento
     * @return the boolean
     * @throws RecensioneVuotaException the recensione vuota exception
     */
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

    /**
     * Aggiungi cliente.
     *
     * @param nuovoCliente the nuovo cliente
     * @throws Exception the exception
     */
    public void aggiungiCliente(Cliente nuovoCliente) throws Exception {
        if (nuovoCliente != null) {
            clienteDAO.inserisciClienteDB(nuovoCliente);
            listaClienti.add(nuovoCliente);
        }
    }

    /**
     * Valida login boolean.
     *
     * @param email    the email
     * @param password the password
     * @return the boolean
     * @throws Exception the exception
     */
    public boolean validaLogin(String email, String password) throws Exception {
        Utente utente = recuperaUtente(email);
        if (utente == null) throw new Exception("Utente non trovato con questa email.");
        if (!utente.getPassword().equals(password)) throw new Exception("Password errata.");

        if (utente instanceof Cliente) {
            impostaUtenteCorrente((Cliente) utente);
        }
        return true;
    }

    /**
     * Recupera utente utente.
     *
     * @param email the email
     * @return the utente
     * @throws Exception the exception
     */
    public Utente recuperaUtente(String email) throws Exception {
        for (Dipendente d : listaDipendenti) {
            if (d.getEmail().equalsIgnoreCase(email)) return d;
        }
        Cliente clienteDalDB = clienteDAO.recuperaClienteDaDB(email);
        if (clienteDalDB != null) return clienteDalDB;
        return null;
    }

    /**
     * Aggiungi segnalazione.
     *
     * @param messaggio the messaggio
     * @param mittente  the mittente
     */
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

    /**
     * Gets segnalazioni.
     *
     * @return the segnalazioni
     */
    public ArrayList<String> getSegnalazioni() {
        try {
            return segnalazioneDAO.recuperaTutteSegnalazioni();
        } catch (Exception e) {
            System.out.println("Errore recupero segnalazioni dal DB: " + e.getMessage());
            return listaSegnalazioni;
        }
    }

    /**
     * Acquista biglietto biglietto.
     *
     * @param prezzo       the prezzo
     * @param posto        the posto
     * @param proiezione   the proiezione
     * @param prenotazione the prenotazione
     * @return the biglietto
     */
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

    /**
     * Convalida biglietto per codice biglietto.
     *
     * @param codiceUnivoco the codice univoco
     * @return the biglietto
     * @throws Exception the exception
     */
    public Biglietto convalidaBigliettoPerCodice(String codiceUnivoco) throws Exception {
        if (codiceUnivoco == null || codiceUnivoco.trim().isEmpty()) {
            throw new Exception("Inserire un codice valido.");
        }
        for (Biglietto b : listaBiglietti) {
            if (b.getCodiceUnivoco() != null && b.getCodiceUnivoco().equals(codiceUnivoco)) {
                if (b.isValido()) {
                    throw new Exception("Attenzione! Questo biglietto (Codice: " + codiceUnivoco + ") risulta GIÀ CONVALIDATO.");
                }

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

    /**
     * Rimborsa singolo biglietto.
     *
     * @param bigliettoDaRimborsare the biglietto da rimborsare
     * @throws Exception the exception
     */
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

    /**
     * Gets proiezioni per film.
     *
     * @param filmSelezionato the film selezionato
     * @return the proiezioni per film
     */
    public ArrayList<Proiezione> getProiezioniPerFilm(Film filmSelezionato) {
        try {
            if (filmSelezionato != null) return proiezioneDAO.recuperaProiezioniDiUnFilm(filmSelezionato);
        } catch (Exception e) {
            System.out.println("Errore nel recupero delle proiezioni: " + e.getMessage());
        }
        return new ArrayList<>();
    }

    /**
     * Aggiungi al carrello.
     *
     * @param proiezione   the proiezione
     * @param quantita     the quantita
     * @param prezzoTotale the prezzo totale
     */
    public void aggiungiAlCarrello(Proiezione proiezione, int quantita, double prezzoTotale) {
        this.listaCarrello.add(new Carrello(proiezione, quantita, prezzoTotale));
    }

    /**
     * Gets carrello.
     *
     * @return the carrello
     */
    public ArrayList<Carrello> getCarrello() {
        return listaCarrello;
    }

    /**
     * Rimuovi dal carrello.
     *
     * @param elemento the elemento
     */
    public void rimuoviDalCarrello(Carrello elemento) {
        if (elemento != null) this.listaCarrello.remove(elemento);
    }

    /**
     * Calcola totale carrello double.
     *
     * @return the double
     */
    public double calcolaTotaleCarrello() {
        double totale = 0;
        for (Carrello elem : listaCarrello) totale += elem.getPrezzoTotale();
        return totale;
    }

    /**
     * Svuota carrello.
     */
    public void svuotaCarrello() {
        this.listaCarrello.clear();
    }

    /**
     * Gets biglietti acquistati.
     *
     * @return the biglietti acquistati
     */
    public ArrayList<Biglietto> getBigliettiAcquistati() { return this.listaBiglietti; }

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

    /**
     * Modifica credenziali cliente.
     *
     * @param cliente       the cliente
     * @param nuovaEmail    the nuova email
     * @param nuovaPassword the nuova password
     * @throws Exception the exception
     */
    public void modificaCredenzialiCliente(Cliente cliente, String nuovaEmail, String nuovaPassword) throws Exception {
        clienteDAO.aggiornaCredenzialiClienteDB(cliente.getEmail(), nuovaEmail, nuovaPassword);
        cliente.setEmail(nuovaEmail);
        cliente.setPassword(nuovaPassword);
    }

    /**
     * Modifica password dipendente.
     *
     * @param dipendente    the dipendente
     * @param nuovaPassword the nuova password
     * @throws Exception the exception
     */
    public void modificaPasswordDipendente(Dipendente dipendente, String nuovaPassword) throws Exception {
        dipendenteDAO.aggiornaPasswordDipendenteDB(dipendente.getEmail(), nuovaPassword);
        dipendente.setPassword(nuovaPassword);
    }

    /**
     * Conferma acquisto carrello.
     *
     * @param metodoPagamento   the metodo pagamento
     * @param percentualeSconto the percentuale sconto
     * @throws SalaPienaException the sala piena exception
     */
    public void confermaAcquistoCarrello(String metodoPagamento, double percentualeSconto) throws SalaPienaException {
        for (Carrello elem : listaCarrello) {
            int quantita = elem.getQuantita();
            Proiezione proiezione = elem.getProiezione();

            double prezzoBase = 8.00;
            if ("IMAX".equalsIgnoreCase(proiezione.getSala().getTipoSala())) {
                prezzoBase = 12.00;
            }

            double prezzoSingoloScontato = prezzoBase - (prezzoBase * (percentualeSconto / 100.0));

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

    /**
     * Valuta codice sconto double.
     *
     * @param codice the codice
     * @return the double
     */
    public double valutaCodiceSconto(String codice) {
        if (codice == null) return 0.0;
        String cod = codice.toUpperCase().trim();
        if (cod.equals("ENTERPRISE")) return 10.0;
        if (cod.equals("SENIOR")) return 50.0;
        if (cod.equals("JUNIOR")) return 25.0;
        return 0.0;
    }

    /**
     * Ottieni recensioni dal db array list.
     *
     * @param titoloFilm the titolo film
     * @return the array list
     */
    public ArrayList<String> ottieniRecensioniDalDB(String titoloFilm) {
        try {
            return recensioneDAO.recuperaRecensioniPerFilm(titoloFilm);
        } catch (Exception e) {
            System.out.println("Errore caricamento recensioni live: " + e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Verifica stato sala string.
     *
     * @param nomeSala the nome sala
     * @return the string
     */
    public String verificaStatoSala(String nomeSala) {
        try {
            return segnalazioneDAO.ottieniProblemaSala(nomeSala);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Segnala sala guasta.
     *
     * @param mittente  the mittente
     * @param nomeSala  the nome sala
     * @param messaggio the messaggio
     */
    public void segnalaSalaGuasta(Dipendente mittente, String nomeSala, String messaggio) {
        try {
            segnalazioneDAO.inserisciSegnalazioneSalaDB(mittente.getEmail(), messaggio, nomeSala);
        } catch (Exception e) {
            System.out.println("Errore segnalazione sala: " + e.getMessage());
        }
    }

    /**
     * Ripara sala.
     *
     * @param nomeSala the nome sala
     */
    public void riparaSala(String nomeSala) {
        try {
            segnalazioneDAO.risolviSegnalazioneSalaDB(nomeSala);
        } catch (Exception e) {
            System.out.println("Errore risoluzione sala: " + e.getMessage());
        }
    }
}