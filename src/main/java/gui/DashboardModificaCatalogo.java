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
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

/**
 * The type Dashboard modifica catalogo.
 */
public class DashboardModificaCatalogo extends JFrame {

    private JPanel mainPanel;
    private JLabel labelGestioneCatalogo;
    private JPanel panelScroll;
    private JScrollPane scrollCatalogo;
    private JButton indietroButton;

    private JPanel panelListaFilm;

    private Controller controller;

    /**
     * Instantiates a new Dashboard modifica catalogo.
     *
     * @param controller the controller
     */
    public DashboardModificaCatalogo(Controller controller) {
        this.controller = controller;

        panelListaFilm = new JPanel();
        panelListaFilm.setLayout(new BoxLayout(panelListaFilm, BoxLayout.Y_AXIS));
        scrollCatalogo.setViewportView(panelListaFilm);
        scrollCatalogo.getVerticalScrollBar().setUnitIncrement(16);

        sistemaGrafica();

        setContentPane(mainPanel);
        setTitle("Gestione Catalogo Film");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(920, 660);
        setMinimumSize(new Dimension(820, 560));
        setLocationRelativeTo(null);

        aggiornaListaFilm();

        indietroButton.addActionListener(e -> this.dispose());

        setVisible(true);
    }

    private void sistemaGrafica() {

        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color testoChiaro = Color.WHITE;
        Color grigioScuro = new Color(50, 58, 89);


        mainPanel.setBackground(sfondoScuro);

        labelGestioneCatalogo.setForeground(testoChiaro);
        labelGestioneCatalogo.setFont(new Font("SansSerif", Font.BOLD, 22));

        panelScroll.setBackground(sfondoScuro);
        panelScroll.setBorder(new EmptyBorder(10, 20, 10, 20));

        scrollCatalogo.setBorder(BorderFactory.createEmptyBorder());
        scrollCatalogo.setBackground(sfondoPannello);
        scrollCatalogo.getViewport().setBackground(sfondoPannello);

        panelListaFilm.setBackground(sfondoPannello);
        panelListaFilm.setBorder(new EmptyBorder(15, 20, 15, 20));

        indietroButton.setBackground(grigioScuro);
        indietroButton.setForeground(testoChiaro);
        indietroButton.setFocusPainted(false);
        indietroButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        indietroButton.setFont(new Font("SansSerif", Font.BOLD, 14));
    }

    private void aggiornaListaFilm() {
        panelListaFilm.removeAll();
        ArrayList<Film> filmDisponibili = controller.getListaFilm();

        if (filmDisponibili.isEmpty()) {
            JLabel lblVuoto = new JLabel("Nessun film presente nel catalogo.");
            lblVuoto.setForeground(new Color(140, 150, 180));
            lblVuoto.setFont(new Font("SansSerif", Font.ITALIC, 14));
            panelListaFilm.add(lblVuoto);
        } else {
            for (Film film : filmDisponibili) {
                JPanel rigaFilm = creaRigaFilm(film);
                panelListaFilm.add(rigaFilm);
                panelListaFilm.add(Box.createVerticalStrut(12));
            }
        }
        panelListaFilm.revalidate();
        panelListaFilm.repaint();
    }

