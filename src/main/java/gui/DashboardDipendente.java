package gui;

import controller.Controller;
import model.Dipendente;
import model.Biglietto;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;

public class DashboardDipendente extends JFrame {
    private JPanel mainPanel;
    private JLabel labelBenvenuto;
    private JButton buttonConvalidaBiglietti;
    private JButton buttonInserisciFilm;
    private JButton buttonGestioneSale;
    private JButton buttonLeggiSegnalazioni;
    private JButton buttonInviaSegnalazione;
    private JButton buttonTornaLogin;
    private JButton buttonEsci;
    private JButton btnModificaCatalogoFilm;
    private JButton buttonModifcaCredenziali;

    private Controller controller;
    private Dipendente dipendenteLoggato;

    public DashboardDipendente(Controller controller, Dipendente dipendente) {
        this.controller = controller;
        this.dipendenteLoggato = dipendente;

        setTitle("Pannello Staff - " + dipendente.getRuolo().toUpperCase());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        sistemaGrafica();

        setContentPane(mainPanel);

        setSize(760, 580);
        setMinimumSize(new Dimension(700, 520));
        setLocationRelativeTo(null);

        labelBenvenuto.setText("Benvenuto/a, " + dipendente.getNome() + " " + dipendente.getCognome() + " (" + dipendente.getRuolo() + ")");


        String ruolo = dipendente.getRuolo().toLowerCase();
        if (ruolo.equals("addetto alle pulizie")) {
            buttonConvalidaBiglietti.setEnabled(false);
            buttonInserisciFilm.setEnabled(false);
            buttonGestioneSale.setEnabled(true);
            btnModificaCatalogoFilm.setEnabled(false);
        } else if (ruolo.equals("cassiere")) {
            buttonInserisciFilm.setEnabled(false);
            buttonGestioneSale.setEnabled(false);
            btnModificaCatalogoFilm.setEnabled(false);
        } else if (ruolo.equals("proiezionista")) {
            buttonConvalidaBiglietti.setEnabled(false);
        }

        buttonEsci.addActionListener(e -> System.exit(0));

        buttonTornaLogin.addActionListener(e -> {
            this.dispose();
            new Home(this.controller);
        });

        buttonInviaSegnalazione.addActionListener(e -> inviaSegnalazione());
        buttonLeggiSegnalazioni.addActionListener(e -> leggiSegnalazioni());

        buttonInserisciFilm.addActionListener(e -> {
            DashboardGestioneFilm finestraInserimento = new DashboardGestioneFilm(controller);
            finestraInserimento.setVisible(true);
        });

        btnModificaCatalogoFilm.addActionListener(e -> {
            DashboardModificaCatalogo finestraModifica = new DashboardModificaCatalogo(controller);
            finestraModifica.setVisible(true);
        });

        buttonConvalidaBiglietti.addActionListener(e -> {
            String codiceInserito = JOptionPane.showInputDialog(this, "Inserisci il CODICE UNIVOCO a 8 cifre del biglietto:", "Controllo Accessi", JOptionPane.QUESTION_MESSAGE);

            if (codiceInserito != null && !codiceInserito.trim().isEmpty()) {
                try {
                    Biglietto b = controller.convalidaBigliettoPerCodice(codiceInserito.trim());

                    String msg = "✅ INGRESSO AUTORIZZATO!\n\n" +
                            "🎬 Film: " + b.getProiezione().getFilm().getTitolo() + "\n" +
                            "💺 Posto: Fila " + b.getPostoAssegnato().getFila() + " - Numero " + b.getPostoAssegnato().getNumeroPosto() + "\n" +
                            "🎫 Codice: " + b.getCodiceUnivoco();

                    JOptionPane.showMessageDialog(this, msg, "Convalida Riuscita", JOptionPane.INFORMATION_MESSAGE);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "❌ Errore: " + ex.getMessage(), "Accesso Negato", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonGestioneSale.addActionListener(e -> {
            new DashboardGestioneSale(controller);
        });

        buttonModifcaCredenziali.addActionListener(e -> {
            this.dispose();
            new DashboardModificaCredenziali(this.controller, this.dipendenteLoggato);
        });

        setVisible(true);
    }

    private void sistemaGrafica() {
        Color sfondo = new Color(18, 22, 40);
        Color pannello = new Color(28, 34, 58);
        Color testo = Color.WHITE;
        Color blu = new Color(54, 112, 233);
        Color viola = new Color(126, 87, 194);
        Color grigioScuro = new Color(50, 58, 89);
        Color rossoMuto = new Color(176, 58, 75);

        mainPanel.setBackground(sfondo);
        mainPanel.removeAll();
        mainPanel.setLayout(new GridBagLayout());

        JPanel pannelloCentrale = new JPanel();
        pannelloCentrale.setBackground(pannello);
        pannelloCentrale.setLayout(new GridBagLayout());
        pannelloCentrale.setBorder(new EmptyBorder(30, 40, 30, 40));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;


        labelBenvenuto.setForeground(testo);
        labelBenvenuto.setHorizontalAlignment(SwingConstants.CENTER);


        JButton[] bottoniBlu = {buttonConvalidaBiglietti, buttonGestioneSale, buttonInviaSegnalazione, buttonLeggiSegnalazioni};
        for (JButton b : bottoniBlu) {
            b.setBackground(blu);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setPreferredSize(new Dimension(240, 45));
        }

        JButton[] bottoniViola = {buttonInserisciFilm, btnModificaCatalogoFilm};
        for (JButton b : bottoniViola) {
            b.setBackground(viola);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setPreferredSize(new Dimension(240, 45));
        }

        buttonModifcaCredenziali.setBackground(grigioScuro);
        buttonModifcaCredenziali.setForeground(Color.WHITE);
        buttonModifcaCredenziali.setFocusPainted(false);
        buttonModifcaCredenziali.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonModifcaCredenziali.setPreferredSize(new Dimension(490, 42));


        buttonTornaLogin.setBackground(grigioScuro);
        buttonTornaLogin.setForeground(Color.WHITE);
        buttonTornaLogin.setFocusPainted(false);
        buttonTornaLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonTornaLogin.setPreferredSize(new Dimension(160, 38));

        buttonEsci.setBackground(rossoMuto);
        buttonEsci.setForeground(Color.WHITE);
        buttonEsci.setFocusPainted(false);
        buttonEsci.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonEsci.setPreferredSize(new Dimension(160, 38));

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(5, 8, 22, 8);
        pannelloCentrale.add(labelBenvenuto, c);

        c.insets = new Insets(8, 8, 8, 8);
        c.gridwidth = 1;


        c.gridy = 1;
        c.gridx = 0; pannelloCentrale.add(buttonConvalidaBiglietti, c);
        c.gridx = 1; pannelloCentrale.add(buttonGestioneSale, c);


        c.gridy = 2;
        c.gridx = 0; pannelloCentrale.add(buttonInserisciFilm, c);
        c.gridx = 1; pannelloCentrale.add(btnModificaCatalogoFilm, c);


        c.gridy = 3;
        c.gridx = 0; pannelloCentrale.add(buttonInviaSegnalazione, c);
        c.gridx = 1; pannelloCentrale.add(buttonLeggiSegnalazioni, c);

        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 2;
        c.insets = new Insets(15, 8, 15, 8);
        pannelloCentrale.add(buttonModifcaCredenziali, c);

        JPanel pannelloFooter = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pannelloFooter.setBackground(pannello);
        pannelloFooter.add(buttonTornaLogin);
        pannelloFooter.add(buttonEsci);

        c.gridy = 5;
        c.gridx = 0;
        c.gridwidth = 2;
        c.insets = new Insets(10, 8, 5, 8);
        pannelloCentrale.add(pannelloFooter, c);

        GridBagConstraints outer = new GridBagConstraints();
        outer.gridx = 0;
        outer.gridy = 0;
        outer.weightx = 1.0;
        outer.weighty = 1.0;
        outer.anchor = GridBagConstraints.CENTER;
        mainPanel.add(pannelloCentrale, outer);

        mainPanel.revalidate();
        mainPanel.repaint();
    }


    private void inviaSegnalazione() {
        String[] opzioniSale = new String[13];
        opzioniSale[0] = "Generale (Nessuna sala specifica)";
        for (int i = 1; i <= 12; i++) opzioniSale[i] = "Sala " + i;

        String salaScelta = (String) JOptionPane.showInputDialog(this,
                "A cosa si riferisce la segnalazione?",
                "Selezione Ambiente",
                JOptionPane.QUESTION_MESSAGE, null, opzioniSale, opzioniSale[0]);

        if (salaScelta == null) return;

        String messaggio = JOptionPane.showInputDialog(this, "Inserisci il dettaglio del problema:", "Invia Segnalazione", JOptionPane.PLAIN_MESSAGE);
        if (messaggio == null || messaggio.trim().isEmpty()) return;

        if (salaScelta.equals("Generale (Nessuna sala specifica)")) {
            controller.aggiungiSegnalazione(messaggio.trim(),dipendenteLoggato);
        } else {
            controller.segnalaSalaGuasta(dipendenteLoggato, salaScelta, messaggio.trim());
        }

        JOptionPane.showMessageDialog(this, "Segnalazione registrata con successo!", "Successo", JOptionPane.INFORMATION_MESSAGE);
    }

    private void leggiSegnalazioni() {
        ArrayList<String> segnalazioni = controller.getSegnalazioni();
        if (segnalazioni == null || segnalazioni.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Non sono presenti segnalazioni nel sistema.", "Segnalazioni", JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        StringBuilder sb = new StringBuilder();
        for (String s : segnalazioni) {
            sb.append(s).append("\n--------------------------------------------------\n");
        }
        JTextArea textArea = new JTextArea(15, 45);
        textArea.setText(sb.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        JScrollPane scrollPane = new JScrollPane(textArea);
        JOptionPane.showMessageDialog(this, scrollPane, "Registro Segnalazioni", JOptionPane.PLAIN_MESSAGE);
    }
}