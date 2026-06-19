package gui;

import controller.Controller;
import model.Cliente;
import javax.swing.*;
import java.awt.*;

public class DashboardPagamento extends JFrame {

    private JPanel panelPagamento;
    private JPanel panelAlto;
    private JButton tornaAlMenuButton;
    private JLabel labelTitolo;
    private JPanel panelCentrale;
    private JLabel labelTipoPagamento;
    private JComboBox comboTipoPagamento;
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

        totaleIniziale = controller.calcolaTotaleCarrello();
        totaleScontato = totaleIniziale;
        labelRiepilogo.setText("Riepilogo Tot. : " + String.format("%.2f €", totaleScontato));

        comboTipoPagamento.addActionListener(e -> {
            String metodoScelto = (String) comboTipoPagamento.getSelectedItem();
            if (metodoScelto != null && metodoScelto.equalsIgnoreCase("PayPal")) {
                toggleCampiCarta(false);
            } else {
                toggleCampiCarta(true);
            }
        });

        textCodiceSconto.addActionListener(e -> {
            applicareLogicaSconto();
        });

        tornaAlMenuButton.addActionListener(e -> {
            this.dispose();
        });
        buttonConferma.addActionListener(e -> {
            String metodoScelto = (String) comboTipoPagamento.getSelectedItem();

            if (metodoScelto == null) {
                JOptionPane.showMessageDialog(this, "Seleziona un metodo di pagamento.", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (metodoScelto.equalsIgnoreCase("Carta di Credito")) {
                if (textNumeroCarta.getText().trim().isEmpty() ||
                        textIntestatario.getText().trim().isEmpty() ||
                        textCVC.getText().trim().isEmpty() ||
                        textDataScadenza.getText().trim().isEmpty()) {

                    JOptionPane.showMessageDialog(this, "Compila tutti i campi della carta di credito per proseguire.", "Campi Mancanti", JOptionPane.WARNING_MESSAGE);
                    return;
                }
            }

            if (!textCodiceSconto.getText().trim().isEmpty() && percentualeScontoApplicata == 0.0) {
                applicareLogicaSconto();
            }
            controller.confermaAcquistoCarrello(metodoScelto, percentualeScontoApplicata);

            JOptionPane.showMessageDialog(this,
                    "Pagamento di " + String.format("%.2f €", totaleScontato) + " autorizzato con successo tramite " + metodoScelto + ".\n" +
                            "La tua prenotazione è ora in stato: CONFERMATA!",
                    "Acquisto Completato",
                    JOptionPane.INFORMATION_MESSAGE);

            this.dispose();
        });

        setVisible(true);
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

            textCodiceSconto.setEnabled(false); //evitare doppi sconti cumulati
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