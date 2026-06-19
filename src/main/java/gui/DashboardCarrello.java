package gui;

import controller.Controller;
import model.Cliente;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

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

    public DashboardCarrello(Controller controller, Cliente cliente) {
        this.controller = controller;
        this.clienteLoggato = cliente;

        setContentPane(panelCarrello);
        setTitle("Il Tuo Carrello - Enterprise Cinema");
        setSize(650, 500);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        listaElementiCarrello.setLayout(new BoxLayout(listaElementiCarrello, BoxLayout.Y_AXIS));

        tornaAlMenuButton.addActionListener(e -> {
            processWindowEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        // MODIFICA: Collegamento effettivo alla DashboardPagamento
        buttonPagamento.addActionListener(e -> {
            this.dispose(); // Chiude la finestra del carrello
            DashboardPagamento finestraPagamento = new DashboardPagamento(this.controller, this.clienteLoggato);

            // Aggiungiamo un listener per riaprire il menu cliente quando il pagamento viene chiuso/completato
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

    private void aggiornaCarrello() {

        listaElementiCarrello.removeAll();
        ArrayList<Controller.ElementoCarrello> elementi = controller.getCarrello();

        if (elementi.isEmpty()) {
            JLabel vuotoLabel = new JLabel("Il tuo carrello è vuoto!", SwingConstants.CENTER);
            vuotoLabel.setFont(new Font("Arial", Font.ITALIC, 16));
            vuotoLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            listaElementiCarrello.add(Box.createVerticalStrut(120));
            listaElementiCarrello.add(vuotoLabel);

            buttonPagamento.setEnabled(false);
        } else {
            buttonPagamento.setEnabled(true);

            for (Controller.ElementoCarrello elem : elementi) {
                JPanel riga = new JPanel(new BorderLayout(15, 5));
                riga.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
                riga.setBackground(new Color(242, 245, 248));
                riga.setMaximumSize(new Dimension(Integer.MAX_VALUE, 65));

                String dataFormattata = elem.getProiezione().getDataOraInizio().toLocalDate().format(dateFormatter);
                String oraFormattata = elem.getProiezione().getDataOraInizio().toLocalTime().format(timeFormatter);
                String nomeSala = elem.getProiezione().getSala().getNumeroSala();
                String titoloFilm = elem.getProiezione().getFilm().getTitolo().toUpperCase();

                String testoBiglietto = elem.getQuantita() + "x tickets per : " + titoloFilm
                        + " (" + dataFormattata + " - " + nomeSala + " - ore " + oraFormattata + ")";

                JLabel labelInfo = new JLabel(testoBiglietto);
                labelInfo.setFont(new Font("Arial", Font.PLAIN, 12));

                JPanel panelDestro = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
                panelDestro.setOpaque(false);

                JLabel labelPrezzoParziale = new JLabel(String.format("%.2f €", elem.getPrezzoTotale()));
                labelPrezzoParziale.setFont(new Font("Arial", Font.BOLD, 14));

                JButton buttonCancellaElemento = new JButton("❌");
                buttonCancellaElemento.setToolTipText("Rimuovi questo ordine dal carrello");
                buttonCancellaElemento.setFocusable(false);
                buttonCancellaElemento.setCursor(new Cursor(Cursor.HAND_CURSOR));

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

        labelTotale.setText("TOT. : " + String.format("%.2f €", controller.calcolaTotaleCarrello()));
        listaElementiCarrello.revalidate();
        listaElementiCarrello.repaint();
    }
}