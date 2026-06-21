package gui;

import controller.Controller;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardGestioneSale extends JFrame {

    private Controller controller;
    private JPanel mainPanel;
    private JPanel panelGrigliaSale;
    private JButton indietroButton;

    public DashboardGestioneSale(Controller controller) {
        this.controller = controller;

        sistemaGrafica();

        setContentPane(mainPanel);
        setTitle("Mappa Sale - Stato e Manutenzione");
        setSize(780, 560);
        setMinimumSize(new Dimension(700, 480));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        indietroButton.addActionListener(e -> this.dispose());

        setVisible(true);
    }

    private void sistemaGrafica() {
        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color grigioScuro = new Color(50, 58, 89);

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.setBackground(sfondoScuro);

        JPanel panelTop = new JPanel(new BorderLayout(15, 0));
        panelTop.setBackground(sfondoScuro);
        panelTop.setBorder(new EmptyBorder(15, 20, 10, 20));

        JLabel lblTitolo = new JLabel("Piantina Sale Cinematografiche", SwingConstants.CENTER);
        lblTitolo.setForeground(Color.WHITE);
        lblTitolo.setFont(new Font("SansSerif", Font.BOLD, 18));

        indietroButton = new JButton("Indietro");
        indietroButton.setBackground(grigioScuro);
        indietroButton.setForeground(Color.WHITE);
        indietroButton.setFocusPainted(false);
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.setPreferredSize(new Dimension(100, 32));

        panelTop.add(lblTitolo, BorderLayout.WEST);
        panelTop.add(indietroButton, BorderLayout.EAST);
        mainPanel.add(panelTop, BorderLayout.NORTH);

        panelGrigliaSale = new JPanel(new GridLayout(3, 4, 15, 15));
        panelGrigliaSale.setBackground(sfondoPannello);
        panelGrigliaSale.setBorder(new EmptyBorder(20, 20, 20, 20));

        disegnaMappa();

        mainPanel.add(panelGrigliaSale, BorderLayout.CENTER);
    }

    private void disegnaMappa() {
        panelGrigliaSale.removeAll();

        Color verdeSuccesso = new Color(46, 204, 113);
        Color rossoErrore = new Color(176, 58, 75);

        for (int i = 1; i <= 12; i++) {
            String nomeSala = "Sala " + i;
            JButton btnSala = new JButton(nomeSala);
            btnSala.setFont(new Font("SansSerif", Font.BOLD, 15));
            btnSala.setFocusable(false);
            btnSala.setCursor(new Cursor(Cursor.HAND_CURSOR));

            String problemaSegnalato = controller.verificaStatoSala(nomeSala);

            if (problemaSegnalato != null) {
                btnSala.setBackground(rossoErrore);
                btnSala.setForeground(Color.WHITE);
                btnSala.setText(nomeSala + " ⚠️");

                btnSala.addActionListener(e -> apriPannelloRisoluzione(nomeSala, problemaSegnalato));
            } else {
                btnSala.setBackground(verdeSuccesso);
                btnSala.setForeground(Color.WHITE);

                btnSala.addActionListener(e -> JOptionPane.showMessageDialog(this, "La " + nomeSala + " è pienamente operativa.", "Tutto OK", JOptionPane.INFORMATION_MESSAGE));
            }

            panelGrigliaSale.add(btnSala);
        }

        panelGrigliaSale.revalidate();
        panelGrigliaSale.repaint();
    }

    private void apriPannelloRisoluzione(String nomeSala, String problema) {
        Color sfondoPannello = new Color(28, 34, 58);
        Color sfondoCard = new Color(38, 46, 78);
        Color testoChiaro = Color.WHITE;
        Color bluAcceso = new Color(54, 112, 233);
        Color grigioScuro = new Color(50, 58, 89);
        Color rossoErrore = new Color(176, 58, 75);

        JDialog dialog = new JDialog(this, "Gestione Problema: " + nomeSala, true);
        dialog.setSize(440, 280);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel panelInfo = new JPanel(new BorderLayout(5, 10));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        panelInfo.setBackground(sfondoPannello);

        JLabel lblAttenzione = new JLabel("⚠️ ANOMALIA REGISTRATA IN " + nomeSala.toUpperCase());
        lblAttenzione.setFont(new Font("SansSerif", Font.BOLD, 14));
        lblAttenzione.setForeground(rossoErrore);
        panelInfo.add(lblAttenzione, BorderLayout.NORTH);

        JTextArea txtDettaglio = new JTextArea("Dettaglio:\n" + problema);
        txtDettaglio.setEditable(false);
        txtDettaglio.setLineWrap(true);
        txtDettaglio.setWrapStyleWord(true);
        txtDettaglio.setBackground(sfondoCard);
        txtDettaglio.setForeground(testoChiaro);
        txtDettaglio.setCaretColor(testoChiaro);
        txtDettaglio.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtDettaglio.setBorder(BorderFactory.createEmptyBorder(8, 8, 8, 8));

        JScrollPane scroll = new JScrollPane(txtDettaglio);
        scroll.setBorder(BorderFactory.createLineBorder(grigioScuro, 1, true));
        panelInfo.add(scroll, BorderLayout.CENTER);

        dialog.add(panelInfo, BorderLayout.CENTER);

        JPanel panelBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBottoni.setBackground(sfondoPannello);
        panelBottoni.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JButton btnRisolvi = new JButton("RISOLVI PROBLEMA");
        btnRisolvi.setBackground(bluAcceso);
        btnRisolvi.setForeground(Color.WHITE);
        btnRisolvi.setFocusPainted(false);
        btnRisolvi.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnRisolvi.setPreferredSize(new Dimension(180, 36));
        btnRisolvi.setCursor(new Cursor(Cursor.HAND_CURSOR));

        btnRisolvi.addActionListener(ev -> {
            int conferma = JOptionPane.showConfirmDialog(dialog, "Confermi di aver risolto il problema in questa sala?", "Conferma Risoluzione", JOptionPane.YES_NO_OPTION);
            if (conferma == JOptionPane.YES_OPTION) {
                controller.riparaSala(nomeSala);
                dialog.dispose();
                disegnaMappa();
            }
        });

        panelBottoni.add(btnRisolvi);
        dialog.add(panelBottoni, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}