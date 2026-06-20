package gui;

import controller.Controller;
import model.Cliente;
import model.Film;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class DashboardRecensioni extends JFrame {
    private JPanel mainPanel;
    private JPanel panelAlto;
    private JButton btnTorna;
    private JButton btnAggiungi;
    private JLabel labelTitolo;
    private JScrollPane scrollRecensioni;
    private JPanel panelListaRecensioni;

    private Controller controller;
    private Cliente clienteLoggato;
    private Film filmSelezionato;

    public DashboardRecensioni(Controller controller, Cliente cliente, Film film) {
        this.controller = controller;
        this.clienteLoggato = cliente;
        this.filmSelezionato = film;

        setContentPane(mainPanel);
        setTitle("Recensioni: " + film.getTitolo());
        setSize(600, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        labelTitolo.setText("Cosa pensano gli utenti di " + film.getTitolo());

        panelListaRecensioni.setLayout(new BoxLayout(panelListaRecensioni, BoxLayout.Y_AXIS));
        scrollRecensioni.getVerticalScrollBar().setUnitIncrement(16);

        if (clienteLoggato == null) {
            btnAggiungi.setEnabled(false);
            btnAggiungi.setToolTipText("Solo i clienti registrati possono recensire.");
        }

        btnTorna.addActionListener(e -> this.dispose());
        btnAggiungi.addActionListener(e -> apriFinestraScritturaRecensione());

        // Carica le recensioni dal Database all'apertura
        aggiornaListaRecensioni();

        setVisible(true);
    }

    private void aggiornaListaRecensioni() {
        panelListaRecensioni.removeAll();

        // RECUPERO IN TEMPO REALE DAL DATABASE!
        ArrayList<String> recensioni = controller.ottieniRecensioniLiveDalDB(filmSelezionato.getTitolo());

        if (recensioni == null || recensioni.isEmpty()) {
            JLabel lblVuoto = new JLabel("Nessuna recensione presente. Sii il primo a recensire!");
            lblVuoto.setFont(new Font("Arial", Font.ITALIC, 14));
            lblVuoto.setBorder(BorderFactory.createEmptyBorder(20, 20, 0, 0));
            panelListaRecensioni.add(lblVuoto);
        } else {
            for (String recStr : recensioni) {

                // Gestione se la stringa è salvata nel formato "autore||voto||commento"
                if (recStr.contains("||")) {
                    String[] parti = recStr.split("\\|\\|");
                    if (parti.length >= 3) {
                        panelListaRecensioni.add(creaCardRecensione(parti[0], parti[1], parti[2]));
                        panelListaRecensioni.add(Box.createVerticalStrut(10));
                    }
                }
                // Adattamento intelligente: se la stringa arriva nel vecchio formato DAO ("Mario ha votato 5/5: Bello!")
                else if (recStr.contains(" ha votato ") && recStr.contains("/5:")) {
                    try {
                        String autore = recStr.substring(0, recStr.indexOf(" ha votato "));
                        String voto = recStr.substring(recStr.indexOf("votato ") + 7, recStr.indexOf("/5:"));
                        String commento = recStr.substring(recStr.indexOf("/5: ") + 4);

                        panelListaRecensioni.add(creaCardRecensione(autore.trim(), voto.trim(), commento.trim()));
                        panelListaRecensioni.add(Box.createVerticalStrut(10));
                    } catch (Exception e) {
                        // Se fallisce l'estrazione per formati strani, lo ignora
                    }
                }
            }
        }
        panelListaRecensioni.revalidate();
        panelListaRecensioni.repaint();
    }

    private JPanel creaCardRecensione(String autore, String voto, String commento) {
        JPanel card = new JPanel(new BorderLayout(5, 5));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1, true),
                BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));
        card.setBackground(Color.WHITE);
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        headerPanel.setOpaque(false);

        JLabel lblAutore = new JLabel("👤 " + autore);
        lblAutore.setFont(new Font("Arial", Font.BOLD, 14));

        int votoInt = 5; // Default di sicurezza
        try {
            votoInt = Integer.parseInt(voto);
        } catch (NumberFormatException ignored) {}

        String stelline = "★".repeat(votoInt) + "☆".repeat(5 - votoInt);
        JLabel lblStelle = new JLabel(stelline);
        lblStelle.setForeground(new Color(241, 196, 15));
        lblStelle.setFont(new Font("Arial", Font.BOLD, 16));

        headerPanel.add(lblAutore);
        headerPanel.add(lblStelle);

        JTextArea txtTesto = new JTextArea(commento);
        txtTesto.setWrapStyleWord(true);
        txtTesto.setLineWrap(true);
        txtTesto.setEditable(false);
        txtTesto.setOpaque(false);
        txtTesto.setFont(new Font("Arial", Font.PLAIN, 13));

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(txtTesto, BorderLayout.CENTER);

        return card;
    }

    private void apriFinestraScritturaRecensione() {
        JDialog dialog = new JDialog(this, "Lascia una recensione", true);
        dialog.setSize(400, 300);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new BorderLayout(5, 5));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel panelStelle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelStelle.add(new JLabel("Il tuo voto: "));
        JComboBox<Integer> comboVoto = new JComboBox<>(new Integer[]{5, 4, 3, 2, 1});
        panelStelle.add(comboVoto);
        panelStelle.add(new JLabel(" Stelle"));
        formPanel.add(panelStelle, BorderLayout.NORTH);

        JTextArea txtNuovoCommento = new JTextArea();
        txtNuovoCommento.setLineWrap(true);
        txtNuovoCommento.setWrapStyleWord(true);
        formPanel.add(new JScrollPane(txtNuovoCommento), BorderLayout.CENTER);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel();
        JButton btnSalva = new JButton("Pubblica");
        btnSalva.setBackground(new Color(39, 174, 96));
        btnSalva.setForeground(Color.WHITE);
        JButton btnAnnulla = new JButton("Annulla");

        btnAnnulla.addActionListener(e -> dialog.dispose());

        btnSalva.addActionListener(e -> {
            int votoScelto = (Integer) comboVoto.getSelectedItem();
            String testoScritto = txtNuovoCommento.getText().trim();

            boolean successo = controller.aggiungiRecensioneAFilm(filmSelezionato, clienteLoggato, votoScelto, testoScritto);

            if (successo) {
                JOptionPane.showMessageDialog(dialog, "Recensione aggiunta con successo!");
                dialog.dispose();
                aggiornaListaRecensioni(); // Ripesca dal DB per mostrarla subito!
            } else {
                JOptionPane.showMessageDialog(dialog, "Assicurati di aver scritto un commento valido.", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        bottomPanel.add(btnSalva);
        bottomPanel.add(btnAnnulla);
        dialog.add(bottomPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}