package gui;

import controller.Controller;
import model.Cliente;
import model.Film;
import model.Proiezione;
import model.Sala;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashSet;

public class DashboardPrenotazione extends JFrame {
    private JPanel panelPrenotazione;
    private JLabel labelTitoloFilm;
    private JLabel labelDettagliFilm;
    private JButton btnScegliData;
    private JComboBox<Proiezione> comboOrari;
    private JComboBox<Integer> comboQuantita;
    private JLabel labelTotPagamento;
    private JButton pulsanteAggiungiCarrello;
    private JButton pulsanteTorna;

    private Controller controller;
    private Cliente clienteLoggato;
    private Film filmSelezionato;
    private double prezzoSingoloBiglietto = 8.00;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private ArrayList<Proiezione> proiezioniDelFilm; // Salvo qui le proiezioni per filtrarle

    public DashboardPrenotazione(Controller controller, Cliente cliente, Film film) {
        this.controller = controller;
        this.clienteLoggato = cliente;
        this.filmSelezionato = film;

        inizializzaInterfaccia();

        setContentPane(panelPrenotazione);
        setTitle("Prenotazione Biglietti - Enterprise Cinema");
        setSize(600, 480);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        labelTitoloFilm.setText(filmSelezionato.getTitolo().toUpperCase());
        // AGGIUNTA SALA NEI DETTAGLI
        labelDettagliFilm.setText("Genere: " + filmSelezionato.getGenere() + " | Durata: " + filmSelezionato.getDurataMinuti() + " min | Ubicazione: " + filmSelezionato.getSalaAssegnata());

        for (int i = 1; i <= 10; i++) {
            comboQuantita.addItem(i);
        }

        // --- INIZIO LOGICA FINESTRA SCORREVOLE 45 GIORNI ---
        LocalDate oggi = LocalDate.now();
        LocalDate fineProgrammazione = filmSelezionato.getDataInizioProgrammazione().plusDays(45);

        // Controllo di sicurezza se i 45 giorni complessivi sono già passati
        if (oggi.isAfter(fineProgrammazione)) {
            JOptionPane.showMessageDialog(this, "Spiacenti, la programmazione di 45 giorni per questo film è terminata.", "Film Scaduto", JOptionPane.WARNING_MESSAGE);
            this.dispose();
            return;
        }

        proiezioniDelFilm = controller.getProiezioniPerFilm(filmSelezionato);
        LinkedHashSet<String> giorniValidi = new LinkedHashSet<>();

        for (Proiezione p : proiezioniDelFilm) {
            if (p.getDataOraInizio() != null) {
                LocalDate dataProiezione = p.getDataOraInizio().toLocalDate();

                // La proiezione deve avvenire da OGGI in poi ed entro i 45 giorni dall'inizio della programmazione
                if ((dataProiezione.isEqual(oggi) || dataProiezione.isAfter(oggi)) &&
                        (dataProiezione.isEqual(fineProgrammazione) || dataProiezione.isBefore(fineProgrammazione))) {

                    giorniValidi.add(dataProiezione.format(dateFormatter));
                }
            }
        }

        pulsanteTorna.addActionListener(e -> {
            this.dispose();
        });

        // AZIONE CALENDARIO
        btnScegliData.addActionListener(e -> apriMiniCalendario(giorniValidi));

        comboOrari.addActionListener(e -> {
            if (comboOrari.getItemCount() == 0) return;
            Proiezione proiezioneSelezionata = (Proiezione) comboOrari.getSelectedItem();

            if (proiezioneSelezionata != null) {
                Sala sala = proiezioneSelezionata.getSala();
                int postiLiberi = sala.controllareCapienzaPostiResidua(proiezioneSelezionata);

                if (postiLiberi < 1) {
                    JOptionPane.showMessageDialog(this, "Siamo spiacenti! Non ci sono più biglietti disponibili per la data/ora scelti", "Posti Esauriti", JOptionPane.WARNING_MESSAGE);
                    pulsanteAggiungiCarrello.setEnabled(false);
                    return;
                }
                Integer quantita = (Integer) comboQuantita.getSelectedItem();
                if (quantita != null && quantita > postiLiberi) {
                    comboQuantita.setSelectedItem(postiLiberi);
                    quantita = postiLiberi;
                }

                double totale = quantita * prezzoSingoloBiglietto;
                labelTotPagamento.setText("Tot. : " + String.format("%.2f", totale) + " €");
                pulsanteAggiungiCarrello.setEnabled(true);
            } else {
                pulsanteAggiungiCarrello.setEnabled(false);
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
                pulsanteAggiungiCarrello.setEnabled(true);
            }
        });

        pulsanteAggiungiCarrello.addActionListener(e -> {
            Proiezione proiezioneSelezionata = (Proiezione) comboOrari.getSelectedItem();
            Integer quantita = (Integer) comboQuantita.getSelectedItem();

            if (proiezioneSelezionata != null && quantita != null) {
                double totale = quantita * prezzoSingoloBiglietto;
                controller.aggiungiAlCarrello(proiezioneSelezionata, quantita, totale);
                JOptionPane.showMessageDialog(this, "I biglietti sono stati aggiunti con successo al tuo carrello!", "Carrello Aggiornato", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
        });

        setVisible(true);
    }

    // Costruisce una griglia di giorni cliccabili
    private void apriMiniCalendario(LinkedHashSet<String> giorniValidi) {
        JDialog dialogCalendario = new JDialog(this, "Seleziona Giorno", true);
        dialogCalendario.setSize(450, 300);
        dialogCalendario.setLocationRelativeTo(this);
        dialogCalendario.setLayout(new GridLayout(0, 5, 5, 5));

        for (String giorno : giorniValidi) {
            JButton btnGiorno = new JButton(giorno.substring(0, 5)); // Mostra es "22/06"
            btnGiorno.setFont(new Font("Arial", Font.BOLD, 14));
            btnGiorno.setCursor(new Cursor(Cursor.HAND_CURSOR));

            btnGiorno.addActionListener(ev -> {
                btnScegliData.setText(giorno);
                btnScegliData.setBackground(new Color(46, 204, 113));
                btnScegliData.setForeground(Color.WHITE);

                comboOrari.removeAllItems();
                pulsanteAggiungiCarrello.setEnabled(false);
                for (Proiezione p : proiezioniDelFilm) {
                    if (p.getDataOraInizio() != null && p.getDataOraInizio().toLocalDate().format(dateFormatter).equals(giorno)) {
                        comboOrari.addItem(p);
                    }
                }
                dialogCalendario.dispose();
            });
            dialogCalendario.add(btnGiorno);
        }
        dialogCalendario.setVisible(true);
    }

    private void inizializzaInterfaccia() {
        panelPrenotazione = new JPanel(new BorderLayout(15, 15));
        panelPrenotazione.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JPanel topPanel = new JPanel(new BorderLayout(15, 5));
        pulsanteTorna = new JButton("← Torna al Catalogo");
        pulsanteTorna.setFocusable(false);

        // IL TUO WRAPPER E SPACER SONO SALVI :)
        JPanel wrapperBottone = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 10));
        wrapperBottone.add(pulsanteTorna);

        JPanel infoFilmPanel = new JPanel(new GridLayout(2, 1, 5, 5));
        labelTitoloFilm = new JLabel("", SwingConstants.CENTER);
        labelTitoloFilm.setFont(new Font("Arial", Font.BOLD, 22));
        labelTitoloFilm.setForeground(new Color(30, 60, 100));
        labelDettagliFilm = new JLabel("", SwingConstants.CENTER);
        labelDettagliFilm.setFont(new Font("Arial", Font.ITALIC, 14));
        infoFilmPanel.add(labelTitoloFilm);
        infoFilmPanel.add(labelDettagliFilm);

        JLabel spacerDestro = new JLabel("");
        spacerDestro.setPreferredSize(new Dimension(145, 10));

        topPanel.add(wrapperBottone, BorderLayout.WEST);
        topPanel.add(infoFilmPanel, BorderLayout.CENTER);
        topPanel.add(spacerDestro, BorderLayout.EAST);
        panelPrenotazione.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 10, 25));

        btnScegliData = new JButton("Scegli Data dal Calendario 📅");
        btnScegliData.setCursor(new Cursor(Cursor.HAND_CURSOR));
        comboOrari = new JComboBox<>();
        comboQuantita = new JComboBox<>();

        centerPanel.add(new JLabel("Data Spettacolo:", SwingConstants.RIGHT));
        centerPanel.add(btnScegliData);
        centerPanel.add(new JLabel("Seleziona Orario:", SwingConstants.RIGHT));
        centerPanel.add(comboOrari);
        centerPanel.add(new JLabel("Quantità Biglietti:", SwingConstants.RIGHT));
        centerPanel.add(comboQuantita);
        panelPrenotazione.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 10));
        labelTotPagamento = new JLabel("Tot. : 0,00 €");
        labelTotPagamento.setFont(new Font("Arial", Font.BOLD, 18));

        pulsanteAggiungiCarrello = new JButton("Aggiungi al Carrello 🛒");
        pulsanteAggiungiCarrello.setBackground(new Color(41, 128, 185));
        pulsanteAggiungiCarrello.setForeground(Color.WHITE);
        pulsanteAggiungiCarrello.setFont(new Font("Arial", Font.BOLD, 14));
        pulsanteAggiungiCarrello.setEnabled(false);

        bottomPanel.add(labelTotPagamento);
        bottomPanel.add(pulsanteAggiungiCarrello);
        panelPrenotazione.add(bottomPanel, BorderLayout.SOUTH);
    }
}