package gui;

import controller.Controller;
import model.Cliente;
import model.Dipendente;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Home extends JFrame {
    private JPanel mainPanel;
    private JLabel labelTitolo;
    private JLabel labelEmail;
    private JTextField textEmail;
    private JLabel labelPassword;
    private JPasswordField textPassword;
    private JButton buttonRegistra;
    private JButton buttonAccedi;
    private Controller controller;

    public static void main(String[] args) {

        Controller ctrl = new Controller();

        new Home(ctrl);
    }

    public Home(Controller controller) {
        this.controller = controller;

        setContentPane(mainPanel);
        setTitle("Login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);


        buttonAccedi.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                eseguiLogin();
            }
        });
    }

    private void eseguiLogin() {
        String email = textEmail.getText().trim();
        String password = new String(textPassword.getPassword()).trim();

        String esito = controller.verificaLogin(email, password);

        if (esito.equals("CLIENTE")) {
            Cliente c = (Cliente) controller.recuperaUtente(email);
            new DashboardCliente(controller, c);
            this.dispose();
        } else if (esito.equals("DIPENDENTE")) {
            Dipendente d = (Dipendente) controller.recuperaUtente(email);
            // Tra poco creeremo DashboardDipendente
        //    new DashboardDipendente(controller, d);
          //  this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenziali errate!");
        }
    }
}