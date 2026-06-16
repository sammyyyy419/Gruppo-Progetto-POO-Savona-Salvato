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
            rigaFilm.add(etichettaDati, BorderLayout.WEST);

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

        setVisible(true);
    }

}