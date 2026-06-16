package gui;

import controller.Controller;
import model.Cliente;
import model.Film;
import javax.swing.*;
import java.awt.*;
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
        setSize(600, 500);
        setLocationRelativeTo(null);
        panelListaFilm.setLayout(new BoxLayout(panelListaFilm, BoxLayout.Y_AXIS));
        ArrayList<Film> filmDisponibili = controller.getListaFilm();

        for (Film film : filmDisponibili) {

            JPanel rigaFilm = new JPanel(new BorderLayout());
            rigaFilm.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

            String testoFilm = film.getTitolo() + " - Genere: " + film.getGenere() + " - Durata: " + film.getDurata();
            JLabel etichettaDati = new JLabel(testoFilm);
            etichettaDati.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

            JLabel labelLocandina = new JLabel();
            String titolo = film.getTitolo().toLowerCase().replace(" ", "_");
            String nomeImmagine = "/" + titolo + ".png";
            java.net.URL imageUrl = DashboardCatalogoFilm.class.getResource(nomeImmagine);

            if (imageUrl != null) {
                ImageIcon iconaOriginale = new ImageIcon(imageUrl);
                Image imgScalata = iconaOriginale.getImage().getScaledInstance(60, 90, Image.SCALE_SMOOTH);
                labelLocandina.setIcon(new ImageIcon(imgScalata));
            } else {
                labelLocandina.setText("No Cover");
                labelLocandina.setPreferredSize(new Dimension(60, 90));
                labelLocandina.setHorizontalAlignment(SwingConstants.CENTER);
                labelLocandina.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            }

            rigaFilm.add(labelLocandina, BorderLayout.WEST);
            rigaFilm.add(etichettaDati, BorderLayout.CENTER);

            JPanel panelPulsanti = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            JButton pulsanteTrama = new JButton("Visualizza Trama");
            JButton pulsantePrenotazione = new JButton("Prenota Biglietti");

            panelPulsanti.add(pulsanteTrama);
            panelPulsanti.add(pulsantePrenotazione);
            rigaFilm.add(panelPulsanti, BorderLayout.EAST);

            pulsanteTrama.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, film.getTrama(), film.getTitolo() + " : ", JOptionPane.INFORMATION_MESSAGE);
            });

            panelListaFilm.add(rigaFilm);
            panelListaFilm.add(new JSeparator(JSeparator.HORIZONTAL));
        }

        tornaAlMenuButton.addActionListener(e -> {
            this.dispose();
        });

        panelListaFilm.revalidate();
        panelListaFilm.repaint();
        setVisible(true);
    }
}