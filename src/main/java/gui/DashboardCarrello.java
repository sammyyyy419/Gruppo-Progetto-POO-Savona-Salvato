package gui;

import controller.Controller;
import model.Cliente;
import model.Carrello;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

/**
 * The type Dashboard carrello.
 */
public class DashboardCarrello extends JFrame {

    private JPanel panelCarrello;
    private JScrollPane scrollCarrello;
    private JPanel listaElementiCarrello;
    private JPanel panelTitoloCarrello;
    private JButton tornaAlMenuButton;
    private JLabel labelCarrello;
    private JPanel panelPagamento;
    private JLabel labelTotale;
    private JButton buttonPagamento;

    private Controller controller;
    private Cliente clienteLoggato;
    private DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

    /**
     * Instantiates a new Dashboard carrello.
     *
     * @param controller the controller
     * @param cliente    the cliente
     */
    public DashboardCarrello(Controller controller, Cliente cliente) {
        this.controller = controller;
        this.clienteLoggato = cliente;

        sistemaGrafica();

        setContentPane(panelCarrello);
        setTitle("Il Tuo Carrello - Enterprise Cinema");
        setSize(720, 540);
        setMinimumSize(new Dimension(660, 480));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        listaElementiCarrello.setLayout(new BoxLayout(listaElementiCarrello, BoxLayout.Y_AXIS));

        tornaAlMenuButton.addActionListener(e -> {
            processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        buttonPagamento.addActionListener(e -> {
            this.dispose();
            DashboardPagamento finestraPagamento = new DashboardPagamento(this.controller, this.clienteLoggato);

            finestraPagamento.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                    new DashboardCliente(controller, clienteLoggato);
                }
            });
        });

        aggiornaCarrello();

