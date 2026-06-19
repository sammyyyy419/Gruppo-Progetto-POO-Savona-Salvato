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
    private JPanel panelPrenotazione;
    private JLabel labelTitoloFilm;
    private JPanel panelDettagliFilm;
    private JLabel labelDettagliFilm;
    private JPanel panelGiorno;
    private JLabel labelGiorno;
    private JComboBox<String> comboGiorni;
    private JPanel panelOrari;
    private JLabel labelOrari;
    private JComboBox<Proiezione> comboOrari;
    private JPanel panelBiglietti;
    private JLabel labelBiglietti;
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
        setContentPane(panelPrenotazione);
        setTitle("Prenotazione Biglietti");
        setSize(550, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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
            // da impelemntare collegamento col pagamento
        });
        inInizializzazione = false;
        if (comboGiorni.getItemCount() > 0) {
            comboGiorni.setSelectedIndex(0);
            if (comboOrari.getItemCount() > 0) {
                comboOrari.setSelectedIndex(0);
            }
        }

        setVisible(true);
    }
}
