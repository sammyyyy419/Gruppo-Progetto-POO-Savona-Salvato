package gui;

import controller.Controller;
import model.Cliente;
import model.Film;
import model.Proiezione;
import model.Sala;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
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

    private ArrayList<Proiezione> proiezioniDelFilm;

    public DashboardPrenotazione(Controller controller, Cliente cliente, Film film) {
        this.controller = controller;
        this.clienteLoggato = cliente;
        this.filmSelezionato = film;

        inizializzaInterfaccia();

        setContentPane(panelPrenotazione);
        setTitle("Prenotazione Biglietti - Enterprise Cinema");
        setSize(660, 520);
        setMinimumSize(new Dimension(600, 460));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        labelTitoloFilm.setText(filmSelezionato.getTitolo().toUpperCase());
        labelDettagliFilm.setText("Genere: " + filmSelezionato.getGenere() + " | Durata: " + filmSelezionato.getDurataMinuti() + " min | Ubicazione: " + filmSelezionato.getSalaAssegnata());

        for (int i = 1; i <= 10; i++) {
            comboQuantita.addItem(i);
        }

        LocalDate oggi = LocalDate.now();
        LocalDate fineProgrammazione = filmSelezionato.getDataInizioProgrammazione().plusDays(45);

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

                if ((dataProiezione.isEqual(oggi) || dataProiezione.isAfter(oggi)) &&
                        (dataProiezione.isEqual(fineProgrammazione) || dataProiezione.isBefore(fineProgrammazione))) {

                    giorniValidi.add(dataProiezione.format(dateFormatter));
                }
            }
        }

        pulsanteTorna.addActionListener(e -> {
            this.dispose();
        });

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

    private void apriMiniCalendario(LinkedHashSet<String> giorniValidi) {
        Color sfondoPannello = new Color(28, 34, 58);
        Color sfondoCard = new Color(38, 46, 78);
        Color testoChiaro = Color.WHITE;

        JDialog dialogCalendario = new JDialog(this, "Seleziona Giorno", true);
        dialogCalendario.setSize(460, 320);
        dialogCalendario.setLocationRelativeTo(this);
        dialogCalendario.setLayout(new GridLayout(0, 5, 6, 6));
        dialogCalendario.getContentPane().setBackground(sfondoPannello);
        ((JPanel) dialogCalendario.getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));

        for (String giorno : giorniValidi) {
            JButton btnGiorno = new JButton(giorno.substring(0, 5));
            btnGiorno.setFont(new Font("SansSerif", Font.BOLD, 13));
            btnGiorno.setBackground(sfondoCard);
            btnGiorno.setForeground(testoChiaro);
            btnGiorno.setFocusPainted(false);
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
        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color sfondoCard = new Color(38, 46, 78);
        Color testoChiaro = Color.WHITE;
        Color bluAcceso = new Color(54, 112, 233);
        Color grigioScuro = new Color(50, 58, 89);

        panelPrenotazione = new JPanel(new BorderLayout());
        panelPrenotazione.setBackground(sfondoScuro);

        JPanel topPanel = new JPanel(new BorderLayout(15, 5));
        topPanel.setBackground(sfondoScuro);
        topPanel.setBorder(new EmptyBorder(15, 20, 10, 20));

        pulsanteTorna = new JButton("← Torna al Catalogo");
        pulsanteTorna.setBackground(grigioScuro);
        pulsanteTorna.setForeground(testoChiaro);
        pulsanteTorna.setFocusable(false);
        pulsanteTorna.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pulsanteTorna.setPreferredSize(new Dimension(160, 32));

        JPanel wrapperBottone = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 5));
        wrapperBottone.setOpaque(false);
        wrapperBottone.add(pulsanteTorna);

        JPanel infoFilmPanel = new JPanel(new GridLayout(2, 1, 5, 2));
        infoFilmPanel.setOpaque(false);

        labelTitoloFilm = new JLabel("", SwingConstants.CENTER);
        labelTitoloFilm.setFont(new Font("SansSerif", Font.BOLD, 20));
        labelTitoloFilm.setForeground(testoChiaro);

        labelDettagliFilm = new JLabel("", SwingConstants.CENTER);
        labelDettagliFilm.setFont(new Font("SansSerif", Font.ITALIC, 13));
        labelDettagliFilm.setForeground(new Color(200, 210, 230));

        infoFilmPanel.add(labelTitoloFilm);
        infoFilmPanel.add(labelDettagliFilm);

        JLabel spacerDestro = new JLabel("");
        spacerDestro.setPreferredSize(new Dimension(160, 10));

        topPanel.add(wrapperBottone, BorderLayout.WEST);
        topPanel.add(infoFilmPanel, BorderLayout.CENTER);
        topPanel.add(spacerDestro, BorderLayout.EAST);
        panelPrenotazione.add(topPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel(new GridLayout(3, 2, 15, 25));
        centerPanel.setBackground(sfondoPannello);
        centerPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        btnScegliData = new JButton("Scegli Data dal Calendario 📅");
        btnScegliData.setBackground(sfondoCard);
        btnScegliData.setForeground(testoChiaro);
        btnScegliData.setFocusPainted(false);
        btnScegliData.setFont(new Font("SansSerif", Font.BOLD, 13));
        btnScegliData.setCursor(new Cursor(Cursor.HAND_CURSOR));

        comboOrari = new JComboBox<>();
        comboOrari.setBackground(sfondoCard);
        comboOrari.setForeground(testoChiaro);
        comboOrari.setFont(new Font("SansSerif", Font.PLAIN, 13));

        comboQuantita = new JComboBox<>();
        comboQuantita.setBackground(sfondoCard);
        comboQuantita.setForeground(testoChiaro);
        comboQuantita.setFont(new Font("SansSerif", Font.PLAIN, 13));

        JLabel lblData = new JLabel("Data Spettacolo:", SwingConstants.RIGHT);
        JLabel lblOrario = new JLabel("Seleziona Orario:", SwingConstants.RIGHT);
        JLabel lblQuant = new JLabel("Quantità Biglietti:", SwingConstants.RIGHT);

        JLabel[] centerLabels = {lblData, lblOrario, lblQuant};
        for (JLabel l : centerLabels) {
            l.setForeground(testoChiaro);
            l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        }

        centerPanel.add(lblData);
        centerPanel.add(btnScegliData);
        centerPanel.add(lblOrario);
        centerPanel.add(comboOrari);
        centerPanel.add(lblQuant);
        centerPanel.add(comboQuantita);
        panelPrenotazione.add(centerPanel, BorderLayout.CENTER);

        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(sfondoScuro);
        bottomPanel.setBorder(new EmptyBorder(12, 20, 15, 20));

        labelTotPagamento = new JLabel("Tot. : 0,00 €");
        labelTotPagamento.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelTotPagamento.setForeground(testoChiaro);

        pulsanteAggiungiCarrello = new JButton("Aggiungi al Carrello 🛒");
        pulsanteAggiungiCarrello.setBackground(bluAcceso);
        pulsanteAggiungiCarrello.setForeground(testoChiaro);
        pulsanteAggiungiCarrello.setFont(new Font("SansSerif", Font.BOLD, 13));
        pulsanteAggiungiCarrello.setFocusPainted(false);
        pulsanteAggiungiCarrello.setCursor(new Cursor(Cursor.HAND_CURSOR));
        pulsanteAggiungiCarrello.setPreferredSize(new Dimension(190, 40));
        pulsanteAggiungiCarrello.setEnabled(false);

        bottomPanel.add(labelTotPagamento, BorderLayout.WEST);
        bottomPanel.add(pulsanteAggiungiCarrello, BorderLayout.EAST);
        panelPrenotazione.add(bottomPanel, BorderLayout.SOUTH);
    }
}