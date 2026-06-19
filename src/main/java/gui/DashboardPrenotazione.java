package gui;

import controller.Controller;
import model.Cliente;
import model.Film;
import model.Proiezione;
import model.Sala;
import javax.swing.*;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class DashboardPrenotazione extends JFrame {
    // Componenti GUI
    private JPanel panelPrenotazione;
    private JLabel labelTitoloFilm;
    private JLabel labelDettagliFilm;
    private JComboBox<String> comboGiorni;
    private JComboBox<Proiezione> comboOrari;
    private JComboBox<Integer> comboQuantita;
    private JLabel labelTotPagamento;
    private JButton pulsantePagamento;

    private Controller controller;
    private Cliente clienteLoggato;
    private Film filmSelezionato;
    private double prezzoSingoloBiglietto = 8.00;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private boolean inInizializzazione = true;

    public DashboardPrenotazione(Controller controller, Cliente cliente, Film film) {
        this.controller = controller;
        this.clienteLoggato = cliente;
        this.filmSelezionato = film;

        // 1. Costruisce l'interfaccia grafica (bypassando il file .form e i suoi errori)
        inizializzaInterfaccia();

        setContentPane(panelPrenotazione);
        setTitle("Prenotazione Biglietti - Enterprise Cinema");
        setSize(550, 450);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Chiude solo questa finestra
        setLocationRelativeTo(null);

        // 2. Inserimento dati iniziali
        labelTitoloFilm.setText(filmSelezionato.getTitolo().toUpperCase());
        labelDettagliFilm.setText("Genere: " + filmSelezionato.getGenere() + "    Durata: " + filmSelezionato.getDurata() + " min");

        for (int i = 1; i <= 10; i++) {
            comboQuantita.addItem(i);
        }

        ArrayList<Proiezione> proiezioni = controller.getProiezioniPerFilm(filmSelezionato);
        LinkedHashSet<String> giorni = new LinkedHashSet<>();

        for (Proiezione p : proiezioni) {
            if (p.getDataOraInizio() != null) {
                giorni.add(p.getDataOraInizio().toLocalDate().format(dateFormatter));
            }
        }
        for (String giorno : giorni) {
            comboGiorni.addItem(giorno);
        }

        // =================================================================
        // LISTENER DEI COMPONENTI
        // =================================================================

        comboGiorni.addActionListener(e -> {
            if (inInizializzazione) return;

            comboOrari.removeAllItems();
            pulsantePagamento.setEnabled(false);
            String giornoSelezionato = (String) comboGiorni.getSelectedItem();
            if (giornoSelezionato != null) {
                for (Proiezione p : proiezioni) {
                    if (p.getDataOraInizio() != null && p.getDataOraInizio().toLocalDate().format(dateFormatter).equals(giornoSelezionato)) {
                        comboOrari.addItem(p);
                    }
                }
            }
        });

        comboOrari.addActionListener(e -> {
            if (comboOrari.getItemCount() == 0) return;
            Proiezione proiezioneSelezionata = (Proiezione) comboOrari.getSelectedItem();

            if (proiezioneSelezionata != null) {
                Sala sala = proiezioneSelezionata.getSala();
                int postiLiberi = sala.controllareCapienzaPostiResidua(proiezioneSelezionata);

                if (postiLiberi < 1) {
                    JOptionPane.showMessageDialog(this, "Siamo spiacenti! Non ci sono più biglietti disponibili per la data/ora scelti", "Posti Esauriti", JOptionPane.WARNING_MESSAGE);
                    pulsantePagamento.setEnabled(false);
                    this.dispose();
                    return;
                }
                Integer quantita = (Integer) comboQuantita.getSelectedItem();
                if (quantita != null && quantita > postiLiberi) {
                    comboQuantita.setSelectedItem(postiLiberi);
                    quantita = postiLiberi;
                }

                double totale = quantita * prezzoSingoloBiglietto;
                labelTotPagamento.setText("Tot. : " + String.format("%.2f", totale) + " €");
                pulsantePagamento.setEnabled(true);
            } else {
                pulsantePagamento.setEnabled(false);
            }
        });

        comboQuantita.addActionListener(e -> {
            if (comboOrari.getItemCount() == 0) return;
            Proiezione proiezioneSelezionata = (Proiezione) comboOrari.getSelectedItem();
            Integer quantita = (Integer) comboQuantita.getSelectedItem();

            if (proiezioneSelezionata != null && quantita != null) {
                Sala sala = proiezioneSelezionata.getSala();
                int postiLiberi = sala.controllareCapienzaPostiResidua(proiezioneSelezionata);

                if (quantita > postiLiberi) {
                    JOptionPane.showMessageDialog(this, "Attenzione! Per questa proiezione sono disponibili solo " + postiLiberi + " biglietti.", "Disponibilità Superata", JOptionPane.WARNING_MESSAGE);
                    comboQuantita.setSelectedItem(postiLiberi);
                    return;
                }
                double totale = quantita * prezzoSingoloBiglietto;
                labelTotPagamento.setText("Tot. : " + String.format("%.2f", totale) + " €");
                pulsantePagamento.setEnabled(true);
            }
        });

        pulsantePagamento.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Reindirizzamento al modulo di pagamento in corso...");
            // TODO: Implementare collegamento col pagamento
        });

        // 3. Setup finale pre-visualizzazione
        inInizializzazione = false;
        if (comboGiorni.getItemCount() > 0) {
            comboGiorni.setSelectedIndex(0);
            if (comboOrari.getItemCount() > 0) {
                comboOrari.setSelectedIndex(0);
            }
        }

        setVisible(true);
    }

    /**
     * Metodo per generare la grafica senza dipendere dal file .form di IntelliJ.
     */
    private void inizializzaInterfaccia() {
        panelPrenotazione = new JPanel(new BorderLayout(15, 15));
        panelPrenotazione.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Pannello Superiore: Titolo e Info Film
        JPanel topPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        labelTitoloFilm = new JLabel("", SwingConstants.CENTER);
        labelTitoloFilm.setFont(new Font("Arial", Font.BOLD, 22));
        labelTitoloFilm.setForeground(new Color(30, 60, 100)); // Colore blu scuro
        labelDettagliFilm = new JLabel("", SwingConstants.CENTER);
        labelDettagliFilm.setFont(new Font("Arial", Font.ITALIC, 14));
        topPanel.add(labelTitoloFilm);
        topPanel.add(labelDettagliFilm);
        panelPrenotazione.add(topPanel, BorderLayout.NORTH);

        // Pannello Centrale: Griglia per le combobox
        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 20));
        comboGiorni = new JComboBox<>();
        comboOrari = new JComboBox<>();
        comboQuantita = new JComboBox<>();

        centerPanel.add(new JLabel("Seleziona Giorno:", SwingConstants.RIGHT));
        centerPanel.add(comboGiorni);
        centerPanel.add(new JLabel("Seleziona Orario:", SwingConstants.RIGHT));
        centerPanel.add(comboOrari);
        centerPanel.add(new JLabel("Quantità Biglietti:", SwingConstants.RIGHT));
        centerPanel.add(comboQuantita);
        panelPrenotazione.add(centerPanel, BorderLayout.CENTER);

        // Pannello Inferiore: Riepilogo prezzo e pulsante pagamento
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        labelTotPagamento = new JLabel("Tot. : 0,00 €");
        labelTotPagamento.setFont(new Font("Arial", Font.BOLD, 18));
        pulsantePagamento = new JButton("Procedi al Pagamento");
        pulsantePagamento.setBackground(new Color(46, 204, 113)); // Verde scuro
        pulsantePagamento.setForeground(Color.WHITE);
        pulsantePagamento.setFont(new Font("Arial", Font.BOLD, 14));
        pulsantePagamento.setEnabled(false); // Disabilitato finché non si sceglie tutto

        bottomPanel.add(labelTotPagamento);
        bottomPanel.add(pulsantePagamento);
        panelPrenotazione.add(bottomPanel, BorderLayout.SOUTH);
    }
}