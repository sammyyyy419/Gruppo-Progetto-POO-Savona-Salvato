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
    private ArrayList<Biglietto> listaBiglietti;
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

    public void modificaFilm(Film filmAttuale, String nTitolo, java.time.LocalTime nDurata, String nGenere, String nClass, String nTrama, String nPercorso, java.time.LocalDate nDataInizio) throws Exception {
        String vecchioTitolo = filmAttuale.getTitolo();

        Film filmAggiornato = new Film(nTitolo, nDurata, nGenere, nClass, nTrama, filmAttuale.getRecensioniClienti(), nPercorso, nDataInizio);

        filmDAO.aggiornaFilmDB(vecchioTitolo, filmAggiornato);

        filmAttuale.setTitolo(nTitolo);
        filmAttuale.setDurata(nDurata);
        filmAttuale.setGenere(nGenere);
        filmAttuale.setClassificazioneEta(nClass);
        filmAttuale.setTrama(nTrama);
        filmAttuale.setPercorsoCopertina(nPercorso);
        filmAttuale.setDataInizioProgrammazione(nDataInizio);
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

        if (utente == null) {
            throw new Exception("Utente non trovato con questa email.");
        }

        if (!utente.getPassword().equals(password)) {
            throw new Exception("Password errata.");
        }

        return true;
    }

    public Utente recuperaUtente(String email) throws Exception {
        for (Dipendente d : listaDipendenti) {
            if (d.getEmail().equalsIgnoreCase(email)) {
                return d;
            }
        }

        Cliente clienteDalDB = clienteDAO.recuperaClienteDaDB(email);
        if (clienteDalDB != null) {
            return clienteDalDB;
        }

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

    public ArrayList<String> getSegnalazioni() {
        return listaSegnalazioni;
    }

    public Biglietto acquistaBiglietto(double prezzo, Posto posto, Proiezione proiezione, Prenotazione prenotazione) {
        Biglietto nuovoBiglietto = new Biglietto(prezzo, posto, proiezione, prenotazione);
        listaBiglietti.add(nuovoBiglietto);
        return nuovoBiglietto;
    }

    public Biglietto convalidaBiglietto(String titoloFilm, char fila, int numeroPosto) {
        for (Biglietto b : listaBiglietti) {
            if (b == null || b.getProiezione() == null || b.getProiezione().getFilm() == null || b.getPostoAssegnato() == null) {
                continue;
            }

            String titoloReale = b.getProiezione().getFilm().getTitolo();
            char filaReale = b.getPostoAssegnato().getFila();
            int numeroPostoReale = b.getPostoAssegnato().getNumeroPosto();

            if (titoloReale != null && titoloReale.equalsIgnoreCase(titoloFilm) &&
                    Character.toUpperCase(filaReale) == Character.toUpperCase(fila) &&
                    numeroPostoReale == numeroPosto) {

                if (!b.isValido()) {
                    b.setValido(true);
                    return b;
                }
            }
        }
        return null;
    }

    public ArrayList<Proiezione> getProiezioniPerFilm(Film filmSelezionato) {
        try {
            if (filmSelezionato != null) {
                return proiezioneDAO.recuperaProiezioniDiUnFilm(filmSelezionato);
            }
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
            this.proiezione = proiezione;
            this.quantita = quantita;
            this.prezzoTotale = prezzoTotale;
        }
        public Proiezione getProiezione() { return proiezione; }
        public int getQuantita() { return quantita; }
        public double getPrezzoTotale() { return prezzoTotale; }
    }

    private ArrayList<ElementoCarrello> carrello = new ArrayList<>();
    public void aggiungiAlCarrello(Proiezione proiezione, int quantita, double prezzoTotale) {
        this.carrello.add(new ElementoCarrello(proiezione, quantita, prezzoTotale));
    }

    public ArrayList<ElementoCarrello> getCarrello() {
        return carrello;
    }

    public void rimuoviDalCarrello(ElementoCarrello elemento) {
        if (elemento != null) {
            this.carrello.remove(elemento);
        }
    }

    public double calcolaTotaleCarrello() {
        double totale = 0;
        for (ElementoCarrello elem : carrello) {
            totale += elem.getPrezzoTotale();
        }
        return totale;
    }

    public void svuotaCarrello() {
        this.carrello.clear();
    }

    public void confermaAcquistoCarrello(String metodoPagamento, double percentualeSconto) {
        double prezzoBase = 8.00;
        double prezzoSingoloScontato = prezzoBase - (prezzoBase * (percentualeSconto / 100.0));

        for (ElementoCarrello elem : carrello) {
            for (int i = 0; i < elem.getQuantita(); i++) {
                acquistaBiglietto(prezzoSingoloScontato, null, elem.getProiezione(), null);
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