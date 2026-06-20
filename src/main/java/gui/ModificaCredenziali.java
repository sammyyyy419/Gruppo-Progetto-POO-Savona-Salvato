package gui;

import controller.Controller;
import model.Cliente;
import model.Dipendente;
import model.Utente;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class ModificaCredenziali extends JFrame {
    private JPanel mainPanel;
    private JLabel labelTitolo;
    private JTextField textNuovaEmail;
    private JTextField textNuovaPassword;
    private JButton btnSalva;
    private JButton btnAnnulla;
    private JLabel labelNuovaEmail;
    private JLabel labelNuovaPassword;

    private Controller controller;
    private Utente utenteLoggato;

    public ModificaCredenziali(Controller controller, Utente utente) {
        this.controller = controller;
        this.utenteLoggato = utente;

        setContentPane(mainPanel);
        setTitle("Modifica le tue Credenziali");
        setSize(450, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        // Precompila i campi con i dati attuali
        textNuovaEmail.setText(utente.getEmail());
        textNuovaPassword.setText(utente.getPassword());

        // LOGICA DEI RUOLI: Se è un dipendente, blocca la modifica della mail!
        if (utente instanceof Dipendente) {
            textNuovaEmail.setEnabled(false);
            textNuovaEmail.setBackground(new Color(230, 230, 230));
            textNuovaEmail.setToolTipText("L'email aziendale non può essere modificata.");
        }

        // ==============================================================
        // NUOVA LOGICA: LISTENER SUI CAMPI DI TESTO IN TEMPO REALE
        // ==============================================================
        DocumentListener controlloCampiListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { controllaCampi(); }
            @Override
            public void removeUpdate(DocumentEvent e) { controllaCampi(); }
            @Override
            public void changedUpdate(DocumentEvent e) { controllaCampi(); }
        };

        // Aggiungiamo l'ascoltatore ad entrambi i campi di testo
        textNuovaEmail.getDocument().addDocumentListener(controlloCampiListener);
        textNuovaPassword.getDocument().addDocumentListener(controlloCampiListener);

        // Eseguiamo il controllo subito all'avvio (così se i campi sono già pieni, il bottone si attiva)
        controllaCampi();

        // Azione per il bottone Annulla
        btnAnnulla.addActionListener(e -> tornaAllaDashboard());

        // Azione per il bottone Salva
        btnSalva.addActionListener(e -> salvaDati());

        setVisible(true);
    }

    // Metodo che accende o spegne il bottone Salva
    private void controllaCampi() {
        String email = textNuovaEmail.getText().trim();
        String password = textNuovaPassword.getText().trim();

        // Abilita il bottone SOLO se entrambi i campi non sono vuoti
        boolean campiValidi = !email.isEmpty() && !password.isEmpty();
        btnSalva.setEnabled(campiValidi);
    }

    // Metodo dedicato per capire a quale schermata tornare
    private void tornaAllaDashboard() {
        this.dispose(); // Chiude la finestra corrente

        if (utenteLoggato instanceof Cliente) {
            new DashboardCliente(controller, (Cliente) utenteLoggato);
        } else if (utenteLoggato instanceof Dipendente) {
            new DashboardDipendente(controller, (Dipendente) utenteLoggato);
        }
    }

    private void salvaDati() {
        String nuovaEmail = textNuovaEmail.getText().trim();
        String nuovaPassword = textNuovaPassword.getText().trim();

        if (nuovaEmail.isEmpty() || nuovaPassword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "I campi non possono essere vuoti.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (utenteLoggato instanceof Cliente) {
                // Aggiorna Cliente nel Database
                controller.modificaCredenzialiCliente((Cliente) utenteLoggato, nuovaEmail, nuovaPassword);
            } else if (utenteLoggato instanceof Dipendente) {
                // Aggiorna Dipendente in memoria
                controller.modificaPasswordDipendente((Dipendente) utenteLoggato, nuovaPassword);
            }

            JOptionPane.showMessageDialog(this, "Credenziali aggiornate con successo!\nLe modifiche sono ora attive.", "Successo", JOptionPane.INFORMATION_MESSAGE);

            // Ritorna alla schermata corretta dopo aver salvato
            tornaAllaDashboard();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore durante l'aggiornamento:\n" + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}