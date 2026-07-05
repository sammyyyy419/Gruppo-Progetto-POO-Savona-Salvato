package gui;

import controller.Controller;
import model.Biglietto;
import model.Cliente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The type Dashboard biglietti acquistati.
 */
public class DashboardBigliettiAcquistati extends JFrame {

    private JPanel mainPanel;
    private JLabel labelTitolo;
    private JScrollPane listaBiglietti;
    private JButton btnTorna;
    private JPanel panelContenitoreBiglietti;

    private Controller controller;
    private Cliente clienteLoggato;

    /**
     * Instantiates a new Dashboard biglietti acquistati.
     *
     * @param controller the controller
     * @param cliente    the cliente
     */
    public DashboardBigliettiAcquistati(Controller controller, Cliente cliente) {
        this.controller = controller;
        this.clienteLoggato = cliente;

        sistemaGrafica();

        setContentPane(mainPanel);
        setTitle("I Tuoi Biglietti - Enterprise Cinema");
        setSize(780, 600);
        setMinimumSize(new Dimension(700, 520));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        caricaBiglietti();

        btnTorna.addActionListener(e -> {
            this.dispose();
            new DashboardCliente(this.controller, this.clienteLoggato);
        });

        setVisible(true);
    }

    /**
     * Aggiorna interfaccia.
     */
    public void aggiornaInterfaccia() {
        caricaBiglietti();
    }

    private void sistemaGrafica() {
        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color testoChiaro = Color.WHITE;
        Color grigioScuro = new Color(50, 58, 89);

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(sfondoScuro);

        JPanel panelTop = new JPanel(new BorderLayout(15, 0));
        panelTop.setBackground(sfondoScuro);
        panelTop.setBorder(new EmptyBorder(15, 20, 15, 20));

        labelTitolo.setForeground(testoChiaro);
        labelTitolo.setFont(new Font("SansSerif", Font.BOLD, 18));
        labelTitolo.setText("I Tuoi Biglietti");

        btnTorna.setText("Indietro");
        btnTorna.setBackground(grigioScuro);
        btnTorna.setForeground(testoChiaro);
        btnTorna.setFocusPainted(false);
        btnTorna.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnTorna.setPreferredSize(new Dimension(100, 32));

        panelTop.add(labelTitolo, BorderLayout.WEST);
        panelTop.add(btnTorna, BorderLayout.EAST);

        listaBiglietti.setBorder(BorderFactory.createEmptyBorder());
        listaBiglietti.setBackground(sfondoPannello);
        listaBiglietti.getViewport().setBackground(sfondoPannello);

        panelContenitoreBiglietti = new JPanel();
        panelContenitoreBiglietti.setLayout(new BoxLayout(panelContenitoreBiglietti, BoxLayout.Y_AXIS));
        panelContenitoreBiglietti.setBackground(sfondoPannello);
        panelContenitoreBiglietti.setBorder(new EmptyBorder(15, 20, 15, 20));

        listaBiglietti.setViewportView(panelContenitoreBiglietti);

        mainPanel.add(panelTop, BorderLayout.NORTH);
        mainPanel.add(listaBiglietti, BorderLayout.CENTER);
    }

    private void caricaBiglietti() {
        panelContenitoreBiglietti.removeAll();
        ArrayList<Biglietto> biglietti = controller.getBigliettiAcquistati();

        if (biglietti.isEmpty()) {
            JLabel vuoto = new JLabel("Nessun biglietto acquistato al momento.");
            vuoto.setForeground(new Color(140, 150, 180));
            vuoto.setFont(new Font("SansSerif", Font.ITALIC, 16));
            panelContenitoreBiglietti.add(vuoto);
        } else {
            for (Biglietto b : biglietti) {
                panelContenitoreBiglietti.add(creaCardBiglietto(b));
                panelContenitoreBiglietti.add(Box.createVerticalStrut(15));
            }
        }
        panelContenitoreBiglietti.revalidate();
        panelContenitoreBiglietti.repaint();
    }