    private JPanel creaRigaFilm(Film film) {
        JPanel riga = new JPanel(new BorderLayout(15, 10));
        riga.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(50, 58, 89), 1, true),
                BorderFactory.createEmptyBorder(12, 15, 12, 15)
        ));
        riga.setBackground(new Color(38, 46, 78));
        riga.setMaximumSize(new Dimension(Integer.MAX_VALUE, 120));

        JLabel labelLocandina = new JLabel();
        labelLocandina.setPreferredSize(new Dimension(60, 90));
        labelLocandina.setHorizontalAlignment(SwingConstants.CENTER);
        labelLocandina.setBorder(BorderFactory.createLineBorder(new Color(50, 58, 89), 1, true));
        labelLocandina.setForeground(Color.WHITE);

        if (film.getPercorsoCopertina() != null && !film.getPercorsoCopertina().isEmpty()) {
            ImageIcon icon = new ImageIcon(film.getPercorsoCopertina());
            Image img = icon.getImage().getScaledInstance(60, 90, Image.SCALE_SMOOTH);
            labelLocandina.setIcon(new ImageIcon(img));
        } else {
            labelLocandina.setText("🎬");
            labelLocandina.setFont(new Font("SansSerif", Font.PLAIN, 24));
        }
        riga.add(labelLocandina, BorderLayout.WEST);

        JPanel panelTesto = new JPanel();
        panelTesto.setLayout(new BoxLayout(panelTesto, BoxLayout.Y_AXIS));
        panelTesto.setBackground(new Color(38, 46, 78));

        JLabel labelTitolo = new JLabel(film.getTitolo());
        labelTitolo.setFont(new Font("SansSerif", Font.BOLD, 16));
        labelTitolo.setForeground(Color.WHITE);

        JLabel labelInfo = new JLabel("Genere: " + film.getGenere() + "  |  Durata: " + film.getDurataMinuti() + " min");
        labelInfo.setFont(new Font("SansSerif", Font.PLAIN, 13));
        labelInfo.setForeground(new Color(200, 210, 230));

        JLabel labelClass = new JLabel("Classificazione: " + film.getClassificazioneEta() + "  |  Ubicazione: " + film.getSalaAssegnata());
        labelClass.setFont(new Font("SansSerif", Font.PLAIN, 13));
        labelClass.setForeground(new Color(200, 210, 230));

        panelTesto.add(labelTitolo);
        panelTesto.add(Box.createVerticalStrut(6));
        panelTesto.add(labelInfo);
        panelTesto.add(Box.createVerticalStrut(4));
        panelTesto.add(labelClass);
        riga.add(panelTesto, BorderLayout.CENTER);

        JPanel panelPulsanti = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 15));
        panelPulsanti.setBackground(new Color(38, 46, 78));

        JButton btnModifica = new JButton("Modifica ✏️");
        btnModifica.setBackground(new Color(50, 58, 89));
        btnModifica.setForeground(Color.WHITE);
        btnModifica.setFocusPainted(false);
        btnModifica.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnModifica.setPreferredSize(new Dimension(110, 32));
        btnModifica.setFont(new Font("SansSerif", Font.PLAIN, 12));

        JButton btnElimina = new JButton("Elimina 🗑️");
        btnElimina.setBackground(new Color(176, 58, 75));
        btnElimina.setForeground(Color.WHITE);
        btnElimina.setFocusPainted(false);
        btnElimina.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnElimina.setPreferredSize(new Dimension(110, 32));
        btnElimina.setFont(new Font("SansSerif", Font.PLAIN, 12));

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
        Color sfondoPannello = new Color(28, 34, 58);
        Color sfondoCard = new Color(38, 46, 78);
        Color testoChiaro = Color.WHITE;
        Color grigioScuro = new Color(50, 58, 89);
        Color verdeSuccesso = new Color(46, 204, 113);

        JDialog dialog = new JDialog(this, "Modifica Film: " + film.getTitolo(), true);
        dialog.setSize(540, 540);
        dialog.setLocationRelativeTo(this);
        dialog.setLayout(new BorderLayout());

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(sfondoPannello);
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 25, 15, 25));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(6, 6, 6, 6);
        c.fill = GridBagConstraints.HORIZONTAL;

        JTextField txtTitolo = new JTextField(film.getTitolo());
        JTextField txtDurata = new JTextField(String.format("%02d:%02d", film.getDurata().getHour(), film.getDurata().getMinute()));
        JTextField txtGenere = new JTextField(film.getGenere());

        String[] sale = new String[12];
        for(int i=0; i<12; i++) sale[i] = "Sala " + (i+1);
        JComboBox<String> cmbSala = new JComboBox<>(sale);
        cmbSala.setSelectedItem(film.getSalaAssegnata());

        JComboBox<String> cmbClassificazione = new JComboBox<>(new String[]{"T (Per Tutti)", "14+", "16+", "18+"});
        cmbClassificazione.setSelectedItem(film.getClassificazioneEta());

        final String[] percorsoAggiornato = {film.getPercorsoCopertina()};

        JButton btnCambiaImmagine = new JButton("Cambia Foto...");
        btnCambiaImmagine.setBackground(grigioScuro);
        btnCambiaImmagine.setForeground(testoChiaro);
        btnCambiaImmagine.setFocusPainted(false);
        btnCambiaImmagine.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCambiaImmagine.setPreferredSize(new Dimension(120, 28));

        String testoLabel = (film.getPercorsoCopertina() != null) ? "Immagine Presente" : "Nessuna immagine";
        JLabel lblPathImmagine = new JLabel(testoLabel);
        lblPathImmagine.setForeground(new Color(180, 190, 210));
        lblPathImmagine.setFont(new Font("SansSerif", Font.ITALIC, 12));

        JPanel panelImg = new JPanel(new BorderLayout(8, 0));
        panelImg.setOpaque(false);
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
        txtTrama.setBackground(sfondoCard);
        txtTrama.setForeground(testoChiaro);
        txtTrama.setCaretColor(testoChiaro);
        txtTrama.setFont(new Font("SansSerif", Font.PLAIN, 13));
        txtTrama.setBorder(BorderFactory.createEmptyBorder(6, 8, 6, 8));

        JScrollPane scrollTrama = new JScrollPane(txtTrama);
        scrollTrama.setBorder(BorderFactory.createLineBorder(grigioScuro, 1, true));
        scrollTrama.setPreferredSize(new Dimension(200, 80));

        JLabel l1 = new JLabel("Titolo:");
        JLabel l2 = new JLabel("Durata (HH:MM):");
        JLabel l3 = new JLabel("Genere:");
        JLabel l4 = new JLabel("Sala Assegnata:");
        JLabel l5 = new JLabel("Classificazione:");
        JLabel l6 = new JLabel("Copertina:");
        JLabel l7 = new JLabel("Trama:");

        JLabel[] labels = {l1, l2, l3, l4, l5, l6, l7};
        for (JLabel l : labels) {
            l.setForeground(testoChiaro);
            l.setFont(new Font("SansSerif", Font.PLAIN, 13));
        }

        JTextField[] fields = {txtTitolo, txtDurata, txtGenere};
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

        JComboBox<?>[] combos = {cmbSala, cmbClassificazione};
        for (JComboBox<?> cb : combos) {
            cb.setBackground(sfondoCard);
            cb.setForeground(testoChiaro);
            cb.setFont(new Font("SansSerif", Font.PLAIN, 13));
        }

        c.gridx = 0; c.gridy = 0; c.weightx = 0.0; formPanel.add(l1, c);
        c.gridx = 1; c.gridy = 0; c.weightx = 1.0; formPanel.add(txtTitolo, c);

        c.gridx = 0; c.gridy = 1; c.weightx = 0.0; formPanel.add(l2, c);
        c.gridx = 1; c.gridy = 1; c.weightx = 1.0; formPanel.add(txtDurata, c);

        c.gridx = 0; c.gridy = 2; c.weightx = 0.0; formPanel.add(l3, c);
        c.gridx = 1; c.gridy = 2; c.weightx = 1.0; formPanel.add(txtGenere, c);

        c.gridx = 0; c.gridy = 3; c.weightx = 0.0; formPanel.add(l4, c);
        c.gridx = 1; c.gridy = 3; c.weightx = 1.0; formPanel.add(cmbSala, c);

        c.gridx = 0; c.gridy = 4; c.weightx = 0.0; formPanel.add(l5, c);
        c.gridx = 1; c.gridy = 4; c.weightx = 1.0; formPanel.add(cmbClassificazione, c);

        c.gridx = 0; c.gridy = 5; c.weightx = 0.0; formPanel.add(l6, c);
        c.gridx = 1; c.gridy = 5; c.weightx = 1.0; formPanel.add(panelImg, c);

        c.gridx = 0; c.gridy = 6; c.weightx = 0.0; c.anchor = GridBagConstraints.NORTH;
        formPanel.add(l7, c);
        c.gridx = 1; c.gridy = 6; c.weightx = 1.0; c.fill = GridBagConstraints.BOTH; c.weighty = 1.0;
        formPanel.add(scrollTrama, c);

        dialog.add(formPanel, BorderLayout.CENTER);

        JPanel bottoniPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        bottoniPanel.setBackground(sfondoPannello);
        bottoniPanel.setBorder(new EmptyBorder(5, 20, 20, 20));

        JButton btnSalva = new JButton("Salva Modifiche");
        btnSalva.setBackground(verdeSuccesso);
        btnSalva.setForeground(Color.WHITE);
        btnSalva.setFocusPainted(false);
        btnSalva.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnSalva.setPreferredSize(new Dimension(140, 36));
        btnSalva.setFont(new Font("SansSerif", Font.BOLD, 12));

        JButton btnAnnulla = new JButton("Annulla");
        btnAnnulla.setBackground(grigioScuro);
        btnAnnulla.setForeground(testoChiaro);
        btnAnnulla.setFocusPainted(false);
        btnAnnulla.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAnnulla.setPreferredSize(new Dimension(100, 36));

        btnAnnulla.addActionListener(e -> dialog.dispose());

        btnSalva.addActionListener(e -> {
            try {
                String nTitolo = txtTitolo.getText().trim();
                LocalTime nDurata = LocalTime.parse(txtDurata.getText().trim());
                String nGenere = txtGenere.getText().trim();
                String nClass = (String) cmbClassificazione.getSelectedItem();
                String nTrama = txtTrama.getText().trim();
                String nSala = (String) cmbSala.getSelectedItem();

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

        bottoniPanel.add(btnAnnulla);
        bottoniPanel.add(btnSalva);
        dialog.add(bottoniPanel, BorderLayout.SOUTH);

        dialog.setVisible(true);
    }
}