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
     * Imposta il cliente specificato come utente correntemente loggato nel sistema.
     *
     * @param c il {@link Cliente} da impostare come utente corrente.
     */
    public void impostaUtenteCorrente(Cliente c) {
        this.utenteLoggatoTemporaneo = c;
    }

    /**
     * Aggiunge un nuovo film al catalogo, salvandolo nel database e nella lista locale.
     *
     * @param nuovoFilm il {@link Film} da aggiungere (ignorato se null).
     * @throws Exception in caso di errore durante il salvataggio nel database.
     */
    public void aggiungiFilm(Film nuovoFilm) throws Exception {
        if (nuovoFilm != null) {
            filmDAO.inserisciFilmDB(nuovoFilm);
            listaFilm.add(nuovoFilm);
        }
    }

    /**
     * Rimuove un film dal catalogo, eliminandolo dal database e dalla lista locale.
     *
     * @param filmDaEliminare il {@link Film} da rimuovere (ignorato se null).
     * @throws Exception in caso di errore durante l'eliminazione dal database.
     */
    public void eliminaFilm(Film filmDaEliminare) throws Exception {
        if (filmDaEliminare != null) {
            filmDAO.eliminaFilmDB(filmDaEliminare);
            listaFilm.remove(filmDaEliminare);
        }
    }

    /**
     * Aggiorna i dettagli di un film esistente, modificandoli sia nel database che nell'oggetto in memoria.
     *
     * @param filmAttuale il {@link Film} da modificare.
     * @param nTitolo il nuovo titolo del film.
     * @param nDurata la nuova durata del film.
     * @param nGenere il nuovo genere del film.
     * @param nClass la nuova classificazione per età.
     * @param nTrama la nuova trama.
     * @param nPercorso il nuovo percorso dell'immagine di copertina.
     * @param nDataInizio la nuova data di inizio programmazione.
     * @param nSala la nuova sala assegnata.
     * @throws Exception in caso di errore durante l'aggiornamento nel database.
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
     * Get lista film.
     *
     * @return L'array lista di film
     */
    public ArrayList<Film> getListaFilm() { return listaFilm; }

    /**
     * Aggiunge una recensione a un film, salvandola nella lista locale e nel database.
     *
     * @param film il {@link Film} da recensire.
     * @param cliente il {@link Cliente} autore della recensione.
     * @param voto il voto assegnato al film.
     * @param commento il testo della recensione.
     * @return true se la recensione è stata aggiunta con successo.
     * @throws RecensioneVuotaException se il film, il cliente o il commento sono nulli o vuoti.
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
     * Aggiunge un nuovo cliente al sistema, registrandolo nel database e nella lista locale.
     *
     * @param nuovoCliente il {@link Cliente} da aggiungere (ignorato se null).
     * @throws Exception in caso di errore durante il salvataggio nel database.
     */
    public void aggiungiCliente(Cliente nuovoCliente) throws Exception {
        if (nuovoCliente != null) {
            clienteDAO.inserisciClienteDB(nuovoCliente);
            listaClienti.add(nuovoCliente);
        }
    }

    /**
     * Verifica le credenziali di accesso di un utente e, se è un cliente, lo imposta come utente corrente.
     *
     * @param email l'email dell'utente da validare.
     * @param password la password dell'utente.
     * @return true se le credenziali sono corrette e il login ha successo.
     * @throws Exception se l'email non è registrata o se la password è errata.
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
     * Cerca un utente nella lista dei dipendenti in memoria o, se non trovato, nel database dei clienti.
     *
     * @param email l'email dell'utente da ricercare.
     * @return l'oggetto {@link Utente} trovato, oppure null se nessun utente corrisponde all'email.
     * @throws Exception in caso di errore durante la comunicazione con il database.
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
     * Crea una nuova segnalazione, salvandola nella lista in memoria e nel database.
     *
     * @param messaggio il testo della segnalazione.
     * @param mittente il {@link Dipendente} che invia la segnalazione.
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
     * Recupera l'elenco di tutte le segnalazioni dal database.
     *
     * @return un {@link ArrayList} contenente le segnalazioni; in caso di errore, restituisce la lista locale in memoria.
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
     * Crea e registra un nuovo biglietto nel sistema, salvandolo nel database e nella lista locale.
     *
     * @param prezzo il costo del biglietto.
     * @param posto il {@link Posto} associato al biglietto.
     * @param proiezione la {@link Proiezione} a cui il biglietto si riferisce.
     * @param prenotazione la {@link Prenotazione} di appartenenza.
     * @return il {@link Biglietto} appena creato.
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
     * Convalida un biglietto tramite il suo codice univoco, aggiornando lo stato sia in memoria che nel database.
     *
     * @param codiceUnivoco il codice identificativo del biglietto da convalidare.
     * @return il {@link Biglietto} convalidato.
     * @throws Exception se il codice è vuoto, inesistente o se il biglietto risulta già convalidato.
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
     * Esegue il rimborso di un singolo biglietto, rimuovendolo dal database e aggiornando lo stato della prenotazione associata.
     *
     * @param bigliettoDaRimborsare il {@link Biglietto} da rimborsare.
     * @throws Exception se il biglietto è nullo o se è già stato convalidato (obliterato).
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
     * Recupera l'elenco delle proiezioni pianificate per un determinato film.
     *
     * @param filmSelezionato il {@link Film} di cui si vogliono recuperare le proiezioni.
     * @return un {@link ArrayList} contenente le proiezioni associate al film; restituisce una lista vuota in caso di errore o film nullo.
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
     * Aggiunge una proiezione con la relativa quantità e il prezzo totale al carrello dell'utente.
     *
     * @param proiezione la {@link Proiezione} da aggiungere.
     * @param quantita la quantità di biglietti selezionati.
     * @param prezzoTotale il prezzo totale per la proiezione aggiunta.
     */
    public void aggiungiAlCarrello(Proiezione proiezione, int quantita, double prezzoTotale) {
        this.listaCarrello.add(new Carrello(proiezione, quantita, prezzoTotale));
    }

    /**
     * Gets carrello.
     *
     * @return l'array list carrello
     */
    public ArrayList<Carrello> getCarrello() {
        return listaCarrello;
    }

    /**
     * Rimuove un elemento specifico dal carrello dell'utente.
     *
     * @param elemento il {@link Carrello} da rimuovere (ignorato se null).
     */
    public void rimuoviDalCarrello(Carrello elemento) {
        if (elemento != null) this.listaCarrello.remove(elemento);
    }

    /**
     * Calcola il costo totale di tutti gli elementi presenti nel carrello.
     *
     * @return la somma dei prezzi totali di ogni elemento nel carrello.
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


    public ArrayList<Biglietto> getBigliettiAcquistati() { return this.listaBiglietti; }

    /**
     * Verifica se un determinato posto è già occupato per una specifica proiezione.
     *
     * @param proiezione la {@link Proiezione} da controllare.
     * @param posto il {@link Posto} da verificare.
     * @return true se il posto risulta occupato, false altrimenti.
     */
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
    /**
     * Tenta di trovare un numero specifico di posti consecutivi nella sala per una determinata proiezione.
     * Se non trova posti consecutivi, prova a trovare un numero equivalente di posti non consecutivi.
     *
     * @param proiezione la {@link Proiezione} per cui ricercare i posti.
     * @param quantitaRichiesta il numero di posti necessari.
     * @return un {@link ArrayList} di posti disponibili.
     * @throws SalaPienaException se non è possibile soddisfare la richiesta, né con posti consecutivi né con posti singoli.
     */
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
     * Aggiorna l'email e la password di un cliente, sincronizzando le modifiche sia nel database che nell'oggetto in memoria.
     *
     * @param cliente il {@link Cliente} di cui modificare le credenziali.
     * @param nuovaEmail la nuova email da impostare.
     * @param nuovaPassword la nuova password da impostare.
     * @throws Exception in caso di errore durante l'aggiornamento nel database.
     */
    public void modificaCredenzialiCliente(Cliente cliente, String nuovaEmail, String nuovaPassword) throws Exception {
        clienteDAO.aggiornaCredenzialiClienteDB(cliente.getEmail(), nuovaEmail, nuovaPassword);
        cliente.setEmail(nuovaEmail);
        cliente.setPassword(nuovaPassword);
    }
    /**
     * Aggiorna la password di un dipendente, sincronizzando la modifica nel database e nell'oggetto in memoria.
     *
     * @param dipendente il {@link Dipendente} di cui modificare la password.
     * @param nuovaPassword la nuova password da impostare.
     * @throws Exception in caso di errore durante l'aggiornamento nel database.
     */
    public void modificaPasswordDipendente(Dipendente dipendente, String nuovaPassword) throws Exception {
        dipendenteDAO.aggiornaPasswordDipendenteDB(dipendente.getEmail(), nuovaPassword);
        dipendente.setPassword(nuovaPassword);
    }

    /**
     * Finalizza l'acquisto degli elementi presenti nel carrello, calcolando i prezzi in base al tipo di sala
     * e all'eventuale sconto, assegnando i posti disponibili e registrando le prenotazioni e i biglietti.
     *
     * @param metodoPagamento il metodo di pagamento utilizzato per la transazione.
     * @param percentualeSconto la percentuale di sconto da applicare al prezzo base.
     * @throws SalaPienaException se non è possibile assegnare i posti necessari per una delle proiezioni nel carrello.
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
     * Verifica la validità di un codice sconto e restituisce la percentuale associata.
     *
     * @param codice il codice sconto fornito dall'utente.
     * @return la percentuale di sconto corrispondente (es. 10.0 per 10%), oppure 0.0 se il codice non è valido.
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
     * Recupera l'elenco delle recensioni associate a un determinato film dal database.
     *
     * @param titoloFilm il titolo del film di cui si desiderano recuperare le recensioni.
     * @return un {@link ArrayList} di stringhe contenente le recensioni; restituisce una lista vuota in caso di errore.
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
     * Recupera lo stato attuale di una sala, verificando se vi siano segnalazioni di guasti o problemi tecnici.
     *
     * @param nomeSala il nome identificativo della sala.
     * @return una stringa descrittiva del problema se presente, null altrimenti o in caso di errore.
     */
    public String verificaStatoSala(String nomeSala) {
        try {
            return segnalazioneDAO.ottieniProblemaSala(nomeSala);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Invia una segnalazione di guasto per una specifica sala, registrandola nel database.
     *
     * @param mittente il {@link Dipendente} che effettua la segnalazione.
     * @param nomeSala il nome identificativo della sala soggetta a guasto.
     * @param messaggio la descrizione dettagliata del problema riscontrato.
     */
    public void segnalaSalaGuasta(Dipendente mittente, String nomeSala, String messaggio) {
        try {
            segnalazioneDAO.inserisciSegnalazioneSalaDB(mittente.getEmail(), messaggio, nomeSala);
        } catch (Exception e) {
            System.out.println("Errore segnalazione sala: " + e.getMessage());
        }
    }

    /**
     * Segnala la risoluzione di un guasto per una specifica sala, aggiornando il suo stato nel database.
     *
     * @param nomeSala il nome identificativo della sala che è stata riparata.
     */
    public void riparaSala(String nomeSala) {
        try {
            segnalazioneDAO.risolviSegnalazioneSalaDB(nomeSala);
        } catch (Exception e) {
            System.out.println("Errore risoluzione sala: " + e.getMessage());
        }
    }
}