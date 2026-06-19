package gui;

import controller.Controller;
import model.Cliente;
import javax.swing.*;
import java.awt.*;

public class DashboardBar extends JFrame {
    private JPanel panelBar;
    private JLabel labelBar;
    private JPanel panelPopCorn;
    private JLabel labelPopCorn;
    private JTextField textPopCorn;
    private JPanel panelNachos;
    private JLabel labelNachos;
    private JTextField textNachos;
    private JPanel panelBibite;
    private JLabel labelBibite;
    private JTextField textBibite;
    private JButton buttonTorna;
    private Controller controller;
    private Cliente clienteLoggato;

    public DashboardBar(Controller controller, Cliente cliente) {
        this.controller = controller;
        this.clienteLoggato = cliente;

        setContentPane(panelBar);
        setTitle("Menù Snack Bar Cinema");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(750, 400);
        setLocationRelativeTo(null);
        textPopCorn.setEditable(false);
        textNachos.setEditable(false);
        textBibite.setEditable(false);

        textPopCorn.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textNachos.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        textBibite.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));

        buttonTorna.addActionListener(e -> {
            processWindowEvent(new java.awt.event.WindowEvent(this, java.awt.event.WindowEvent.WINDOW_CLOSING));
        });

        setVisible(true);
    }
}
