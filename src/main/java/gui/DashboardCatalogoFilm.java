package gui;

import controller.Controller;
import model.Cliente;
import model.Film;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;

public class DashboardCatalogoFilm extends JFrame {
    private JPanel panelCatalogo;
    private JButton tornaAlMenuButton;
    private JScrollPane scrollPanelFilm;
    private JPanel panelListaFilm;
    private Controller controller;
    private Cliente clienteLoggato;

    /**
     * Crea una nuova istanza della dashboard del catalogo film.
     *
     * @param controller il controller principale che gestisce la logica del sistema.
     * @param cliente il {@link Cliente} che sta visualizzando il catalogo.
     */
    public DashboardCatalogoFilm(Controller controller, Cliente cliente) {
        this.controller = controller;
        this.clienteLoggato = cliente;

        sistemaGrafica();

        setContentPane(panelCatalogo);
        setTitle("Catalogo Film Disponibili");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(960, 720);
        setMinimumSize(new Dimension(850, 600));
        setLocationRelativeTo(null);

        panelListaFilm.setLayout(new BoxLayout(panelListaFilm, BoxLayout.Y_AXIS));

        ArrayList<Film> filmDisponibili = controller.getListaFilm();

        for (Film film : filmDisponibili) {

            JPanel rigaFilm = new JPanel(new BorderLayout(20, 10));
            rigaFilm.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createLineBorder(new Color(50, 58, 89), 1, true),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            rigaFilm.setBackground(new Color(38, 46, 78));

            JLabel labelLocandina = new JLabel();
            labelLocandina.setPreferredSize(new Dimension(120, 180));
            labelLocandina.setHorizontalAlignment(SwingConstants.CENTER);
            labelLocandina.setBorder(BorderFactory.createEmptyBorder());
            labelLocandina.setForeground(Color.WHITE);

            if (film.getPercorsoCopertina() != null && !film.getPercorsoCopertina().isEmpty()) {
                File fileImmagine = new File(film.getPercorsoCopertina());
                if (fileImmagine.exists()) {
                    ImageIcon icona = new ImageIcon(film.getPercorsoCopertina());
                    Image imgScalata = icona.getImage().getScaledInstance(120, 180, Image.SCALE_SMOOTH);
                    labelLocandina.setIcon(new ImageIcon(imgScalata));
                } else {
                    labelLocandina.setText("N/D");
                }
            } else {
                labelLocandina.setText("🎬");
                labelLocandina.setFont(new Font("SansSerif", Font.PLAIN, 40));
            }
            rigaFilm.add(labelLocandina, BorderLayout.WEST);

            JPanel panelTesto = new JPanel();
            panelTesto.setLayout(new BoxLayout(panelTesto, BoxLayout.Y_AXIS));
            panelTesto.setBackground(new Color(38, 46, 78));

            JLabel labelTitolo = new JLabel(film.getTitolo());
            labelTitolo.setFont(new Font("SansSerif", Font.BOLD, 18));
            labelTitolo.setForeground(Color.WHITE);

            JLabel labelInfoGenerali = new JLabel("Genere: " + film.getGenere() + "  |  Durata: " + film.getDurataMinuti() + " min");
            labelInfoGenerali.setFont(new Font("SansSerif", Font.PLAIN, 13));
            labelInfoGenerali.setForeground(new Color(200, 210, 230));

            JLabel labelClassificazione = new JLabel("Classificazione Età: " + film.getClassificazioneEta());
            labelClassificazione.setFont(new Font("SansSerif", Font.PLAIN, 13));
            labelClassificazione.setForeground(new Color(200, 210, 230));

            JLabel labelSala = new JLabel("Ubicazione: " + film.getSalaAssegnata());
            labelSala.setFont(new Font("SansSerif", Font.BOLD, 13));
            labelSala.setForeground(new Color(54, 112, 233));

            panelTesto.add(labelTitolo);
            panelTesto.add(Box.createVerticalStrut(10));
            panelTesto.add(labelInfoGenerali);
            panelTesto.add(Box.createVerticalStrut(5));
            panelTesto.add(labelClassificazione);
            panelTesto.add(Box.createVerticalStrut(5));
            panelTesto.add(labelSala);

            rigaFilm.add(panelTesto, BorderLayout.CENTER);

            JPanel panelPulsanti = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
            panelPulsanti.setBackground(new Color(38, 46, 78));

            JButton pulsanteTrama = new JButton("Visualizza Trama");
            pulsanteTrama.setBackground(new Color(50, 58, 89));
            pulsanteTrama.setForeground(Color.WHITE);
            pulsanteTrama.setFocusPainted(false);
            pulsanteTrama.setCursor(new Cursor(Cursor.HAND_CURSOR));
            pulsanteTrama.setPreferredSize(new Dimension(140, 35));

            JButton pulsanteRecensioni = new JButton("Recensioni");
            pulsanteRecensioni.setBackground(new Color(50, 58, 89));
            pulsanteRecensioni.setForeground(Color.WHITE);
            pulsanteRecensioni.setFocusPainted(false);
            pulsanteRecensioni.setCursor(new Cursor(Cursor.HAND_CURSOR));
            pulsanteRecensioni.setPreferredSize(new Dimension(120, 35));

            JButton pulsantePrenotazione = new JButton("Prenota Biglietti");
            pulsantePrenotazione.setBackground(new Color(54, 112, 233));
            pulsantePrenotazione.setForeground(Color.WHITE);
            pulsantePrenotazione.setFocusPainted(false);
            pulsantePrenotazione.setCursor(new Cursor(Cursor.HAND_CURSOR));
            pulsantePrenotazione.setPreferredSize(new Dimension(150, 35));
            pulsantePrenotazione.setFont(new Font("SansSerif", Font.BOLD, 12));

            panelPulsanti.add(pulsanteTrama);
            panelPulsanti.add(pulsanteRecensioni);
            panelPulsanti.add(pulsantePrenotazione);
            rigaFilm.add(panelPulsanti, BorderLayout.SOUTH);

            pulsanteRecensioni.addActionListener(e -> {
                new DashboardRecensioni(controller, clienteLoggato, film);
            });

            pulsanteTrama.addActionListener(e -> {
                mostraFinestraDettagli(film);
            });

            pulsantePrenotazione.addActionListener(e -> {
                new DashboardPrenotazione(controller, clienteLoggato, film);
            });

            panelListaFilm.add(rigaFilm);
            panelListaFilm.add(Box.createVerticalStrut(15));
        }

        tornaAlMenuButton.addActionListener(e -> {
            this.dispose();
            new DashboardCliente(this.controller, this.clienteLoggato);
        });

        setVisible(true);
    }

