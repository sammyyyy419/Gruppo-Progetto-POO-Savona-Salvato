package gui;

import controller.Controller;
import model.Cliente;
import javax.swing.*;
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

    private Controller controller;
    private Cliente clienteLoggato;

    public DashboardCliente(Controller controller, Cliente cliente) {
        this.controller = controller;
        this.clienteLoggato = cliente;

        setContentPane(mainPanel);
        setTitle("Benvenuto/a " + cliente.getNome() + " !");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);

        buttonEsci.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
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

        // ======= MODIFICA APPORTATA QUI =======
        buttonMenuBar.addActionListener(e -> {
            this.setVisible(false); // Nasconde la dashboard del cliente
            DashboardBar finestraBar = new DashboardBar(this.controller, this.clienteLoggato); // Apre il menù del bar

            // Aggiungiamo un listener per sapere quando la finestra del bar viene chiusa
            finestraBar.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    DashboardCliente.this.setVisible(true); // Mostra di nuovo la dashboard cliente
                }
            });
        });
        // ======================================

        // Da inserire nel costruttore di DashboardCliente insieme agli altri listener
        buttonCarrello.addActionListener(e -> {
            this.setVisible(false);
            DashboardCarrello finestraCarrello = new DashboardCarrello(this.controller, this.clienteLoggato);

            // Quando si chiude il carrello, si torna al menu cliente
            finestraCarrello.addWindowListener(new java.awt.event.WindowAdapter() {
                @Override
                public void windowClosing(java.awt.event.WindowEvent windowEvent) {
                    DashboardCliente.this.setVisible(true);
                }
            });
        });

        buttonModificaCredenziali.addActionListener(e -> {
            JOptionPane.showMessageDialog(this,"Apertura procedura per modificare le credenziali...");
        });
    }
}