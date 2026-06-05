package gui;

import controller.Controller;
import model.Dipendente;

import javax.swing.*;
import java.util.ArrayList;

public class DashboardDipendente extends JFrame {
    private JPanel mainPanel;
    private JLabel labelBenvenuto;
    private JButton buttonVendiBiglietti;
    private JButton buttonGestioneFilm;
    private JButton buttonGestioneSale;
    private JButton buttonLeggiSegnalazioni;
    private JButton buttonInviaSegnalazione;
    private JButton buttonEsci;

    private Controller controller;
    private Dipendente dipendenteLoggato;

    public DashboardDipendente(Controller controller, Dipendente dipendente) {
        this.controller = controller;
        this.dipendenteLoggato = dipendente;

        setContentPane(mainPanel);
        setTitle("Area Dipendenti - Enterprise Cinema");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(500, 400);
        setLocationRelativeTo(null);

        labelBenvenuto.setText("Accesso: " + dipendente.getNome() + " " + dipendente.getCognome() + " | Ruolo: " + dipendente.getRuolo().toUpperCase());

        configuraPermessi();

        buttonInviaSegnalazione.addActionListener(e -> inviaSegnalazione());
        buttonLeggiSegnalazioni.addActionListener(e -> leggiSegnalazioni());
        buttonEsci.addActionListener(e -> eseguiLogout());

        buttonVendiBiglietti.addActionListener(e -> JOptionPane.showMessageDialog(this, "Apertura cassa in corso..."));
        buttonGestioneFilm.addActionListener(e -> JOptionPane.showMessageDialog(this, "Apertura gestione film in corso..."));
        buttonGestioneSale.addActionListener(e -> JOptionPane.showMessageDialog(this, "Apertura gestione sale in corso..."));

        setVisible(true);
    }

    private void configuraPermessi() {
        buttonVendiBiglietti.setVisible(false);
        buttonGestioneFilm.setVisible(false);
        buttonGestioneSale.setVisible(false);
        buttonLeggiSegnalazioni.setVisible(false);

        String ruolo = dipendenteLoggato.getRuolo().toLowerCase();

        if (ruolo.equals("cassiere")) {
            buttonVendiBiglietti.setVisible(true);
        } else if (ruolo.equals("proiezionista")) {
            buttonGestioneFilm.setVisible(true);
        } else if (ruolo.equals("pulizie")) {
            buttonGestioneSale.setVisible(true);
        } else if (ruolo.equals("manager")) {
            buttonLeggiSegnalazioni.setVisible(true);
            buttonVendiBiglietti.setVisible(true);
            buttonGestioneFilm.setVisible(true);
            buttonGestioneSale.setVisible(true);
        }
    }

    private void inviaSegnalazione() {
        String messaggio = JOptionPane.showInputDialog(this, "Scrivi la tua segnalazione:", "Nuova Segnalazione", JOptionPane.PLAIN_MESSAGE);

        if (messaggio != null && !messaggio.trim().isEmpty()) {
            controller.aggiungiSegnalazione(messaggio, dipendenteLoggato);
            JOptionPane.showMessageDialog(this, "Segnalazione inviata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void leggiSegnalazioni() {
        ArrayList<String> segnalazioni = controller.getSegnalazioni();

        if (segnalazioni == null || segnalazioni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non ci sono segnalazioni al momento.", "Segnalazioni", JOptionPane.INFORMATION_MESSAGE);
        } else {
            StringBuilder testo = new StringBuilder();
            for (String s : segnalazioni) {
                testo.append(s).append("\n\n");
            }
            JOptionPane.showMessageDialog(this, testo.toString(), "Elenco Segnalazioni", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void eseguiLogout() {
        new Home(controller);
        this.dispose();
    }
}