    private void sistemaGrafica() {
        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color testoChiaro = Color.WHITE;
        Color grigioScuro = new Color(50, 58, 89);

        panelCatalogo.removeAll();
        panelCatalogo.setLayout(new BorderLayout());
        panelCatalogo.setBackground(sfondoScuro);

        JPanel panelTop = new JPanel(new BorderLayout(15, 0));
        panelTop.setBackground(sfondoScuro);
        panelTop.setBorder(new EmptyBorder(15, 20, 15, 20));

        JLabel labelTitoloSchermata = new JLabel("Catalogo Film Disponibili");
        labelTitoloSchermata.setForeground(testoChiaro);
        labelTitoloSchermata.setFont(new Font("SansSerif", Font.BOLD, 18));

        tornaAlMenuButton.setText("Indietro");
        tornaAlMenuButton.setBackground(grigioScuro);
        tornaAlMenuButton.setForeground(testoChiaro);
        tornaAlMenuButton.setFocusPainted(false);
        tornaAlMenuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tornaAlMenuButton.setPreferredSize(new Dimension(100, 32));

        panelTop.add(labelTitoloSchermata, BorderLayout.WEST);
        panelTop.add(tornaAlMenuButton, BorderLayout.EAST);

        scrollPanelFilm.setBorder(BorderFactory.createEmptyBorder());
        scrollPanelFilm.setBackground(sfondoPannello);
        scrollPanelFilm.getViewport().setBackground(sfondoPannello);

        panelListaFilm.setBackground(sfondoPannello);
        panelListaFilm.setBorder(new EmptyBorder(15, 20, 15, 20));

        panelCatalogo.add(panelTop, BorderLayout.NORTH);
        panelCatalogo.add(scrollPanelFilm, BorderLayout.CENTER);
    }

    private void mostraFinestraDettagli(Film film) {
        JDialog dialogDettagli = new JDialog(this, "Dettagli Film: " + film.getTitolo(), true);
        dialogDettagli.setLayout(new BorderLayout(10, 10));
        dialogDettagli.setSize(520, 440);
        dialogDettagli.setLocationRelativeTo(this);

        JPanel panelContenuto = new JPanel();
        panelContenuto.setLayout(new BoxLayout(panelContenuto, BoxLayout.Y_AXIS));
        panelContenuto.setBorder(BorderFactory.createEmptyBorder(20, 20, 15, 20));
        panelContenuto.setBackground(new Color(28, 34, 58));

        JLabel dTitolo = new JLabel(film.getTitolo());
        dTitolo.setFont(new Font("SansSerif", Font.BOLD, 18));
        dTitolo.setForeground(Color.WHITE);

        JTextArea dTrama = new JTextArea(film.getTrama());
        dTrama.setLineWrap(true);
        dTrama.setWrapStyleWord(true);
        dTrama.setEditable(false);
        dTrama.setFont(new Font("SansSerif", Font.PLAIN, 14));
        dTrama.setBackground(new Color(38, 46, 78));
        dTrama.setForeground(Color.WHITE);
        dTrama.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane scrollTrama = new JScrollPane(dTrama);
        scrollTrama.setBorder(BorderFactory.createLineBorder(new Color(50, 58, 89), 1, true));

        panelContenuto.add(dTitolo);
        panelContenuto.add(Box.createVerticalStrut(15));
        panelContenuto.add(scrollTrama);

        dialogDettagli.add(panelContenuto, BorderLayout.CENTER);

        JPanel panelBottom = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBottom.setBackground(new Color(28, 34, 58));
        panelBottom.setBorder(new EmptyBorder(0, 0, 15, 0));

        JButton buttonChiudi = new JButton("Chiudi");
        buttonChiudi.setBackground(new Color(50, 58, 89));
        buttonChiudi.setForeground(Color.WHITE);
        buttonChiudi.setFocusPainted(false);
        buttonChiudi.setPreferredSize(new Dimension(120, 36));
        buttonChiudi.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonChiudi.addActionListener(ev -> dialogDettagli.dispose());

        panelBottom.add(buttonChiudi);
        dialogDettagli.add(panelBottom, BorderLayout.SOUTH);

        dialogDettagli.setVisible(true);
    }
}