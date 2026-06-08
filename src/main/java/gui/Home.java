package gui;

import controller.Controller;
import model.Cliente;
import model.Dipendente;
import model.Utente;

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

        buttonRegistra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Registrazione(controller);
            }
        });
    }

    private void eseguiLogin() {
        String email = textEmail.getText().trim();
        String password = new String(textPassword.getPassword()).trim();

        try {
            // 1. Valida il login tramite il controller
            controller.validaLogin(email, password);

            // 2. Recupera l'utente autenticato
            Utente u = controller.recuperaUtente(email);

            if (u instanceof model.Dipendente) {
                // CORREZIONE: Passiamo 'this.controller' e facciamo il cast di 'u' a Dipendente
                new DashboardDipendente(this.controller, (model.Dipendente) u).setVisible(true);
                this.dispose();
            } else if (u instanceof model.Cliente) {
                // CORREZIONE: Stessa cosa per il Cliente
                new DashboardCliente(this.controller, (model.Cliente) u).setVisible(true);
                this.dispose();
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore di Accesso", JOptionPane.ERROR_MESSAGE);
        }
    }
}