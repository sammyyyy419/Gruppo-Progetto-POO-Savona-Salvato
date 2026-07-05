package gui;

import controller.Controller;
import model.Cliente;
import model.Film;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

/**
 * The type Dashboard recensioni.
 */
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

    /**
     * Instantiates a new Dashboard recensioni.
     *
     * @param controller the controller
     * @param cliente    the cliente
     * @param film       the film
     */
    public DashboardRecensioni(Controller controller, Cliente cliente, Film film) {
        this.controller = controller;
        this.clienteLoggato = cliente;
        this.filmSelezionato = film;

        sistemaGrafica();

        setContentPane(mainPanel);
        setTitle("Recensioni: " + film.getTitolo());
        setSize(720, 540);
        setMinimumSize(new Dimension(660, 480));
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

        aggiornaListaRecensioni();

        setVisible(true);
    }

    private void sistemaGrafica() {
        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color testoChiaro = Color.WHITE;
        Color bluAcceso = new Color(54, 112, 233);
        Color grigioScuro = new Color(50, 58, 89);

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(sfondoScuro);

        panelAlto.removeAll();
        panelAlto.setLayout(new BorderLayout(15, 0));
        panelAlto.setBackground(sfondoScuro);
        panelAlto.setBorder(new EmptyBorder(15, 20, 15, 20));

        labelTitolo.setForeground(testoChiaro);
        labelTitolo.setFont(new Font("SansSerif", Font.BOLD, 16));
        panelAlto.add(labelTitolo, BorderLayout.WEST);

        JPanel panelBottoniTop = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        panelBottoniTop.setOpaque(false);

        btnAggiungi.setText("Scrivi Recensione");
        btnAggiungi.setBackground(bluAcceso);
        btnAggiungi.setForeground(testoChiaro);
        btnAggiungi.setFocusPainted(false);
        btnAggiungi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAggiungi.setPreferredSize(new Dimension(150, 32));
        btnAggiungi.setFont(new Font("SansSerif", Font.BOLD, 12));

        btnTorna.setText("Indietro");
        btnTorna.setBackground(grigioScuro);
        btnTorna.setForeground(testoChiaro);
        btnTorna.setFocusPainted(false);
        btnTorna.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTorna.setPreferredSize(new Dimension(100, 32));

        panelBottoniTop.add(btnAggiungi);
        panelBottoniTop.add(btnTorna);
        panelAlto.add(panelBottoniTop, BorderLayout.EAST);

        scrollRecensioni.setBorder(BorderFactory.createEmptyBorder());
        scrollRecensioni.setBackground(sfondoPannello);
        scrollRecensioni.getViewport().setBackground(sfondoPannello);

        panelListaRecensioni.setBackground(sfondoPannello);
        panelListaRecensioni.setBorder(new EmptyBorder(15, 20, 15, 20));

        mainPanel.add(panelAlto, BorderLayout.NORTH);
        mainPanel.add(scrollRecensioni, BorderLayout.CENTER);
    }

    private void aggiornaListaRecensioni() {
        panelListaRecensioni.removeAll();

        ArrayList<String> recensioni = controller.ottieniRecensioniDalDB(filmSelezionato.getTitolo());

        if (recensioni == null || recensioni.isEmpty()) {
            JLabel lblVuoto = new JLabel("Nessuna recensione presente. Sii il primo a recensire!");
            lblVuoto.setForeground(new Color(140, 150, 180));
            lblVuoto.setFont(new Font("SansSerif", Font.ITALIC, 14));
            lblVuoto.setBorder(BorderFactory.createEmptyBorder(20, 0, 0, 0));
            panelListaRecensioni.add(lblVuoto);
        } else {
            for (String recStr : recensioni) {
                if (recStr.contains("||")) {
                    String[] parti = recStr.split("\\|\\|");
                    if (parti.length >= 3) {
                        panelListaRecensioni.add(creaCardRecensione(parti[0], parti[1], parti[2]));
                        panelListaRecensioni.add(Box.createVerticalStrut(12));
                    }
                } else if (recStr.contains(" ha votato ") && recStr.contains("/5:")) {
                    try {
                        String autore = recStr.substring(0, recStr.indexOf(" ha votato "));
                        String voto = recStr.substring(recStr.indexOf("votato ") + 7, recStr.indexOf("/5:"));
                        String commento = recStr.substring(recStr.indexOf("/5: ") + 4);

                        panelListaRecensioni.add(creaCardRecensione(autore.trim(), voto.trim(), commento.trim()));
                        panelListaRecensioni.add(Box.createVerticalStrut(12));
                    } catch (Exception e) {
                    }
                }
            }
        }
        panelListaRecensioni.revalidate();
        panelListaRecensioni.repaint();
    }

    private JPanel creaCardRecensione(String autore, String voto, String commento) {
        JPanel card = new JPanel(new BorderLayout(5, 8));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 58, 89), 1, true),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        card.setBackground(new Color(38, 46, 78));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 110));

        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 0));
        headerPanel.setOpaque(false);

        JLabel lblAutore = new JLabel("👤 " + autore);
        lblAutore.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblAutore.setForeground(Color.WHITE);

        int votoInt = 5;
        try {
            votoInt = Integer.parseInt(voto);
        } catch (NumberFormatException ignored) {}

        String stelline = "★".repeat(votoInt) + "☆".repeat(5 - votoInt);
        JLabel lblStelle = new JLabel(stelline);
        lblStelle.setForeground(new Color(241, 196, 15));
        lblStelle.setFont(new Font("SansSerif", Font.BOLD, 15));

        headerPanel.add(lblAutore);
        headerPanel.add(lblStelle);

        JTextArea txtTesto = new JTextArea(commento);
        txtTesto.setWrapStyleWord(true);
        txtTesto.setLineWrap(true);
        txtTesto.setEditable(false);
        txtTesto.setOpaque(false);
        txtTesto.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtTesto.setForeground(new Color(210, 220, 240));

        card.add(headerPanel, BorderLayout.NORTH);
        card.add(txtTesto, BorderLayout.CENTER);

        return card;
    }

    private void apriFinestraScritturaRecensione() {
        Color sfondoPannello = new Color(28, 34, 58);
        Color sfondoCard = new Color(38, 46, 78);
        Color testoChiaro = Color.WHITE;
        Color grigioScuro = new Color(50, 58, 89);
        Color verdeSuccesso = new Color(46, 204, 113);

        JDialog dialog = new JDialog(this, "Lascia una recensione", true);
        dialog.setSize(440, 320);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel formPanel = new JPanel(new BorderLayout(8, 8));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        formPanel.setBackground(sfondoPannello);

        JPanel panelStelle = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panelStelle.setOpaque(false);
        JLabel lblVotoPrompt = new JLabel("Il tuo voto: ");
        lblVotoPrompt.setForeground(testoChiaro);
        lblVotoPrompt.setFont(new Font("SansSerif", Font.BOLD, 13));

        JComboBox<Integer> comboVoto = new JComboBox<>(new Integer[]{5, 4, 3, 2, 1});
        comboVoto.setBackground(sfondoCard);
        comboVoto.setForeground(testoChiaro);

        JLabel lblStellePrompt = new JLabel(" Stelle");
        lblStellePrompt.setForeground(testoChiaro);
        lblStellePrompt.setFont(new Font("SansSerif", Font.PLAIN, 13));

        panelStelle.add(lblVotoPrompt);
        panelStelle.add(comboVoto);
        panelStelle.add(lblStellePrompt);
        formPanel.add(panelStelle, BorderLayout.NORTH);

        JTextArea txtNuovoCommento = new JTextArea();
        txtNuovoCommento.setLineWrap(true);
        txtNuovoCommento.setWrapStyleWord(true);
        txtNuovoCommento.setBackground(sfondoCard);
        txtNuovoCommento.setForeground(testoChiaro);
        txtNuovoCommento.setCaretColor(testoChiaro);
        txtNuovoCommento.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtNuovoCommento.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scrollNuovo = new JScrollPane(txtNuovoCommento);
        scrollNuovo.setBorder(BorderFactory.createLineBorder(grigioScuro, 1, true));
        formPanel.add(scrollNuovo, BorderLayout.CENTER);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 0));
        bottomPanel.setBackground(sfondoPannello);
        bottomPanel.setBorder(new EmptyBorder(0, 0, 15, 0));

        JButton btnSalva = new JButton("Pubblica");
        btnSalva.setBackground(verdeSuccesso);
        btnSalva.setForeground(Color.WHITE);
        btnSalva.setFocusPainted(false);
        btnSalva.setPreferredSize(new Dimension(120, 36));
        btnSalva.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JButton btnAnnulla = new JButton("Annulla");
        btnAnnulla.setBackground(grigioScuro);
        btnAnnulla.setForeground(Color.WHITE);
        btnAnnulla.setFocusPainted(false);
        btnAnnulla.setPreferredSize(new Dimension(100, 36));
        btnAnnulla.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnAnnulla.addActionListener(e -> dialog.dispose());

        btnSalva.addActionListener(e -> {
            int votoScelto = (Integer) comboVoto.getSelectedItem();
            String testoScritto = txtNuovoCommento.getText().trim();

            boolean successo = controller.aggiungiRecensioneAFilm(filmSelezionato, clienteLoggato, votoScelto, testoScritto);

            if (successo) {
                JOptionPane.showMessageDialog(dialog, "Recensione aggiunta con successo!");
                dialog.dispose();
                aggiornaListaRecensioni();
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