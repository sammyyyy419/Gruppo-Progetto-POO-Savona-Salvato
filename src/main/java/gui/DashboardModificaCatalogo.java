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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class DashboardModificaCatalogo extends JFrame {
    private JPanel mainPanel;
    private JButton btnTorna;
    private JScrollPane scrollPanelFilm;
    private JPanel panelListaFilm;

    private Controller controller;

    public DashboardModificaCatalogo(Controller controller) {
        this.controller = controller;

        setContentPane(mainPanel);
        setTitle("Gestione Catalogo Film");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(850, 600);
        setLocationRelativeTo(null);

        panelListaFilm.setLayout(new BoxLayout(panelListaFilm, BoxLayout.Y_AXIS));
        aggiornaListaFilm();

        btnTorna.addActionListener(e -> this.dispose());

        setVisible(true);
    }

    private void aggiornaListaFilm() {
        panelListaFilm.removeAll();
        ArrayList<Film> filmDisponibili = controller.getListaFilm();

        if (filmDisponibili.isEmpty()) {
            panelListaFilm.add(new JLabel("Nessun film presente nel catalogo."));
        } else {
            for (Film film : filmDisponibili) {
                JPanel rigaFilm = creaRigaFilm(film);
                panelListaFilm.add(rigaFilm);
                panelListaFilm.add(new JSeparator(JSeparator.HORIZONTAL));
            }
        }
        panelListaFilm.revalidate();
        panelListaFilm.repaint();
    }

    private JPanel creaRigaFilm(Film film) {
        JPanel riga = new JPanel(new BorderLayout(15, 10));
        riga.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        riga.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JLabel labelLocandina = new JLabel();
        labelLocandina.setPreferredSize(new Dimension(60, 90));
        labelLocandina.setHorizontalAlignment(SwingConstants.CENTER);
        labelLocandina.setBorder(BorderFactory.createLineBorder(Color.GRAY));

        if (film.getPercorsoCopertina() != null && !film.getPercorsoCopertina().isEmpty()) {
            ImageIcon icon = new ImageIcon(film.getPercorsoCopertina());
            Image img = icon.getImage().getScaledInstance(60, 90, Image.SCALE_SMOOTH);
            labelLocandina.setIcon(new ImageIcon(img));
        } else {
            labelLocandina.setText("🎬");
        }
        riga.add(labelLocandina, BorderLayout.WEST);

        JPanel panelTesto = new JPanel();
        panelTesto.setLayout(new BoxLayout(panelTesto, BoxLayout.Y_AXIS));
        JLabel labelTitolo = new JLabel(film.getTitolo());
        labelTitolo.setFont(new Font("Arial", Font.BOLD, 16));
        JLabel labelInfo = new JLabel("Genere: " + film.getGenere() + "  |  Durata: " + film.getDurataMinuti() + " min");

        // AGGIUNTO: MOSTRA LA SALA ANCHE QUI!
        JLabel labelClass = new JLabel("Classificazione: " + film.getClassificazioneEta() + "  |  Ubicazione: " + film.getSalaAssegnata());

        panelTesto.add(labelTitolo);
        panelTesto.add(Box.createVerticalStrut(5));
        panelTesto.add(labelInfo);
        panelTesto.add(Box.createVerticalStrut(3));
        panelTesto.add(labelClass);
        riga.add(panelTesto, BorderLayout.CENTER);

        JPanel panelPulsanti = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 15));
        JButton btnModifica = new JButton("Modifica ✏️");
        JButton btnElimina = new JButton("Elimina 🗑️");
        btnElimina.setBackground(new Color(231, 76, 60));
        btnElimina.setForeground(Color.WHITE);

        panelPulsanti.add(btnModifica);
        panelPulsanti.add(btnElimina);
        riga.add(panelPulsanti, BorderLayout.EAST);

        btnElimina.addActionListener(e -> {
            int conferma = JOptionPane.showConfirmDialog(this,
                    "Sei sicuro di voler eliminare '" + film.getTitolo() + "' dal database?",
                    "Conferma Eliminazione", JOptionPane.YES_NO_OPTION);
            if (conferma == JOptionPane.YES_OPTION) {
                try {
                    controller.eliminaFilm(film);
                    JOptionPane.showMessageDialog(this, "Film eliminato con successo!");
                    aggiornaListaFilm();
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Errore: " + ex.getMessage(), "Errore DB", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        btnModifica.addActionListener(e -> {
            mostraFinestraModifica(film);
        });

        return riga;
    }

    private void mostraFinestraModifica(Film film) {
        JDialog dialog = new JDialog(this, "Modifica Film: " + film.getTitolo(), true);
        dialog.setSize(500, 480); // Leggermente allargato
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        // AGGIORNATO A 7 RIGHE!
        JPanel formPanel = new JPanel(new GridLayout(7, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JTextField txtTitolo = new JTextField(film.getTitolo());
        JTextField txtDurata = new JTextField(String.format("%02d:%02d", film.getDurata().getHour(), film.getDurata().getMinute()));
        JTextField txtGenere = new JTextField(film.getGenere());

        // AGGIUNTA SALA
        String[] sale = new String[12];
        for(int i=0; i<12; i++) sale[i] = "Sala " + (i+1);
        JComboBox<String> cmbSala = new JComboBox<>(sale);
        cmbSala.setSelectedItem(film.getSalaAssegnata());

        JComboBox<String> cmbClassificazione = new JComboBox<>(new String[]{"T (Per Tutti)", "14+", "16+", "18+"});
        cmbClassificazione.setSelectedItem(film.getClassificazioneEta());

        final String[] percorsoAggiornato = {film.getPercorsoCopertina()};

        JButton btnCambiaImmagine = new JButton("Cambia Foto...");
        String testoLabel = (film.getPercorsoCopertina() != null) ? "Immagine Presente" : "Nessuna immagine";
        JLabel lblPathImmagine = new JLabel(testoLabel);
        JPanel panelImg = new JPanel(new BorderLayout(5, 5));
        panelImg.add(btnCambiaImmagine, BorderLayout.WEST);
        panelImg.add(lblPathImmagine, BorderLayout.CENTER);

        btnCambiaImmagine.addActionListener(ev -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(new FileNameExtensionFilter("Immagini (JPG, PNG)", "jpg", "jpeg", "png"));
            if (fileChooser.showOpenDialog(dialog) == JFileChooser.APPROVE_OPTION) {
                File fileSel = fileChooser.getSelectedFile();
                try {
                    File dir = new File("locandine");
                    if (!dir.exists()) dir.mkdir();
                    Path dest = Paths.get("locandine", fileSel.getName());
                    Files.copy(fileSel.toPath(), dest, StandardCopyOption.REPLACE_EXISTING);

                    percorsoAggiornato[0] = "locandine/" + fileSel.getName();
                    lblPathImmagine.setText("✅ " + fileSel.getName());
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(dialog, "Errore: " + ex.getMessage());
                }
            }
        });

        JTextArea txtTrama = new JTextArea(film.getTrama());
        txtTrama.setLineWrap(true);
        txtTrama.setWrapStyleWord(true);
        JScrollPane scrollTrama = new JScrollPane(txtTrama);

        formPanel.add(new JLabel("Titolo:")); formPanel.add(txtTitolo);
        formPanel.add(new JLabel("Durata (HH:MM):")); formPanel.add(txtDurata);
        formPanel.add(new JLabel("Genere:")); formPanel.add(txtGenere);
        formPanel.add(new JLabel("Sala Assegnata:")); formPanel.add(cmbSala); // AGGIUNTO
        formPanel.add(new JLabel("Classificazione:")); formPanel.add(cmbClassificazione);
        formPanel.add(new JLabel("Copertina:")); formPanel.add(panelImg);
        formPanel.add(new JLabel("Trama:")); formPanel.add(scrollTrama);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel bottoniPanel = new JPanel();
        JButton btnSalva = new JButton("Salva Modifiche");
        JButton btnAnnulla = new JButton("Annulla");

        btnAnnulla.addActionListener(e -> dialog.dispose());

        btnSalva.addActionListener(e -> {
            try {
                String nTitolo = txtTitolo.getText().trim();
                LocalTime nDurata = LocalTime.parse(txtDurata.getText().trim());
                String nGenere = txtGenere.getText().trim();
                String nClass = (String) cmbClassificazione.getSelectedItem();
                String nTrama = txtTrama.getText().trim();
                String nSala = (String) cmbSala.getSelectedItem(); // AGGIUNTO

                // AGGIUNTO nSala ALLA CHIAMATA DEL CONTROLLER
                controller.modificaFilm(film, nTitolo, nDurata, nGenere, nClass, nTrama, percorsoAggiornato[0], film.getDataInizioProgrammazione(), nSala);

                JOptionPane.showMessageDialog(dialog, "Film aggiornato!");
                dialog.dispose();
                aggiornaListaFilm();

            } catch (DateTimeParseException ex) {
                JOptionPane.showMessageDialog(dialog, "Durata non valida. Usa HH:MM", "Errore", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(dialog, "Errore salvataggio: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        bottoniPanel.add(btnSalva);
        bottoniPanel.add(btnAnnulla);
        dialog.add(bottoniPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}