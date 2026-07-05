package gui;

import controller.Controller;
import model.Biglietto;
import model.Cliente;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * The type Dashboard rimborso.
 */
public class DashboardRimborso extends JFrame {

    private JPanel panelRimborso;
    private JPanel panelTop;
    private JButton annulaButton;
    private JLabel labelRimborso;
    private JPanel panelDati;
    private JLabel labelIBAN;
    private JTextField textIBAN;
    private JPanel panelBottom;
    private JLabel labelTotRimborso;
    private JButton autorizzaRimborsoButton;

    private Controller controller;
    private Cliente clienteLoggato;
    private Biglietto bigliettoDaRimborsare;
    private DashboardBigliettiAcquistati finestraPadre;

    /**
     * Instantiates a new Dashboard rimborso.
     *
     * @param controller    the controller
     * @param cliente       the cliente
     * @param biglietto     the biglietto
     * @param finestraPadre the finestra padre
     */
    public DashboardRimborso(Controller controller, Cliente cliente, Biglietto biglietto, DashboardBigliettiAcquistati finestraPadre) {
        this.controller = controller;
        this.clienteLoggato = cliente;
        this.bigliettoDaRimborsare = biglietto;
        this.finestraPadre = finestraPadre;

        sistemaGrafica();

        setContentPane(panelRimborso);
        setTitle("Rimborso Biglietto - " + biglietto.getCodiceUnivoco());
        setSize(540, 240);
        setMinimumSize(new Dimension(480, 210));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(finestraPadre);

        labelTotRimborso.setText("Tot. da rimborsare: " + String.format("%.2f €", biglietto.getPrezzoFinale()));

        autorizzaRimborsoButton.setEnabled(false);

        textIBAN.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { controllaCampi(); }
            @Override
            public void removeUpdate(DocumentEvent e) { controllaCampi(); }
            @Override
            public void changedUpdate(DocumentEvent e) { controllaCampi(); }

            private void controllaCampi() {
                boolean hasTesto = !textIBAN.getText().trim().isEmpty();
                autorizzaRimborsoButton.setEnabled(hasTesto);
            }
        });

        annulaButton.addActionListener(e -> {
            this.dispose();
        });

        autorizzaRimborsoButton.addActionListener(e -> {
            String ibanInserito = textIBAN.getText().trim();

            if (ibanInserito.length() < 5) {
                JOptionPane.showMessageDialog(this, "Inserisci un IBAN valido per procedere con lo storno.", "Errore IBAN", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                controller.rimborsaSingoloBiglietto(bigliettoDaRimborsare);

                JOptionPane.showMessageDialog(this,
                        "Rimborso autorizzato con successo!\nL'importo di " + String.format("%.2f €", bigliettoDaRimborsare.getPrezzoFinale()) + " sarà stornato sull'IBAN:\n" + ibanInserito + "\n\nIl posto in sala è stato nuovamente liberato.",
                        "Transazione Completata", JOptionPane.INFORMATION_MESSAGE);

                finestraPadre.aggiornaInterfaccia();

                this.dispose();

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore durante il rimborso: " + ex.getMessage(), "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
            }
        });

        setVisible(true);
    }

    private void sistemaGrafica() {
        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color sfondoCard = new Color(38, 46, 78);
        Color testoChiaro = Color.WHITE;
        Color bluAcceso = new Color(54, 112, 233);
        Color grigioScuro = new Color(50, 58, 89);

        panelRimborso.removeAll();
        panelRimborso.setLayout(new BorderLayout());
        panelRimborso.setBackground(sfondoScuro);

        panelTop.removeAll();
        panelTop.setLayout(new BorderLayout(15, 0));
        panelTop.setBackground(sfondoScuro);
        panelTop.setBorder(new EmptyBorder(15, 20, 10, 20));

        labelRimborso.setText("Richiesta di Rimborso");
        labelRimborso.setForeground(testoChiaro);
        labelRimborso.setFont(new Font("SansSerif", Font.BOLD, 16));

        annulaButton.setText("Annulla");
        annulaButton.setBackground(grigioScuro);
        annulaButton.setForeground(testoChiaro);
        annulaButton.setFocusPainted(false);
        annulaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        annulaButton.setPreferredSize(new Dimension(90, 30));

        panelTop.add(labelRimborso, BorderLayout.WEST);
        panelTop.add(annulaButton, BorderLayout.EAST);

        panelDati.removeAll();
        panelDati.setLayout(new GridBagLayout());
        panelDati.setBackground(sfondoPannello);
        panelDati.setBorder(new EmptyBorder(15, 20, 15, 20));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(5, 5, 5, 5);
        c.fill = GridBagConstraints.HORIZONTAL;

        labelIBAN.setText("IBAN per accredito:");
        labelIBAN.setForeground(testoChiaro);
        labelIBAN.setFont(new Font("SansSerif", Font.PLAIN, 13));
        labelIBAN.setPreferredSize(new Dimension(120, 30));

        textIBAN.setBackground(sfondoCard);
        textIBAN.setForeground(testoChiaro);
        textIBAN.setCaretColor(testoChiaro);
        textIBAN.setFont(new Font("Monospaced", Font.PLAIN, 13));
        textIBAN.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(grigioScuro, 1, true),
                BorderFactory.createEmptyBorder(5, 8, 5, 8)
        ));

        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 0.0;
        panelDati.add(labelIBAN, c);

        c.gridx = 1;
        c.gridy = 0;
        c.weightx = 1.0;
        panelDati.add(textIBAN, c);

        panelBottom.removeAll();
        panelBottom.setLayout(new BorderLayout());
        panelBottom.setBackground(sfondoScuro);
        panelBottom.setBorder(new EmptyBorder(12, 20, 15, 20));

        labelTotRimborso.setForeground(testoChiaro);
        labelTotRimborso.setFont(new Font("SansSerif", Font.BOLD, 14));

        autorizzaRimborsoButton.setText("Conferma Rimborso");
        autorizzaRimborsoButton.setBackground(bluAcceso);
        autorizzaRimborsoButton.setForeground(testoChiaro);
        autorizzaRimborsoButton.setFocusPainted(false);
        autorizzaRimborsoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        autorizzaRimborsoButton.setPreferredSize(new Dimension(160, 38));
        autorizzaRimborsoButton.setFont(new Font("SansSerif", Font.BOLD, 12));

        panelBottom.add(labelTotRimborso, BorderLayout.WEST);
        panelBottom.add(autorizzaRimborsoButton, BorderLayout.EAST);

        panelRimborso.add(panelTop, BorderLayout.NORTH);
        panelRimborso.add(panelDati, BorderLayout.CENTER);
        panelRimborso.add(panelBottom, BorderLayout.SOUTH);
    }
}