package gui;

import controller.Controller;
import model.Dipendente;
import model.Biglietto;

import javax.swing.*;
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

    private Controller controller;
    private Dipendente dipendenteLoggato;

    public DashboardDipendente(Controller controller, Dipendente dipendente) {
        this.controller = controller;
        this.dipendenteLoggato = dipendente;

        setContentPane(mainPanel);
        setTitle("Pannello Staff - " + dipendente.getRuolo().toUpperCase());
        setSize(550, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        labelBenvenuto.setText("Benvenuto, " + dipendente.getNome() + " " + dipendente.getCognome() + " (" + dipendente.getRuolo() + ")");

        // Logica per i ruoli
        String ruolo = dipendente.getRuolo().toLowerCase();
        if (ruolo.equals("addetto alle pulizie")) {
            buttonConvalidaBiglietti.setEnabled(false);
            buttonInserisciFilm.setEnabled(false);
            buttonGestioneSale.setEnabled(false);
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

        buttonInserisciFilm.addActionListener(e -> new DashboardGestioneFilm(controller));
        btnModificaCatalogoFilm.addActionListener(e -> new DashboardModificaCatalogo(controller));

        // NUOVA LOGICA CONVALIDA TRAMITE CODICE
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
            JOptionPane.showMessageDialog(this, "Modulo Gestione Sale in costruzione.", "Info", JOptionPane.INFORMATION_MESSAGE);
        });

        setVisible(true);
    }

    private void inviaSegnalazione() {
        String messaggio = JOptionPane.showInputDialog(this, "Inserisci il testo della segnalazione da inviare:", "Invia Segnalazione", JOptionPane.PLAIN_MESSAGE);
        if (messaggio == null || messaggio.trim().isEmpty()) return;
        controller.aggiungiSegnalazione(dipendenteLoggato, messaggio.trim());
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