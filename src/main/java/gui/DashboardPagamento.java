package gui;

import controller.Controller;
import model.Cliente;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

public class DashboardPagamento extends JFrame {

    private JPanel panelPagamento;
    private JPanel panelAlto;
    private JButton tornaAlMenuButton;
    private JLabel labelTitolo;
    private JPanel panelCentrale;
    private JLabel labelTipoPagamento;
    private JComboBox<String> comboTipoPagamento;
    private JPanel panelCampiCarta;
    private JLabel labelNumeroCarta;
    private JTextField textNumeroCarta;
    private JLabel labelCVC;
    private JTextField textCVC;
    private JLabel labelDataScadenza;
    private JTextField textDataScadenza;
    private JLabel labelIntestatario;
    private JTextField textIntestatario;
    private JLabel labelCodiceSconto;
    private JTextField textCodiceSconto;
    private JButton buttonConferma;
    private JLabel labelRiepilogo;

    private Controller controller;
    private Cliente clienteLoggato;
    private double totaleIniziale;
    private double totaleScontato;
    private double percentualeScontoApplicata = 0.0;

    public DashboardPagamento(Controller controller, Cliente cliente) {
        this.controller = controller;
        this.clienteLoggato = cliente;

        setContentPane(panelPagamento);
        setTitle("Check-out Pagamento - Enterprise Cinema");
        setSize(580, 520);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        // Popoliamo il menu a tendina
        comboTipoPagamento.addItem("Carta di Credito");
        comboTipoPagamento.addItem("PayPal");

        // Il pulsante parte disabilitato finché non compiliamo i campi
        buttonConferma.setEnabled(false);

        totaleIniziale = controller.calcolaTotaleCarrello();
        totaleScontato = totaleIniziale;
        labelRiepilogo.setText("Riepilogo Tot. : " + String.format("%.2f €", totaleScontato));

        // LISTENER SUI CAMPI DI TESTO (Controllo in tempo reale)
        DocumentListener controlloCampiListener = new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) { controllaCampiObbligatori(); }
            @Override
            public void removeUpdate(DocumentEvent e) { controllaCampiObbligatori(); }
            @Override
            public void changedUpdate(DocumentEvent e) { controllaCampiObbligatori(); }
        };

        textNumeroCarta.getDocument().addDocumentListener(controlloCampiListener);
        textIntestatario.getDocument().addDocumentListener(controlloCampiListener);
        textCVC.getDocument().addDocumentListener(controlloCampiListener);
        textDataScadenza.getDocument().addDocumentListener(controlloCampiListener);

        comboTipoPagamento.addActionListener(e -> {
            String metodoScelto = (String) comboTipoPagamento.getSelectedItem();
            if (metodoScelto != null && metodoScelto.equalsIgnoreCase("PayPal")) {
                toggleCampiCarta(false);
            } else {
                toggleCampiCarta(true);
            }
            controllaCampiObbligatori(); // Ricontrolla i campi ogni volta che cambi metodo
        });

        textCodiceSconto.addActionListener(e -> applicareLogicaSconto());

        tornaAlMenuButton.addActionListener(e -> this.dispose());

        buttonConferma.addActionListener(e -> {
            String metodoScelto = (String) comboTipoPagamento.getSelectedItem();

            // Riapplica lo sconto se l'utente ha scritto qualcosa ma non ha premuto "Invio"
            if (!textCodiceSconto.getText().trim().isEmpty() && percentualeScontoApplicata == 0.0) {
                applicareLogicaSconto();
            }

            try {
                // TENTA DI CONFERMARE L'ACQUISTO
                controller.confermaAcquistoCarrello(metodoScelto, percentualeScontoApplicata);

                // SE NON CI SONO ERRORI, MOSTRA IL MESSAGGIO E CHIUDI LA FINESTRA
                JOptionPane.showMessageDialog(this,
                        "Pagamento riuscito con successo, puoi visualizzare i tuoi biglietti nell'area dedicata: Visualizza Biglietti Acquistati",
                        "Acquisto Completato",
                        JOptionPane.INFORMATION_MESSAGE);

                this.dispose();

            } catch (exception.SalaPienaException ex) {
                // SE LA SALA È PIENA, MOSTRA L'ERRORE E BLOCCA IL PAGAMENTO!
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore - Sala Piena", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                // CATTURA QUALSIASI ALTRO ERRORE GENERICO
                JOptionPane.showMessageDialog(this, "Errore durante l'acquisto: " + ex.getMessage(), "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Esegue un primo controllo all'apertura per gestire lo stato di base
        controllaCampiObbligatori();
        setVisible(true);
    }

    private void controllaCampiObbligatori() {
        String metodoScelto = (String) comboTipoPagamento.getSelectedItem();

        if (metodoScelto == null) {
            buttonConferma.setEnabled(false);
            return;
        }

        if (metodoScelto.equalsIgnoreCase("PayPal")) {
            buttonConferma.setEnabled(true);
        } else if (metodoScelto.equalsIgnoreCase("Carta di Credito")) {
            boolean campiPieni = !textNumeroCarta.getText().trim().isEmpty() &&
                    !textIntestatario.getText().trim().isEmpty() &&
                    !textCVC.getText().trim().isEmpty() &&
                    !textDataScadenza.getText().trim().isEmpty();

            buttonConferma.setEnabled(campiPieni);
        }
    }

    private void applicareLogicaSconto() {
        String codice = textCodiceSconto.getText().trim();
        if (codice.isEmpty()) return;

        double percentuale = controller.valutaCodiceSconto(codice);

        if (percentuale > 0) {
            percentualeScontoApplicata = percentuale;
            double importoSconto = totaleIniziale * (percentuale / 100.0);
            totaleScontato = totaleIniziale - importoSconto;

            labelRiepilogo.setText("<html>Riepilogo Tot. : <s>" + String.format("%.2f €", totaleIniziale) + "</s> " +
                    "<font color='green'><b>" + String.format("%.2f €", totaleScontato) + "</b> (-" + (int)percentuale + "%)</font></html>");

            textCodiceSconto.setEnabled(false);
            JOptionPane.showMessageDialog(this, "Codice coupon valido! Sconto del " + (int)percentuale + "% applicato.", "Sconto Attivato", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Il codice sconto inserito non è valido.", "Coupon Non Valido", JOptionPane.ERROR_MESSAGE);
            textCodiceSconto.setText("");
        }
    }

    private void toggleCampiCarta(boolean enable) {
        textNumeroCarta.setEnabled(enable);
        textIntestatario.setEnabled(enable);
        textCVC.setEnabled(enable);
        textDataScadenza.setEnabled(enable);

        Color coloreSfondo = enable ? Color.WHITE : new Color(225, 225, 225);
        textNumeroCarta.setBackground(coloreSfondo);
        textIntestatario.setBackground(coloreSfondo);
        textCVC.setBackground(coloreSfondo);
        textDataScadenza.setBackground(coloreSfondo);

        if (!enable) {
            textNumeroCarta.setText("");
            textIntestatario.setText("");
            textCVC.setText("");
            textDataScadenza.setText("");
        }
    }
}