        setVisible(true);
    }

    private void sistemaGrafica() {
        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color testoChiaro = Color.WHITE;
        Color bluAcceso = new Color(54, 112, 233);
        Color grigioScuro = new Color(50, 58, 89);

        panelCarrello.setLayout(new BorderLayout());
        panelCarrello.setBackground(sfondoScuro);

        panelTitoloCarrello.setBackground(sfondoScuro);
        panelTitoloCarrello.setLayout(new BorderLayout(15, 0));
        panelTitoloCarrello.setBorder(new EmptyBorder(15, 20, 15, 20));

        labelCarrello.setForeground(testoChiaro);
        labelCarrello.setFont(new Font("SansSerif", Font.BOLD, 18));
        labelCarrello.setText("Il Tuo Carrello");

        tornaAlMenuButton.setText("Indietro");
        tornaAlMenuButton.setBackground(grigioScuro);
        tornaAlMenuButton.setForeground(testoChiaro);
        tornaAlMenuButton.setFocusPainted(false);
        tornaAlMenuButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        tornaAlMenuButton.setPreferredSize(new Dimension(100, 32));

        panelTitoloCarrello.add(labelCarrello, BorderLayout.WEST);
        panelTitoloCarrello.add(tornaAlMenuButton, BorderLayout.EAST);

        scrollCarrello.setBorder(BorderFactory.createEmptyBorder());
        scrollCarrello.setBackground(sfondoPannello);
        scrollCarrello.getViewport().setBackground(sfondoPannello);

        listaElementiCarrello.setBackground(sfondoPannello);
        listaElementiCarrello.setBorder(new EmptyBorder(15, 20, 15, 20));

        panelPagamento.setBackground(sfondoScuro);
        panelPagamento.setLayout(new BorderLayout());
        panelPagamento.setBorder(new EmptyBorder(15, 20, 15, 20));

        labelTotale.setForeground(testoChiaro);
        labelTotale.setFont(new Font("SansSerif", Font.BOLD, 18));

        buttonPagamento.setText("Procedi al Pagamento");
        buttonPagamento.setBackground(bluAcceso);
        buttonPagamento.setForeground(testoChiaro);
        buttonPagamento.setFocusPainted(false);
        buttonPagamento.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonPagamento.setPreferredSize(new Dimension(200, 42));
        buttonPagamento.setFont(new Font("SansSerif", Font.BOLD, 13));

        panelPagamento.add(labelTotale, BorderLayout.WEST);
        panelPagamento.add(buttonPagamento, BorderLayout.EAST);

        panelCarrello.add(panelTitoloCarrello, BorderLayout.NORTH);
        panelCarrello.add(scrollCarrello, BorderLayout.CENTER);
        panelCarrello.add(panelPagamento, BorderLayout.SOUTH);
    }

    private void aggiornaCarrello() {
        listaElementiCarrello.removeAll();
        ArrayList<Carrello> elementi = controller.getCarrello();

        Color sfondoRiga = new Color(38, 46, 78);
        Color testoChiaro = Color.WHITE;

        if (elementi.isEmpty()) {
            JLabel vuotoLabel = new JLabel("Il tuo carrello è vuoto!", SwingConstants.CENTER);
            vuotoLabel.setForeground(new Color(140, 150, 180));
            vuotoLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
            vuotoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            listaElementiCarrello.add(Box.createVerticalStrut(120));
            listaElementiCarrello.add(vuotoLabel);

            buttonPagamento.setEnabled(false);
        } else {
            buttonPagamento.setEnabled(true);

            for (Carrello elem : elementi) {
                JPanel riga = new JPanel(new BorderLayout(15, 5));
                riga.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                riga.setBackground(sfondoRiga);
                riga.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));

                String dataFormattata = elem.getProiezione().getDataOraInizio().toLocalDate().format(dateFormatter);
                String oraFormattata = elem.getProiezione().getDataOraInizio().toLocalTime().format(timeFormatter);
                String nomeSala = elem.getProiezione().getSala().getNumeroSala();
                String titoloFilm = elem.getProiezione().getFilm().getTitolo().toUpperCase();

                String testoBiglietto = elem.getQuantita() + "x tickets per : " + titoloFilm
                        + " (" + dataFormattata + " - " + nomeSala + " - ore " + oraFormattata + ")";

                JLabel labelInfo = new JLabel(testoBiglietto);
                labelInfo.setForeground(testoChiaro);
                labelInfo.setFont(new Font("SansSerif", Font.PLAIN, 13));

                JPanel panelDestro = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
                panelDestro.setOpaque(false);

                JLabel labelPrezzoParziale = new JLabel(String.format("%.2f €", elem.getPrezzoTotale()));
                labelPrezzoParziale.setForeground(new Color(100, 210, 140));
                labelPrezzoParziale.setFont(new Font("SansSerif", Font.BOLD, 14));

                JButton buttonCancellaElemento = new JButton("❌");
                buttonCancellaElemento.setToolTipText("Rimuovi questo ordine dal carrello");
                buttonCancellaElemento.setFocusable(false);
                buttonCancellaElemento.setCursor(new Cursor(Cursor.HAND_CURSOR));
                buttonCancellaElemento.setContentAreaFilled(false);
                buttonCancellaElemento.setBorder(BorderFactory.createEmptyBorder());

                buttonCancellaElemento.addActionListener(ev -> {
                    controller.rimuoviDalCarrello(elem);
                    aggiornaCarrello();
                });

                panelDestro.add(labelPrezzoParziale);
                panelDestro.add(buttonCancellaElemento);

                riga.add(labelInfo, BorderLayout.CENTER);
                riga.add(panelDestro, BorderLayout.EAST);

                listaElementiCarrello.add(riga);
                listaElementiCarrello.add(Box.createVerticalStrut(8));
            }
        }

        labelTotale.setText("TOTALE: " + String.format("%.2f €", controller.calcolaTotaleCarrello()));
        listaElementiCarrello.revalidate();
        listaElementiCarrello.repaint();
    }
}