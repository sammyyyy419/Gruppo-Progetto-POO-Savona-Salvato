package gui;

import controller.Controller;
import model.Cliente;
import model.Film;
import javax.swing.*;
import java.awt.*;
import java.time.LocalTime;
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
        setSize(850, 600);
        setLocationRelativeTo(null);

        panelListaFilm.setLayout(new BoxLayout(panelListaFilm, BoxLayout.Y_AXIS));

        // Metodo commentato per ora, se vuoi inserire i film dal database!
        // se vuoi auto-popolarla per test de-commenta la riga sotto:
        // inizializzaFilmDiProva();

        ArrayList<Film> filmDisponibili = controller.getListaFilm();

        for (Film film : filmDisponibili) {

            JPanel rigaFilm = new JPanel(new BorderLayout(15, 10));
            rigaFilm.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));

            /*
            String titoloImg = film.getTitolo().toLowerCase().replace(" ", "_");
            String nomeImmagine = "/" + titoloImg + ".png";
            java.net.URL imageUrl = DashboardCatalogoFilm.class.getResource(nomeImmagine);

            if (imageUrl != null) {
                ImageIcon iconaOriginale = new ImageIcon(imageUrl);
                Image imgScalata = iconaOriginale.getImage().getScaledInstance(60, 90, Image.SCALE_SMOOTH);
                labelLocandina.setIcon(new ImageIcon(imgScalata));
            } else {
                labelLocandina.setText("🎬");
                labelLocandina.setPreferredSize(new Dimension(60, 90));
                labelLocandina.setHorizontalAlignment(SwingConstants.CENTER);
                labelLocandina.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            } */

            JLabel labelLocandina = new JLabel("🎬");
            labelLocandina.setPreferredSize(new Dimension(60, 90));
            labelLocandina.setHorizontalAlignment(SwingConstants.CENTER);
            labelLocandina.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            rigaFilm.add(labelLocandina, BorderLayout.WEST);

            JPanel panelTesto = new JPanel();
            panelTesto.setLayout(new BoxLayout(panelTesto, BoxLayout.Y_AXIS));

            JLabel labelTitolo = new JLabel(film.getTitolo());
            labelTitolo.setFont(new Font("Arial", Font.BOLD, 16));

            JLabel labelInfoGenerali = new JLabel("Genere: " + film.getGenere() + "  |  Durata: " + film.getDurataMinuti() + " min");
            labelInfoGenerali.setFont(new Font("Arial", Font.PLAIN, 13));

            JLabel labelClassificazione = new JLabel("Classificazione Età: " + film.getClassificazioneEta());
            labelClassificazione.setFont(new Font("Arial", Font.PLAIN, 13));

            JLabel labelSalaOrario = new JLabel("Sala: Sala 1  |  Orario: 18:30 - 21:00");
            labelSalaOrario.setFont(new Font("Arial", Font.PLAIN, 13));

            panelTesto.add(labelTitolo);
            panelTesto.add(Box.createVerticalStrut(5));
            panelTesto.add(labelInfoGenerali);
            panelTesto.add(Box.createVerticalStrut(3));
            panelTesto.add(labelClassificazione);
            panelTesto.add(Box.createVerticalStrut(3));
            panelTesto.add(labelSalaOrario);

            rigaFilm.add(panelTesto, BorderLayout.CENTER);

            JPanel panelPulsanti = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 15));
            JButton pulsanteTrama = new JButton("Visualizza Trama");
            JButton pulsanteRecensioni = new JButton("Recensioni");
            JButton pulsantePrenotazione = new JButton("Prenota Biglietti");

            panelPulsanti.add(pulsanteTrama);
            panelPulsanti.add(pulsanteRecensioni);
            panelPulsanti.add(pulsantePrenotazione);
            rigaFilm.add(panelPulsanti, BorderLayout.EAST);

            pulsanteRecensioni.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, "Sezione Recensioni non ancora popolata.", "Recensioni: " + film.getTitolo(), JOptionPane.INFORMATION_MESSAGE);
            });

            pulsanteTrama.addActionListener(e -> {
                mostraFinestraDettagli(film);
            });

            pulsantePrenotazione.addActionListener(e -> {
                new DashboardPrenotazione(controller, clienteLoggato, film);
            });
            panelListaFilm.add(rigaFilm);
            panelListaFilm.add(new JSeparator(JSeparator.HORIZONTAL));
        }

        tornaAlMenuButton.addActionListener(e -> {
            this.dispose();
        });
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
        dTitolo.setFont(new Font("Arial", Font.BOLD, 20));

        JLabel dInfo = new JLabel("<html><body style='font-family: Arial; font-size: 11px;'>"
                + "<b>Genere:</b> " + film.getGenere() + " &nbsp;|&nbsp; <b>Durata:</b> " + film.getDurataMinuti() + " min<br>"
                + "<b>Classificazione Età:</b> " + film.getClassificazioneEta() + "<br>"
                + "<b>Ubicazione:</b> Sala 1 &nbsp;|&nbsp; <b>Orario Sessione:</b> 18:30 - 21:00"
                + "</body></html>");
        dInfo.setBorder(BorderFactory.createEmptyBorder(10, 0, 15, 0));

        JTextArea dTrama = new JTextArea(film.getTrama());
        dTrama.setLineWrap(true);
        dTrama.setWrapStyleWord(true);
        dTrama.setEditable(false);
        dTrama.setBackground(panelContenuto.getBackground());
        dTrama.setFont(new Font("Arial", Font.ITALIC, 14));

        JScrollPane scrollTrama = new JScrollPane(dTrama);
        scrollTrama.setBorder(BorderFactory.createTitledBorder("Trama del Film"));

        panelContenuto.add(dTitolo);
        panelContenuto.add(dInfo);
        panelContenuto.add(scrollTrama);

        dialogDettagli.add(panelContenuto, BorderLayout.CENTER);

        JPanel panelInferiore = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 12));
        JButton buttonAcquista = new JButton("Acquista");
        JButton buttonChiudi = new JButton("Chiudi");

        buttonChiudi.addActionListener(ev -> dialogDettagli.dispose());
        buttonAcquista.addActionListener(ev -> {
            JOptionPane.showMessageDialog(dialogDettagli, "Funzionalità di acquisto non ancora implementata.", "Acquista", JOptionPane.INFORMATION_MESSAGE);
        });

        panelInferiore.add(buttonAcquista);
        panelInferiore.add(buttonChiudi);
        dialogDettagli.add(panelInferiore, BorderLayout.SOUTH);

        dialogDettagli.setVisible(true);
    }
}