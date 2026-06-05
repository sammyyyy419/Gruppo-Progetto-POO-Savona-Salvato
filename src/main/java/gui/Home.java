package gui;

import controller.Controller;
import exception.PasswordErrataException;
import exception.UtenteNonTrovatoException;
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
            controller.validaLogin(email, password);

            Utente u = controller.recuperaUtente(email);

            if (u instanceof Cliente) {
                new DashboardCliente(controller, (Cliente) u);
                this.dispose();
            } else if (u instanceof Dipendente) {
                new DashboardDipendente(controller, (Dipendente) u);
                this.dispose();
            }
        } catch (UtenteNonTrovatoException | PasswordErrataException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Errore di Accesso", JOptionPane.ERROR_MESSAGE);
        }
    }
}