package gui;

import controller.Controller;
import javax.swing.*;
import java.awt.*;

public class DashboardGestioneSale extends JFrame {

    private Controller controller;
    private JPanel mainPanel;

    public DashboardGestioneSale(Controller controller) {
        this.controller = controller;

        setTitle("Mappa Sale - Stato e Manutenzione");
        setSize(700, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JLabel lblTitolo = new JLabel("Piantina Sale Cinematografiche", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 22));
        lblTitolo.setBorder(BorderFactory.createEmptyBorder(15, 0, 10, 0));
        add(lblTitolo, BorderLayout.NORTH);


        mainPanel = new JPanel(new GridLayout(3, 4, 15, 15));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        disegnaMappa();

        add(mainPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    private void disegnaMappa() {
        mainPanel.removeAll();


        for (int i = 1; i <= 12; i++) {
            String nomeSala = "Sala " + i;
            JButton btnSala = new JButton(nomeSala);
            btnSala.setFont(new Font("Arial", Font.BOLD, 16));
            btnSala.setFocusable(false);
            btnSala.setCursor(new Cursor(Cursor.HAND_CURSOR));

            String problemaSegnalato = controller.verificaStatoSala(nomeSala);

            if (problemaSegnalato != null) {
                btnSala.setBackground(new Color(231, 76, 60));
                btnSala.setForeground(Color.WHITE);
                btnSala.setText(nomeSala + " ⚠️");

                btnSala.addActionListener(e -> apriPannelloRisoluzione(nomeSala, problemaSegnalato));
            } else {
                btnSala.setBackground(new Color(46, 204, 113));
                btnSala.setForeground(Color.WHITE);

                btnSala.addActionListener(e -> JOptionPane.showMessageDialog(this, "La " + nomeSala + " è pienamente operativa.", "Tutto OK", JOptionPane.INFORMATION_MESSAGE));
            }

            mainPanel.add(btnSala);
        }

        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void apriPannelloRisoluzione(String nomeSala, String problema) {
        JDialog dialog = new JDialog(this, "Gestione Problema: " + nomeSala, true);
        dialog.setSize(400, 250);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout(10, 10));

        JPanel panelInfo = new JPanel(new BorderLayout(5, 5));
        panelInfo.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JLabel lblAttenzione = new JLabel("⚠️ ANOMALIA REGISTRATA IN " + nomeSala.toUpperCase());
        lblAttenzione.setFont(new Font("Arial", Font.BOLD, 14));
        lblAttenzione.setForeground(Color.RED);
        panelInfo.add(lblAttenzione, BorderLayout.NORTH);

        JTextArea txtDettaglio = new JTextArea("Dettaglio:\n" + problema);
        txtDettaglio.setEditable(false);
        txtDettaglio.setLineWrap(true);
        txtDettaglio.setWrapStyleWord(true);
        txtDettaglio.setFont(new Font("Arial", Font.PLAIN, 14));
        panelInfo.add(new JScrollPane(txtDettaglio), BorderLayout.CENTER);

        dialog.add(panelInfo, BorderLayout.CENTER);

        JPanel panelBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton btnRisolvi = new JButton("RISOLVI PROBLEMA");
        btnRisolvi.setBackground(new Color(41, 128, 185));
        btnRisolvi.setForeground(Color.WHITE);
        btnRisolvi.setFont(new Font("Arial", Font.BOLD, 14));

        btnRisolvi.addActionListener(ev -> {
            int conferma = JOptionPane.showConfirmDialog(dialog, "Confermi di aver risolto il problema in questa sala?", "Conferma Risoluzione", JOptionPane.YES_NO_OPTION);
            if (conferma == JOptionPane.YES_OPTION) {
                controller.riparaSala(nomeSala);
                dialog.dispose();
                disegnaMappa(); // Ridisegna i colori dinamicamente!
            }
        });

        panelBottoni.add(btnRisolvi);
        dialog.add(panelBottoni, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}