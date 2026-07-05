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

    private JPanel mainPanel;
    private JPanel panelMid;
    private JButton annullaButton;
    private JButton salvaNelDatabaseButton;
    private JLabel labelTitolo;
    private JLabel labelDurata;
    private JLabel labelGenere;
    private JLabel labelSala;
    private JLabel labelEta;
    private JLabel labelCopertina;
    private JLabel labelTrama;
    private JButton scegliFileButton;

    private JComboBox<String> comboBoxSala;
    private JComboBox<String> comboBoxEta;

    private JTextField textTitolo;
    private JTextField textGenere;
    private JTextField textDurata;
    private JTextArea textTrama;   // <-- Aggiornato a JTextArea

    private String percorsoImmagineSelezionata = null;

    /**
     * Crea una nuova istanza della dashboard di gestione film.
     *
     * @param controller il controller principale che gestisce la logica di business e l'interazione con il database.
     */
    public DashboardGestioneFilm(Controller controller) {
        this.controller = controller;

        sistemaGrafica();

        setContentPane(mainPanel);
        setTitle("Gestione Film - Inserimento Nuovo Titolo");
        setSize(580, 640);
        setMinimumSize(new Dimension(520, 580));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        if (comboBoxSala != null) {
            comboBoxSala.removeAllItems();
            for(int i = 1; i <= 12; i++) {
                comboBoxSala.addItem("Sala " + i);
            }
        }

        if (comboBoxEta != null) {
            comboBoxEta.removeAllItems();
            comboBoxEta.addItem("T (Per Tutti)");
            comboBoxEta.addItem("14+");
            comboBoxEta.addItem("16+");
            comboBoxEta.addItem("18+");
        }


        scegliFileButton.addActionListener(e -> {
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
                    scegliFileButton.setText("✅ " + fileSelezionato.getName());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Errore durante il caricamento: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        annullaButton.addActionListener(e -> this.dispose());

        salvaNelDatabaseButton.addActionListener(e -> {
            String titolo = textTitolo != null ? textTitolo.getText().trim() : "";
            String durataFilm = textDurata != null ? textDurata.getText().trim() : "";
            String genere = textGenere != null ? textGenere.getText().trim() : "";
            String classificazione = comboBoxEta != null ? (String) comboBoxEta.getSelectedItem() : "";
            String trama = textTrama != null ? textTrama.getText().trim() : "";
            String salaScelta = comboBoxSala != null ? (String) comboBoxSala.getSelectedItem() : "";

            if (titolo.isEmpty() || durataFilm.isEmpty() || genere.isEmpty() || trama.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Compila tutti i campi prima di salvare.", "Attenzione", JOptionPane.WARNING_MESSAGE);
                return;
            }

            try {
                if (durataFilm.length() == 5) durataFilm += ":00";
                LocalTime durata = LocalTime.parse(durataFilm);

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
        Color grigioScuro = new Color(50, 58, 89);
        Color verde = new Color(46, 204, 113);

        if (mainPanel != null) {
            mainPanel.setBackground(sfondoScuro);
        }

        if (panelMid != null) {
            panelMid.setBackground(sfondoPannello);
            panelMid.setBorder(new EmptyBorder(20, 25, 20, 25));
        }

        JLabel[] labels = {labelTitolo, labelDurata, labelGenere, labelSala, labelEta, labelCopertina, labelTrama};
        for (JLabel lbl : labels) {
            if (lbl != null) {
                lbl.setForeground(testoChiaro);
                lbl.setFont(new Font("SansSerif", Font.PLAIN, 13));
            }
        }

        JTextField[] fields = {textTitolo, textDurata, textGenere};
        for (JTextField field : fields) {
            if (field != null) {
                field.setBackground(sfondoCard);
                field.setForeground(testoChiaro);
                field.setCaretColor(testoChiaro);
                field.setFont(new Font("SansSerif", Font.PLAIN, 13));
                field.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(grigioScuro, 1, true),
                        BorderFactory.createEmptyBorder(6, 10, 6, 10)
                ));
            }
        }

        if (textTrama != null) {
            textTrama.setBackground(sfondoCard);
            textTrama.setForeground(testoChiaro);
            textTrama.setCaretColor(testoChiaro);
            textTrama.setFont(new Font("SansSerif", Font.PLAIN, 13));
            textTrama.setLineWrap(true);
            textTrama.setWrapStyleWord(true);
            textTrama.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(grigioScuro, 1, true),
                    BorderFactory.createEmptyBorder(6, 10, 6, 10)
            ));
        }

        JComboBox<?>[] combos = {comboBoxSala, comboBoxEta};
        for (JComboBox<?> combo : combos) {
            if (combo != null) {
                combo.setBackground(sfondoCard);
                combo.setForeground(testoChiaro);
                combo.setFont(new Font("SansSerif", Font.PLAIN, 13));
            }
        }

        if (scegliFileButton != null) {
            scegliFileButton.setBackground(grigioScuro);
            scegliFileButton.setForeground(testoChiaro);
            scegliFileButton.setFocusPainted(false);
            scegliFileButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        if (annullaButton != null) {
            annullaButton.setBackground(grigioScuro);
            annullaButton.setForeground(testoChiaro);
            annullaButton.setFocusPainted(false);
            annullaButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            annullaButton.setPreferredSize(new Dimension(100, 38));
        }

        if (salvaNelDatabaseButton != null) {
            salvaNelDatabaseButton.setBackground(verde);
            salvaNelDatabaseButton.setForeground(Color.WHITE);
            salvaNelDatabaseButton.setFocusPainted(false);
            salvaNelDatabaseButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            salvaNelDatabaseButton.setPreferredSize(new Dimension(160, 38));
            salvaNelDatabaseButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        }
    }
}