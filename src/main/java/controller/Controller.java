package controller;

import exception.FilmNonDisponibileException;
import exception.PasswordErrataException;
import exception.UtenteNonTrovatoException;
import model.Cliente;
import model.Dipendente;
import model.Film;
import model.Utente;

import java.util.ArrayList;

public class Controller {

    private ArrayList<Cliente> listaClienti;
    private ArrayList<Dipendente> listaDipendenti;

    public Controller() {
        this.listaClienti = new ArrayList<>();
        this.listaDipendenti = new ArrayList<>();

        // Inizializzazione dati di prova
        listaClienti.add(new Cliente("Sammy", "Cliente", "sammy@gmail.com", "password123"));
        listaDipendenti.add(new Dipendente("Francesca", "Volpe", "francesca.volpe@enterprise.com", "sammy", "cassiere"));
    }

    // Metodo per il check rapido (usato in Home se necessario)
    public String verificaLogin(String emailInserita, String passwordInserita) {
        if (emailInserita.endsWith("@enterprise.com")) {
            for (Dipendente d : listaDipendenti) {
                if (d.getEmail().equals(emailInserita) && d.getPassword().equals(passwordInserita)) {
                    return "DIPENDENTE";
                }
            }
        } else {
            for (Cliente c : listaClienti) {
                if (c.getEmail().equals(emailInserita) && c.getPassword().equals(passwordInserita)) {
                    return "CLIENTE";
                }
            }
        }
        return "ACCESSO FALLITO!";
    }

    // Metodo per recuperare l'oggetto utente completo
    public Utente recuperaUtente(String email) {
        for (Cliente c : listaClienti) {
            if (c.getEmail().equals(email)) return c;
        }
        for (Dipendente d : listaDipendenti) {
            if (d.getEmail().equals(email)) return d;
        }
        return null;
    }

    // Metodo di validazione professionale con eccezioni
    public void validaLogin(String email, String password) throws UtenteNonTrovatoException, PasswordErrataException {
        Utente u = recuperaUtente(email);

        if (u == null) {
            throw new UtenteNonTrovatoException("Utente non trovato!");
        }
        if (!u.getPassword().equals(password)) {
            throw new PasswordErrataException("Password errata!");
        }
    }

    // Logica di business: Prenotazione Film
  /*  public void prenotaFilm(Film film) throws FilmNonDisponibileException {
        if (!film.isDisponibile()) {
            throw new FilmNonDisponibileException("Il film " + film.getTitolo() + " non è al momento proiettabile.");
        }
        // Qui aggiungerai la logica per collegare la prenotazione al cliente
    }
    */
}