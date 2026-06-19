package gui;

import controller.Controller;
import model.Film;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalTime;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class DashboardGestioneFilm extends JFrame {
    private Controller controller;

    private JTextField textTitolo;
    private JTextField textDurata;
    private JTextField textGenere;
    private JComboBox<String> comboClassificazione;
    private JComboBox<String> comboSala; // AGGIUNTO
    private JTextArea textTrama;
    private JButton btnScegliImmagine;
    private JLabel labelPathImmagine;
    private JButton buttonSalva;
    private JButton buttonAnnulla;

    private String percorsoImmagineSelezionata = null;

    public DashboardGestioneFilm(Controller controller) {
        this.controller = controller;

        setTitle("Gestione Film - Inserimento Nuovo Titolo");
        setSize(550, 550);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // AGGIORNATO: 7 righe invece di 6
        JPanel panelForm = new JPanel(new GridLayout(7, 2, 10, 10));
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

        // AGGIUNTA SALA
        panelForm.add(new JLabel("Sala Assegnata:"));
        String[] sale = new String[12];
        for(int i=0; i<12; i++) sale[i] = "Sala " + (i+1);
        comboSala = new JComboBox<>(sale);
        panelForm.add(comboSala);

        panelForm.add(new JLabel("Classificazione Età:"));
        comboClassificazione = new JComboBox<>(new String[]{"T (Per Tutti)", "14+", "16+", "18+"});
        panelForm.add(comboClassificazione);

        panelForm.add(new JLabel("Copertina:"));
        JPanel panelImmagine = new JPanel(new BorderLayout(5, 5));
        btnScegliImmagine = new JButton("Scegli File...");
        labelPathImmagine = new JLabel("Nessun file");
        panelImmagine.add(btnScegliImmagine, BorderLayout.WEST);
        panelImmagine.add(labelPathImmagine, BorderLayout.CENTER);
        panelForm.add(panelImmagine);

        panelForm.add(new JLabel("Trama:"));
        textTrama = new JTextArea();
        textTrama.setLineWrap(true);
        textTrama.setWrapStyleWord(true);
        JScrollPane scrollTrama = new JScrollPane(textTrama);
        panelForm.add(scrollTrama);

        add(panelForm, BorderLayout.CENTER);

        JPanel panelBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonSalva = new JButton("Salva nel Database");
        buttonSalva.setBackground(new Color(46, 204, 113));
        buttonSalva.setForeground(Color.WHITE);
        buttonAnnulla = new JButton("Annulla");

        panelBottoni.add(buttonSalva);
        panelBottoni.add(buttonAnnulla);
        add(panelBottoni, BorderLayout.SOUTH);

        btnScegliImmagine.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Immagini (JPG, PNG)", "jpg", "jpeg", "png"));

            if (fileChooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
                File fileSelezionato = fileChooser.getSelectedFile();
                try {
                    File cartellaLocandine = new File("locandine");
                    if (!cartellaLocandine.exists()) cartellaLocandine.mkdir();

                    Path origine = fileSelezionato.toPath();
                    Path destinazione = Paths.get("locandine", fileSelezionato.getName());
                    Files.copy(origine, destinazione, StandardCopyOption.REPLACE_EXISTING);

                    percorsoImmagineSelezionata = "locandine/" + fileSelezionato.getName();
                    labelPathImmagine.setText("✅ " + fileSelezionato.getName());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Errore durante il caricamento: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        buttonAnnulla.addActionListener(e -> this.dispose());

        buttonSalva.addActionListener(e -> {
            String titolo = textTitolo.getText().trim();
            String durataStr = textDurata.getText().trim();
            String genere = textGenere.getText().trim();
            String classificazione = (String) comboClassificazione.getSelectedItem();
            String trama = textTrama.getText().trim();
            String salaScelta = (String) comboSala.getSelectedItem(); // AGGIUNTO

            if (titolo.isEmpty() || durataStr.isEmpty() || genere.isEmpty() || trama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Compila tutti i campi prima di salvare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                if (durataStr.length() == 5) durataStr += ":00";
                LocalTime durata = LocalTime.parse(durataStr);

                // AGGIUNTO: salaScelta nel costruttore
                Film nuovoFilm = new Film(titolo, durata, genere, classificazione, trama, null, percorsoImmagineSelezionata, LocalDate.now(), salaScelta);

                controller.aggiungiFilm(nuovoFilm);

                JOptionPane.showMessageDialog(this, "Film '" + titolo + "' registrato con successo nel Database!", "Successo", JOptionPane.INFORMATION_MESSAGE);
                this.dispose();

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(this, "Formato durata non valido. Usa HH:MM (es. 02:15).", "Errore Input", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Errore durante il salvataggio nel database:\n" + ex.getMessage(), "Errore DB", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}