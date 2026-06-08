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
    private Dipendente dipendente;
    private Cliente cliente;

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
            // 1. Chiama il metodo del controller (che può lanciare Exception)
            controller.validaLogin(email, password);

            // 2. Se le credenziali sono corrette, recupera l'utente ed esegui lo switch delle schermate
            Utente u = controller.recuperaUtente(email);

            if (u instanceof model.Dipendente) {
                new DashboardDipendente(controller,dipendente).setVisible(true);
                this.dispose();
            } else if (u instanceof model.Cliente) {
                new DashboardCliente(controller,cliente).setVisible(true);
                this.dispose();
            }

        } catch (Exception e) {
            // <--- CAMBIA IL CATCH QUI METTENDO 'Exception e'
            // In questo modo catturi qualsiasi errore e mostri il messaggio impostato nel controller
            JOptionPane.showMessageDialog(this, e.getMessage(), "Errore di Accesso", JOptionPane.ERROR_MESSAGE);
        }
    }
}