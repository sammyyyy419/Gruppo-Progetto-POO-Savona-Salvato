package gui;

import controller.Controller;
import model.Cliente;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class DashboardBar extends JFrame {
    private JPanel panelBar;
    private JLabel labelBar;
    private JPanel panelPopCorn;
    private JLabel labelPopCorn;
    private JPanel panelNachos;
    private JLabel labelNachos;
    private JPanel panelBibite;
    private JLabel labelBibite;
    private JButton buttonTorna;
    private JLabel labelTipoPopCorn;
    private JLabel labelTipoNachos;
    private JLabel labelTipoBibite;
    private Controller controller;
    private Cliente clienteLoggato;

    public DashboardBar(Controller controller, Cliente cliente) {
        this.controller = controller;
        this.clienteLoggato = cliente;

        sistemaGrafica();

        setContentPane(panelBar);
        setTitle("Menù Snack Bar Cinema");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(840, 540);
        setMinimumSize(new Dimension(760, 480));
        setLocationRelativeTo(null);

        buttonTorna.addActionListener(e -> {
            processWindowEvent(new java.awt.event.WindowEvent(this, java.awt.event.WindowEvent.WINDOW_CLOSING));
        });

        setVisible(true);
    }

    private void sistemaGrafica() {
        Color sfondoScuro = new Color(18, 22, 40);
        Color sfondoPannello = new Color(28, 34, 58);
        Color sfondoRiga = new Color(38, 46, 78);
        Color testoChiaro = Color.WHITE;
        Color bluAcceso = new Color(54, 112, 233);
        Color grigioScuro = new Color(50, 58, 89);

        panelBar.removeAll();
        panelBar.setLayout(new BorderLayout());
        panelBar.setBackground(sfondoScuro);

        JPanel panelTop = new JPanel(new BorderLayout(15, 0));
        panelTop.setBackground(sfondoScuro);
        panelTop.setBorder(new EmptyBorder(15, 20, 15, 20));

        labelBar.setForeground(testoChiaro);
        labelBar.setFont(new Font("SansSerif", Font.BOLD, 18));
        labelBar.setText("Il Nostro Menù Bar");

        buttonTorna.setText("Indietro");
        buttonTorna.setBackground(grigioScuro);
        buttonTorna.setForeground(testoChiaro);
        buttonTorna.setFocusPainted(false);
        buttonTorna.setCursor(new Cursor(Cursor.HAND_CURSOR));
        buttonTorna.setPreferredSize(new Dimension(100, 32));

        panelTop.add(labelBar, BorderLayout.WEST);
        panelTop.add(buttonTorna, BorderLayout.EAST);

        JPanel panelContenuto = new JPanel();
        panelContenuto.setLayout(new BoxLayout(panelContenuto, BoxLayout.Y_AXIS));
        panelContenuto.setBackground(sfondoPannello);
        panelContenuto.setBorder(new EmptyBorder(20, 20, 20, 20));

        panelPopCorn.removeAll();
        panelPopCorn.setLayout(new BorderLayout(20, 0));
        panelPopCorn.setBackground(sfondoRiga);
        panelPopCorn.setBorder(new EmptyBorder(12, 15, 12, 15));
        panelPopCorn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));

        labelPopCorn.setText("PopCorn :");
        labelPopCorn.setForeground(bluAcceso);
        labelPopCorn.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelPopCorn.setPreferredSize(new Dimension(85, 30));

        labelTipoPopCorn.setForeground(testoChiaro);
        labelTipoPopCorn.setFont(new Font("SansSerif", Font.PLAIN, 13));
        labelTipoPopCorn.setText("<html>• PopCorn Grandi &nbsp;&nbsp;—&nbsp;&nbsp; 5,00 €<br>• PopCorn Medi &nbsp;&nbsp;&nbsp;&nbsp;—&nbsp;&nbsp; 4,00 €<br>• PopCorn Piccoli &nbsp;&nbsp;—&nbsp;&nbsp; 3,00 €</html>");

        panelPopCorn.add(labelPopCorn, BorderLayout.WEST);
        panelPopCorn.add(labelTipoPopCorn, BorderLayout.CENTER);

        panelNachos.removeAll();
        panelNachos.setLayout(new BorderLayout(20, 0));
        panelNachos.setBackground(sfondoRiga);
        panelNachos.setBorder(new EmptyBorder(12, 15, 12, 15));
        panelNachos.setMaximumSize(new Dimension(Integer.MAX_VALUE, 95));

        labelNachos.setText("Nachos :");
        labelNachos.setForeground(bluAcceso);
        labelNachos.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelNachos.setPreferredSize(new Dimension(85, 30));

        labelTipoNachos.setForeground(testoChiaro);
        labelTipoNachos.setFont(new Font("SansSerif", Font.PLAIN, 13));
        labelTipoNachos.setText("<html>• Nachos con Mayo &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;—&nbsp;&nbsp; 5,50 €<br>• Nachos con Guacamole &nbsp;&nbsp;&nbsp;&nbsp;—&nbsp;&nbsp; 6,00 €<br>• Nachos con Cheddar e Bacon —&nbsp;&nbsp; 6,50 €</html>");

        panelNachos.add(labelNachos, BorderLayout.WEST);
        panelNachos.add(labelTipoNachos, BorderLayout.CENTER);

        panelBibite.removeAll();
        panelBibite.setLayout(new BorderLayout(20, 0));
        panelBibite.setBackground(sfondoRiga);
        panelBibite.setBorder(new EmptyBorder(12, 15, 12, 15));
        panelBibite.setMaximumSize(new Dimension(Integer.MAX_VALUE, 135));

        labelBibite.setText("Bibite :");
        labelBibite.setForeground(bluAcceso);
        labelBibite.setFont(new Font("SansSerif", Font.BOLD, 14));
        labelBibite.setPreferredSize(new Dimension(85, 30));

        labelTipoBibite.setForeground(testoChiaro);
        labelTipoBibite.setFont(new Font("SansSerif", Font.PLAIN, 13));
        labelTipoBibite.setText("<html>• Acqua &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;—&nbsp;&nbsp; 1,00 €<br>• Coca-Cola &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;—&nbsp;&nbsp; 1,50 €<br>• Pepsi &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;—&nbsp;&nbsp; 2,00 €<br>• Birra Piccola &nbsp;&nbsp;&nbsp;—&nbsp;&nbsp; 3,00 €<br>• Birra Grande &nbsp;&nbsp;—&nbsp;&nbsp; 4,50 €</html>");

        panelBibite.add(labelBibite, BorderLayout.WEST);
        panelBibite.add(labelTipoBibite, BorderLayout.CENTER);

        panelContenuto.add(panelPopCorn);
        panelContenuto.add(Box.createVerticalStrut(12));
        panelContenuto.add(panelNachos);
        panelContenuto.add(Box.createVerticalStrut(12));
        panelContenuto.add(panelBibite);

        panelBar.add(panelTop, BorderLayout.NORTH);
        panelBar.add(panelContenuto, BorderLayout.CENTER);
    }
}