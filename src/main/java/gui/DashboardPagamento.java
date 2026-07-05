package gui;

import controller.Controller;
import model.Cliente;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;

/**
 * The type Dashboard pagamento.
 */
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

    /**
     * Instantiates a new Dashboard pagamento.
     *
     * @param controller the controller
     * @param cliente    the cliente
     */
    public DashboardPagamento(Controller controller, Cliente cliente) {
        this.controller = controller;
        this.clienteLoggato = cliente;

        sistemaGrafica();

        setContentPane(panelPagamento);
        setTitle("Check-out Pagamento - Enterprise Cinema");
        setSize(640, 560);
        setMinimumSize(new Dimension(580, 500));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        comboTipoPagamento.addItem("Carta di Credito");
        comboTipoPagamento.addItem("PayPal");

        buttonConferma.setEnabled(false);

        totaleIniziale = controller.calcolaTotaleCarrello();
        totaleScontato = totaleIniziale;
        labelRiepilogo.setText("Riepilogo Tot. : " + String.format("%.2f €", totaleScontato));

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
            controllaCampiObbligatori();
        });

        textCodiceSconto.addActionListener(e -> applicareLogicaSconto());

        tornaAlMenuButton.addActionListener(e -> this.dispose());

        buttonConferma.addActionListener(e -> {
            String metodoScelto = (String) comboTipoPagamento.getSelectedItem();

            if (!textCodiceSconto.getText().trim().isEmpty() && percentualeScontoApplicata == 0.0) {
                applicareLogicaSconto();
            }

            try {
                controller.confermaAcquistoCarrello(metodoScelto, percentualeScontoApplicata);

                JOptionPane.showMessageDialog(this,
                        "Pagamento riuscito con successo, puoi visualizzare i tuoi biglietti nell'area dedicata: Visualizza Biglietti Acquistati",
                        "Acquisto Completato",
                        JOptionPane.INFORMATION_MESSAGE);

                this.dispose();

            } catch (exception.SalaPienaException ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore - Sala Piena", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore durante l'acquisto: " + ex.getMessage(), "Errore di Sistema", JOptionPane.ERROR_MESSAGE);
            }
        });

        controllaCampiObbligatori();
        setVisible(true);
    }

    private void sistemaGrafica() {
        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color sfondoCard = new Color(38, 46, 78);
        Color testoChiaro = Color.WHITE;
        Color bluAcceso = new Color(54, 112, 233);
        Color grigioScuro = new Color(50, 58, 89);

        panelPagamento.removeAll();
        panelPagamento.setLayout(new BorderLayout());
        panelPagamento.setBackground(sfondoScuro);

        panelAlto.removeAll();
        panelAlto.setLayout(new BorderLayout(15, 0));
        panelAlto.setBackground(sfondoScuro);
        panelAlto.setBorder(new EmptyBorder(15, 20, 10, 20));

        labelTitolo.setText("Check-out Pagamento");
        labelTitolo.setForeground(testoChiaro);
        labelTitolo.setFont(new Font("SansSerif", Font.BOLD, 18));

        tornaAlMenuButton.setText("Indietro");
        tornaAlMenuButton.setBackground(grigioScuro);
        tornaAlMenuButton.setForeground(testoChiaro);
        tornaAlMenuButton.setFocusPainted(false);
        tornaAlMenuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tornaAlMenuButton.setPreferredSize(new Dimension(100, 32));

        panelAlto.add(labelTitolo, BorderLayout.WEST);
        panelAlto.add(tornaAlMenuButton, BorderLayout.EAST);

        panelCentrale.removeAll();
        panelCentrale.setLayout(new GridBagLayout());
        panelCentrale.setBackground(sfondoPannello);
        panelCentrale.setBorder(new EmptyBorder(20, 25, 20, 25));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel[] labels = {labelTipoPagamento, labelNumeroCarta, labelIntestatario, labelDataScadenza, labelCVC, labelCodiceSconto};
        for (JLabel l : labels) {
            l.setForeground(testoChiaro);
            l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        }

        comboTipoPagamento.setBackground(sfondoCard);
        comboTipoPagamento.setForeground(testoChiaro);
        comboTipoPagamento.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JTextField[] fields = {textNumeroCarta, textIntestatario, textDataScadenza, textCVC, textCodiceSconto};
        for (JTextField f : fields) {
            f.setBackground(sfondoCard);
            f.setForeground(testoChiaro);
            f.setCaretColor(testoChiaro);
            f.setFont(new Font("SansSerif", Font.PLAIN, 13));
            f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(grigioScuro, 1, true),
                    BorderFactory.createEmptyBorder(6, 10, 6, 10)
            ));
        }

        c.gridx = 0; c.gridy = 0; c.weightx = 0.0; panelCentrale.add(labelTipoPagamento, c);
        c.gridx = 1; c.gridy = 0; c.weightx = 1.0; panelCentrale.add(comboTipoPagamento, c);

        c.gridx = 0; c.gridy = 1; c.weightx = 0.0; panelCentrale.add(labelNumeroCarta, c);
        c.gridx = 1; c.gridy = 1; c.weightx = 1.0; panelCentrale.add(textNumeroCarta, c);

        c.gridx = 0; c.gridy = 2; c.weightx = 0.0; panelCentrale.add(labelIntestatario, c);
        c.gridx = 1; c.gridy = 2; c.weightx = 1.0; panelCentrale.add(textIntestatario, c);

        c.gridx = 0; c.gridy = 3; c.weightx = 0.0; panelCentrale.add(labelDataScadenza, c);
        c.gridx = 1; c.gridy = 3; c.weightx = 1.0; panelCentrale.add(textDataScadenza, c);

        c.gridx = 0; c.gridy = 4; c.weightx = 0.0; panelCentrale.add(labelCVC, c);
        c.gridx = 1; c.gridy = 4; c.weightx = 1.0; panelCentrale.add(textCVC, c);

        c.gridx = 0; c.gridy = 5; c.weightx = 0.0; panelCentrale.add(labelCodiceSconto, c);
        c.gridx = 1; c.gridy = 5; c.weightx = 1.0; panelCentrale.add(textCodiceSconto, c);

        JPanel panelBottom = new JPanel();
        panelBottom.setLayout(new BorderLayout());
        panelBottom.setBackground(sfondoScuro);
        panelBottom.setBorder(new EmptyBorder(12, 20, 15, 20));

        labelRiepilogo.setForeground(testoChiaro);
        labelRiepilogo.setFont(new Font("SansSerif", Font.BOLD, 15));

        buttonConferma.setText("Conferma Acquisto");
        buttonConferma.setBackground(bluAcceso);
        buttonConferma.setForeground(testoChiaro);
        buttonConferma.setFocusPainted(false);
        buttonConferma.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonConferma.setPreferredSize(new Dimension(160, 40));
        buttonConferma.setFont(new Font("SansSerif", Font.BOLD, 13));

        panelBottom.add(labelRiepilogo, BorderLayout.WEST);
        panelBottom.add(buttonConferma, BorderLayout.EAST);

        panelPagamento.add(panelAlto, BorderLayout.NORTH);
        panelPagamento.add(panelCentrale, BorderLayout.CENTER);
        panelPagamento.add(panelBottom, BorderLayout.SOUTH);
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
                    "<font color='#2ECC71'><b>" + String.format("%.2f €", totaleScontato) + "</b> (-" + (int)percentuale + "%)</font></html>");

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

        Color coloreSfondo = enable ? new Color(38, 46, 78) : new Color(28, 34, 58);
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