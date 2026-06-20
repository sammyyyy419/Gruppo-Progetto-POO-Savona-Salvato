package gui;

import controller.Controller;
import model.Biglietto;
import model.Cliente;

import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DashboardBigliettiAcquistati extends JFrame {

    private JPanel mainPanel;
    private JLabel labelTitolo;
    private JScrollPane listaBiglietti;
    private JButton btnTorna;
    private JPanel panelContenitoreBiglietti;

    private Controller controller;
    private Cliente clienteLoggato;

    public DashboardBigliettiAcquistati(Controller controller, Cliente cliente) {
        this.controller = controller;
        this.clienteLoggato = cliente;

        setContentPane(mainPanel);
        setTitle("I Tuoi Biglietti - Enterprise Cinema");
        setSize(750, 600);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        panelContenitoreBiglietti = new JPanel();
        panelContenitoreBiglietti.setLayout(new BoxLayout(panelContenitoreBiglietti, BoxLayout.Y_AXIS));
        listaBiglietti.setViewportView(panelContenitoreBiglietti);

        caricaBiglietti();

        btnTorna.addActionListener(e -> {
            this.dispose();
            new DashboardCliente(this.controller, this.clienteLoggato);
        });

        setVisible(true);
    }

    public void aggiornaInterfaccia() {
        caricaBiglietti();
    }

    private void caricaBiglietti() {
        panelContenitoreBiglietti.removeAll();
        ArrayList<Biglietto> biglietti = controller.getBigliettiAcquistati();

        if (biglietti.isEmpty()) {
            JLabel vuoto = new JLabel("Nessun biglietto acquistato al momento.");
            vuoto.setFont(new Font("Arial", Font.ITALIC, 16));
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
                BorderFactory.createLineBorder(Color.DARK_GRAY, 2, true),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        card.setBackground(new Color(250, 250, 250));
        card.setMaximumSize(new Dimension(Integer.MAX_VALUE, 130));

        String titoloFilm = b.getProiezione() != null ? b.getProiezione().getFilm().getTitolo().toUpperCase() : "N/D";
        String dataOra = b.getProiezione() != null ? b.getProiezione().getDataOraInizio().format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : "N/D";
        String sala = b.getProiezione() != null ? b.getProiezione().getSala().getNumeroSala() : "N/D";
        String fila = b.getPostoAssegnato() != null ? String.valueOf(b.getPostoAssegnato().getFila()) : "-";
        String numeroPosto = b.getPostoAssegnato() != null ? String.valueOf(b.getPostoAssegnato().getNumeroPosto()) : "-";

        String statoTesto = b.isValido() ? "CONVALIDATO" : "DA CONVALIDARE";
        Color coloreStato = b.isValido() ? new Color(39, 174, 96) : Color.RED;

        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setOpaque(false);
        JLabel lblTitolo = new JLabel(titoloFilm);
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 18));
        infoPanel.add(lblTitolo);
        infoPanel.add(new JLabel("Data e Ora: " + dataOra));
        infoPanel.add(new JLabel("Ubicazione: " + sala));

        JPanel postoPanel = new JPanel(new GridLayout(5, 1));
        postoPanel.setOpaque(false);

        JLabel lblPosto = new JLabel("FILA: " + fila + "  |  POSTO: " + numeroPosto);
        lblPosto.setFont(new Font("Arial", Font.BOLD, 16));
        lblPosto.setForeground(new Color(41, 128, 185));

        JLabel lblCodice = new JLabel("CODICE: " + b.getCodiceUnivoco());
        lblCodice.setFont(new Font("Monospaced", Font.BOLD, 14));

        JLabel lblStato = new JLabel("Stato: " + statoTesto);
        lblStato.setFont(new Font("Arial", Font.BOLD, 14));
        lblStato.setForeground(coloreStato);

        postoPanel.add(lblPosto);
        postoPanel.add(lblCodice);
        postoPanel.add(new JLabel("Pagato: " + String.format("%.2f €", b.getPrezzoFinale())));
        postoPanel.add(lblStato);

        // Se non è stato ancora obliterato, mostro il pulsante di rimborso
        if (!b.isValido()) {
            JButton btnRimborsa = new JButton("Annulla e Rimborsa");
            btnRimborsa.setFont(new Font("Arial", Font.PLAIN, 11));
            btnRimborsa.setForeground(Color.RED);
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