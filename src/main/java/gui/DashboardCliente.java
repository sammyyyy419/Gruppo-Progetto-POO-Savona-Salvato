package gui;

import controller.Controller;
import model.Cliente;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DashboardCliente extends JFrame {
    private JPanel mainPanel;
    private JButton buttonCatalogoFilm;
    private JButton buttonMenuBar;
    private JButton buttonEsci;
    private JButton buttonModificaCredenziali;
    private JButton buttonCarrello;
    private JButton buttonBiglietti;
    private JButton buttonTornaLogin;

    private Controller controller;
    private Cliente clienteLoggato;

    /**
     * Inizializza la dashboard per il cliente autenticato, configurando il layout e gli ascoltatori
     * per gli eventi dei bottoni che permettono di passare alle altre schermate dell'applicazione.
     *
     * @param controller il controller di sistema per gestire la logica di business.
     * @param cliente il {@link Cliente} che ha effettuato l'accesso.
     */
    public DashboardCliente(Controller controller, Cliente cliente) {
        this.controller = controller;
        this.clienteLoggato = cliente;

        setTitle("Benvenuto/a " + cliente.getNome() + " !");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        sistemaGrafica();

        setContentPane(mainPanel);
        setSize(680, 500);
        setMinimumSize(new Dimension(620, 460));
        setLocationRelativeTo(null);
        setVisible(true);

        buttonEsci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        buttonTornaLogin.addActionListener(e -> {
            this.dispose();
            new Home(this.controller);
        });

        buttonCatalogoFilm.addActionListener(e -> {
            this.setVisible(false);
            DashboardCatalogoFilm finestraCatalogo = new DashboardCatalogoFilm(this.controller, this.clienteLoggato);
            finestraCatalogo.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    DashboardCliente.this.setVisible(true);
                }
            });
        });

        buttonMenuBar.addActionListener(e -> {
            this.setVisible(false);
            DashboardBar finestraBar = new DashboardBar(this.controller, this.clienteLoggato);

            finestraBar.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    DashboardCliente.this.setVisible(true);
                }
            });
        });

        buttonCarrello.addActionListener(e -> {
            this.setVisible(false);
            DashboardCarrello finestraCarrello = new DashboardCarrello(this.controller, this.clienteLoggato);

            finestraCarrello.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    DashboardCliente.this.setVisible(true);
                }
            });
        });

        buttonBiglietti.addActionListener(e -> {
            this.setVisible(false);
            DashboardBigliettiAcquistati finestraBiglietti = new DashboardBigliettiAcquistati(this.controller, this.clienteLoggato);

            finestraBiglietti.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    DashboardCliente.this.setVisible(true);
                }
            });
        });

        buttonModificaCredenziali.addActionListener(e -> {
            this.dispose();
            new DashboardModificaCredenziali(this.controller, this.clienteLoggato);
        });
    }

    private void sistemaGrafica() {
        Color pannello = new Color(28, 34, 58);
        Color testo = Color.WHITE;
        Color blu = new Color(54, 112, 233);
        Color viola = new Color(126, 87, 194);
        Color grigioScuro = new Color(50, 58, 89);
        Color rossoMuto = new Color(176, 58, 75);

        mainPanel.removeAll();
        mainPanel.setLayout(new BorderLayout());

        JPanel pannelloCentrale = new JPanel();
        pannelloCentrale.setBackground(pannello);
        pannelloCentrale.setLayout(new GridBagLayout());
        pannelloCentrale.setBorder(new EmptyBorder(25, 30, 25, 30));

        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(8, 8, 8, 8);
        c.fill = GridBagConstraints.BOTH;
        c.weightx = 1.0;

        JLabel labelServizi = new JLabel("Ecco i nostri servizi per te :");
        labelServizi.setForeground(testo);
        labelServizi.setFont(new Font("SansSerif", Font.BOLD, 15));
        labelServizi.setHorizontalAlignment(SwingConstants.CENTER);

        JButton[] bottoniAzzurri = {buttonCatalogoFilm, buttonBiglietti};
        for (JButton b : bottoniAzzurri) {
            b.setBackground(blu);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setPreferredSize(new Dimension(220, 45));
        }

        JButton[] bottoniViola = {buttonMenuBar, buttonCarrello};
        for (JButton b : bottoniViola) {
            b.setBackground(viola);
            b.setForeground(Color.WHITE);
            b.setFocusPainted(false);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
            b.setPreferredSize(new Dimension(220, 45));
        }

        buttonModificaCredenziali.setBackground(grigioScuro);
        buttonModificaCredenziali.setForeground(Color.WHITE);
        buttonModificaCredenziali.setFocusPainted(false);
        buttonModificaCredenziali.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonModificaCredenziali.setPreferredSize(new Dimension(450, 42));

        buttonTornaLogin.setBackground(grigioScuro);
        buttonTornaLogin.setForeground(Color.WHITE);
        buttonTornaLogin.setFocusPainted(false);
        buttonTornaLogin.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonTornaLogin.setPreferredSize(new Dimension(150, 38));

        buttonEsci.setBackground(rossoMuto);
        buttonEsci.setForeground(Color.WHITE);
        buttonEsci.setFocusPainted(false);
        buttonEsci.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonEsci.setPreferredSize(new Dimension(150, 38));

        c.gridx = 0;
        c.gridy = 0;
        c.gridwidth = 2;
        c.insets = new Insets(5, 8, 20, 8);
        pannelloCentrale.add(labelServizi, c);

        c.insets = new Insets(8, 8, 8, 8);
        c.gridwidth = 1;

        c.gridy = 1;
        c.gridx = 0; pannelloCentrale.add(buttonCatalogoFilm, c);
        c.gridx = 1; pannelloCentrale.add(buttonBiglietti, c);

        c.gridy = 2;
        c.gridx = 0; pannelloCentrale.add(buttonMenuBar, c);
        c.gridx = 1; pannelloCentrale.add(buttonCarrello, c);

        c.gridy = 3;
        c.gridx = 0;
        c.gridwidth = 2;
        c.insets = new Insets(12, 8, 12, 8);
        pannelloCentrale.add(buttonModificaCredenziali, c);

        JPanel pannelloFooter = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        pannelloFooter.setBackground(pannello);
        pannelloFooter.add(buttonTornaLogin);
        pannelloFooter.add(buttonEsci);

        c.gridy = 4;
        c.gridx = 0;
        c.gridwidth = 2;
        c.insets = new Insets(8, 8, 5, 8);
        pannelloCentrale.add(pannelloFooter, c);

        mainPanel.add(pannelloCentrale, BorderLayout.CENTER);

        mainPanel.revalidate();
        mainPanel.repaint();
    }
}