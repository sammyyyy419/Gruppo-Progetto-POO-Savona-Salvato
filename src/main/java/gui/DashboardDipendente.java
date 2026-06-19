package gui;

import controller.Controller;
import model.Dipendente;
import model.Biglietto;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class DashboardDipendente extends JFrame {
    private JPanel mainPanel;
    private JLabel labelBenvenuto;
    private JButton buttonConvalidaBiglietti;
    private JButton buttonGestioneFilm;
    private JButton buttonGestioneSale;
    private JButton buttonLeggiSegnalazioni;
    private JButton buttonInviaSegnalazione;
    private JButton buttonTornaLogin; // NUOVO PULSANTE
    private JButton buttonEsci;

    private Controller controller;
    private Dipendente dipendenteLoggato;

    public DashboardDipendente(Controller controller, Dipendente dipendente) {
        this.controller = controller;
        this.dipendenteLoggato = dipendente;

        mainPanel = new JPanel();
        labelBenvenuto = new JLabel();
        buttonConvalidaBiglietti = new JButton("Convalida Biglietti");
        buttonGestioneFilm = new JButton("Gestione Film");
        buttonGestioneSale = new JButton("Gestione Sale");
        buttonLeggiSegnalazioni = new JButton("Leggi Segnalazioni");
        buttonInviaSegnalazione = new JButton("Invia Segnalazione");
        buttonTornaLogin = new JButton("Torna al Login"); // INIZIALIZZAZIONE
        buttonEsci = new JButton("Esci dal Programma"); // TESTO MODIFICATO

        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelSuperiore = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelBenvenuto.setText("Accesso: " + dipendente.getNome() + " " + dipendente.getCognome() + " | Ruolo: " + dipendente.getRuolo().toUpperCase());
        labelBenvenuto.setFont(new Font("Arial", Font.BOLD, 12));
        panelSuperiore.add(labelBenvenuto);
        mainPanel.add(panelSuperiore, BorderLayout.NORTH);

        // GRIGLIA MODIFICATA: da 3 a 4 righe per fare spazio ai 7 pulsanti
        JPanel panelBottoni = new JPanel(new GridLayout(4, 2, 10, 10));
        panelBottoni.add(buttonConvalidaBiglietti);
        panelBottoni.add(buttonInviaSegnalazione);
        panelBottoni.add(buttonLeggiSegnalazioni);
        panelBottoni.add(buttonGestioneFilm);
        panelBottoni.add(buttonGestioneSale);
        panelBottoni.add(buttonTornaLogin); // AGGIUNTO ALLA GRIGLIA
        panelBottoni.add(buttonEsci);
        mainPanel.add(panelBottoni, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setTitle("Area Dipendenti - Enterprise Cinema");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 400);
        setLocationRelativeTo(null);

        configuraPermessi();

        buttonInviaSegnalazione.addActionListener(e -> inviaSegnalazione());
        buttonLeggiSegnalazioni.addActionListener(e -> leggiSegnalazioni());
        buttonConvalidaBiglietti.addActionListener(e -> eseguiConvalidaBiglietti());

        // APRE LA SCHERMATA DI INSERIMENTO FILM
        buttonGestioneFilm.addActionListener(e -> {
            new DashboardGestioneFilm(this.controller).setVisible(true);
        });

        buttonGestioneSale.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Apertura modulo Gestione Sale...", "Gestione Sale", JOptionPane.INFORMATION_MESSAGE);
        });

        // NUOVO LISTENER: Torna alla schermata di Login
        buttonTornaLogin.addActionListener(e -> {
            this.dispose(); // Chiude questa dashboard
            new Home(this.controller).setVisible(true); // Riapre il login
        });

        // LISTENER ESISTENTE: Chiude tutto il programma
        buttonEsci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    private void configuraPermessi() {
        String ruolo = dipendenteLoggato.getRuolo().toLowerCase();
        switch (ruolo) {
            case "cassiere":
                buttonGestioneFilm.setEnabled(false);
                buttonGestioneSale.setEnabled(false);
                break;
            case "proiezionista":
                buttonConvalidaBiglietti.setEnabled(false);
                buttonGestioneSale.setEnabled(false);
                break;
            case "addetto alle pulizie":
                buttonConvalidaBiglietti.setEnabled(false);
                buttonGestioneFilm.setEnabled(false);
                buttonGestioneSale.setEnabled(false);
                break;
            case "manager":
                break;
            default:
                buttonGestioneFilm.setEnabled(false);
                buttonGestioneSale.setEnabled(false);
                break;
        }
    }

    private void eseguiConvalidaBiglietti() {
        String titolo = JOptionPane.showInputDialog(this, "Inserisci il titolo del film:", "Convalida - Titolo Film", JOptionPane.PLAIN_MESSAGE);
        if (titolo == null || titolo.trim().isEmpty()) return;

        String filaStr = JOptionPane.showInputDialog(this, "Inserisci la lettera della fila:", "Convalida - Fila", JOptionPane.PLAIN_MESSAGE);
        if (filaStr == null || filaStr.trim().isEmpty()) return;
        char fila = filaStr.trim().charAt(0);

        String numStr = JOptionPane.showInputDialog(this, "Inserisci il numero del posto:", "Convalida - Numero Posto", JOptionPane.PLAIN_MESSAGE);
        if (numStr == null || numStr.trim().isEmpty()) return;

        try {
            int numeroPosto = Integer.parseInt(numStr.trim());
            Biglietto b = controller.convalidaBiglietto(titolo.trim(), fila, numeroPosto);
            if (b != null) {
                JOptionPane.showMessageDialog(this, "Biglietto convalidato con successo!\n\n" + b.generaTitoloIngresso(), "Convalida Riuscita", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Nessun biglietto corrispondente trovato oppure il biglietto è già stato OBLITERATO.", "Errore Convalida", JOptionPane.ERROR_MESSAGE);
            }
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Il numero del posto deve essere un valore numerico intero.", "Errore Input", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void inviaSegnalazione() {
        String messaggio = JOptionPane.showInputDialog(this, "Inserisci il testo della segnalazione da inviare:", "Invia Segnalazione", JOptionPane.PLAIN_MESSAGE);
        if (messaggio == null || messaggio.trim().isEmpty()) return;
        controller.aggiungiSegnalazione(dipendenteLoggato, messaggio.trim());
        JOptionPane.showMessageDialog(this, "Segnalazione registrata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void leggiSegnalazioni() {
        ArrayList<String> segnalazioni = controller.getSegnalazioni();
        if (segnalazioni == null || segnalazioni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non sono presenti segnalazioni nel sistema.", "Segnalazioni", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : segnalazioni) {
            sb.append(s).append("\n--------------------------------------------------\n");
        }
        JTextArea textArea = new JTextArea(15, 45);
        textArea.setText(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Registro Segnalazioni", JOptionPane.PLAIN_MESSAGE);
    }
}