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
    private JButton buttonModifcaCredenziali; // Il tuo nuovo bottone

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
            buttonGestioneSale.setEnabled(true); // L'addetto pulizie ORA PUO' accedere alle sale
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

        // LOGICA CONVALIDA TRAMITE CODICE
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

        // APERTURA MAPPA SALE
        buttonGestioneSale.addActionListener(e -> {
            new DashboardGestioneSale(controller);
        });

        // =========================================================
        // NUOVA LOGICA: APERTURA SCHERMATA MODIFICA CREDENZIALI
        // =========================================================
        buttonModifcaCredenziali.addActionListener(e -> {
            this.dispose(); // Chiude la dashboard attuale
            new ModificaCredenziali(this.controller, this.dipendenteLoggato); // Apre la finestra di modifica
        });

        setVisible(true);
    }

    // METODO SOSTITUITO PER LA TENDINA DELLE SALE
    private void inviaSegnalazione() {
        String[] opzioniSale = new String[13];
        opzioniSale[0] = "Generale (Nessuna sala specifica)";
        for (int i = 1; i <= 12; i++) opzioniSale[i] = "Sala " + i;

        String salaScelta = (String) JOptionPane.showInputDialog(this,
                "A cosa si riferisce la segnalazione?",
                "Selezione Ambiente",
                JOptionPane.QUESTION_MESSAGE, null, opzioniSale, opzioniSale[0]);

        if (salaScelta == null) return; // L'utente ha cliccato su "Annulla"

        String messaggio = JOptionPane.showInputDialog(this, "Inserisci il dettaglio del problema:", "Invia Segnalazione", JOptionPane.PLAIN_MESSAGE);
        if (messaggio == null || messaggio.trim().isEmpty()) return;

        if (salaScelta.equals("Generale (Nessuna sala specifica)")) {
            controller.aggiungiSegnalazione(dipendenteLoggato, messaggio.trim());
        } else {
            // Segnalazione legata a una sala precisa!
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