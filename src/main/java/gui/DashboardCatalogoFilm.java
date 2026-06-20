package gui;

import controller.Controller;
import model.Cliente;
import model.Film;
import javax.swing.*;
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

    public DashboardCatalogoFilm(Controller controller, Cliente cliente) {
        this.controller = controller;
        this.clienteLoggato = cliente;

        setContentPane(panelCatalogo);
        setTitle("Catalogo Film Disponibili");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);

        panelListaFilm.setLayout(new BoxLayout(panelListaFilm, BoxLayout.Y_AXIS));

        ArrayList<Film> filmDisponibili = controller.getListaFilm();

        for (Film film : filmDisponibili) {

            JPanel rigaFilm = new JPanel(new BorderLayout(20, 10));
            rigaFilm.setBorder(BorderFactory.createCompoundBorder(
                    BorderFactory.createMatteBorder(0, 0, 1, 0, Color.LIGHT_GRAY),
                    BorderFactory.createEmptyBorder(15, 15, 15, 15)
            ));
            rigaFilm.setBackground(Color.WHITE);

            JLabel labelLocandina = new JLabel();
            labelLocandina.setPreferredSize(new Dimension(120, 180));
            labelLocandina.setHorizontalAlignment(SwingConstants.CENTER);
            labelLocandina.setBorder(BorderFactory.createEmptyBorder());

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
            panelTesto.setBackground(Color.WHITE);

            JLabel labelTitolo = new JLabel(film.getTitolo());
            labelTitolo.setFont(new Font("SansSerif", Font.BOLD, 20));

            JLabel labelInfoGenerali = new JLabel("Genere: " + film.getGenere() + "  |  Durata: " + film.getDurataMinuti() + " min");
            labelInfoGenerali.setFont(new Font("SansSerif", Font.PLAIN, 14));

            JLabel labelClassificazione = new JLabel("Classificazione Età: " + film.getClassificazioneEta());
            labelClassificazione.setFont(new Font("SansSerif", Font.PLAIN, 14));

            JLabel labelSala = new JLabel("Ubicazione: " + film.getSalaAssegnata());
            labelSala.setFont(new Font("SansSerif", Font.BOLD, 14));
            labelSala.setForeground(new Color(41, 128, 185));

            panelTesto.add(labelTitolo);
            panelTesto.add(Box.createVerticalStrut(10));
            panelTesto.add(labelInfoGenerali);
            panelTesto.add(Box.createVerticalStrut(5));
            panelTesto.add(labelClassificazione);
            panelTesto.add(Box.createVerticalStrut(5));
            panelTesto.add(labelSala);

            rigaFilm.add(panelTesto, BorderLayout.CENTER);

            JPanel panelPulsanti = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
            panelPulsanti.setBackground(Color.WHITE);
            JButton pulsanteTrama = new JButton("Visualizza Trama");
            JButton pulsanteRecensioni = new JButton("Recensioni");
            JButton pulsantePrenotazione = new JButton("Prenota Biglietti");

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
        }

        tornaAlMenuButton.addActionListener(e -> {
            this.dispose();
            new DashboardCliente(this.controller, this.clienteLoggato);
        });

        setVisible(true);
    }

    private void mostraFinestraDettagli(Film film) {
        JDialog dialogDettagli = new JDialog(this, "Dettagli Film: " + film.getTitolo(), true);
        dialogDettagli.setLayout(new BorderLayout(10, 10));
        dialogDettagli.setSize(500, 420);
        dialogDettagli.setLocationRelativeTo(this);

        JPanel panelContenuto = new JPanel();
        panelContenuto.setLayout(new BoxLayout(panelContenuto, BoxLayout.Y_AXIS));
        panelContenuto.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        JLabel dTitolo = new JLabel(film.getTitolo());
        dTitolo.setFont(new Font("SansSerif", Font.BOLD, 20));

        JTextArea dTrama = new JTextArea(film.getTrama());
        dTrama.setLineWrap(true);
        dTrama.setWrapStyleWord(true);
        dTrama.setEditable(false);
        dTrama.setFont(new Font("SansSerif", Font.PLAIN, 14));
        JScrollPane scrollTrama = new JScrollPane(dTrama);

        panelContenuto.add(dTitolo);
        panelContenuto.add(Box.createVerticalStrut(15));
        panelContenuto.add(scrollTrama);

        dialogDettagli.add(panelContenuto, BorderLayout.CENTER);

        JButton buttonChiudi = new JButton("Chiudi");
        buttonChiudi.addActionListener(ev -> dialogDettagli.dispose());
        dialogDettagli.add(buttonChiudi, BorderLayout.SOUTH);

        dialogDettagli.setVisible(true);
    }
}