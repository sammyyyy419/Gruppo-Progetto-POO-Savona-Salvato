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

/**
 * The type Dashboard prenotazione.
 */
public class DashboardPrenotazione extends JFrame {

    private JPanel mainPanel;
    private JPanel topPanel;
    private JButton tornaAlCatalogoButton;
    private JLabel labelTitolo;
    private JLabel labelInfo;

    private JPanel midPanel;
    private JLabel labelData;
    private JLabel labelOrari;
    private JLabel labelQuantita;
    private JButton calendarioButton;
    private JComboBox<Proiezione> comboBox1;
    private JComboBox<Integer> comboBox2;

    private JPanel bottomPanel;
    private JLabel labelTotale;
    private JButton aggiungiAlCarrelloButton;

    private Controller controller;
    private Cliente clienteLoggato;
    private Film filmSelezionato;
    private double prezzoSingoloBiglietto = 8.00;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private ArrayList<Proiezione> proiezioniDelFilm;

    /**
     * Instantiates a new Dashboard prenotazione.
     *
     * @param controller the controller
     * @param cliente    the cliente
     * @param film       the film
     */
    public DashboardPrenotazione(Controller controller, Cliente cliente, Film film) {
        this.controller = controller;
        this.clienteLoggato = cliente;
        this.filmSelezionato = film;

        sistemaGrafica();

        setContentPane(mainPanel);
        setTitle("Prenotazione Biglietti - Enterprise Cinema");
        setSize(660, 520);
        setMinimumSize(new Dimension(600, 460));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        labelTitolo.setText(filmSelezionato.getTitolo().toUpperCase());
        labelInfo.setText("Genere: " + filmSelezionato.getGenere() + " | Durata: " + filmSelezionato.getDurataMinuti() + " min | Ubicazione: " + filmSelezionato.getSalaAssegnata());

        for (int i = 1; i <= 10; i++) {
            comboBox2.addItem(i);
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

        tornaAlCatalogoButton.addActionListener(e -> {
            this.dispose();
        });

        calendarioButton.addActionListener(e -> apriMiniCalendario(giorniValidi));

        comboBox1.addActionListener(e -> {
            if (comboBox1.getItemCount() == 0) return;
            Proiezione proiezioneSelezionata = (Proiezione) comboBox1.getSelectedItem();

            if (proiezioneSelezionata != null) {

                if ("IMAX".equalsIgnoreCase(proiezioneSelezionata.getSala().getTipoSala())) {
                    prezzoSingoloBiglietto = 12.00;
                } else {
                    prezzoSingoloBiglietto = 8.00;
                }

                Sala sala = proiezioneSelezionata.getSala();
                int postiLiberi = sala.controllareCapienzaPostiResidua(proiezioneSelezionata);

                if (postiLiberi < 1) {
                    JOptionPane.showMessageDialog(this, "Siamo spiacenti! Non ci sono più biglietti disponibili per la data/ora scelti", "Posti Esauriti", JOptionPane.WARNING_MESSAGE);
                    aggiungiAlCarrelloButton.setEnabled(false);
                    return;
                }

                Integer quantita = (Integer) comboBox2.getSelectedItem();
                if (quantita != null && quantita > postiLiberi) {
                    comboBox2.setSelectedItem(postiLiberi);
                    quantita = postiLiberi;
                }

                double totale = quantita * prezzoSingoloBiglietto;
                labelTotale.setText("Tot. : " + String.format("%.2f", totale) + " €");
                aggiungiAlCarrelloButton.setEnabled(true);
            } else {
                aggiungiAlCarrelloButton.setEnabled(false);
            }
        });

        comboBox2.addActionListener(e -> {
            if (comboBox1.getItemCount() == 0) return;
            Proiezione proiezioneSelezionata = (Proiezione) comboBox1.getSelectedItem();
            Integer quantita = (Integer) comboBox2.getSelectedItem();

            if (proiezioneSelezionata != null && quantita != null) {
                Sala sala = proiezioneSelezionata.getSala();
                int postiLiberi = sala.controllareCapienzaPostiResidua(proiezioneSelezionata);

                if (quantita > postiLiberi) {
                    JOptionPane.showMessageDialog(this, "Attenzione! Per questa proiezione sono disponibili solo " + postiLiberi + " biglietti.", "Disponibilità Superata", JOptionPane.WARNING_MESSAGE);
                    comboBox2.setSelectedItem(postiLiberi);
                    return;
                }
                double totale = quantita * prezzoSingoloBiglietto;
                labelTotale.setText("Tot. : " + String.format("%.2f", totale) + " €");
                aggiungiAlCarrelloButton.setEnabled(true);
            }
        });

        aggiungiAlCarrelloButton.addActionListener(e -> {
            Proiezione proiezioneSelezionata = (Proiezione) comboBox1.getSelectedItem();
            Integer quantita = (Integer) comboBox2.getSelectedItem();

            if (proiezioneSelezionata != null && quantita != null) {
                double totale = quantita * prezzoSingoloBiglietto;
                controller.aggiungiAlCarrello(proiezioneSelezionata, quantita, totale);
                JOptionPane.showMessageDialog(this, "I biglietti sono stati aggiunti con successo al tuo carrello!", "Carrello Aggiornato", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();
            }
        });

        setVisible(true);
    }

    private void sistemaGrafica() {

        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color sfondoCard = new Color(38, 46, 78);
        Color testoChiaro = Color.WHITE;
        Color bluAcceso = new Color(54, 112, 233);
        Color grigioScuro = new Color(50, 58, 89);

        mainPanel.setBackground(sfondoScuro);

        topPanel.setBackground(sfondoScuro);
        topPanel.setBorder(new EmptyBorder(15, 20, 10, 20));

        tornaAlCatalogoButton.setBackground(grigioScuro);
        tornaAlCatalogoButton.setForeground(testoChiaro);
        tornaAlCatalogoButton.setFocusable(false);
        tornaAlCatalogoButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tornaAlCatalogoButton.setPreferredSize(new Dimension(160, 32));

        labelTitolo.setFont(new Font("SansSerif", Font.BOLD, 20));
        labelTitolo.setForeground(testoChiaro);
        labelTitolo.setHorizontalAlignment(SwingConstants.CENTER);

        labelInfo.setFont(new Font("SansSerif", Font.ITALIC, 13));
        labelInfo.setForeground(new Color(200, 210, 230));
        labelInfo.setHorizontalAlignment(SwingConstants.CENTER);

        midPanel.setBackground(sfondoPannello);
        midPanel.setBorder(new EmptyBorder(30, 40, 30, 40));

        calendarioButton.setBackground(sfondoCard);
        calendarioButton.setForeground(testoChiaro);
        calendarioButton.setFocusPainted(false);
        calendarioButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        calendarioButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        comboBox1.setBackground(sfondoCard);
        comboBox1.setForeground(testoChiaro);
        comboBox1.setFont(new Font("SansSerif", Font.PLAIN, 13));

        comboBox2.setBackground(sfondoCard);
        comboBox2.setForeground(testoChiaro);
        comboBox2.setFont(new Font("SansSerif", Font.PLAIN, 13));

        labelData.setForeground(testoChiaro);
        labelData.setFont(new Font("SansSerif", Font.PLAIN, 13));

        labelOrari.setForeground(testoChiaro);
        labelOrari.setFont(new Font("SansSerif", Font.PLAIN, 13));

        labelQuantita.setForeground(testoChiaro);
        labelQuantita.setFont(new Font("SansSerif", Font.PLAIN, 13));

        bottomPanel.setBackground(sfondoScuro);
        bottomPanel.setBorder(new EmptyBorder(12, 20, 15, 20));

        labelTotale.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelTotale.setForeground(testoChiaro);
        labelTotale.setText("Tot. : 0,00 €"); // Valore di default

        aggiungiAlCarrelloButton.setBackground(bluAcceso);
        aggiungiAlCarrelloButton.setForeground(testoChiaro);
        aggiungiAlCarrelloButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        aggiungiAlCarrelloButton.setFocusPainted(false);
        aggiungiAlCarrelloButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        aggiungiAlCarrelloButton.setPreferredSize(new Dimension(190, 40));
        aggiungiAlCarrelloButton.setEnabled(false); // Disabilitato all'inizio
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
                calendarioButton.setText(giorno);
                calendarioButton.setBackground(new Color(46, 204, 113));
                calendarioButton.setForeground(Color.WHITE);

                comboBox1.removeAllItems();
                aggiungiAlCarrelloButton.setEnabled(false);

                for (Proiezione p : proiezioniDelFilm) {
                    if (p.getDataOraInizio() != null && p.getDataOraInizio().toLocalDate().format(dateFormatter).equals(giorno)) {
                        comboBox1.addItem(p);
                    }
                }
                dialogCalendario.dispose();
            });
            dialogCalendario.add(btnGiorno);
        }
        dialogCalendario.setVisible(true);
    }
}