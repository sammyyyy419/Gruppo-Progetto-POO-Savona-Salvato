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
    private JButton buttonEsci;

    private Controller controller;
    private Dipendente dipendenteLoggato;

    public DashboardDipendente(Controller controller, Dipendente dipendente) {
        this.controller = controller;
        this.dipendenteLoggato = dipendente;

        // Inizializzazione esplicita dei componenti per garantire il funzionamento senza file .form esterni
        mainPanel = new JPanel();
        labelBenvenuto = new JLabel();
        buttonConvalidaBiglietti = new JButton("Convalida Biglietti");
        buttonGestioneFilm = new JButton("Gestione Film");
        buttonGestioneSale = new JButton("Gestione Sale");
        buttonLeggiSegnalazioni = new JButton("Leggi Segnalazioni");
        buttonInviaSegnalazione = new JButton("Invia Segnalazione");
        buttonEsci = new JButton("Esci");

        // Configurazione del Layout della Finestra
        mainPanel.setLayout(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        // Pannello Superiore (Benvenuto)
        JPanel panelSuperiore = new JPanel(new FlowLayout(FlowLayout.LEFT));
        labelBenvenuto.setText("Accesso: " + dipendente.getNome() + " " + dipendente.getCognome() + " | Ruolo: " + dipendente.getRuolo().toUpperCase());
        labelBenvenuto.setFont(new Font("Arial", Font.BOLD, 12));
        panelSuperiore.add(labelBenvenuto);
        mainPanel.add(panelSuperiore, BorderLayout.NORTH);

        // Pannello Centrale (Bottoni delle Funzionalità)
        JPanel panelBottoni = new JPanel(new GridLayout(3, 2, 10, 10));
        panelBottoni.add(buttonConvalidaBiglietti);
        panelBottoni.add(buttonInviaSegnalazione);
        panelBottoni.add(buttonLeggiSegnalazioni);
        panelBottoni.add(buttonGestioneFilm);
        panelBottoni.add(buttonGestioneSale);
        panelBottoni.add(buttonEsci);
        mainPanel.add(panelBottoni, BorderLayout.CENTER);

        setContentPane(mainPanel);
        setTitle("Area Dipendenti - Enterprise Cinema");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(550, 400);
        setLocationRelativeTo(null);

        // Configurazione dei permessi in base al ruolo operativo
        configuraPermessi();

        // Associazione dei Listener ai Bottoni
        buttonInviaSegnalazione.addActionListener(e -> inviaSegnalazione());
        buttonLeggiSegnalazioni.addActionListener(e -> leggiSegnalazioni());
        buttonConvalidaBiglietti.addActionListener(e -> eseguiConvalidaBiglietti());

        buttonGestioneFilm.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Apertura modulo Gestione Film...", "Gestione Film", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonGestioneSale.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Apertura modulo Gestione Sale...", "Gestione Sale", JOptionPane.INFORMATION_MESSAGE);
        });

        buttonEsci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
    }

    /**
     * Configura la visibilità e l'abilitazione dei bottoni in base al ruolo del dipendente loggato.
     */
    private void configuraPermessi() {
        String ruolo = dipendenteLoggato.getRuolo().toLowerCase();

        switch (ruolo) {
            case "cassiere":
                // Il cassiere convalida i biglietti e manda/legge segnalazioni, ma non gestisce film e sale
                buttonGestioneFilm.setEnabled(false);
                buttonGestioneSale.setEnabled(false);
                break;

            case "proiezionista":
                // Il proiezionista non si occupa della convalida né delle sale, ma potrebbe gestire i film
                buttonConvalidaBiglietti.setEnabled(false);
                buttonGestioneSale.setEnabled(false);
                break;

            case "addetto alle pulizie":
                // L'addetto alle pulizie usa solo le segnalazioni ed esce
                buttonConvalidaBiglietti.setEnabled(false);
                buttonGestioneFilm.setEnabled(false);
                buttonGestioneSale.setEnabled(false);
                break;

            case "manager":
                // Il manager ha accesso completo a tutte le funzionalità della dashboard
                break;

            default:
                // Comportamento restrittivo di default per ruoli non riconosciuti
                buttonGestioneFilm.setEnabled(false);
                buttonGestioneSale.setEnabled(false);
                break;
        }
    }

    /**
     * Richiede i dati necessari tramite input dialog e convalida il biglietto nel controller.
     */
    private void eseguiConvalidaBiglietti() {
        String titolo = JOptionPane.showInputDialog(this, "Inserisci il titolo del film:", "Convalida - Titolo Film", JOptionPane.PLAIN_MESSAGE);
        if (titolo == null || titolo.trim().isEmpty()) return;

        String filaStr = JOptionPane.showInputDialog(this, "Inserisci la lettera della fila:", "Convalida - Fila", JOptionPane.PLAIN_MESSAGE);
        if (filaStr == null || filaStr.trim().isEmpty()) return;

        // Estraiamo il primo carattere inserito per passarlo correttamente come char
        char fila = filaStr.trim().charAt(0);

        String numStr = JOptionPane.showInputDialog(this, "Inserisci il numero del posto:", "Convalida - Numero Posto", JOptionPane.PLAIN_MESSAGE);
        if (numStr == null || numStr.trim().isEmpty()) return;

        try {
            int numeroPosto = Integer.parseInt(numStr.trim());

            // Invia le credenziali e il char fila al metodo di convalida del controller
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

    /**
     * Permette al dipendente di digitare una segnalazione e la registra nel sistema.
     */
    private void inviaSegnalazione() {
        String messaggio = JOptionPane.showInputDialog(this, "Inserisci il testo della segnalazione da inviare:", "Invia Segnalazione", JOptionPane.PLAIN_MESSAGE);
        if (messaggio == null || messaggio.trim().isEmpty()) return;

        // Richiama il metodo del controller per tracciare la segnalazione inviata da questo dipendente
        controller.aggiungiSegnalazione(dipendenteLoggato, messaggio.trim());
        JOptionPane.showMessageDialog(this, "Segnalazione registrata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Mostra l'elenco completo di tutte le segnalazioni inserite nel sistema all'interno di una finestra di testo scorrevole.
     */
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