package gui;

import controller.Controller;
import model.Cliente;
import model.Dipendente;
import model.Utente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;


public class DashboardModificaCredenziali extends JFrame {
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

    /**
     * Crea una nuova istanza della dashboard di modifica credenziali, inizializzando i campi
     * con i dati correnti dell'utente e impostando i listener per la validazione dinamica degli input.
     *
     * @param controller il controller di sistema per gestire la persistenza dei dati.
     * @param utente l'oggetto {@link Utente} (istanza di {@link Cliente} o {@link Dipendente}) che richiede la modifica.
     */
    public DashboardModificaCredenziali(Controller controller, Utente utente) {
        this.controller = controller;
        this.utenteLoggato = utente;

        sistemaGrafica();

        setContentPane(mainPanel);
        setTitle("Modifica le tue Credenziali");
        setSize(520, 320);
        setMinimumSize(new Dimension(460, 280));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        textNuovaEmail.setText(utente.getEmail());
        textNuovaPassword.setText(utente.getPassword());

        if (utente instanceof Dipendente) {
            textNuovaEmail.setEnabled(false);
            textNuovaEmail.setBackground(new Color(50, 58, 89));
            textNuovaEmail.setToolTipText("L'email aziendale non può essere modificata.");
        }

        DocumentListener controlloCampiListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { controllaCampi(); }
            @Override
            public void removeUpdate(DocumentEvent e) { controllaCampi(); }
            @Override
            public void changedUpdate(DocumentEvent e) { controllaCampi(); }
        };

        textNuovaEmail.getDocument().addDocumentListener(controlloCampiListener);
        textNuovaPassword.getDocument().addDocumentListener(controlloCampiListener);

        controllaCampi();

        btnAnnulla.addActionListener(e -> tornaAllaDashboard());
        btnSalva.addActionListener(e -> salvaDati());

        setVisible(true);
    }

    private void sistemaGrafica() {
        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color sfondoCard = new Color(38, 46, 78);
        Color testoChiaro = Color.WHITE;
        Color bluAcceso = new Color(54, 112, 233);
        Color grigioScuro = new Color(50, 58, 89);

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(sfondoScuro);

        JPanel pannelloCentrale = new JPanel(new GridBagLayout());
        pannelloCentrale.setBackground(sfondoPannello);
        pannelloCentrale.setBorder(new EmptyBorder(25, 30, 25, 30));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.HORIZONTAL;

        labelTitolo.setForeground(testoChiaro);
        labelTitolo.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelTitolo.setHorizontalAlignment(SwingConstants.CENTER);

        labelNuovaEmail.setForeground(testoChiaro);
        labelNuovaEmail.setFont(new Font("SansSerif", Font.PLAIN, 13));

        labelNuovaPassword.setForeground(testoChiaro);
        labelNuovaPassword.setFont(new Font("SansSerif", Font.PLAIN, 13));

        textNuovaEmail.setBackground(sfondoCard);
        textNuovaEmail.setForeground(testoChiaro);
        textNuovaEmail.setCaretColor(testoChiaro);
        textNuovaEmail.setFont(new Font("SansSerif", Font.PLAIN, 13));
        textNuovaEmail.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(grigioScuro, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        textNuovaPassword.setBackground(sfondoCard);
        textNuovaPassword.setForeground(testoChiaro);
        textNuovaPassword.setCaretColor(testoChiaro);
        textNuovaPassword.setFont(new Font("SansSerif", Font.PLAIN, 13));
        textNuovaPassword.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(grigioScuro, 1, true),
                BorderFactory.createEmptyBorder(6, 10, 6, 10)
        ));

        btnSalva.setText("Salva Modifiche");
        btnSalva.setBackground(bluAcceso);
        btnSalva.setForeground(testoChiaro);
        btnSalva.setFocusPainted(false);
        btnSalva.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalva.setPreferredSize(new Dimension(140, 36));
        btnSalva.setFont(new Font("SansSerif", Font.BOLD, 12));

        btnAnnulla.setText("Annulla");
        btnAnnulla.setBackground(grigioScuro);
        btnAnnulla.setForeground(testoChiaro);
        btnAnnulla.setFocusPainted(false);
        btnAnnulla.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAnnulla.setPreferredSize(new Dimension(100, 36));

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(0, 8, 15, 8);
        pannelloCentrale.add(labelTitolo, c);

        c.insets = new Insets(6, 8, 6, 8);
        c.gridwidth = 1;

        c.gridx = 0;
        c.gridy = 1;
        c.weightx = 0.0;
        pannelloCentrale.add(labelNuovaEmail, c);

        c.gridx = 1;
        c.weightx = 1.0;
        pannelloCentrale.add(textNuovaEmail, c);

        c.gridx = 0;
        c.gridy = 2;
        c.weightx = 0.0;
        pannelloCentrale.add(labelNuovaPassword, c);

        c.gridx = 1;
        c.weightx = 1.0;
        pannelloCentrale.add(textNuovaPassword, c);

        JPanel panelBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBottoni.setOpaque(false);
        panelBottoni.add(btnAnnulla);
        panelBottoni.add(btnSalva);

        c.gridx = 0;
        c.gridy = 3;
        c.gridwidth = 2;
        c.insets = new Insets(18, 8, 0, 8);
        pannelloCentrale.add(panelBottoni, c);

        mainPanel.add(pannelloCentrale, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void controllaCampi() {
        String email = textNuovaEmail.getText().trim();
        String password = textNuovaPassword.getText().trim();
        boolean campiValidi = !email.isEmpty() && !password.isEmpty();
        btnSalva.setEnabled(campiValidi);
    }

    private void tornaAllaDashboard() {
        this.dispose();
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
                controller.modificaCredenzialiCliente((Cliente) utenteLoggato, nuovaEmail, nuovaPassword);
            } else if (utenteLoggato instanceof Dipendente) {
                controller.modificaPasswordDipendente((Dipendente) utenteLoggato, nuovaPassword);
            }

            JOptionPane.showMessageDialog(this, "Credenziali aggiornate con successo!\nLe modifiche sono ora attive.", "Successo", JOptionPane.INFORMATION_MESSAGE);
            tornaAllaDashboard();

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore durante l'aggiornamento:\n" + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
        }
    }
}