package gui;

import controller.Controller;
import model.Cliente;
import model.Film;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class CatalogoFilm extends JFrame {
    private JPanel panelCatalogo;
    private JButton tornaAlMenuButton;
    private JScrollPane scrollPanelFilm;
    private JPanel panelListaFilm;
    private Controller controller;
    private Cliente clienteLoggato;

    public CatalogoFilm(Controller controller, Cliente cliente) {
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

            JPanel rigaFilm = new JPanel();
            //aggiungere locandina a lato
            String testoFilm = film.getTitolo() + " - Genere: " + film.getGenere() + " - Durata: " + film.getDurata();
            JLabel etichettaDati = new JLabel(testoFilm);
            JButton pulsanteTrama = new JButton("Visualizza Trama");
            JButton pulsantePrenotazione = new JButton("Prenota Biglietti");

            pulsanteTrama.addActionListener(e -> {
                JOptionPane.showMessageDialog(this, film.getTrama(), film.getTitolo() + " : ", JOptionPane.INFORMATION_MESSAGE);
            });

           /*
           pulsantePrenotazione.addActionListener(e -> {
              da implementare
           });

            */
            rigaFilm.add(etichettaDati);
            rigaFilm.add(pulsanteTrama);
            rigaFilm.add(pulsantePrenotazione);

            panelListaFilm.add(rigaFilm);
        }

        tornaAlMenuButton.addActionListener(e -> {
            this.dispose();});

        setVisible(true);
    }


}
