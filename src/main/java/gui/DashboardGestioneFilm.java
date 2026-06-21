package gui;

import controller.Controller;
import model.Film;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
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
    private JComboBox<String> comboSala;
    private JTextArea textTrama;
    private JButton btnScegliImmagine;
    private JLabel labelPathImmagine;
    private JButton buttonSalva;
    private JButton buttonAnnulla;

    private String percorsoImmagineSelezionata = null;

    public DashboardGestioneFilm(Controller controller) {
        this.controller = controller;

        textTitolo = new JTextField();
        textDurata = new JTextField("02:00");
        textGenere = new JTextField();

        String[] sale = new String[12];
        for(int i = 0; i < 12; i++) {
            sale[i] = "Sala " + (i + 1);
        }
        comboSala = new JComboBox<>(sale);
        comboClassificazione = new JComboBox<>(new String[]{"T (Per Tutti)", "14+", "16+", "18+"});

        btnScegliImmagine = new JButton("Scegli File...");
        labelPathImmagine = new JLabel("Nessun file");
        textTrama = new JTextArea();
        buttonSalva = new JButton("Salva nel Database");
        buttonAnnulla = new JButton("Annulla");

        sistemaGrafica();

        setTitle("Gestione Film - Inserimento Nuovo Titolo");
        setSize(580, 640);
        setMinimumSize(new Dimension(520, 580));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

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
            String grannyStr = textDurata.getText().trim();
            String genere = textGenere.getText().trim();
            String classificazione = (String) comboClassificazione.getSelectedItem();
            String trama = textTrama.getText().trim();
            String salaScelta = (String) comboSala.getSelectedItem();

            if (titolo.isEmpty() || grannyStr.isEmpty() || genere.isEmpty() || trama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Compila tutti i campi prima di salvare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                if (grannyStr.length() == 5) grannyStr += ":00";
                LocalTime durata = LocalTime.parse(grannyStr);

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

    private void sistemaGrafica() {
        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color sfondoCard = new Color(38, 46, 78);
        Color testoChiaro = Color.WHITE;
        Color bluAcceso = new Color(54, 112, 233);
        Color grigioScuro = new Color(50, 58, 89);
        Color verdeSuccesso = new Color(46, 204, 113);

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(sfondoScuro);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(sfondoPannello);
        panelForm.setBorder(new EmptyBorder(20, 25, 10, 25));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel lblTitoloForm = new JLabel("Titolo del Film:");
        JLabel lblDurataForm = new JLabel("Durata (Formato HH:MM):");
        JLabel lblGenereForm = new JLabel("Genere:");
        JLabel lblSalaForm = new JLabel("Sala Assegnata:");
        JLabel lblClassForm = new JLabel("Classificazione Età:");
        JLabel lblCopertinaForm = new JLabel("Copertina:");
        JLabel lblTramaForm = new JLabel("Trama:");

        JLabel[] labels = {lblTitoloForm, lblDurataForm, lblGenereForm, lblSalaForm, lblClassForm, lblCopertinaForm, lblTramaForm};
        for (JLabel l : labels) {
            l.setForeground(testoChiaro);
            l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        }

        JTextField[] fields = {textTitolo, textDurata, textGenere};
        for (JTextField f : fields) {
            f.setBackground(sfondoCard);
            f.setForeground(testoChiaro);
            f.setCaretColor(testoChiaro);
            f.setFont(new Font("SansSerif", Font.PLAIN, 13));
            f.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(grigioScuro, 1, true),
                    BorderFactory.createEmptyBorder(6, 10, 6, 10)
            ));
        }

        JComboBox<?>[] combos = {comboSala, comboClassificazione};
        for (JComboBox<?> cb : combos) {
            cb.setBackground(sfondoCard);
            cb.setForeground(testoChiaro);
            cb.setFont(new Font("SansSerif", Font.PLAIN, 13));
        }

        JPanel panelImmagine = new JPanel(new BorderLayout(8, 0));
        panelImmagine.setOpaque(false);

        btnScegliImmagine.setBackground(grigioScuro);
        btnScegliImmagine.setForeground(testoChiaro);
        btnScegliImmagine.setFocusPainted(false);
        btnScegliImmagine.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnScegliImmagine.setPreferredSize(new Dimension(110, 30));

        labelPathImmagine.setForeground(new Color(180, 190, 210));
        labelPathImmagine.setFont(new Font("SansSerif", Font.ITALIC, 12));

        panelImmagine.add(btnScegliImmagine, BorderLayout.WEST);
        panelImmagine.add(labelPathImmagine, BorderLayout.CENTER);

        textTrama.setLineWrap(true);
        textTrama.setWrapStyleWord(true);
        textTrama.setBackground(sfondoCard);
        textTrama.setForeground(testoChiaro);
        textTrama.setCaretColor(testoChiaro);
        textTrama.setFont(new Font("SansSerif", Font.PLAIN, 13));
        textTrama.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

        JScrollPane scrollTrama = new JScrollPane(textTrama);
        scrollTrama.setBorder(BorderFactory.createLineBorder(grigioScuro, 1, true));
        scrollTrama.setPreferredSize(new Dimension(200, 90));

        c.gridx = 0; c.gridy = 0; c.weightx = 0.0; panelForm.add(lblTitoloForm, c);
        c.gridx = 1; c.gridy = 0; c.weightx = 1.0; panelForm.add(textTitolo, c);

        c.gridx = 0; c.gridy = 1; c.weightx = 0.0; panelForm.add(lblDurataForm, c);
        c.gridx = 1; c.gridy = 1; c.weightx = 1.0; panelForm.add(textDurata, c);

        c.gridx = 0; c.gridy = 2; c.weightx = 0.0; panelForm.add(lblGenereForm, c);
        c.gridx = 1; c.gridy = 2; c.weightx = 1.0; panelForm.add(textGenere, c);

        c.gridx = 0; c.gridy = 3; c.weightx = 0.0; panelForm.add(lblSalaForm, c);
        c.gridx = 1; c.gridy = 3; c.weightx = 1.0; panelForm.add(comboSala, c);

        c.gridx = 0; c.gridy = 4; c.weightx = 0.0; panelForm.add(lblClassForm, c);
        c.gridx = 1; c.gridy = 4; c.weightx = 1.0; panelForm.add(comboClassificazione, c);

        c.gridx = 0; c.gridy = 5; c.weightx = 0.0; panelForm.add(lblCopertinaForm, c);
        c.gridx = 1; c.gridy = 5; c.weightx = 1.0; panelForm.add(panelImmagine, c);

        c.gridx = 0; c.gridy = 6; c.weightx = 0.0; c.anchor = GridBagConstraints.NORTH; c.fill = GridBagConstraints.HORIZONTAL;
        panelForm.add(lblTramaForm, c);
        c.gridx = 1; c.gridy = 6; c.weightx = 1.0; c.fill = GridBagConstraints.BOTH; c.weighty = 1.0;
        panelForm.add(scrollTrama, c);

        JPanel panelBottoni = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        panelBottoni.setBackground(sfondoScuro);
        panelBottoni.setBorder(new EmptyBorder(10, 20, 15, 20));

        buttonSalva.setBackground(verdeSuccesso);
        buttonSalva.setForeground(Color.WHITE);
        buttonSalva.setFocusPainted(false);
        buttonSalva.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonSalva.setPreferredSize(new Dimension(160, 38));
        buttonSalva.setFont(new Font("SansSerif", Font.BOLD, 12));

        buttonAnnulla.setBackground(grigioScuro);
        buttonAnnulla.setForeground(testoChiaro);
        buttonAnnulla.setFocusPainted(false);
        buttonAnnulla.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonAnnulla.setPreferredSize(new Dimension(100, 38));

        panelBottoni.add(buttonAnnulla);
        panelBottoni.add(buttonSalva);

        mainPanel.add(panelForm, BorderLayout.CENTER);
        mainPanel.add(panelBottoni, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }
}