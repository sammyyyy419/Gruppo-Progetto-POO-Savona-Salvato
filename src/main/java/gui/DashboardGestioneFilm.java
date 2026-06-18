package gui;

import controller.Controller;
import model.Film;

import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

public class DashboardGestioneFilm extends JFrame {
    private Controller controller;

    private JTextField textTitolo;
    private JTextField textDurata;
    private JTextField textGenere;
    private JComboBox<String> comboClassificazione;
    private JTextArea textTrama;
    private JButton buttonSalva;
    private JButton buttonAnnulla;

    public DashboardGestioneFilm(Controller controller) {
        this.controller = controller;

        setTitle("Gestione Film - Inserimento Nuovo Titolo");
        setSize(500, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelForm = new JPanel(new GridLayout(5, 2, 10, 10));
        panelForm.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));

        panelForm.add(new JLabel("Titolo del Film:"));
        textTitolo = new JTextField();
        panelForm.add(textTitolo);

        panelForm.add(new JLabel("Durata (Formato HH:MM):"));
        textDurata = new JTextField("02:00");
        panelForm.add(textDurata);

        panelForm.add(new JLabel("Genere:"));
        textGenere = new JTextField();
        panelForm.add(textGenere);

        panelForm.add(new JLabel("Classificazione Età:"));
        String[] opzioniEta = {"T (Per Tutti)", "14+", "16+", "18+"};
        comboClassificazione = new JComboBox<>(opzioniEta);
        panelForm.add(comboClassificazione);

        add(panelForm, BorderLayout.NORTH);

        JPanel panelTrama = new JPanel(new BorderLayout());
        panelTrama.setBorder(BorderFactory.createEmptyBorder(0, 20, 10, 20));
        panelTrama.add(new JLabel("Trama:"), BorderLayout.NORTH);

        textTrama = new JTextArea(5, 20);
        textTrama.setLineWrap(true);
        textTrama.setWrapStyleWord(true);
        JScrollPane scrollTrama = new JScrollPane(textTrama);
        panelTrama.add(scrollTrama, BorderLayout.CENTER);

        add(panelTrama, BorderLayout.CENTER);

        JPanel panelPulsanti = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        buttonSalva = new JButton("Salva nel Database");
        buttonAnnulla = new JButton("Annulla");

        panelPulsanti.add(buttonSalva);
        panelPulsanti.add(buttonAnnulla);
        add(panelPulsanti, BorderLayout.SOUTH);

        buttonAnnulla.addActionListener(e -> this.dispose());
        buttonSalva.addActionListener(e -> salvaFilm());
    }

    private void salvaFilm() {
        String titolo = textTitolo.getText().trim();
        String durataStr = textDurata.getText().trim();
        String genere = textGenere.getText().trim();
        String classificazione = (String) comboClassificazione.getSelectedItem();
        String trama = textTrama.getText().trim();

        if (titolo.isEmpty() || durataStr.isEmpty() || genere.isEmpty() || trama.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Compila tutti i campi prima di salvare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try {
            if (durataStr.length() == 5) {
                durataStr += ":00";
            }
            LocalTime durata = LocalTime.parse(durataStr);

            Film nuovoFilm = new Film(titolo, durata, genere, classificazione, trama, null);

            controller.aggiungiFilm(nuovoFilm);

            JOptionPane.showMessageDialog(this, "Film '" + titolo + "' registrato con successo nel Database!", "Successo", JOptionPane.INFORMATION_MESSAGE);
            this.dispose();

        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato durata non valido. Usa HH:MM (es. 02:15).", "Errore Input", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Errore durante il salvataggio nel database:\n" + ex.getMessage(), "Errore DB", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}