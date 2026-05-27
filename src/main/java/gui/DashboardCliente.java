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
            JOptionPane.showMessageDialog(this, "Apertura Catalogo Film in corso...");
        });
    }
}