    private JPanel creaCardBiglietto(Biglietto b) {
        JPanel card = new JPanel(new GridLayout(1, 2, 10, 10));
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 58, 89), 1, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(new Color(38, 46, 78));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 140));

        String titoloFilm = b.getProiezione() != null ? b.getProiezione().getFilm().getTitolo().toUpperCase() : "N/D";
        String dataOra = b.getProiezione() != null ? b.getProiezione().getDataOraInizio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "N/D";
        String sala = b.getProiezione() != null ? b.getProiezione().getSala().getNumeroSala() : "N/D";
        String fila = b.getPostoAssegnato() != null ? String.valueOf(b.getPostoAssegnato().getFila()) : "-";
        String numeroPosto = b.getPostoAssegnato() != null ? String.valueOf(b.getPostoAssegnato().getNumeroPosto()) : "-";

        String statoTesto = b.isValido() ? "CONVALIDATO" : "DA CONVALIDARE";
        Color coloreStato = b.isValido() ? new Color(46, 204, 113) : new Color(231, 76, 60);

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setOpaque(false);

        JLabel lblTitolo = new JLabel(titoloFilm);
        lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 16));
        lblTitolo.setForeground(Color.WHITE);
        infoPanel.add(lblTitolo);

        JLabel lblData = new JLabel("Data e Ora: " + dataOra);
        lblData.setForeground(Color.WHITE);
        lblData.setFont(new Font("SansSerif", Font.PLAIN, 13));
        infoPanel.add(lblData);

        JLabel lblSala = new JLabel("Ubicazione: " + sala);
        lblSala.setForeground(Color.WHITE);
        lblSala.setFont(new Font("SansSerif", Font.PLAIN, 13));
        infoPanel.add(lblSala);

        JPanel postoPanel = new JPanel(new GridLayout(5, 1));
        postoPanel.setOpaque(false);

        JLabel lblPosto = new JLabel("FILA: " + fila + "  |  POSTO: " + numeroPosto);
        lblPosto.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblPosto.setForeground(new Color(54, 112, 233));

        JLabel lblCodice = new JLabel("CODICE: " + b.getCodiceUnivoco());
        lblCodice.setFont(new Font("Monospaced", Font.BOLD, 13));
        lblCodice.setForeground(Color.WHITE);

        JLabel lblPrezzo = new JLabel("Pagato: " + String.format("%.2f €", b.getPrezzoFinale()));
        lblPrezzo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        lblPrezzo.setForeground(Color.WHITE);

        JLabel lblStato = new JLabel("Stato: " + statoTesto);
        lblStato.setFont(new Font("SansSerif", Font.BOLD, 13));
        lblStato.setForeground(coloreStato);

        postoPanel.add(lblPosto);
        postoPanel.add(lblCodice);
        postoPanel.add(lblPrezzo);
        postoPanel.add(lblStato);

        if (!b.isValido()) {
            JButton btnRimborsa = new JButton("Annulla e Rimborsa");
            btnRimborsa.setFont(new Font("SansSerif", Font.BOLD, 11));
            btnRimborsa.setBackground(new Color(176, 58, 75));
            btnRimborsa.setForeground(Color.WHITE);
            btnRimborsa.setFocusable(false);
            btnRimborsa.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnRimborsa.addActionListener(e -> {
                int conferma = JOptionPane.showConfirmDialog(this,
                        "Sei sicuro di voler annullare il biglietto?\nQuesta azione eliminerà il ticket e libererà il posto in sala.",
                        "Conferma Annullamento",
                        JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);

                if (conferma == JOptionPane.YES_OPTION) {
                    new DashboardRimborso(controller, clienteLoggato, b, this);
                }
            });
            postoPanel.add(btnRimborsa);
        } else {
            postoPanel.add(new JLabel(""));
        }

        card.add(infoPanel);
        card.add(postoPanel);

        return card;
    }
}