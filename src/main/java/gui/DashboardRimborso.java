package gui;

import controller.Controller;
import model.Biglietto;
import model.Cliente;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

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

    public DashboardRimborso(Controller controller, Cliente cliente, Biglietto biglietto, DashboardBigliettiAcquistati finestraPadre) {
        this.controller = controller;
        this.clienteLoggato = cliente;
        this.bigliettoDaRimborsare = biglietto;
        this.finestraPadre = finestraPadre;

        setContentPane(panelRimborso);
        setTitle("Rimborso Biglietto - " + biglietto.getCodiceUnivoco());
        setSize(450, 250);
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
}