package controller;

import model.Cliente;
import model.Dipendente;
import model.Utente;

import java.util.ArrayList;

public class Controller {

    private ArrayList<Cliente> listaClienti;
    private ArrayList<Dipendente> listaDipendenti;

    public Controller() {

        this.listaClienti = new ArrayList<>();
        this.listaDipendenti = new ArrayList<>();

        listaClienti.add(new Cliente("Sammy", "Cliente", "sammy@gmail.com", "password123"));
        listaDipendenti.add(new Dipendente("Francesca", "Volpe", "francesca.volpe@enterprise.com", "sammy","cassiere"));
    }


    public String verificaLogin(String emailInserita, String passwordInserita){
        if(emailInserita.endsWith("@enterprise.com")) {
            for(Dipendente d : listaDipendenti) {
                if(d.getEmail().equals(emailInserita) && d.getPassword().equals(passwordInserita)) {
                    return "DIPENDENTE";
                }
            }
        } else {
            for (Cliente c : listaClienti) {
                if(c.getEmail().equals(emailInserita) && c.getPassword().equals(passwordInserita)) {
                    return "CLIENTE";
                }
            }
        }
        return "ACCESSO FALLITO!";
    }


    public Utente recuperaUtente(String email) {
        for (Cliente c : listaClienti) {
            if (c.getEmail().equals(email)) return c;
        }
        for (Dipendente d : listaDipendenti) {
            if (d.getEmail().equals(email)) return d;
        }
        return null;
    }


}
